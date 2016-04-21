package com.thunder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;

import com.thunder.controller.User;
import com.thunder.persistence.DataSourceFactory;

public class UserDaoImpl implements IUserDao {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	private DataSource dataSource;
	
	protected DataSource getDataSource() {
		if(dataSource == null){
			dataSource = DataSourceFactory.getDataSource();
		}
		return dataSource;
	}
	
	public UserDaoImpl(){
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private void closeConnections(Connection conn, PreparedStatement stmt, ResultSet resultSet) throws SQLException{
		if(conn != null){
			conn.close();
		}
		
		if(stmt != null){
			stmt.close();
		}
		
		if(resultSet != null){
			resultSet.close();
		}
	}
	
	@Override
	public void addUser(User u) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try{
			conn = getDataSource().getConnection();
			
			String addUserQuery = "INSERT INTO crazymovie.users(user_name, user_email, facebook_id)" + 
			 		"VALUES (?, ?, ?);"; 
			
			stmt = conn.prepareStatement(addUserQuery);
			
			stmt.setString(1, u.getName());
			stmt.setString(2, u.getEmail());
			stmt.setLong(3, u.getFacebookId());
			
			boolean result = stmt.execute();
			if(result){
				logger.debug("add User Good");
			} else {
				 logger.debug("add User Failure");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("Problem adding User", e);
		} finally{
			try {
				closeConnections(conn, stmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn("Problem closing connection in adding user", e);
			}
		}

	}
	
	@Override
	public User getUser(int id) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		User retUser = null;
		try{
			conn = getDataSource().getConnection();
			
			String addUserQuery = "SELECT * FROM crazymovie.users WHERE crazymovie.users.user_id=?"; 
			
			stmt = conn.prepareStatement(addUserQuery);
			
			stmt.setInt(1, id);
			
			boolean result = stmt.execute();
			
			if(result){
				logger.debug("get User Good");
				resultSet = stmt.getResultSet();
				if(resultSet.next()){
					retUser = new User();
					retUser.setEmail(resultSet.getString("user_email"));
					retUser.setName(resultSet.getString("user_name"));
					retUser.setId(resultSet.getInt("user_id"));
					retUser.setFacebookId(resultSet.getLong("facebook_id"));
				}
			} else {
				 logger.debug("get User Failure");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("Problem get user ", e);
		} finally{
			try {
				closeConnections(conn, stmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn("Problem closing connection in get user by id", e);
			}
		}
		
		return retUser;
	}
	
	@Override
	public User getUser(String name, String email) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		User retUser = null;
		try{
			conn = getDataSource().getConnection();
			
			String addUserQuery = "SELECT * FROM crazymovie.users WHERE crazymovie.users.user_name=?"
					+ "AND crazymovie.users.user_email=?"; 
			
			stmt = conn.prepareStatement(addUserQuery);
			
			stmt.setString(1, name);
			stmt.setString(2, email);
			
			boolean result = stmt.execute();
			
			if(result){
				logger.debug("get User Good");
				resultSet = stmt.getResultSet();
				if(resultSet.next()){
					retUser = new User();
					retUser.setEmail(resultSet.getString("user_email"));
					retUser.setName(resultSet.getString("user_name"));
					retUser.setId(resultSet.getInt("user_id"));
					retUser.setFacebookId(resultSet.getLong("facebook_id"));
				}
			} else {
				 logger.debug("get User Failure");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("Problem get user ", e);
		} finally{
			try {
				closeConnections(conn, stmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn("Problem closing connection in get user by id", e);
			}
		}
		
		return retUser;
	}
	
	
	@Override
	public List<User> getFriends(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void editUser(User u) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try{
			conn = getDataSource().getConnection();
			
			String addUserQuery = "UPDATE crazymovie.users SET user_name=?, user_email=?"
					+ "WHERE user_id=?"; 
			
			stmt = conn.prepareStatement(addUserQuery);
			
			stmt.setString(1, u.getName());
			stmt.setString(2, u.getEmail());
			stmt.setInt(3, u.getId());
			
			boolean result = stmt.execute();
			if(result){
				logger.debug("edit User Good");
			} else {
				 logger.debug("edit User Failure");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("Problem editting User", e);
		} finally{
			try {
				closeConnections(conn, stmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn("Problem closing connection in editting user", e);
			}
		}

	}
	@Override
	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try{
			conn = getDataSource().getConnection();
			
			String addUserQuery = "DELETE FROM crazymovie.users WHERE user_id = ?"; 
			
			stmt = conn.prepareStatement(addUserQuery);
			
			stmt.setInt(1, id);
			
			boolean result = stmt.execute();
			if(result){
				logger.debug("delete User Good");
			} else {
				 logger.debug("delete User Failure");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("Problem adding User", e);
		} finally{
			try {
				closeConnections(conn, stmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn("Problem closing connection in delete user", e);
			}
		}
	}
	
}
