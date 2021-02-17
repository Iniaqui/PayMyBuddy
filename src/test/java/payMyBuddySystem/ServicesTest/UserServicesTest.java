package payMyBuddySystem.ServicesTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import payMyBuddySystem.models.User;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc 
class UserServicesTest {
	User u = new User ();
	@Autowired
	private MockMvc mockMvc;
	@Before
	void setUp() {
		
	}
	@Test
	void createdTest() throws Exception {
		String user ="{\r\n"
				+ "        \"mail\": \"utilisateur1@gmail.com\",\r\n"
				+ "        \"balance\": 25000.0,\r\n"
				+ "        \"status\": true,\r\n"
				+ "        \"mdp\": \"azerty527\"\r\n"
				+ "       \r\n"
				+ "    }";
		MockHttpServletRequestBuilder req =post("/user/create").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(user);
		this.mockMvc.perform(req).andExpect(status().isCreated());
	}

}
