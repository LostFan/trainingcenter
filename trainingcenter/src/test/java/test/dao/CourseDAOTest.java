package test.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import finalproject.dao.CoursesDAO;
import finalproject.domain.Category;
import finalproject.domain.Course;
import finalproject.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-persistence-context.xml" })
public class CourseDAOTest {
//
	@Autowired
	private CoursesDAO courseDao;

	@Test
	public final void testGetAllCategories() {
		List<Category> categoryList = courseDao.getAllCategories();
		assertEquals("Number of categories should be " + 2, categoryList.size(), 3);
	}
	
	@Test
	public final void  testGetCoursesByCreater(){
		User user = new User();
		user.setId(3);
		List<Course> courseList = courseDao.getCoursesByCreater(user);
		assertEquals("Number of courses should be " + 8, courseList.size(), 8);
		
	}
	
	@Test
	public final void  testGetCoursesBySubscribler(){
		User user = new User();
		user.setId(5);
		List<Course> courseList = courseDao.getCoursesBySubscribler(user);
		assertEquals("Number of courses should be " + 1, courseList.size(), 1);
		
	}
	
	@Test
	public final void  testGetCoursesByAttender(){
		User user = new User();
		user.setId(6);
		List<Course> courseList = courseDao.getCoursesByAttender(user);
		assertEquals("Number of courses should be " + 3, courseList.size(), 3);
		
	}
	
	@Test
	public final void testGetAllCourses() {
		List<Course> courseList = courseDao.getAllCourses();
		assertEquals("Number of courses should be " + 8, courseList.size(), 8);
	}
	
	@Test
	public final void testGetCourseById() {
		Course course = courseDao.getCourse(1);
		assertNotNull("Course should not be null ", course);
	}
	
	@Test
	public final void testGetCourseByIdNegative() {
		Course course = courseDao.getCourse(20);
		assertNull("Course should be null ", course);
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
}
