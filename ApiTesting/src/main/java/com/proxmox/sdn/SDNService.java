package com.proxmox.sdn;

import org.springframework.http.ResponseEntity;

public interface SDNService {

	ResponseEntity<String> createZones(Zones zones) throws Exception;

	ResponseEntity<String> createVnets(Vnets vnets) throws Exception;
	
	ResponseEntity<String> createSubnets(Subnets subnets) throws Exception;

	ResponseEntity<String> getSubnets(String vnet);

	ResponseEntity<String> getZone(String zone);
	
	ResponseEntity<String> deleteZones(String zone);

	ResponseEntity<String> deleteSubnet(String vnet, String subnet);

//	ResponseEntity<String> deleteVnet(String zone);

	


}