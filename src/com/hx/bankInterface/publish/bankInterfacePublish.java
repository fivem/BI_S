package com.hx.bankInterface.publish;

 
import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;

import com.hx.bankInterface.service.impl.BankInterfaceImpl;
import com.hx.bankInterface.util.ReadProperties;

public class bankInterfacePublish {
	private static Logger logger = Logger.getLogger(bankInterfacePublish.class);
	private static ReadProperties rs = new ReadProperties();
	private static String pubStr = "";
	
	public static void main(String[] args) {
		logger.debug("+++++++++++++++++");
		rs.readProp();
		pubStr = ReadProperties.address+':'+ ReadProperties.port+"/BI";
		logger.debug(pubStr);
//		Endpoint.publish(pubStr,new BankInterfaceImpl());
		System.out.println("BIÒÑÆô¶¯...");
	}
}
