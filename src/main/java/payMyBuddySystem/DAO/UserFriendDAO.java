package payMyBuddySystem.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import payMyBuddy.config.DataBaseConfig;
import payMyBuddySystem.constants.DBConstants;
import payMyBuddySystem.models.User;
import payMyBuddySystem.models.UserFriend;

public class UserFriendDAO implements DAO<UserFriend>{
DataBaseConfig dataBaseConfig = new DataBaseConfig();
	
	
	public boolean create(UserFriend data) {
		// TODO Auto-generated method stub
		Connection con= null;
		boolean res = false;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.SAVE_USER_FRIEND);
			ps.setInt(2,data.getIdFriend());
			ps.setInt(1, data.getIdUser());
			int ligne  = ps.executeUpdate();
			if(ligne>0) {
				res = true;
			}
			dataBaseConfig.closePreparedStatement(ps);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dataBaseConfig.closeConnection(con);
		}
		
		return res;
	}

	
	public UserFriend read(int i) {
		// TODO Auto-generated method stub
		UserFriend userFriend = null;
		Connection con = null;
		try {
			con = dataBaseConfig.getConnection();
			 PreparedStatement ps = con.prepareStatement(DBConstants.READ_USER_FRIEND);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				userFriend= new UserFriend();
				userFriend.setId(i);
				userFriend.setIdFriend(rs.getInt(1));
				userFriend.setIdUser(rs.getInt(2));
			
			}
			dataBaseConfig.closePreparedStatement(ps);
			
		}catch(Exception e ) {
			System.out.println("Error in the reading userFriend");
		}
		finally {
			dataBaseConfig.closeConnection(con);
		}
		return userFriend;
	}

	
	public boolean update(UserFriend data) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean delete(int id) {
		boolean res = false ;
		Connection con = null;
		try {
			con = dataBaseConfig.getConnection();
			
		}catch(Exception e) {
			System.out.println("DELETING ERROR ");
		}finally {
			dataBaseConfig.closeConnection(con);
		}
		// TODO Auto-generated method stub
		return res;
	}

	
	public List<UserFriend> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public User getUserFriend(String mail) {
		User u = new User();
		Connection con = null;
		try {
			con = dataBaseConfig.getConnection();
			 PreparedStatement ps = con.prepareStatement(DBConstants.READ_USER_FRIEND_BY_MAIL);
			 ps.setString(1, mail);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				u.setUserId(rs.getInt(1));
			}
			dataBaseConfig.closePreparedStatement(ps);
			
		}catch(Exception e ) {
			System.out.println("Error in the reading userFriend");
		}
		finally {
			dataBaseConfig.closeConnection(con);
		}
		return u;
	}
	public ArrayList<String> getAllUserFriend(int i){
		ArrayList<String> listeUserFriend = new ArrayList<String>();
		Connection con = null;
		try {
			con = dataBaseConfig.getConnection();
			 PreparedStatement ps = con.prepareStatement(DBConstants.READ_USER_FRIEND);//SELECT idRelation ,idFriend, idUser FROM userfriend WHERE idUser = ? OR idFriend= ?
			 ps.setInt(1, i);
			 ps.setInt(2, i);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int idFriend = rs.getInt(2);
				PreparedStatement psUser = con.prepareStatement(DBConstants.READ_USER_BY_ID);//SELECT idUser,mail,balance,step FROM users WHERE idUser = ? 
				psUser.setInt(1, idFriend);
				ResultSet resUser = psUser.executeQuery();
				if(resUser.next()) {
					String mailFriend = resUser.getString(2);
					listeUserFriend.add(mailFriend);
				}
			}
			dataBaseConfig.closePreparedStatement(ps);
			
		}catch(Exception e ) {
			System.out.println("Error in the reading userFriend");
		}
		finally {
			dataBaseConfig.closeConnection(con);
		}
		return listeUserFriend;
	}


	
}
