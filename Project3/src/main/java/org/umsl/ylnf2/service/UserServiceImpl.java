package org.umsl.ylnf2.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umsl.ylnf2.dao.UserDAO;
import org.umsl.ylnf2.entity.User;

@Service
public class UserServiceImpl implements UserService{
	
	private UserDAO userDAO;

	@Override
	@Transactional
	public User getUserById(int userId) {
		return this.userDAO.getUserById(userId);
	}

	@Override
	@Transactional
	public void updateUser(User u) {
		u.setLastUpdated(new Date());
		this.userDAO.updateUser(u);
	}

	@Override
	@Transactional
	public void deleteUser(User u) {
		this.userDAO.deleteUser(u);
	}

	@Override
	@Transactional
	public User addUser(User u) {
		u.setLastUpdated(new Date());
		return this.userDAO.addUser(u);
	}

	@Override
	@Transactional
	public List<User> listUsers() {
		return this.userDAO.listUsers();
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

}
