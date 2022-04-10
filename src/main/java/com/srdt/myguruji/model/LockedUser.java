package com.srdt.myguruji.model;

public class LockedUser
{
	private long lockstatusid;
	private long courseid;
	private long batchid;
	private long studentid;
	private String lockstatus;
	private long lockId;
	private String reason;
	private String resolve;
	private String createdby;
	private String campusid;
	private String fullname;
	
	public LockedUser() {
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
	@Override
	public String toString() {
		return "LockedUser [lockstatusid=" + lockstatusid + ", courseid=" + courseid + ", batchid=" + batchid
				+ ", studentid=" + studentid + ", lockstatus=" + lockstatus + "]";
	}
	public long getLockId() {
		return lockId;
	}
	public void setLockId(long lockId) {
		this.lockId = lockId;
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
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public LockedUser(long lockstatusid, long courseid, long batchid, long studentid, String lockstatus, long lockId,
			String reason, String resolve, String createdby) {
		super();
		this.lockstatusid = lockstatusid;
		this.courseid = courseid;
		this.batchid = batchid;
		this.studentid = studentid;
		this.lockstatus = lockstatus;
		this.lockId = lockId;
		this.reason = reason;
		this.resolve = resolve;
		this.createdby = createdby;
	}

	public String getCampusid() {
		return campusid;
	}

	public void setCampusid(String campusid) {
		this.campusid = campusid;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
}