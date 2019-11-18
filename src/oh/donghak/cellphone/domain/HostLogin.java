/*
 * 작성일: 20191118
 * 작성자: 오동학
 * 개요: 관리자 로그인
 */
package oh.donghak.cellphone.domain;

import java.io.IOException;
import java.util.Scanner;

import oh.donghak.cellphone.service.Host;

public class HostLogin implements Host{

	Scanner scan = new Scanner(System.in);
	CellphoneHashMap phoneList = CellphoneHashMap.getInstance();
		
	// 관리자 로그인
	public void login() throws IOException {
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
	public int managerMenu() {
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
	public int stockMenu() {
		System.out.println("----------재고 관리----------");
		System.out.println("1.목록 2.추가 3.수정 4.삭제 5.이전");
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
	public int orderMenu() {
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
	
	// 메뉴
	public void menu() throws IOException {
		int menu = 0;
		do {
			menu = 0;
			menu = managerMenu();
			
			switch(menu) {
			case 1:	// 재고관리
				int stockMenu = 0;
				do {
					stockMenu = stockMenu();
					switch(stockMenu) {
					case 1: // 목록
						phoneList.showAllStacks();
						break;
					case 2: // 추가
						phoneList.addCellphone();
						break;
					case 3: // 수정
						phoneList.editCellphone();
						break;
					case 4: // 삭제
						phoneList.removePhone();
						break;
					}
				}while( stockMenu != 5);
				break;
			case 2:	//주문관리
				OrderList orderList = OrderList.getInstance();
				int orderMenu = 0;
				do {
					orderMenu = 0;
					orderMenu = orderMenu();
					switch(orderMenu) {
					case 1: // 주문목록
						orderList.showAllOrders();
						break;
					case 2:  // 결제승인
						orderList.approveOrder();
						break;
					case 3:  // 결제취소
						orderList.cancelOrder();
						break;
					case 4: // 결산
						orderList.getProfit();
						break;
				}
				}while(orderMenu != 5);
				break;
				
			case 3:
				//로그아웃
				break;
			}
		}while(menu != 3);
		return;
	}
}
