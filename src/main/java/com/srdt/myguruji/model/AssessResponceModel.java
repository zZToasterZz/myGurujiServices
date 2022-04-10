package com.srdt.myguruji.model;

public class AssessResponceModel
{
	private long responseid;
	private long courseid;
	private long assessmentid;
	private long questionid;
	private String emplid;
	private String questiontype;
	private String quesResponse;
	private String subResponse;
	private double marksobtained;
	
	public AssessResponceModel(long responseid, long courseid, long assessmentid, long questionid, String emplid,
			String questiontype, String quesResponse, String subResponse, double marksobtained) {
		super();
		this.responseid = responseid;
		this.courseid = courseid;
		this.assessmentid = assessmentid;
		this.questionid = questionid;
		this.emplid = emplid;
		this.questiontype = questiontype;
		this.quesResponse = quesResponse;
		this.subResponse = subResponse;
		this.marksobtained = marksobtained;
	}
	public AssessResponceModel() {
		super();
	}
	public long getResponseid() {
		return responseid;
	}
	public void setResponseid(long responseid) {
		this.responseid = responseid;
	}
	public long getCourseid() {
		return courseid;
	}
	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}
	public long getAssessmentid() {
		return assessmentid;
	}
	public void setAssessmentid(long assessmentid) {
		this.assessmentid = assessmentid;
	}
	public long getQuestionid() {
		return questionid;
	}
	public void setQuestionid(long questionid) {
		this.questionid = questionid;
	}
	public String getEmplid() {
		return emplid;
	}
	public void setEmplid(String emplid) {
		this.emplid = emplid;
	}
	public String getQuestiontype() {
		return questiontype;
	}
	public void setQuestiontype(String questiontype) {
		this.questiontype = questiontype;
	}
	public String getQuesResponse() {
		return quesResponse;
	}
	public void setQuesResponse(String quesResponse) {
		this.quesResponse = quesResponse;
	}
	public String getSubResponse() {
		return subResponse;
	}
	public void setSubResponse(String subResponse) {
		this.subResponse = subResponse;
	}
	public double getMarksobtained() {
		return marksobtained;
	}
	public void setMarksobtained(double marksobtained) {
		this.marksobtained = marksobtained;
	}
	@Override
	public String toString() {
		return "AssessResponceModel [responseid=" + responseid + ", courseid=" + courseid + ", assessmentid="
				+ assessmentid + ", questionid=" + questionid + ", emplid=" + emplid + ", questiontype=" + questiontype
				+ ", quesResponse=" + quesResponse + ", subResponse=" + subResponse + ", marksobtained=" + marksobtained
				+ "]";
	}
}