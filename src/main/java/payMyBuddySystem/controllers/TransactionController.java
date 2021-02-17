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

import payMyBuddySystem.models.Transaction;
import payMyBuddySystem.services.TransactionServices;


@RestController
@RequestMapping("/transactions")
public class TransactionController {
	@Autowired
	TransactionServices transactionServices;
	
	@PostMapping("/create")
	public ResponseEntity<Transaction> createTransaction(HttpServletRequest request, @RequestBody Transaction transaction) {
		String mail = request.getParameter("mail") != null && !request.getParameter("mail").isEmpty()
				? request.getParameter("mail")
				: null;
		String mdp = request.getParameter("mdp") != null && !request.getParameter("mdp").isEmpty()
				? request.getParameter("mdp")
				: null;
		
	
		boolean result=transactionServices.saveTransaction(mail,mdp,transaction);
		if(result) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
	}
	@GetMapping
	public ArrayList<Transaction> getAllTransaction(HttpServletRequest request){
		String mailUser = request.getParameter("mailUser")!= null 
							&& ! request.getParameter("mailUser").isEmpty() ? request.getParameter("mailUser")
									:null;
		String mdp = request.getParameter("mdp") != null && !request.getParameter("mdp").isEmpty()
				? request.getParameter("mdp")
				: null;
		return transactionServices.getAllTransactionByUser(mailUser,mdp);
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
