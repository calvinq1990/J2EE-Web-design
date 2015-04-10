package controller;
/**
 * 
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * Course: 08600
 * 
 */
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import model.FavoriteDAO;
import model.Model;
import model.UserDAO;
import databeans.Favorite;
import databeans.User;


@SuppressWarnings("serial")
public class Controller extends HttpServlet {

    public void init() throws ServletException {
        Model model = new Model(getServletConfig());
        UserDAO userDAO = model.getUserDAO();
        FavoriteDAO favoriteDAO = model.getFavoriteDAO();
        
        Action.add(new ChangePwdAction(model));
        Action.add(new ListAction(model));
        Action.add(new LoginAction(model));
        Action.add(new LogoutAction(model));
        Action.add(new ManageAction(model));
        Action.add(new RegisterAction(model));
        Action.add(new RemoveAction(model));
        Action.add(new UploadAction(model));
        Action.add(new ViewAction(model));
      
        
        
        
        try {
			if (userDAO.getCount() < 1) {
				   // create the users and favorites
				   User u1 = new User();
				   u1.setEmailAddr("potus@whitehouse.gov");
				   u1.setFirstName("Barack");
				   u1.setLastName("Obama");
				   u1.setPassword("Michelle");
				   userDAO.createAutoIncrement(u1);
				 
				   Favorite f1 = new Favorite();
				   f1.setUrl("http://www.whitehouse.gov");
				   f1.setComment("My home");
				   f1.setUserId(u1.getId());
				   favoriteDAO.createAutoIncrement(f1);
				   Favorite f2 = new Favorite();
				   f2.setUrl("http://www.whitehouse.gov");
				   f2.setComment("My home");
				   f2.setUserId(u1.getId());
				   favoriteDAO.createAutoIncrement(f2);
				   Favorite f3 = new Favorite();
				   f3.setUrl("http://www.whitehouse.gov");
				   f3.setComment("My home");
				   f3.setUserId(u1.getId());
				   favoriteDAO.createAutoIncrement(f3);
				   Favorite f4 = new Favorite();
				   f4.setUrl("http://www.google.com");
				   f4.setComment("web site");
				   f4.setUserId(u1.getId());
				   favoriteDAO.createAutoIncrement(f4);
				   
				   
				   User u2 = new User();
				   u2.setEmailAddr("123@gmail.com");
				   u2.setFirstName("Barack2");
				   u2.setLastName("Obama2");
				   u2.setPassword("Michelle2");
				   userDAO.createAutoIncrement(u2);
				 
				   Favorite f5 = new Favorite();
				   f5.setUrl("http://www.google.com");
				   f5.setComment("web site");
				   f5.setUserId(u2.getId());
				   favoriteDAO.createAutoIncrement(f5);
				   Favorite f6 = new Favorite();
				   f6.setUrl("http://www.google.com");
				   f6.setComment("web site");
				   f6.setUserId(u2.getId());
				   favoriteDAO.createAutoIncrement(f6);
				   Favorite f7 = new Favorite();
				   f7.setUrl("http://www.google.com");
				   f7.setComment("web site");
				   f7.setUserId(u2.getId());
				   favoriteDAO.createAutoIncrement(f7);   
				   Favorite f8 = new Favorite();
				   f8.setUrl("http://www.google.com");
				   f8.setComment("web site");
				   f8.setUserId(u2.getId());
				   favoriteDAO.createAutoIncrement(f8);
				   
				   User u3 = new User();
				   u3.setEmailAddr("123@baidu.com");
				   u3.setFirstName("a3");
				   u3.setLastName("ab3");
				   u3.setPassword("abc3");
				   userDAO.createAutoIncrement(u3);
				 
				   Favorite f9 = new Favorite();
				   f9.setUrl("http://www.baidu.com");
				   f9.setComment("Chinese web");
				   f9.setUserId(u3.getId());
				   favoriteDAO.createAutoIncrement(f9);
				   Favorite f10 = new Favorite();
				   f10.setUrl("http://www.baidu.com");
				   f10.setComment("Chinese web");
				   f10.setUserId(u3.getId());
				   favoriteDAO.createAutoIncrement(f9);
				   Favorite f11 = new Favorite();
				   f11.setUrl("http://www.baidu.com");
				   f11.setComment("Chinese web");
				   f11.setUserId(u3.getId());
				   favoriteDAO.createAutoIncrement(f11);
				   Favorite f12 = new Favorite();
				   f12.setUrl("http://www.baidu.com");
				   f12.setComment("Chinese web");
				   f12.setUserId(u3.getId());
				   favoriteDAO.createAutoIncrement(f12);
				   
				}
		} catch (RollbackException e) {
			e.printStackTrace();
		}
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nextPage = performTheAction(request);
        sendToNextPage(nextPage,request,response);
    }
    
    /*
     * Extracts the requested action and (depending on whether the user is logged in)
     * perform it (or make the user login).
     * @param request
     * @return the next page (the view)
     */
    private String performTheAction(HttpServletRequest request) {
        HttpSession session     = request.getSession(true);
        String      servletPath = request.getServletPath();
        User        user = (User) session.getAttribute("user");
        String      action = getActionName(servletPath);


        if (action.equals("register.do") || action.equals("login.do") || action.equals("view.do")) {
			return Action.perform(action,request);
        }
        
        if (user == null) {
        	
			return Action.perform("list.do",request);
        }

		return Action.perform(action,request);
    }

    /*
     * If nextPage is null, send back 404
     * If nextPage ends with ".do", redirect to this page.
     * If nextPage ends with ".jsp", dispatch (forward) to the page (the view)
     *    This is the common case
     */
    private void sendToNextPage(String nextPage, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	if (nextPage == null) {
    		response.sendError(HttpServletResponse.SC_NOT_FOUND,request.getServletPath());
    		return;
    	}
    	// open url
    	if (nextPage.endsWith("redirect.jsp")) {
    		String parts[] = nextPage.split("\t");
    		response.sendRedirect(parts[0]);
    		return;
    	}
    		
    	
    	if (nextPage.endsWith(".do")) {
			response.sendRedirect(nextPage);
			return;
    	}
    	
    	if (nextPage.endsWith(".jsp")) {
	   		RequestDispatcher d = request.getRequestDispatcher("WEB-INF/" + nextPage);
	   		d.forward(request,response);
	   		return;
    	}
    	
    	if (nextPage.equals("image")) {
	   		RequestDispatcher d = request.getRequestDispatcher(nextPage);
	   		d.forward(request,response);
	   		return;
    	}
    	
    	throw new ServletException(Controller.class.getName()+".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
    }

	/*
	 * Returns the path component after the last slash removing any "extension"
	 * if present.
	 */
    private String getActionName(String path) {
    	// We're guaranteed that the path will start with a slash
        int slash = path.lastIndexOf('/');
        return path.substring(slash+1);
    }
}
