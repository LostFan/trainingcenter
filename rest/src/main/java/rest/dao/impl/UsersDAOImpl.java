package rest.dao.impl;


import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import rest.dao.UsersDAO;
import rest.domain.Course;
import rest.domain.Role;
import rest.domain.User;

// â DAO
@Repository("userRepository")
public class UsersDAOImpl implements UsersDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public User getUserForId(int id){
		Session session = sessionFactory.openSession();
		return (User) session.get(User.class, id);
	}
	

	public User getUserForNameAndPassword(String name, String password){
		name = name.toLowerCase();
		Session session = sessionFactory.openSession();
		return (User) session.createCriteria(User.class).add(Restrictions.eq("name", name)).
				add(Restrictions.eq("password", password)).uniqueResult();
	}
	
	public User getUserForName(String name)
	{
		name = name.toLowerCase();
		Session session = sessionFactory.openSession();
		return (User) session.createCriteria(User.class).add(Restrictions.eq("name", name)).uniqueResult();
	}
	
	public User getUserForUniqueRole(Role role)
	{
		Session session = sessionFactory.openSession();
		return (User) session.createCriteria(User.class).add(Restrictions.eq("role", role)).uniqueResult();
	}
	
	public List <User> getUserListForRole(Role role)
	{
		Session session = sessionFactory.openSession();
		return session.createCriteria(User.class).add(Restrictions.eq("role", role)).list();
	}
	
	
}
