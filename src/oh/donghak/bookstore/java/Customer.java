package oh.donghak.bookstore.java;

import java.util.Scanner;

import domain.Cellphone;

public class Customer {

	Scanner scan = new Scanner(System.in);
	
	Cart cart = new Cart();
	
	public void menu() {
		int menu = 0;
		do {
			System.out.println("------------------고객 메뉴-----------");
			System.out.println("1.장바구니  2. 구매    3.환불   4.로그아웃");
			System.out.println("---------------------------------");
			
			System.out.println("메뉴 번호를 입력하세요. : ");
			menu = scan.nextInt();
			switch(menu) {
			case 1: // 장바구니
				int cartMenu = 0;
				do {
					System.out.println("------장바구니--------");
					System.out.println("1.추가   2.삭제    3.구매    4.이전");
					System.out.println("-------------------");
					

					System.out.println("메뉴 번호를 입력하세요. : ");
					cartMenu = scan.nextInt();
					switch(cartMenu) {
					case 1: //추가
						cart.cartAdd(new Cellphone());
						break;
					case 2: //삭제
						break;
					case 3: //구매
						break;
					case 4: //이전
						break;
					}
				}while(cartMenu != 4);
				break;
			case 2: // 구매
				break;
			case 3: // 환불
				break;
			case 4: // 로그아웃
				break;
			}
		}while(menu != 4);
	}
	
}
