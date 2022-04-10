package com.srdt.myguruji.model;

public class AssessMarks
{
	private String emplid;
	private long assessmentid;
	private long questionid;
	private double marks = 0.0;
	
	public AssessMarks(String studentid, long assessmentid, long questionid, double marks) {
		super();
		this.emplid = emplid;
		this.assessmentid = assessmentid;
		this.questionid = questionid;
		this.marks = marks;
	}
	public String getEmplid() {
		return emplid;
	}
	public void setEmplid(String emplid) {
		this.emplid = emplid;
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
	public double getMarks() {
		return marks;
	}
	public void setMarks(double marks) {
		this.marks = marks;
	}
	@Override
	public String toString() {
		return "AssessMarks [emplid=" + emplid + ", assessmentid=" + assessmentid + ", questionid=" + questionid
				+ ", marks=" + marks + "]";
	}
}