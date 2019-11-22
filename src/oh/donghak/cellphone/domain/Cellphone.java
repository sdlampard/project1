/*
 * 작성일: 20191118
 * 작성자: 오동학
 * 개요: 핸드폰 클래스
 */
package oh.donghak.cellphone.domain;

public class Cellphone {
//맴버변수 4~5개
	private int regiNum; // 등록번호
	private String brand; // 브랜드
	private String modelName; // 모델명
	private int price; // 가격
	private int amount; // 수량
	
	public Cellphone() {}
	
	public Cellphone(int regiNum, String brand, String modelName, int price, int amount) {
		this.regiNum = regiNum;
		this.brand = brand;
		this.modelName = modelName;
		this.price = price;
		this.amount = amount;
	}
	
	public int getRegiNum() {
		return regiNum;
	}

	public void setRegiNum(int regiNum) {
		this.regiNum = regiNum;
	}

	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public String toString() {
		return regiNum+"\t"+brand+"\t"+modelName+"\t"+price+"\t"+amount;
	}
}
