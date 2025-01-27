package com.proxmox;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
@RestController
public class Controller {

	@Value("${proxmox.api.url}")
	private String proxmoxApiUrl;

	@Value("${proxmox.token.id}")
	private String tokenId;

	@Value("${proxmox.token.secret}")
	private String tokenSecret;

	//@Autowired
	//private SaveProxmox saveProxmox;
	
	@Autowired
	private VMConfigRepository configRepository;

	private AtomicLong idCounter = new AtomicLong(1000);

	// Generate a unique ID (you can customize this logic based on your needs)
	public Long generateId() {
		// Generate a new ID by incrementing the counter
		return idCounter.incrementAndGet();
	}

	@PostMapping("/create-vm")
	public ResponseEntity<String> createVM(@RequestBody VMConfig proxmoxDetails) {

		Long generatedId = generateId();
		proxmoxDetails.setVmid(generatedId); // Set the generated ID manually

		System.err.println(">>"+proxmoxDetails.getVmid());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Construct payload
		Map<String, Object> payload = new HashMap<>();
		payload.put("vmid", proxmoxDetails.getVmid());
		payload.put("name", proxmoxDetails.getNode());
		payload.put("memory", proxmoxDetails.getMemory());
		payload.put("cores", proxmoxDetails.getCores());
		payload.put("sockets", 1);
		payload.put("scsihw", "virtio-scsi-pci");
		payload.put("net0", "virtio,bridge=vmbr0");
		payload.put("ostype", "l26");

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

		// Create HttpEntity with JSON body and headers
		HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

		// API endpoint URL
		String url = proxmoxApiUrl + "/nodes/" + proxmoxDetails.getNode() + "/lxc";

		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

			if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
				configRepository.save(proxmoxDetails);
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