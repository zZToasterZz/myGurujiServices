package com.srdt.myguruji.enitity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "AssignmentAttachments")
@NamedQueries({
	@NamedQuery(name = "AssignmentAttachments.deleteStudentAttachments", query = "delete from AssignmentAttachments a where a.studentDetails.StudentId =:stdntid and a.assignmentDetails.assignmentID=:asgnid and a.questionDetails.QuestionId=:quesid and a.AssignmentAttachmentID not in (:atchid)"),
	//@NamedQuery(name="AssignmentAttachments.findResponseAttachment",query="select a from AssignmentAttachments a where a.AssignmentAttachmentID=(select Max(ar.AssignmentAttachmentID) from AssignmentAttachments ar where ar.assignmentDetails.assignmentID=:assignid and ar.studentDetails.StudentId=:stdentid and ar.questionDetails.QuestionId=:quesid and ar.assignmentResponse.AssignmentResponseID=:responseid)")
	
})
public class AssignmentAttachments extends SharedField implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "AssignmentAttachments_sqr", sequenceName = "AssignmentAttachments_sqr", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "AssignmentAttachments_sqr",strategy = GenerationType.SEQUENCE)
	private long AssignmentAttachmentID;
	
	@Column(length = 150)
	private String OriginalFileName;
	
	@Column(length = 150)
	private String GeneratedFileName;
	
	@Column(length = 10)
	private String FileExtension;
	
	@Column(length = 500)
	private String FilePath;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AssignmentResponseID", referencedColumnName = "AssignmentResponseID", insertable = true, updatable = true)
	private AssignmentResponse assignmentResponse;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "StudentId", referencedColumnName = "StudentId", insertable = true, updatable = true) 
	private StudentDetails studentDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QuestionId", referencedColumnName = "QuestionId",insertable = true, updatable = true)
	@NotNull
	private QuestionDetails questionDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AssignmentID", referencedColumnName = "assignmentID", insertable = true, updatable = true)
	 
	private AssignmentDetails assignmentDetails;

	public AssignmentAttachments() {
		super();
	}

	public AssignmentAttachments(long assignmentAttachmentID, String originalFileName, String generatedFileName,
			String fileExtension, String filePath,   AssignmentResponse assignmentResponse,
			  StudentDetails studentDetails, @NotNull QuestionDetails questionDetails,
			  AssignmentDetails assignmentDetails) {
		super();
		AssignmentAttachmentID = assignmentAttachmentID;
		OriginalFileName = originalFileName;
		GeneratedFileName = generatedFileName;
		FileExtension = fileExtension;
		FilePath = filePath;
		this.assignmentResponse = assignmentResponse;
		this.studentDetails = studentDetails;
		this.questionDetails = questionDetails;
		this.assignmentDetails = assignmentDetails;
		System.out.println(studentDetails.getEmplid());
		setCreatedBy(studentDetails.getEmplid());
		setModifiedBy(studentDetails.getEmplid());
	}

	public long getAssignmentAttachmentID() {
		return AssignmentAttachmentID;
	}

	public void setAssignmentAttachmentID(long assignmentAttachmentID) {
		AssignmentAttachmentID = assignmentAttachmentID;
	}

	public String getOriginalFileName() {
		return OriginalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		OriginalFileName = originalFileName;
	}

	public String getGeneratedFileName() {
		return GeneratedFileName;
	}

	public void setGeneratedFileName(String generatedFileName) {
		GeneratedFileName = generatedFileName;
	}

	public String getFileExtension() {
		return FileExtension;
	}

	public void setFileExtension(String fileExtension) {
		FileExtension = fileExtension;
	}

	public String getFilePath() {
		return FilePath;
	}

	public void setFilePath(String filePath) {
		FilePath = filePath;
	}

	public AssignmentResponse getAssignmentResponse() {
		return assignmentResponse;
	}

	public void setAssignmentResponse(AssignmentResponse assignmentResponse) {
		this.assignmentResponse = assignmentResponse;
	}

	public StudentDetails getStudentDetails() {
		return studentDetails;
	}

	public void setStudentDetails(StudentDetails studentDetails) {
		this.studentDetails = studentDetails;
	}

	public QuestionDetails getQuestionDetails() {
		return questionDetails;
	}

	public void setQuestionDetails(QuestionDetails questionDetails) {
		this.questionDetails = questionDetails;
	}

	public AssignmentDetails getAssignmentDetails() {
		return assignmentDetails;
	}

	public void setAssignmentDetails(AssignmentDetails assignmentDetails) {
		this.assignmentDetails = assignmentDetails;
	}

}