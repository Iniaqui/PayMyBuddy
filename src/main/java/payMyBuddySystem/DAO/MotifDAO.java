package payMyBuddySystem.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import payMyBuddy.config.DataBaseConfig;
import payMyBuddySystem.constants.DBConstants;
import payMyBuddySystem.models.Motif;


public class MotifDAO implements DAO<Motif> {
	DataBaseConfig dataBaseConfig = new DataBaseConfig();
	public boolean create(Motif data) {
		// TODO Auto-generated method stub
		boolean res = false;
		Connection con = null;
		try {
			con= dataBaseConfig.getConnection();
			PreparedStatement ps= con.prepareStatement(DBConstants.SAVE_MOTIF);
			ps.setString(1, data.getDescriptionMotif());
			int ligne = ps.executeUpdate();
			if(ligne>0) {
				res= true ;
			}
			dataBaseConfig.closePreparedStatement(ps);
			
		}catch(Exception e){
			System.out.println("Error in this creating motif");
		}finally {
			dataBaseConfig.closeConnection(con);
		}
		return res;
	}

	
	public Motif read(int i) {
		// TODO Auto-generated method stub
		Motif motif= null;
		Connection  con = null;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps =  con.prepareStatement(DBConstants.READ_MOTIF);
			ps.setInt(1, i);
			ResultSet res =ps.executeQuery();
			if(res.next()) {
				motif = new Motif();
				motif.setDescriptionMotif(res.getString(2));
			}
			dataBaseConfig.closePreparedStatement(ps);
			return motif;
			
		}catch(Exception e) {
			System.out.println("Error in this ");
		}finally {
			dataBaseConfig.closeConnection(con);
			
		}
		
		return motif;
	}


	public boolean update(Motif data) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}


	public List<Motif> getAll() {
		// TODO Auto-generated method stub
		return null;
	}


	public ArrayList<Motif> getAllMotifByMail(String userMail) {
		// TODO Auto-generated method stub
		ArrayList<Motif> listeMotifByUser = new ArrayList<Motif>();
		Connection con = null;
		
		try {
			con= dataBaseConfig.getConnection();
			PreparedStatement ps= con.prepareStatement(DBConstants.READ_MOTIF_BY_MAIL);
			ps.setString(1, userMail);
			ResultSet res = ps.executeQuery();
			while(res.next()) {
				Motif m = new Motif ();
				m.setDescriptionMotif(res.getString(1));
				m.setIdMotif(res.getInt(2));
				listeMotifByUser.add(m);
			}
			dataBaseConfig.closePreparedStatement(ps);
		}catch(Exception e){
			System.out.println("Error in this creating motif");
		}finally {
			dataBaseConfig.closeConnection(con);
		}
		
		return listeMotifByUser;
	}
	

}
