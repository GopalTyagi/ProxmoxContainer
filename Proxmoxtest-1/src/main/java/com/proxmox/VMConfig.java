package com.proxmox;

import jakarta.persistence.*;

@Entity
@Table(name = "vm_configs")
public class VMConfig {

	@Id
	@GeneratedValue
	private Long vmid; // VM ID (can be auto-generated)
	private String node;
	private String storage;
	private int cores;
	private String ostemplate; // required
	private String pool;
	private String password;
	private String hostname;
	private String net0;

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

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	

	public Long getVmid() {
		return vmid;
	}

	public void setVmid(Long vmid) {
		this.vmid = vmid;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public int getCores() {
		return cores;
	}

	public void setCores(int cores) {
		this.cores = cores;
	}

	public String getOstemplate() {
		return ostemplate;
	}

	public void setOstemplate(String ostemplate) {
		this.ostemplate = ostemplate;
	}

	public String getPool() {
		return pool;
	}

	public void setPool(String pool) {
		this.pool = pool;
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

	public String getNet0() {
		return net0;
	}

	public void setNet0(String net0) {
		this.net0 = net0;
	}

}