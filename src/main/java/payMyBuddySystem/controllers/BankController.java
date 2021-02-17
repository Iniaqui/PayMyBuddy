package payMyBuddySystem.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import payMyBuddySystem.models.Bank;
import payMyBuddySystem.services.BankServices;

@RestController
@RequestMapping("/banks")
public class BankController {
	@Autowired 
	BankServices bankServices;
	
	@PostMapping("/ajout")
	public ResponseEntity<Bank> ajouterBank(@RequestBody Bank b,HttpServletRequest request){
		String mail = request.getParameter("mail")!=null
				&& !request.getParameter("mail").isEmpty() ? request.getParameter("mail") 
						: null;
		String mdp = request.getParameter("mdp")!=null
				&& !request.getParameter("mdp").isEmpty() ? request.getParameter("mdp") 
						: null;
		
		boolean isCreated = bankServices.createBank(b,mail,mdp);
		if(isCreated) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/user")
	public ArrayList<Bank> getUserBank(HttpServletRequest request){
		String mail = request.getParameter("mail")!=null
				&& !request.getParameter("mail").isEmpty() ? request.getParameter("mail") 
						: null;
		String mdp = request.getParameter("mdp")!=null
				&& !request.getParameter("mdp").isEmpty() ? request.getParameter("mdp") 
						: null;
		return bankServices.getBankByMail(mail,mdp);
	}

}
