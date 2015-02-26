package finalproject.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import finalproject.domain.Decision;
import finalproject.form.ApproveForm;

@Component
public class ApproveFormValidator implements Validator {

	public boolean supports(Class<?> aClass) { 
        return aClass.isAssignableFrom(ApproveForm.class);
	}

	public void validate(Object o, Errors errors) {
		ApproveForm approve = (ApproveForm) o;
	   if (approve.getDecision() == null || approve.getDecision() == Decision.None)
		     errors.rejectValue("decision", "NotNull.approve.decision"); 
	   //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reason", "Size.approve.reason");
	}
}
