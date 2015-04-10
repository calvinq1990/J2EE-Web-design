package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class UserForm extends FormBean {
	private String emailAddr = "";
	
	public String getUserName()  { return emailAddr; }
	
	public void setUserName(String s)  { emailAddr = trimAndConvert(s,"<>>\"]"); }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (emailAddr == null || emailAddr.length() == 0) {
			errors.add("userform: Email Address is required");
		}
		
		return errors;
	}
}
