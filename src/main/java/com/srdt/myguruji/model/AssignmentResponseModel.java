package com.srdt.myguruji.model;

import java.util.List;

public class AssignmentResponseModel
{
	private long assignmentResponseID;
	private long assignmentid;
	private long studentid;
	private long questionid;
	private String objectiveResponse;
	private String subjectiveResponse;
	private String hasAttachment;
	private String marksObtained;
	private String emplid;
	private String responsestatus;
	List<AssignmentAttachmentModel> attachments;
	
	@Override
	public String toString() {
		return "AssignmentResponseModel [assignmentResponseID=" + assignmentResponseID + ", assignmentid="
				+ assignmentid + ", studentid=" + studentid + ", questionid=" + questionid + ", objectiveResponse="
				+ objectiveResponse + ", subjectiveResponse=" + subjectiveResponse + ", hasAttachment=" + hasAttachment
				+ ", marksObtained=" + marksObtained + ", attachments=" + attachments + "]";
	}
	
	
	public AssignmentResponseModel(long assignmentResponseID, long assignmentid, long studentid, long questionid,
			String objectiveResponse, String subjectiveResponse, String hasAttachment, String marksObtained,
			String emplid, List<AssignmentAttachmentModel> attachments) {
		super();
		this.assignmentResponseID = assignmentResponseID;
		this.assignmentid = assignmentid;
		this.studentid = studentid;
		this.questionid = questionid;
		this.objectiveResponse = objectiveResponse;
		this.subjectiveResponse = subjectiveResponse;
		this.hasAttachment = hasAttachment;
		this.marksObtained = marksObtained;
		this.emplid = emplid;
		this.attachments = attachments;
	}


	public AssignmentResponseModel(long assignmentResponseID, long assignmentid, long studentid, long questionid,
			String objectiveResponse, String subjectiveResponse, String hasAttachment, String marksObtained,
			List<AssignmentAttachmentModel> attachments) {
		super();
		this.assignmentResponseID = assignmentResponseID;
		this.assignmentid = assignmentid;
		this.studentid = studentid;
		this.questionid = questionid;
		this.objectiveResponse = objectiveResponse;
		this.subjectiveResponse = subjectiveResponse;
		this.hasAttachment = hasAttachment;
		this.marksObtained = marksObtained;
		this.attachments = attachments;
	}
	public AssignmentResponseModel() {
		super();
	}
	public long getAssignmentResponseID() {
		return assignmentResponseID;
	}
	public void setAssignmentResponseID(long assignmentResponseID) {
		this.assignmentResponseID = assignmentResponseID;
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
	public long getQuestionid() {
		return questionid;
	}
	public void setQuestionid(long questionid) {
		this.questionid = questionid;
	}
	public String getObjectiveResponse() {
		return objectiveResponse;
	}
	public void setObjectiveResponse(String objectiveResponse) {
		this.objectiveResponse = objectiveResponse;
	}
	public String getSubjectiveResponse() {
		return subjectiveResponse;
	}
	public void setSubjectiveResponse(String subjectiveResponse) {
		this.subjectiveResponse = subjectiveResponse;
	}
	public String getHasAttachment() {
		return hasAttachment;
	}
	public void setHasAttachment(String hasAttachment) {
		this.hasAttachment = hasAttachment;
	}
	
	public String getMarksObtained() {
		return marksObtained;
	}
	public void setMarksObtained(String marksObtained) {
		this.marksObtained = marksObtained;
	}
	public List<AssignmentAttachmentModel> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<AssignmentAttachmentModel> attachments) {
		this.attachments = attachments;
	}
	public String getEmplid() {
		return emplid;
	}
	public void setEmplid(String emplid) {
		this.emplid = emplid;
	}


	public String getResponsestatus() {
		return responsestatus;
	}


	public void setResponsestatus(String responsestatus) {
		this.responsestatus = responsestatus;
	}
	
	
}