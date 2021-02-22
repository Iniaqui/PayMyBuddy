package payMyBuddySystem.ServicesTest;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import payMyBuddy.config.DataBaseConfig;
import payMyBuddySystem.DAO.UserDAO;
import payMyBuddySystem.models.User;
import payMyBuddySystem.services.UserServices;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc 
class UserServicesTest {
	
	static DataBaseConfig dbtest = new DataBaseConfig();
	
	UserServices userServicesTest= new UserServices();
	
	private static UserDAO userDAO= new UserDAO();
	
	@Autowired
	private MockMvc mockMvc;
	@Before
	void setUp() {
		
	}
	
	@AfterAll
	public static void  cleanUp() {
		Connection con = null;
		int ligne =0;
		try {
			con = dbtest.getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM users WHERE mail=?");
			ps.setString(1, "utilisateurTest1@gmail.com");
		    ligne = ps.executeUpdate();
		    //System.out.println(ligne);
		    dbtest.closePreparedStatement(ps);
		    System.out.println("requet fermé" );
		}
		catch(Exception e) {
			System.out.println("Clean Up fails ");
			
		}
		finally {
			dbtest.closeConnection(con);
		}
		
	}
	
	@Test
	void createdTest() throws Exception {
		//User u = new User();
		String user ="{\r\n"
				+ "        \"mail\": \"utilisateurTest1@gmail.com\",\r\n"
				+ "        \"balance\": 25000.0,\r\n"
				+ "        \"status\": true,\r\n"
				+ "        \"mdp\": \"azerty527\"\r\n"
				+ "       \r\n"
				+ "    }";
		
		MockHttpServletRequestBuilder req =post("/user/create").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(user);
		// u =userServicesTest.getUserByMailAndPass("utilisateurTest1@gmail.com", "azerty527");
		this.mockMvc.perform(req).andExpect(status().isCreated());
		 User u = userDAO.getUserByMail("utilisateurTest1@gmail.com");
		//System.out.println(u.getMail());
		if(u!=null) {
			System.out.println("Creation");
		}
		assertTrue(u.getMail().equals("utilisateurTest1@gmail.com"));
			
		
		
	}
	
	@Test
	 void updateTest() throws Exception {
		Float balance = (float) 5000;
		
		String user ="{\r\n"
				+ "        \"mail\": \"utilisateurTest1@gmail.com\",\r\n"
				+ "        \"balance\": 5000,\r\n"
				+ "        \"status\": true,\r\n"
				+ "        \"mdp\": \"azerty527\"\r\n"
				+ "       \r\n"
				+ "    }";
		MockHttpServletRequestBuilder req = put("/user/update").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(user);
		
		this.mockMvc.perform(req).andExpect(status().isCreated());
		User u = userServicesTest.getUserByMailAndPass("utilisateurTest1@gmail.com", "azerty527");
		System.out.println("Valeur changé " + u.getBalance());
		System.out.println(Float.compare(u.getBalance(),balance));
		if(Float.compare(u.getBalance(), (float)5000)==0) {
			
			System.out.println("Valeur changé ");
			
		}
		
	 }
	@Test
	void deleteTest() throws Exception {
		MockHttpServletRequestBuilder req = delete("/user/delete?mail=utilisateurTest1@gmail.com&&mdp=azerty527").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8);
		this.mockMvc.perform(req).andExpect(status().isAccepted());
		User u = userServicesTest.getUserByMailAndPass("utilisateurTest1@gmail.com", "azerty527");
		if(u.isStatus()==false) {
			System.out.println("Status changé ");
		}
		
	}
	
	@Test
	void getUserTest() throws Exception {
		MockHttpServletRequestBuilder req = get("/user/user?mail=utilisateurTest1@gmail.com&&mdp=azerty527").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8);
		this.mockMvc.perform(req).andExpect(status().isOk());
	}
	@Test
	void getAllUserTest() throws Exception {
		MockHttpServletRequestBuilder req = get("/user").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8);
		this.mockMvc.perform(req).andExpect(status().isOk());
	}

}
