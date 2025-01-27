package com.proxmox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VMController {

    @Autowired
    private VMConfigService vmConfigService;

    @Autowired
    private ProxmoxAPI proxmoxAPI;

    @PostMapping("/create-vm")
    public ResponseEntity<String> createVM(@RequestBody VMConfig vmConfig) {
        System.out.println("Creating VM with hostname: " + vmConfig.getHostname());
        try {
            SSLUtil.disableCertificateValidation();

            System.out.println("inside try");
            
            // Call Proxmox API to create the container
            proxmoxAPI.createContainer(vmConfig);

            vmConfigService.createAndSaveVMConfig(vmConfig);
            System.out.println("inside try 2222");
            
            // Instead of saving, just return a success message
            return ResponseEntity.ok("VM created successfully with VMID : " + vmConfig.getVmid());
        } catch (Exception e) {
            System.err.println("Error creating VM: " + e.getMessage());
            return ResponseEntity.status(500).body("Error creating VM: " + e.getMessage()); // Return error message
        }
    }
}