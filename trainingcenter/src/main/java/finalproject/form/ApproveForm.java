package finalproject.form;

import org.hibernate.validator.constraints.NotBlank;

import finalproject.domain.Decision;


public class ApproveForm {

	private Decision decision;
	
	@NotBlank
	private String reason;

	public Decision getDecision() {
		return decision;
	}

	public void setDecision(Decision decision) {
		this.decision = decision;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	

}
