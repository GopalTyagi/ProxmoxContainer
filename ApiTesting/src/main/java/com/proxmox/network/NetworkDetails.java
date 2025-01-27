package com.proxmox.network;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Network_info")
public class NetworkDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long networkId;

	@Column(name = "iface", nullable = true, length = 150) 
	private String iface;

	@Column(name = "node", nullable = true, length = 150)
	private String node;

	@Enumerated(EnumType.STRING)
	private Type type;

	public enum Type {
		bridge, bond, eth, alias, vlan, OVSBridge, OVSBond, OVSPort, OVSIntPort, unknown
	}

	@Column(name = "cidr")
	private String cidr;

	@Column(name = "address")
	private String address;

	@Column(name = "bridge_vlan_aware")
	private boolean bridge_vlan_aware;
	

	@Column(name = "gateway")
	private String gateway;

	@Column(name = "autostart")
	private boolean autostart;

	@Column(name = "comments")
	private String comments;
	
	@Column(name = "netmask")
	private String netmask;
	
	

	public String getNetmask() {
		return netmask;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	public Long getNetworkId() {
		return networkId;
	}

	public void setNetworkId(Long networkId) {
		this.networkId = networkId;
	}

	public String getIface() {
		return iface;
	}

	public void setIface(String iface) {
		this.iface = iface;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	public String getCidr() {
		return cidr;
	}

	public void setCidr(String cidr) {
		this.cidr = cidr;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public boolean isAutostart() {
		return autostart;
	}

	public void setAutostart(boolean autostart) {
		this.autostart = autostart;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean isBridge_vlan_aware() {
		return bridge_vlan_aware;
	}

	public void setBridge_vlan_aware(boolean bridge_vlan_aware) {
		this.bridge_vlan_aware = bridge_vlan_aware;
	}

	
	

}
