package com.pk.bazy.service;

import java.util.List;

import com.pk.bazy.model.Follower;
import com.pk.bazy.model.Friend;
import com.pk.bazy.model.Tweet;
import com.pk.bazy.model.User;

public class CassandraDao implements Dao {

	@Override
	public User login(String login, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generateRandomData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		return "Cassandra";
	}

	@Override
	public List<Tweet> getTweets(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Follower> getFollowers(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Friend> getFreinds(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfTweets(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfFollowers(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfFollowed(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Follower> getFollowed(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
