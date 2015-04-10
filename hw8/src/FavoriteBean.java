/**
 * 
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * Course: 08600
 * 
 */


import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class FavoriteBean {
	private int  id;
	private int userId;
	private String url;
	private String comment;
	private int clickCount;
	
	public int    getId()	{ return id;           }
	public int    getUserId()	{ return userId;           }
    public int getClickCount()	{return clickCount;}
    public String getComment()	{return comment;}
    public String getUrl() {return url;}
    
    public void   setId(int i)	{ id = i;              }
    public void   setUserId(int i)	{ userId = i;      }
    public void	setClickCount(int i)	{clickCount = i;}
    public void setComment(String s)	{comment = s;}
    public void setUrl(String s)	{url = s;}

}
