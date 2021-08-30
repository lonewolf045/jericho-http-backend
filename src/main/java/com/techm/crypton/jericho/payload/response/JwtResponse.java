package com.techm.crypton.jericho.payload.response;

import java.util.UUID;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private UUID id;
	private String username;
	public JwtResponse(String token, UUID id, String username) {
		this.token = token;
		this.id = id;
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}	
}
