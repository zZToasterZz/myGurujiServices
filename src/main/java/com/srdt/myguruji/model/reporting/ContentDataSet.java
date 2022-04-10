package com.srdt.myguruji.model.reporting;

import java.util.ArrayList;
import java.util.List;

public class ContentDataSet {
	
	private String pnum;
	private String crsecode;
	private List<String> batchid=new ArrayList<>();
	
	public ContentDataSet() {
		super();
	}
	public ContentDataSet(String pnum, String crsecode, List<String> batchid) {
		super();
		this.pnum = pnum;
		this.crsecode = crsecode;
		this.batchid = batchid;
	}
	public String getPnum() {
		return pnum;
	}
	public void setPnum(String pnum) {
		this.pnum = pnum;
	}
	public String getCrsecode() {
		return crsecode;
	}
	public void setCrsecode(String crsecode) {
		this.crsecode = crsecode;
	}
	public List<String> getBatchid() {
		return batchid;
	}
	public void setBatchid(List<String> batchid) {
		this.batchid = batchid;
	}
	@Override
	public String toString() {
		return "ContentDataSet [pnum=" + pnum + ", crsecode=" + crsecode + ", batchid=" + batchid + "]";
	}

}
