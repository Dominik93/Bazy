package com.pk.bazy.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.pk.bazy.model.User;
import com.pk.bazy.service.CassandraDao;
import com.pk.bazy.service.Dao;
import com.pk.bazy.service.MysqlDao;

@ManagedBean(name = "usersView")
@ViewScoped
public class UsersView {
	
	@ManagedProperty("#{userSession}")
	private UserSession session;
	
	private List<User> users;
	
	@PostConstruct
	public void init(){
		users = session.getDao().getUsers();
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public UserSession getSession() {
		return session;
	}

	public void setSession(UserSession session) {
		this.session = session;
	}

	
	
	
	
}
