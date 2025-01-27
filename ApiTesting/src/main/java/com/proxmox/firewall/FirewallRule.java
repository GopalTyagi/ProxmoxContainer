package com.proxmox.firewall;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "firewall_rules")
public class FirewallRule {

	@Id
	private Long vmid;
	private String node;
	private String action;
//	private String macro;
	@Enumerated(EnumType.STRING)
	private Architecture type;
	public String iface;
	public String source;
	public Integer enable ;
	public String proto;
	public String sport;
	public String dest;
	public String dport;
	public String comment;



	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public String getDport() {
		return dport;
	}

	public void setDport(String dport) {
		this.dport = dport;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getProto() {
		return proto;
	}

	public void setProto(String proto) {
		this.proto = proto;
	}

	public String getIface() {
		return iface;
	}

	public void setIface(String iface) {
		this.iface = iface;
	}

	public enum Architecture {
		in, out, forward;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getVmid() {
		return vmid;
	}

	public void setVmid(Long vmid) {
		this.vmid = vmid;
	}

	public Architecture getType() {
		return type;
	}

	public void setType(Architecture type) {
		this.type = type;
	}

	/*
	 * public String getMacro() { return macro; }
	 * 
	 * public void setMacro(String macro) { this.macro = macro; }
	 */
}