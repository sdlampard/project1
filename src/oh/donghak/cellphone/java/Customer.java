/*
 * 작성일: 20191118
 * 작성자: 오동학
 * 개요: 구매자 객체
 * 		- 바로구매
 * 		- 환불
 * 		- 비밀번호 변경
 */
package oh.donghak.cellphone.java;

import oh.donghak.cellphone.domain.Cellphone;
import oh.donghak.cellphone.domain.HostLogin;
import oh.donghak.cellphone.domain.Order;
import oh.donghak.cellphone.service.Guest;

public class Customer implements Guest{
	
	HostLogin host = HostLogin.getInstance();
	
	private String id;
	private String password;
	private String rank = NEW;
	private int cumulativeMoney = 0;
	Cart cart = new Cart();
	
	public Customer() {}
	
	public Customer(String id, String password) {
		this.id = id;
		this.password = password;
	}
	
	public Customer(String id, String password, String rank, int cumulativeMoney) {
		this.id = id;
		this.password = password;
		this.rank = rank;
		this.cumulativeMoney = cumulativeMoney;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Cart getCart() {
		return cart;
	}
	
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public int getCumulativeMoney() {
		return cumulativeMoney;
	}

	public void setCumulativeMoney(int cumulativeMoney) {
		this.cumulativeMoney = cumulativeMoney;
	}

	// 바로 구매
	public void buyNow() {
		System.out.print("구매하실 핸드폰 코드를 입력하세요. [이전:0] :");
		int phone = 0;
		String phoneCode = scan.next();
		try {
			phone = Integer.parseInt(phoneCode);
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
			return;
		}
		
		if(phone==0)
			return;
		
		System.out.print("수량을 입력하세요 : ");
		int buyAmount = 0;
		String amountStr = scan.next();
		try {
			buyAmount = Integer.parseInt(amountStr);
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
			return;
		}
		
		if(buyAmount <= 0) {
			System.out.println("최소한 1개 이상은 주문하셔야합니다.");
			return;
		}
		
		try {
			// 재고수량 보다 많은 수량을 구매 원할 시
			if(host.getPhoneList().get(phone).getAmount()<buyAmount) {
				System.out.println("구매할 수량이 재고보다 많습니다.");
				return;
			}
			
			
			Cellphone buyNowPhone = new Cellphone( 
				host.getPhoneList().get(phone).getRegiNum(),
				host.getPhoneList().get(phone).getBrand(), host.getPhoneList().get(phone).getModelName(), 
				host.getPhoneList().get(phone).getPrice(), buyAmount);

			if(this.getCumulativeMoney()>VIP_LEVEL) {
				buyNowPhone.setPrice((int)(buyNowPhone.getPrice()*0.95));
				System.out.println("VIP님 감사드립니다 소정의 할인을 해드렸습니다.");
			}
			
			host.getOrderList().add(new Order(buyNowPhone,id));
			
			this.setCumulativeMoney(this.getCumulativeMoney() + host.getPhoneList().get(phone).getPrice() * buyAmount);

			if(this.getCumulativeMoney()>VIP_LEVEL) {
				this.setRank(VIP);;
			}
			
			
			host.getPhoneList().get(phone).setAmount(host.getPhoneList().get(phone).getAmount()-buyAmount);
			
			System.out.println("=================================");
			System.out.println("\t"+"구매요청이 완료되었습니다.");
			System.out.println("=================================");
		} catch(NullPointerException e) {
			System.out.println("그런 핸드폰 코드는 없습니다. 제대로 입력해주세요!");
		}
	}
	
	//환불
	public void refund() {
		System.out.println("-------------- 구매 완료 목록 --------------");
		System.out.println("번호"+"\t"+"제조사"+"\t"+"모델명"+"\t"+"가격"+"\t"+"재고");
		System.out.println("--------------------------------------");
		host.showAllOrders(id);
		System.out.print("환불 요청할 핸드폰의 코드를 입력하세요. [이전:0] : ");
		int refundNum = 0;
		String refundStr = scan.next();
		try{
			refundNum = Integer.parseInt(refundStr);
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
		}
		
		if(refundNum == 0)
			return;
		host.requestRefund(refundNum);
	}
	
	// 비밀번호 변경
	public void changePassword() {
		System.out.print("현재 비밀번호를 입력해주세요");
		String currentPassword = scan.next();
		if(!currentPassword.equals(password)) {
			System.out.println("비밀번호가 틀렸습니다.");
			return;
		}
		System.out.print("변경하실 비밀번호를 입력해주세요");
		String newPasword = scan.next();
		setPassword(newPasword);
	}
	
	//메뉴
	public void menu() {
		int menu = 0;
		do {
			menu = customerMenu();
			switch(menu) {
			case 1: // 장바구니
				int cartMenu = 0;
				do {
					cartMenu = cartMenu();
					switch(cartMenu) {
					case 1: //추가
						host.showAllStacks();
						cart.cartAdd();
						break;
					case 2: // 삭제
						cart.showAllCart();
						cart.removeCart();
						break;
					case 3: // 구매
						cart.showAllCart();
						cart.buyCart(id);
						break;
					case 4: // 목록
						cart.showAllCart();
						break;
					case 5: // 이전
						break;
					}
				}while(cartMenu != 5);
				break;
			case 2: // 구매
				host.showAllStacks();
				buyNow();
				break;
			case 3: // 환불
				refund();
				break;
			case 4: // 비밀번호 변경
				changePassword();
				break;
			case 5:	// 로그아웃
				System.out.println("현재까지 구매하신 금액은: "+this.getCumulativeMoney() +"입니다.");
				break;
			}
		}while(menu != 6);
	}
	
}
