/*
 * 작성일: 20191118
 * 작성자: 오동학
 * 개요: 장바구니
 * 		- 장바구니 추가
 * 		- 장바구니 구매
 * 		- 장바구니 삭제
 * 		- 장바구니 목록
 */
package oh.donghak.cellphone.java;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import oh.donghak.cellphone.domain.Cellphone;
import oh.donghak.cellphone.domain.CellphoneHashMap;
import oh.donghak.cellphone.domain.Order;
import oh.donghak.cellphone.domain.OrderList;

public class Cart {
	HashMap<Integer, Cellphone> list;
	CellphoneHashMap phoneMap = CellphoneHashMap.getInstance();

	Scanner scan = new Scanner(System.in);
	
	public Cart () {
		list  = new HashMap<> ();
	}
	
	public HashMap<Integer, Cellphone> getList() {
		return list;
	}
	
	public void addCart(Cellphone phone) {
		list.put(phone.getRegiNum(), phone);
	}

	// 장바구니에 추가	
	public void cartAdd() {	
		System.out.print("장바구니에 담을 핸드폰의 코드를 입력하세요. [이전0] : ");
		int regiNum = 0;

		String regi = scan.next();
		// 숫자만 입력되게 예외처리
		try{
			regiNum = Integer.parseInt(regi);
		}catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
		}
		
		if(regiNum == 0)
			return;
		
		System.out.print("수량을 입력하세요. : ");
		int amount = 0;
		String amountString = scan.next();
		// 숫자만 입력되게 예외처리
		try{
			amount = Integer.parseInt(amountString);
		}catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
			return;
		}
		
		try {
			// 수량 체크
			if(phoneMap.getCellphone(regiNum).getAmount() < amount) {
				System.out.println("원하시는 수량이 재고 보다 많습니다.");
				return;
			}
			
			// 핸드폰 정보 가져오기
			Cellphone newPhone = new Cellphone(phoneMap.getCellphone(regiNum).getRegiNum(),phoneMap.getCellphone(regiNum).getBrand(), phoneMap.getCellphone(regiNum).getModelName(), 
					phoneMap.getCellphone(regiNum).getPrice(), amount);
			// 카트에 추가
			list.put(newPhone.getRegiNum(), newPhone);
		} catch(NullPointerException e) {
			System.out.println("장바구니에 담길 핸드폰 코드가 틀렸습니다.");
		}
	}
	
	// 장바구니에서 삭제
	public boolean removeCart() { 
		System.out.print("삭제하려는 핸드폰의 코드를 입력하세요. [이전:0]: ");
		int num = 0;
		String numStr = scan.next();
		// 숫자만 입력되게 예외처리
		try {
			num = Integer.parseInt(numStr);
		}catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
		}
		
		if(num == 0)
			return false;
		
		// 장바구니에 있나 확인
		if(list.containsKey(num)) {
			// 재고에 수량 돌려넣기
			phoneMap.getCellphone(num).setAmount(phoneMap.getCellphone(num).getAmount() + list.get(num).getAmount());
			
			list.remove(num);
			System.out.println("삭제 되었습니다.");
			
			return true;
		}
		
		System.out.println("그런 핸드폰은 없습니다.");
		return false;
	}
	
	// 장바구니에서 구매하기
	public void buyCart(String id) { 
		System.out.print("구매하실 핸드폰 코드를 입력하세요. [이전:0] :");
		
		int num = 0;

		String numStr = scan.next();
		// 숫자만 입력되게 예외처리
		try {
			num = Integer.parseInt(numStr);
		}catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
		}
		
		if(num == 0)
			return;
		
		if(list.containsKey(num)) {
			// 핸드폰 수량 빼오기
			phoneMap.getCellphone(num).setAmount(phoneMap.getCellphone(num).getAmount() - list.get(num).getAmount());
						
			OrderList orderList = OrderList.getInstance();
			orderList.addOrder(new Order(list.get(num),id));
			System.out.println("===============================");
			System.out.println("\t"+"구매요청이 완료되었습니다.");
			System.out.println("===============================");
			list.remove(num);
		}
	}
	
	// 카트 보여주기
	public void showAllCart() {
		Iterator<Integer> iter = list.keySet().iterator();

		System.out.println("--------------- 장바구니 목록 ----------------");
		System.out.println("번호"+"\t"+"제조사"+"\t"+"모델명"+"\t"+"가격"+"\t"+"재고");
		System.out.println("-----------------------------------------");
		while(iter.hasNext()) {
			System.out.println(list.get(iter.next()));
		}

	}
}
