package com.proxmox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VMConfigService {

   @Autowired
   private VMConfigRepository vmConfigRepository;

 

  
   public List<VMConfig> getAllVMConfigs() {
       return vmConfigRepository.findAll();
   }
}