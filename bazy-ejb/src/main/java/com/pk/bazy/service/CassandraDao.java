package com.pk.bazy.service;

import java.util.List;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.pk.bazy.model.Follower;
import com.pk.bazy.model.Friend;
import com.pk.bazy.model.Tweet;
import com.pk.bazy.model.User;

public class CassandraDao implements Dao {
	private static String contactPoint = "192.168.56.101";
	private static String keyspace = "cassandra";

	private Cluster cluster;// = Cluster.builder().addContactPoint(contactPoint).build();
	private Session session;// = cluster.connect(keyspace);

	public CassandraDao() {
		cluster = Cluster.builder().addContactPoint(contactPoint).build();
		session = cluster.connect(keyspace);
	}

	@Override
	public User login(String login, String password) {
		User user = new User();
		try {
			Statement stmt = new SimpleStatement(
					"select username, password FROM users where username = '" + login + "';");

			ResultSet results = session.execute(stmt);
			for (Row row : results) {
				String passwd = row.getString("password");

				if (password.equals(passwd)) {
					user = new User(row.getString("username"), row.getString("password"));
				}
			}
		} catch (Exception ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}
		return user;
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
