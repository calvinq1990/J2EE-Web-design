/**
 * 
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * Course: 08600
 * 
 */

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;


public class UserDAO extends GenericDAO<UserBean> {
	public UserDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(UserBean.class, tableName, cp);
	}

	public UserBean read(String emailAddr) throws RollbackException {
		try {
			Transaction.begin();
			
			// Get item at top of list
			UserBean[] a = match(MatchArg.equals("emailAddr", emailAddr));

			UserBean bean;
			if (a.length == 0) {
				bean = null;
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
	
	public void create(UserBean user) throws RollbackException {
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

