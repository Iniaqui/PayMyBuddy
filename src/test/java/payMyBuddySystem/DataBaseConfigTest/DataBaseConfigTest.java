package payMyBuddySystem.DataBaseConfigTest;
import java.io.IOException;
import java.io.InputStream;
import java.lang.System.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

public class DataBaseConfigTest {
	private static final String dbfilePath = "./application.properties";

	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Properties pro=dataBaseProperties();
		Connection c;
		Class.forName("com.mysql.cj.jdbc.Driver");
		c=DriverManager.getConnection(dataBaseProperties().getProperty("db.url"),dataBaseProperties().getProperty("db.user"),dataBaseProperties().getProperty("db.password"));
		return c;
	}
	public void closePreparedStatement (PreparedStatement ps ) {
		if(ps!= null) {
			try {
				ps.close();
				System.out.println("Statement closing ");
			}
			catch(SQLException e) {
				System.out.println("Fail of closign statement ");
			}
		}
	}
	
	  public void closeConnection(Connection con){
	        if(con!=null){
	            try {
	                con.close();
	                System.out.println("Closing DB connection");
	            } catch (SQLException e) {
	                System.out.println("Error while closing connection");
	            }
	        }
	    }

	private Properties dataBaseProperties() {
		
		Properties prop = new Properties ();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(dbfilePath);
		if(inputStream!=null) {
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return prop;
	
	}
}




