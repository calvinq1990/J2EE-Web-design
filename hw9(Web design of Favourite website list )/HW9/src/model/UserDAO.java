package model;
/**
 * 
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * Course: 08600
 * 
 */
import java.util.Arrays;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.User;


public class UserDAO extends GenericDAO<User> {
	
	public UserDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(User.class, tableName, pool);
	}

	public User[] getUsers() throws RollbackException {
		User[] users = match();
		Arrays.sort(users); 
		return users;
	}
	
	public void setPassword(int id, String password) throws RollbackException {
        try {
        	Transaction.begin();
        	System.out.println("UserDAO: reset password before read");
			User dbUser = read(id);
			System.out.println("UserDAO: reset password after read");
			
			if (dbUser == null) {
				throw new RollbackException("User "+ id +" no longer exists");
			}
			
			System.out.println("UserDAO: reset password " + dbUser.getEmailAddr());
			
			dbUser.setPassword(password);
			
			update(dbUser);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
	
	public User read(String emailAddr) throws RollbackException {
		try {
			Transaction.begin();
			
			// Get item at top of list
			User[] a = match(MatchArg.equals("emailAddr", emailAddr));

			User bean;
			if (a.length == 0) {
				bean = null;
				System.out.println("no users");
			} else {
				bean = a[0];
			}
			Transaction.commit();
			return bean;

		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
	
	public void create(User user) throws RollbackException {
		try {
			Transaction.begin();
			createAutoIncrement(user);
		
			Transaction.commit();
		

		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
		
	}
}
