package com.hx.bankInterface.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {
	//publish information
	public static String address="";
	public static String port="";
	
	//user information
	public static String user="";
	public static String password="";
	
	//Business information
	public static String stopflag="";
	public static String prearrears="";
	public static String multiarea="";
	public static String fileMode="DOC";
	
	public void readProp(){
		Properties prop =  new  Properties();    
	    InputStream in = Object. class .getResourceAsStream( "/config.properties" );    
	     try  {    
	        prop.load(in);    
	        address  = prop.getProperty("address").trim();    
	        port  = prop.getProperty("port").trim();
	        user = prop.getProperty("username").trim();
	        password = prop.getProperty("password").trim();
	        stopflag = prop.getProperty("stopflag").trim();
	        prearrears = prop.getProperty("prearrears").trim();
	        multiarea = prop.getProperty("multiarea").trim();
	        fileMode = prop.getProperty("fileMode").trim();
	     }  catch  (IOException e) {    
	        e.printStackTrace();    
	    }   
	}
}
