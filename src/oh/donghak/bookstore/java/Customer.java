package oh.donghak.bookstore.java;

import java.util.Scanner;

import oh.donghak.bookstore.domain.Cellphone;
import oh.donghak.bookstore.domain.CellphoneHashMap;
import oh.donghak.bookstore.domain.Order;
import oh.donghak.bookstore.domain.OrderList;
import oh.donghak.bookstore.service.Guest;

public class Customer implements Guest{

	Scanner scan = new Scanner(System.in);
	private String id;
	private String password;
	CellphoneHashMap phoneMap = CellphoneHashMap.getInstance();
	Cart cart = new Cart();
	
	public Customer() {}
	
	public Customer(String id, String password) {
		this.id = id;
		this.password = password;
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

	public int customerMenu() {
		System.out.println("------------------고객 메뉴-----------");
		System.out.println("1.장바구니"+"\t"+"2.구매"+"\t"+"3.환불"+"\t"+"4.비밀번호 변경"+"\t"+"5. 로그아웃");
		System.out.println("---------------------------------");

		int menu = 0;
		System.out.print("메뉴 번호를 입력하세요. : ");
		String menuStr = scan.next();
		try {
			menu = Integer.parseInt(menuStr);
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
		}
		return menu;
	}
	
	public int cartMenu() {
		System.out.println("------장바구니--------");
		System.out.println("1.추가   2.삭제    3.구매    4.목록    5.이전");
		System.out.println("-------------------");

		int menu = 0;
		System.out.print("메뉴 번호를 입력하세요. : ");
		String menuStr = scan.next();
		try {
			menu = Integer.parseInt(menuStr);
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
		}
		return menu;
	}
	
	public void buyNow() {
		System.out.print("구매하실 핸드폰 코드를 입력하세요. [이전:0] :");
		int phone2 = 0;
		String phoneCode = scan.next();
		try {
			phone2 = Integer.parseInt(phoneCode);
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
			return;
		}
		
		if(phone2==0)
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
		
		try {
			if(phoneMap.getCellphone(phone2).getAmount()<buyAmount) {
				System.out.println("구매할 수량이 재고보다 많습니다.");
				return;
			}
			Cellphone buyNowPhone = new Cellphone( 
				phoneMap.getCellphone(phone2).getRegiNum(),
				phoneMap.getCellphone(phone2).getBrand(), phoneMap.getCellphone(phone2).getModelName(), 
				phoneMap.getCellphone(phone2).getPrice(), buyAmount);
			
			OrderList orderList = OrderList.getInstance();
			orderList.addOrder(new Order(buyNowPhone,id));
			
			phoneMap.getCellphone(phone2).setAmount(phoneMap.getCellphone(phone2).getAmount()-buyAmount);
			
			System.out.println("=========================");
			System.out.println("구매요청이 완료되었습니다.");
			System.out.println("=========================");
		} catch(NullPointerException e) {
			System.out.println("그런 핸드폰 코드는 없습니다. 제대로 입력해주세요!");
		}
	}
	
	//환불
	public void refund() {
		System.out.println("------구매 완료 목록--------");
		System.out.println("번호    제조사   모델명   가격   재고");
		System.out.println("===========================");
		OrderList orderList2 = OrderList.getInstance();
		orderList2.showAllOrders(id);
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
		orderList2.requestRefund(refundNum);
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
						phoneMap.showAllStacks();
						cart.cartAdd();
						break;
					case 2: // 삭제
						cart.removeCart();
						break;
					case 3: // 구매
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
				phoneMap.showAllStacks();
				buyNow();
				break;
			case 3: // 환불
				refund();
				break;
			case 4: // 비밀번호 변경
				changePassword();
				break;
			case 5:	// 로그아웃
				break;
			}
		}while(menu != 5);
	}
	
}
