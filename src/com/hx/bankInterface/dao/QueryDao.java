package com.hx.bankInterface.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.hx.bankInterface.po.ArrearsInfo;
import com.hx.bankInterface.po.ChargeInfo;

public interface QueryDao {
	ArrayList<ArrearsInfo> queryByCard(String userCard,String chargeMonth);
	ArrayList<HashMap<String,Object>> queryExists(String userCard,String chargeMonth);
	int queryStopheating(String chargeMonth);
	String queryChargeMonth();
	int queryMultiArea(String userCard);
	int queryPreArrears(String card,String chargeMonth);
	int getStopFlag(ChargeInfo ci);
}
