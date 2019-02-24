package org.umsl.ylnf2.service;

import java.util.List;

import org.umsl.ylnf2.entity.User;

public interface UserService {
	public User getUserById(int userId);
	public void updateUser(User u);
	public void deleteUser(User u);
	public User addUser(User u);
	public List<User> listUsers();
}
