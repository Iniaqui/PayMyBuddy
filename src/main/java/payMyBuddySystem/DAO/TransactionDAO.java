package payMyBuddySystem.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;

import payMyBuddy.config.DataBaseConfig;
import payMyBuddySystem.constants.DBConstants;
import payMyBuddySystem.models.Transaction;
import payMyBuddySystem.models.User;

public class TransactionDAO implements DAO<Transaction>{
DataBaseConfig dataBaseConfig = new DataBaseConfig();

	public boolean create(Transaction data) {
		// TODO Auto-generated method stub
		Connection con = null;
		boolean res = false;
		try {
			con = dataBaseConfig.getConnection();
			//Obtenons les informations de chacun des utilisateur dans cette transactions 
			PreparedStatement receiver = con.prepareStatement(DBConstants.READ_USER_BY_ID);// SELECT idUser,mail,balance,step FROM users WHERE idUser = ?
			PreparedStatement sender = con.prepareStatement(DBConstants.READ_USER_BY_ID);// SELECT idUser,mail,balance,step FROM users WHERE idUser = ?
			receiver.setInt(1, data.getIdReceiver());
			sender.setInt(1, data.getIdSender());
			ResultSet resultUserSender = sender.executeQuery();
			ResultSet resultUserReceiver =receiver.executeQuery();
			
			if(resultUserReceiver.next() && resultUserSender.next() ) {
				System.out.println("Verification des soldes ");
				float balancerSender=resultUserSender.getFloat(3);
				float balanceReceiver =resultUserReceiver.getFloat(3);
				if(balancerSender>=(data.getAmount()+data.getFees())) {
					System.out.println("Deducation des soldes ");
					float newBalanceSender = balancerSender-data.getAmount()-data.getFees();
					float newBalanceReceiver = balanceReceiver+data.getAmount();
					
					con.setAutoCommit(false);
					try {
						// MISE 0 JOUR DE LA BALANCE DU DESTINATAIRE 
						PreparedStatement psUpdateReceiver =con.prepareStatement(DBConstants.UPDATE_USER_BALANCE);
						psUpdateReceiver.setFloat(1,newBalanceReceiver );
						psUpdateReceiver.setInt(2, data.getIdReceiver());
						int lignereceiver = psUpdateReceiver.executeUpdate();
						System.out.println("Mise à jour de la table User reussi " + lignereceiver);
						// MISE A JOUR DE LA BALANCE EXPEDITEUR 
						PreparedStatement psUpdateSender = con.prepareStatement(DBConstants.UPDATE_USER_BALANCE);
						psUpdateSender.setFloat(1, newBalanceSender);
						psUpdateSender.setInt(2, data.getIdSender());
						int ligneSender = psUpdateSender.executeUpdate();
						System.out.println("Mise à jour de la table UserSender reussi " + ligneSender);
						// INSERTION DE LA TRANSACTIONS DANS LA TABLE TRANSACTIONS
						PreparedStatement ps = con.prepareStatement(DBConstants.SAVE_TRANSACTION);
						/*START TRANSACTION;"
						+ "UPDATE users set balance = ? WHERE idUser = ?;\r\n"
						+ "UPDATE users set balance = ? WHERE idUser = ?;\r\n"
						+ "\r\n"
						+ "INSERT INTO transactions(idUserReceiver,idUserSender,idMotif, fees,amount,transactionType) VALUES(?,?,?,?,?,?);\r\n"
						+ "commit;*/
						
						ps.setInt(1, data.getIdReceiver());
						ps.setInt(2, data.getIdSender());
						ps.setInt(3, data.getIdMotif());
						ps.setFloat(4, data.getFees());
						ps.setFloat(5, data.getAmount());
						ps.setString(6, data.getTransactionType());
						System.out.println(data.getIdReceiver() +" \n" + data.getIdSender() +"\n " +data.getAmount() +"\n "+data.getIdMotif()+"\n "+ data.getFees()+ "\n " +data.getTransactionType());
						int ligne = ps.executeUpdate();
						System.out.println("Mise à jour de la table TRansaction reussi "+ ligne);
						// VERIFICATION DE LA REUSISTE DES TRANSCATIONS 
						if(ligne>0 && ligneSender>0 && lignereceiver >0 ) {
							System.out.println("Transaction reussi");
							res= true;
						}
						else {
							con.rollback();
						}
						// FERMETURE DES REQUETES PREPARE
						dataBaseConfig.closePreparedStatement(ps);
						dataBaseConfig.closePreparedStatement(psUpdateSender);
						dataBaseConfig.closePreparedStatement(psUpdateReceiver);
						con.commit();
					}
					catch (Exception e){
						con.rollback();
					}
					con.setAutoCommit(true);
				}
				else {
					System.out.println("Fond insuffissant");
				}

			}	
			else {
				System.out.println("Utilisateurs non trouvé ");
			}
			
		}catch(Exception e) {
			System.out.println("Error in the saving data");
		}
		finally {
			dataBaseConfig.closeConnection(con);
			return res;
		}
		
	}

	
	public Transaction read(int i) {
		
		// TODO Auto-generated method stub
		Connection con= null;
		Transaction trans = null;
		try {
			con= dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.READ_TRANSACTION);
			ps.setInt(1, i);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				trans = new Transaction();
				trans.setAmount(rs.getFloat(6));
				trans.setFees(rs.getFloat(5));
				trans.setIdMotif(rs.getInt(4));
				trans.setIdSender(rs.getInt(3));
				trans.setIdReceiver(rs.getInt(2));
				trans.setIdTransaction(rs.getInt(1));
			}
			dataBaseConfig.closePreparedStatement(ps);
			
		}catch(Exception e ) {
			System.out.println("No Data IN DB");
		}
		finally {
			dataBaseConfig.closeConnection(con);
			return trans;
		}
		
	}

	
	public boolean update(Transaction data) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public List<Transaction> getAll() {
		// TODO Auto-generated method stub
		return null;
	}


	public ArrayList<Transaction> readByMail(User u ) {
		// TODO Auto-generated method stub
		Connection con= null;
		ArrayList<Transaction> listeTrans = new ArrayList<Transaction>();
		try {
			con= dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.READ_TRANSACTION_BY_MAIL2 ); /*select  transactions.idTransaction,\r\n"
				+ "		transactions.idUserReceiver,\r\n"
				+ "        transactions.idUserSender,\r\n"
				+ "        transactions.idMotif,\r\n"
				+ "        transactions.fees,\r\n"
				+ "        transactions.amount,\r\n"
				+ "        transactions.transactionType \r\n"
				+ "FROM transactions \r\n"
				+ "INNER JOIN users \r\n"
				+ "	ON users.idUser = transactions.idUserReceiver \r\n"
				+ "    OR users.idUser = transactions.idUserSender\r\n"
				+ " WHERE users.mail = ?;*/
			ps.setInt(1, u.getUserId());
			ps.setInt(2, u.getUserId());
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Transaction trans = new Transaction();
				trans = new Transaction();
				trans.setAmount(rs.getFloat(6));
				trans.setFees(rs.getFloat(5));
				trans.setIdMotif(rs.getInt(4));
				trans.setIdSender(rs.getInt(3));
				trans.setIdReceiver(rs.getInt(2));
				PreparedStatement receiver = con.prepareStatement(DBConstants.READ_USER_BY_ID);// SELECT idUser,mail,balance,step FROM users WHERE idUser = ?
				receiver.setInt(1, trans.getIdReceiver());
				ResultSet rsReceiver = receiver.executeQuery();
				if(rsReceiver.next()) {
					trans.setMailReceiver(rsReceiver.getString(2));
				}
				trans.setIdTransaction(rs.getInt(1));
				trans.setTransactionType(rs.getString(7));
				listeTrans.add(trans);
				dataBaseConfig.closePreparedStatement(receiver);
			}
			
			dataBaseConfig.closePreparedStatement(ps);
		}catch(Exception e ) {
			System.out.println("No Data IN DB Transacction");
		}
		finally {
			dataBaseConfig.closeConnection(con);
			
		}
		return listeTrans;
	}
	
	
	 
}
