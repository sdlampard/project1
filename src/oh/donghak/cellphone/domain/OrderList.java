/*
 * 작성일: 20191118
 * 작성자: 오동학
 * 개요: 주문목록
 * 		- 전체 주문목록 보여주기
 * 		- 미결제 주문목록 보여주기
 * 		- 결제 승인
 * 		- 결제 취소
 * 		- 개인주문목록
 * 		- 결산
 * 		- 주문 추가
 * 		- 환불요청
 * 		- 주문목록 저장
 * 		- 주문목록 불러오기
 * 
 */
package oh.donghak.cellphone.domain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderList {
	
	private static OrderList orderList = new OrderList();
	private ArrayList<Order> orders;
	private long profit = 0;
	Scanner scan = new Scanner(System.in);
	
	private OrderList () {
		orders = new ArrayList<> ();
	}
	
	public static OrderList getInstance() {
		if(orderList == null) {
			orderList = new OrderList();
		}
		return orderList;
	}
	
	// 전체 주문 목록
	public void showAllOrders() {
		if(orders.size() == 0) {
			System.out.println("주문이 없습니다.");
			return;
		}
		for(int i = 0; i < orders.size(); i++) {
			System.out.println((i+1) + "\t" + orders.get(i));
		}
	}	
	
	// 미결제 주문 목록
	public void showUnSignedOrders() {
		for(int i = 0; i < orders.size(); i++) {
			Order order = orders.get(i);
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
		} catch(NumberFormatException e) {
			System.out.println("숫자만 입력해주세요 제발!");
			System.out.println();
		}
		
		// 주문번호 0 이면 나가지도록
		if(orderNum == 0)
			return;
		
		if(orderNum > 0 && orderNum <= orders.size()) {
			orderNum--;
			Order order = orders.get(orderNum);
			
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
		for(int i = 0; i < orders.size(); i++) {
			if(orders.get(i).isRefundRequest())
				System.out.println((i+1)+" "+orders.get(i).getCellphone());
		}
		
		System.out.print("환불 처리할 코드를 입력하세요. [이전:0] : ");
		int orderNum = 0;

		String orderStr = scan.next();
		try {
			orderNum = Integer.parseInt(orderStr);
		} catch(NumberFormatException e) {
			System.out.println("숫자만 입력해주세요 제발!");
		}
		
		// 존재 여부와 환불요청 확인
		if(orderNum > 0 && orderNum <= orders.size()) {
			orderNum--;
			Order order = orders.get(orderNum);
			if(order.isRefundRequest()) {
				// 환불된 금액 결산에서 빼기
				profit -= (order.getCellphone().getAmount() * order.getCellphone().getPrice());
				// 환불 체크 완료
				orders.get(orderNum).setRefundComplete(true);

				System.out.println("===============================");
				System.out.println("\t"+"환불 처리 되었습니다.");
				System.out.println("===============================");
				
				CellphoneHashMap phoneMap = CellphoneHashMap.getInstance();
				// 환불된 물품들 다시 재고에 올리기
				phoneMap.getCellphone(order.getCellphone().getRegiNum()).setAmount(phoneMap.getCellphone(order.getCellphone().getRegiNum()).getAmount()+ orders.get(orderNum).getCellphone().getAmount());

				// 주문에서 삭제
				orders.remove(orderNum);
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
		for(int i = 0; i < orders.size(); i++) {
			if(orders.get(i).getUserId().equals(id)) {
				System.out.println((i+1) + "\t" + orders.get(i));
			}
		}
	}	

	// 결산
	public void getProfit() {
		System.out.println("결산 : "+profit+"원");
	}
	
	// 환불 요청
	public void requestRefund(int refundNum) {
		// 주문에 있는지 확인
		if(refundNum > 0 && refundNum <= orders.size()) {
			refundNum--;
			// 환불요청 true
			if(orders.get(refundNum).isRefundRequest()) {
				System.out.println("이미 환불 요청 하셨습니다.");
				return;
			}
			orders.get(refundNum).setRefundRequest(true);
			System.out.println("===============================");
			System.out.println("\t"+"환불 요청 되었습니다.");
			System.out.println("===============================");
			if(orders.get(refundNum).isRefundRequest() 
					&& !orders.get(refundNum).isSignCompelted()) {
				orders.remove(refundNum);
			}
			return;
		}
		System.out.println("===============================");
		System.out.println("\t"+"환불 요청 되지 않았습니다.");
		System.out.println("===============================");
	}
	
	// 주문 추가
	public void addOrder(Order order) {
		orders.add(order);
	}

	// 주문 저장
	public void saveOrders() {
		if(orders.size() > 0) {
			try {
				FileOutputStream fos = new FileOutputStream("orders.txt");
				DataOutputStream dos = new DataOutputStream(fos);
				
				for(Order order : orders) {
					dos.writeInt(order.getCellphone().getRegiNum());
					dos.writeUTF(order.getCellphone().getBrand());
					dos.writeUTF(order.getCellphone().getModelName());
					dos.writeInt(order.getCellphone().getPrice());
					dos.writeInt(order.getCellphone().getAmount());
					dos.writeUTF(order.getUserId());
					dos.writeBoolean(order.isRefundComplete());
					dos.writeBoolean(order.isRefundRequest());
					dos.writeBoolean(order.isSignCompelted());
					if(orders.indexOf(order) == orders.size()-1) {
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

				orders.add(order);
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
