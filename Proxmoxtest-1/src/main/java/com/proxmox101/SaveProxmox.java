package com.proxmox101;

import org.springframework.http.ResponseEntity;

import com.proxmox.VMConfig;


public interface SaveProxmox {
	ResponseEntity<String> createVM(VMConfig vmConfig) throws Exception;

}
