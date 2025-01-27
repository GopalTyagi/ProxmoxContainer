package com.proxmox.container;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proxmox.SaveProxmox;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProxmoxServiceimpl implements SaveProxmox {

	@Value("${proxmox.api.url}")
	private String proxmoxApiUrl;

	@Value("${proxmox.token.id}")
	private String tokenId;

	@Value("${proxmox.token.secret}")
	private String tokenSecret;

	@Autowired
	ProxmoxDetailsRepo detailsRepo;

	// Generate a unique ID
	private AtomicLong idCounter = new AtomicLong(1000);

	public Long generateId() {
		// Generate a new ID by incrementing the counter
		return idCounter.incrementAndGet();
	}

	// Create VM logic
	public ResponseEntity<String> createContainer(ProxmoxDetails proxmoxDetails) {
		// Generate and set a unique ID
		Long generatedId = generateId();
		proxmoxDetails.setVmId(generatedId);
        // proxmoxDetails.setNode(proxmoxApiUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);
		headers.setContentType(MediaType.APPLICATION_JSON);

		System.out.println("inside create Container" + headers);

		// Construct payload
		Map<String, Object> payload = new HashMap();
		payload.put("vmid", proxmoxDetails.getVmId());
		payload.put("node", proxmoxDetails.getNode());
		payload.put("memory", proxmoxDetails.getMemory());
		payload.put("cores", proxmoxDetails.getCores());
		payload.put("storage", proxmoxDetails.getStorage());
		payload.put("password", proxmoxDetails.getPassword());
		payload.put("hostname", proxmoxDetails.getHostname());
		payload.put("ostemplate", proxmoxDetails.getOstemplate());
		payload.put("arch", proxmoxDetails.getArch());
		payload.put("ostype", proxmoxDetails.getOstype());

		// Serialize payload to JSON
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonPayload;
		try {
			jsonPayload = objectMapper.writeValueAsString(payload);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error serializing request body: " + e.getMessage());
		}

		// Set Content-Length to avoid chunked transfer encoding
		headers.setContentLength(jsonPayload.getBytes(StandardCharsets.UTF_8).length);

		HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

		// API endpoint URL

		// String url = proxmoxApiUrl + "/nodes/" + proxmoxDetails.getNode() + "/lxc";

		String url = proxmoxApiUrl + "nodes/" + proxmoxDetails.getNode() + "/lxc";

		System.out.println(proxmoxApiUrl + "   ( and different node is :=> " + proxmoxDetails.getNode());

		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

			if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
				// Save the VM details to the repository
//				detailsRepo.save(proxmoxDetails);
				HttpStatusCode status = response.getStatusCode();
				return ResponseEntity.ok("Container created successfully: " + response.getBody());
			} else {
				return ResponseEntity.status(response.getStatusCode()).body("Failed to create Container. Status: "
						+ response.getStatusCode() + ", Body: " + response.getBody());
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
		}
	}

}