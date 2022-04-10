package com.srdt.myguruji.model;

import java.util.List;

public class AssessmentSchedule {
	
	private long courseid;
	private long scheduledid;
	private long assessmentid;
	private String startdate;
	private String enddate;
	private String startdatetime;
	private String enddatetime;
	private String descr;
    private List<Long> batchids;
    private String createdby;
    private int duration;
    private String responsestatus;
    private String Comment;
    
	public String getResponsestatus() {
		return responsestatus;
	}
	public void setResponsestatus(String responsestatus) {
		this.responsestatus = responsestatus;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	public AssessmentSchedule(long courseid, long scheduledid, long assessmentid, String startdate, String enddate,
			String startdatetime, String enddatetime, String descr, List<Long> batchids, String createdby, int duration,
			String responsestatus, String comment) {
		super();
		this.courseid = courseid;
		this.scheduledid = scheduledid;
		this.assessmentid = assessmentid;
		this.startdate = startdate;
		this.enddate = enddate;
		this.startdatetime = startdatetime;
		this.enddatetime = enddatetime;
		this.descr = descr;
		this.batchids = batchids;
		this.createdby = createdby;
		this.duration = duration;
		this.responsestatus = responsestatus;
		Comment = comment;
	}
	public AssessmentSchedule() {
		super();
	}
	public AssessmentSchedule(long courseid, long scheduledid, long assessmentid, String startdate, String enddate,
			String startdatetime, String enddatetime, String descr, List<Long> batchids,String createdby,int duration) {
		super();
		this.duration = duration;
		this.courseid = courseid;
		this.scheduledid = scheduledid;
		this.assessmentid = assessmentid;
		this.startdate = startdate;
		this.enddate = enddate;
		this.startdatetime = startdatetime;
		this.enddatetime = enddatetime;
		this.descr = descr;
		this.batchids = batchids;
		this.createdby = createdby;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public long getCourseid() {
		return courseid;
	}
	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}
	public long getScheduledid() {
		return scheduledid;
	}
	public void setScheduledid(long scheduledid) {
		this.scheduledid = scheduledid;
	}
	public long getAssessmentid() {
		return assessmentid;
	}
	public void setAssessmentid(long assessmentid) {
		this.assessmentid = assessmentid;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getStartdatetime() {
		return startdatetime;
	}
	public void setStartdatetime(String startdatetime) {
		this.startdatetime = startdatetime;
	}
	public String getEnddatetime() {
		return enddatetime;
	}
	public void setEnddatetime(String enddatetime) {
		this.enddatetime = enddatetime;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public List<Long> getBatchids() {
		return batchids;
	}
	public void setBatchids(List<Long> batchids) {
		this.batchids = batchids;
	}
	@Override
	public String toString() {
		return "AssessmentSchedule [courseid=" + courseid + ", scheduledid=" + scheduledid + ", assessmentid="
				+ assessmentid + ", startdate=" + startdate + ", enddate=" + enddate + ", startdatetime="
				+ startdatetime + ", enddatetime=" + enddatetime + ", descr=" + descr + ", batchids=" + batchids
				+ ", createdby=" + createdby + "]";
	}    
}
