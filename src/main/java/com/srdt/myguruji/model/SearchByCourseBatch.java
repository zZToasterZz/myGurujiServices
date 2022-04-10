package com.srdt.myguruji.model;

public class SearchByCourseBatch {

	private String courseid;
	private String batchid;
	
	public SearchByCourseBatch() {
		super();
	}

	public SearchByCourseBatch(String courseid, String batchid) {
		super();
		this.courseid = courseid;
		this.batchid = batchid;
	}

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}
	
}
