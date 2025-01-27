package com.proxmox.network;

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
public class NetworkServiceImpl implements NetworkService {

	@Value("${proxmox.api.url}")
	private String proxmoxApiUrl;

	@Value("${proxmox.token.id}")
	private String tokenId;

	@Value("${proxmox.token.secret}")
	private String tokenSecret;

	@Autowired
	NetworkRepository networkRepository;


	public ResponseEntity<String> createNetwork(NetworkDetails networkDetails) {
		// Construct the Proxmox API endpoint URL
		String url = String.format("%s/nodes/%s/network", proxmoxApiUrl, networkDetails.getNode());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> payload = new HashMap<>();
		payload.put("type", networkDetails.getType().name()); // Ensure type is in string format
		payload.put("iface", networkDetails.getIface());
		payload.put("comments", networkDetails.getComments());

		payload.put("cidr", networkDetails.getCidr());
		payload.put("netmask", networkDetails.getNetmask());
		payload.put("gateway", networkDetails.getGateway());

		payload.put("autostart", networkDetails.isAutostart());
		payload.put("address", networkDetails.getAddress());

		payload.put("bridge_vlan_aware", networkDetails.isBridge_vlan_aware());

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
				networkRepository.save(networkDetails); 

				// Return success response
				return ResponseEntity.ok("Network created successfully: " + response.getBody());
			} else {
				return ResponseEntity.status(response.getStatusCode())
						.body("Failed to create network. Response: " + response.getBody());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error occurred while calling Proxmox API: " + e.getMessage());
		}
	}

	public ResponseEntity<String> reloadNetworkConfig(String node) throws Exception {
		String url = String.format("%s/nodes/%s/network", proxmoxApiUrl, node);

		// Construct the headers with authorization
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Send an empty JSON object '{}' as the body
		HttpEntity<String> entity = new HttpEntity<>("{}", headers); // Passing an empty JSON object body

		RestTemplate restTemplate = new RestTemplate();

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
			return response;
		} catch (Exception e) {
			return new ResponseEntity<>("Error while reloading network config: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<String> deleteNetworkConfig(String node) {
		String url = String.format("%s/nodes/%s/qemu/%s/firewall/rules/%s", proxmoxApiUrl, node);
		System.out.println("URL: " + url);

		// Set up the authorization headers
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);
		headers.setContentType(MediaType.APPLICATION_JSON);

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<>(headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
			System.out.println("Response: " + response);

			if (response.getStatusCode() == HttpStatus.OK) {
				networkRepository.deleteByNode(node);
				return ResponseEntity.ok("Network Configuration deleted successfully: " + response.getBody());
			} else {
				return ResponseEntity.status(response.getStatusCode())
						.body("Failed to delete Network Configuration. Status: " + response.getStatusCode() + ", Body: "
								+ response.getBody());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
		}
	}
	public ResponseEntity<String> deleteNetworkConfigWithIface(String iface, Long id, String node) {
		String url = String.format("%s/nodes/%s/network/%s", proxmoxApiUrl, node, iface);
		System.out.println("URL: " + url);

		// Set up the authorization headers
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create a REST template for the API call
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<>(headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
			System.out.println("Response: " + response);

			if (response.getStatusCode() == HttpStatus.OK) {
				networkRepository.deleteByNetworkId(id);
				return ResponseEntity.ok("Interface Network Configuration deleted successfully: " + response.getBody());
			} else {
				return ResponseEntity.status(response.getStatusCode())
						.body("Failed to delete interface Network Configuration. Status: " + response.getStatusCode()
								+ ", Body: " + response.getBody());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
		}
	}

}
