package com.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth.dao.UserDaoImpl;
import com.auth.model.User;
@Service("userService")
@Transactional(rollbackFor=Exception.class)
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDaoImpl userDao;

	@Override
	public User getUser(String id) {
		return userDao.getUser(id);
	}
	
	public User findByUserName(String username){
		return userDao.findByUserName(username);
	}
	
	@Override
	public List<User> getAllUser() {
		return userDao.getAllUser();
	}

	@Override
	public void addUser(User user) {
		userDao.addUser(user);
	}

	@Override
	public boolean delUser(String id) {
		return userDao.delUser(id);
	}
}
