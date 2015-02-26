package finalproject.service.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.UriBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import finalproject.dao.UsersDAO;
import finalproject.domain.Course;
import finalproject.domain.Role;
import finalproject.domain.User;
import finalproject.service.SecurityService;
import finalproject.service.UsersService;

@Service("usersService")
public class UsersServiceImpl implements UsersService {
	@Autowired
	private UsersDAO usersRepository;
	
	@Autowired
	private SecurityService securityService;
	
	public static final Logger log = LogManager.getLogger("myLogger");

	public boolean getUserForNameAndPassword(String name, String password, HttpSession session) {
		RestTemplate restTemplate = new RestTemplate();
		User user1 = restTemplate.getForObject("http://localhost:8080/rest/rest/users/3", User.class);
		System.out.println(user1.getName());
		User user = usersRepository.getUserForNameAndPassword(name, password);
		if (user != null) {
			session.setAttribute("user", user);
			log.info(user.getName() + " logged in");
			return true;
		}
		return false;
	}
	
	public User getUserForName(String name) {
		
		return usersRepository.getUserForName(name);
	}
	
	 private static URI getBaseURI() {
		    return UriBuilder.fromUri("http://localhost:8080/finalprojecty.rest").build();
		  }
	

}
