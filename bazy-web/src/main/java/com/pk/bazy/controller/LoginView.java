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

import com.pk.bazy.model.User;
import com.pk.bazy.service.CassandraDao;
import com.pk.bazy.service.Dao;
import com.pk.bazy.service.MysqlDao;

@ManagedBean(name = "loginView")
@ViewScoped
public class LoginView {
	
	private String login;
	private String password;
	private String database;
	private Map<String, String> databases;
	
	@ManagedProperty("#{userSession}")
	private UserSession session;
	
	@PostConstruct
	public void init() {
		Logger.getLogger("LoginView").log(Level.INFO, "init");
		databases = new HashMap<>();
		databases.put("Cass", "C");
		databases.put("Mysql", "S");
	}
	
	public void onChange() {
		Logger.getLogger("LoginView").log(Level.INFO, database.toString());
    }
	
	public void logIn() {
		Logger.getLogger("LoginView").log(Level.INFO, "logIn");
		if (database.equals("C"))
			session.setDao(new CassandraDao());
		else	
			session.setDao(new MysqlDao());
		User user = session.getDao().login(login, password);
		session.setUser(user);
		if (user.getUsername() != null) {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			try {
				ec.redirect(ec.getRequestContextPath() + "/profile.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public Map<String, String> getDatabases() {
		return databases;
	}

	public void setDatabases(Map<String, String> databases) {
		this.databases = databases;
	}

	public UserSession getSession() {
		return session;
	}

	public void setSession(UserSession session) {
		this.session = session;
	}
	
	
}
