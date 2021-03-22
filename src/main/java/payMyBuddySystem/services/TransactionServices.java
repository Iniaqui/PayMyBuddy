package payMyBuddySystem.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import payMyBuddySystem.DAO.TransactionDAO;
import payMyBuddySystem.DAO.UserDAO;
import payMyBuddySystem.factory.DAOFactory;
import payMyBuddySystem.models.Transaction;
import payMyBuddySystem.models.User;
@Service
public class TransactionServices {
	TransactionDAO transDAO= new TransactionDAO();
	UserDAO userDAO = new UserDAO();
	public boolean saveTransaction(String mail,Transaction trans) {
		
		boolean isSaved = false;
		User u= userDAO.getUserByMail(mail);
		User receiver = userDAO.getUserByMail(trans.getMailReceiver());
		if(u!= null && receiver != null) {
			trans.setIdReceiver(receiver.getUserId());
			
			trans.setIdSender(u.getUserId());
			trans.setFees(calculateFees(trans));
			System.out.println("Sender "+ trans.getIdSender());
			System.out.println("Receiver "+ trans.getIdReceiver());
			System.out.println("IdMotif" + trans.getIdMotif());
			isSaved = DAOFactory.getInstanceDAO("Trans").create(trans);
		}
		
		return isSaved;
	}
	public Transaction getTransaction(int id) {
		return (Transaction) DAOFactory.getInstanceDAO("Trans").read(id);
	}
	public ArrayList<Transaction> getAllTransactionByUser(String mailUser) {
		// TODO Auto-generated method stub
		User u = userDAO.getUserByMail(mailUser);
		ArrayList<Transaction > listeTransactions = new ArrayList<Transaction>();
		if(u!=null) {
			 listeTransactions = transDAO.readByMail(u);
		}
		
		return listeTransactions;
	}
	public float calculateFees(Transaction trans) {
		float balanceTransaction = trans.getAmount();
		float fees = (float) ((balanceTransaction *0.5)/100);
		return fees;
	}
	
}
