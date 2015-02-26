package finalproject.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import finalproject.domain.Course;
import finalproject.domain.CourseState;
import finalproject.form.ApproveForm;
import finalproject.form.CourseForm;
import finalproject.form.UpdateForm;
import finalproject.service.SecurityService;
import finalproject.service.MailService;
import finalproject.validator.ApproveFormValidator;


@Controller
@RequestMapping(value = "/courses")
public class LecturerConroller extends MainConroller{
	
	@Autowired
	private ApproveFormValidator approveFormValidator;
	
	@Autowired
	private MailService mailService;
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	  public String createcourse(@Valid @ModelAttribute("course") CourseForm course, BindingResult result, 
			  ModelMap model, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Create");
			if (result.hasErrors()) {
				model.addAttribute("allCategories", coursesService.getAllCategories());
				return "create_course";
			}
			else {
				coursesService.save(course);
				return "redirect:/courses";
			}
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	  public String createcourseGet(ModelMap model, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Create");
		model.addAttribute("course", new CourseForm());
		model.addAttribute("allCategories", coursesService.getAllCategories());
		return "create_course";
	}
	
	@RequestMapping(value = "/{courseId}/update", method = RequestMethod.GET)
	  public String updateGet(ModelMap model, @PathVariable("courseId") String courseIdStr, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Update");
		model.addAttribute("course", new UpdateForm());
		model.addAttribute("allCategories", coursesService.getAllCategories());
		Course course = coursesService.getCurrentCourse(courseIdStr);
		securityService.checkAllowed(course, "update");
		model.addAttribute("currentcourse", course);
		return "update_course";
	}
		
	@RequestMapping(value = "/{courseId}/update", method = RequestMethod.POST)
  public String updatePost(ModelMap model, @PathVariable("courseId") String courseIdStr,
  		@Valid @ModelAttribute("course") UpdateForm course, BindingResult result, String action, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Update");
		Course currentCourse = coursesService.getCurrentCourse(courseIdStr);
		model.addAttribute("currentcourse", currentCourse);
		if (result.hasErrors()) {
			model.addAttribute("allCategories", coursesService.getAllCategories());
			return "update_course";
			
		}
		else {

			if("update".equals(action))
				coursesService.update(Integer.parseInt(courseIdStr) , course);
			else {
				coursesService.changeStateCourses(Integer.parseInt(courseIdStr), CourseState.Proposal);
			}
			return "redirect:/courses";
		}
	}
	
	@RequestMapping(value = "/{courseId}/approve", method = RequestMethod.GET)
	  public String attendGet(ModelMap model, @PathVariable("courseId") String courseIdStr, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Attend");
		model.addAttribute("approve", new ApproveForm());
		Course course = coursesService.getCurrentCourse(courseIdStr);
		securityService.checkAllowed(course, "approve");
		model.addAttribute("currentcourse", course);
		return "approve";
	}
	
	@RequestMapping(value = "/{courseId}/approve", method = RequestMethod.POST)
	  public String attendPost(ModelMap model, @PathVariable("courseId") String courseIdStr, HttpServletRequest req,
			  @Valid @ModelAttribute("approve") ApproveForm approve, BindingResult result) {
		req.setAttribute("pageTitle", "Approve");
		Course course = coursesService.getCurrentCourse(courseIdStr);
		securityService.checkAllowed(course, "approve");
		model.addAttribute("currentcourse", course);
		approveFormValidator.validate(approve, result);
		if (result.hasErrors()) {
			return "approve";	
		}
		coursesService.approvalManagers(course, approve);
		return "redirect:/courses";
	}
	
//	@InitBinder("approve")
//	private void courseFormInitBinder(WebDataBinder binder) {
//        binder.setValidator(approveFormValidator); 
//    }
	
	@RequestMapping(value = "/{courseId}/delete", method = RequestMethod.GET)
	public String deleteeGet(ModelMap model, @PathVariable("courseId") String courseIdStr, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Delete");
		Course course = coursesService.getCurrentCourse(courseIdStr);
		securityService.checkAllowed(course, "delete");
		model.addAttribute("currentcourse", course);
		return "delete";
	}
		
	@RequestMapping(value = "/{courseId}/delete", method = RequestMethod.POST)
	public String deletePost(ModelMap model, @PathVariable("courseId") String courseIdStr, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Delete");
		Course currentCourse = coursesService.getCurrentCourse(courseIdStr);
		model.addAttribute("currentcourse", currentCourse);
		coursesService.delete(Integer.parseInt(courseIdStr));
		return "redirect:/courses";
	}
	
	@RequestMapping(value = "/{courseId}/start", method = RequestMethod.GET)
	public String starteGet(ModelMap model, @PathVariable("courseId") String courseIdStr, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Start");
		Course course = coursesService.getCurrentCourse(courseIdStr);
		securityService.checkAllowed(course, "start");
		model.addAttribute("currentcourse", course);
		return "start";
	}
		
	@RequestMapping(value = "/{courseId}/start", method = RequestMethod.POST)
	public String startPost(ModelMap model, @PathVariable("courseId") String courseIdStr, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Start");
		Course currentCourse = coursesService.getCurrentCourse(courseIdStr);
		model.addAttribute("currentcourse", currentCourse);
		Course course = coursesService.getCurrentCourse(courseIdStr);
		coursesService.start(course, CourseState.InProgress);
		return "redirect:/courses";
	}
	
	@RequestMapping(value = "/{courseId}/finish", method = RequestMethod.GET)
	public String finishGet(ModelMap model, @PathVariable("courseId") String courseIdStr, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Finish");
		Course course = coursesService.getCurrentCourse(courseIdStr);
		securityService.checkAllowed(course, "finish");
		model.addAttribute("currentcourse", course);
		return "finish";
	}
		
	@RequestMapping(value = "/{courseId}/finish", method = RequestMethod.POST)
	public String finishPost(ModelMap model, @PathVariable("courseId") String courseIdStr, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Finish");
		Course currentCourse = coursesService.getCurrentCourse(courseIdStr);
		model.addAttribute("currentcourse", currentCourse);
		Course course = coursesService.getCurrentCourse(courseIdStr);
		coursesService.finish(course, CourseState.Finished);
		return "redirect:/courses";
	}
	
	@RequestMapping(value = "/{courseId}/notify", method = RequestMethod.GET)
	public String notifyGet(ModelMap model, @PathVariable("courseId") String courseIdStr, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Notify");
		Course course = coursesService.getCurrentCourse(courseIdStr);
		securityService.checkAllowed(course, "notify");
		model.addAttribute("currentcourse", course);
		return "notify";
	}
		
	@RequestMapping(value = "/{courseId}/notify", method = RequestMethod.POST)
	public String notifyPost(ModelMap model, @PathVariable("courseId") String courseIdStr, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Notify");
		Course currentCourse = coursesService.getCurrentCourse(courseIdStr);
		model.addAttribute("currentcourse", currentCourse);
		Course course = coursesService.getCurrentCourse(courseIdStr);
		mailService.courseNotify(course);
		return "redirect:/courses";
	}
	
	@RequestMapping(value = "/{courseId}/review", method = RequestMethod.GET)
	public String reviewGet(ModelMap model, @PathVariable("courseId") String courseIdStr, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Review");
		Course course = coursesService.getCurrentCourse(courseIdStr);
		securityService.checkAllowed(course, "update");
		model.addAttribute("currentcourse", course);
		return "send_to_review";
	}
		
	@RequestMapping(value = "/{courseId}/review", method = RequestMethod.POST)
	public String reviewPost(ModelMap model, @PathVariable("courseId") String courseIdStr, HttpServletRequest req) {
		req.setAttribute("pageTitle", "Review");
		Course currentCourse = coursesService.getCurrentCourse(courseIdStr);
		model.addAttribute("currentcourse", currentCourse);
		coursesService.changeStateCourses(Integer.parseInt(courseIdStr), CourseState.Proposal);
		return "redirect:/courses";
	}
	
}
