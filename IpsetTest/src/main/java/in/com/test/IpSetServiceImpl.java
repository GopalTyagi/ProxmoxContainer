package in.com.test;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class IpSetServiceImpl {

    @Value("${proxmox.api.url}")
    private String proxmoxApiUrl;

    @Value("${proxmox.token.id}")
    private String tokenId;

    @Value("${proxmox.token.secret}")
    private String tokenSecret;

    @Autowired
    private IpsetRepo repo; // Assuming you have an IpSetRepo for database operations

 //   private AtomicLong idCounter = new AtomicLong(1000);

//    public Long generateId() {
//        return idCounter.incrementAndGet(); // Ensure this starts from a valid base
//    }

    public ResponseEntity<String> createIpSet(IpSet ipSetDetails) {
      //  Long generatedId = generateId();
     //   ipSetDetails.setId(generatedId); // Assuming IpSet has an ID field

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Construct the payload for creating an IP set
        Map<String, Object> payload = new HashMap<>();
        payload.put("cidr", ipSetDetails.getCidr());
        payload.put("name", ipSetDetails.getName());
        if (ipSetDetails.getComment() != null) {
            payload.put("comment", ipSetDetails.getComment());
        }
//        if (ipSetDetails.getNoMatch() != null) {
//            payload.put("nomatch", ipSetDetails.getNoMatch());
//        }

        // Serialize the payload to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload;
        try {
            jsonPayload = objectMapper.writeValueAsString(payload);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error serializing request body: " + e.getMessage());
        }

        headers.setContentLength(jsonPayload.getBytes(StandardCharsets.UTF_8).length);

        // Define the API endpoint for creating an IP set
        String url = String.format("%s/cluster/firewall/ipset/%s", proxmoxApiUrl, ipSetDetails.getName());
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

        try {
            // Make the API call to Proxmox to create the IP set
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            // Check the response status and handle success or failure
            if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
                // Save the IP set to the database (ID is auto-generated by JPA)
                repo.save(ipSetDetails);
                return ResponseEntity.ok("IP Set created successfully: " + response.getBody());
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Failed to create IP Set. Status: "
                        + response.getStatusCode() + ", Body: " + response.getBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }
}