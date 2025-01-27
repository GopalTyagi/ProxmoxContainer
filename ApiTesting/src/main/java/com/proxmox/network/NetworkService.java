package com.proxmox.network;

import org.springframework.http.ResponseEntity;

public interface NetworkService {

	ResponseEntity<String> createNetwork(NetworkDetails networkDetails) throws Exception;

	ResponseEntity<String> reloadNetworkConfig(String node) throws Exception;

	ResponseEntity<String> deleteNetworkConfig(String node) throws Exception;

	//ResponseEntity<String> updateNetworkIface(String iface, NetworkDetails networkDetails, Long id) throws Exception;

	ResponseEntity<String> deleteNetworkConfigWithIface(String iface,Long id,String node) throws Exception;

}