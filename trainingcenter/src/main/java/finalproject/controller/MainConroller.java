package finalproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import finalproject.exception.NotAccessException;
import finalproject.service.CoursesService;
import finalproject.service.SecurityService;
import finalproject.service.UsersService;
@Controller
public class MainConroller {
	
	@Autowired
	protected UsersService usersService;
	
	@Autowired
	protected CoursesService coursesService;
	
	@Autowired
	protected SecurityService securityService;
	
	@ExceptionHandler(NotAccessException.class)
	 public ModelAndView notHaveaccess(NotAccessException ex) {
		ModelAndView mav = new ModelAndView("do_not_have_access");
		mav.addObject("errorMessage", ex.getMessage());
		return mav;
       }
	

}
