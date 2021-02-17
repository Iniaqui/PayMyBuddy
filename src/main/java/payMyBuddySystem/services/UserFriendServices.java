package payMyBuddySystem.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import payMyBuddySystem.DAO.UserDAO;
import payMyBuddySystem.DAO.UserFriendDAO;
import payMyBuddySystem.factory.DAOFactory;
import payMyBuddySystem.models.User;
import payMyBuddySystem.models.UserFriend;

@Service
public class UserFriendServices {
	UserDAO userDAO = new UserDAO();
	UserFriendDAO userFriendDAO = new UserFriendDAO();
	
	public boolean saveUserFriend(UserFriend userFriend,String mail,String mpd,String mailFriend) {
		boolean isSaved = false;
		//Obtenir l'id de l'ami 
		userFriend.setIdFriend(getUserFriend(mailFriend));
		// Check les informations de l'utilisation et identifier l'id user  
		userFriend.setIdUser(getUser(mail,mpd));
		isSaved = DAOFactory.getInstanceDAO("UserFriend").create(userFriend);
		return isSaved;
	}
	/*public HashMap<int,String> listeOfUserFriend(){
		
	}*/

	public ArrayList<String> getAllFriendByUser(String mailUser,String mdp) {
		// TODO Auto-generated method stub
		int idUser = getUser(mailUser,mdp);
		
		return userFriendDAO.getAllUserFriend(idUser);
	}

	public boolean deleteUserFriend(String idUserFriend) {
		// TODO Auto-generated method stub
		return false;
	}
	public int  getUserFriend(String mailFriend) {
	User u = userFriendDAO.getUserFriend(mailFriend);
			
	return u.getUserId(); 
	}
	public int getUser(String mail , String mdp) {
	User u = userDAO.getUserByMail(mail, mdp);
			
			return u.getUserId();
	}
}
