package payMyBuddySystem.models;

import java.util.Collection;



public class User  {
	protected int userId;
	protected String mail;
	protected float balance ;
	protected boolean status;
	protected String mdp ;


	public int getUserId() {
		return userId;
	}
	public String getMail() {
		return mail;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setMail(String mail) {
		this.mail = mail;
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
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	

	
}
