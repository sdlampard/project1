/*
 * 작성일: 20191118
 * 작성자: 오동학
 * 개요: 관리자 로그인
 * 
 * 수정일: 20191118
 * 		Order와 재고리스트 hostLogin 안으로 가져옴
 */
package oh.donghak.cellphone.domain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oh.donghak.cellphone.java.Customer;
import oh.donghak.cellphone.service.Host;

public class HostLogin implements Host{
	
	private static HostLogin host = new HostLogin();

	private Map<Integer, Cellphone> phoneList; // 재고리스트
	private List<Order> orderList; // 주문리스트
	private long profit = 0; // 결산
	
	public Map<Integer, Cellphone> getPhoneList() {
		return phoneList;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	private HostLogin () {
		phoneList = new HashMap<> ();
		orderList = new ArrayList<> ();
	}
	
	public static HostLogin getInstance() {
		if(host == null) {
			host = new HostLogin();
		}
		return host;
	}
	
	public void addPhone(Cellphone phone) {
		phoneList.put(phone.getRegiNum(), phone);
	}
	
	// 수량만 수정하기
	public void morePhones() {
		System.out.print("추가하실 핸드폰 번호 :");
		String phoneStr = scan.next();
		int phone = 0;
		try {
			phone = Integer.parseInt(phoneStr);
		}catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
			return;
		}
		
		System.out.print("추가할 수량 : ");
		String numStr = scan.next();
		int num = 0;
		try {
			num = Integer.parseInt(numStr);
		}catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
			return;
		}
		
		phoneList.get(phone).setAmount(num);
		return;
	}
	
	// 재고에 핸드폰 올리기
	public void addCellphone() {
		System.out.println("=========핸드폰 등록=========");
		System.out.print("등록번호 : ");
		String regi = scan.next();
		int regiNum = 0;
		try {
			regiNum = Integer.parseInt(regi);
			if(regiNum < 1) {
				System.out.println("양심은 어디에 두셨습니까?");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
			return;
		}

		System.out.print("제조사 : ");
		String brand = scan.next();

		System.out.print("모델명 : ");
		String modelName = scan.next();

		System.out.print("가격 : ");
		String priceStr = scan.next();
		int price = 0;
		try {
			price = Integer.parseInt(priceStr);
			if(price < 0) {
				System.out.println("양심은 어디에 두셨습니까?");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
			return;
		}
		System.out.print("수량 : ");
		String amountStr = scan.next();
		int amount = 0;
		try {
			amount = Integer.parseInt(amountStr);
			if(amount < 0) {
				System.out.println("양심은 어디에 두셨습니까?");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
			return;
		}
		
		// 등록번호 안겹치게 체크
		while(phoneList.containsKey(regiNum)) {
			regiNum++;
		}
		
		Cellphone phone = new Cellphone(regiNum,brand, modelName, price, amount);
		phoneList.put(phone.getRegiNum(), phone);
		System.out.println();
	}
	
	// 선택된 핸드폰 정보 가져오기
	public Cellphone getCellphone(int regiNum) {
		if(phoneList.containsKey(regiNum)) {
			return phoneList.get(regiNum);
		}
		return null;
	}
	
	// 핸드폰 삭제
	public void removePhone() {
		System.out.print("번호를 입력해주세요 : [이전:0]");
		int delNum = 0;

		// 숫자만 입력되게 예외처리
		try {
			delNum = scan.nextInt();
			if(delNum < 0) {
				System.out.println("양심은 어디에 두셨습니까?");
				return;
			}
		} catch(InputMismatchException e) {
			System.out.println("숫자만 입력해주세요 제발!");
		}
		
		if(delNum != 0) {
			try {
				if(phoneList.containsKey(delNum)) {
					phoneList.remove(delNum);
					System.out.println("==============핸드폰 삭제==============");
					System.out.println("\t"+delNum+"번의 핸드폰이 삭제되었습니다.");
					System.out.println("===================================");
				}else {
					System.out.println("==============핸드폰 삭제==============");
					System.out.println("\t"+"존재 하지않는 핸드폰 번호입니다.");
					System.out.println("===================================");
				}
				
			} catch( NullPointerException e) {
				System.out.println("코드를 제대로 입력해주세요!");
			}
		}		
	}
	
	// 핸드폰 수정
	public void editCellphone() {
		System.out.print("수정하려는 핸드폰의 번호를 입력해주세요 [이전:0] : ");
		
		int num = 0;
		// 숫자만 입력되게 예외처리
		try {
			num = scan.nextInt();
			if(num < 0) {
				System.out.println("양심은 어디에 두셨습니까?");
				return;
			}
		} catch(InputMismatchException e) {
			System.out.println("숫자만 입력해주세요 제발!");
		}
		
		if(num != 0) {
			try {
				System.out.println("--------- 핸드폰 수정 ---------");
				System.out.print("제조사 : ");
				String editBrand = scan.next();
				System.out.print("모델명 : ");
				String editModelName = scan.next();
				System.out.print("가격 : ");
				int editPrice = 0;
				// 숫자만 입력되게 예외처리
				try {
					editPrice = scan.nextInt();
				} catch(InputMismatchException e) {
					System.out.println("숫자만 입력해주세요 제발!");
				}
				System.out.print("수량 : ");
				int editAmount = 0;
				// 숫자만 입력되게 예외처리
				try {
					editAmount = scan.nextInt();
				} catch(InputMismatchException e) {
					System.out.println("숫자만 입력해주세요 제발!");
				}
				Cellphone phone = new Cellphone(num, editBrand, editModelName, editPrice, editAmount);
				phoneList.replace(num, phone);
				System.out.println("========================");
				System.out.println("\t"+"핸드폰이 수정되었습니다.");
				System.out.println("=========================");
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
			} catch ( NullPointerException e) {
				System.out.println("없는 핸드폰을 수정하려 하셨습니다.");
			}
		}
		return;
	}
	
	// 재고 보여주기
	public void showAllStacks() {
		Iterator<Integer> iter = phoneList.keySet().iterator();

		System.out.println("----------------- 재고 목록 ----------------");
		System.out.println("번호"+"\t"+"제조사"+"\t"+"모델명"+"\t"+"가격"+"\t"+"재고");
		System.out.println("----------------------------------------");
		while(iter.hasNext()) {
			System.out.println(phoneList.get(iter.next()));
		}
	}
	
	// 재고 불러오기
	public void readFile() throws FileNotFoundException {
		try {
			FileInputStream fr = new FileInputStream("phoneList.txt");
			try {
				DataInputStream dis = new DataInputStream(fr);
				while(true) {
					int regiNum = dis.readInt();
					String brand = dis.readUTF();
					String model = dis.readUTF();
					int price = dis.readInt();
					int amount = dis.readInt();
					
					Cellphone upPhone = new Cellphone(regiNum, brand, model, price, amount);
					phoneList.put(regiNum, upPhone);
					char newline = dis.readChar();
					if(newline == '\n') continue;
					if(newline == '\t') {
						System.out.println("재고 불러오기 성공!");
						break;
					}
				}				
				dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch ( FileNotFoundException e) {
			System.out.println("불러올 파일이 업습니다.");
		}
		
	}
	
	// 재고 저장
	public void saveAllStacks() {
		Iterator<Integer> iter = phoneList.keySet().iterator();
		if(iter.hasNext()) {
			try {
				FileOutputStream fos = new FileOutputStream("phoneList.txt");
				DataOutputStream dos = new DataOutputStream(fos);
				
				while(iter.hasNext()) {
					Cellphone phone = phoneList.get(iter.next());
					dos.writeInt(phone.getRegiNum());
					dos.writeUTF(phone.getBrand());
					dos.writeUTF(phone.getModelName());
					dos.writeInt(phone.getPrice());
					dos.writeInt(phone.getAmount());
					if(iter.hasNext()) {
						dos.writeChar('\n');
					}else {
						dos.writeChar('\t');
					}
				}
				dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 전체 주문 목록
	public void showAllOrders() {
		if(orderList.size() == 0) {
			System.out.println("주문이 없습니다.");
			return;
		}
		for(int i = 0; i < orderList.size(); i++) {
			System.out.println((i+1) + "\t" + orderList.get(i));
		}
	}	
	
	// 미결제 주문 목록
	public void showUnSignedOrders() {
		for(int i = 0; i < orderList.size(); i++) {
			Order order = orderList.get(i);
			if(!order.isSignCompelted()) {
				System.out.println((i+1)+"\t"+order);
			}
		}
	}
	
	// 결제 승인
	public void approveOrder() {
		
		showUnSignedOrders();
		
		System.out.print("구매 승인할 코드를 입력하세요. [이전:0] : ");
		int orderNum = 0;
		
		String orderStr = scan.next();
		try {
			orderNum = Integer.parseInt(orderStr);
			if(orderNum < 0) {
				System.out.println("양심은 어디에 두셨습니까?");
				return;
			}
		} catch(NumberFormatException e) {
			System.out.println("숫자만 입력해주세요 제발!");
			System.out.println();
		}
		
		// 주문번호 0 이면 나가지도록
		if(orderNum == 0)
			return;
		
		if(orderNum > 0 && orderNum <= orderList.size()) {
			orderNum--;
			Order order = orderList.get(orderNum);
			
			// 결제를 이미 마쳤을때
			if(order.isSignCompelted()) {
				System.out.println("이미 결제 하셨습니다.");
				System.out.println();
				return;
			}
			
			// 결산
			profit += (order.getCellphone().getAmount() * order.getCellphone().getPrice());
			order.setSignCompelted(true);

			System.out.println("===============================");
			System.out.println("\t"+"결제 승인 되었습니다.");
			System.out.println("===============================");
		}
	}
	
	// 결제 취소
	public void cancelOrder() {
		for(int i = 0; i < orderList.size(); i++) {
			if(orderList.get(i).isRefundRequest())
				System.out.println((i+1)+" "+orderList.get(i).getCellphone());
		}
		
		System.out.print("환불 처리할 코드를 입력하세요. [이전:0] : ");
		int orderNum = 0;

		String orderStr = scan.next();
		try {
			orderNum = Integer.parseInt(orderStr);
			if(orderNum < 0) {
				System.out.println("양심은 어디에 두셨습니까?");
				return;
			}
		} catch(NumberFormatException e) {
			System.out.println("숫자만 입력해주세요 제발!");
		}
		
		if(orderNum == 0)
			return;
		
		// 존재 여부와 환불요청 확인
		if(orderNum > 0 && orderNum <= orderList.size()) {
			orderNum--;
			Order order = orderList.get(orderNum);
			if(order.isRefundRequest()) {
				// 환불된 금액 결산에서 빼기
				profit -= (order.getCellphone().getAmount() * order.getCellphone().getPrice());
				// 환불 체크 완료
				orderList.get(orderNum).setRefundComplete(true);

				System.out.println("===============================");
				System.out.println("\t"+"환불 처리 되었습니다.");
				System.out.println("===============================");
				
				// 환불된 물품이 아직 재고에 있다면
				if(phoneList.containsKey(order.getCellphone().getRegiNum())){
					// 환불된 물품들 다시 재고에 올리기
					phoneList.get(order.getCellphone().getRegiNum()).setAmount(
							phoneList.get(order.getCellphone().getRegiNum()).getAmount() 
							+ orderList.get(orderNum).getCellphone().getAmount());
				}
				// 주문에서 삭제
				orderList.remove(orderNum);
				return;
			}
		}	
		System.out.println("===============================");
		System.out.println("\t"+"환불 처리 실패하셨습니다.");
		System.out.println("===============================");
		return;
	}
	
	// 개인 주문 목록
	public void showAllOrders(String id) {
		for(int i = 0; i < orderList.size(); i++) {
			if(orderList.get(i).getUserId().equals(id)) {
				System.out.println((i+1) + "\t" + orderList.get(i));
			}
		}
	}	

	// 결산
	public void getProfit() {
		System.out.println("결산 : "+profit+"원");
	}
	
	// 환불 요청
	public void requestRefund(int refundNum, Customer customer) {
		// 주문에 있는지 확인
		if(refundNum == 0)
			return;
		
		if(refundNum > 0 && refundNum <= orderList.size()) {
			refundNum--;
			// 환불요청 true
			if(orderList.get(refundNum).isRefundRequest()) {
				System.out.println("이미 환불 요청 하셨습니다.");
				return;
			}
			orderList.get(refundNum).setRefundRequest(true);
			customer.setCumulativeMoney(customer.getCumulativeMoney()-
					orderList.get(refundNum).getCellphone().getPrice() * 
					orderList.get(refundNum).getCellphone().getAmount() );
			
			// 아직 결제 되지 않은 주문에 환불 요청이 들어왔을 때
			if(orderList.get(refundNum).isRefundRequest() 
					&& !orderList.get(refundNum).isSignCompelted()) {

				if(phoneList.containsKey(orderList.get(refundNum).getCellphone().getRegiNum())){
					// 환불된 물품들 다시 재고에 올리기
					phoneList.get(orderList.get(refundNum).getCellphone().getRegiNum()).setAmount(
							phoneList.get(orderList.get(refundNum).getCellphone().getRegiNum()).getAmount() 
							+ orderList.get(refundNum).getCellphone().getAmount());
				}
				orderList.remove(refundNum);
				System.out.println("===============================");
				System.out.println("\t"+"주문이 취소 되었습니다.");
				System.out.println("===============================");
			}else {
				System.out.println("===============================");
				System.out.println("\t"+"환불 요청 되었습니다.");
				System.out.println("===============================");
			}
			return;
		}
		System.out.println("===============================");
		System.out.println("\t"+"환불 요청 되지 않았습니다.");
		System.out.println("===============================");
	}
	
	// 주문 추가
	public void addOrder(Order order) {
		orderList.add(order);
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
						showAllStacks();
						break;
					case 2: // 추가
						addCellphone();
						break;
					case 3: // 수정
						editCellphone();
						break;
					case 4: // 삭제
						removePhone();
						break;
					case 5: // 수량만 추가
						showAllStacks();
						morePhones();
						break;
					}
				}while( stockMenu != 6);
				break;
			case 2:	//주문관리
				int orderMenu = 0;
				do {
					orderMenu = 0;
					orderMenu = orderMenu();
					switch(orderMenu) {
					case 1: // 주문목록
						showAllOrders();
						break;
					case 2:  // 결제승인
						approveOrder();
						break;
					case 3:  // 결제취소
						cancelOrder();
						break;
					case 4: // 결산
						getProfit();
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
	

	// 주문 저장
	public void saveOrders() {
		if(orderList.size() > 0) {
			try {
				FileOutputStream fos = new FileOutputStream("orders.txt");
				DataOutputStream dos = new DataOutputStream(fos);
				
				for(Order order : orderList) {
					dos.writeInt(order.getCellphone().getRegiNum());
					dos.writeUTF(order.getCellphone().getBrand());
					dos.writeUTF(order.getCellphone().getModelName());
					dos.writeInt(order.getCellphone().getPrice());
					dos.writeInt(order.getCellphone().getAmount());
					dos.writeUTF(order.getUserId());
					dos.writeBoolean(order.isRefundComplete());
					dos.writeBoolean(order.isRefundRequest());
					dos.writeBoolean(order.isSignCompelted());
					if(orderList.indexOf(order) == orderList.size()-1) {
						dos.writeChar('\t');
						dos.writeLong(profit);
					}else
						dos.writeChar('\n');
				}
				
				dos.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	// 주문 불러오기
	public void readOrders() {
		FileInputStream fis;
		try {
			fis = new FileInputStream("orders.txt");
			try {
			DataInputStream dis = new DataInputStream(fis);
			
			while(true) {
				
				int regiNum = dis.readInt();
				String brand = dis.readUTF();
				String model = dis.readUTF();
				int price = dis.readInt();
				int amount = dis.readInt();
				Cellphone phone = new Cellphone(regiNum,brand, model, price, amount);
				String userId = dis.readUTF();
	
				Order order = new Order(phone, userId);
				
				order.setRefundComplete(dis.readBoolean());
				order.setRefundRequest(dis.readBoolean());
				order.setSignCompelted(dis.readBoolean());

				orderList.add(order);
				char end = dis.readChar();
				if(end == '\n') {
					continue;
				}else if(end == '\t') {
					profit = dis.readLong();
					System.out.println("주문목록 불러오기 성공!");
					break;
				}
				
			}
			dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			System.out.println("불러올 파일이 없습니다.");
		}
	}
}
