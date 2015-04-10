/**
 * 
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * Course: 08600
 * 
 */


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;



public class HW8 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private FavoriteDAO itemDAO;
	private UserDAO userDAO;

	public void init() throws ServletException {
		String jdbcDriverName = getInitParameter("jdbcDriver");
		String jdbcURL = getInitParameter("jdbcURL");

		try {
			ConnectionPool cp = new ConnectionPool(jdbcDriverName, jdbcURL);
			userDAO = new UserDAO(cp, "jiaqiluo_user");
			itemDAO = new FavoriteDAO(cp, "jiaqiluo_favorite");
		} catch (DAOException e) {
			throw new ServletException(e);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			login(request, response);
		} else {
			try {
				manageList(request, response);
			} catch (RollbackException e) {
				e.printStackTrace();
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();
		UserBean user;
		
		if (request.getParameter("button") != null
				&& request.getParameter("button").equals("RegisterLink")) {

			outputRegisterPage(response, null);

			return;
		}

		try {
			if (request.getParameter("button") != null
					&& request.getParameter("button").equals("Register")) {
//				System.out.println("Register");
				RegisterForm form = new RegisterForm(request);

				errors.addAll(form.getValidationErrors());
				if (errors.size() != 0) {
					outputRegisterPage(response, errors);
					return;
				}

				user = new UserBean();
				user.setFirstName(form.getFirstName());
				user.setLastName(form.getLastName());
				user.setEmailAddr(form.getEmailAddress());
				user.setPassword(form.getPassword());

				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				userDAO.create(user);
				manageList(request, response);

				return;
			}
		} catch (Exception e) {
			errors.add(e.getMessage());
			outputRegisterPage(response, errors);
			return;

		}

		LoginForm form = new LoginForm(request);

		if (!form.isPresent()) {
			outputLoginPage(response, form, null);
			return;
		}

		errors.addAll(form.getValidationErrors());
		if (errors.size() != 0) {
			outputLoginPage(response, form, errors);
			return;
		}

		try {
			if (form.getButton().equals("Login")) {

				user = userDAO.read(form.getEmailAddr());
				if (user == null) {
					errors.add("No such user");
					outputLoginPage(response, form, errors);
					return;
				}

				if (!form.getPassword().equals(user.getPassword())) {
					errors.add("Incorrect password");
					outputLoginPage(response, form, errors);
					return;
				}

				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				manageList(request, response);
			}
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			outputLoginPage(response, form, errors);
		}



	}

	private void manageList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			RollbackException {
		// Look at the action parameter to see what we're doing to the list
		String action = request.getParameter("action");

		if (action == null) {
			outputFavoriteList(request, response);
			return;
		}



		if (action.equals("Logout")) {
			request.getSession().setAttribute("user", null);
			this.outputLoginPage(response, null, null);
			return;
		}

		if (action.equals("Add Favorite")) {
			processAdd(request, response, true);
			return;
		}
		
		if (request.getParameter("action") != null
				&& request.getParameter("action").equals("click")) {
			String id = request.getParameter("id");
			itemDAO.updateCount(id);
			outputFavoriteList(request, response, "Clicked");
			return;

		}

		outputFavoriteList(request, response, "No such operation: " + action);
	}

	private void processAdd(HttpServletRequest request,
			HttpServletResponse response, boolean addToTop)
			throws ServletException, IOException, RollbackException {
		List<String> errors = new ArrayList<String>();

		ItemForm form = new ItemForm(request);

		errors.addAll(form.getValidationErrors());
		if (errors.size() > 0) {
			outputFavoriteList(request, response, errors);
			return;
		}
		UserBean u = (UserBean) request.getSession().getAttribute("user");

		FavoriteBean bean = new FavoriteBean();
		bean.setComment(form.getComment());
		bean.setUrl(form.getURL());
		bean.setUserId(u.getId());



		itemDAO.create(bean);
		outputFavoriteList(request, response, "Favorite Page Added");

	}

	private void generateHead(PrintWriter out) {
		out.println("  <head>");
		out.println("    <meta charset=\"utf-8\"/>");
		out.println("    <title>To Do List Login</title>");

		out.println("	<style>");
		out.println("		table, th, td {");
		out.println("		border: 1px solid black;");
		out.println("		}");
		out.println("	</style>");
		out.println("  </head>");
	}

	private void outputRegisterPage(HttpServletResponse response,
			List<String> errors) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");

		generateHead(out);

		if (errors != null && errors.size() > 0) {
			for (String error : errors) {
				out.println("<p style=\"font-size: large; color: red\">");
				out.println(error);
				out.println("</p>");
			}
		}

		out.println("<body>");
		out.println("<h2>Favorite Web Page List Register</h2>");
		out.println("<form method=\"POST\" action=\"?\" >");
		out.println("    <table>");
		out.println("        <tr>");
		out.println("            <td style=\"font-size: x-large\">E-mail Address:</td>");
		out.println("            <td><input type=\"text\" name=\"emailAddr\" /></td>");
		out.println("        <tr>");
		out.println("        <tr>");
		out.println("            <td style=\"font-size: x-large\">First Name:</td>");
		out.println("            <td><input type=\"text\" name=\"firstName\" /></td>");
		out.println("        <tr>");
		out.println("        <tr>");
		out.println("            <td style=\"font-size: x-large\">Last Name:</td>");
		out.println("            <td><input type=\"text\" name=\"lastName\" /></td>");
		out.println("        <tr>");
		out.println("        <tr>");
		out.println("            <td style=\"font-size: x-large\">Password:</td>");
		out.println("            <td><input type=\"password\" name=\"password\" /></td>");
		out.println("        <tr>");
		out.println("            <td colspan=\"2\" style=\"text-align: center;\">");

		out.println("             <input type=\"submit\" name=\"button\" value=\"Register\" /></td>");
		out.println("        <tr>");
		out.println("    <table>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");

	}

	private void outputLoginPage(HttpServletResponse response, LoginForm form,
			List<String> errors) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<!DOCTYPE html>");
		out.println("<html>");

		generateHead(out);

		out.println("<body>");
		out.println("<h2>Favorite Web Page List Login</h2>");

		if (errors != null && errors.size() > 0) {
			for (String error : errors) {
				out.println("<p style=\"font-size: large; color: red\">");
				out.println(error);
				out.println("</p>");
			}
		}

		// Generate an HTML <form> to get data from the user
		out.println("<form method=\"POST\" action=\"?\">");
		out.println("    <table>");
		out.println("        <tr>");
		out.println("            <td style=\"font-size: x-large\">E-mail Address:</td>");
		out.println("            <td>");
		out.println("                <input type=\"text\" name=\"emailAddr\"");

		out.println("                />");
		out.println("            </td>");
		out.println("        </tr>");
		out.println("        <tr>");
		out.println("            <td style=\"font-size: x-large\">Password:</td>");
		out.println("            <td><input type=\"password\" name=\"password\" /></td>");
		out.println("        </tr>");
		out.println("        <tr>");
		out.println("            <td colspan=\"2\" style=\"text-align: center;\">");
		out.println("                <input type=\"submit\" name=\"button\" value=\"Login\" />");

		out.println("            </td>");
		out.println("        </tr>");
		out.println("    </table>");

		out.println("</form>");
		out.println("<a href=\"?button=RegisterLink\">Click here to register.</a> ");
		out.println("</body>");
		out.println("</html>");
	}

	private void outputFavoriteList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<String> list = new ArrayList<String>();
		outputFavoriteList(request, response, list);
	}

	private void outputFavoriteList(HttpServletRequest request,
			HttpServletResponse response, String message) throws IOException {
		List<String> list = new ArrayList<String>();
		list.add(message);
		outputFavoriteList(request, response, list);
	}

	private void outputFavoriteList(HttpServletRequest request,
			HttpServletResponse response, List<String> messages)
			throws IOException {
		FavoriteBean[] beans;
		try {
			UserBean user = (UserBean) request.getSession()
					.getAttribute("user");
			beans = itemDAO.getUserFavorites(user.getId());

			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();

			out.println("<!DOCTYPE html>");
			out.println("<html>");

			generateHead(out);

			out.println("<body>");
			out.println("<h2>Favorites for" + " " + user.getFirstName() + " "
					+ user.getLastName() + "</h2>");

			// Generate an HTML <form> to get data from the user
			out.println("<form method=\"POST\" action=\"?\">");
			out.println("        <p><td colspan=\"3\"><hr/></td>");

			out.println("        </p>");
			out.println("        <p>");
			out.println("            <td style=\"font-size: large\">Item to Add:</td>");

			out.println("        </p>");
			out.println("    <table>");

			out.println("            <td style=\"font-size: large\">URL:</td>");
			out.println("            <td colspan=\"2\"><input type=\"text\" size=\"40\" name=\"url\"/></td>");
			out.println("        </tr>");
			out.println("        </tr>");
			out.println("            <td style=\"font-size: large\">Comment:</td>");
			out.println("            <td colspan=\"2\"><input type=\"text\" size=\"40\" name=\"comment\"/></td>");
			out.println("        </tr>");

			out.println("        <tr>");
			out.println("            <td colspan=\"2\" style=\"text-align: center;\">");
			out.println("            <input type=\"submit\" name=\"action\" value=\"Add Favorite\"/>");

			out.println("            <input type=\"submit\" name=\"action\" value=\"Logout\"/></td>");

			out.println("    </table>");
			out.println("    </p>");
			out.println("        <td colspan=\"10\"><hr/></td></p>");
			out.println("</form>");

			for (String message : messages) {
				out.println("<p style=\"font-size: large; color: red\">");
				out.println(message);
				out.println("</p>");
			}

			out.println("<p style=\"font-size: x-large\">The list now has "
					+ beans.length + " items.</p>");
			for (int i = 0; i < beans.length; i++) {
				out.println("    <p>");

				out.println("		<td><a href=\"?action=click&id="
						+ beans[i].getId() + "\">" + beans[i].getUrl()
						+ "			</a></td> ");

				out.println("    </p>");

				out.println("    <p>");
				out.println("        <td><span style=\"font-size: large\">"
						+ beans[i].getComment() + "</td>");
				out.println("    </p>");

				out.println("    <p>");
				out.println("        <td><span style=\"font-size: large\">"
						+ beans[i].getClickCount() + " Clicks" + "</td>");
				out.println("    </p>");
				out.println("    <p>");
				out.println("    	<td>");
				out.println("    		<br>");
				out.println("    	</td>");
				out.println("    </p>");
			}

			out.println("</body>");
			out.println("</html>");

		} catch (RollbackException e) {
			messages.add(e.getMessage());
			beans = new FavoriteBean[0];
		}

	}
}
