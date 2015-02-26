package finalproject.dao;

import java.util.List;

import finalproject.domain.Course;
import finalproject.domain.Role;
import finalproject.domain.User;

public interface UsersDAO {

	public abstract User getUserForId(int id);

	public abstract User getUserForNameAndPassword(String name,
			String password);
	
	public abstract User getUserForName(String name);
	
	public abstract User getUserForUniqueRole(Role role);
	

	
	public abstract List <User> getUserListForRole(Role role);
	
}