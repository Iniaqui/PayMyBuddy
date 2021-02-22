package payMyBuddySystem.security;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.google.gson.Gson;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import payMyBuddySystem.models.User;

public class AuthentificationFilter extends UsernamePasswordAuthenticationFilter
{

    private AuthenticationManager authenticationManager;

    public AuthentificationFilter(AuthenticationManager authenticationManager)

    {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/login");
    }

    @Override

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
    {
        try

        {
        	/*System.out.println(request.getInputStream().toString());
        	String mail = request.getParameter("mail") != null && !request.getParameter("mail").isEmpty()
    				? request.getParameter("mail")
    				: null;
    		String mdp = request.getParameter("mdp") != null && !request.getParameter("mdp").isEmpty()
    				? request.getParameter("mdp")
    				: null;
        	User newUser= new User();
        	newUser.setMail(mail);*/
        	User user = new Gson().fromJson(request.getReader(), User.class);
         // User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getMail(), user.getMdp(),new ArrayList<>()));

        }

        catch(IOException e)

        {

            throw new RuntimeException("Could not read request" + e);

        }

    }


    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication)

    {
        String token = Jwts.builder()

               .setSubject(((User) authentication.getPrincipal()).getMail())
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
                .signWith(SignatureAlgorithm.HS512, "SecretKeyToGenJWTs".getBytes())

                .compact();

        response.addHeader("Authorization","Bearer " + token);
    }
}