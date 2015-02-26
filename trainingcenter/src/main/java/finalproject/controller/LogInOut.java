package finalproject.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import finalproject.domain.User;
import finalproject.form.UserForm;
import finalproject.service.CoursesService;
import finalproject.service.UsersService;

@Controller
public class LogInOut extends MainConroller {

	public static final Logger log = LogManager.getLogger("myLogger");
		
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(ModelMap model, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Courses");
		if(securityService.getCurrentUser() != null)
			return "redirect:/courses";
		//model.addAttribute("allcourses", coursesService.getAllCourses());
		model.addAttribute("user", new UserForm());
        return "login";
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPage(HttpSession session, String name, String password, ModelMap model, 
    		@Valid @ModelAttribute("user") UserForm user, BindingResult result) {
		model.addAttribute("user", user);
		if (result.hasErrors()) {
			return "login";	
		}

		if (!usersService.getUserForNameAndPassword(name, password, session)){
			result.reject("wrongPassword", "Unknown user or invalid password.");
			return "login";
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage() {
        return "logout";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logoutPagePost(HttpSession session, HttpServletRequest req) {
		User user = (User) session.getAttribute("user");
		log.info(user.getName() + " log out.");
		session.removeAttribute("user");
        return "redirect:/login";
	}
}
