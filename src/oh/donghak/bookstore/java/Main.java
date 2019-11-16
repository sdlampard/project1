package oh.donghak.bookstore.java;

import java.io.IOException;
import java.util.Scanner;

import oh.donghak.bookstore.domain.CellphoneHashMap;
import oh.donghak.bookstore.domain.HostLogin;
import oh.donghak.bookstore.domain.MemberList;
import oh.donghak.bookstore.domain.OrderList;

public class Main {

	static Scanner scan = new Scanner(System.in);
	
	public static  int login() {
		int menu = 0;
		System.out.println("--------------로그인2------------");
		System.out.println("1.고객"+"\t"+"2.관리자"+"\t"+"3.회원가입"+"\t"+ "4.종료");
		System.out.println("------------------------------");
		System.out.print("메뉴번호를 입력하세요. : ");
		String menuStr = scan.next();
		try {
			menu = Integer.parseInt(menuStr);
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
		}
		return menu;
	}
	
	public static void main(String[] args) throws IOException {
		CellphoneHashMap phoneMap = CellphoneHashMap.getInstance();
		OrderList orderList = OrderList.getInstance();
		HostLogin host = new HostLogin();
		MemberList member = new MemberList();
		
		member.loadMemberList();
		phoneMap.readFile();
		orderList.readOrders();
		
		int menu = 0;
		do {
			menu = login();
			switch(menu) {
			case 1: // 고객 로그인
				member.login();
				break;
			case 2: // 관리자 로그인
				host.login();
				break;
			case 3: // 회원가입
				member.addMember();
				break;
			case 4: // 종료
				break;
			}
		}while(menu != 4);
		scan.close();
		member.saveMemberList();
		phoneMap.saveAllStacks();
		orderList.saveOrders();
		System.out.println("종료!");
	}
}
