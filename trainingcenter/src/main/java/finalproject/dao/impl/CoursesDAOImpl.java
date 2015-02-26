package finalproject.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import finalproject.dao.CoursesDAO;
import finalproject.domain.Category;
import finalproject.domain.Course;
import finalproject.domain.User;

@Repository("courseRepository")
public class CoursesDAOImpl implements CoursesDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public List <Course> getCoursesByCreater(User creater){
		
		Session session = sessionFactory.openSession();
		return  session.createCriteria(Course.class).add(Restrictions.eq("creater", creater)).list();
	}
	
	public List <Course> getCoursesBySubscribler(User user){
		
		Session session = sessionFactory.openSession();
		return  session.createCriteria(Course.class).createAlias("coursesSubscribe", "cS").
				add(Restrictions.eq("cS.id", user.getId())).list();
	}
	
	public List <Course> getCoursesByAttender(User user){
		
		Session session = sessionFactory.openSession();
		return  session.createCriteria(Course.class).createAlias("coursesAttend", "cA").
				add(Restrictions.eq("cA.id", user.getId())).list();
	}
	
	public List<Course> getAllCourses() {	
		Session session = sessionFactory.openSession();
		List<Course> courses = session.createCriteria(Course.class).list();
		session.close();
		return courses;
	}
	
	public Course getCourse(int id){
		Session session = sessionFactory.openSession();
		Course course = (Course) session.get(Course.class, id);
		session.close();
		return course;
	}
	
	public Category getCategory(int id){
		Session session = sessionFactory.openSession();
		Category category = (Category) session.get(Category.class, id);
		session.close();
		return category;
	}
	
	public void save(Course course){
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(course);
		tx.commit();
		session.close();
	}
	

	public List<Category> getAllCategories() {
		Session session = sessionFactory.openSession();
		List<Category> categories = session.createCriteria(Category.class).list();
		session.close();
		return categories;
	}


	public void update(Course course) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(course);
		tx.commit();
		session.close();
		
	}
	
	public void delete(Course course) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(course);
		tx.commit();
		session.close();
		
	}

	
}
