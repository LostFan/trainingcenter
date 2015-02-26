package rest.rest;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import rest.dao.CoursesDAO;
import rest.dao.UsersDAO;
import rest.dao.impl.UsersDAOImpl;
import rest.domain.User;




@Component
@Path("/users/{id}")
@Produces(MediaType.APPLICATION_JSON)
public class FirstRest {
	
	@Autowired
	private CoursesDAO coursesDAO;
	
	@Autowired
	private UsersDAO usersDAO;
	

	@GET
    public Response getUserById(@PathParam("id") String id) {
		
		User user = (User) usersDAO.getUserForId(Integer.parseInt(id));
		System.out.println(user);
		return Response.status(Response.Status.OK).entity(user).build();
    	

    }

    @PUT
    public void firstPut() {
    }

}
