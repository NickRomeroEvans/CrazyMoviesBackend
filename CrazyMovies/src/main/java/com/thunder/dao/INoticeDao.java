package com.thunder.dao;

import java.util.List;

import com.thunder.controller.Notice;
import com.thunder.controller.User;

public interface INoticeDao {
	public void addNotice(Notice n);
	
	public void deleteNotice(Notice n);
	
	public void editNotice(Notice n);
	
	public List<Notice> getUserNotices(User u);
	
	public List<Notice> getUserAccepted(User u);
	
	public List<Notice> getUserRejected(User u);
}
