package com.proxmox.ipset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/ipset")
public class IpsetController {

    @Autowired
    private IpSetService ipSetService;

    @PostMapping
    public ResponseEntity<String> createIpSetNew(@RequestBody IpsetNew ipSet) {
        return ipSetService.createIpSetNew(ipSet);
    }
    
    @PostMapping("/cidr")
    public ResponseEntity<String> createIpSet(@RequestBody IpsetNew ipSet) {
        return ipSetService.createIpSet(ipSet);
    }
    
    @PostMapping("/vm")
    public ResponseEntity<String> createVmIPsets(@RequestBody VmIPsets vmIPsets) {
        return ipSetService.createVmIPsets(vmIPsets);
    }
    
    @PostMapping("/vm/cidr")
    public ResponseEntity<String> createVmIPsetsCidr(@RequestBody VmIPsets vmIPsets) {
        return ipSetService.createVmIPsetsCidr(vmIPsets);
    
    }
}
    
