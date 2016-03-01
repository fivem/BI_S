package com.hx.bankInterface.po;

import java.util.ArrayList;
/**
 * 用户欠费信息
 * @author GHAO QQ：862748943
 *
 */
public class UserInfo extends Result {
	/**
	 * 用户卡号
	 */
	private String userCard;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 用户地址
	 */
	private String userAddress;
	/**
	 * 欠费信息
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
