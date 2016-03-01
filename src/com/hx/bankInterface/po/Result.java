package com.hx.bankInterface.po;

public class Result {
	
	private String returnCode;
	private String returnInfo;
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnInfo() {
		return returnInfo;
	}
	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}
	@Override
	public String toString() {
		return "Result [returnCode=" + returnCode + ", returnInfo="
				+ returnInfo + "]";
	}
	
	 
}
