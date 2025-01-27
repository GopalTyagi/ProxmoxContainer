package com.proxmox.test.Service;

import org.springframework.http.ResponseEntity;

import com.proxmox.test.entity.ProxmoxDetails;

public interface SaveProxmox {

	ResponseEntity<String> createVM(ProxmoxDetails proxmoxDetails) throws Exception;

}
