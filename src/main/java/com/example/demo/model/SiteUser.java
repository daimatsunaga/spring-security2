//package com.example.demo.model;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Size;
//
//@Entity
//public class SiteUser {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	
//	@Size(min = 2, max = 20)
//	private String username;
//	
//	@Size(min = 4, max = 255)
//	private String password;
//	
//	@NotBlank
//	@Email
//	private String email;
//	
//	
//	private int gender;
//	private boolean admin;
//	private String role;
//	private boolean active = true;
//	
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getUsername() {
//		return username;
//	}
//	public void setUsername(String username) {
//		this.username = username;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	public int getGender() {
//		return gender;
//	}
//	public void setGender(int gender) {
//		this.gender = gender;
//	}
//	public boolean isAdmin() {
//		return admin;
//	}
//	public void setAdmin(boolean admin) {
//		this.admin = admin;
//	}
//	public String getRole() {
//		return role;
//	}
//	public void setRole(String role) {
//		this.role = role;
//	}
//	public boolean isActive() {
//		return active;
//	}
//	public void setActive(boolean active) {
//		this.active = active;
//	}
//	@Override
//	public String toString() {
//		return "SiteUser [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
//				+ ", gender=" + gender + ", admin=" + admin + ", role=" + role + ", active=" + active + "]";
//	}
//	
//	
//
//}
