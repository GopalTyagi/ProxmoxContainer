package com.proxmox101;


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
import com.proxmox.VMConfig;
import com.proxmox.VMConfigRepository;

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
	VMConfigRepository configRepository;

	// Generate a unique ID
	private AtomicLong idCounter = new AtomicLong(1000);

	public Long generateId() {
		// Generate a new ID by incrementing the counter
		return idCounter.incrementAndGet();
	}

	// Create VM logic
	public ResponseEntity<String> createVM(VMConfig vmConfig) {
		// Generate and set a unique ID
		Long generatedId = generateId();
		vmConfig.setVmid(generatedId);
		vmConfig.setNode(proxmoxApiUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Construct payload
		Map<String, Object> payload = new HashMap();
		payload.put("vmid", vmConfig.getVmid());
		payload.put("name", vmConfig.getNode());
//		payload.put("memory", vmConfig.getMemory());
//		payload.put("cores", vmConfig.getCores());
//		payload.put("sockets", 1);
//		payload.put("scsihw", "virtio-scsi-pci");
//		payload.put("net0", "virtio,bridge=vmbr0");
//		payload.put("ostype", "l26");

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
		String url = proxmoxApiUrl + "/nodes/" + vmConfig.getNode() + "/lxc";

		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

			if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
				// Save the VM details to the repository
//				detailsRepo.save(proxmoxDetails);
				HttpStatusCode status = response.getStatusCode();
				return ResponseEntity.ok("VM created successfully: " + response.getBody());
			} else {
				return ResponseEntity.status(response.getStatusCode()).body(
						"Failed to create VM. Status: " + response.getStatusCode() + ", Body: " + response.getBody());
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
		}
	}

	
}