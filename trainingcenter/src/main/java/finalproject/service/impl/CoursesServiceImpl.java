package finalproject.service.impl;

import java.lang.Thread.State;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalproject.dao.CoursesDAO;
import finalproject.dao.UsersDAO;
import finalproject.domain.Category;
import finalproject.domain.Course;
import finalproject.domain.CourseState;
import finalproject.domain.Decision;
import finalproject.domain.Role;
import finalproject.domain.User;
import finalproject.exception.NotAccessException;
import finalproject.form.ApproveForm;
import finalproject.form.CourseForm;
import finalproject.form.UpdateForm;
import finalproject.service.CoursesService;
import finalproject.service.MailService;
import finalproject.service.SecurityService;


@Service("coursesService")
public class CoursesServiceImpl implements CoursesService{

	@Autowired
	private CoursesDAO coursesDAO;
	
	@Autowired
	private UsersDAO usersDAO;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private SecurityService securityService;
	
	public static final Logger log = LogManager.getLogger("myLogger");
	
	static public final HashMap<String,UserSets> commands = new HashMap<String,UserSets>();
	
	public CoursesServiceImpl()
	{
		commands.put("subcribe", new AddSubcribeCourse());
		commands.put("attend", new AddAttendCourse());
	}
	
	private class AddSubcribeCourse implements UserSets {
		public void addCourse(Course course) {
			User user = securityService.getCurrentUser();
			course.getCoursesSubscribe().add(user);
			if(course.getMinimalSubscribers() == course.getCoursesSubscribe().size() &&
					course.getState() == CourseState.New) {
				course.setState(CourseState.Open);
				log.info(course.getName() + " (id="+ course.getId()+") has a state " + course.getState());
				mailService.courseOpened(course);
			}
			coursesDAO.update(course);
				
			log.info(user.getName() + " (id="+ user.getId()+") subscribe to course: " + course.getName()
					+ " (id="+ course.getId()+")");
		}
	}

	private class AddAttendCourse implements UserSets {
		public void addCourse(Course course) {
			User user = securityService.getCurrentUser();
			course.getCoursesSubscribe().remove(user);
			course.getCoursesAttend().add(user);
			if(course.getMinimalAttendee() == course.getCoursesAttend().size() &&
					course.getState() == CourseState.Open) {
				course.setState(CourseState.Ready);	
				log.info(course.getName() + " (id="+ course.getId()+") has a state " + course.getState());
				mailService.courseReady(course);
			}
			coursesDAO.update(course);
			log.info(user.getName() + " (id="+ user.getId()+") attend to course: " + course.getName()
					+ " (id="+ course.getId()+")");
		}
	}


	public void addCourse(Course course, String method) {
		CoursesService.UserSets addCourse = commands.get(method);
		addCourse.addCourse(course);
		
	}

	public void evaluateCourse(Course course, int grade) {
		User user = securityService.getCurrentUser();
		course.getCoursesEvaluate().put(user, grade);
		coursesDAO.update(course);	
		log.info(user.getName() + " (id="+ user.getId()+") evaluated course: " + course.getName()
				+ " (id="+ course.getId()+") of" + grade);
		
	}
	public List <Course> getAllCourses() {
		return coursesDAO.getAllCourses();
	}
	
	public void save(CourseForm courseForm)
	{
		User user = securityService.getCurrentUser();
		Category category = new Category();
		category.setId(courseForm.getCategoryId());
		Course newCourse = new Course(courseForm.getName(), category, 
				courseForm.getDescription(), courseForm.getLinks(), 1, 1, user, CourseState.Draft);
		coursesDAO.save(newCourse);
		log.info(user.getName() + " (id="+ user.getId()+") create course: " + newCourse.getName()
				+ " (id="+ newCourse.getId()+")");
		//usersDAO.updateCourseCreatorList(user, newCourse);
	}
	
	public void delete(int courseId)
	{
		Course course = getCourse(courseId);
		coursesDAO.delete(course);
		if(course.getState() == CourseState.Rejected)
			mailService.courseDeleted(course);
		User user = securityService.getCurrentUser();
		log.info(user.getName() + " (id="+ user.getId()+") delete course: " + course.getName()
				+ " (id="+ course.getId()+")");
		//usersDAO.updateCourseCreatorList(user, newCourse);
	}
	
	public void update(int courseId, UpdateForm updateForm){
		Course course = getCourse(courseId);
		course.setName(updateForm.getName());
		course.setLinks(updateForm.getLinks());	
		course.setDescription(updateForm.getDescription());
		course.setMinimalSubscribers(updateForm.getMinimalSubscribers());
		course.setMinimalAttendee(updateForm.getMinimalAttendee());
		Category category = new Category();
		category.setId(updateForm.getCategoryId());
		course.setCategory(category);	
		coursesDAO.update(course);
		User user = securityService.getCurrentUser();
		log.info(user.getName() + " (id="+ user.getId()+") update course: " + course.getName()
				+ " (id="+ course.getId()+")");
	}
	
	public Course getCourse(int id){
		return coursesDAO.getCourse(id);
	}

	public List<Category> getAllCategories() {
		return coursesDAO.getAllCategories();
	}
	
	public void start(Course course, CourseState state){
		course.setState(state);
		coursesDAO.update(course);
		log.info(course.getName() + " (id="+ course.getId()+") has a state " + course.getState());
		mailService.courseStarted(course);
	}
	
	public void finish(Course course, CourseState state){
		course.setState(state);
		coursesDAO.update(course);
		log.info(course.getName() + " (id="+ course.getId()+") has a state " + course.getState());
		mailService.courseFinished(course);
	}
	
	public void changeStateCourses(int courseId, CourseState state){
		Course course = getCourse(courseId);
		course.setState(state);
		if(state == CourseState.Proposal) {
			course.setDecisionDM(Decision.None);
			course.setReasonDM(null);
			course.setDecisionKM(Decision.None);
			course.setReasonKM(null);
		}
		coursesDAO.update(course);
		User user = securityService.getCurrentUser();
		mailService.courseAnnouncement(course);
		log.info(user.getName() + " (id="+ user.getId()+") change state of course: " + course.getName()
				+ " (id="+ course.getId()+") to" + course.getState());
	}

	public Course getCurrentCourse(String courseIdStr) {
		Course course = null;
		try{
			int currentID = Integer.parseInt(courseIdStr);
			course = getCourse(currentID);
			if (course == null) {
				throw new NotAccessException("Course Not Found");
			}
		}
		catch (NumberFormatException ex) {
			throw new NotAccessException("Bad ID Criteria");
		}
		return course;
	}
	
	public void approvalManagers(Course course, ApproveForm approveForm) {
		
		User user = securityService.getCurrentUser();
		if(user.getRole() == Role.DM) {
			course.setDecisionDM(approveForm.getDecision());
			course.setReasonDM(approveForm.getReason());
		}
		if(user.getRole() == Role.KM) {
			course.setDecisionKM(approveForm.getDecision());
			course.setReasonKM(approveForm.getReason());
		}		
		String decision =null;
		if(user.getRole() == Role.DM) 
			decision = course.getDecisionDM().toString().toLowerCase();
		else
			decision = course.getDecisionKM().toString().toLowerCase();
		
		log.info(user.getName() + " (id="+ user.getId()+") "+ decision
						+ " course: " + course.getName()	+ " (id="+ course.getId()+")");

		
		mailService.courseApprovalUpdate(user, course);
		
		if(course.getDecisionDM() == Decision.Approve && course.getDecisionKM() == Decision.Approve) {
			course.setState(CourseState.New);
			mailService.newCourseAdded(user, course);
			log.info(course.getName() + " (id="+ course.getId()+") has a state " + course.getState());
			
		}
		if(course.getDecisionDM() != Decision.None && course.getDecisionKM() != Decision.None)
			if(course.getDecisionDM() == Decision.Reject || course.getDecisionKM() == Decision.Reject) {
				course.setState(CourseState.Rejected);
				mailService.courseRejected(user, course);
				log.info(course.getName() + " (id="+ course.getId()+") has a state " + course.getState());
			}
		coursesDAO.update(course);
		
		
		
	}
	
	public List<Course> getMyCourses()
	{
		User currentUser = securityService.getCurrentUser();
		//currentUser = usersDAO.getUserForId(currentUser.getId());
		if(currentUser.getRole() == Role.LECTURER)			 
			return coursesDAO.getCoursesByCreater(currentUser);
		if(currentUser.getRole() == Role.STUDENT) {
			List <Course> allMyCourses = coursesDAO.getCoursesBySubscribler(currentUser);
			allMyCourses.addAll(coursesDAO. getCoursesByAttender(currentUser));
			return allMyCourses;
		}
			return null;
	}
}

