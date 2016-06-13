package com.pk.bazy.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.pk.bazy.model.Follower;
import com.pk.bazy.model.Friend;
import com.pk.bazy.model.User;
import com.pk.bazy.service.CassandraDao;
import com.pk.bazy.service.Dao;
import com.pk.bazy.service.MysqlDao;

@ManagedBean(name = "followersTweetsView")
@ViewScoped
public class FollowersTweetsView {
	
	@ManagedProperty("#{userSession}")
	private UserSession session;
	
	private List<Follower> followers;
	
	@PostConstruct
	public void init(){
		String userName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("user");
		if (userName == null || userName.isEmpty()) {
			followers = session.getDao().getFollowersWithTweets(session.getUser());
		} else {
			followers = session.getDao().getFollowersWithTweets(new User(userName));
		}
	}

	public UserSession getSession() {
		return session;
	}

	public void setSession(UserSession session) {
		this.session = session;
	}

	public List<Follower> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Follower> followers) {
		this.followers = followers;
	}

	
}
