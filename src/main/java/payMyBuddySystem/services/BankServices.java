package payMyBuddySystem.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import payMyBuddySystem.DAO.BankDAO;
import payMyBuddySystem.DAO.TransactionDAO;
import payMyBuddySystem.DAO.UserDAO;
import payMyBuddySystem.factory.DAOFactory;
import payMyBuddySystem.models.Bank;
import payMyBuddySystem.models.User;
@Service
public class BankServices {
	UserDAO userDAO = new UserDAO();
	BankDAO bankDAO = new BankDAO();
	TransactionDAO transDAO= new  TransactionDAO ();
	public boolean createBank(Bank bank,String mail) {
		bank.setIdUser(findUser(mail));
		boolean isCreated =false;
		isCreated = DAOFactory.getInstanceDAO("Bank").create(bank);
		return isCreated;
	}
	private int findUser(String mail) {
		// TODO Auto-generated method stub
		User u = userDAO.getUserByMail(mail);
		
		return u.getUserId();
	}
	public boolean updateBank(Bank bank) {
		boolean isUpdated =false;
		isUpdated = DAOFactory.getInstanceDAO("Bank").update(bank);
		return isUpdated;
	}
	public boolean deleteBank(int id) {
		boolean isDeleted=false;
		isDeleted = DAOFactory.getInstanceDAO("Bank").delete(id);
		return isDeleted;
	}
	public Bank readBank(int id) {
		Bank bank = (Bank) DAOFactory.getInstanceDAO("Bank").read(id);
		return bank;
	}
	public ArrayList<Bank> getBankByMail(String mail) {
		// TODO Auto-generated method stub
		ArrayList<Bank> listeBankOfUser = bankDAO.getBankByMail(mail);
		return listeBankOfUser;
	}
	public boolean retrait(String username,Float ask) {//Retrait sur son compte PayMyBuddy
		
		return transDAO.retrait(username, ask);
	}
	public boolean depot(Float ask , String username) {//Depot sur son compte PayMyBuddy 
		return transDAO.depot(username, ask);
	}
}
