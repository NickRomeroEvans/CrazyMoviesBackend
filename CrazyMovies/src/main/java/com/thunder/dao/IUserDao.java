package com.thunder.dao;

import java.util.List;

import com.thunder.controller.User;

public interface IUserDao {
	public void addUser(User u);
	
	public User getUser(int id);
	
	public User getUser(String name, String email);
	
	public List<User> getFriends(int id);
	
	public void editUser(User u);
	
	public void deleteUser(int id);
}
