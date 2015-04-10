package formbeans;
/**
 * 
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * Course: 08600
 * 
 */
import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class LoginForm extends FormBean {
	private String emailAddr;
	private String password;
	
	public String getEmailAddr()  { return emailAddr; }
	public String getPassword()  { return password; }
	
	public void setEmailAddr(String s) { emailAddr = trimAndConvert(s,"<>\"");  }
	public void setPassword(String s) {	password = s.trim();                  }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (emailAddr == null || emailAddr.length() == 0) {
			errors.add("Login: Email Address is required");
		}
		
		if (password == null || password.length() == 0) {
			errors.add("Password is required");
		}
		
		return errors;
	}
}