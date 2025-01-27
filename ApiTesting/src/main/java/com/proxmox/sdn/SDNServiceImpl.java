package com.proxmox.sdn;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SDNServiceImpl implements SDNService {

	/*
	 * SDNServiceImpl is a Spring service that manages Proxmox SDN components,
	 * including zones, VNets, and subnets, through the Proxmox API.
	 */

	@Value("${proxmox.api.url}")
	private String proxmoxApiUrl;

	@Value("${proxmox.token.id}")
	private String tokenId;

	@Value("${proxmox.token.secret}")
	private String tokenSecret;

	@Autowired
	ZonesRepository repository;

	@Autowired
	VnetsRepository vnetsRepository;

	@Autowired
	SubnetsRepository subnetsRepository;

	public ResponseEntity<String> createZones(Zones zones) {

		String url = String.format("%s/cluster/sdn/zones", proxmoxApiUrl);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> payload = new HashMap<>();
		payload.put("zone", zones.getZone());
		payload.put("mtu", zones.getMtu());
		payload.put("nodes", zones.getNodes());
		payload.put("type", zones.getType());
		payload.put("ipam", zones.getIpam());

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonPayload;

		try {
			jsonPayload = objectMapper.writeValueAsString(payload);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error serializing request body: " + e.getMessage());
		}

		// Create HttpEntity with payload and headers
		HttpEntity<String> httpEntity = new HttpEntity<>(jsonPayload, headers);

		// Use RestTemplate to send the request
		RestTemplate restTemplate = new RestTemplate();

		try {
			// Send the POST request to Proxmox
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

			if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
				// Save the network details to the database
				repository.save(zones); // Save to the database

				// Return success response
				return ResponseEntity.ok("Zones created successfully: " + response.getBody());
			} else {
				return ResponseEntity.status(response.getStatusCode())
						.body("Failed to create Zones. Response: " + response.getBody());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error occurred while calling Proxmox API: " + e.getMessage());
		}
	}

	public ResponseEntity<String> createVnets(Vnets vnets) throws Exception {

		String url = String.format("%s/cluster/sdn/vnets", proxmoxApiUrl);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Construct the payload for creating a network
		Map<String, Object> payload = new HashMap<>();
		payload.put("vnet", vnets.getVnet());
		payload.put("alias", vnets.getAlias());
		payload.put("zone", vnets.getZone());

		// Serialize the payload to JSON
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonPayload;

		try {
			jsonPayload = objectMapper.writeValueAsString(payload);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error serializing request body: " + e.getMessage());
		}

		// Create HttpEntity with payload and headers
		HttpEntity<String> httpEntity = new HttpEntity<>(jsonPayload, headers);

		// Use RestTemplate to send the request
		RestTemplate restTemplate = new RestTemplate();

		try {
			// Send the POST request to Proxmox
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

			if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
				// Save the network details to the database
				vnetsRepository.save(vnets); // Save to the database

				// Return success response
				return ResponseEntity.ok("Vnets created successfully: " + response.getBody());
			} else {
				return ResponseEntity.status(response.getStatusCode())
						.body("Failed to create Vnets. Response: " + response.getBody());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error occurred while calling Proxmox API: " + e.getMessage());
		}
	}

	public ResponseEntity<String> createSubnets(Subnets subnets) throws Exception {

		String url = String.format("%s/cluster/sdn/vnets/%s/subnets", proxmoxApiUrl, subnets.getVnet());

		// Prepare headers with authorization
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Construct the payload for creating a network
		Map<String, Object> payload = new HashMap<>();
		payload.put("subnet", subnets.getSubnet()); // Ensure type is in string format
		payload.put("dnszoneprefix", subnets.getDnszoneprefix());
		payload.put("vnet", subnets.getVnet());
		payload.put("snat", subnets.isSnat());
		payload.put("gateway", subnets.getGateway());
		payload.put("type", subnets.getType());

		// Serialize the payload to JSON
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonPayload;

		try {
			jsonPayload = objectMapper.writeValueAsString(payload);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error serializing request body: " + e.getMessage());
		}

		// Create HttpEntity with payload and headers
		HttpEntity<String> httpEntity = new HttpEntity<>(jsonPayload, headers);

		// Use RestTemplate to send the request
		RestTemplate restTemplate = new RestTemplate();

		try {
			// Send the POST request to Proxmox
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

			// Check if the response is successful
			if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
				// Save the network details to the database
				subnetsRepository.save(subnets); // Save to the database

				// Return success response
				return ResponseEntity.ok("Subnets created successfully: " + response.getBody());
			} else {
				return ResponseEntity.status(response.getStatusCode())
						.body("Failed to create Subnets. Response: " + response.getBody());
			}

		} catch (Exception e) {
			// Handle exception if the request fails
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error occurred while calling Proxmox API: " + e.getMessage());
		}
	}

	public ResponseEntity<String> getSubnets(String vnet) {
		String url = String.format("%s/cluster/sdn/vnets/%s/subnets", proxmoxApiUrl, vnet);

		// Prepare headers with authorization
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);

		// Use RestTemplate to send the request
		RestTemplate restTemplate = new RestTemplate();

		try {
			// Send the GET request to Proxmox
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers),
					String.class);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error occurred while calling Proxmox API: " + e.getMessage());
		}
	}

	public ResponseEntity<String> deleteZones(String zone) {
		String url = String.format("%s/cluster/sdn/zones/%s", proxmoxApiUrl, zone);

		// Prepare headers with authorization
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create HttpEntity with headers (no body needed for DELETE)
		HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

		// Use RestTemplate to send the request
		RestTemplate restTemplate = new RestTemplate();

		try {
			// Send the DELETE request to Proxmox
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, String.class);

			// Check if the response status is OK (200)
			if (response.getStatusCode() == HttpStatus.OK) {
				// Return a success message if the deletion was successful
				return ResponseEntity.ok("Zone '" + zone + "' has been successfully deleted.");
			} else {
				// Handle unexpected response status
				return ResponseEntity.status(response.getStatusCode()).body("Failed to delete zone '" + zone
						+ "'. Server responded with status: " + response.getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			String customMessage = "On this zone some Vnets are running.. Please try again later.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(customMessage + " Error details: " + e.getMessage());
		}
	}

	public ResponseEntity<String> getZone(String zone) {
		String url = String.format("%s/cluster/sdn/zones/%s", proxmoxApiUrl, zone);

		// Prepare headers with authorization
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create HttpEntity with headers (no body needed for GET)
		HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

		// Use RestTemplate to send the request
		RestTemplate restTemplate = new RestTemplate();

		try {
			// Send the GET request to Proxmox
			return restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving zone details: " + e.getMessage());
		}
	}

	public ResponseEntity<String> deleteSubnet(String vnet, String subnet) {
		String url = String.format("%s/cluster/sdn/vnets/%s/subnets/%s", proxmoxApiUrl, vnet, subnet);
		System.out.println(url);
		// Prepare headers with authorization
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create HttpEntity with headers (no body needed for DELETE)
		HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

		// Use RestTemplate to send the request
		RestTemplate restTemplate = new RestTemplate();

		try {
			// Send the DELETE request to Proxmox
			return restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting subnet: " + e.getMessage());
		}
	}
}
