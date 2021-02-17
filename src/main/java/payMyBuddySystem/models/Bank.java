package payMyBuddySystem.models;

public class Bank {
	int idBank;
	int idUser;
	String iban ;
	String swift;
	float acount;
	public int getIdBank() {
		return idBank;
	}
	public void setIdBank(int idBank) {
		this.idBank = idBank;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getSwift() {
		return swift;
	}
	public void setSwift(String swift) {
		this.swift = swift;
	}
	public float getAcount() {
		return acount;
	}
	public void setAcount(float acount) {
		this.acount = acount;
	}
}
