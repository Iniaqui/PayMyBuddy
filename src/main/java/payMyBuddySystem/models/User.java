package payMyBuddySystem.models;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails{
	protected int userId;
	
	protected String username;
	protected float balance ;
	protected boolean status;
	protected String password ;


	public int getUserId() {
		return userId;
	}
	public String getMail() {
		return username;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setMail(String mail) {
		this.username = mail;
	}
	public float getBalance() {
		return balance;
	} 
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMdp() {
		return password;
	}
	public void setMdp(String mdp) {
		this.password = mdp;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	

	
}
