package oh.donghak.cellphone.service;

import java.util.Scanner;

public interface Guest {
	
	String VIP = "VIP";
	String NEW = "NEW";
	
	double VIP_SALE = 0.05;
	int VIP_LEVEL = 100000000;

	Scanner scan = new Scanner(System.in);

	void buyNow();
	void refund();
	void menu();
	
	// 고객 메뉴
	public default int customerMenu() {
		System.out.println("------------------------고객 메뉴-----------------------");
		System.out.println("1.장바구니     2.구매     3.환불     4.비밀번호 변경    5.구매금액    6.로그아웃");
		System.out.println("-----------------------------------------------------");

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
	
	// 장바구니 메뉴
	public default int cartMenu() {
		System.out.println("-----------------장바구니-----------------");
		System.out.println("1.추가"+"\t"+"2.삭제"+"\t"+"3.구매"+"\t"+"4.목록"+"\t"+"5.이전");
		System.out.println("---------------------------------------");

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
	
}
