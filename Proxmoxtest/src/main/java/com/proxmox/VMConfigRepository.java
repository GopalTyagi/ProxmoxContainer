package com.proxmox;

//VMConfigRepository.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VMConfigRepository extends JpaRepository<VMConfig, Long> {
}
