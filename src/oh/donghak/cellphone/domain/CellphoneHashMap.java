/*
 * 작성일: 20191118
 * 작성자: 오동학
 * 개요: 핸드폰 재고
 * 		- 재고 추가
 * 		- 재고 수정
 * 		- 재고 삭제
 * 		- 재고 저장
 * 		- 재고 불러오기
 */
package oh.donghak.cellphone.domain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class CellphoneHashMap {
	
	Scanner scan = new Scanner(System.in);
	
	private static CellphoneHashMap phoneHashMap = new CellphoneHashMap();
	
	private HashMap<Integer, Cellphone> phoneList;
	
	private CellphoneHashMap () {
		phoneList = new HashMap<> ();
	}
	
	public static CellphoneHashMap getInstance() {
		if( phoneHashMap == null) {
			phoneHashMap = new CellphoneHashMap();
		}
		return phoneHashMap; 
	}
	
	public void addPhone(Cellphone phone) {
		phoneList.put(phone.getRegiNum(), phone);
	}
	
	// 재고에 핸드폰 올리기
	public void addCellphone() {
		System.out.println("=========핸드폰 등록=========");
		System.out.print("등록번호 : ");
		String regi = scan.next();
		int regiNum = 0;
		try {
			regiNum = Integer.parseInt(regi);
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
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
			return;
		}
		System.out.print("수량 : ");
		String amountStr = scan.next();
		int amount = 0;
		try {
			amount = Integer.parseInt(amountStr);
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
		} catch(InputMismatchException e) {
			System.out.println("숫자만 입력해주세요 제발!");
		}
		
		if(delNum != 0) {
			try {
				phoneList.remove(delNum);
				System.out.println("==========핸드폰 삭제===========");
				System.out.println("\t"+delNum+"번의 핸드폰이 삭제되었습니다.");
				System.out.println("===========================");
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
					phoneHashMap.addPhone(upPhone);
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
}
