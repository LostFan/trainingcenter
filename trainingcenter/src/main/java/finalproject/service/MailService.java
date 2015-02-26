package finalproject.service;

import finalproject.domain.Course;
import finalproject.domain.User;


public interface MailService {
	

	public abstract void courseAnnouncement(Course course);
	
	public abstract void courseApprovalUpdate(User fromUser, Course course);
	
	public abstract void newCourseAdded(User fromUser, Course course);
	
	public abstract void courseRejected(User fromUser, Course course);
	
	public abstract void courseDeleted(Course course);
	
	public abstract void courseOpened(Course course);
	
	public abstract void courseReady(Course course);
	
	public abstract void courseStarted(Course course);
	
	public abstract void courseFinished(Course course);
	
	public abstract void courseNotify(Course course);

}
