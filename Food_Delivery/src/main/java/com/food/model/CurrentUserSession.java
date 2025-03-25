package com.food.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class CurrentUserSession {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer currSessionId;
	
	@NotNull(message = "Email is require")
	@Email
	private String email;	
	
	@NotNull(message = "Date time is require")
	private LocalDateTime loginDateTime;
	
	@NotNull(message = "Role is require")
	private String role;
	private Integer customerId;

	private String privateKey;

	public Integer getCurrSessionId() {
		return currSessionId;
	}

	public void setCurrSessionId(Integer currSessionId) {
		this.currSessionId = currSessionId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getLoginDateTime() {
		return loginDateTime;
	}

	public void setLoginDateTime(LocalDateTime loginDateTime) {
		this.loginDateTime = loginDateTime;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	

}
