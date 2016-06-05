package com.pk.bazy.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.pk.bazy.model.User;
import com.pk.bazy.service.Dao;

@SessionScoped
@ManagedBean(name = "userSession")
public class UserSession {

	private User user;
	private Dao dao;

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
