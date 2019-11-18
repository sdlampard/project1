package oh.donghak.cellphone.service;

import java.io.IOException;

public interface Host {
	String ID = "host";
	String PASSWORD = "host";
	
	void login() throws IOException;
	int managerMenu();
	int stockMenu();
	int orderMenu();
	void menu() throws IOException;
}
