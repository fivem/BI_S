package com.hx.bankInterface.po;

public class BillInfo extends Result{
	
	private String billCode;
	private String billDate;
	private String userCard;
	private String payMode;
	private String billMan;
	
	public String getBillCode() {
		return billCode;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getUserCard() {
		return userCard;
	}
	public void setUserCard(String userCard) {
		this.userCard = userCard;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public String getBillMan() {
		return billMan;
	}
	public void setBillMan(String billMan) {
		this.billMan = billMan;
	}
	@Override
	public String toString() {
		return "BillInfo [billCode=" + billCode + ", billDate=" + billDate
				+ ", userCard=" + userCard + ", payMode=" + payMode
				+ ", billMan=" + billMan + "]";
	}
	
}
