package org.umsl.ylnf2.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.umsl.ylnf2.entity.User;

@Repository
public class UserDAOImpl implements UserDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public User getUserById(int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		User u = (User) session.load(User.class, new Integer(userId));
		logger.info("User load successfully. User details = " + u);
		return u;
	}

	@Override
	public void updateUser(User u) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(u);
		logger.info("User update successfully. User details = " + u);
	}

	@Override
	public void deleteUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		User u = (User) session.load(User.class, new Integer(user.getUsersID()));
		if(null != u) {
			session.delete(u);
		}
		logger.info("User deleted successfully. User details = " + u);
	}

	@Override
	public User addUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		User u = user;
		session.persist(u);
		session.flush();
		logger.info("User added successfully. User detail = " + u);
		return u;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> userList = session.createQuery("from User").list();
		logger.info("Users listed successfully.");
		return userList;
	}
}
