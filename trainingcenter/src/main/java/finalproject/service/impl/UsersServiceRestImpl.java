package finalproject.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.UriBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import finalproject.dao.UsersDAO;
import finalproject.domain.Course;
import finalproject.domain.Role;
import finalproject.domain.User;
import finalproject.service.SecurityService;
import finalproject.service.UsersService;

@Service("usersService")
public class UsersServiceRestImpl implements UsersService {
	@Autowired
	private UsersDAO usersRepository;
	
	@Autowired
	private SecurityService securityService;
	
	public static final Logger log = LogManager.getLogger("myLogger");
	

	public boolean getUserForNameAndPassword(String name, String password, HttpSession session) {
		
		
		RestTemplate restTemplate = new RestTemplate();
		System.out.println("http://localhost:8080/finalproject/rest/" + name + "/" + password);
		User user = restTemplate.getForObject("http://localhost:8080/finalproject/rest/users/" + name + "/" + password, User.class);
		
		//User user = usersRepository.getUserForNameAndPassword(name, password);
		if (user != null) {
			System.out.println(user.getName());
			session.setAttribute("user", user);
			log.info(user.getName() + " logged in");
			return true;
		}
		return false;
	}
	
	public User getUserForName(String name) {
		
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(link + "/rest/users/3", User.class);
	}
	
	 private static URI getBaseURI() {
		    return UriBuilder.fromUri("http://localhost:8080/finalproject.rest").build();
		  }
	

}
