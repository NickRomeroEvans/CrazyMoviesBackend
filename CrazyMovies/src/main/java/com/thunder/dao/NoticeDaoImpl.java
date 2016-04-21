package com.thunder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;

import com.thunder.controller.Movie;
import com.thunder.controller.Notice;
import com.thunder.controller.User;
import com.thunder.persistence.DataSourceFactory;

public class NoticeDaoImpl implements INoticeDao {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(NoticeDaoImpl.class);
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
	public void addNotice(Notice n) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			 conn = getDataSource().getConnection();
			 
			 String goodQuery = "INSERT INTO crazymovie.notices(user_id, movie_id, status) " + 
			 		" VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE status=? ";
			 stmt = conn.prepareStatement(goodQuery);
			 
			 stmt.setInt(1, n.getUser_id());
			 stmt.setInt(2, n.getMovie_id());
			 stmt.setString(3, n.getStatus());
			 stmt.setString(4, n.getStatus());
			 
			 boolean result = stmt.execute();
			 
			 if(result){
				 logger.debug("Success Add Notice");
			 }else {
				 logger.debug("Fail Add Notice");
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("Problem finding notice ", e);
		} finally{
			try {
				closeConnections(conn, stmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn("Problem closing connection in add notice", e);
			}
		}

	}

	@Override
	public void deleteNotice(Notice n) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editNotice(Notice n) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;

		try {
			 conn = getDataSource().getConnection();
			 
			 String goodQuery = "UPDATE crazymovie.notices "
			 		+ "SET crazymovie.notices.status=? "
			 		+ "WHERE crazymovie.notices.notice_id=? ";
			 
			 stmt = conn.prepareStatement(goodQuery);
			 
			 stmt.setString(1, n.getStatus());
			 //stmt.setInt(2, n.getId());
			 
			 boolean result = stmt.execute();
			 
			 if(result){
				 System.out.println("Edit notice success");
			 }else {
				 logger.debug("Failure edit notice ");
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			logger.warn("Problem edit notice ", e);
		} finally{
			try {
				closeConnections(conn, stmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn("Problem closing connection in edit notice ", e);
			}
		}
	}

	@Override
	public List<Notice> getUserNotices(User u) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		List<Notice> noticeList = new ArrayList<Notice>();
		try {
			 conn = getDataSource().getConnection();
			
			String goodQuery = "SELECT * FROM crazymovie.notices WHERE crazymovie.notices.user_id=?";
			 stmt = conn.prepareStatement(goodQuery);
			 
			 stmt.setInt(1, u.getId());
			 
			 boolean result = stmt.execute();
			 
			 if(result){
				 resultSet = stmt.getResultSet();
				 Notice temp;
				 logger.debug(""+resultSet.getFetchSize());
				 
				 while(resultSet.next()){
					 temp = new Notice();
					 
					 //temp.setId(resultSet.getInt("notice_id"));
					 temp.setMovie_id(resultSet.getInt("movie_id"));
					 temp.setStatus(resultSet.getString("status"));
					 temp.setUser_id(resultSet.getInt("user_id"));
					 temp.setMovie_title(resultSet.getString("movie_title"));
					 
					 noticeList.add(temp);
				 } 

			 }else {
				 logger.debug("Failure");
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("Problem finding notices ", e);
		} finally{
			try {
				closeConnections(conn, stmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn("Problem closing connection in finding notices for user", e);
			}
		}
		
		return noticeList;
	}

	@Override
	public List<Notice> getUserAccepted(User u) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		List<Notice> noticeList = new ArrayList<Notice>();
		try {
			 conn = getDataSource().getConnection();

			String goodQuery ="SELECT * FROM crazymovie.notices LEFT JOIN crazymovie.movies " + 
					" ON crazymovie.notices.movie_id=crazymovie.movies.movie_id " + 
					" WHERE crazymovie.notices.user_id=? " + 
					" AND crazymovie.notices.status=?";
			 stmt = conn.prepareStatement(goodQuery);
			 
			 stmt.setInt(1, u.getId());
			 stmt.setString(2, Notice.ACCEPTED);
			 
			 boolean result = stmt.execute();
			 
			 if(result){
				 resultSet = stmt.getResultSet();
				 Notice temp;
				 logger.debug("Got results for accepted");
				 
				 while(resultSet.next()){
					 temp = new Notice();
					 
					// temp.setId(resultSet.getInt("notice_id"));
					 temp.setMovie_id(resultSet.getInt("movie_id"));
					 temp.setStatus(resultSet.getString("status"));
					 temp.setUser_id(resultSet.getInt("user_id"));
					 temp.setMovie_title(resultSet.getString("movie_title"));
					 
					 noticeList.add(temp);
				 } 
				 
			 }else {
				 logger.debug("Failure");
			 }
		} catch (SQLException e) {
			 System.out.println(e);
			// TODO Auto-generated catch block
			logger.warn("Problem finding notices ", e);
		} finally{
			try {
				closeConnections(conn, stmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn("Problem closing connection in finding notices for user", e);
			}
		}
		
		return noticeList;

	}

	@Override
	public List<Notice> getUserRejected(User u) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		List<Notice> noticeList = new ArrayList<Notice>();
		try {
			 conn = getDataSource().getConnection();
			
			String goodQuery = "SELECT * FROM crazymovie.notices LEFT JOIN crazymovie.movies " + 
					" ON crazymovie.notices.movie_id=crazymovie.movies.movie_id " + 
					" WHERE crazymovie.notices.user_id=? " + 
					" AND crazymovie.notices.status=?";
			
			 stmt = conn.prepareStatement(goodQuery);
			 
			 stmt.setInt(1, u.getId());
			 stmt.setString(2, Notice.REJECTED);
			 //System.out.println("check imp find4");*/
			 //String query = "SELECT * from branches";
			 //stmt = conn.prepareStatement(query);
			 boolean result = stmt.execute();
			 
			 if(result){
				 resultSet = stmt.getResultSet();
				 Notice temp;
				 logger.debug(""+resultSet.getFetchSize());
				 
				 while(resultSet.next()){
					 temp = new Notice();
					 
					 //temp.setId(resultSet.getInt("notice_id"));
					 temp.setMovie_id(resultSet.getInt("movie_id"));
					 temp.setStatus(resultSet.getString("status"));
					 temp.setUser_id(resultSet.getInt("user_id"));
					 temp.setMovie_title(resultSet.getString("movie_title"));
					 
					 noticeList.add(temp);
				 } 

			 }else {
				 logger.debug("Failure");
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("Problem finding notices ", e);
		} finally{
			try {
				closeConnections(conn, stmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn("Problem closing connection in finding notices for user", e);
			}
		}
		
		return noticeList;

	}

}
