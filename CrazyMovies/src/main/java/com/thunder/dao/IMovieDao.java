package com.thunder.dao;

import java.util.List;

import com.thunder.controller.Movie;
import com.thunder.controller.User;

public interface IMovieDao {
	public void addMovie(Movie m);
	
	// Get movie by id
	public Movie getMovie(int id);
	
	public Movie getMovie(String title);
	
	public List<Movie> getAllMovies();
	
	public List<Movie> unNoticedMoives(User u);
}
