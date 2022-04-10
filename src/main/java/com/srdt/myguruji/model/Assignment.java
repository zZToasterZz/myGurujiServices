package com.srdt.myguruji.model;

import java.util.List;

public class Assignment
{
	private long courseID;
	private long assignmentID;
	private String assignmentTitle;
	private String assignmentDescr;
	private String assignmentType;
	private String dueDate;
	private String hasPlan;
	private String hasUnit;
	private String hasQuestion;
	private String hasStudent;
	private String isPublished;
	private double maxMarks;
	private String createdby;
	private String level;
	private List<AssignmentPlan> assignplan;
	private List<AddedQuestion> questionlist;
	private List<AssignmentResponseModel> assignresponse;
	private String responseStatus;
	private List<Student> studentinfo;
	
	public Assignment() {
		super();
	}

	public Assignment(long courseID, long assignmentID, String assignmentTitle, String assignmentDescr,
			String assignmentType, String dueDate, String hasPlan, String hasUnit, String hasQuestion,
			String isPublished, double maxMarks, String createdby, String level) {
		super();
		this.courseID = courseID;
		this.assignmentID = assignmentID;
		this.assignmentTitle = assignmentTitle;
		this.assignmentDescr = assignmentDescr;
		this.assignmentType = assignmentType;
		this.dueDate = dueDate;
		this.hasPlan = hasPlan;
		this.hasUnit = hasUnit;
		this.hasQuestion = hasQuestion;
		this.isPublished = isPublished;
		this.maxMarks = maxMarks;
		this.createdby = createdby;
		this.level = level;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Assignment(long courseID, long assignmentID, String assignmentTitle, String assignmentDescr,
			String assignmentType, String dueDate, String hasPlan, String hasUnit, String hasQuestion,
			String isPublished, double maxMarks, String createdby) {
		super();
		this.courseID = courseID;
		this.assignmentID = assignmentID;
		this.assignmentTitle = assignmentTitle;
		this.assignmentDescr = assignmentDescr;
		this.assignmentType = assignmentType;
		this.dueDate = dueDate;
		this.hasPlan = hasPlan;
		this.hasUnit = hasUnit;
		this.hasQuestion = hasQuestion;
		this.isPublished = isPublished;
		this.maxMarks = maxMarks;
		this.createdby = createdby;
	}

	public Assignment(long courseID, long assignmentID, String assignmentTitle, String assignmentDescr,
			String assignmentType, String dueDate, String hasPlan, String hasUnit, String hasQuestion,
			String isPublished, double maxMarks) {
		super();
		this.courseID = courseID;
		this.assignmentID = assignmentID;
		this.assignmentTitle = assignmentTitle;
		this.assignmentDescr = assignmentDescr;
		this.assignmentType = assignmentType;
		this.dueDate = dueDate;
		this.hasPlan = hasPlan;
		this.hasUnit = hasUnit;
		this.hasQuestion = hasQuestion;
		this.isPublished = isPublished;
		this.maxMarks = maxMarks;
	}

	public long getCourseID() {
		return courseID;
	}

	public void setCourseID(long courseID) {
		this.courseID = courseID;
	}

	public long getAssignmentID() {
		return assignmentID;
	}

	public void setAssignmentID(long assignmentID) {
		this.assignmentID = assignmentID;
	}

	public String getAssignmentTitle() {
		return assignmentTitle;
	}

	public void setAssignmentTitle(String assignmentTitle) {
		this.assignmentTitle = assignmentTitle;
	}

	public String getAssignmentDescr() {
		return assignmentDescr;
	}

	public void setAssignmentDescr(String assignmentDescr) {
		this.assignmentDescr = assignmentDescr;
	}

	public String getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getHasPlan() {
		return hasPlan;
	}

	public void setHasPlan(String hasPlan) {
		this.hasPlan = hasPlan;
	}

	public String getHasUnit() {
		return hasUnit;
	}

	public void setHasUnit(String hasUnit) {
		this.hasUnit = hasUnit;
	}

	public String getHasQuestion() {
		return hasQuestion;
	}

	public void setHasQuestion(String hasQuestion) {
		this.hasQuestion = hasQuestion;
	}

	public String getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(String isPublished) {
		this.isPublished = isPublished;
	}

	public double getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(double maxMarks) {
		this.maxMarks = maxMarks;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	
	public List<AssignmentPlan> getAssignplan() {
		return assignplan;
	}

	public void setAssignplan(List<AssignmentPlan> assignplan) {
		this.assignplan = assignplan;
	}
	
	

	public List<AddedQuestion> getQuestionlist() {
		return questionlist;
	}

	public void setQuestionlist(List<AddedQuestion> questionlist) {
		this.questionlist = questionlist;
	}

	
	
	public String getHasStudent() {
		return hasStudent;
	}

	public void setHasStudent(String hasStudent) {
		this.hasStudent = hasStudent;
	}

	
	public List<AssignmentResponseModel> getAssignresponse() {
		return assignresponse;
	}

	public void setAssignresponse(List<AssignmentResponseModel> assignresponse) {
		this.assignresponse = assignresponse;
	}

	
	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public List<Student> getStudentinfo() {
		return studentinfo;
	}

	public void setStudentinfo(List<Student> studentinfo) {
		this.studentinfo = studentinfo;
	}

	@Override
	public String toString() {
		return "Assignment [courseID=" + courseID + ", assignmentID=" + assignmentID + ", assignmentTitle="
				+ assignmentTitle + ", assignmentDescr=" + assignmentDescr + ", assignmentType=" + assignmentType
				+ ", dueDate=" + dueDate + ", hasPlan=" + hasPlan + ", hasUnit=" + hasUnit + ", hasQuestion="
				+ hasQuestion + ", isPublished=" + isPublished + ", maxMarks=" + maxMarks + "]";
	}
}