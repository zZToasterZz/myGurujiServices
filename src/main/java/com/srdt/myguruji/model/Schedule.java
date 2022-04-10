package com.srdt.myguruji.model;

import java.util.List;

public class Schedule {
	
	private long assessmentid;
	private long courseid;
	private String assessmentitle;
	private String assessmencode;
	private String assessmendescr;
	private String coursecode;
	private String coursedescr;
	private String startdate;
	private String enddate;
	private String startdatetime;
	private String enddatetime;
	private String IsActive;
	private int duration;
	private int maxmarks;
	private String batchcodes;
	private String batchid;
	private String createdby;
	private List<SectionDetails> sectionDetails;
	private long studentcount;
	
	public Schedule() {
		super();
	}
	public long getStudentcount() {
		return studentcount;
	}
	public void setStudentcount(long studentcount) {
		this.studentcount = studentcount;
	}
	public Schedule(long assessmentid, long courseid, String assessmentitle, String assessmencode,
			String assessmendescr, String coursecode, String coursedescr, String startdate, String enddate,
			String startdatetime, String enddatetime, String isActive, int duration, int maxmarks, String batchcodes,
			String createdby, List<SectionDetails> sectionDetails) {
		super();
		this.assessmentid = assessmentid;
		this.courseid = courseid;
		this.assessmentitle = assessmentitle;
		this.assessmencode = assessmencode;
		this.assessmendescr = assessmendescr;
		this.coursecode = coursecode;
		this.coursedescr = coursedescr;
		this.startdate = startdate;
		this.enddate = enddate;
		this.startdatetime = startdatetime;
		this.enddatetime = enddatetime;
		IsActive = isActive;
		this.duration = duration;
		this.maxmarks = maxmarks;
		this.batchcodes = batchcodes;
		this.createdby = createdby;
		this.sectionDetails = sectionDetails;
	}
	public String getIsActive() {
		return IsActive;
	}
	public void setIsActive(String isActive) {
		IsActive = isActive;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getBatchcodes() {
		return batchcodes;
	}
	public void setBatchcodes(String batchcodes) {
		this.batchcodes = batchcodes;
	}
	public Schedule(long assessmentid, long courseid, String assessmentitle, String assessmencode,
			String assessmendescr, String coursecode, String coursedescr, String startdate, String enddate,
			String startdatetime, String enddatetime) {
		super();
		this.assessmentid = assessmentid;
		this.courseid = courseid;
		this.assessmentitle = assessmentitle;
		this.assessmencode = assessmencode;
		this.assessmendescr = assessmendescr;
		this.coursecode = coursecode;
		this.coursedescr = coursedescr;
		this.startdate = startdate;
		this.enddate = enddate;
		this.startdatetime = startdatetime;
		this.enddatetime = enddatetime;
	}
	public Schedule(long assessmentid, long courseid, String assessmentitle, String assessmencode,
			String assessmendescr, String coursecode, String coursedescr, String startdate, String enddate,
			String startdatetime, String enddatetime, int duration, int maxmarks) {
		super();
		this.assessmentid = assessmentid;
		this.courseid = courseid;
		this.assessmentitle = assessmentitle;
		this.assessmencode = assessmencode;
		this.assessmendescr = assessmendescr;
		this.coursecode = coursecode;
		this.coursedescr = coursedescr;
		this.startdate = startdate;
		this.enddate = enddate;
		this.startdatetime = startdatetime;
		this.enddatetime = enddatetime;
		this.duration = duration;
		this.maxmarks = maxmarks;
	}
	public Schedule(long assessmentid, long courseid, String assessmentitle, String assessmencode,
			String assessmendescr, String coursecode, String coursedescr, String startdate, String enddate,
			String startdatetime, String enddatetime, int duration, int maxmarks, String batchcodes, String createdby, String IsActive,
			long studentcount) {
		super();
		this.assessmentid = assessmentid;
		this.courseid = courseid;
		this.assessmentitle = assessmentitle;
		this.assessmencode = assessmencode;
		this.assessmendescr = assessmendescr;
		this.coursecode = coursecode;
		this.coursedescr = coursedescr;
		this.startdate = startdate;
		this.enddate = enddate;
		this.startdatetime = startdatetime;
		this.enddatetime = enddatetime;
		this.duration = duration;
		this.maxmarks = maxmarks;
		this.batchcodes = batchcodes;
		this.createdby = createdby;
		this.IsActive = IsActive;
		this.studentcount = studentcount;
	}
	public Schedule(long assessmentid, long courseid, String assessmentitle, String assessmencode,
			String assessmendescr, String coursecode, String coursedescr, String startdate, String enddate,
			String startdatetime, String enddatetime, int duration, int maxmarks, String batchcodes, String createdby,
			List<SectionDetails> sectionDetails) {
		super();
		this.assessmentid = assessmentid;
		this.courseid = courseid;
		this.assessmentitle = assessmentitle;
		this.assessmencode = assessmencode;
		this.assessmendescr = assessmendescr;
		this.coursecode = coursecode;
		this.coursedescr = coursedescr;
		this.startdate = startdate;
		this.enddate = enddate;
		this.startdatetime = startdatetime;
		this.enddatetime = enddatetime;
		this.duration = duration;
		this.maxmarks = maxmarks;
		this.batchcodes = batchcodes;
		this.createdby = createdby;
		this.sectionDetails = sectionDetails;
	}
	public List<SectionDetails> getSectionDetails() {
		return sectionDetails;
	}
	public void setSectionDetails(List<SectionDetails> sectionDetails) {
		this.sectionDetails = sectionDetails;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getMaxmarks() {
		return maxmarks;
	}
	public void setMaxmarks(int maxmarks) {
		this.maxmarks = maxmarks;
	}
	public long getAssessmentid() {
		return assessmentid;
	}
	public void setAssessmentid(long assessmentid) {
		this.assessmentid = assessmentid;
	}
	public long getCourseid() {
		return courseid;
	}
	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}
	public String getAssessmentitle() {
		return assessmentitle;
	}
	public void setAssessmentitle(String assessmentitle) {
		this.assessmentitle = assessmentitle;
	}
	public String getAssessmencode() {
		return assessmencode;
	}
	public void setAssessmencode(String assessmencode) {
		this.assessmencode = assessmencode;
	}
	public String getAssessmendescr() {
		return assessmendescr;
	}
	public void setAssessmendescr(String assessmendescr) {
		this.assessmendescr = assessmendescr;
	}
	public String getCoursecode() {
		return coursecode;
	}
	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}
	public String getCoursedescr() {
		return coursedescr;
	}
	public void setCoursedescr(String coursedescr) {
		this.coursedescr = coursedescr;
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

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}
}
