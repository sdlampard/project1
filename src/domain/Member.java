package domain;

import java.util.HashMap;
import java.util.Scanner;

public class Member {
	
	Scanner scan = new Scanner(System.in);
	HashMap<String, String> memberList = new HashMap<> ();
	
	public Member() {}
	
	public void addMember() {
		System.out.println("===================");
		System.out.println("회원가입");
		System.out.println("===================");

		System.out.print("ID : ");
		String id = scan.next();
		System.out.print("PW : ");
		String password = scan.next();
		if(memberList.containsKey(id)) {
			System.out.println("이미 만들어진 아이디 입니다.");
			return;
		}
		memberList.put(id, password);
		System.out.println("===================");
		System.out.println("회원가입 완료");
		System.out.println("===================");
	}
	
	public boolean login(String id, String password) {
		if(memberList.containsKey(id)) {
			if(memberList.get(id).equals(password)) {
				System.out.println("==================");
				System.out.println("로그인 되었습니다.");
				System.out.println("==================");
				return true;
			}
		}
		System.out.println("로그인에 실패하셨습니다.");
		return false;
	}
}
