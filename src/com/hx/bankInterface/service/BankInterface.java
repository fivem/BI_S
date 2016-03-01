package com.hx.bankInterface.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hx.bankInterface.po.BillInfo;
import com.hx.bankInterface.po.CheckBillCodeInfo;
import com.hx.bankInterface.po.CheckBillInfo;
import com.hx.bankInterface.po.Result;
import com.hx.bankInterface.po.RollBackBillInfo;
import com.hx.bankInterface.po.UserInfo;

//@Consumes("application/josn")
//@Produces("application/xml")  
//@Path("/rest")
public interface BankInterface {
	//@POST
	//@Path("/query/{id}")
	public UserInfo Query(String card);
	public Result Charge(BillInfo bill);
	public Result Rollback(RollBackBillInfo rbbInfo);
	public Result CheckBill(CheckBillInfo cbInfo);
	public CheckBillCodeInfo CheckBillCode(CheckBillCodeInfo cbc); 
}
