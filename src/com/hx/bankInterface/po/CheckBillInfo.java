package com.hx.bankInterface.po;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class CheckBillInfo extends UserInfo{
	private String billDate;
	private String billMan;
	private byte[] file;
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getBillMan() {
		return billMan;
	}
	public void setBillMan(String billMan) {
		this.billMan = billMan;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	@Override
	public String toString() {
		return "CheckBillInfo [billDate=" + billDate + ", billMan=" + billMan
				+ ", file=" + Arrays.toString(file) + "]";
	}
	
}
