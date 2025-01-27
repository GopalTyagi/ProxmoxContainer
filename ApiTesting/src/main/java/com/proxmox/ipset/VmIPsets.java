package com.proxmox.ipset;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "VmIPsets")
public class VmIPsets {

	@Id
	public String Name;
	public String Node;
	public Integer Vmid;
	public String Comment;
public String cidr;


	
	public String getCidr() {
	return cidr;
}

public void setCidr(String cidr) {
	this.cidr = cidr;
}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getNode() {
		return Node;
	}

	public void setNode(String node) {
		Node = node;
	}

	public Integer getVmid() {
		return Vmid;
	}

	public void setVmid(Integer vmid) {
		Vmid = vmid;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		Comment = comment;
	}

}
