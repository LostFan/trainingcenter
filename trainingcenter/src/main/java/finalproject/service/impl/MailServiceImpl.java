package finalproject.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import finalproject.dao.UsersDAO;
import finalproject.domain.Course;
import finalproject.domain.CourseState;
import finalproject.domain.Decision;
import finalproject.domain.Role;
import finalproject.domain.User;
import finalproject.form.CourseForm;
import finalproject.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@Service("mailService")
public class MailServiceImpl implements MailService {
	
    @Autowired
    private FreeMarkerConfigurer freemarkerConfig;
    
    @Autowired
    private UsersDAO usersDAO;
    
    @Autowired
    private JavaMailSenderImpl mailSender;
         
    
    public static final Logger log = LogManager.getLogger("myLogger");
    
	public void sendMail(String message, List<String> toUser, List<String> ccUser) {
		
		Properties p = System.getProperties();
		p.put("mail.smtp.host", "localhost");
		Session s = Session.getDefaultInstance(p,null);
		MimeMessage m = new MimeMessage(s);
		//InternetAddress to;
		try {
			//to = new InternetAddress(toUser);
			m.setContent(message, "text/html; charset=utf-8");
			m.setSubject("Course Announcement");
			MimeMessageHelper mHelper = new MimeMessageHelper(m);
			for (String to : toUser) {
				mHelper.addTo(to);
			}
			for (String cc : ccUser) {
				mHelper.addCc(cc);
			}
			mailSender.send(mHelper.getMimeMessage());
		}  catch (MessagingException e) {
			log.error("MimeMessage exception", e);
			return;
		}
		
		
	}
    
	public void courseAnnouncement(Course course) {
		
		ServletRequestAttributes sra = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
		String link = sra.getRequest().getScheme() + "://" + sra.getRequest().getServerName() + ":"
		+ sra.getRequest().getServerPort() + sra.getRequest().getContextPath()
		+ "/courses/" + course.getId() + "/approve";
		Map model = new HashMap();
		model.put("course", course);
		model.put("CourseApproveLink", link);
		String DM = usersDAO.getUserForUniqueRole(Role.DM).getName();
	    String KM = usersDAO.getUserForUniqueRole(Role.KM).getName();
		String message = null;
		try {
			Template template =
				 freemarkerConfig.getConfiguration().getTemplate("mail/course_announcement.ftl", "UTF-8");
			message = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		} catch (IOException e) {
			log.error("Freemarker e-mail" , e);
			return;
		} catch (TemplateException e) {
			log.error("Freemarker e-mail", e);
			return;
		}
		String creater =  course.getCreater().getName();
		 //System.out.println(message);
		List <String> cc = new ArrayList<String>();
		cc.add(creater);
		List <String> to = new ArrayList<String>();
		to.add(KM);
		to.add(DM);
		sendMail(message, to, cc);
		log.info("message to " + KM +". \n"+ message);
		log.info("message to " + DM +". \n"+ message);
	}

	public void courseApprovalUpdate(User fromUser, Course course) {
		
		Map model = new HashMap();
		model.put("course", course);
		model.put("manager", fromUser);
		if(fromUser.getRole() == Role.DM) {
			model.put("reason", course.getReasonDM());
			model.put("decision", course.getDecisionDM());
		}
		if(fromUser.getRole() == Role.KM) {
			model.put("reason", course.getReasonKM());
			model.put("decision", course.getDecisionKM());
		}
		String DM = usersDAO.getUserForUniqueRole(Role.DM).getName();
	    String KM = usersDAO.getUserForUniqueRole(Role.KM).getName();
		String message = null;
		try {
			Template template =
				 freemarkerConfig.getConfiguration().getTemplate("mail/course_approval_update.ftl", "UTF-8");
			message  = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		} catch (IOException e) {
			log.error("Freemarker e-mail" , e);
			return;
		} catch (TemplateException e) {
			log.error("Freemarker e-mail", e);
			return;
		}
		List <String> creater =  new ArrayList<String>();
		creater.add(course.getCreater().getName());
		List <String> cc = new ArrayList<String>();
		cc.add(KM);
		cc.add(DM);
		sendMail(message, creater, cc);
		log.info("message to " + creater +". \n"+ message);
		//sendMail(message, fromUserName, secondLetterTo);
	}

	public void courseRejected(User fromUser, Course course) {

		ServletRequestAttributes sra = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes(); 
		String link = sra.getRequest().getScheme() + "://" + sra.getRequest().getServerName() + ":"
				+ sra.getRequest().getServerPort() + sra.getRequest().getContextPath()
				+ "/courses/" + course.getId() + "/update";
		Map model = new HashMap();
		model.put("course", course);
		model.put("CourseUpdateLink", link);
		if(course.getDecisionDM() == Decision.Reject && course.getDecisionKM() == Decision.Reject)
			model.put("number", "both");
		else
			model.put("number", "one vote");
		

		String DM = usersDAO.getUserForUniqueRole(Role.DM).getName();
	    String KM = usersDAO.getUserForUniqueRole(Role.KM).getName();
		String message = null;
		try {
			Template template =
				 freemarkerConfig.getConfiguration().getTemplate("mail/course_rejected.ftl", "UTF-8");
			message  = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		} catch (IOException e) {
			log.error("Freemarker e-mail" , e);
			return;
		} catch (TemplateException e) {
			log.error("Freemarker e-mail", e);
			return;
		}
		List <String> creater =  new ArrayList<String>();
		creater.add(course.getCreater().getName());
		List <String> cc = new ArrayList<String>();
		cc.add(KM);
		cc.add(DM);
		sendMail(message, creater, cc);
		log.info("message from to " + creater +". \n"+ message);

		
	}

	public void newCourseAdded(User fromUser, Course course) {
		
		ServletRequestAttributes sra = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes(); 
		String courseDetailLink = sra.getRequest().getScheme() + "://" + sra.getRequest().getServerName() + ":"
				+ sra.getRequest().getServerPort() + sra.getRequest().getContextPath()
				+ "/courses/" + course.getId();
		String courseSubscribeLink = courseDetailLink + "/subscribe";
		Map model = new HashMap();
		model.put("course", course);
		model.put("courseDetailLink", courseDetailLink);
		model.put("courseSubscribeLink", courseSubscribeLink);
		
		String DM = usersDAO.getUserForUniqueRole(Role.DM).getName();
	    String KM = usersDAO.getUserForUniqueRole(Role.KM).getName();
		String message = null;
		try {
			Template template =
				 freemarkerConfig.getConfiguration().getTemplate("mail/new_course_added.ftl", "UTF-8");
			message  = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		} catch (IOException e) {
			log.error("Freemarker e-mail" , e);
			return;
		} catch (TemplateException e) {
			log.error("Freemarker e-mail", e);
			return;
		}
		String creater =  course.getCreater().getName();
		List <String> cc = new ArrayList<String>();
		cc.add(creater);
		cc.add(KM);
		cc.add(DM);
		List <String> to = new ArrayList<String>();
		for (User student : usersDAO.getUserListForRole(Role.STUDENT)) {
			to.add(student.getName());
		}
		sendMail(message, to, cc);
		log.info("message to all students" + ". \n"+ message);
	}

	public void courseDeleted(Course course) {
		Map model = new HashMap();
		model.put("course", course);
		String DM = usersDAO.getUserForUniqueRole(Role.DM).getName();
	    String KM = usersDAO.getUserForUniqueRole(Role.KM).getName();
		String message = null;
		try {
			Template template =
				 freemarkerConfig.getConfiguration().getTemplate("mail/course_deleted.ftl", "UTF-8");
			message = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		} catch (IOException e) {
			log.error("Freemarker e-mail" , e);
			return;
		} catch (TemplateException e) {
			log.error("Freemarker e-mail", e);
			return;
		}
		List <String> creater =  new ArrayList<String>();
		creater.add(course.getCreater().getName());
		List <String> cc = new ArrayList<String>();
		cc.add(DM);
		cc.add(KM);
		sendMail(message, creater, cc);
		log.info("message from "+ creater +" to " + KM +". \n"+ message);
		log.info("message from "+ creater +" to " + DM +". \n"+ message);
		
	}
	
	public void courseOpened(Course course) {
		ServletRequestAttributes sra = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes(); 
		String courseDetailLink = sra.getRequest().getScheme() + "://" + sra.getRequest().getServerName() + ":"
				+ sra.getRequest().getServerPort() + sra.getRequest().getContextPath()
				+ "/courses/" + course.getId();
		String courseAttendeLink = courseDetailLink + "/attend";
		Map model = new HashMap();
		model.put("course", course);
		model.put("courseDetailLink", courseDetailLink);
		model.put("courseSubscribeLink", courseAttendeLink);
		String DM = usersDAO.getUserForUniqueRole(Role.DM).getName();
	    String KM = usersDAO.getUserForUniqueRole(Role.KM).getName();
		String message = null;
		try {
			Template template =
				 freemarkerConfig.getConfiguration().getTemplate("mail/course_opened.ftl", "UTF-8");
			message = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		} catch (IOException e) {
			log.error("Freemarker e-mail" , e);
			return;
		} catch (TemplateException e) {
			log.error("Freemarker e-mail", e);
			return;
		}
		String creater =  course.getCreater().getName();
		List <String> cc = new ArrayList<String>();
		cc.add(creater);
		cc.add(KM);
		cc.add(DM);
		List <String> to = new ArrayList<String>();
		for (User student : course.getCoursesSubscribe()) 
			to.add(student.getName());
		sendMail(message, to, cc);
		log.info("message to subscribers"+". \n");
		
	}

	public void courseReady(Course course) {
		ServletRequestAttributes sra = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes(); 
		String courseDetailLink = sra.getRequest().getScheme() + "://" + sra.getRequest().getServerName() + ":"
				+ sra.getRequest().getServerPort() + sra.getRequest().getContextPath()
				+ "/courses/" + course.getId();
		Map model = new HashMap();
		model.put("course", course);
		model.put("courseDetailLink", courseDetailLink);
		String DM = usersDAO.getUserForUniqueRole(Role.DM).getName();
	    String KM = usersDAO.getUserForUniqueRole(Role.KM).getName();
		String message = null;
		try {
			Template template =
				 freemarkerConfig.getConfiguration().getTemplate("mail/course_ready.ftl", "UTF-8");
			message = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		} catch (IOException e) {
			log.error("Freemarker e-mail" , e);
			return;
		} catch (TemplateException e) {
			log.error("Freemarker e-mail", e);
			return;
		}
		String creater =  course.getCreater().getName();
		List <String> cc = new ArrayList<String>();
		cc.add(creater);
		cc.add(KM);
		cc.add(DM);
		List <String> to = new ArrayList<String>();
		
		for (User student : course.getCoursesAttend()) 
			to.add(student.getName());
		sendMail(message, to, cc);
		log.info("message from to attenders"+". \n");
		
	}
	
	public void courseStarted(Course course) {
		ServletRequestAttributes sra = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes(); 
		String courseDetailLink = sra.getRequest().getScheme() + "://" + sra.getRequest().getServerName() + ":"
				+ sra.getRequest().getServerPort() + sra.getRequest().getContextPath()
				+ "/courses/" + course.getId();
		Map model = new HashMap();
		model.put("course", course);
		model.put("courseDetailLink", courseDetailLink);
		String DM = usersDAO.getUserForUniqueRole(Role.DM).getName();
	    String KM = usersDAO.getUserForUniqueRole(Role.KM).getName();
		String message = null;
		try {
			Template template =
				 freemarkerConfig.getConfiguration().getTemplate("mail/course_ready.ftl", "UTF-8");
			message = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		} catch (IOException e) {
			log.error("Freemarker e-mail" , e);
			return;
		} catch (TemplateException e) {
			log.error("Freemarker e-mail", e);
			return;
		}
		String creater =  course.getCreater().getName();
		List <String> cc = new ArrayList<String>();
		cc.add(creater);
		cc.add(KM);
		cc.add(DM);
		List <String> to = new ArrayList<String>();
		
		for (User student : course.getCoursesAttend()) 
			to.add(student.getName());
		sendMail(message, to, cc);
		log.info("message from to attenders"+". \n");
		
		
	}
	
	public void courseFinished(Course course) {
		ServletRequestAttributes sra = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes(); 
		String courseDetailLink = sra.getRequest().getScheme() + "://" + sra.getRequest().getServerName() + ":"
				+ sra.getRequest().getServerPort() + sra.getRequest().getContextPath()
				+ "/courses/" + course.getId();
		Map model = new HashMap();
		model.put("course", course);
		model.put("courseDetailLink", courseDetailLink);
		String DM = usersDAO.getUserForUniqueRole(Role.DM).getName();
	    String KM = usersDAO.getUserForUniqueRole(Role.KM).getName();
		String message = null;
		try {
			Template template =
				 freemarkerConfig.getConfiguration().getTemplate("mail/course_finished.ftl", "UTF-8");
			message = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		} catch (IOException e) {
			log.error("Freemarker e-mail" , e);
			return;
		} catch (TemplateException e) {
			log.error("Freemarker e-mail", e);
			return;
		}
		String creater =  course.getCreater().getName();
		List <String> cc = new ArrayList<String>();
		cc.add(creater);
		cc.add(KM);
		cc.add(DM);
		List <String> to = new ArrayList<String>();
		
		for (User student : course.getCoursesAttend()) 
			to.add(student.getName());
		sendMail(message, to, cc);
		log.info("message from to attenders"+". \n");
		
	}
	
	public void courseNotify(Course course) {
		ServletRequestAttributes sra = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes(); 
		String courseDetailLink = sra.getRequest().getScheme() + "://" + sra.getRequest().getServerName() + ":"
				+ sra.getRequest().getServerPort() + sra.getRequest().getContextPath()
				+ "/courses/" + course.getId();
		String courseEvaluateLink = courseDetailLink + "/evaluate";
		Map model = new HashMap();
		model.put("course", course);
		model.put("courseDetailLink", courseDetailLink);
		model.put("courseEvaluateLink", courseEvaluateLink);
		String DM = usersDAO.getUserForUniqueRole(Role.DM).getName();
	    String KM = usersDAO.getUserForUniqueRole(Role.KM).getName();
		String message = null;
		try {
			Template template =
				 freemarkerConfig.getConfiguration().getTemplate("mail/course_notify.ftl", "UTF-8");
			message = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		} catch (IOException e) {
			log.error("Freemarker e-mail" , e);
			return;
		} catch (TemplateException e) {
			log.error("Freemarker e-mail", e);
			return;
		}
		String creater =  course.getCreater().getName();
		List <String> cc = new ArrayList<String>();
		cc.add(creater);
		cc.add(KM);
		cc.add(DM);
		List <String> to = new ArrayList<String>();
		for (User student : course.getCoursesAttend()) 
			if(!course.getCoursesEvaluate().containsKey(student)) 
				to.add(student.getName());
		sendMail(message, to, cc);
		log.info("message to attenders" + ". \n");
			
		
	}


}
