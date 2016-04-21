package com.thunder.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.thunder.dao.ILoginDao;
import com.thunder.dao.IUserDao;
import com.thunder.dao.LoginDaoImpl;
import com.thunder.dao.UserDaoImpl;
import com.thunder.security.UserManager;

@Controller
public class UserController {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserManager userManager;
	  
	private ILoginDao loginDao;
	private IUserDao userDao;
	
	public UserController(){
		loginDao = new LoginDaoImpl();
		userDao = new UserDaoImpl();
	}
	
    public void setLoginDao(ILoginDao dao) {
    	this.loginDao = dao;
    }
    
    public void setUserDao(IUserDao dao) {
    	this.userDao = dao;
    }
	
	@RequestMapping(value = "/api/addUser", method = RequestMethod.POST)
	public @ResponseBody User addUser(@RequestParam("name") String name, @RequestParam("email") String email){
		User u = new User();
		u.setEmail(email);
		u.setName(name);
		u.setFacebookId(null);
		
		userDao.addUser(u);
		
		return userDao.getUser(name, email);
	}
	
	@RequestMapping(value = "/api/addFBUser", method = RequestMethod.POST)
	public @ResponseBody User addUser(User u){
		userDao.addUser(u);
		
		return userDao.getUser(u.getName(), u.getEmail());
	}
	
	@RequestMapping(value = "/api/getUser", method = RequestMethod.GET, produces = "application/json" )
	public @ResponseBody User getUser(@RequestParam("id") int id){
		return userDao.getUser(id);
	}
	
	@RequestMapping(value = "/api/getUserFB", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody User getUser(@RequestParam("name") String name, @RequestParam("email") String email){
		return userDao.getUser(name, email);
	}
	
	@RequestMapping(value = "/api/editUser", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void editUser(User u){
		u = userManager.getActiveUser();
		userDao.editUser(u);
	}
	
	@RequestMapping(value = "/api/addLogin", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void addLogin(User u, @RequestParam("password") String password){
		loginDao.addLogin(u, password);
	}
	
	@RequestMapping (value = "/api/login", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody User login(@RequestParam("name") String name, @RequestParam("password") String password){
		return loginDao.login(name, password);
	}
	
	@RequestMapping (value = "/api/loginFB", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody User login(){
		User u = userManager.getActiveUser();
		
		User retVal = userDao.getUser(u.getName(), u.getEmail());
		
		if( retVal == null){
			userDao.addUser(u);
			retVal = userDao.getUser(u.getName(), u.getEmail());
			
		}
		
		return retVal;
	}
	
	@RequestMapping (value = "/api/editLogin", method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody User editLogin(@RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("id") int id){
		loginDao.editLogin(name, password, id);
		
		return loginDao.login(name, password);
	}
	
	
}
