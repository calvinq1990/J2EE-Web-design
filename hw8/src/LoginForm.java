/**
 * 
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * Course: 08600
 * 
 */

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class LoginForm  {
    private String emailAddr;
    private String password;
    private String button;
	
    public LoginForm(HttpServletRequest request) {
    	emailAddr = request.getParameter("emailAddr");
    	password = request.getParameter("password");
    	button   = request.getParameter("button");
    }
    public String getEmailAddr()  { return emailAddr; }
    public String getPassword()  { return password; }
    public String getButton()    { return button;   }
    
    public boolean isPresent()   { return button != null; }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (emailAddr == null || emailAddr.length() == 0) errors.add("Email Address is required");
        if (password == null || password.length() == 0) errors.add("Password is required");
        if (button == null) errors.add("Button is required");

        if (errors.size() > 0) return errors;

        if (!button.equals("Login") && !button.equals("Register")) errors.add("Invalid button");
        if (emailAddr.matches(".*[<>\"].*")) errors.add("User Name may not contain angle brackets or quotes");
		
        return errors;
    }
}
