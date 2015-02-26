package finalproject.form;

import org.hibernate.validator.constraints.Range;


public class EvaluationForm {

	@Range(min = 1, max =5)
	private int grade;

	public void setGrade(int grade) {
			this.grade = grade;
	}
}
