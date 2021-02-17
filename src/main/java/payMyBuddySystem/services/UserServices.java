package payMyBuddySystem.services;


import java.util.List;
import java.util.Objects;

import javax.security.auth.message.AuthException;


import org.springframework.stereotype.Service;

import payMyBuddySystem.DAO.UserDAO;
import payMyBuddySystem.factory.DAOFactory;
import payMyBuddySystem.models.User;
import payMyBuddySystem.models.UserModel;
import payMyBuddySystem.security.SecurityConfig;

@Service
public class UserServices   {
	UserDAO userDAO = new UserDAO();
	public boolean saveUser(User u ) throws Exception {
		u.setMdp(SecurityConfig.getSaltedHash(u.getMdp()));
		boolean isSaved = DAOFactory.getInstanceDAO("User").create(u);
		return isSaved;
		
	}

	public User getUserByMailAndPass(String mail,String mdp) {
		// TODO Auto-generated method stub
		User u= userDAO.getUserByMail(mail,mdp);
		return u;
	}

	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		
		boolean isUpdate = DAOFactory.getInstanceDAO("User").update(user);
		return isUpdate;
	}

	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		List<User> listeUser = DAOFactory.getInstanceDAO("User").getAll();
		return listeUser;
	}

	public boolean deleteUser(int id) {
		// TODO Auto-generated method stub
		
		boolean isDeleted = DAOFactory.getInstanceDAO("User").delete(id);
		return isDeleted;
	}


	
}

