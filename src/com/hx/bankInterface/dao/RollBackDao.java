package com.hx.bankInterface.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.hx.bankInterface.po.Result;
import com.hx.bankInterface.po.RollBackBillInfo;

public interface RollBackDao {
	ArrayList<HashMap<String,Object>> queryByBillCode(RollBackBillInfo rbbInfo);
	Result rollBack(RollBackBillInfo rbbInfo);
	int updateSfBill(RollBackBillInfo rbbInfo);
	int updateSfDetail(RollBackBillInfo rbbInfo);
	int updateSfCharge(RollBackBillInfo rbbInfo);
	int updateScBolt(RollBackBillInfo rbbInfo);
	int updateScBoltDetail(RollBackBillInfo rbbInfo);
}
