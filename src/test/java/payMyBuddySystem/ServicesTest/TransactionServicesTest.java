package payMyBuddySystem.ServicesTest;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import payMyBuddy.config.DataBaseConfig;
import payMyBuddySystem.models.Transaction;
import payMyBuddySystem.models.User;
import payMyBuddySystem.services.TransactionServices;
import payMyBuddySystem.services.UserServices;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc 
class TransactionServicesTest {
	static DataBaseConfig dbtest = new DataBaseConfig();

	
	@Autowired 
	MockMvc mockMvc;
	
	@Autowired
	UserServices userServicesTest;
	@Autowired
	TransactionServices transServicesTest;
	
	@BeforeAll
	static void setUp() {
		
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
		    	//SUPRESSION transactions
		    	PreparedStatement supprRelation = con.prepareStatement("DELETE  FROM transactions WHERE idUserReceiver=? AND idUserSender = ? ");
		    	supprRelation.setInt(1, idUserFriend);
		    	supprRelation.setInt(2, idUser);
		    	
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
		   int ligneSuppr= supprUser.executeUpdate();
		    int ligneSpprFriend =supprUserFriend.executeUpdate();
		    
		    
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
	void ajoutTransfert() throws Exception {
		
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
		
		
		/*MockHttpServletRequestBuilder reqUser =post("/user/create").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(user);
		MockHttpServletRequestBuilder reqUserFriend =post("/user/create").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(user1);
		this.mockMvc.perform(reqUser).andExpect(status().isCreated());
		this.mockMvc.perform(reqUserFriend).andExpect(status().isCreated());*/
		Transaction trans = new Transaction();
		float amount = 2000;
		trans.setAmount(amount);
		trans.setIdMotif(1);
		trans.setMailReceiver("utilisateurFriend@gmail.com");
		trans.setTransactionType("DEPOT");
		assertTrue(transServicesTest.saveTransaction("utilisateurTest1@gmail.com", trans));

	/*	MockHttpServletRequestBuilder reqTrans =post("/transactions/create?mail=utilisateurTest1@gmail.com&&mdp=azerty527").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(trans);
		this.mockMvc.perform(reqTrans).andExpect(status().isCreated());*/
		
	}

}
