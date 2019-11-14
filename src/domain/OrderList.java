package domain;

import java.util.HashMap;
import java.util.Iterator;

public class OrderList {
	
	private HashMap<Integer, Order> orderMap;
	private int profit = 0;
	
	public OrderList () {
		orderMap = new HashMap<> ();
	}
	
	public void approveOrder(int orderNum) {
		if(orderMap.containsKey(orderNum)) {
			Order order = orderMap.get(orderNum);
			profit -= (order.getCellphone().getAmount() * order.getCellphone().getPrice());
		}
	}
	
	public void cancelOrder(int orderNum) {
		if(orderMap.containsKey(orderNum) && orderMap.get(orderNum).isRefund() == false) {
			Order order = orderMap.get(orderNum);
			profit -= (order.getCellphone().getAmount() * order.getCellphone().getPrice());
			orderMap.get(orderNum).setRefund(true);
		}
	}
	
	public void showAllOrders() {
		Iterator<Integer> iter = orderMap.keySet().iterator();
		
		while(iter.hasNext()) {
			System.out.println(orderMap.get(iter.next()));
		}
	}
	
}
