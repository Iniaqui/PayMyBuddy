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
	
	public boolean saveUserFriend(UserFriend userFriend,String mail,String mailFriend) {
		boolean isSaved = false;
		//Obtenir l'id de l'ami 
		userFriend.setIdFriend(getUserFriend(mailFriend));
		// Check les informations de l'utilisation et identifier l'id user  
		userFriend.setIdUser(getUser(mail));
		System.out.println("idUser "+ userFriend.getIdUser() + "\n idFriend "+ userFriend.getIdFriend());
		isSaved = DAOFactory.getInstanceDAO("UserFriend").create(userFriend);
		return isSaved;
	}
	/*public HashMap<int,String> listeOfUserFriend(){
		
	}*/

	public ArrayList<String> getAllFriendByUser(String mailUser) {
		// TODO Auto-generated method stub
		int idUser = getUser(mailUser);
		
		return userFriendDAO.getAllUserFriend(idUser);
	}

	public boolean deleteUserFriend(String username , String mailFriend) {
		
		User u  = userDAO.getUserByMail(username);
		User friend = userDAO.getUserByMail(mailFriend);
		// TODO Auto-generated method stub
		return userFriendDAO.deletUserFriend(u.getUserId(), friend.getUserId());
	}
	public int  getUserFriend(String mailFriend) {
	User u = userDAO.getUserByMail(mailFriend);
			
	return u.getUserId(); 
	}
	public int getUser(String mail) {
	User u = userDAO.getUserByMail(mail);
			
			return u.getUserId();
	}
}
