package com.proxmox.sdn;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Table
@Entity(name = "Zones_Info")
public class Zones {

	@Id
	@GeneratedValue
	@Column(name = "ZoneId", length = 100)
	private Long ZoneId;

	@Column(name = "mtu", length = 50)
	private int mtu;

	@Column(name = "zone", length = 100)
	private String zone;
	
	@Column(name = "nodes", length = 100)
	private String nodes;

	
	
	@Enumerated(EnumType.STRING)
	private type type;

	public enum type {
		evpn , faucet , qinq , simple , vlan , vxlan
	}
	
	@Column(name = "ipam", length = 50)
	private String ipam;

	
	public Long getZoneId() {
		return ZoneId;
	}

	public void setZoneId(Long zoneId) {
		ZoneId = zoneId;
	}

	public int getMtu() {
		return mtu;
	}

	public void setMtu(int mtu) {
		this.mtu = mtu;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getNodes() {
		return nodes;
	}

	public void setNodes(String nodes) {
		this.nodes = nodes;
	}

	
	

	public type getType() {
		return type;
	}

	public void setType(type type) {
		this.type = type;
	}

	public String getIpam() {
		return ipam;
	}

	public void setIpam(String ipam) {
		this.ipam = ipam;
	}

	
	
}
