package com.thunder.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.thunder.controller.User;
import com.thunder.dao.ILoginDao;
import com.thunder.dao.LoginDaoImpl;

public class LoginDaoTest {
	private ILoginDao loginDao;
	
	@Before
	public void setUp() throws Exception {
		loginDao = new LoginDaoImpl();
	}

	@Test
	public void addLoginTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void loginTest(){
		User u = loginDao.login("John E.", "simple1");
		assertEquals("John E.", u.getName());
		assertEquals(1, u.getId());
	}

	@Test
	public void editLoginTest(){
		System.out.println("Edit Login Test");
		
		User u = loginDao.login("name", "pass");
		
		assertEquals(null, u);
		
		u = loginDao.login("John E.", "simple1");
		assertEquals("John E.", u.getName());
		assertEquals(1, u.getId());
		
		loginDao.editLogin("name", "pass", 1);
		
		
		u = loginDao.login("John E.", "simple1");
		
		assertEquals(null, u);
		
		u = loginDao.login("name", "pass");
		assertEquals("name", u.getName());
		assertEquals(1, u.getId());
		
		loginDao.editLogin("John E.", "simple1", 1);
		
		u = loginDao.login("John E.", "simple1");
		assertEquals("John E.", u.getName());
		assertEquals(1, u.getId());
		
	}
	
	@Test
	public void verifyLoginTest(){
		fail("Not yet implemented");
	}
	
	@Test
	public void removeLoginTest(){
		fail("Not yet implemented");
	}
}
