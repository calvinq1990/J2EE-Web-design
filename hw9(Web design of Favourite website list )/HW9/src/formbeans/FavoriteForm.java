package formbeans;
/**
 * 
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * Course: 08600
 * 
 */
import java.util.ArrayList;
import org.mybeans.form.FormBean;

public class FavoriteForm extends FormBean  {
	private String button     = "";

	
	private String url     = "";
	private String comment    = "";
	

	
	public String       getButton()         { return button;         }
	public String 		getUrl()            { return url;           }
	public String       getComment()        { return comment;        }

	public void setButton(String s)         { button      = s;        }
	public void setUrl(String s)        { url     = trimAndConvert(s,"<>\""); }
	public void setComment(String s)  {comment     = trimAndConvert(s,"<>\"");     }
	
	public ArrayList<String> getValidationErrors() {
		ArrayList<String> errors = new ArrayList<String>();
		
		
		if (url == null || url.length() == 0) {
			errors.add("URL is empty");
			return errors;
		}
		
		if (comment == null || comment.length() == 0) {
			errors.add("Comment is empty");
			return errors;
		}
		
		
		return errors;
	}
}
