package com.srdt.myguruji.model;

public class FacultyTag {
	private long tagid;
	private long facultyid;
	private long batchid;
	private String createdby;
	
	public FacultyTag() {
		super();
	}

	public FacultyTag(long tagid, long facultyid, long batchid,String createdby) {
		super();
		this.tagid = tagid;
		this.facultyid = facultyid;
		this.batchid = batchid;
		this.createdby = createdby;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public long getTagid() {
		return tagid;
	}

	public void setTagid(long tagid) {
		this.tagid = tagid;
	}

	public long getFacultyid() {
		return facultyid;
	}

	public void setFacultyid(long facultyid) {
		this.facultyid = facultyid;
	}

	public long getBatchid() {
		return batchid;
	}

	public void setBatchid(long batchid) {
		this.batchid = batchid;
	}
}
