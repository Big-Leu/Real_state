package application;

public class Transactions {
	
	private String property_id;
	private int seller_id;
	private String buyer_id;
	private String mode_of_transaction;
	private String transaction_type;
	private String date;

	public Transactions() {
		this.property_id= "";
		this.seller_id= 0;
		this.buyer_id= "";
		this.date= "";
		this.transaction_type= "";
		this.mode_of_transaction = "";
	}
	
	public Transactions(String property_id,int seller_id,String buyer_id,String date,String transaction_type,String mode_of_transaction) {
		this.property_id= property_id;
		this.seller_id= seller_id;
		this.buyer_id= buyer_id;
		this.date= date;
		this.transaction_type= transaction_type;
		this.mode_of_transaction = mode_of_transaction;
	}

	public String getProperty_id() {
		return property_id;
	}

	public void setProperty_id(String property_id) {
		this.property_id = property_id;
	}

	public int getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(int seller_id) {
		this.seller_id = seller_id;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getMode_of_transaction() {
		return mode_of_transaction;
	}

	public void setMode_of_transaction(String mode_of_transaction) {
		this.mode_of_transaction = mode_of_transaction;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	

}
