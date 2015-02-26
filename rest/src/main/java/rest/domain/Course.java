package rest.domain;

import java.util.Map;
import java.util.Set;

public class Course {
	
	private int id;
	
	private String name;
	
	private String description;
	
	private String links;
	
	private User creater;
	
	private Category category;
	
	private CourseState state;
	
	private Decision decisionKM;
	
	private Decision decisionDM;
	
	private String reasonKM;
	
	private String reasonDM;
	
	private int minimalSubscribers;
	
	private int minimalAttendee;
	
	private Set<User> coursesSubscribe;
	
	private Set<User> coursesAttend;
	
	private Map<User, Integer> coursesEvaluate;
	
	public Course(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Course(String name, Category category, String description, String links, User creater, CourseState courseState) {
		this.category = category;
		this.description = description;
		this.name = name;
		this.links = links;
		this.creater = creater;
		this.state = courseState;
	}
	
	public Course(String name, Category category, String description, String links, int minimalSubscribers,
			int minimalAttendee, User creater, CourseState courseState) {
		this.category = category;
		this.description = description;
		this.name = name;
		this.links = links;
		this.creater = creater;
		this.state = courseState;
		this.minimalSubscribers = minimalSubscribers;
		this.minimalAttendee = minimalAttendee;
	}
	
	public Course() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLinks() {
		return links;
	}

	public void setLinks(String links) {
		this.links = links;
	}

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public CourseState getState() {
		return state;
	}

	public void setState(CourseState courseState) {
		this.state = courseState;
	}

	public Decision getDecisionKM() {
		return decisionKM;
	}

	public void setDecisionKM(Decision decisionKM) {
		this.decisionKM = decisionKM;
	}

	public Decision getDecisionDM() {
		return decisionDM;
	}

	public void setDecisionDM(Decision decisionDM) {
		this.decisionDM = decisionDM;
	}

	public String getReasonKM() {
		return reasonKM;
	}

	public void setReasonKM(String reasonKM) {
		this.reasonKM = reasonKM;
	}

	public String getReasonDM() {
		return reasonDM;
	}

	public void setReasonDM(String reasonDM) {
		this.reasonDM = reasonDM;
	}
	
	public int getMinimalSubscribers() {
		return minimalSubscribers;
	}

	public void setMinimalSubscribers(int minimalSubscribers) {
		this.minimalSubscribers = minimalSubscribers;
	}

	public int getMinimalAttendee() {
		return minimalAttendee;
	}

	public void setMinimalAttendee(int minimalAttendee) {
		this.minimalAttendee = minimalAttendee;
	}

	public Set<User> getCoursesSubscribe() {
		return coursesSubscribe;
	}

	public void setCoursesSubscribe(Set<User> coursesSubscribe) {
		this.coursesSubscribe = coursesSubscribe;
	}

	public Set<User> getCoursesAttend() {
		return coursesAttend;
	}

	public void setCoursesAttend(Set<User> coursesAttend) {
		this.coursesAttend = coursesAttend;
	}

	public Map<User, Integer> getCoursesEvaluate() {
		return coursesEvaluate;
	}

	public void setCoursesEvaluate(Map<User, Integer> coursesEvaluate) {
		this.coursesEvaluate = coursesEvaluate;
	}
	
	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	 public Double getMeanGrade() {
		  if (this.coursesEvaluate.size() == 0) {
	            return null;
	        }
		 double meanGrade = 0;
		 for (int grade : coursesEvaluate.values()) {
			 meanGrade =+ grade;
		}
		return meanGrade/coursesEvaluate.size();
	 }
	 
	 public Double getUserGrade(User user) {
		  if (!this.coursesEvaluate.containsKey(user)) {
	            return null;
	        }
		 return this.coursesEvaluate.get(user).doubleValue();
	 }

}
