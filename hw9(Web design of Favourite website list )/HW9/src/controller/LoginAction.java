package controller;
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
import javax.servlet.http.HttpSession;

import model.Model;
import model.UserDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.User;
import formbeans.LoginForm;


public class LoginAction extends Action {
	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);
	
	private UserDAO userDAO;

	public LoginAction(Model model) {
		userDAO = model.getUserDAO();
	}

	public String getName() { return "login.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        try {
	    	LoginForm form = formBeanFactory.create(request);
	        request.setAttribute("form",form);
	        request.setAttribute("userList",userDAO.getUsers());

	        if (!form.isPresent()) {
	            return "login.jsp";
	        }

	        errors.addAll(form.getValidationErrors());
	        if (errors.size() != 0) {
	            return "login.jsp";
	        }

	        User user = userDAO.read(form.getEmailAddr());
	        
	        if (user == null) {
	            errors.add("Email Address not found");
	            return "login.jsp";
	        }

	        // Check the password
	        if (!user.getPassword().toString().equals(form.getPassword())) {
	            errors.add("Incorrect password");
	            return "login.jsp";
	        }
	
	        HttpSession session = request.getSession();
	        session.setAttribute("user",user);
			

	        return "manage.do";
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
    }
}
