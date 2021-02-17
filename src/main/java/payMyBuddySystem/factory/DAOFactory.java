package payMyBuddySystem.factory;

import payMyBuddySystem.DAO.BankDAO;
import payMyBuddySystem.DAO.DAO;
import payMyBuddySystem.DAO.MotifDAO;
import payMyBuddySystem.DAO.TransactionDAO;
import payMyBuddySystem.DAO.UserDAO;
import payMyBuddySystem.DAO.UserFriendDAO;

public class DAOFactory {
	public static DAO getInstanceDAO(String className ) {
		switch(className) {
		case "User" :{
			return new UserDAO();
		}
		case "Bank" :{
			return new BankDAO();
		}
		case "Motif" :{
			return new MotifDAO();
		}
		case "Trans" : {
			return new TransactionDAO();
		}
		case "UserFriend" :{
			return new UserFriendDAO();
		}
		default :{
			return null;
		}
		}
			
	
		
	}
}
