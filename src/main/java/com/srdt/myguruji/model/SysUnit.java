package com.srdt.myguruji.model;

public class SysUnit {
	
	private long sysgenid;
	private long courseid;
	private String sysunitid;
	private String title;
	private String descr;
	public SysUnit() {
		super();
	}
	
	public SysUnit(long sysgenid, long courseid, String sysunitid, String title, String descr) {
		super();
		this.sysgenid = sysgenid;
		this.courseid = courseid;
		this.sysunitid = sysunitid;
		this.title = title;
		this.descr = descr;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public long getSysgenid() {
		return sysgenid;
	}
	public void setSysgenid(long sysgenid) {
		this.sysgenid = sysgenid;
	}
	public long getCourseid() {
		return courseid;
	}
	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}
	public String getSysunitid() {
		return sysunitid;
	}
	public void setSysunitid(String sysunitid) {
		this.sysunitid = sysunitid;
	}
}
