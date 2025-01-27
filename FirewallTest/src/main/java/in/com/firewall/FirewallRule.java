package in.com.firewall;

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
	private String macro;
	@Enumerated(EnumType.STRING)
	private Architecture type;
	
	public enum Architecture {
		in,out,forward;
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

	public String getMacro() {
		return macro;
	}

	public void setMacro(String macro) {
		this.macro = macro;
	}
}