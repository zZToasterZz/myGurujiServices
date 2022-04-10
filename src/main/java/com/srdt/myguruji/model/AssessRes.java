package com.srdt.myguruji.model;

public class AssessRes {
	
	private long courseid;
	private long questionid;
	private long assessmentid;
	private String qtype;
	private String quesres;
	private String subres;
	private String emplid;
	public AssessRes() {
		super();
	}
	public String getEmplid() {
		return emplid;
	}
	public void setEmplid(String emplid) {
		this.emplid = emplid;
	}
	public long getCourseid() {
		return courseid;
	}
	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}
	public long getQuestionid() {
		return questionid;
	}
	public void setQuestionid(long questionid) {
		this.questionid = questionid;
	}
	public long getAssessmentid() {
		return assessmentid;
	}
	public void setAssessmentid(long assessmentid) {
		this.assessmentid = assessmentid;
	}
	public String getQtype() {
		return qtype;
	}
	public void setQtype(String qtype) {
		this.qtype = qtype;
	}
	public String getQuesres() {
		return quesres;
	}
	public void setQuesres(String quesres) {
		this.quesres = quesres;
	}
	public String getSubres() {
		return subres;
	}
	public void setSubres(String subres) {
		this.subres = subres;
	}
}
