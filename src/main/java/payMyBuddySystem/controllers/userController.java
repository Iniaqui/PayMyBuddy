package payMyBuddySystem.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import payMyBuddySystem.models.User;
import payMyBuddySystem.models.UserModel;
import payMyBuddySystem.security.GetUserToken;
import payMyBuddySystem.services.UserServices;

@RestController
@RequestMapping("/user")
public class userController {
	@Autowired
	UserServices userServices;
 
	@PostMapping(value = "/create")
	public ResponseEntity<User> createUser(@RequestBody User user) throws Exception {
		User newUser = null;
		boolean result = userServices.saveUser(user);
		System.out.println(result);
		if (result) {

			return new ResponseEntity<>(user ,HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/update")
	public ResponseEntity<User> updateUser(HttpServletRequest request, @RequestBody User u) {
		String username = GetUserToken.getUserFromToken();
		System.out.println(username);
		User newUser = null;
		boolean result = userServices.updateUser(u);
		if (result) {
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(newUser, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<User> deleteUser(HttpServletRequest request) {
		String username = GetUserToken.getUserFromToken();
		System.out.println(username);
		boolean result = false;
		/*String mail = request.getParameter("mail") != null && !request.getParameter("mail").isEmpty()
				? request.getParameter("mail")
				: null;
		String mdp = request.getParameter("mdp") != null && !request.getParameter("mdp").isEmpty()
				? request.getParameter("mdp")
				: null;*/
		User u = userServices.getUserByMail(username);
		if (u != null) {
			System.out.println(u.getUserId());
			result = userServices.deleteUser(u.getUserId());
			if (result) {
				return new ResponseEntity<>(HttpStatus.ACCEPTED);

			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else {
			System.out.println("Echec");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/user")
	@ResponseBody
	public User getUser(HttpServletRequest request) {
		String username = GetUserToken.getUserFromToken();
		System.out.println(username);
		/*String mail = request.getParameter("mail")!=null
				&& !request.getParameter("mail").isEmpty() ? request.getParameter("mail") 
						: null;
		String mdp = request.getParameter("mdp")!=null
				&& ! request.getParameter("mdp").isEmpty() ? request.getParameter("mdp") 
				: null;*/
		return userServices.getUserByMail(username);
	}

	@GetMapping
	@ResponseBody
	public List<User> getAllUser(HttpServletRequest request) {
		System.out.println("Passe");
		return this.userServices.getAllUser();

	}

}
