/**
 * 
 */
package payMyBuddySystem.ServicesTest;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import payMyBuddy.config.DataBaseConfig;
import payMyBuddySystem.DAO.BankDAO;
import payMyBuddySystem.DAO.UserDAO;
import payMyBuddySystem.models.Bank;
import payMyBuddySystem.models.User;
import payMyBuddySystem.services.BankServices;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc 
class BankServicesTest {
	static DataBaseConfig dbtest = new DataBaseConfig();
	BankServices bankServicesTest = new BankServices();
	
	@Autowired
	MockMvc mockMvc;
	
	@Mock
	private static BankDAO bankDAO;
	
	@Mock
	private static UserDAO userDAO;
	
	@BeforeAll
	static void  setUp()  {
		 
	}
	
	@AfterAll
	static void cleanUp() {
		Connection con = null;
		int ligne =0;
		int ligne2=0;
		try {
			con = dbtest.getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM users WHERE username=?");
			PreparedStatement psBank = con.prepareStatement("DELETE FROM bank WHERE iban = ?");
			psBank.setString(1, "FR00 2584 5132 4152 8759 7891 A12");
			ps.setString(1, "utilisateurTest1@gmail.com");
			ligne2 = psBank.executeUpdate();
			if(ligne2>0) {
				ligne = ps.executeUpdate();
			}
		    if(ligne>0) {
		    	System.out.println("Reussite");
		    }
		    dbtest.closePreparedStatement(psBank);
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
	@Disabled
	void ajoutBankTest() throws Exception {
		//User u = new User();
				String user ="{\r\n"
						+ "        \"mail\": \"utilisateurTest1@gmail.com\",\r\n"
						+ "        \"balance\": 25000.0,\r\n"
						+ "        \"status\": true,\r\n"
						+ "        \"mdp\": \"azerty527\"\r\n"
						+ "       \r\n"
						+ "    }";
				
				when(userDAO.create(any(User.class))).thenReturn(true);
				MockHttpServletRequestBuilder req =post("/user/create").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(user);
				
				// u =userServicesTest.getUserByMailAndPass("utilisateurTest1@gmail.com", "azerty527");
				this.mockMvc.perform(req).andExpect(status().isCreated());
				
				//assertTrue(u.getMail().equals("utilisateurTest1@gmail.com"));
			
			String bank= " {\r\n"
					+ "        \"iban\": \"FR00 2584 5132 4152 8759 7891 A12\",\r\n"
					+ "        \"swift\": \"BNPA FRPP 456\",\r\n"
					+ "        \"acount\": 15680.0\r\n"
					+ "    }";
			when(bankDAO.create(any(Bank.class))).thenReturn(true);
			MockHttpServletRequestBuilder req1 =post("/banks/ajout").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(bank);
			this.mockMvc.perform(req1).andExpect(status().isCreated());
			
	}
	
	@Test
	@Disabled
	void getBankUserTest() throws Exception {
		MockHttpServletRequestBuilder req1 =get("/banks/user").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8);
		this.mockMvc.perform(req1).andExpect(status().isOk());
	}
	

}
