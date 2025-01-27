package com.proxmox101;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proxmox.VMConfig;
@RestController
public class VMPController {

	@Autowired
	 SaveProxmox saveProxmox;
	
	 @PostMapping("/create-vm111")
	    public ResponseEntity<String> createVM(@RequestBody VMConfig proxmoxDetails) {
	        try {
	            return saveProxmox.createVM(proxmoxDetails);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Error occurred: " + e.getMessage());
	        }
	    }
	
}
