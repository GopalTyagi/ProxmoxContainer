package com.proxmox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VMConfigRepository extends JpaRepository<VMConfig, Integer> {


}