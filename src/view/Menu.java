package view;

public interface Menu {
	int SHOP_LOGIN = 999;
	
	int HOST_BOOK_LIST = 111;
	int HOST_BOOK_ADD = 112;
	int HOST_BOOK_UPDATE = 113;
	int HOST_BOOK_DEL = 114;
	
	int HOST_ORDER_LIST = 121;
	int HOST_ORDER_ADD = 122;
	int HOST_ORDER_DEL = 123;
	
	int GUEST_ORDER_ADD = 211;
	int GUEST_ORDER_DEL = 212;
	
	void printMenu();
}
