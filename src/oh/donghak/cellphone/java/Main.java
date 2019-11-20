/*
 * 작성일: 20191118
 * 작성자: 오동학
 * 개요: 메인
 * 수정일: 20191120
 */
package oh.donghak.cellphone.java;

import java.io.IOException;
import java.util.Scanner;

import oh.donghak.cellphone.domain.HostLogin;
import oh.donghak.cellphone.domain.MemberList;

public class Main {

	static Scanner scan = new Scanner(System.in);
	
	// 로그인
	public static int loginMenu() {
		int menu = 0;
		System.out.println("--------------로그인2------------");
		System.out.println("1.고객"+"\t"+"2.관리자"+"\t"+"3.회원가입"+"\t"+ "4.종료");
		System.out.println("-------------------------------");
		System.out.print("메뉴번호를 입력하세요. : ");
		String menuStr = scan.next();
		try {
			menu = Integer.parseInt(menuStr);
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
			System.out.println();
		}
		return menu;
	}
	
	// 메뉴
	public static void main(String[] args) throws IOException {
		HostLogin host = HostLogin.getInstance();
		MemberList member = new MemberList();
		
		// 파일 불러오기
		member.loadMemberList();
		host.readFile();
		host.readOrders();
		
		int menu = 0;
		do {
			menu = loginMenu();
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
		host.saveAllStacks();
		host.saveOrders();
		member.saveMemberList();
		System.out.println("종료!");
	}
}
