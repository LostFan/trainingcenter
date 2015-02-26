package finalproject.service;

import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpSession;

import finalproject.domain.Course;
import finalproject.domain.User;

public interface UsersService {
	
	public abstract boolean getUserForNameAndPassword(String name, String password, HttpSession session);
	
	public abstract User getUserForName(String name);
	
	
}
