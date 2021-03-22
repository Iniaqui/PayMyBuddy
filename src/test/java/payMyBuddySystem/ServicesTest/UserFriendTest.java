package payMyBuddySystem.ServicesTest;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import payMyBuddy.config.DataBaseConfig;
import payMyBuddySystem.models.User;
import payMyBuddySystem.models.UserFriend;
import payMyBuddySystem.services.UserFriendServices;
import payMyBuddySystem.services.UserServices;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc 
@WebAppConfiguration
class UserFriendTest {
	static DataBaseConfig dbtest = new DataBaseConfig();
	
	@Autowired
	 MockMvc mockMvc;
	@Autowired
	UserFriendServices userFriendServices ;
	
	@Autowired
	UserServices userServicesTest;
	
	@BeforeAll
	static void setUp () throws Exception {
	}
	
	@AfterAll
	static void cleanUp() {
		Connection con = null;
		int ligne =0;
		try {
			con = dbtest.getConnection();
			
			//Rcuperation id User
			PreparedStatement ps = con.prepareStatement("SELECT idUser FROM users WHERE username=?");
			PreparedStatement psFriend = con.prepareStatement("SELECT idUser FROM users WHERE username=?");
			
			ps.setString(1, "utilisateurTest1@gmail.com");
			psFriend.setString(1, "utilisateurFriend@gmail.com");
		    ResultSet res= ps.executeQuery();
		    ResultSet resFriend = psFriend.executeQuery();
		
		 
		 
		    if(res.next() && resFriend.next()) {
		    	int idUser = res.getInt(1);
		    	int idUserFriend = resFriend.getInt(1);
		    	//SUPRESSION RELATION AMI
		    	PreparedStatement supprRelation = con.prepareStatement("DELETE  FROM userfriend WHERE idUser=? AND idFriend = ? ");
		    	supprRelation.setInt(1, idUser);
		    	supprRelation.setInt(2, idUserFriend);
		    	int ligneRelation = supprRelation.executeUpdate();
		    	if(ligneRelation>0) {
		    		System.out.println("Relation supprimé ");
		    	}
		    	  dbtest.closePreparedStatement(supprRelation);
		    }
		    
		    dbtest.closePreparedStatement(ps);
		    dbtest.closePreparedStatement(psFriend);
		    
		  //SUPPRESSION USER 
		    PreparedStatement supprUser = con.prepareStatement("DELETE FROM users WHERE username=?");
		    PreparedStatement supprUserFriend = con.prepareStatement("DELETE FROM users WHERE username=?");
		    supprUser.setString(1, "utilisateurTest1@gmail.com");
		    supprUserFriend.setString(1, "utilisateurFriend@gmail.com");
		    supprUser.executeUpdate();
		    supprUserFriend.executeUpdate();
		    
		    
		    dbtest.closePreparedStatement(supprUser);
		    dbtest.closePreparedStatement(supprUserFriend);
		    //System.out.println(ligne);
		   
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
	void test() throws Exception {
		/*String user ="{\r\n"
				+ "        \"mail\": \"utilisateurTest1@gmail.com\",\r\n"
				+ "        \"balance\": 25000.0,\r\n"
				+ "        \"status\": true,\r\n"
				+ "        \"mdp\": \"azerty527\"\r\n"
				+ "       \r\n"
				+ "    }";*/
		float balance = 25000;
		User u = new User ();
		u.setMail("utilisateurTest1@gmail.com");
		u.setMdp("azerty527");
		u.setStatus(true);
		u.setBalance(balance);
		/*String user1 ="{\r\n"
				+ "        \"mail\": \"utilisateurFrien@gmail.com\",\r\n"
				+ "        \"balance\": 25000.0,\r\n"
				+ "        \"status\": true,\r\n"
				+ "        \"mdp\": \"qwerty458\"\r\n"
				+ "       \r\n"
				+ "    }";*/
		float balance2 = 25000;
		User u2 = new User ();
		u2.setMail("utilisateurFriend@gmail.com");
		u2.setMdp("azerty527");
		u2.setStatus(true);
		u2.setBalance(balance2);
		 
		boolean isSaved =userServicesTest.saveUser(u);
		System.out.println(isSaved);
		
		
		boolean isSaved2 =userServicesTest.saveUser(u2);
		System.out.println(isSaved2);
		
		boolean isSaved3 =userFriendServices.saveUserFriend(new UserFriend(), u.getMail(), u2.getMail());
		System.out.println(isSaved3);
		
		
		assertTrue(isSaved3);
		/*MockHttpServletRequestBuilder reqUser =post("/user/create").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(user);
		MockHttpServletRequestBuilder reqUserFriend =post("/user/create").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(user1);
		
		this.mockMvc.perform(reqUser).andExpect(status().isCreated());
		this.mockMvc.perform(reqUserFriend).andExpect(status().isCreated());
		
		MockHttpServletRequestBuilder req =post("/userFriend/create?mail=utilisateurTest1@gmail.com&&mdp=azerty527&&mailFriend=utilisateurFrien@gmail.com").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8);
		mockMvc.perform(req).andExpect(status().isCreated());*/
	}
	
	/*@Test
	void getUserFriendTest() throws Exception {
		ArrayList<String> liste= userFriendServices.getAllFriendByUser("utilisateurTest1@gmail.com");
		boolean isEmpty = !liste.isEmpty();
		assertTrue(isEmpty);
		
		MockHttpServletRequestBuilder req =get("/userFriend?mailUser=utilisateurTest1@gmail.com&&mdp=azerty527").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8);
		mockMvc.perform(req).andExpect(status().isOk());
	}*/

}
