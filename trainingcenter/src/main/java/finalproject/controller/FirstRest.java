package finalproject.controller;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;




@Component
public class FirstRest {
	
    @GET
    @Path("/firstrest")
    public ModelAndView firstGet() {
		ModelAndView mav = new ModelAndView("logout");
		return mav;

    }

    @PUT
    public void firstPut() {
    }

}
