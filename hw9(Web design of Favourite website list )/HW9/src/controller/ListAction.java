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

import model.Model;
import model.FavoriteDAO;
import model.UserDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Favorite;
import databeans.User;
import formbeans.IdForm;

public class ListAction extends Action {
	private FormBeanFactory<IdForm> formBeanFactory = FormBeanFactory
			.getInstance(IdForm.class);

	private FavoriteDAO favoriteDAO;
	private UserDAO userDAO;

	public ListAction(Model model) {
		favoriteDAO = model.getFavoriteDAO();
		userDAO = model.getUserDAO();
	}

	public String getName() {
		return "list.do";
	}

	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			// Set up user list for nav bar
			request.setAttribute("userList", userDAO.getUsers());

			IdForm form = formBeanFactory.create(request);
			System.out.println("ListAction: " + form.getId());
			if (form.getId() != null) {
				int id = form.getIdAsInt();
				if (form.getId() == null || form.getId().length() == 0) {
					errors.add("User must be specified");
					return "error.jsp";
				}

				User user = userDAO.read(id);
				if (user == null) {
					errors.add("Invalid User ID: " + id);
					return "error.jsp";
				}
				System.out.println("ListAction before get list: "
						+ user.getEmailAddr() + " " + user.getId());
				Favorite[] favoriteList = favoriteDAO.getUserFavorites(user
						.getId());
				System.out.println("ListAction after get list: "
						+ user.getEmailAddr() + " " + user.getId());
				request.setAttribute("favoriteList", favoriteList);
			}
			return "visit.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}

}
