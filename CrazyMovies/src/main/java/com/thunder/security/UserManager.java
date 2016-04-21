/**
 * 
 */
package com.thunder.security;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;

import com.thunder.controller.User;
import com.thunder.dao.IUserDao;

/**
 * @author chaos
 *
 */
public class UserManager {
	
	@Autowired
	private IUserDao userDao;
	
	public UserManager(){
	}
	
	private static final ThreadLocal<User> currentUser = new ThreadLocal<User>();
	
	public User validateUser(User user) throws AuthenticationException {
		User u = userDao.getUser(user.getName(), user.getEmail());
		if(u == null){
			throw new AuthenticationException();
		} 
		
		return u;
	}
	
	public void threadLocalTrack(User user) {
		currentUser.set(user);
	}
	
	public User getActiveUser() {
		return currentUser.get();
	}
}
