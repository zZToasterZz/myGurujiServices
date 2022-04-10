package com.srdt.myguruji.model;

import java.util.Date;
import java.util.List;

public class LockedStatusResponse
{
	private long lockstatusid;
	private long courseid;
	private long batchid;
	private long studentid;
	private String lockstatus;
	private Date created_date;
	private String reason;
	private String resolve;
	
	public LockedStatusResponse() {
		super();
	}
	public long getLockstatusid() {
		return lockstatusid;
	}
	public void setLockstatusid(long lockstatusid) {
		this.lockstatusid = lockstatusid;
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
	public long getStudentid() {
		return studentid;
	}
	public void setStudentid(long studentid) {
		this.studentid = studentid;
	}
	public String getLockstatus() {
		return lockstatus;
	}
	public void setLockstatus(String lockstatus) {
		this.lockstatus = lockstatus;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public LockedStatusResponse(long lockstatusid, long courseid, long batchid, long studentid, String lockstatus,
			Date created_date, String reason, String resolve) {
		super();
		this.lockstatusid = lockstatusid;
		this.courseid = courseid;
		this.batchid = batchid;
		this.studentid = studentid;
		this.lockstatus = lockstatus;
		this.created_date = created_date;
		this.reason = reason;
		this.resolve = resolve;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getResolve() {
		return resolve;
	}
	public void setResolve(String resolve) {
		this.resolve = resolve;
	}
}