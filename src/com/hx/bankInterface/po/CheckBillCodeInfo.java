package com.hx.bankInterface.po;

import java.util.ArrayList;

public class CheckBillCodeInfo extends Result {
	private String billMan;
	private String billDate;
	private ArrayList<String> billCode;
	public String getBillMan() {
		return billMan;
	}
	public void setBillMan(String billMan) {
		this.billMan = billMan;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public ArrayList<String> getBillCode() {
		return billCode;
	}
	public void setBillCode(ArrayList<String> billCode) {
		this.billCode = billCode;
	}
	@Override
	public String toString() {
		return "CheckBillCode [billMan=" + billMan + ", billDate=" + billDate
				+ ", billCode=" + billCode + "]";
	}
}
