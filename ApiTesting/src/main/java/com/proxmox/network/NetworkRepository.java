package com.proxmox.network;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NetworkRepository extends JpaRepository<NetworkDetails,Long>{


	@Query(value = "SELECT COALESCE(MAX(vm_id), 0) FROM network_info", nativeQuery = true)
	Long findMaxVmId();
    
	void deleteByNode(String node);
    
	void deleteByNetworkId(Long id);

}
