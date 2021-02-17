package payMyBuddySystem.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import payMyBuddy.config.DataBaseConfig;
import payMyBuddySystem.constants.DBConstants;
import payMyBuddySystem.models.User;
import payMyBuddySystem.models.UserModel;
import payMyBuddySystem.security.SecurityConfig;

public class UserDAO implements DAO<User> {
DataBaseConfig dataBaseConfig = new DataBaseConfig();
	
	public boolean create(User data) {
		Connection con = null;
		int ligne =0;
		boolean resu=false;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.SAVE_USER);
			ps.setString(1,data.getMail());
			ps.setString(2,data.getMdp());
			ps.setFloat(3, data.getBalance());
			ps.setBoolean(4,data.isStatus());
		    ligne = ps.executeUpdate();
		    System.out.println(ligne);
		    dataBaseConfig.closePreparedStatement(ps);
		    if(ligne>0) {
		    	resu = true;
		    }
		}
		catch(Exception e) {
			System.out.println("Error , the saving of user fails ");
			
		}
		finally {
			dataBaseConfig.closeConnection(con);
			  System.out.println(resu);
			return resu;
			
		}
		
		// TODO Auto-generated method stub
		
	}

	
	public User read(int i) {
		System.out.println("Je passe");
		Connection con = null;
		User user =  null;
		try {
			con = dataBaseConfig.getConnection();
			System.out.println("System ON ");
			PreparedStatement ps  = con.prepareStatement(DBConstants.READ_USER_BY_ID);
			ps.setInt(1, i);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				user= new User();
				user.setUserId(rs.getInt(1));
				user.setBalance(rs.getFloat(3));
				user.setMail(rs.getString(2));
				user.setStatus(rs.getBoolean(4));
			}
			dataBaseConfig.closePreparedStatement(ps);
			
		}catch(Exception e ) {
			System.out.println("No Data IN DB");
		}
		finally {
			dataBaseConfig.closeConnection(con);
			return user;
		}
		
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		Connection con = null;
		int ligne = 0;
		boolean res=false;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.DELETE_USER);
			ps.setBoolean(1, false);
			ps.setInt(2, id);	
			ligne = ps.executeUpdate();
			if(ligne>0) {
				res= true;
			}
			dataBaseConfig.closePreparedStatement(ps);
		}catch(Exception e){
			System.out.println("Error in deleting of data");
		}finally {
			dataBaseConfig.closeConnection(con);
		}
		
		return res;
	}
	public List<User> getAll() {
		// TODO Auto-generated method stub
		List<User> listeUser= new ArrayList<User>();
		Connection con = null;
		ResultSet res;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.GET_ALL_USERS);
			// SELECT idUser,mail,balance,step FROM users
			res=ps.executeQuery();
			while(res.next()) {
				User u = new User();
				u.setMail(res.getString(2));
				u.setUserId(res.getInt(1));
				u.setBalance(res.getFloat(3));
				u.setStatus(res.getBoolean(4));
				listeUser.add(u);
			}
			dataBaseConfig.closePreparedStatement(ps);
		}catch(Exception e) {
			System.out.println("Fail into to the loading file ");
		}
		return listeUser;
	}
	
	public User getUserByMail(String mail,String mdp) {
		Connection con = null;
		User u= null;
		
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement select = con.prepareStatement(DBConstants.READ_USER_BY_MAIL);//  SELECT idUser,mail,balance,step FROM users WHERE mail = ?
			select.setString(1, mail);
			ResultSet res = select.executeQuery();
			if(res.next()) {
				if(SecurityConfig.check(mdp, res.getString(5))) {
					u= new User();
					System.out.println("Valeur trouvé ");
					u = new User();
					u.setMail(res.getString(2));
					u.setBalance(res.getFloat(3));
					u.setStatus(res.getBoolean(4));
					u.setUserId(res.getInt(1));
				}
			
				
			}
			dataBaseConfig.closePreparedStatement(select);
		}catch(Exception e ){
			System.out.println("No user found of data");
		}finally {
			
			dataBaseConfig.closeConnection(con);
		}
		return u;
	}
	public User getUserByMail(String mail) {
		Connection con = null;
		User u= null;
		
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement select = con.prepareStatement(DBConstants.READ_USER_BY_MAIL);//  SELECT idUser,mail,balance,step FROM users WHERE mail = ? and mdp=?
			select.setString(1, mail);
			
			ResultSet res = select.executeQuery();
			
			if(res.next()) {
				u= new User();
				System.out.println("Valeur trouvé ");
				u = new User();
				u.setMail(res.getString(2));
				u.setBalance(res.getFloat(3));
				u.setStatus(res.getBoolean(4));
				u.setUserId(res.getInt(1));
				u.setMdp(res.getString(5));
				
			}
			else {
				System.out.println("Je passe par la ");
			}
			dataBaseConfig.closePreparedStatement(select);
		}catch(Exception e ){
			System.out.println("No user found of data");
		}finally {
			
			dataBaseConfig.closeConnection(con);
		}
		return u;
	}


	@Override
	public boolean update(User data) {
		
		Connection con = null;
		int ligne;
		boolean result=false;
		User u = this.getUserByMail(data.getMail(),data.getMdp());
		if(u!= null) {
			System.out.println("J'entre dedans ");
			try {
				con = dataBaseConfig.getConnection();
				PreparedStatement ps = con.prepareStatement(DBConstants.UPDATE_USER);// "UPDATE users set mail= ?,mdp = ? , balance =? ,step = ? where idUser=?  ";
				ps.setString(1, data.getMail());
				
				ps.setFloat(2,data.getBalance());
				ps.setBoolean(3, data.isStatus());
				ps.setInt(4,u.getUserId());
				ligne = ps.executeUpdate();
				if(ligne>0) {
					result=true;
				}
				dataBaseConfig.closePreparedStatement(ps);
			}catch(Exception e ){
				System.out.println("Error in updating of data");
			}finally {
				
				dataBaseConfig.closeConnection(con);
			}
		}
		else {
			System.out.println("No user found");
		}
		
		return result;
		
	}
	
}
