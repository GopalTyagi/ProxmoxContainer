package com.proxmox.sdn;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity(name = "Vnets_Info")
public class Vnets {

	@Id
	@GeneratedValue
	@Column(name = "VnetId", length = 100)
	private Long VnetId;

	@Column(name = "vnet", length = 50)
	private String vnet;

	@Column(name = "alias", length = 100)
	private String alias;

	@Column(name = "zone", length = 100)
	private String zone;

	public Long getVnetId() {
		return VnetId;
	}

	public void setVnetId(Long vnetId) {
		VnetId = vnetId;
	}

	public String getVnet() {
		return vnet;
	}

	public void setVnet(String vnet) {
		this.vnet = vnet;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

}
