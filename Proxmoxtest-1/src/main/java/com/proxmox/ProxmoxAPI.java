package com.proxmox;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ProxmoxAPI {

    @Value("${proxmox.api.url}")
    private String proxmoxApiUrl;

    @Value("${proxmox.token.id}")
    private String tokenId;

    @Value("${proxmox.token.secret}")
    private String tokenSecret;

    public void createContainer(VMConfig config) throws Exception {
        // Construct URL for creating LXC container
        String urlString = proxmoxApiUrl + "/nodes/" + config.getNode() + "/lxc"; 
        System.out.println("Creating container at URL: " + urlString);

        // Initialize HTTP connection
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Set authorization header
        String authorizationHeader = "PVEAPIToken=" + tokenId + "=" + tokenSecret;
        connection.setRequestProperty("Authorization", authorizationHeader);
        connection.setRequestProperty("Content-Type", "application/json");

        // Convert VM configuration to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInputString = objectMapper.writeValueAsString(config);
        
        // Write JSON to request body
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Get response code and handle response
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
            System.out.println("Container created successfully!");
        } else {
            System.out.println("Failed to create container. Response Code: " + responseCode);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    errorResponse.append(line);
                }
                System.out.println("Error Response: " + errorResponse.toString());
                System.out.println("Request URL: " + urlString);
                System.out.println("Authorization Header: " + authorizationHeader);
                System.out.println("Request Body: " + jsonInputString);
            }
        }
    }
}