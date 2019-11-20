package oh.donghak.cellphone.service;

import java.io.IOException;
import java.util.Scanner;

public interface Host {
	String ID = "host";
	String PASSWORD = "host";
	
	Scanner scan = new Scanner(System.in);
	
	void menu() throws IOException;
	// 관리자 로그인
	public default void login() throws IOException {
		System.out.print("관리자 ID : ");
		String id = scan.next();
		System.out.print("관리자 PW : ");
		String password = scan.next();
		if(id.equals(ID) && password.equals(PASSWORD)) {
			System.out.println("=============================");
			System.out.println("\t"+"로그인 되었습니다.");
			System.out.println("=============================");
			menu();
		}else {
			System.out.println("=============================");
			System.out.println("\t"+"로그인 실패.");
			System.out.println("=============================");
		}
	}
	
	// 관리자 메뉴
	public default int managerMenu() {
		System.out.println("------------관리자 메뉴--------------");
		System.out.println("1.재고관리           2.주문관리             3.로그아웃");
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
	
	// 재고 관리 메뉴
	public default int stockMenu() {
		System.out.println("----------재고 관리----------");
		System.out.println("1.목록 2.추가 3.수정 4.삭제 5.수량만 추가 6.이전");
		System.out.println("--------------------------");
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
	
	// 주문 관리 메뉴
	public default int orderMenu() {
		System.out.println("-----------------주문 관리-----------------");
		System.out.println("1.주문목록    2.결제승인    3.결제취소    4.결산    5.이전");
		System.out.println("----------------------------------------");
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
