/*
 * 작성일: 20191118
 * 작성자: 오동학
 * 개요: 장바구니
 * 		addCart - 장바구니 추가
 * 		buyCart - 장바구니 구매
 * 		removeCart - 장바구니 삭제
 * 		showAllCart - 장바구니 목록
 * 
 * 수정일: 20191122
 * 		addCart - 중복된 주문은 수량만 추가 
 * 		removeCart - 수량 추가되는거 제거
 * 		buyCart - 에서 재고보다 많으면 주문삭제
 */
package oh.donghak.cellphone.java;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import oh.donghak.cellphone.domain.Cellphone;
import oh.donghak.cellphone.domain.HostLogin;
import oh.donghak.cellphone.domain.Order;

public class Cart {
	
	private HashMap<Integer, Cellphone> list; //장바구니 리스트
	
	HostLogin host = HostLogin.getInstance();

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
			if(amount < 0) {
				System.out.println("양심은 어디에 두셨습니까?");
				return;
			}
		}catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
			return;
		}
		
		try {
			// 수량 체크
			if(host.getCellphone(regiNum).getAmount() < amount) {
				System.out.println("원하시는 수량이 재고 보다 많습니다.");
				return;
			}
			
			// 핸드폰 정보 가져오기
			Cellphone newPhone = new Cellphone(host.getCellphone(regiNum).getRegiNum(),
					host.getCellphone(regiNum).getBrand(), host.getCellphone(regiNum).getModelName(), 
					host.getCellphone(regiNum).getPrice(), amount);
			
			// 중복된 장바구니엔 수량만 추가
			if(list.containsKey(newPhone.getRegiNum())) {
				System.out.println("중복되어서 수량 추가하였습니다.");
				newPhone.setAmount(amount+list.get(newPhone.getRegiNum()).getAmount());
			}
			
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
			list.remove(num);
			System.out.println("삭제 되었습니다.");
			
			return true;
		}
		
		System.out.println("그런 핸드폰은 없습니다.");
		return false;
	}
	
	// 장바구니에서 구매하기
	public void buyCart(String id, Customer customer) { 
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
			//재고가 있나 확인
			if(host.getCellphone(num).getAmount()<list.get(num).getAmount()) {
				list.remove(num);
				System.out.println("재고보다 많은 수량입니다 다시 주문해주세요.");
				return;
			}
			// 핸드폰 수량 빼오기
			host.getCellphone(num).setAmount(
					host.getCellphone(num).getAmount() - list.get(num).getAmount());
						
			host.addOrder(new Order(list.get(num),id));
			customer.setCumulativeMoney(customer.getCumulativeMoney()+list.get(num).getAmount()*list.get(num).getPrice());
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
