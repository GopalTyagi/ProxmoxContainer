package com.proxmox.sdn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SDNController {

	/*
	 * SDNController handles HTTP POST requests to create zones, VNets, and subnets
	 * via the SDNService.
	 */

	@Autowired
	SDNService sdnService;

	@PostMapping("/zones")
	public ResponseEntity<String> createZones(@RequestBody Zones zones) throws Exception {
		return sdnService.createZones(zones);
	}

	@PostMapping("/vnets")
	public ResponseEntity<String> createVnets(@RequestBody Vnets vnets) throws Exception {
		return sdnService.createVnets(vnets);
	}

	@PostMapping("/subnets")
	public ResponseEntity<String> createSubnets(@RequestBody Subnets subnets) throws Exception {
		return sdnService.createSubnets(subnets);
	}

	@GetMapping("/vnets/{vnet}/subnets")
	public ResponseEntity<String> getSubnets(@PathVariable String vnet) throws Exception {
		return sdnService.getSubnets(vnet);
	}

	@GetMapping("/{zone}")
	public ResponseEntity<String> getZone(@PathVariable String zone) {
		return sdnService.getZone(zone);
	}

	@DeleteMapping("/{zone}")
	public ResponseEntity<String> deleteZone(@PathVariable String zone) {
		return sdnService.deleteZones(zone);

	}

	@DeleteMapping("/{vnet}/subnets/{subnet}")
	public ResponseEntity<String> deleteSubnet(@PathVariable String vnet, @PathVariable String subnet) {
		return sdnService.deleteSubnet(vnet, subnet);
	}

}
