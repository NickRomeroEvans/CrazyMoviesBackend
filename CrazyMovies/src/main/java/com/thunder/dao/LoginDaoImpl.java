package com.thunder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;

import com.thunder.controller.Position;
import com.thunder.controller.User;
import com.thunder.persistence.DataSourceFactory;

public class LoginDaoImpl implements ILoginDao {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(LoginDaoImpl.class);
	private DataSource dataSource;
	
	protected DataSource getDataSource() {
		if(dataSource == null){
			dataSource = DataSourceFactory.getDataSource();
		}
		return dataSource;
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
	public void addLogin(User u, String password) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			 conn = getDataSource().getConnection();
			 
			 String goodQuery = "INSERT INTO crazymovie.login(user_id, password, user_name)" + 
					"VALUES (?, ?, ?);";
			 stmt = conn.prepareStatement(goodQuery);
			 
			 stmt.setInt(1, u.getId());
			 stmt.setString(2, password);
			 stmt.setString(3, u.getName());

			 boolean result = stmt.execute();
			 
			 if(result){
				 logger.debug("Success Add Login");
			 }else {
				 logger.debug("Fail Add Login");
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("Problem adding login ", e);
		} finally{
			try {
				closeConnections(conn, stmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn("Problem closing connection in add login", e);
			}
		}

	}

	@Override
	public User login(String name, String password) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		User retUser = null;

		try {
			 conn = getDataSource().getConnection();
			 
			 String goodQuery = "Select * from crazymovie.login LEFT JOIN crazymovie.users "
			 		+ " ON crazymovie.login.user_id=crazymovie.users.user_id "
			 		+ " WHERE crazymovie.login.password=? "
			 		+ " AND crazymovie.login.user_name=? ";
			 
			 stmt = conn.prepareStatement(goodQuery);
			 
			 stmt.setString(1, password);
			 stmt.setString(2, name);

			 boolean result = stmt.execute();
			 
			 if(result){
				 resultSet = stmt.getResultSet();
				 
				 System.out.println("Login");
				 
				 if(resultSet.next()){
					 retUser = new User();
					 
					 retUser.setId(resultSet.getInt("user_id"));
					 retUser.setName(resultSet.getString("user_name"));
					 retUser.setEmail(resultSet.getString("user_email"));
					 System.out.println(retUser.getName());
					 System.out.println(retUser.getId());
					 System.out.println(retUser.getEmail());
				 } 

			 }else {
				 logger.debug("Failure");
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			logger.warn("Problem finding branches ", e);
		} finally{
			try {
				closeConnections(conn, stmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn("Problem closing connection in finding branches", e);
			}
		}
		
		return retUser;
	}

	@Override
	public boolean verifyLogin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void editLogin(String name, String password, int userId) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		//User retUser = null;

		try {
			 conn = getDataSource().getConnection();
			 
			 String goodQuery = "UPDATE crazymovie.login "
			 		+ "SET crazymovie.login.password=?, crazymovie.login.user_name=? "
			 		+ "WHERE crazymovie.login.user_id=? ";
			 
			 stmt = conn.prepareStatement(goodQuery);
			 
			 stmt.setString(1, password);
			 stmt.setString(2, name);
			 stmt.setInt(3, userId);
			 
			 boolean result = stmt.execute();
			 
			 if(result){
				 System.out.println("Edit login success");
			 }else {
				 logger.debug("Failure");
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			logger.warn("Problem edit login ", e);
		} finally{
			try {
				closeConnections(conn, stmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn("Problem closing connection in edit login ", e);
			}
		}

	}

	@Override
	public boolean removeLogin() {
		// TODO Auto-generated method stub
		return false;
	}

}
