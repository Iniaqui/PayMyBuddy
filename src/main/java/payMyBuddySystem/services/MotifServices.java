package payMyBuddySystem.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import payMyBuddySystem.DAO.MotifDAO;
import payMyBuddySystem.factory.DAOFactory;
import payMyBuddySystem.models.Motif;
@Service
public class MotifServices {
 public boolean saveMotif(Motif motif) {
	 
	 boolean isSaved =false;
	 isSaved = DAOFactory.getInstanceDAO("Motif").create(motif);
	 return isSaved;
 }
 public Motif readMotif(int  i) {
	 	return (Motif) DAOFactory.getInstanceDAO("Motif").read(i);
 }
 
 public ArrayList<Motif> getAllMotifByUser(String userMail) {
	 MotifDAO motifDAO = new MotifDAO();
	 
	 return motifDAO.getAllMotifByMail(userMail);
 }
 
}
