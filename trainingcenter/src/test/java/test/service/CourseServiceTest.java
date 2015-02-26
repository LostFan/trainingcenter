package test.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import finalproject.dao.CoursesDAO;
import finalproject.dao.UsersDAO;
import finalproject.domain.Course;
import finalproject.domain.CourseState;
import finalproject.domain.Decision;
import finalproject.domain.Role;
import finalproject.domain.User;
import finalproject.form.ApproveForm;
import finalproject.form.CourseForm;
import finalproject.form.UpdateForm;
import finalproject.service.CoursesService;
import finalproject.service.MailService;
import finalproject.service.SecurityService;
import finalproject.service.impl.CoursesServiceImpl;



@RunWith(MockitoJUnitRunner.class)
public class CourseServiceTest {
	@Mock
	private CoursesDAO courseDao;

	@Mock
	private UsersDAO userDao;

	@Mock
	private SecurityService securityService;

	@Mock
	private MailService mailService;


	@InjectMocks
	private CoursesService courseService = new CoursesServiceImpl();
	
	private static User user;
	
	private static Course course = new Course();
	
	private static CourseForm courseForm = new CourseForm();
	
	private static UpdateForm updateForm = new UpdateForm();
	
	private static ApproveForm approveForm = new ApproveForm();
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		user = new User(3, "lecturer-a@tc.edu", "123", Role.LECTURER);
		course.setCoursesEvaluate(new HashMap <User, Integer>());
		course.setCoursesSubscribe(new HashSet <User>());
		course.setCoursesAttend(new HashSet <User>());
		course.setMinimalAttendee(2);
		course.setMinimalSubscribers(2);
		updateForm.setMinimalAttendee(2);
		updateForm.setMinimalSubscribers(2);
		

	}
	
	@Before
	public final void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public final void testEvaluateCourse() {
		when(securityService.getCurrentUser()).thenReturn(user);
		courseService.evaluateCourse(course, 5);
		verify(courseDao).update(course);
	}
	
	@Test
	public final void testAttendCourseCourseNotChangeStateNotMinAtt() {
		when(securityService.getCurrentUser()).thenReturn(user);
		course.setState(CourseState.Open);
		courseService.addCourse(course, "attend");
		verify(courseDao).update(course);
	}
	
	@Test
	public final void testAttendCourseCourseNotChangeStateNotStateOpen() {
		when(securityService.getCurrentUser()).thenReturn(user);
		course.setState(CourseState.Ready);
		User u = new User();
		course.getCoursesAttend().add(u);
		courseService.addCourse(course, "attend");
		verify(courseDao).update(course);
		course.getCoursesAttend().remove(u);
	}
	
	@Test
	public final void testAttendCourseCourseChangeState() {
		when(securityService.getCurrentUser()).thenReturn(user);
		course.setState(CourseState.Open);
		User u = new User();
		course.getCoursesAttend().add(u);
		courseService.addCourse(course, "attend");
		verify(mailService).courseReady(Matchers.any(Course.class));
		verify(courseDao).update(course);
		course.getCoursesAttend().remove(u);
	}
	
	@Test
	public final void testSubscribeCourseCourseNotChangeStateNotMinSub() {
		ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
		when(securityService.getCurrentUser()).thenReturn(user);
		course.setState(CourseState.New);
		courseService.addCourse(course, "subcribe");
		verify(courseDao).update(captor.capture());
		assertEquals("Course must be open", CourseState.New, captor.getValue().getState());
	}
	
	@Test
	public final void testSubscribeCourseCourseNotChangeStateNotStateNew() {
		ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
		when(securityService.getCurrentUser()).thenReturn(user);
		course.setState(CourseState.Open);
		User u = new User();
		course.getCoursesSubscribe().add(u);
		courseService.addCourse(course, "subcribe");
		verify(courseDao).update(captor.capture());
		course.getCoursesSubscribe().remove(u);
		assertEquals("Course must be open", CourseState.Open, captor.getValue().getState());
	}
	
	@Test
	public final void testSubscribeCourseCourseChangeState() {
		ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
		when(securityService.getCurrentUser()).thenReturn(user);
		course.setState(CourseState.New);
		User u = new User();
		course.getCoursesSubscribe().add(u);
		courseService.addCourse(course, "subcribe");
		verify(mailService).courseOpened(Matchers.any(Course.class));
		verify(courseDao).update(captor.capture());
		course.getCoursesSubscribe().remove(u);
		assertEquals("Course must be open", CourseState.Open, captor.getValue().getState());
	}
	
	
	@Test
	public final void testSaveCourse() {
		when(securityService.getCurrentUser()).thenReturn(user);
		courseService.save(courseForm);
		verify(courseDao).save(course);
	}
	
	@Test
	public final void testDeleteCourse() {
		when(courseService.getCourse(Matchers.any(Integer.class))).thenReturn(course);
		when(securityService.getCurrentUser()).thenReturn(user);
		course.setState(CourseState.Rejected);
		courseService.delete(course.getId());
		verify(courseDao).delete(Matchers.any(Course.class));
	}
	
	@Test
	public final void testUpdateCourse() {
		when(courseService.getCourse(Matchers.any(Integer.class))).thenReturn(course);
		when(securityService.getCurrentUser()).thenReturn(user);
		courseService.update(Matchers.any(Integer.class), updateForm);
		verify(courseDao).update(Matchers.any(Course.class));
	}
	
	@Test
	public final void testChangeStateCourses() {
		ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
		when(courseService.getCourse(Matchers.any(Integer.class))).thenReturn(course);
		when(securityService.getCurrentUser()).thenReturn(user);
		courseService.changeStateCourses(Matchers.any(Integer.class), CourseState.Proposal);
		verify(mailService).courseAnnouncement(Matchers.any(Course.class));
		verify(courseDao).update(captor.capture());
		assertEquals("Decision DM must be \"None\"", Decision.None, captor.getValue().getDecisionDM());
	}
	
	@Test
	public final void testApprovalManagersApprove() {
		approveForm.setDecision(Decision.Approve);
		ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
		Course newCourse = new Course();
		newCourse.setDecisionKM(Decision.Approve);
		when(courseService.getCourse(Matchers.any(Integer.class))).thenReturn(newCourse);
		when(securityService.getCurrentUser()).thenReturn(new User(1, "", "", Role.DM));
		courseService.approvalManagers(newCourse, approveForm);
		verify(courseDao).update(captor.capture());
		assertEquals("Course must be new", CourseState.New, captor.getValue().getState());
		
		
	}
	
	@Test
	public final void testApprovalManagersReject() {
		approveForm.setDecision(Decision.Reject);
		ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
		Course newCourse = new Course();
		newCourse.setDecisionKM(Decision.Approve);
		when(courseService.getCourse(Matchers.any(Integer.class))).thenReturn(newCourse);
		when(securityService.getCurrentUser()).thenReturn(new User(1, "", "", Role.DM));
		courseService.approvalManagers(newCourse, approveForm);
		verify(courseDao).update(captor.capture());
		assertEquals("Course must be rejected", CourseState.Rejected, captor.getValue().getState());
		
		
	}
	
	@Test
	public final void testGetMyCourses() {
		when(securityService.getCurrentUser()).thenReturn(user);
		List <Course> courses = courseService.getMyCourses();
		verify(courseDao).getCoursesByCreater(user);
		
	}
}
