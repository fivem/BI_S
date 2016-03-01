package com.hx.bankInterface.dao;

public interface CheckLogDao {
	public int saveCheckFile(StringBuffer sb,String billDate);
	public String getCheckFile(String billDate);
	public int delCheckFile(String billDate);
}
