package com.thunder.controller;

public class User {
	private int id;
	private String name;
	private String email;
	private Long facebookId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getFacebookId() {
		return facebookId;
	}
	public void setFacebookId(Long facebookId) {
		this.facebookId = facebookId;
	}
	
	
}
