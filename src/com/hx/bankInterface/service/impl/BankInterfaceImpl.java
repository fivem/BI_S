package com.hx.bankInterface.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.jws.WebMethod;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.hx.bankInterface.dao.ChargeDao;
import com.hx.bankInterface.dao.CheckBillDao;
import com.hx.bankInterface.dao.CheckLogDao;
import com.hx.bankInterface.dao.QueryDao;
import com.hx.bankInterface.dao.RollBackDao;
import com.hx.bankInterface.po.ArrearsInfo;
import com.hx.bankInterface.po.BillInfo;
import com.hx.bankInterface.po.ChargeInfo;
import com.hx.bankInterface.po.CheckBillCodeInfo;
import com.hx.bankInterface.po.CheckBillInfo;
import com.hx.bankInterface.po.Result;
import com.hx.bankInterface.po.RollBackBillInfo;
import com.hx.bankInterface.po.UserInfo;
import com.hx.bankInterface.service.BankInterface;
import com.hx.bankInterface.util.ReadProperties;

//@Controller
//����ע�������⣺is not an instance of the actual controller bean instance 
//@EnableAspectJAutoProxy(proxyTargetClass = true)
//@RequestMapping(value = "**/rest")
public class BankInterfaceImpl implements BankInterface {
	private static Logger logger = Logger.getLogger(BankInterfaceImpl.class);
	private static ReadProperties rs = new ReadProperties();

	@Autowired
	private QueryDao query;

	@Autowired
	private RollBackDao rollback;

	@Autowired
	private CheckBillDao checkbill;

	@Autowired
	private ChargeDao charge;

	@Autowired
	private CheckLogDao checkLogDao;
	
	private BigDecimal currentarrears = BigDecimal.ZERO;
	private BigDecimal prearrears = BigDecimal.ZERO;
	private BigDecimal multiarea = BigDecimal.ZERO;

	/**
	 * 
	 * @param card
	 * @return
	 */
	@WebMethod
	//@RequestMapping(value ={"/query/{id}"}, method = RequestMethod.GET)
	public UserInfo Query(@PathVariable("id") String card) {
		logger.error("query card:"+card);
		String chargeMonth = query.queryChargeMonth();

		UserInfo ui = new UserInfo();

		ArrayList<HashMap<String, Object>> exists = query.queryExists(card,
				chargeMonth);

		if (exists == null || exists.size() == 0) {
			ui.setReturnCode("01");
			ui.setReturnInfo("�û�������");
			return ui;
		}

		for (HashMap<String, Object> map : exists) {
			currentarrears = (BigDecimal) map.get("QF");
			prearrears = (BigDecimal) map.get("OLDCOUNT");
			multiarea = (BigDecimal) map.get("CHAYEARCOUNT");

			ui.setUserCard((String) map.get("HOUSECODE"));
			ui.setUserName((String) map.get("OWNERNAME"));
			ui.setUserAddress((String) map.get("ADDRESS"));
		}

		if ("OPEN".equals(ReadProperties.prearrears)) {
			if (BigDecimal.ZERO.compareTo(prearrears) < 0) {
				ui.setReturnCode("02");
				ui.setReturnInfo("�г�Ƿ������");
				return ui;
			}
		}

		if ("OPEN".equals(ReadProperties.multiarea)) {
			if (BigDecimal.ZERO.compareTo(multiarea) < 0) {
				ui.setReturnCode("03");
				ui.setReturnInfo("�����������");
				return ui;
			}
		}

		if ("OPEN".equals(ReadProperties.stopflag)) {
			int stop = query.queryStopheating(chargeMonth);
			if (stop > 0) {
				ui.setReturnCode("04");
				ui.setReturnInfo("�û���ͣ��");
				return ui;
			}
		}

		ArrayList<ArrearsInfo> arr = query.queryByCard(card, chargeMonth);

		ui.setArrearsInfo(arr);

		ui.setReturnCode("00");
		ui.setReturnInfo("�ɹ�");

		return ui;
	}

	/**
	 * 
	 * @param cha
	 * @return
	 */
//	@SuppressWarnings("finally")
	//@RequestMapping(value ={"/charge1"}, method = RequestMethod.POST)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Result Charge(@RequestBody BillInfo billInfo) {
		Result ret = new Result();
		String chargeMonth = query.queryChargeMonth();
		// ��ѯ
		UserInfo ui = Query(billInfo.getUserCard());
		if (ui == null) {
			ret.setReturnCode("01");
			ret.setReturnInfo("�û�������");
			return ret;
		}
		if (!"00".equals(ui.getReturnCode())) {
			ret.setReturnCode(ui.getReturnCode());
			ret.setReturnInfo(ui.getReturnInfo());
			return ret;
		}
		for (ArrearsInfo ai : ui.getArrearsInfo()) {
			ChargeInfo ci = new ChargeInfo();

			try {
				BeanUtils.copyProperties(ci, ai);
				// 2015.12.02����
				// System.out.println(ci.toString());

				BeanUtils.copyProperties(ci, billInfo);
				// 2015.12.02����
				// System.out.println(ci);
			} catch (IllegalAccessException | InvocationTargetException e1) {
				e1.printStackTrace();
			}
			// �����շѼƻ�
			int sf_charge = charge.updateSfCharge(ci);
			// �����շ���ϸ
			int sf_detail = charge.updateSfDetail(ci);
			// �ǵ�ǰ��Ȳ�������˨����
			if (chargeMonth.equals(ci.getChargeMonth())) {
				// ��ѯ��ǰ��������Ƿ�ͣ��
				int stopFlag = query.getStopFlag(ci);
				if (stopFlag == 0) {
					// �´￪˨��
					int sc_bolt = charge.updateScBolt(ci);
					int sc_boltDetail = charge.updateScBoltDetail(ci);
				}

			}

			if (!(sf_detail > 0 && sf_charge > 0)) {
				try {
					throw new Exception(new RuntimeException());
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					return ui;
				}
			}
		}

		// �����շѵ�
		int sf_bill = charge.updateSfBill(billInfo);

		if (!(sf_bill > 0)) {
			try {
				throw new Exception(new RuntimeException());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				return ui;
			}
		}
		ui.setReturnCode("00");
		ui.setReturnInfo("�ɹ�");
		return ui;
	}
	/**
	 * 
	 * @param billCode
	 * @return
	 */
//	@SuppressWarnings("finally")
//	@WebMethod
	@RequestMapping(value ={"/rollback"}, method = RequestMethod.POST)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Result Rollback(@RequestBody RollBackBillInfo rollbackbill) {
		Result ret = new Result();
		ArrayList<HashMap<String, Object>> exists = rollback
				.queryByBillCode(rollbackbill);
		if (exists == null || exists.size() == 0) {
			ret.setReturnCode("05");
			ret.setReturnInfo("ԭ���ײ�����");
			return ret;
		}
		for (HashMap<String, Object> hm : exists) {
			if ("1".equals(hm.get("ROLLBACKFLAG"))) {
				ret.setReturnCode("06");
				ret.setReturnInfo("ԭ�����ѳ���");
				return ret;
			}
			if (rollbackbill.getBillDate() == null
					|| !rollbackbill.getBillDate().equals(hm.get("BILLDATE"))) {
				ret.setReturnCode("07");
				ret.setReturnInfo("��ֹ���ճ���");
				return ret;
			}
		}

		int sf_bill = rollback.updateSfBill(rollbackbill);
		int sf_detail = rollback.updateSfDetail(rollbackbill);
		int sf_charge = rollback.updateSfCharge(rollbackbill);
		int sc_bolt = rollback.updateScBolt(rollbackbill);
		int sc_boltDetail = rollback.updateScBoltDetail(rollbackbill);
		if (!(sf_bill > 0 && sf_detail > 0 && sf_charge > 0 && sc_bolt > 0 && sc_boltDetail > 0)) {
			try {
				ret.setReturnCode("99");
				ret.setReturnInfo("ҵ�����쳣");
				throw new Exception(new RuntimeException());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				return ret;
			}
		}

		ret.setReturnCode("00");
		ret.setReturnInfo("�ɹ�");
		return ret;
	}

	/**
	 * 
	 * @param billCheck
	 * @return
	 */
	@WebMethod
	//@RequestMapping(value ={"/check"}, method = RequestMethod.POST)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Result CheckBill(@RequestBody CheckBillInfo cbInfo) {
		Result ret = new Result();
		String billDate = cbInfo.getBillDate();
		String billMan = cbInfo.getBillMan();
		String chargeMonth = query.queryChargeMonth();
		//�洢�ĵ���
		Gson gson = new Gson();
		
		
		
		String path=Thread.currentThread().getContextClassLoader().getResource("").toString();  
        
        path=path.replace("file:", ""); //ȥ��file:  
        path=path.replace("classes/", ""); //ȥ��class\  
        path=path.substring(1); //ȥ����һ��\,�� \D:\JavaWeb...
		path =  path+"CheckFile";
		
        String file ="/billMan."+cbInfo.getBillDate()+".txt";
		try {
			// ��ȡ�������ļ�
			InputStream is = new ByteArrayInputStream(cbInfo.getFile()); 
			if ("DOC".equals(ReadProperties.fileMode)) {
				OutputStream os = new FileOutputStream(new File(path + file));
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				byte[] b = new byte[1024 * 4];
				int kobe = 0;
				while((kobe = is.read(b)) != -1) {
					// ������ĵ�
					os.write(b, 0, kobe);
				}
				is.close();
				os.close();
			}
			if("SQL".equals(ReadProperties.fileMode)){
				byte[] b = new byte[1024 * 4];
				int kobe = 0;
				StringBuffer sb = new StringBuffer();
				while((kobe = is.read(b)) != -1) {
					// ƴ���ַ���
					sb.append(new String(b, "UTF-8"));
				}
				checkLogDao.delCheckFile(billDate);
				checkLogDao.saveCheckFile(sb,billDate);
			}
			InputStreamReader read = new InputStreamReader(is, "UTF-8");
			BufferedReader reader = new BufferedReader(read);
			String line;

			// ѭ����ÿ�ζ�һ��
			while ((line = reader.readLine()) != null) {
				// jsonToObject
				BillInfo bi = gson.fromJson(line.trim(), BillInfo.class);

				if (!processBillCode(bi.getBillCode(), chargeMonth)) {
					Result result = Charge(bi);
					if (!"00".equals(ret.getReturnCode())) {
						// TODO
						// ��¼��־
					}
				}
			}
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ���˳���
		ArrayList<RollBackBillInfo> rbb = checkbill.getRollBack(billDate,
				billMan);
		for (RollBackBillInfo r : rbb) {
			Result retsult = Rollback(r);
			if (!"00".equals(ret.getReturnCode())) {
				// TODO ����ʧ�� ��¼��־
			}
		}
		ret.setReturnCode("00");
		ret.setReturnInfo("�ɹ�");
		return ret;
	}

	/**
	 * ֻ���������ˮ��
	 */
	@WebMethod
	//@RequestMapping(value ={"/checkCode"}, method = RequestMethod.POST)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public CheckBillCodeInfo CheckBillCode(@RequestBody CheckBillCodeInfo cbc) {
		ArrayList<String> notExists = new ArrayList<String>();
		String billDate = cbc.getBillDate();
		String billMan = cbc.getBillMan();
		String chargeMonth = query.queryChargeMonth();
		ArrayList<String> billCodes = cbc.getBillCode();
		for (String bc : billCodes) {
			if (!processBillCode(bc, chargeMonth)) {
				notExists.add(bc);
			}
		}

		// ���˳���
		ArrayList<RollBackBillInfo> rbb = checkbill.getRollBack(billDate,
				billMan);
		for (RollBackBillInfo r : rbb) {
			Result ret = Rollback(r);
			if (!"00".equals(ret.getReturnCode())) {
				// TODO ����ʧ�� ��¼��־
			}
		}

		cbc.setReturnCode("00");
		cbc.setReturnInfo("�ɹ�");
		cbc.setBillDate(billDate);
		cbc.setBillMan(billMan);
		cbc.setBillCode(notExists);
		return cbc;
	}

	/**
	 * ���˹��߷�����������billcode
	 * 
	 * @param bc
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean processBillCode(String bc, String chargemonth) {
		// check
		int count = checkbill.checkBillCode(bc);
		// ������
		if (count == 0) {
			int rollBackFlag = checkbill.checkRollBack(bc);
			if (rollBackFlag > 0) {
				// ���˻ָ�
				int updateSfBill = checkbill.updateSfBill(bc);
				int updateSfDetail = checkbill.updateSfDetail(bc);
				int updateSfCharge = checkbill.updateSfCharge(bc);
				int updateScBolt = checkbill.updateScBolt(bc, chargemonth);
				int updateScDetail = checkbill.updateScDetail(bc, chargemonth);
				return true;
			} else {
				// ��¼��־����ѯʧ��billCode
				return false;
			}
		} else {
			// ��ƽ�˱�ʶ
			int switchFlag = checkbill.switchBankFlag(bc);
			return true;
		}
	}

}
