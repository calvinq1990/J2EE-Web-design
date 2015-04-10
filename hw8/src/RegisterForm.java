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

public class RegisterForm  {
    private String firstName;
    private String lastName;
    private String emailAddr;
    private String password;
    private String button;
	
    public RegisterForm(HttpServletRequest request) {
    	firstName = request.getParameter("firstName");
    	password = request.getParameter("password");
    	button   = request.getParameter("button");
    	lastName = request.getParameter("lastName");
    	emailAddr = request.getParameter("emailAddr");
    	
    }
    public String getFirstName()  { return firstName; }
    public String getLastName()  { return lastName; }
    public String getEmailAddress()  { return emailAddr; }
    public String getPassword()  { return password; }
    public String getButton()    { return button;   }
    
    public boolean isPresent()   { return button != null; }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (firstName == null || firstName.length() == 0) errors.add("First Name is required");
        if (lastName == null || lastName.length() == 0) errors.add("Last Name is required");
        if (password == null || password.length() == 0) errors.add("Password is required");
        if (emailAddr == null || emailAddr.length() == 0) errors.add("Email address is required");
        if (button == null) errors.add("Button is required");

        if (errors.size() > 0) return errors;

        if ( !button.equals("Register")) errors.add("Invalid button");
        if (firstName.matches(".*[<>\"].*")) errors.add("First Name may not contain angle brackets or quotes");
        if (lastName.matches(".*[<>\"].*")) errors.add("Last Name may not contain angle brackets or quotes");
        return errors;
    }
}
