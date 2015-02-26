package test.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import finalproject.dao.UsersDAO;
import finalproject.domain.Role;
import finalproject.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-persistence-context.xml" })
public class UserDAOTest {
	
	@Autowired
	private UsersDAO userDAO;

	@Test
	public final void testGetUserForId() {
		User user = userDAO.getUserForId(1);
		assertNotNull("User should not be null", user);
	}
	
	@Test
	public final void testGetUserForIdNegative() {
		User user = userDAO.getUserForId(10);
		assertNull("User should be null", user);
	}
	
	@Test
	public final void testGetUserForNameAndPassword() {
		String name = "lecturer-b@tc.edu";
		String password = "abc";
		User user = userDAO.getUserForNameAndPassword(name, password);
		assertNotNull("User should not be null", user);
	}
	
	@Test
	public final void testGetUserForNameAndPasswordWrongName() {
		String name = "Qlecturer-d@tc.edu";
		String password = "abc";
		User user = userDAO.getUserForNameAndPassword(name, password);
		assertNull("User should be null", user);
	}
	
	@Test
	public final void testGetUserForNameAndPasswordWrongPassword() {
		String name = "lecturer-b@tc.edu";
		String password = "1234";
		User user = userDAO.getUserForNameAndPassword(name, password);
		assertNull("User should be null", user);
	}
	
	@Test
	public final void testGetUserForNameAndPasswordUpCaseName() {
		String name = "Lecturer-b@tc.edu";
		String password = "abc";
		User user = userDAO.getUserForNameAndPassword(name, password);
		assertNotNull("User should not be null", user);
	}
	
	@Test
	public final void testGetUserForNameAndPasswordUpCasePassword() {
		String name = "lecturer-b@tc.edu";
		String password = "Abc";
		User user = userDAO.getUserForNameAndPassword(name, password);
		assertNull("User should be null", user);
	}
	
	@Test
	public final void testGetUserForName() {
		String name = "lecturer-b@tc.edu";
		User user = userDAO.getUserForName(name);
		assertNotNull("User should not be null", user);
	}

	@Test
	public final void testGetUserForNameNegative() {
		String name = "lecturer-b";
		User user = userDAO.getUserForName(name);
		assertNull("User should be null", user);
	}
	
	@Test
	public final void testGetUserForUniqueRole() {
		User user = userDAO.getUserForUniqueRole(Role.DM);
		assertNotNull("User should not be null", user);
	}
	
	@Test
	public final void testGetUserListForRole() {
		List<User> users = userDAO.getUserListForRole(Role.STUDENT);
		assertEquals("Number of users should be" + 3 , users.size(), 3);
	}
}
