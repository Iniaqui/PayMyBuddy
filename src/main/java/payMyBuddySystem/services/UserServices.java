package payMyBuddySystem.services;


import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import payMyBuddySystem.DAO.UserDAO;
import payMyBuddySystem.factory.DAOFactory;
import payMyBuddySystem.models.User;
import payMyBuddySystem.security.SecurityConfig;
import payMyBuddySystem.security.WebSecurityConfig;

@Service
public class UserServices   implements UserDetailsService{
	UserDAO userDAO = new UserDAO();
	
	public boolean saveUser(User u ) throws Exception {
		//u.setMdp(SecurityConfig.getSaltedHash(u.getMdp()));
		u.setMdp( WebSecurityConfig.passwordEncoder().encode(u.getMdp()));
		
		boolean isSaved = DAOFactory.getInstanceDAO("User").create(u);
		return isSaved;
		
	}

	public User getUserByMailAndPass(String mail,String mdp) {
		// TODO Auto-generated method stub
		User u= userDAO.getUserByMail(mail,mdp);
		if(u!=null) {
			System.out.println("Je retourne une valeur non nulle");
		}
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userDAO.getUserByMail(username);
		
		        if(user == null)
		
		        {
		
		            throw new UsernameNotFoundException(username);
		
		        }
		
		        return new org.springframework.security.core.userdetails.User(user.getMail(), user.getMdp(), Collections.emptyList());
		
	}

	public User getUserByMail(String username) {
		// TODO Auto-generated method stub
		return userDAO.getUserByMail(username);
	}


	
}

