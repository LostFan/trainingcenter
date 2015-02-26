package rest.dao;

import java.util.List;

import rest.domain.Category;
import rest.domain.Course;
import rest.domain.CourseState;
import rest.domain.Decision;
import rest.domain.User;


public interface CoursesDAO{

	public abstract List<Course> getAllCourses();
	
	public abstract List<Category> getAllCategories();

	public abstract Course getCourse(int id);
	
	public abstract Category getCategory(int id);

	public abstract void save(Course course);
	
	public abstract void update(Course course);
	
	public abstract void delete(Course course);
	
	public abstract List <Course> getCoursesByCreater(User creater);
	
	public abstract List <Course> getCoursesBySubscribler(User user);
	
	public abstract List <Course> getCoursesByAttender(User user);
}