package model;
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
import databeans.Favorite;

public class FavoriteDAO extends GenericDAO<Favorite> {
	public FavoriteDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(Favorite.class, tableName, pool);
	}



	public void updateCount(int id) throws RollbackException {
		try {
			Transaction.begin();
			System.out.println("update");
			Favorite[] items = match(MatchArg.equals("id", id));
			items[0].setClickCount(items[0].getClickCount() + 1);
			update(items[0]);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
		
		
	}
	
	public void create(Favorite item) throws RollbackException {
		try {
			Transaction.begin();
			createAutoIncrement(item);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
	
	public Favorite[] getUserFavorites(int userId) throws RollbackException {
		Favorite[] items = null; 
		try {
			Transaction.begin();
		// Calls GenericDAO's match() method.
		// This no match constraint arguments, match returns all the Item beans 
			items = match(MatchArg.equals("userId", userId));
        
        // Sort the list in click count order
		
			
        java.util.Arrays.sort(items, new Comparator<Favorite>() {
        	public int compare(Favorite item1, Favorite item2) {
        		return item2.getClickCount() - item1.getClickCount();
        	}
		});
			 Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}       
		 return items;
	}

	public void delete(int id, int owner) throws RollbackException {
		try {
			System.out.println("FavoriteDao: begin");
			Transaction.begin();
    		Favorite f = read(id);

    		if (f == null) {
				throw new RollbackException("Photo does not exist: id="+id);
    		}

    		if (owner != f.getUserId()) {
				throw new RollbackException("Photo not owned by "+owner);
    		}
    		System.out.println("FavoriteDao: before delete" + id);
			delete(id);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
	
}
