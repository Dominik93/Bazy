package com.pk.bazy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.pk.bazy.model.Tweet;
import com.pk.bazy.model.User;
import com.pk.bazy.service.CassandraDao;
import com.pk.bazy.service.Dao;
import com.pk.bazy.service.MysqlDao;

@ManagedBean(name = "profileView")
@ViewScoped
public class ProfileView {
		
	@ManagedProperty("#{userSession}")
	private UserSession session;
	
	private User user;
	private Tweet tweet;
	
	@PostConstruct
	public void init() {
		String userName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("user");
		if (userName == null || userName.isEmpty()) {
			this.user = session.getUser();
		} else {
			this.user = new User(userName);
		}
		tweet = new Tweet(this.user.getUsername());
	}
	
	public void becomeFriend() {
		session.getDao().insertFriend(session.getUser(), this.user);
	}
	
	public void followUser() {
		session.getDao().insertFollower(session.getUser(), this.user);
	}
	
	public boolean myProfile() {
		return session.getUser().equals(this.user);
	}
	
	public void makeTweet() {
		session.getDao().insertTweet(tweet);
	}

	public void redirect(String page) {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(ec.getRequestContextPath() + "/" +  page +".xhtml?user=" + session.getUser().getUsername());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Tweet getTweet() {
		return tweet;
	}

	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}

	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public UserSession getSession() {
		return session;
	}

	public void setSession(UserSession session) {
		this.session = session;
	}
	
	
}
