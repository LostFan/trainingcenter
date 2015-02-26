package finalproject.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import finalproject.domain.Course;
import finalproject.form.CourseForm;
import finalproject.form.EvaluationForm;




@Controller
@RequestMapping(value = "/courses")
public class StudentController extends MainConroller {
		
	@RequestMapping(value = "/{courseId}/subscribe", method = RequestMethod.GET)
	public String subscribeGet(ModelMap model, @PathVariable("courseId") String courseIdStr, 
			HttpServletRequest req) {
		req.setAttribute("pageTitle", "Subcribe");
		model.addAttribute("course", new CourseForm());
		Course course = coursesService.getCurrentCourse(courseIdStr);
		securityService.checkAllowed(course, "subcribe");
		model.addAttribute("currentcourse", course);	
		return "subscribe";
		
	}
	
	
	@RequestMapping(value = "/{courseId}/subscribe", method = RequestMethod.POST)
    public String subscribePost(ModelMap model, @PathVariable("courseId") String courseIdStr,
    		 Course currentcourse, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Subcribe");
		
		Course course = coursesService.getCurrentCourse(courseIdStr);
		securityService.checkAllowed(course, "subcribe");
		coursesService.addCourse(course, "subcribe");
		return "redirect:/courses";

	}
	
	@RequestMapping(value = "/{courseId}/attend", method = RequestMethod.GET)
	public String attendGet(ModelMap model, @PathVariable("courseId") String courseIdStr, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Attend");
		model.addAttribute("course", new CourseForm());
		Course course = coursesService.getCurrentCourse(courseIdStr);
		securityService.checkAllowed(course, "attend");
		model.addAttribute("currentcourse", course);	
		return "attend";
		
	}
	
	
	@RequestMapping(value = "/{courseId}/attend", method = RequestMethod.POST)
	public String attendPost(ModelMap model, @PathVariable("courseId") String courseIdStr, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Attend");
		Course course = coursesService.getCurrentCourse(courseIdStr);
		securityService.checkAllowed(course, "attend");
		coursesService.addCourse(course, "attend");
		return "redirect:/courses";

	}
	
	@RequestMapping(value = "/{courseId}/evaluate", method = RequestMethod.GET)
	public String evaluateGet(ModelMap model, @PathVariable("courseId") String courseIdStr, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Evaluate");
		model.addAttribute("evaluation", new EvaluationForm());
		Course course = coursesService.getCurrentCourse(courseIdStr);
		securityService.checkAllowed(course, "evaluate");
		model.addAttribute("currentcourse", course);
		return "evaluate";		
	}

	@RequestMapping(value = "/{courseId}/evaluate", method = RequestMethod.POST)
	public String evaluatePost(ModelMap model, @PathVariable("courseId") String courseIdStr, String grade,
			@Valid @ModelAttribute("evaluation") EvaluationForm evaluation, BindingResult result, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Evaluate");
		Course course = coursesService.getCurrentCourse(courseIdStr);
		securityService.checkAllowed(course, "evaluate");
		model.addAttribute("currentcourse", course);
		if (result.hasErrors()) {
			return "evaluate";	
		}
		int gradeInt = Integer.parseInt(grade);
		coursesService.evaluateCourse(course, gradeInt);
		return "redirect:/courses";
	}
	
	
	
	@RequestMapping(value = "/{courseId}/participants", method = RequestMethod.GET)
    public String participantsGet(ModelMap model, HttpServletRequest req, @PathVariable("courseId") String courseIdStr) {
		req.setAttribute("pageTitle", "Participants");
		Course course = coursesService.getCurrentCourse(courseIdStr);
		securityService.checkAllowed(course, "participants");
		//Course course = getCurrentCourse(model, courseIdStr, "Course Participants");

		model.addAttribute("currentcourse", course);	
		return "participants";
	}

	
	
	
}
	
