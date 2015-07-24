package com.auth.service;

import java.util.List;
import com.auth.model.User;

public interface UserService {
	public User getUser(String id);
	public List<User> getAllUser();
	public void addUser(User user);
	public boolean delUser(String id);
}
