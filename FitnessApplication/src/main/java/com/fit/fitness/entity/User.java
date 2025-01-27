package com.fit.fitness.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "IN_USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	protected Long id;

	@Column(name = "NAME", length = 50)
	private String name;

	@Column(name = "EMAIL", length = 50)
	private String email;

	@Column(name = "PASSWORD", length = 500)
	private String password;

	@Column(name = "ROLE_NAME", length = 50)
	private String roleName = null;

	@Column(name = "ROLE_ID")
	private Long roleId;

	@Column(name = "PHONE", length = 50)
	private String phone;

	@Column(name = "DOB")
	private String dob;

	@Column(name = "GENDER", length = 10)
	private String gender;

	public User() {

	}

	public User(Long id, String name, String email, String password, String roleName, Long roleId, String phone,
			String dob, String gender) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.roleName = roleName;
		this.roleId = roleId;
		this.phone = phone;
		this.dob = dob;
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
