package domain;

import java.util.ArrayList;
import java.util.List;

public class OrderList {
	private List<Order> orders;
	
	public OrderList() {
		orders= new ArrayList<Order> ();
	}
	
	public void addOrder(Order order) {
		orders.add(order);
	}
	
	public void removeOrder(Order order) {
		
	}
	
	public void showAllOrders() {
		
	}
	
}