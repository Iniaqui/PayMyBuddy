package payMyBuddySystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Transaction {
	@JsonIgnore
	private int idTransaction;
	@JsonIgnore
	private int idReceiver;
	@JsonIgnore
	private int idSender;
	@JsonIgnore
	private int idMotif ;
	private float fees;
	private float amount;
	private String transactionType ;
	private String mailReceiver;
	
	public String getMailReceiver() {
		return mailReceiver;
	}
	public void setMailReceiver(String mailReceiver) {
		this.mailReceiver = mailReceiver;
	}
	public int getIdTransaction() {
		return idTransaction;
	}
	public void setIdTransaction(int idTransaction) {
		this.idTransaction = idTransaction;
	}
	public int getIdReceiver() {
		return idReceiver;
	}
	public void setIdReceiver(int idReceiver) {
		this.idReceiver = idReceiver;
	}
	public int getIdSender() {
		return idSender;
	}
	public void setIdSender(int idSender) {
		this.idSender = idSender;
	}
	public int getIdMotif() {
		return idMotif;
	}
	public void setIdMotif(int idMotif) {
		this.idMotif = idMotif;
	}
	public float getFees() {
		return fees;
	}
	public void setFees(float fees) {
		this.fees = fees;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	
 
}
