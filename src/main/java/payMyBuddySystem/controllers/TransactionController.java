package payMyBuddySystem.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import payMyBuddySystem.models.Transaction;
import payMyBuddySystem.services.TransactionServices;


@RestController
@RequestMapping("/transactions")
public class TransactionController {
	@Autowired
	TransactionServices transactionServices;
	
	@PostMapping("/create")
	public ResponseEntity<Transaction> createTransaction(HttpServletRequest request, @RequestBody Transaction transaction) {
		/*String mail = request.getParameter("mail") != null && !request.getParameter("mail").isEmpty()
				? request.getParameter("mail")
				: null;
		String mdp = request.getParameter("mdp") != null && !request.getParameter("mdp").isEmpty()
				? request.getParameter("mdp")
				: null;
		*/
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
		   username = ((UserDetails)principal).getUsername();
		} else {
		   username = principal.toString();
		}
		System.out.println(username);
		boolean result=transactionServices.saveTransaction(username,transaction);
		if(result) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
	}
	@GetMapping
	public ArrayList<Transaction> getAllTransaction(HttpServletRequest request){
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
		   username = ((UserDetails)principal).getUsername();
		} else {
		   username = principal.toString();
		}
		System.out.println(username);
		
		return transactionServices.getAllTransactionByUser(username);
	}
	@GetMapping("/transaction")
	public Transaction getTransactionById(HttpServletRequest request) {
		String idString = request.getParameter("id")!=null
							&& !request.getParameter("id").isEmpty() ? request.getParameter("id")
									:null;
		int id = Integer.parseInt(idString);
		Transaction trans = transactionServices.getTransaction(id);
		return trans;
	}
	
}
