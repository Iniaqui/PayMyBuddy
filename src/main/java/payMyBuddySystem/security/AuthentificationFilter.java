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
import org.springframework.security.core.userdetails.UserDetails;
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
        	User user = new Gson().fromJson(request.getReader(), User.class);
         // User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),new ArrayList<>()));

        }

        catch(IOException e)

        {

            throw new RuntimeException("Could not read request" + e);

        }

    }


    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication)

    {
    	 UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        String token = Jwts.builder()

               .setSubject(userDetail.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS512, "SecretKeyToGenJWTs".getBytes())

                .compact();
        

        response.addHeader("Authorization","Bearer " + token);
    }
}