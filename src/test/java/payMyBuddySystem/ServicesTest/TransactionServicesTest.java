package payMyBuddySystem.ServicesTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import payMyBuddy.config.DataBaseConfig;
import payMyBuddySystem.services.TransactionServices;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc 
class TransactionServicesTest {
	static DataBaseConfig dbtest = new DataBaseConfig();
	TransactionServices transServices = new TransactionServices();
	
	@Autowired 
	MockMvc mockMvc;
	
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
			PreparedStatement ps = con.prepareStatement("SELECT idUser FROM users WHERE mail=?");
			PreparedStatement psFriend = con.prepareStatement("SELECT idUser FROM users WHERE mail=?");
			ps.setString(1, "utilisateurTest1@gmail.com");
			psFriend.setString(1, "utilisateurFrien@gmail.com");
		    ResultSet res= ps.executeQuery();
		    ResultSet resFriend = psFriend.executeQuery();
		    if(res.next() && resFriend.next()) {
		    	int idUser = res.getInt(1);
		    	int idUserFriend = resFriend.getInt(1);
		    	//SUPRESSION transactions
		    	PreparedStatement supprRelation = con.prepareStatement("DELETE  FROM transactions WHERE idUserReceiver=? AND idUserSender = ? ");
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
		    PreparedStatement supprUser = con.prepareStatement("DELETE FROM users WHERE mail=?");
		    PreparedStatement supprUserFriend = con.prepareStatement("DELETE FROM users WHERE mail=?");
		    supprUser.setString(1, "utilisateurTest1@gmail.com");
		    supprUserFriend.setString(1, "utilisateurFrien@gmail.com");
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
	void ajoutTransfert() throws Exception {
		
		String user ="{\r\n"
				+ "        \"mail\": \"utilisateurTest1@gmail.com\",\r\n"
				+ "        \"balance\": 25000.0,\r\n"
				+ "        \"status\": true,\r\n"
				+ "        \"mdp\": \"azerty527\"\r\n"
				+ "       \r\n"
				+ "    }";
		String user1 ="{\r\n"
				+ "        \"mail\": \"utilisateurFrien@gmail.com\",\r\n"
				+ "        \"balance\": 25000.0,\r\n"
				+ "        \"status\": true,\r\n"
				+ "        \"mdp\": \"qwerty458\"\r\n"
				+ "       \r\n"
				+ "    }";
		
		MockHttpServletRequestBuilder reqUser =post("/user/create").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(user);
		MockHttpServletRequestBuilder reqUserFriend =post("/user/create").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(user1);
		this.mockMvc.perform(reqUser).andExpect(status().isCreated());
		this.mockMvc.perform(reqUserFriend).andExpect(status().isCreated());
		
		String trans= " {\r\n"
				+ "        \"mailReceiver\":\"utilisateurFrien@gmail.com\",\r\n"
				+ "        \"idMotif\": 1,\r\n"
				+ "        \"amount\": 1000,\r\n"
				+ "        \"transactionType\": \"DEPOT\"\r\n"
				+ "    }";
		MockHttpServletRequestBuilder reqTrans =post("/transactions/create?mail=utilisateurTest1@gmail.com&&mdp=azerty527").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(trans);
		this.mockMvc.perform(reqTrans).andExpect(status().isCreated());
	}

}