package com.proxmox;


import org.springframework.http.ResponseEntity;

import com.proxmox.container.ProxmoxDetails;

public interface SaveProxmox {

	ResponseEntity<String> createContainer(ProxmoxDetails proxmoxDetails) throws Exception;

}