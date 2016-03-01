package com.hx.bankInterface.dao;

import com.hx.bankInterface.po.BillInfo;
import com.hx.bankInterface.po.ChargeInfo;

public interface ChargeDao {
	int updateSfBill(BillInfo billCode);
	int updateSfDetail(ChargeInfo chargeInfo);
	int updateSfCharge(ChargeInfo chargeInfo);
	int updateScBolt(ChargeInfo chargeInfo);
	int updateScBoltDetail(ChargeInfo chargeInfo);
}
