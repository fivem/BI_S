package com.hx.bankInterface.po;

public class RollBackBillInfo extends UserInfo {
	private String billDate;
	private String oldBillCode;
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getOldBillCode() {
		return oldBillCode;
	}
	public void setOldBillCode(String oldBillCode) {
		this.oldBillCode = oldBillCode;
	}
	@Override
	public String toString() {
		return "BillInfo [billDate="
				+ billDate + ", oldBillCode=" + oldBillCode + "]";
	}
	
	
}
