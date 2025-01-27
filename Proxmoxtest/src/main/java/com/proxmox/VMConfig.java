package com.proxmox;

import jakarta.persistence.*;

@Entity
@Table(name = "vm_configsPro")
public class VMConfig {
 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Vmid;
    private String storage;
    private int cores;
    private String ostemplate; // required
    private String pool;
    private String password;
    private String hostname;
    private String memory;
    private String node; 
    


    public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	@Enumerated(EnumType.STRING)
    private Architecture arch;

    @Enumerated(EnumType.STRING)
    private OperatingSystemType ostype;

   

    public enum Architecture {
        AMD64, I386, ARM64;
    }

    public enum OperatingSystemType {
        DEBIAN, DEVUAN, UBUNTU, CENTOS, FEDORA;
    }

    // Getters and Setters...

  
    public String getStorage() {
        return storage;
    }

  
	public Long getVmid() {
		return Vmid;
	}

	public void setVmid(Long vmid) {
		Vmid = vmid;
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
    
   // Getters and Setters for tokenId and tokenSecret
 
   public String getNode() {
       return node;
   }

   public void setNode(String node) {
       this.node = node;
   }
}