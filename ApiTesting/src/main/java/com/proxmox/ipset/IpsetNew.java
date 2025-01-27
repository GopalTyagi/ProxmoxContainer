package com.proxmox.ipset;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "IpSetNew")
public class IpsetNew {

	@Id
	public String Name;
	public String Cidr;
	public String Comment;
	
	
	public String getCidr() {
		return Cidr;
	}
	public void setCidr(String cidr) {
		Cidr = cidr;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}

	
	
}


