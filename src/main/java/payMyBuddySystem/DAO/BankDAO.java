package payMyBuddySystem.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import payMyBuddy.config.DataBaseConfig;
import payMyBuddySystem.constants.DBConstants;
import payMyBuddySystem.models.Bank;
import payMyBuddySystem.security.SecurityConfig;


public class BankDAO  implements DAO<Bank>{
	DataBaseConfig dataBaseConfig = new DataBaseConfig();
	
	public boolean create(Bank data) {
		boolean res = false;
		Connection con = null;
		try {
			con= dataBaseConfig.getConnection();
			PreparedStatement ps= con.prepareStatement(DBConstants.SAVE_BANK);//INSERT INTO bank(idUser,iban ,swift,acount) VALUES (?,?,?,?)
			ps.setInt(1, data.getIdUser());
			ps.setString(2, data.getIban());
			ps.setString(3, data.getSwift());
			ps.setFloat(4, data.getAcount());
			
			int ligne  = ps.executeUpdate();
			if(ligne>0) {
				res = true;
			}
			dataBaseConfig.closePreparedStatement(ps);
			return res;
		}catch(Exception e){
			System.out.println("Error in this creating bank");
		}finally {
			dataBaseConfig.closeConnection(con);
		}
		return res;
		// TODO Auto-generated method stub
		
	}

	public Bank read(int i) {
		// TODO Auto-generated method stub
		Bank bank= null;
		Connection  con = null;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps =  con.prepareStatement(DBConstants.READ_BANK);
			ps.setInt(1, i);
			ResultSet res =ps.executeQuery();
			if(res.next()) {
				bank = new Bank();
				bank.setAcount(res.getFloat(4));
				bank.setIban(res.getString(2));
				bank.setSwift(res.getString(3));
			}
			dataBaseConfig.closePreparedStatement(ps);
			return bank;
			
		}catch(Exception e) {
			System.out.println("Error in this ");
		}finally {
			dataBaseConfig.closeConnection(con);
			
		}
		
		return bank;
		
	}

	public boolean update(Bank data) {
		// TODO Auto-generated method stub
		Connection con = null;
		boolean res = false;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.UPDATE_BANK);
			ps.setString(1, data.getIban());
			ps.setString(2, data.getSwift());
			ps.setFloat(3,data.getAcount());
			res = ps.execute();
			dataBaseConfig.closePreparedStatement(ps);
		}catch(Exception e ){
			System.out.println("Error in updating of bank");
		}finally {
			
			dataBaseConfig.closeConnection(con);
		}
		return res;
		
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		Connection con = null;
		boolean res = false;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.DELETE_BANK);
			ps.setInt(1, id);	
			res = ps.execute();
			dataBaseConfig.closePreparedStatement(ps);
		}catch(Exception e){
			System.out.println("Error in deleting of data");
		}finally {
			dataBaseConfig.closeConnection(con);
		}
		
		return res;
		
	}

	
	public List<Bank> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Bank> getBankByMail(String mail,String mdp) {
		Connection con = null;
		ArrayList<Bank> bankOfUser= new ArrayList<Bank>();
		try {
			con= dataBaseConfig.getConnection();
			// Verifications du compte 
			PreparedStatement ps= con.prepareStatement(DBConstants.READ_USER_BY_MAIL);//SELECT idUser,mail,balance,step FROM users WHERE mail = ? and mdp=?
			ps.setString(1, mail);
			ResultSet res  = ps.executeQuery();
			if(res.next()) {
				if(SecurityConfig.check(mdp, res.getString(5))) {
					int idUser = res.getInt(1);
					PreparedStatement bankPs = con.prepareStatement(DBConstants.READ_BANK_BY_MAIL);//SELECT idBank, iban ,swift,acount  FROM bank WHERE idUser = ?
					bankPs.setInt(1, idUser);
					ResultSet bankRes = bankPs.executeQuery();
					while(bankRes.next()) {
						Bank bank = new Bank();
						bank.setIdBank(bankRes.getInt(1));
						bank.setIban(bankRes.getString(2));
						bank.setSwift(bankRes.getString(3));
						bank.setAcount(bankRes.getInt(4));
						bankOfUser.add(bank);
					}
					dataBaseConfig.closePreparedStatement(bankPs);
				}
				
			}
			else {
				System.out.println("Aucun utilisateur trouvé , mot de passe ou mail incorrect ");
			}
			// Fermeture des requetes 
			
			dataBaseConfig.closePreparedStatement(ps);
		}catch(Exception e){
			System.out.println("Error in this gettinhg bank");
		}finally {
			dataBaseConfig.closeConnection(con);
		}
		return bankOfUser;
	}
	
}
