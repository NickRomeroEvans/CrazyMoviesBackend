package com.thunder.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.thunder.dao.IMovieDao;
import com.thunder.dao.INoticeDao;
import com.thunder.dao.MovieDaoImpl;
import com.thunder.dao.NoticeDaoImpl;
import com.thunder.security.UserManager;

@Controller
public class MovieController {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(MovieController.class);
	
	@Autowired
	private UserManager userManager;
	
	private IMovieDao movieDao;
	private INoticeDao noticeDao;
	
	public MovieController(){
		movieDao = new MovieDaoImpl();
		noticeDao = new NoticeDaoImpl();
	}
	
    public void setMovieDao(IMovieDao dao) {
    	this.movieDao = dao;
    }
    
    public void setNoticeDao(INoticeDao dao) {
    	this.noticeDao = dao;
    }

	@RequestMapping(value = "/api/addMovie", method = RequestMethod.POST, produces = "application/json" )
	public @ResponseBody Movie addUser(Movie movie){
		movieDao.addMovie(movie);
		return movieDao.getMovie(movie.getTitle());
	}
	
	@RequestMapping(value = "/api/getMovieById", method = RequestMethod.GET, produces = "application/json" )
	public @ResponseBody Movie getMovie(@RequestParam int id){
		return movieDao.getMovie(id);
	}
	
	@RequestMapping(value = "/api/getMovie", method = RequestMethod.GET, produces = "application/json" )
	public @ResponseBody Movie getMovie(@RequestParam String title){
		return movieDao.getMovie(title);
	}
	
	@RequestMapping(value = "/api/getAllMovies", method = RequestMethod.GET, produces = "application/json" )
	public @ResponseBody List<Movie> getAllMovies(){
		return movieDao.getAllMovies();
	}
	
	@RequestMapping(value = "/api/getNotices", method = RequestMethod.GET, produces = "application/json" )
	public @ResponseBody List<Notice> getNotices(){
		User u = userManager.getActiveUser();
		
		return noticeDao.getUserNotices(u);
	}
	
	@RequestMapping(value = "/api/getAcceptNotices", method = RequestMethod.GET, produces = "application/json" )
	public @ResponseBody List<Notice> getAcceptedNotices(){
		User u = userManager.getActiveUser();
		
		return noticeDao.getUserAccepted(u);
	}
	
	@RequestMapping(value = "/api/getRejectNotices", method = RequestMethod.GET, produces = "application/json" )
	public @ResponseBody  List<Notice> getRejectedNotices(){
		User u = userManager.getActiveUser();
		
		return noticeDao.getUserRejected(u);
	}
	
	@RequestMapping(value = "/api/addNotice", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void addNotice(Notice n){
		User u = userManager.getActiveUser();
		
		if(u.getId() == n.getUser_id()){
			noticeDao.addNotice(n);
		}

	}
	
	@RequestMapping(value = "/api/editNotice", method = RequestMethod.PUT, produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public void editNotice(Notice n){
		User u = userManager.getActiveUser();
		
		if(u.getId() == n.getUser_id()){
			noticeDao.editNotice(n);
		}
	}
	
	@RequestMapping(value = "/api/deleteNotice", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteNotice(Notice n){
		User u = userManager.getActiveUser();
		
		if(u.getId() == n.getUser_id()){
			noticeDao.deleteNotice(n);
		}
	}
	
	@RequestMapping(value = "/api/getQueueMovies", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Movie>  getQueue(){
		User u = userManager.getActiveUser();
		
		return movieDao.unNoticedMoives(u);
	}
}
