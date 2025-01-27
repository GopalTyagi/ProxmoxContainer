package com.proxmox.ipset;

import org.springframework.data.repository.CrudRepository;


public interface IpsetRepo extends CrudRepository<IpsetNew, String> {

}
