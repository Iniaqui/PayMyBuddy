package payMyBuddySystem.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import payMyBuddySystem.models.UserFriend;
import payMyBuddySystem.services.UserFriendServices;

@RestController
public class  UserFriendController {
	@Autowired
	UserFriendServices userFriendServices;
	
	@PostMapping("/userFriend/create")
	public ResponseEntity<UserFriend> createUserFriend(HttpServletRequest request ) {
		String mail = request.getParameter("mail")!=null
				&& !request.getParameter("mail").isEmpty() ? request.getParameter("mail") 
						: null;
		String mdp = request.getParameter("mdp")!=null
				&& !request.getParameter("mdp").isEmpty() ? request.getParameter("mdp") 
						: null;
		String mailFriend = request.getParameter("mailFriend")!=null
				&& !request.getParameter("mailFriend").isEmpty() ? request.getParameter("mailFriend") 
						: null;
			
		
		UserFriend newUserFriend= new UserFriend();
		boolean result  = userFriendServices.saveUserFriend(newUserFriend,mail,mdp,mailFriend);
		if(result) {
			return new ResponseEntity<>(HttpStatus.CREATED);
			
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/userFriend")
	public ArrayList<String> getAllUserFriendByUser(HttpServletRequest request){
		String mailUser = request.getParameter("mailUser") != null
							&& ! request.getParameter("mailUser").isEmpty() ? request.getParameter("mailUser")
								:null;
		String mdp = request.getParameter("mdp")!=null
				&& !request.getParameter("mdp").isEmpty() ? request.getParameter("mdp") 
						: null;
		
		return  userFriendServices.getAllFriendByUser(mailUser, mdp);				
		
	}
	
	@DeleteMapping("/userFriend/delete")
	public ResponseEntity<UserFriend> deleteUserFriend(HttpServletRequest request){
		String mailUser =request.getParameter("mailUser") != null
				&& ! request.getParameter("mailUser").isEmpty() ? request.getParameter("mailUser")
						:null;
				
		String idUserFriend =request.getParameter("idUserFriend") != null
				&& ! request.getParameter("idUserFriend").isEmpty() ? request.getParameter("idUserFriend")
						:null;
		
		boolean result = userFriendServices.deleteUserFriend(idUserFriend);
		if(result) {
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
