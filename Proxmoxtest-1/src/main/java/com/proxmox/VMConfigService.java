package com.proxmox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VMConfigService {

    @Autowired
    private VMConfigRepository vmConfigRepository;

    public VMConfig createAndSaveVMConfig(VMConfig vmConfig) {
        return vmConfigRepository.save(vmConfig);
    }

    public List<VMConfig> getAllVMConfigs() {
        return vmConfigRepository.findAll();
    }

}