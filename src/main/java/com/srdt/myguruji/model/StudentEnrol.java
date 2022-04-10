package com.srdt.myguruji.model;

public class StudentEnrol {

	private long studentid;
	private long batchid;
	private long enrollmentid;
	private String createdby;
	public StudentEnrol() {
		super();
	}

	public StudentEnrol(long studentid, long batchid, long enrollmentid,String createdby) {
		super();
		this.studentid = studentid;
		this.batchid = batchid;
		this.enrollmentid = enrollmentid;
		this.createdby = createdby;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public long getStudentid() {
		return studentid;
	}

	public void setStudentid(long studentid) {
		this.studentid = studentid;
	}

	public long getBatchid() {
		return batchid;
	}

	public void setBatchid(long batchid) {
		this.batchid = batchid;
	}

	public long getEnrollmentid() {
		return enrollmentid;
	}

	public void setEnrollmentid(long enrollmentid) {
		this.enrollmentid = enrollmentid;
	}	
}