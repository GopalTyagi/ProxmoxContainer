package in.com.firewall;

import org.springframework.http.ResponseEntity;


public interface SaveProxmox {

	ResponseEntity<String> createfirewall(FirewallRule firewallRule ,Long vmid) throws Exception;

}