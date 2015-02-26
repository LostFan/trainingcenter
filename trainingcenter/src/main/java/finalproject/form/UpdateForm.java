package finalproject.form;

import org.hibernate.validator.constraints.Range;

public class UpdateForm extends CourseForm {

	@Range(min = 1, max = 10)
	private int minimalSubscribers;
	
	@Range(min = 1, max = 10)
	private int minimalAttendee;
	
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
	
	
}
