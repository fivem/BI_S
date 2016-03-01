package com.hx.bankInterface.po;

import java.math.BigDecimal;


/**
 * 
 * @author GHAO QQ:862748943
 *
 */
public class ArrearsInfo {
	/**
	 * ���
	 */
	private String chargeMonth;
	/**
	 * ������
	 */
	private String houseCHID;
	/**
	 * ����
	 */
	private BigDecimal price;
	/**
	 * ���
	 */
	private BigDecimal houseArea;
	/**
	 * �ܽ����������ɽ�
	 */
	private BigDecimal totalAccount;
	/**
	 * ���ɽ�
	 */
	private BigDecimal forfeit;
	
	public String getChargeMonth() {
		return chargeMonth;
	}
	public void setChargeMonth(String chargeMonth) {
		this.chargeMonth = chargeMonth;
	}
	public String getHouseCHID() {
		return houseCHID;
	}
	public void setHouseCHID(String houseCHID) {
		this.houseCHID = houseCHID;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getHouseArea() {
		return houseArea;
	}
	public void setHouseArea(BigDecimal houseArea) {
		this.houseArea = houseArea;
	}
	public BigDecimal getTotalAccount() {
		return totalAccount;
	}
	public void setTotalAccount(BigDecimal totalAccount) {
		this.totalAccount = totalAccount;
	}
	public BigDecimal getForfeit() {
		return forfeit;
	}
	public void setForfeit(BigDecimal forfeit) {
		this.forfeit = forfeit;
	}
	@Override
	public String toString() {
		return "ArrearsInfo [chargeMonth=" + chargeMonth + ", houseCHID="
				+ houseCHID + ", price=" + price + ", houseArea=" + houseArea
				+ ", totalAccount=" + totalAccount + ", forfeit=" + forfeit
				+ "]";
	}
	
}
