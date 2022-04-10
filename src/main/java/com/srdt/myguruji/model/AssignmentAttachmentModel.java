package com.srdt.myguruji.model;

public class AssignmentAttachmentModel
{
	private long assignmentAttachmentID;
	private long assignmentResponseID;
	private long assignmentid;
	private long questionid;
	private long studentid;
	private String originalFileName;
	private String generatedFileName;
	private String fileExtension;
	private String filePath;
	
	public AssignmentAttachmentModel() {
		super();
	}
	public AssignmentAttachmentModel(long assignmentAttachmentID, long assignmentResponseID, long assignmentid,
			long questionid, long studentid, String originalFileName, String generatedFileName, String fileExtension,
			String filePath) {
		super();
		assignmentAttachmentID = assignmentAttachmentID;
		this.assignmentResponseID = assignmentResponseID;
		this.assignmentid = assignmentid;
		this.questionid = questionid;
		this.studentid = studentid;
		this.originalFileName = originalFileName;
		this.generatedFileName = generatedFileName;
		this.fileExtension = fileExtension;
		this.filePath = filePath;
	}


	
	public long getAssignmentAttachmentID() {
		return assignmentAttachmentID;
	}
	public void setAssignmentAttachmentID(long assignmentAttachmentID) {
		this.assignmentAttachmentID = assignmentAttachmentID;
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
	public void setAssignmentid(long assessmentid) {
		this.assignmentid = assignmentid;
	}
	public long getQuestionid() {
		return questionid;
	}
	public void setQuestionid(long questionid) {
		this.questionid = questionid;
	}
	public long getStudentid() {
		return studentid;
	}
	public void setStudentid(long studentid) {
		this.studentid = studentid;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getGeneratedFileName() {
		return generatedFileName;
	}
	public void setGeneratedFileName(String generatedFileName) {
		this.generatedFileName = generatedFileName;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}