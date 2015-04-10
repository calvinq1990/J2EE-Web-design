/**
 * 
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * Course: 08600
 * 
 */

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class UserBean {
	private int id;
    private String password;
    
    private String firstName;
    private String lastName;
    private String emailAddr;

    public int getId()	{ return id; }
    public String getPassword()	{ return password; }


    public String getFirstName()	{return firstName;}
    public String getLastName()	{return lastName;}
    public String getEmailAddr()	{return emailAddr;}
    
    public void setId(int i)	{id = i;}
    public void setPassword(String s)	{ password = s;    }
    public void setFirstName(String s)	{firstName = s;}
    public void setLastName(String s)	{lastName = s;}
    public void setEmailAddr(String s)	{emailAddr = s;}
}
