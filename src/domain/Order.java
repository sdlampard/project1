package domain;

public class Order {
	private Cellphone phone;
	private boolean refund = false;
	
	public Order() {}
	
	public Order(Cellphone phone) {
		this.phone = phone;
	}
	
	public Cellphone getCellphone() {
		return phone;
	}

	public boolean isRefund() {
		return refund;
	}

	public void setRefund(boolean refund) {
		this.refund = refund;
	}
	
	
}
