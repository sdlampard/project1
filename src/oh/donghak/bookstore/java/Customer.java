package oh.donghak.bookstore.java;

import java.util.Scanner;

import domain.Cellphone;
import domain.CellphoneHashMap;

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
					System.out.println("1.추가   2.삭제    3.구매    4.목록    5.이전");
					System.out.println("-------------------");

					System.out.println("메뉴 번호를 입력하세요. : ");
					cartMenu = scan.nextInt();
					switch(cartMenu) {
					case 1: //추가
						CellphoneHashMap phoneMap = CellphoneHashMap.getInstance();
						phoneMap.showAllStacks();
						System.out.print("장바구니에 담을 핸드폰의 코드를 입력하세요. [이전0] : ");
						int regiNum = scan.nextInt();
						Cellphone buyPhone = phoneMap.getCellphone(regiNum);
						System.out.print("수량을 입력하세요. : ");
						int amount = scan.nextInt();
						buyPhone.setAmount(amount);
						cart.cartAdd(buyPhone);
						break;
					case 2: // 삭제
						System.out.print("삭제하려는 책의 코드를 입력하세요. [이전:0]: ");
						int phoneNum = scan.nextInt();
						cart.removeCart(phoneNum);
						break;
					case 3: // 구매
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
				break;
			case 3: // 환불
				break;
			case 4: // 로그아웃
				break;
			}
		}while(menu != 4);
	}
	
}
