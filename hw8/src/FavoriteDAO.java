/**
 * 
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * Course: 08600
 * 
 */

import java.util.Comparator;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;



public class FavoriteDAO extends GenericDAO<FavoriteBean> {
	public FavoriteDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(FavoriteBean.class, tableName, cp);
	}
	

	public void updateCount(String id) throws RollbackException {
		try {
			Transaction.begin();
			System.out.println("update");
			FavoriteBean[] items = match(MatchArg.equals("id", Integer.parseInt(id)));
			System.out.println("url: " + items[0].getUrl());
			items[0].setClickCount(items[0].getClickCount() + 1);
			System.out.println("Click count: "  +items[0].getClickCount());
			update(items[0]);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
		
		
	}
	
	public void create(FavoriteBean item) throws RollbackException {
		try {
			Transaction.begin();
			createAutoIncrement(item);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
	
	public FavoriteBean[] getUserFavorites(int userId) throws RollbackException {
		 
		// Calls GenericDAO's match() method.
		// This no match constraint arguments, match returns all the Item beans 
		FavoriteBean[] items = match(MatchArg.equals("userId", userId));
        
        // Sort the list in click count order
        java.util.Arrays.sort(items, new Comparator<FavoriteBean>() {
        	public int compare(FavoriteBean item1, FavoriteBean item2) {
        		return item2.getClickCount() - item1.getClickCount();
        	}
		});
        
        return items;
	}

}
