package finalproject.dao;

import java.util.List;

import finalproject.domain.Category;
import finalproject.domain.Course;
import finalproject.domain.CourseState;
import finalproject.domain.Decision;
import finalproject.domain.User;
import finalproject.form.CourseForm;

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