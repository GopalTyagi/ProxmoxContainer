package com.proxmox;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity(name = "proxmox_details")
public class ProxmoxDetails {

	@Id
	@Column(name = "vm_id", length = 100)
	private Long vmId;

	@Column(name = "cores", length = 50)
	private int cores;

	@Column(name = "node", nullable = false, length = 150)
	private String node;
	
	@Column(name = "storage", length = 100)
	private String storage;

	@Column(name = "memory", length = 100)
	private int memory;
	
	@Column(name = "ostemplate", length = 100)
	private String ostemplate;

	@Column(name = "password", length = 100)
	private String password;

	@Column(name = "hostname", length = 100)
	private String hostname;
	
	
	@Column(name = "tags", length = 100)
	private String tags;
	
	@Enumerated(EnumType.STRING)
	private Architecture arch;

	@Enumerated(EnumType.STRING)
	private OperatingSystemType ostype;

	
	public enum Architecture {
		amd64, i386, arm64, armhf, riscv32, riscv64;
	}

	public enum OperatingSystemType {
		debian, devuan, ubuntu, centos, fedora, opensuse, archlinux, alpine, gentoo, nixos;
	}

	public Long getVmId() {
		return vmId;
	}

	public void setVmId(Long vmId) {
		this.vmId = vmId;
	}

	public int getCores() {
		return cores;
	}

	public void setCores(int cores) {
		this.cores = cores;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public String getOstemplate() {
		return ostemplate;
	}

	public void setOstemplate(String ostemplate) {
		this.ostemplate = ostemplate;
	}



	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Architecture getArch() {
		return arch;
	}

	public void setArch(Architecture arch) {
		this.arch = arch;
	}

	public OperatingSystemType getOstype() {
		return ostype;
	}

	public void setOstype(OperatingSystemType ostype) {
		this.ostype = ostype;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}