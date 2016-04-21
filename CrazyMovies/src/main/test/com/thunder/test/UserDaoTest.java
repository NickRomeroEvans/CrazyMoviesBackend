package com.thunder.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.thunder.controller.User;
import com.thunder.dao.IUserDao;
import com.thunder.dao.UserDaoImpl;

public class UserDaoTest {
	private IUserDao userDao;
	@Before
	public void setUp() throws Exception {
		userDao = new UserDaoImpl();
	}

	// add
	// getUser
	// getFriends
	// editUser
	// deleteUser
	
	@Test
	public void getUserTest() {
		User u = userDao.getUser(3);
		assertEquals("Fail E.", u.getName());
		assertEquals("test2@example.com", u.getEmail());
		assertEquals(3, u.getId());
	}
	
	@Test
	public void addUserTest(){
		userDao.addUser("simple", "simple@example.com");
		User u = userDao.getUser(4);
		assertEquals("simple", u.getName());
		assertEquals("simple@example.com", u.getEmail());
		assertEquals(4, u.getId());
		userDao.deleteUser(u.getId());
		u = userDao.getUser(4);
		assertEquals(null, u);
	}
	
	@Test
	public void editUserTest(){
		userDao.addUser("simple", "simple@example.com");
		User u = userDao.getUser(5);
		
		assertEquals("simple", u.getName());
		assertEquals("simple@example.com", u.getEmail());
		assertEquals(5, u.getId());
		
		u.setEmail("newEmail@example.com");
		u.setName("newName");
		
		userDao.editUser(u);
		
		assertEquals("newName", u.getName());
		assertEquals("newEmail@example.com", u.getEmail());
		assertEquals(5, u.getId());
		
		userDao.deleteUser(u.getId());
		u = userDao.getUser(5);
		assertEquals(null, u);
	}

}
