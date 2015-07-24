package com.auth.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.auth.dao.UserDao;
import com.auth.model.User;

@Repository("userDao")
@SuppressWarnings("unchecked")
public class UserDaoImpl implements UserDao{
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public User getUser(String id) {
		String hql = "from User u where u.id=:id";//参数莫名
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("id", id);
		return (User)query.uniqueResult();
	}
	
	public User findByUserName(String name) {
		String hql = "from User u where u.loginName=:id and u.delflag =:delflag";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("id", name);
		query.setInteger("delflag", 0);
		return (User)query.uniqueResult();
	}
	
	@Override
	public List<User> getAllUser() {
		String hql = "from User";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Override
	public void addUser(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public boolean delUser(String id) {
		String hql = "DELETE User u where u.id = :id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("id", id);
		return (query.executeUpdate() > 0);
	}
}
