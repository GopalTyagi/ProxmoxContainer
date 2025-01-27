package com.proxmox;


import org.springframework.http.ResponseEntity;

public interface SaveProxmox {

	ResponseEntity<String> createVM(ProxmoxDetails proxmoxDetails) throws Exception;

}