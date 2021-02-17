package payMyBuddySystem.DataBaseService;

import java.sql.Connection;
import java.sql.PreparedStatement;

import payMyBuddySystem.DataBaseConfigTest.DataBaseConfigTest;

public class DataBasePrepareService {
	DataBaseConfigTest dataBaseConfigTest = new DataBaseConfigTest();
	   public void clearDataBaseEntries(){
	        Connection connection = null;
	        try{
	            connection = dataBaseConfigTest.getConnection();

	            //delete all user
	           PreparedStatement ps = connection.prepareStatement("delete from users where mailUser =? ");
	            ps.setString(1,"utilisateur1@gmail.com");
	           ps.execute();

	            
	            //connection.prepareStatement("truncate table ticket").execute();
	            
	            

	        }catch(Exception e){
	            e.printStackTrace();
	        }finally {
	        	dataBaseConfigTest.closeConnection(connection);
	        }
	    }
}
