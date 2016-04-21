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
import com.thunder.controller.User;
import com.thunder.persistence.DataSourceFactory;

public class MovieDaoImpl implements IMovieDao {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(MovieDaoImpl.class);
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
	public void addMovie(Movie m) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try{
			conn = getDataSource().getConnection();
			
			String addUserQuery = "INSERT INTO crazymovie.movies(movie_title)" + 
			 		"VALUES (?);"; 
			
			stmt = conn.prepareStatement(addUserQuery);
			
			stmt.setString(1, m.getTitle());
			
			boolean result = stmt.execute();
			if(result){
				logger.debug("add Movie Good");
			} else {
				 logger.debug("add Movie Failure");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("Problem adding Movie", e);
		} finally{
			try {
				closeConnections(conn, stmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn("Problem closing connection in adding Movie", e);
			}
		}


	}

	@Override
	public Movie getMovie(int id) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Movie movie = null;
		try{
			conn = getDataSource().getConnection();
			
			String addUserQuery = "SELECT * FROM crazymovie.movies WHERE crazymovie.movies.movie_id=?"; 
			
			stmt = conn.prepareStatement(addUserQuery);
			
			stmt.setInt(1, id);
			
			boolean result = stmt.execute();
			
			if(result){
				logger.debug("get Movie Good");
				resultSet = stmt.getResultSet();
				if(resultSet.next()){
					movie = new Movie();
					movie.setTitle(resultSet.getString("movie_title"));
					movie.setId(resultSet.getInt("movie_id"));
				}
			} else {
				 logger.debug("get Movie Failure");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("Problem get Movie ", e);
		} finally{
			try {
				closeConnections(conn, stmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn("Problem closing connection in get Movie by id", e);
			}
		}
		if(movie == null){
			movie = new Movie();
			movie.setId(0);
			movie.setTitle("");
		}
		return movie;
	}

	@Override
	public Movie getMovie(String title){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Movie movie = null;
		try{
			conn = getDataSource().getConnection();
			
			String addUserQuery = "SELECT * FROM crazymovie.movies WHERE crazymovie.movies.movie_title=?"; 
			
			stmt = conn.prepareStatement(addUserQuery);
			
			stmt.setString(1, title);
			
			boolean result = stmt.execute();
			
			if(result){
				logger.debug("get Movie Good");
				resultSet = stmt.getResultSet();
				if(resultSet.next()){
					movie = new Movie();
					movie.setTitle(resultSet.getString("movie_title"));
					movie.setId(resultSet.getInt("movie_id"));
				}
			} else {
				 logger.debug("get Movie Failure");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("Problem get Movie ", e);
		} finally{
			try {
				closeConnections(conn, stmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn("Problem closing connection in get Movie by id", e);
			}
		}
		
		return movie;
	}
	
	@Override
	public List<Movie> getAllMovies() {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		List<Movie> movieList = new ArrayList<Movie>();
		try {
			 conn = getDataSource().getConnection();
			
			String goodQuery = "SELECT * FROM crazymovie.movies";
			 stmt = conn.prepareStatement(goodQuery);
			 
			 //System.out.println("check imp find4");*/
			 //String query = "SELECT * from branches";
			 //stmt = conn.prepareStatement(query);
			 boolean result = stmt.execute();
			 
			 if(result){
				 resultSet = stmt.getResultSet();
				 Movie temp;
				 logger.debug(""+resultSet.getFetchSize());
				 
				 while(resultSet.next()){
					 temp = new Movie();
					 
					 temp.setId(resultSet.getInt("movie_id"));
					 temp.setTitle(resultSet.getString("movie_title"));

					 movieList.add(temp);
				 } 

			 }else {
				 logger.debug("Failure");
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("Problem finding movies ", e);
		} finally{
			try {
				closeConnections(conn, stmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn("Problem closing connection in finding movies", e);
			}
		}
		
		return movieList;
	}

	@Override
	public List<Movie> unNoticedMoives(User u) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		List<Movie> movieList = new ArrayList<Movie>();
		try {
			 conn = getDataSource().getConnection();

			String goodQuery = "SELECT * FROM crazymovie.movies where movie_id not in " + 
					"(select movie_id from crazymovie.notices  WHERE crazymovie.notices.user_id=?)";
			 
			stmt = conn.prepareStatement(goodQuery);

			stmt.setInt(1, u.getId());

			//System.out.println("check imp find4");*/
			//String query = "SELECT * from branches";
			//stmt = conn.prepareStatement(query);
			boolean result = stmt.execute();
			 
			 if(result){
				 resultSet = stmt.getResultSet();
				 Movie temp;
				 
				 while(resultSet.next()){
					 temp = new Movie();
					 
					 temp.setId(resultSet.getInt("movie_id"));
					 temp.setTitle(resultSet.getString("movie_title"));

					 movieList.add(temp);
				 } 

			 }else {
				 logger.debug("Failure");
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("Problem finding queue of movies ", e);
		} finally{
			try {
				closeConnections(conn, stmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.warn("Problem closing connection in queue of movies", e);
			}
		}
		
		return movieList;
	}

}
