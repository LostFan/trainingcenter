package finalproject.controller;



import javax.servlet.http.HttpServletRequest;




import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import finalproject.domain.Course;


@Controller
public class AllUsersController extends MainConroller {
	
	@RequestMapping(value = "/error/404")
    public String error() {
		 return "redirect:/courses";
	}
	
	@RequestMapping(value = "/courses", method = RequestMethod.GET)
    public String mainPageGet(ModelMap model, HttpServletRequest req, Integer category) {
		req.setAttribute("pageTitle", "Courses");
		model.addAttribute("allcourses", coursesService.getAllCourses());
		model.addAttribute("allCategories", coursesService.getAllCategories());
		if (category == null)
			model.addAttribute("category", 0);
		else
			model.addAttribute("category", category);
		model.addAttribute("page", "Courses");
        return "courses_table";
	}
	
	@RequestMapping(value = "/courses", method = RequestMethod.POST)
    public String mainPagePost(ModelMap model, Integer categoryId, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Courses");
		model.addAttribute("allcourses", coursesService.getAllCourses());
		model.addAttribute("allCategories", coursesService.getAllCategories());
		model.addAttribute("categoryId",categoryId);
		model.addAttribute("page","Courses");
        return "courses_table";
	}
	
	@RequestMapping(value = "/courses/{courseId}", method = RequestMethod.GET)
    public String detailGet(ModelMap model, @PathVariable("courseId") String courseIdStr, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Detail");
		Course course = coursesService.getCurrentCourse(courseIdStr);
		securityService.checkAllowed(course, "detail");
		model.addAttribute("currentcourse", course);	
		return "detail";
	}
	
	@RequestMapping(value = "/mycourses", method = RequestMethod.GET)
    public String mycoursesGet(ModelMap model, HttpServletRequest req, Integer category) {
		req.setAttribute("pageTitle", "My Courses");
		model.addAttribute("allcourses", coursesService.getMyCourses());
		model.addAttribute("allCategories", coursesService.getAllCategories());
		if (category == null)
			model.addAttribute("category", 0);
		else
			model.addAttribute("category", category);
		model.addAttribute("page","My courses");		
		return "courses_table";
	}
	
	@RequestMapping(value = "/mycourses", method = RequestMethod.POST)
    public String mycoursesPost(ModelMap model, Integer categoryId, HttpServletRequest req) {
		req.setAttribute("pageTitle", "My Courses");
		model.addAttribute("allcourses", coursesService.getMyCourses());
		model.addAttribute("allCategories", coursesService.getAllCategories());
		model.addAttribute("categoryId",categoryId);
		model.addAttribute("page","My courses");
        return "courses_table";
	}

}
