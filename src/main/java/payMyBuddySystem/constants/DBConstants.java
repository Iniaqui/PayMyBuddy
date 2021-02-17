package payMyBuddySystem.constants;

public class DBConstants {
	//Users
		public static final String SAVE_USER = "INSERT INTO users(mail,mdp,balance,step) VALUES(?,?,?,?)";
		public static final String READ_USER_BY_ID = "SELECT idUser,mail,balance,step FROM users WHERE idUser = ? ";
		public static final String UPDATE_USER = "UPDATE users set mail= ?, balance =? ,step = ? where idUser=?  ";
		public static final String DELETE_USER = "UPDATE users SET step = ?  WHERE idUser =? ";
		public static final String GET_ALL_USERS = "SELECT idUser,mail,balance,step FROM users";
		public static final String READ_USER_BY_MAIL_PASS= "SELECT idUser,mail,balance,step FROM users WHERE mail = ? and mdp=? ";
		public static final String READ_USER_BY_MAIL= "SELECT idUser,mail,balance,step,mdp FROM users WHERE mail = ?";
		// Transactions
		
		public static final String UPDATE_USER_BALANCE="UPDATE users set balance = ? WHERE idUser = ?";
		
		public static final String SAVE_TRANSACTION = "INSERT INTO transactions(idUserReceiver,idUserSender,idMotif, fees,amount,transactionType) VALUES(?,?,?,?,?,?)";
		public static final String READ_TRANSACTION = "SELECT idTransaction,idUserReceiver,idUserSender,idMotif,fees,amount,transactionType WHERE idTransaction = ? ";
		public static final String READ_TRANSACTIONS_BY_MAIL = "select  transactions.idTransaction,\r\n"
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
				+ " WHERE users.mail = ?; ";
		// UserFriend
		public static final String SAVE_USER_FRIEND = "INSERT INTO userfriend(idUser,idFriend)  VALUES (? ,?)";
		public static final String READ_USER_FRIEND = "SELECT idRelation ,idFriend, idUser FROM userfriend WHERE idUser = ? OR idFriend= ? ";
		public static final String DELETE_USER_FRIEND = "DELETE FROM userfriend WHERE idRelation = ? ";
		public static final String READ_USER_FRIEND_BY_MAIL = "SELECT idUser,mail,balance,step FROM users WHERE mail = ?";
		
		// MOTIF
		public static final String SAVE_MOTIF = "INSERT INTO motif(descriptionMotif) VALUES( ?) ";
		public static final String READ_MOTIF = "SELECT idMotif, descriptionMotif FROM motif WHERE idMotif = ? ";
		public static final String READ_MOTIF_BY_MAIL ="SELECT descriptionMotif,m.idMotif FROM motif m, transactions t, users u \r\n"
				+ "WHERE m.idMotif = t.idMotif \r\n"
				+ "AND  (t.idUserReceiver=u.idUser OR t.idUserSender = u.idUser)\r\n"
				+ "\r\n"
				+ "AND u.mail = ?";
		
		// BANK 
		public static final String SAVE_BANK = "INSERT INTO bank(idUser,iban ,swift,acount) VALUES (?,?,?,?)";
		public static final String READ_BANK = "SELECT idUser, iban ,swift,acount  FROM bank WHERE idBank = ? ";
		public static final String DELETE_BANK = "DELETE FROM bank WHERE idBank = ? ";
		public static final String UPDATE_BANK = "UPDATE bank set iban = ? , swift = ? ,acount = ? WHERE idBank=?";
		public static final String READ_BANK_BY_MAIL = "SELECT idBank, iban ,swift,acount  FROM bank WHERE idUser = ?";
}
