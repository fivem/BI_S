package com.hx.bankInterface.po;

import java.util.ArrayList;
/**
 * �û�Ƿ����Ϣ
 * @author GHAO QQ��862748943
 *
 */
public class UserInfo extends Result {
	/**
	 * �û�����
	 */
	private String userCard;
	/**
	 * �û�����
	 */
	private String userName;
	/**
	 * �û���ַ
	 */
	private String userAddress;
	/**
	 * Ƿ����Ϣ
	 */
	private ArrayList<ArrearsInfo> arrearsInfo;
	
	public String getUserCard() {
		return userCard;
	}
	public void setUserCard(String userCard) {
		this.userCard = userCard;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public ArrayList<ArrearsInfo> getArrearsInfo() {
		return arrearsInfo;
	}
	public void setArrearsInfo(ArrayList<ArrearsInfo> arrearsInfo) {
		this.arrearsInfo = arrearsInfo;
	}
	@Override
	public String toString() {
		return "UserInfo [userCard=" + userCard + ", userName=" + userName
				+ ", userAddress=" + userAddress + ", arrearsInfo="
				+ arrearsInfo + "]";
	}
}
