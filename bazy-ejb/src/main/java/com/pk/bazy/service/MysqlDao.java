package com.pk.bazy.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

import com.pk.bazy.model.Follower;
import com.pk.bazy.model.Friend;
import com.pk.bazy.model.Tweet;
import com.pk.bazy.model.User;

public class MysqlDao implements Dao {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/zbd";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "";

	public MysqlDao() {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException ex) {
			System.out.println("Error: unable to load driver class!");
			System.exit(1);
		}
	}

	private int getRowsFetch(ResultSet rs) {
		int size = 0;
		try {
			rs.last();
			size = rs.getRow();
			rs.beforeFirst();
		} catch (Exception ex) {
			return 0;
		}
		return size;
	}

	private long getDuration(long start, long end) {
		return end - start;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}

	public void log(String sql, long time, int fetchRows) throws SQLException {
		String sqlLog = "INSERT INTO logs (select_stmt, duration, rows_fetch) " + "VALUES (\"" + sql + "\", " + time
				+ ", " + fetchRows + ");";
		Logger.getLogger(Dao.class.getName()).log(Level.INFO, sqlLog);
		getConnection().prepareStatement(sqlLog).execute();
	}

	public ResultSet executeQuery(String sql, boolean log) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement(sql);
		System.out.println(sql);
		long start = System.currentTimeMillis();
		ResultSet rs = ps.executeQuery();
		long end = System.currentTimeMillis();
		if (log)
			log(sql, getDuration(start, end), getRowsFetch(rs));
		return rs;
	}

	public void execute(String sql, boolean log) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement(sql);
		Logger.getLogger(Dao.class.getName()).log(Level.INFO, sql);
		long start = System.currentTimeMillis();
		ps.execute();
		long end = System.currentTimeMillis();
		if (log)
			log(sql, getDuration(start, end), 0);
	}

	@Override
	public User login(String login, String password) {
		User user = new User();
		try {
			ResultSet rs = executeQuery("SELECT username, password FROM users where username LIKE '" + login
					+ "' and password LIKE '" + password + "';", true);
			while (rs.next()) {
				user = new User(rs.getString("username"), rs.getString("password"));
			}
		} catch (SQLException ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}
		return user;
	}

	@Override
	public List<User> getUsers() {
		List<User> users = new ArrayList<>();
		try {
			ResultSet rs = executeQuery("SELECT username, password FROM users;", true);
			while (rs.next()) {
				users.add(new User(rs.getString("username"), rs.getString("password")));
			}
		} catch (SQLException ex) {
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
		return "MySQL";
	}

	@Override
	public List<Tweet> getTweets(User user) {
		List<Tweet> tweets = new ArrayList<>();
		try {
			ResultSet rs = executeQuery(
					"SELECT tweet_id, username, body FROM tweets WHERE username LIKE '" + user.getUsername() + "';",
					true);
			while (rs.next()) {
				tweets.add(new Tweet(rs.getInt("tweet_id"), rs.getString("username"), rs.getString("body")));
			}
		} catch (SQLException ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}
		return tweets;
	}

	@Override
	public List<Follower> getFollowers(User user) {
		List<Follower> followers = new ArrayList<>();
		try {
			ResultSet rs = executeQuery(
					"SELECT follower, since FROM followers where username LIKE '" + user.getUsername() + "';", true);
			while (rs.next()) {
				followers.add(new Follower(rs.getString("follower"), rs.getTimestamp("since")));
			}
		} catch (SQLException ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}
		return followers;
	}

	@Override
	public List<Follower> getFollowed(User user) {
		List<Follower> followers = new ArrayList<>();
		try {
			ResultSet rs = executeQuery(
					"SELECT username, since FROM followers where follower LIKE '" + user.getUsername() + "';", true);
			while (rs.next()) {
				followers.add(new Follower(rs.getString("username"), rs.getTimestamp("since")));
			}
		} catch (SQLException ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}
		return followers;
	}

	@Override
	public List<Friend> getFreinds(User user) {
		List<Friend> friends = new ArrayList<>();
		try {
			ResultSet rs = executeQuery(
					"SELECT username, since FROM friends where username LIKE '" + user.getUsername() + "';", true);
			while (rs.next()) {
				friends.add(new Friend(rs.getString("username"), rs.getTimestamp("since")));
			}
		} catch (SQLException ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}
		return friends;
	}

	@Override
	public int getNumberOfTweets(User user) {
		int tweets = 0;
		try {
			ResultSet rs = executeQuery(
					"SELECT count(tweet_id) FROM tweets WHERE username LIKE '" + user.getUsername() + "';", true);
			rs.next();
			tweets = rs.getInt(1);
		} catch (SQLException ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}
		return tweets;
	}

	@Override
	public int getNumberOfFollowers(User user) {
		int followers = 0;
		try {
			ResultSet rs = executeQuery(
					"SELECT count(username) FROM followers WHERE username LIKE '" + user.getUsername() + "';", true);
			rs.next();
			followers = rs.getInt(1);
		} catch (SQLException ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}
		return followers;
	}

	@Override
	public int getNumberOfFollowed(User user) {
		int followed = 0;
		try {
			ResultSet rs = executeQuery(
					"SELECT count(username) FROM followers WHERE follower LIKE '" + user.getUsername() + "';", true);
			rs.next();
			followed = rs.getInt(1);
		} catch (SQLException ex) {
			Logger.getLogger(Dao.class.getName()).log(Level.INFO, null, ex);
		}
		return followed;
	}

}
