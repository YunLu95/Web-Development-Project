package org.umsl.ylnf2.dao;

import java.util.List;

import org.umsl.ylnf2.entity.User;

public interface UserDAO {
	public User getUserById(int userId);
	public void updateUser(User u);
	public void deleteUser(User u);
	public User addUser(User u);
	public List<User> listUsers();
}
