package rest.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rest.dao.CoursesDAO;
import rest.dao.UsersDAO;
import rest.domain.Category;
import rest.domain.Course;

@Component
@Path("/category")
@Produces(MediaType.APPLICATION_JSON)
public class CategoryRest {
	
	@Autowired
	private CoursesDAO coursesDAO;
	
	@Autowired
	private UsersDAO usersDAO;
			
	@Path("/{id}")
	@GET
    public Response getUserByNameAndPass(@PathParam("id") String id) {
		
		Category category = coursesDAO.getCategory(Integer.parseInt(id));
		System.out.println(category);
		return Response.status(Response.Status.OK).entity(category).build();

    }


}
