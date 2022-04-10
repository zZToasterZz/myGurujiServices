package com.srdt.myguruji.model;

public class Batches {
	
	private long courseid,batchid;
	private String title,batchcode,descr,createdby,type,year,seq,section;
	public Batches() {
		super();
	}
	
	public Batches(String title, String descr, String createdby, String type,String section) {
		super();
		this.title = title;
		this.descr = descr;
		this.createdby = createdby;
		this.type = type;
		this.section=section;
	}
    
	public Batches(long courseid, long batchid, String title, String batchcode, String descr, String createdby,
			String type,String section) {
		super();
		this.courseid = courseid;
		this.batchid = batchid;
		this.title = title;
		this.batchcode = batchcode;
		this.descr = descr;
		this.createdby = createdby;
		this.type = type;
		this.section=section;
	}

	public Batches(long courseid, long batchid, String title, String batchcode, String descr, String createdby,
			String type, String year, String seq,String section) {
		super();
		this.courseid = courseid;
		this.batchid = batchid;
		this.title = title;
		this.batchcode = batchcode;
		this.descr = descr;
		this.createdby = createdby;
		this.type = type;
		this.year = year;
		this.seq = seq;
		this.section=section;
	}

	public String getSection() {
		return section != null ? section : "";
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public long getCourseid() {
		return courseid;
	}
	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}
	public long getBatchid() {
		return batchid;
	}
	public void setBatchid(long batchid) {
		this.batchid = batchid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBatchcode() {
		return batchcode;
	}
	public void setBatchcode(String batchcode) {
		this.batchcode = batchcode;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}    
}
