package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;
import model.FavoriteDAO;
import model.UserDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Favorite;
import formbeans.IdForm;
/**
 * 
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * Course: 08600
 * 
 */
public class ViewAction extends Action {
	private FormBeanFactory<IdForm> formBeanFactory = FormBeanFactory.getInstance(IdForm.class);

	private FavoriteDAO favoriteDAO;
	private UserDAO  userDAO;
	
    public ViewAction(Model model) {
    	favoriteDAO = model.getFavoriteDAO();
    	userDAO  = model.getUserDAO();
	}

    public String getName() { return "view.do"; }



    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
		try {
            // Set up user list for nav bar
			System.out.println("ViewAction: start ");
			request.setAttribute("userList",userDAO.getUsers());

	    	IdForm form = formBeanFactory.create(request);
	    	System.out.println("ViewAction:" + form.getId());
	        // Any validation errors?
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() != 0) {
	            return "error.jsp";
	        }
	        
        
    		int id = form.getIdAsInt();
    		Favorite f = favoriteDAO.read(id);
    		if (f == null) {
    			errors.add("No picture with id="+id);
    			return "error.jsp";
    		}
    		favoriteDAO.updateCount(f.getId());
    		request.setAttribute("favoriteList",favoriteDAO.getUserFavorites(f.getUserId()));
    		return f.getUrl() + "	redirect.jsp";
           

    	} catch (RollbackException e) {
    		errors.add(e.getMessage());
    		return "error.jsp";
    	} catch (FormBeanException e) {
    		errors.add(e.getMessage());
    		return "error.jsp";
    	}
    }
}
