package oh.donghak.bookstore.java;

import java.util.HashMap;
import java.util.Iterator;

import domain.Cellphone;

public class Cart {
	HashMap<Integer, Cellphone> list;
	
	public Cart () {
		list  = new HashMap<> ();
	}
	
	public void cartAdd(Cellphone phone) {
		list.put(phone.getRegiNum(), phone);
	}
	
	public boolean removeCart(int num) {
		if(list.containsKey(num)) {
			list.remove(num);
			return true;
		}

		System.out.println("그런 핸드폰은 없습니다.");
		return false;
	}
	
	public void showAllCart() {
		Iterator<Integer> iter = list.keySet().iterator();

		System.out.println("============장바구니 목록==========");
		System.out.println("번호    제조사   모델명   가격   재고");
		System.out.println("===========================");
		while(iter.hasNext()) {
			System.out.println(list.get(iter.next()));
		}

	}
}
