package domain;

public class Order {
	private Cellphone phone;
	private boolean refund = false;
	
	public Order() {}
	
	public Order(Cellphone phone) {
		this.phone = phone;
	}
}
