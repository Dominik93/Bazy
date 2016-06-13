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

@ManagedBean(name = "friendsView")
@ViewScoped
public class FriendsView {
	
	@ManagedProperty("#{userSession}")
	private UserSession session;
	
	private List<Friend> friends;
	
	@PostConstruct
	public void init(){
		String userName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("user");
		if (userName == null || userName.isEmpty()) {
			friends = session.getDao().getFriends(session.getUser());
		} else {
			friends = session.getDao().getFriends(new User(userName));
		}
	}

	public UserSession getSession() {
		return session;
	}

	public void setSession(UserSession session) {
		this.session = session;
	}

	public List<Friend> getFriends() {
		return friends;
	}

	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}

	
}
