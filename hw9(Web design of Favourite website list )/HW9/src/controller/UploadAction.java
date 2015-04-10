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
import formbeans.FavoriteForm;
/**
 * 
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * Course: 08600
 * 
 */
public class UploadAction extends Action {
	private FormBeanFactory<FavoriteForm> formBeanFactory = FormBeanFactory.getInstance(FavoriteForm.class);

	private FavoriteDAO favoriteDAO;
	private UserDAO  userDAO;
	
	public UploadAction(Model model) {
    	favoriteDAO = model.getFavoriteDAO();
    	userDAO  = model.getUserDAO();
	}

	public String getName() { return "upload.do"; }

    public String perform(HttpServletRequest request) {
        // Set up the errors list
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
		try {
            // Set up user list for nav bar
			request.setAttribute("userList",userDAO.getUsers());

			User user = (User) request.getSession(false).getAttribute("user");
			Favorite[] favoriteList = favoriteDAO.getUserFavorites(user.getId());
			System.out.println("uploadaction: " + favoriteList.length);
	        request.setAttribute("favoriteList",favoriteList);

			FavoriteForm form = formBeanFactory.create(request);
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() > 0) return "error.jsp";


	        Favorite favorite = new Favorite();
	        favorite.setUserId(user.getId());
	        favorite.setClickCount(0);
	        favorite.setComment(form.getComment());
	        favorite.setUrl(form.getUrl());
	        favoriteDAO.create(favorite);

	        Favorite[] newFavoriteList = favoriteDAO.getUserFavorites(user.getId());
	        request.setAttribute("favoriteList",newFavoriteList);
	        return "manage.jsp";
	 	} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "manage.jsp";
	 	} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "manage.jsp";
		}
    }
    


}
