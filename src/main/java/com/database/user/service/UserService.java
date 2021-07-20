package com.database.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.database.user.entity.User;
import com.database.user.repository.UserRepository;


@Service
public class UserService {
	
	
	
	@Autowired
	private UserRepository userrepo;
	
	public User saveUser(User user) {
		return userrepo.save(user);
	}

}
