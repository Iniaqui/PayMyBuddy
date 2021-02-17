package payMyBuddySystem.DAO;

import java.util.List;

public interface DAO<DataObject> {
	
	boolean create(DataObject data);
	DataObject read(int i);
	boolean update(DataObject data);
	boolean delete(int id );

	List<DataObject> getAll();
}
