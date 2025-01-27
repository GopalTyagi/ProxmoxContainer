package com.proxmox.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proxmox.test.entity.ProxmoxDetails;

public interface ProxmoxDetailsRepo extends JpaRepository<ProxmoxDetails, Long> {

}
