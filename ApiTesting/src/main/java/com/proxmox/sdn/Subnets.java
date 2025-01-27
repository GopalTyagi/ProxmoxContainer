package com.proxmox.sdn;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity(name = "Subnets_Info")
public class Subnets {

	@Id
	@GeneratedValue
	@Column(name = "SubnetId", length = 100)
	private Long SubnetId;

	@Column(name = "vnet", length = 50)
	private String vnet;

	@Column(name = "subnet", length = 100)
	private String subnet;
	
	@Column(name = "dnszoneprefix", length = 100)
	private String dnszoneprefix ;
	
	@Column(name = "gateway", length = 100)
	private String gateway ;
	
	
	@Column(name = "snat", length = 100)
	private boolean snat ;
	
	@Column(name = "type", length = 100)
	private String type;


	public Long getSubnetId() {
		return SubnetId;
	}

	public void setSubnetId(Long subnetId) {
		SubnetId = subnetId;
	}

	public String getVnet() {
		return vnet;
	}

	public void setVnet(String vnet) {
		this.vnet = vnet;
	}

	
	public String getSubnet() {
		return subnet;
	}

	public void setSubnet(String subnet) {
		this.subnet = subnet;
	}
	
	

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getDnszoneprefix() {
		return dnszoneprefix;
	}

	public void setDnszoneprefix(String dnszoneprefix) {
		this.dnszoneprefix = dnszoneprefix;
	}

	public boolean isSnat() {
		return snat;
	}

	public void setSnat(boolean snat) {
		this.snat = snat;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}