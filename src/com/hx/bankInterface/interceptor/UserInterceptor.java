package com.hx.bankInterface.interceptor;

import java.util.List;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.hx.bankInterface.dao.LoginDao;
import com.hx.bankInterface.util.ReadProperties;
 
public class UserInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
	private static Logger logger = Logger.getLogger(UserInterceptor.class);
	
	@Autowired
	private LoginDao loginDao;
 
	public UserInterceptor() {
		super(Phase.PRE_PROTOCOL);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void handleMessage(SoapMessage msg) throws Fault {
		// TODO Auto-generated method stub
		  logger.debug(msg);

		  List<Header> headers = msg.getHeaders();
		  if(headers == null || headers.size() == 0){
			  throw new Fault(new IllegalArgumentException("空的消息头"));
		  }else{
			  for(Header h : headers){
				  Element headerEle = (Element)h.getObject();
				  NodeList usernameNode = headerEle.getElementsByTagName("username");
				  NodeList password = headerEle.getElementsByTagName("password");
				  if(usernameNode.getLength()==0 || password.getLength()==0){
					  throw new Fault(new IllegalArgumentException("输入口令"));
				  }
				  if(!checkUser(headerEle)){
					  throw new Fault(new IllegalArgumentException("口令错误"));
				  } 
			  }
		  }
	}
	
	private boolean checkUser(Element e){
		String username = e.getElementsByTagName("username").item(0).getTextContent() ;
		String password = e.getElementsByTagName("password").item(0).getTextContent();
		int login = loginDao.getUser(username, password);
		if(login > 0){
			return true;
		}
		return false;
	}
}
