package com.srdt.myguruji.model;

public class AssignmentStatusDetailsModel
{
	private long assignmentStatusID;
	private long assignmentid;
	private long studentid;
	private String timingstatus;
	private String submitstatus;
	private String evaluationstatus;
	
	public AssignmentStatusDetailsModel() {
		super();
	}
	public AssignmentStatusDetailsModel(long assignmentStatusID, long assignmentid, long studentid, String timingstatus,
			String submitstatus, String evaluationstatus) {
		super();
		this.assignmentStatusID = assignmentStatusID;
		this.assignmentid = assignmentid;
		this.studentid = studentid;
		this.timingstatus = timingstatus;
		this.submitstatus = submitstatus;
		this.evaluationstatus = evaluationstatus;
	}
	public long getAssignmentStatusID() {
		return assignmentStatusID;
	}
	public void setAssignmentStatusID(long assignmentStatusID) {
		this.assignmentStatusID = assignmentStatusID;
	}
	public long getAssignmentid() {
		return assignmentid;
	}
	public void setAssignmentid(long assignmentid) {
		this.assignmentid = assignmentid;
	}
	public long getStudentid() {
		return studentid;
	}
	public void setStudentid(long studentid) {
		this.studentid = studentid;
	}
	public String getTimingstatus() {
		return timingstatus;
	}
	public void setTimingstatus(String timingstatus) {
		this.timingstatus = timingstatus;
	}
	public String getSubmitstatus() {
		return submitstatus;
	}
	public void setSubmitstatus(String submitstatus) {
		this.submitstatus = submitstatus;
	}
	public String getEvaluationstatus() {
		return evaluationstatus;
	}
	public void setEvaluationstatus(String evaluationstatus) {
		this.evaluationstatus = evaluationstatus;
	}
}