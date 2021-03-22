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

import payMyBuddySystem.models.Motif;
import payMyBuddySystem.security.GetUserToken;
import payMyBuddySystem.services.MotifServices;

@RestController
@RequestMapping("/Motifs")
public class MotifController {
	@Autowired
	MotifServices motifServices;
	@GetMapping("/user")
	
	public ArrayList<Motif> getAllMotif(HttpServletRequest request){
		String mailuser = request.getParameter("userMail") != null
							&& ! request.getParameter("userMail").isEmpty() ? request.getParameter("userMail")
									: null ;
		
		return motifServices.getAllMotifByUser(GetUserToken.getUserFromToken());
	}
	@PostMapping("/create")
	public ResponseEntity<Motif> create(@RequestBody Motif m){
		 boolean isCreated = motifServices.saveMotif(m) ;
		 if(isCreated) {
			 return  new ResponseEntity<>(HttpStatus.CREATED);
		 }
		 else {
			 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		 }
	}

}
