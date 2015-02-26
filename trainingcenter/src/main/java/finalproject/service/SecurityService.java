package finalproject.service;

import java.util.HashMap;

import finalproject.domain.Course;
import finalproject.domain.User;

public interface SecurityService {

	public abstract User getCurrentUser();
	
	public abstract String getCurrentURL();
	
	static HashMap<String,AllowedSets> commands = new HashMap<String,AllowedSets>();
	
	public abstract void checkAllowed(Course course, String method);
	
	interface AllowedSets
	{
		void checkAllowed(Course course);
	}
	
}
