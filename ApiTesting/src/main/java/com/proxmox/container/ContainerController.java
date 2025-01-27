package com.proxmox.container;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.proxmox.SaveProxmox;

@RestController
public class ContainerController {

	@Value("${proxmox.api.url}")
	private String proxmoxApiUrl;

	@Value("${proxmox.token.id}")
	private String tokenId;

	@Value("${proxmox.token.secret}")
	private String tokenSecret;

	@Autowired
	SaveProxmox saveProxmox;
	
	    @PostMapping("/create-vm")
	    public ResponseEntity<String> createContainer(@RequestBody ProxmoxDetails proxmoxDetails) {
	        try {
	        	 System.out.println("inside contoller post");
	        	return saveProxmox.createContainer(proxmoxDetails);
	           
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Error occurred: " + e.getMessage());
	        }
	    }
}