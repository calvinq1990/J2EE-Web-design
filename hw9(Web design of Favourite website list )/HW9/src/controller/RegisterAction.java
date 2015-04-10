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
import formbeans.RegisterForm;

public class RegisterAction extends Action {
	private FormBeanFactory<RegisterForm> formBeanFactory = FormBeanFactory.getInstance(RegisterForm.class);

	private UserDAO userDAO;
	
	public RegisterAction(Model model) {
		userDAO = model.getUserDAO();
	}

	public String getName() { return "register.do"; }

    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);

        try {
	        RegisterForm form = formBeanFactory.create(request);
	        request.setAttribute("form",form);
	        request.setAttribute("userList",userDAO.getUsers());
	
	        if (!form.isPresent()) {
	            return "register.jsp";
	        }
	
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() != 0) {
	            return "register.jsp";
	        }
	        User[] users = this.userDAO.getUsers();
	        for (User user : users) {
	        	if (user.getEmailAddr().equals(form.getEmailAddr()))  {
	        		errors.add("Create fails! Duplicate Email Address!");
	        		return "register.jsp";
	        	}
	        }
	        // Create the user bean
	        User user = new User();
	        user.setEmailAddr(form.getEmailAddr());
	        user.setFirstName(form.getFirstName());
	        user.setLastName(form.getLastName());
	        user.setPassword(form.getPassword());
        	userDAO.create(user);
        
			// Attach (this copy of) the user bean to the session
	        HttpSession session = request.getSession(false);
	        session.setAttribute("user",user);
	        
			return "manage.do";
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "register.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "register.jsp";
        }
    }
}
