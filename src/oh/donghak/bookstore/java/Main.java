package oh.donghak.bookstore.java;

import java.util.Scanner;

import domain.HostLogin;
import domain.Member;

public class Main {
	public static void main(String[] args) {
		HostLogin host = new HostLogin();
		Member member = new Member();
		Customer customer = new Customer();
		Scanner scan = new Scanner(System.in);
		int menu = 0;
		do {
			System.out.println("--------------로그인------------");
			System.out.println("1.고객"+"\t"+"2.관리자"+"\t"+"3.회원가입"+"\t"+ "4.종료");
			System.out.println("------------------------------");
			System.out.print("메뉴번호를 입력하세요. : ");
			menu = scan.nextInt();
			switch(menu) {
			case 1:
				System.out.print("고객 ID : ");
				String customerId = scan.next();
				System.out.print("고객 PW : ");
				String customerPassword = scan.next();
				if(member.login(customerId, customerPassword)) {
					customer.menu();
				}
				break;
			case 2:
				System.out.print("관리자 ID : ");
				String hostId = scan.next();
				System.out.print("관리자 PW : ");
				String hostPassword = scan.next();
				if(host.login(hostId, hostPassword)) {
					System.out.println("==================");
					System.out.println("로그인 되었습니다.");
					System.out.println("==================");
					host.menu();
				}else {
					System.out.println("로그인 실패");
				}
				break;
			case 3: // 회원가입
				member.addMember();
				break;
			case 4: // 종료
				break;
			}
		}while(menu != 4);
		scan.close();
		System.out.println("종료!");
		
	}

}
