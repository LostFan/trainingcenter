package rest.dao;

import java.util.List;

import rest.domain.Course;
import rest.domain.Role;
import rest.domain.User;


public interface UsersDAO {

	public abstract User getUserForId(int id);

	public abstract User getUserForNameAndPassword(String name,
			String password);
	
	public abstract User getUserForName(String name);
	
	public abstract User getUserForUniqueRole(Role role);
	

	
	public abstract List <User> getUserListForRole(Role role);
	
}