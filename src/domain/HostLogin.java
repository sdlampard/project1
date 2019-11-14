package domain;

import java.util.Scanner;

import service.Host;

public class HostLogin implements Host{
	
	Scanner scan = new Scanner(System.in);
	CellphoneHashMap phoneList = CellphoneHashMap.getInstance();
		
	public boolean login(String id, String password) {
		if(id.equals(ID) && password.equals(PASSWORD)) {
			return true;
		}
		return false;
	}
	
	public void menu() {
		int menu = 0;
		do {
			System.out.println("-----------------관리자 메뉴--------------");
			System.out.println("1. 재고관리  2. 주문관리 3.로그아웃");
//			System.out.println("1.목록 2.추가 3.수정 4.삭제 5.이전");
			System.out.println("----------------------------------------");

			System.out.print("메뉴 번호를 입력하세요. : ");
			menu = scan.nextInt();
			
			switch(menu) {
			case 1:
				int stockMenu = 0;
				do {
					// 재고관리
					System.out.println("-----------------재고 관리--------------");
					System.out.println("1.목록 2.추가 3.수정 4.삭제 5.이전");
					System.out.println("----------------------------------------");
					System.out.print("메뉴 번호를 입력하세요. : ");
					stockMenu = scan.nextInt();
					switch(stockMenu) {
					case 1: // 목록
						phoneList.showAllStacks();
						break;
					case 2: // 추가
						System.out.println("=========핸드폰 등록=======");
						System.out.print("등록번호: ");
						int regiNum = scan.nextInt();
						System.out.print("제조사 : ");
						String brand = scan.next();
						System.out.print("모델명 : ");
						String modelName = scan.next();
						System.out.print("가격 : ");
						int price = scan.nextInt();
						System.out.print("수량 : ");
						int amount = scan.nextInt();
						phoneList.addCellphone(new Cellphone(regiNum, brand, modelName, price, amount));
						break;
					case 3: // 수정
						System.out.print("수정하려는 핸드폰의 번호를 입력해주세요 [이전:0] : ");
						int num = scan.nextInt();
						if(num != 0) {						
							System.out.println("=========핸드폰 수정=======");
							System.out.print("제조사 : ");
							String editBrand = scan.next();
							System.out.print("모델명 : ");
							String editModelName = scan.next();
							System.out.print("가격 : ");
							int editPrice = scan.nextInt();
							System.out.print("수량 : ");
							int editAmount = scan.nextInt();
							phoneList.editCellphone(num, new Cellphone(num, editBrand, editModelName, editPrice, editAmount));
							System.out.println("========================");
							System.out.println("핸드폰이 수정되었습니다.");
							System.out.println("=========================");
						}
						break;
					case 4: // 삭제
						System.out.print("번호를 입력해주세요 : ");
						int delNum = scan.nextInt();
						if(delNum != 0) {
							if(phoneList.removePhones(delNum)) {
							System.out.println("==========핸드폰 삭제===========");
							System.out.println(delNum+"번의 핸드폰이 삭제되었습니다.");
							System.out.println("=========================");
							}
						}
						break;
					case 5: // 이전 
						break;
					}
				}while( stockMenu != 5);
				break;
			case 2:
				int orderMenu = 0;
				do {
				//주문관리
				System.out.println("-----------------재고 관리--------------");
				System.out.println("1.주문목록 2.결제승인 3.결제취소 4.결산 5.이전");
				System.out.println("----------------------------------------");
				System.out.print("메뉴 번호를 입력하세요. : ");
				orderMenu = scan.nextInt();
				switch(orderMenu) {
				case 1: // 주문목록
					break;
				case 2:  // 결제승인
					break;
				case 3:  // 결제취소
					break;
				case 4: // 결산
					break;
				case 5: // 이전
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
