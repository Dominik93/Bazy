package com.pk.bazy.service;

import java.util.List;

import javax.ejb.Local;

import com.pk.bazy.model.User;

public interface Dao {

	User login(String login, String password);
	List<User> getUsers();
	void generateRandomData();
}
