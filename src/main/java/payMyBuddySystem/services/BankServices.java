package payMyBuddySystem.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import payMyBuddySystem.DAO.BankDAO;
import payMyBuddySystem.DAO.UserDAO;
import payMyBuddySystem.factory.DAOFactory;
import payMyBuddySystem.models.Bank;
import payMyBuddySystem.models.User;
@Service
public class BankServices {
	UserDAO userDAO = new UserDAO();
	BankDAO bankDAO = new BankDAO();
	public boolean createBank(Bank bank,String mail,String mdp) {
		bank.setIdUser(findUser(mail,mdp));
		boolean isCreated =false;
		isCreated = DAOFactory.getInstanceDAO("Bank").create(bank);
		return isCreated;
	}
	private int findUser(String mail, String mdp) {
		// TODO Auto-generated method stub
		User u = userDAO.getUserByMail(mail, mdp);
		
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
	public ArrayList<Bank> getBankByMail(String mail,String mdp) {
		// TODO Auto-generated method stub
		ArrayList<Bank> listeBankOfUser = bankDAO.getBankByMail(mail,mdp);
		return listeBankOfUser;
	}
	
}
