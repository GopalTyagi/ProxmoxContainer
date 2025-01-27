package com.proxmox.network;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NetworkController {

	@Autowired
	NetworkService networkService;

	@PostMapping("/network")
	public ResponseEntity<String> createNetwork(@RequestBody NetworkDetails networkDetails) throws Exception {
		if (networkDetails.getType() == null || networkDetails.getNode() == null || networkDetails.getIface() == null) {
			return ResponseEntity.badRequest().body("Missing required parameters: type, node, iface.");
		}
		return networkService.createNetwork(networkDetails);

	}

	@PutMapping("/{node}/network")
	public ResponseEntity<String> reloadNetwork(@PathVariable String node) throws Exception {
		return networkService.reloadNetworkConfig(node);
	}

	@DeleteMapping("/{node}/network")
	public ResponseEntity<String> deleteNetwork(@PathVariable String node) throws Exception {
		return networkService.deleteNetworkConfig(node);
	}
	
	@DeleteMapping("/{node}/network/{id}/{iface}")
	public ResponseEntity<String> deleteNetworkConfigWithIface(@PathVariable String iface,@PathVariable Long id,@PathVariable String node) throws Exception {
		return networkService.deleteNetworkConfigWithIface(iface,id,node);
	}


}