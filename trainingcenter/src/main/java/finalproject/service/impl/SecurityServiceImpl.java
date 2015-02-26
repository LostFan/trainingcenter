package finalproject.service.impl;

import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import finalproject.domain.Course;
import finalproject.domain.CourseState;
import finalproject.domain.Role;
import finalproject.domain.User;
import finalproject.exception.NotAccessException;
import finalproject.service.SecurityService;


@Service("securityService")
public class SecurityServiceImpl implements SecurityService{
	
	static public final HashMap<String,AllowedSets> commands = new HashMap<String,AllowedSets>();
	
	
	public SecurityServiceImpl()
	{
		commands.put("subcribe", new checkSubcribeAllowed());
		commands.put("attend", new checkAttendAllowed());
		commands.put("evaluate", new checkEvaluateAllowed());
		commands.put("update", new checkUpdateAllowed());
		commands.put("detail", new checkDetailAllowed());
		commands.put("participants", new checkParticipantsAllowed());
		commands.put("approve", new checkApproveAllowed());
		commands.put("delete", new checkDeleteAllowed());
		commands.put("start", new checkStartAllowed());
		commands.put("finish", new checkFinishAllowed());
		commands.put("notify", new checkNotifyAllowed());
	}

	public User getCurrentUser() {
		   ServletRequestAttributes sra = (ServletRequestAttributes)
	                RequestContextHolder.getRequestAttributes();
	        return (User) sra.getRequest().getSession().getAttribute("user");
	}
	
	public String getCurrentURL() {
		   ServletRequestAttributes sra = (ServletRequestAttributes)
	                RequestContextHolder.getRequestAttributes();
	        return sra.getRequest().getRequestURL().toString();
	}
	
	public void checkAllowed(Course course, String method) {
		SecurityService.AllowedSets set = commands.get(method);
		set.checkAllowed(course);
		
	}

	
	private class checkUpdateAllowed implements AllowedSets {
		public void checkAllowed(Course course) {
			User user = getCurrentUser();
			if(!course.getCreater().equals(user) && 
					(course.getState() == CourseState.Draft || course.getState() == CourseState.Rejected))
				throw new NotAccessException("Course Not Found");
			if(!(course.getState() == CourseState.Draft || course.getState() == CourseState.Rejected)) 
					throw new NotAccessException("Action not allowed. Say again please.");
		}
		
	}
	
	private class checkDetailAllowed implements AllowedSets {
		public void checkAllowed(Course course) {
			User user = getCurrentUser();
			if(!course.getCreater().equals(user) && course.getState() == CourseState.Draft)
				throw new NotAccessException("Course Not Found");
			if(	course.getState() == CourseState.Proposal || course.getState() == CourseState.Rejected) 
				if(!(course.getCreater().equals(user) || user.getRole() == Role.DM || user.getRole() == Role.KM))
					throw new NotAccessException("Action not allowed. Say again please.");
		}
	}
	
	private class checkParticipantsAllowed implements AllowedSets {
		public void checkAllowed(Course course) {
			User user = getCurrentUser();
			if(course.getState() == CourseState.Draft)
				if(!course.getCreater().equals(user))
					throw new NotAccessException("Course Not Found");
			if(course.getState() == CourseState.Proposal || course.getState() == CourseState.Proposal) 
				if(!course.getCreater().equals(user) || user.getRole() == Role.KM || user.getRole() == Role.DM)
					throw new NotAccessException("Course Not Found");
		}
	}

	public void checkSubAtEvAllowed(Course course) {
		User user = getCurrentUser();
		if(course.getState() == CourseState.Proposal || course.getState() == CourseState.Draft
				|| course.getState() == CourseState.Rejected) 
			throw new NotAccessException("Course Not Found");
		if(user.getRole() == Role.LECTURER || user.getRole() == Role.KM || user.getRole() == Role.DM)
			throw new NotAccessException("Action not allowed. Say again please.");
	}
	
	private class checkSubcribeAllowed implements AllowedSets {
		public void checkAllowed(Course course) {
			checkSubAtEvAllowed(course);
			User user = getCurrentUser();
			if(course.getCoursesSubscribe().contains(user))
				throw new NotAccessException("Action not allowed. Say again please.");
			if(!(course.getState() == CourseState.New || course.getState() == CourseState.Open
					|| course.getState() == CourseState.Ready))
				throw new NotAccessException("Action not allowed. Say again please.");
		}
	}

	private class checkAttendAllowed implements AllowedSets {
		public void checkAllowed(Course course) {
			checkSubAtEvAllowed(course);
			User user = getCurrentUser();
			if(course.getCoursesAttend().contains(user) || !course.getCoursesSubscribe().contains(user))
				throw new NotAccessException("Action not allowed. Say again please.");
			if(!( course.getState() == CourseState.Open
					|| course.getState() == CourseState.Ready))
				throw new NotAccessException("Action not allowed. Say again please.");
		}
	}
	
	private class checkEvaluateAllowed implements AllowedSets {
		public void checkAllowed(Course course) {
			checkSubAtEvAllowed(course);
			User user = getCurrentUser();
			if(course.getCoursesEvaluate().containsKey(user) || course.getState() != CourseState.Finished
					|| !course.getCoursesAttend().contains(user))
				throw new NotAccessException("Action not allowed. Say again please.");
			
		}
	}
	
	private class checkApproveAllowed implements AllowedSets {
		public void checkAllowed(Course course) {
			User user = getCurrentUser();
			if(course.getState() == CourseState.Draft) 
				throw new NotAccessException("Course Not Found");
			
			if(course.getState() == CourseState.Proposal && user.getRole() == Role.STUDENT)
				throw new NotAccessException("Course Not Found");
			
			if(!(course.getState() == CourseState.Proposal || course.getState() == CourseState.Rejected)) 
				throw new NotAccessException("Action not allowed. Say again please.");
			
			if(course.getState() == CourseState.Proposal && user.getRole() == Role.LECTURER)
				throw new NotAccessException("Action not allowed. Say again please.");
			
		}

	}
	private class checkDeleteAllowed implements AllowedSets {
		public void checkAllowed(Course course) {
			User user = getCurrentUser();
			if(!course.getCreater().equals(user) && course.getState() == CourseState.Draft)
				throw new NotAccessException("Course Not Found");
			if(!course.getCreater().equals(user) && 
					(course.getState() == CourseState.Proposal || course.getState() == CourseState.Rejected) &&
					(user.getRole() == Role.STUDENT || user.getRole() == Role.LECTURER))
				throw new NotAccessException("Course Not Found");
			if(!(course.getState() == CourseState.Draft || course.getState() == CourseState.Rejected)) 
					throw new NotAccessException("Action not allowed. Say again please.");
			
		}
	}
	
	private class checkStartAllowed implements AllowedSets {
		public void checkAllowed(Course course) {
			User user = getCurrentUser();
			if(!course.getCreater().equals(user) || course.getState() != CourseState.Ready)
				throw new NotAccessException("Action not allowed. Say again please.");
			
		}
	}
	
	private class checkFinishAllowed implements AllowedSets {
		public void checkAllowed(Course course) {
			User user = getCurrentUser();
			if(!course.getCreater().equals(user) || course.getState() != CourseState.InProgress)
				throw new NotAccessException("Action not allowed. Say again please.");
			
		}
	}
	

	private class checkNotifyAllowed implements AllowedSets {
		public void checkAllowed(Course course) {
			User user = getCurrentUser();
			if(!course.getCreater().equals(user) || course.getState() != CourseState.Finished)
				throw new NotAccessException("Action not allowed. Say again please.");
			if(course.getCoursesEvaluate().size() == course.getCoursesAttend().size())
				throw new NotAccessException("Action not allowed. Say again please.");
			
		}
	}
}
