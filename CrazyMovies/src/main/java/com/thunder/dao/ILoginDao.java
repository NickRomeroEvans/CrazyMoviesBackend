package com.thunder.dao;

import com.thunder.controller.User;

public interface ILoginDao {

	public void addLogin(User u, String password);
	
	public User login(String name, String password);
	
	public void editLogin(String name, String password, int userId);
	
	public boolean verifyLogin();
	
	public boolean removeLogin();
}
