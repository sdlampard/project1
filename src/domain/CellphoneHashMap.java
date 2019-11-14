package domain;

import java.util.HashMap;
import java.util.Iterator;

public class CellphoneHashMap {
	
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
	
	public void addCellphone(Cellphone phone) {
		phoneList.put(phone.getRegiNum(), phone);
	}
	
	
	public Cellphone getCellphone(int regiNum) {
		if(phoneList.containsKey(regiNum)) {
			return phoneList.get(regiNum);
		}
		return null;
	}
	
	public boolean removePhones(int regiNum) {

		if(phoneList.containsKey(regiNum)) {
			phoneList.remove(regiNum);
			return true;
		}

		System.out.println("그런 핸드폰은 없습니다.");
		return false;
		
	}
	
	public void editCellphone(int regiNum, Cellphone phone) {
		phoneList.replace(regiNum, phone);
	}
	
	public void showAllStacks() {
		Iterator<Integer> iter = phoneList.keySet().iterator();

		System.out.println("======== 재고 목록 ========");
		System.out.println("번호    제조사   모델명   가격   재고");
		System.out.println("===========================");
		while(iter.hasNext()) {
			System.out.println(phoneList.get(iter.next()));
		}
	}
}
