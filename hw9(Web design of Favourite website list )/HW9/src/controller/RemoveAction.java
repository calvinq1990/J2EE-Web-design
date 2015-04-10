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
import databeans.User;
import formbeans.IdForm;
/**
 * 
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * Course: 08600
 * 
 */
public class RemoveAction extends Action {




	private FormBeanFactory<IdForm> formBeanFactory = FormBeanFactory.getInstance(IdForm.class);

	private FavoriteDAO favoriteDAO;
	private UserDAO  userDAO;

    public RemoveAction(Model model) {
    	favoriteDAO = model.getFavoriteDAO();
    	userDAO  = model.getUserDAO();
	}

    public String getName() { return "remove.do"; }

    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
		try {
			System.out.println("in RemoveAction!");
            // Set up user list for nav bar
			request.setAttribute("userList",userDAO.getUsers());

	    	IdForm form = formBeanFactory.create(request);
	    	
	    	User user = (User) request.getSession().getAttribute("user");

			int id = form.getIdAsInt();
			favoriteDAO.delete(id, user.getId());

    		// Be sure to get the photoList after the delete
			Favorite[] favoriteList = favoriteDAO.getUserFavorites(user.getId());
	        request.setAttribute("favoriteList",favoriteList);

	        return "manage.jsp";
		} catch (RollbackException e) {
    		errors.add(e.getMessage());
    		return "error.jsp";
		} catch (FormBeanException e) {
    		errors.add(e.getMessage());
    		return "error.jsp";
    	}
    }
}
