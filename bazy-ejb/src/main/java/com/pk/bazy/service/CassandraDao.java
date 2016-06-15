package com.pk.bazy.service;

import java.util.ArrayList;
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
	private static String contactPoint1 = "192.168.56.101";
	private static String contactPoint2 = "192.168.56.102";
	private static String keyspace = "cassandra";

	private Cluster cluster;
	private Session session;

	public CassandraDao() {
		cluster = Cluster.builder().addContactPoints(contactPoint1, contactPoint2).build();
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
		List<User> users = new ArrayList<>();

		try {
			Statement stmt = new SimpleStatement("select username, password FROM users;");

			ResultSet results = session.execute(stmt);
			for (Row row : results) {
				users.add(new User(row.getString("username"), row.getString("password")));
			}
		} catch (Exception ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}

		return users;
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
		List<Tweet> tweets = new ArrayList<>();
		try {
			Statement stmt = new SimpleStatement(
					"select tweet_id, username, body FROM tweets where username = '" + user.getUsername() + "';");

			ResultSet results = session.execute(stmt);
			for (Row rs : results) {
				tweets.add(new Tweet(Math.abs(rs.getUUID("tweet_id").hashCode()), rs.getString("username"),
						rs.getString("body")));
			}
		} catch (Exception ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}

		return tweets;
	}

	@Override
	public List<Follower> getFollowers(User user) {
		List<Follower> followers = new ArrayList<>();
		try {
			Statement stmt = new SimpleStatement(
					"select follower, since FROM followers where username = '" + user.getUsername() + "';");

			ResultSet results = session.execute(stmt);
			for (Row row : results) {
				followers.add(new Follower(row.getString("follower"), row.getTimestamp("since")));
			}
		} catch (Exception ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}

		return followers;
	}

	@Override
	public List<Follower> getFollowed(User user) {
		List<Follower> followers = new ArrayList<>();
		try {
			Statement stmt = new SimpleStatement(
					"select username, since FROM followers where follower = '" + user.getUsername() + "';");

			ResultSet results = session.execute(stmt);
			for (Row row : results) {
				followers.add(new Follower(row.getString("username"), row.getTimestamp("since")));
			}
		} catch (Exception ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}

		return followers;
	}

	@Override
	public List<Friend> getFriends(User user) {
		List<Friend> friends = new ArrayList<>();
		try {
			Statement stmt = new SimpleStatement(
					"select username, since FROM friends where username = '" + user.getUsername() + "';");

			ResultSet results = session.execute(stmt);
			for (Row row : results) {
				friends.add(new Friend(row.getString("username"), row.getTimestamp("since")));
			}
		} catch (Exception ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}

		return friends;
	}

	@Override
	public int getNumberOfTweets(User user) {
		int tweets = 0;
		try {
			Statement stmt = new SimpleStatement(
					"select count(tweet_id) FROM tweets where username = '" + user.getUsername() + "';");

			ResultSet results = session.execute(stmt);
			for (Row row : results) {
				tweets = (int) row.getLong(0);
			}
		} catch (Exception ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}

		return tweets;
	}

	@Override
	public int getNumberOfFollowers(User user) {
		int followers = 0;
		try {
			Statement stmt = new SimpleStatement(
					"select count(username) FROM followers where username = '" + user.getUsername() + "';");

			ResultSet results = session.execute(stmt);
			for (Row row : results) {
				followers = (int) row.getLong(0);
			}
		} catch (Exception ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}

		return followers;
	}

	@Override
	public int getNumberOfFollowed(User user) {
		int followed = 0;
		try {
			Statement stmt = new SimpleStatement(
					"select count(username) FROM followers where follower = '" + user.getUsername() + "';");

			ResultSet results = session.execute(stmt);
			for (Row row : results) {
				followed = (int) row.getLong(0);
			}
		} catch (Exception ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}

		return followed;
	}

	@Override
	public int getNumberOfFriends(User user) {
		int followed = 0;
		try {
			Statement stmt = new SimpleStatement(
					"SELECT count(username) FROM friends WHERE username = '" + user.getUsername() + "';");
			ResultSet results = session.execute(stmt);
			for (Row row : results) {
				followed = (int) row.getLong(0);
			}
		} catch (Exception ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}
		return followed;
	}

	@Override
	public List<Friend> getFriendsWithTweets(User user) {
		List<Friend> friends = getFriends(user);

		for (Friend friend : friends) {
			friend.setTweets(getTweets(new User(friend.getUsername())));
		}

		return friends;
	}

	@Override
	public List<Follower> getFollowersWithTweets(User user) {
		List<Follower> followers = getFollowers(user);

		for (Follower follower : followers) {
			follower.setTweets(getTweets(new User(follower.getUsername())));
		}

		return followers;
	}

	@Override
	public void insertFriend(User user, User user2) {
		try {
			session.execute("INSERT INTO friends (username, friend, since) VALUES ('" + user.getUsername() + "', '"
					+ user2.getUsername() + "', dateof(now()))");
		} catch (Exception ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}
	}

	@Override
	public void insertFollower(User user, User user2) {
		try {
			session.execute("INSERT INTO followers (username, follower, since) VALUES ('" + user.getUsername() + "', '"
					+ user2.getUsername() + "', dateof(now()))");
		} catch (Exception ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}
	}

	@Override
	public void insertTweet(Tweet tweet) {
		try {
			session.execute("INSERT INTO tweets (tweet_id, username, body) VALUES (uuid(), '" + tweet.getUsername()
					+ "', '" + tweet.getBody() + "')");
		} catch (Exception ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}
	}

}
