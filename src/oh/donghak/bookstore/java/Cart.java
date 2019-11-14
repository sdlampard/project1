package oh.donghak.bookstore.java;

import java.util.ArrayList;

import domain.Cellphone;

public class Cart {
	ArrayList<Cellphone> list;
	
	public Cart () {
		list  = new ArrayList<Cellphone> ();
	}
	
	public void cartAdd(Cellphone phone) {
		list.add(phone);
	}
	
	public boolean removeCart(int num) {
		return false;
	}
}
