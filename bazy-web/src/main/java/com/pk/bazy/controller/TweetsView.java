package com.pk.bazy.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.pk.bazy.model.Tweet;
import com.pk.bazy.model.User;

@ViewScoped
@ManagedBean(name = "tweetsView")
public class TweetsView {

	
	@ManagedProperty("#{userSession}")
	private UserSession session;
	
	
	private List<Tweet> tweets;
	
	@PostConstruct
	public void init() {
		String user = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("user");
		if (user == null || user.isEmpty()) {
			tweets = session.getDao().getTweets(session.getUser());
		} else {
			tweets = session.getDao().getTweets(new User(user));
		}
		
	}

	public UserSession getSession() {
		return session;
	}

	public void setSession(UserSession session) {
		this.session = session;
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	
	
	
}
