package com.proxmox.firewall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirewallController {

	@Value("${proxmox.api.url}")
	private String proxmoxApiUrl;

	@Value("${proxmox.token.id}")
	private String tokenId;

	@Value("${proxmox.token.secret}")
	private String tokenSecret;

	@Autowired
	private FirewallServiceimpl firewallServiceimpl;


	@PostMapping("create-firewall/{vmid}")
	public ResponseEntity<String> createfirewall(@RequestBody FirewallRule firewallRule, @PathVariable Long vmid) {
		try {
			System.out.println("inside contoller post");
			return firewallServiceimpl.createfirewall(firewallRule, vmid);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
		}
	}
	
}