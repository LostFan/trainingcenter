package finalproject.service;

import java.util.HashMap;
import java.util.List;







import finalproject.domain.Category;
import finalproject.domain.Course;
import finalproject.domain.CourseState;
import finalproject.domain.User;
import finalproject.form.ApproveForm;
import finalproject.form.CourseForm;
import finalproject.form.UpdateForm;

public interface CoursesService {
	public abstract List <Course> getAllCourses();
	
	public abstract void save(CourseForm courseForm);
	
	public abstract void update(int id, UpdateForm updateForm);
	
	public abstract void delete(int id);
	
	public abstract Course getCourse(int id);
	
	public abstract Course getCurrentCourse(String courseIdStr);
	
	public abstract List<Category> getAllCategories();
	
	public abstract void changeStateCourses(int courseId, CourseState state);
	
	public abstract void approvalManagers(Course course, ApproveForm approveForm);
	
	public abstract List<Course>  getMyCourses();

	public abstract void evaluateCourse(Course course, int grade);

	public abstract void addCourse(Course course, String method);
	
	static HashMap<String,UserSets> commands = new HashMap<String,UserSets>();

	interface UserSets
	{
		void addCourse(Course course);
	}
	
	public abstract void start(Course course, CourseState state);
	
	public abstract void finish(Course course, CourseState state);
	
	
}
