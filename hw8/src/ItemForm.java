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

public class ItemForm {
	private String url;
	private String comment;
	
	public ItemForm(HttpServletRequest request) {
		url = request.getParameter("url");
		comment = request.getParameter("comment");
	}
	
	public String getURL()  { return url; }
    public String getComment()  { return comment; }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		
		if (url == null || url.length() == 0) {
			errors.add("URL is required");
		}	
		
		if (comment == null || comment.length() == 0) {
			errors.add("Comment is required");
		}	

		return errors;
	}


}
