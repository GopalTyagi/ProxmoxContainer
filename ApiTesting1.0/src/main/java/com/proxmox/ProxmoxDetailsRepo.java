package com.proxmox;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ProxmoxDetailsRepo extends JpaRepository<ProxmoxDetails, Long> {

	@Query(value = "SELECT COALESCE(MAX(vm_id), 0) FROM proxmox_details", nativeQuery = true)
	Long findMaxVmId();
}