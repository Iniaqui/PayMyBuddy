package payMyBuddySystem.models;

public class UserModel extends User {
	protected String nouveauMdp;
	public String getNouveauMdp() {
		return nouveauMdp;
	}
	public void setNouveauMdp(String nouveauMdp) {
		this.nouveauMdp = nouveauMdp;
	}
	public UserModel() {
		super();
	}
}
