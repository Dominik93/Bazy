package com.pk.bazy.service;

import java.util.List;

import javax.ejb.Local;

import com.pk.bazy.model.Follower;
import com.pk.bazy.model.Friend;
import com.pk.bazy.model.Tweet;
import com.pk.bazy.model.User;

public interface Dao {

	User login(String login, String password);
	List<User> getUsers();
	List<Tweet> getTweets(User user);
	List<Follower> getFollowers(User user);
	List<Friend> getFreinds(User user);
	
	void generateRandomData();
}
