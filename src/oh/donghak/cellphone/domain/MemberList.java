/*
 * 작성일: 20191118
 * 작성자: 오동학
 * 개요: 회원목록
 * 		- 회원가입
 * 		- 로그인
 * 		- 멤버 저장
 * 		- 맴버 불러오기
 */
package oh.donghak.cellphone.domain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import oh.donghak.cellphone.java.Cart;
import oh.donghak.cellphone.java.Customer;

public class MemberList {
	
	Scanner scan = new Scanner(System.in);
	HashMap<String, Customer> memberList = new HashMap<> ();
	
	public MemberList() {}
	
	// 회원 추가
	public void addMember() {
		System.out.println("===================");
		System.out.println("\t"+"회원가입");
		System.out.println("===================");

		System.out.print("ID : ");
		String id = scan.next();
		System.out.print("PW : ");
		String password = scan.next();
		if(memberList.containsKey(id)) {
			System.out.println("이미 만들어진 아이디 입니다.");
			System.out.println();
			return;
		}
		
		memberList.put(id, new Customer(id, password));
		System.out.println("========================");
		System.out.println("\t"+"회원가입 완료");
		System.out.println("========================");
	}
	
	// 로그인
	public boolean login() {
		System.out.print("고객 ID : ");
		String id = scan.next();
		System.out.print("고객 PW : ");
		String password = scan.next();

		if(memberList.containsKey(id)) {
			
			if(memberList.get(id).getPassword().equals(password)) {
				System.out.println("=============================");
				System.out.println("\t"+"로그인 되었습니다.");
				System.out.println("=============================");
				memberList.get(id).menu();
				return true;
			}
		}
		System.out.println("=============================");
		System.out.println("\t"+"로그인 실패.");
		System.out.println("=============================");
		return false;
	}
	
	// 멤버 저장
	public void saveMemberList() {
		Iterator<String> iter = memberList.keySet().iterator();
		if(iter.hasNext()) {
			try {
				FileOutputStream fos = new FileOutputStream("members.txt");
				DataOutputStream dos = new DataOutputStream(fos);
								
				while(iter.hasNext()) {
					Customer customer = memberList.get(iter.next());
					dos.writeUTF(customer.getId());
					dos.writeUTF(customer.getPassword());
					
					Cart cart = customer.getCart();
					
					Iterator<Integer> iterCart = cart.getList().keySet().iterator();

					while(iterCart.hasNext()) {
						dos.writeChar('\\');
						Cellphone phone = cart.getList().get(iterCart.next());
						dos.writeInt(phone.getRegiNum());
						dos.writeUTF(phone.getBrand());
						dos.writeUTF(phone.getModelName());
						dos.writeInt(phone.getPrice());
						dos.writeInt(phone.getAmount());
					}
					
					if(iter.hasNext()) {
						dos.writeChar('\n');
					}else
						dos.writeChar('\t');
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
	
	// 멤버 불러오기
	public void loadMemberList(){
		try {
			FileInputStream fis = new FileInputStream("members.txt");
			try {
				DataInputStream dis = new DataInputStream(fis);
				
				while(true) {
					String id = dis.readUTF();
					String password = dis.readUTF();
					
					Customer customer = new Customer(id, password);
					
					char newLine = dis.readChar();
					while(newLine == '\\') {
						int regiNum = dis.readInt();
						String brand = dis.readUTF();
						String model = dis.readUTF();
						int price = dis.readInt();
						int amount = dis.readInt();
						
						Cellphone upPhone = new Cellphone(regiNum, brand, model, price, amount);
						customer.getCart().addCart(upPhone);
						newLine = dis.readChar();
					}
					
					if(newLine == '\n') {
						memberList.put(id, customer);
						continue;
					}else if(newLine == '\t') {
						memberList.put(id, customer);
						System.out.println("회원목록 불러오기 성공!");
						break;
					}
				}
				dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch( FileNotFoundException e) {
			System.out.println("불러올 파일이 없습니다.");
		}

	}
}
