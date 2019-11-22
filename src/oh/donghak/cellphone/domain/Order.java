/*
 * 작성일: 20191118
 * 작성자: 오동학
 * 개요: 주문 클래스
 */
package oh.donghak.cellphone.domain;

public class Order {
	
	private Cellphone phone; // 헨드폰
	private String userId = null;; // 사용자id
	private boolean refundRequest = false; // 환불요청
	private boolean refundComplete = false; // 환불완료
	private boolean signCompelted = false; // 결제완료
	
	public Order() {}

	public Order(Cellphone phone) {
		this.phone = phone;
	}
	
	public Order(Cellphone phone, String id) {
		this.phone = phone;
		this.userId = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Cellphone getCellphone() {
		return phone;
	}

	public boolean isRefundRequest() {
		return refundRequest;
	}

	public void setRefundRequest(boolean refundRequest) {
		this.refundRequest = refundRequest;
	}

	public boolean isRefundComplete() {
		return refundComplete;
	}

	public void setRefundComplete(boolean refundComplete) {
		this.refundComplete = refundComplete;
	}
	
	public boolean isSignCompelted() {
		return signCompelted;
	}

	public void setSignCompelted(boolean signCompelted) {
		this.signCompelted = signCompelted;
	}

	public String toString() {
		return phone.toString();
	}
	
}
