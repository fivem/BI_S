package com.hx.bankInterface.dao;

import java.util.ArrayList;

import com.hx.bankInterface.po.Result;
import com.hx.bankInterface.po.RollBackBillInfo;

public interface CheckBillDao {
	int checkBillCode(String billCode);
	int checkRollBack(String billCode);
	int revokeRollBack(String billCode);
	int switchBankFlag(String billCode);
	ArrayList<RollBackBillInfo> getRollBack(String billDate,String billMan);
	
	int updateSfBill(String billCode);
	int updateSfDetail(String billCode);
	int updateSfCharge(String billCode);
	int updateScBolt(String billCode,String chargeMonth);
	int updateScDetail(String billCode,String chargeMonth);
}
