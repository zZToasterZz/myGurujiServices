package com.srdt.myguruji.enitity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "AssignmentResponse",uniqueConstraints = @UniqueConstraint(columnNames = {"AssignmentID","QuestionId","StudentId"}))
@NamedQueries({
	@NamedQuery(name = "AssignmentResponse.findByResponseId",query = "select a from AssignmentResponse a where a.AssignmentResponseID = :id"),
	@NamedQuery(name="AssignmentResponse.findResponseByStudentId",query="select ar from AssignmentResponse ar where ar.assignmentDetails.assignmentID=: assignId and ar.studentDetails.StudentId=: studentId and ar.questionDetails.QuestionId=: quesId"),
	@NamedQuery(name="AssignmentResponse.saveMarks",query="update AssignmentResponse ar set ar.MarksObtained=:marks where ar.assignmentDetails.assignmentID=: assignId and ar.studentDetails.StudentId=: studentId and ar.questionDetails.QuestionId=: quesId and ar.AssignmentResponseID=: responseId"),
		//@NamedQuery(name="AssignmentResponse.getObtainedMarks", query="select sum(ar.MarksObtained) from AssignmentResponse ar where ar.assignmentDetails.assignmentID=: assignId and ar.studentDetails.StudentId=: studentId")
})
public class AssignmentResponse extends SharedField implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "AssignmentResponse_sqr", sequenceName = "AssignmentResponse_sqr", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "AssignmentResponse_sqr",strategy = GenerationType.SEQUENCE)
	private long AssignmentResponseID;
	
	@Column(length = 50)
	private String ObjectiveResponse;
	
	@Lob
	@Column(length=16777000)
	private String SubjectiveResponse;
	
	@Column(length = 1)
	private String HasAttachment = "N";
	
	@Column
	private double MarksObtained;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "StudentId", referencedColumnName = "StudentId", insertable = true, updatable = true)
	private StudentDetails studentDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QuestionId", referencedColumnName = "QuestionId",insertable = true, updatable = true)
	@NotNull
	private QuestionDetails questionDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AssignmentID", referencedColumnName = "AssignmentID", insertable = true, updatable = true)
	private AssignmentDetails assignmentDetails;
	
	@OneToMany(mappedBy="assignmentResponse",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<AssignmentAttachments> attachments;
	
	
	public AssignmentResponse() {
		super();
	}

	public AssignmentResponse(long assignmentResponseID, String objectiveResponse, String subjectiveResponse,
			String hasAttachment, double marksObtained, StudentDetails studentDetails,
			@NotNull QuestionDetails questionDetails) {
		super();
		AssignmentResponseID = assignmentResponseID;
		ObjectiveResponse = objectiveResponse;
		SubjectiveResponse = subjectiveResponse;
		HasAttachment = hasAttachment;
		MarksObtained = marksObtained;
		this.studentDetails = studentDetails;
		this.questionDetails = questionDetails;
		setCreatedBy(studentDetails.getEmplid());
		setModifiedBy(studentDetails.getEmplid());
	}

	public List<AssignmentAttachments> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<AssignmentAttachments> attachments) {
		this.attachments = attachments;
	}

	public AssignmentResponse(long assignmentResponseID, String objectiveResponse, String subjectiveResponse,
			String hasAttachment, double marksObtained,  StudentDetails studentDetails,
			@NotNull QuestionDetails questionDetails,  AssignmentDetails assignmentDetails,
			List<AssignmentAttachments> attachments) {
		super();
		AssignmentResponseID = assignmentResponseID;
		ObjectiveResponse = objectiveResponse;
		SubjectiveResponse = subjectiveResponse;
		HasAttachment = hasAttachment;
		MarksObtained = marksObtained;
		this.studentDetails = studentDetails;
		this.questionDetails = questionDetails;
		this.assignmentDetails = assignmentDetails;
		this.attachments = attachments;
		setCreatedBy(studentDetails.getEmplid());
		setModifiedBy(studentDetails.getEmplid());
	}

	public AssignmentResponse(long assignmentResponseID, String objectiveResponse, String subjectiveResponse,
			String hasAttachment, double marksObtained,  StudentDetails studentDetails,
			@NotNull QuestionDetails questionDetails,  AssignmentDetails assignmentDetails) {
		super();
		AssignmentResponseID = assignmentResponseID;
		ObjectiveResponse = objectiveResponse;
		SubjectiveResponse = subjectiveResponse;
		HasAttachment = hasAttachment;
		MarksObtained = marksObtained;
		this.studentDetails = studentDetails;
		this.questionDetails = questionDetails;
		this.assignmentDetails = assignmentDetails;
		setCreatedBy(studentDetails.getEmplid());
		setModifiedBy(studentDetails.getEmplid());
	}

	public long getAssignmentResponseID() {
		return AssignmentResponseID;
	}

	public void setAssignmentResponseID(long assignmentResponseID) {
		AssignmentResponseID = assignmentResponseID;
	}

	public String getObjectiveResponse() {
		return ObjectiveResponse;
	}

	public void setObjectiveResponse(String objectiveResponse) {
		ObjectiveResponse = objectiveResponse;
	}

	public String getSubjectiveResponse() {
		return SubjectiveResponse;
	}

	public void setSubjectiveResponse(String subjectiveResponse) {
		SubjectiveResponse = subjectiveResponse;
	}

	public String getHasAttachment() {
		return HasAttachment;
	}

	public void setHasAttachment(String hasAttachment) {
		HasAttachment = hasAttachment;
	}

	public double getMarksObtained() {
		return MarksObtained;
	}

	public void setMarksObtained(double marksObtained) {
		MarksObtained = marksObtained;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}