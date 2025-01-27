package com.proxmox;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

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

	private AtomicLong idCounter = new AtomicLong(1000);

	public Long generateId() {
		return idCounter.incrementAndGet(); // Ensure this starts from a valid base
	}

	/*
	 * public Long generateId() { Long maxVmId = detailsRepo.findMaxVmId(); return
	 * maxVmId + 1; }
	 */

	@Override
	public ResponseEntity<String> createVM(ProxmoxDetails proxmoxDetails) {
		Long generatedId = generateId();
		proxmoxDetails.setVmId(generatedId);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> payload = new HashMap<>();
		payload.put("vmid", proxmoxDetails.getVmId());
		payload.put("node", proxmoxDetails.getNode());
		payload.put("memory", proxmoxDetails.getMemory());
		payload.put("cores", proxmoxDetails.getCores());
		payload.put("storage", proxmoxDetails.getStorage());
		payload.put("password", proxmoxDetails.getPassword());
		payload.put("hostname", "innprox-hostnameC");
		payload.put("ostemplate", proxmoxDetails.getOstemplate());
		payload.put("arch", proxmoxDetails.getArch());
		payload.put("ostype", proxmoxDetails.getOstype());
		payload.put("tags", proxmoxDetails.getTags());

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonPayload;
		try {
			jsonPayload = objectMapper.writeValueAsString(payload);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error serializing request body: " + e.getMessage());
		}

		headers.setContentLength(jsonPayload.getBytes(StandardCharsets.UTF_8).length);

		// String url = String.format("%s/nodes/%s/qemu", proxmoxApiUrl,
		// proxmoxDetails.getNode());
		String url = proxmoxApiUrl + "nodes/" + proxmoxDetails.getNode() + "/lxc";

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

			if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
				detailsRepo.save(proxmoxDetails);
				return ResponseEntity.ok("VM created successfully: " + response.getBody());
			} else {
				return ResponseEntity.status(response.getStatusCode()).body(
						"Failed to create VM. Status: " + response.getStatusCode() + ", Body: " + response.getBody());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
		}
	}

	/*
	 * // Generate a unique ID private AtomicLong idCounter = new AtomicLong(1000);
	 * 
	 * public Long generateId() { // Generate a new ID by incrementing the counter
	 * return idCounter.incrementAndGet(); }
	 * 
	 * // Create VM logic public ResponseEntity<String> createVM(ProxmoxDetails
	 * proxmoxDetails) { // Generate and set a unique ID Long generatedId =
	 * generateId(); proxmoxDetails.setVmId(generatedId); //
	 * proxmoxDetails.setNode(proxmoxApiUrl); HttpHeaders headers = new
	 * HttpHeaders(); headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" +
	 * tokenSecret); headers.setContentType(MediaType.APPLICATION_JSON);
	 * 
	 * System.out.println("inside create vm" + headers);
	 * 
	 * // Construct payload Map<String, Object> payload = new HashMap();
	 * payload.put("vmid", proxmoxDetails.getVmId()); payload.put("node",
	 * proxmoxDetails.getNode()); payload.put("memory", proxmoxDetails.getMemory());
	 * payload.put("cores", proxmoxDetails.getCores()); payload.put("storage",
	 * proxmoxDetails.getStorage()); payload.put("password",
	 * proxmoxDetails.getPassword()); payload.put("hostname", "innprox-hostnameC");
	 * payload.put("ostemplate", proxmoxDetails.getOstemplate());
	 * payload.put("arch", proxmoxDetails.getArch()); payload.put("ostype",
	 * proxmoxDetails.getOstype());
	 * 
	 * // Serialize payload to JSON ObjectMapper objectMapper = new ObjectMapper();
	 * String jsonPayload; try { jsonPayload =
	 * objectMapper.writeValueAsString(payload); } catch (Exception e) { return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	 * .body("Error serializing request body: " + e.getMessage()); }
	 * 
	 * // Set Content-Length to avoid chunked transfer encoding
	 * headers.setContentLength(jsonPayload.getBytes(StandardCharsets.UTF_8).length)
	 * ;
	 * 
	 * HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);
	 * 
	 * // API endpoint URL
	 * 
	 * // String url = proxmoxApiUrl + "/nodes/" + proxmoxDetails.getNode() +
	 * "/lxc";
	 * 
	 * String url = proxmoxApiUrl + "nodes/" + proxmoxDetails.getNode() + "/lxc";
	 * 
	 * System.out.println(proxmoxApiUrl + "   ( and different node is :=> " +
	 * proxmoxDetails.getNode());
	 * 
	 * RestTemplate restTemplate = new RestTemplate(); try { ResponseEntity<String>
	 * response = restTemplate.postForEntity(url, request, String.class);
	 * 
	 * if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() ==
	 * HttpStatus.CREATED) { // Save the VM details to the repository
	 * 
	 * detailsRepo.save(proxmoxDetails); HttpStatusCode status =
	 * response.getStatusCode(); return
	 * ResponseEntity.ok("VM created successfully: " + response.getBody()); } else {
	 * return ResponseEntity.status(response.getStatusCode()).body(
	 * "Failed to create VM. Status: " + response.getStatusCode() + ", Body: " +
	 * response.getBody()); } } catch (Exception e) { return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
	 * body("Error occurred: " + e.getMessage()); } }
	 */
}
