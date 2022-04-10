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
@Table(name = "AssignmentStatusDetails")
@NamedQueries({
	@NamedQuery(name="AssignmentStatusDetails.getStudentStatus", query="select asd.SubmitStatus from AssignmentStatusDetails asd where asd.assignmentDetails.assignmentID=: assignmentId and asd.studentDetails.StudentId=:studentid"),
	@NamedQuery(name="AssignmentStatusDetails.getExistingStudentStatus", query="select asd from AssignmentStatusDetails asd where asd.assignmentDetails.assignmentID=: assignmentId and asd.studentDetails.StudentId=:studentid"),
	@NamedQuery(name="AssignmentStatusDetails.setEvaluationStatus", query="update AssignmentStatusDetails asd set asd.EvaluationStatus=:status where asd.assignmentDetails.assignmentID=: assignmentId and asd.studentDetails.StudentId=:studentid"),
	//@NamedQuery(name="AssignmentStatusDetails.getAssignmentCountforStudentId",query="select count(asd) from AssignmentStatusDetails asd where asd.studentDetails.StudentId=:studentid")
})

public class AssignmentStatusDetails extends SharedField implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "AssignmentSubmissionStatus_sqr", sequenceName = "AssignmentSubmissionStatus_sqr", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "AssignmentSubmissionStatus_sqr",strategy = GenerationType.SEQUENCE)
	private long AssignmentStatusID;
	
	@Column(length = 1)
	private String TimingStatus;
	
	@Column(length = 1)
	private String SubmitStatus;
	
	@Column(length = 1)
	private String EvaluationStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AssignmentID", referencedColumnName = "AssignmentID", insertable = true, updatable = true)
	private AssignmentDetails assignmentDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "StudentId", referencedColumnName = "StudentId", insertable = true, updatable = true)
	private StudentDetails studentDetails;

	public AssignmentStatusDetails() {
		super();
	}

	public AssignmentStatusDetails(long assignmentStatusID, String timingStatus, String submitStatus,
			String evaluationStatus, @NotBlank AssignmentDetails assignmentDetails,
			@NotBlank StudentDetails studentDetails) {
		super();
		AssignmentStatusID = assignmentStatusID;
		TimingStatus = timingStatus;
		SubmitStatus = submitStatus;
		EvaluationStatus = evaluationStatus;
		this.assignmentDetails = assignmentDetails;
		this.studentDetails = studentDetails;
	}

	public long getAssignmentStatusID() {
		return AssignmentStatusID;
	}

	public void setAssignmentStatusID(long assignmentStatusID) {
		AssignmentStatusID = assignmentStatusID;
	}

	public String getTimingStatus() {
		return TimingStatus;
	}

	public void setTimingStatus(String timingStatus) {
		TimingStatus = timingStatus;
	}

	public String getSubmitStatus() {
		return SubmitStatus;
	}

	public void setSubmitStatus(String submitStatus) {
		SubmitStatus = submitStatus;
	}

	public String getEvaluationStatus() {
		return EvaluationStatus;
	}

	public void setEvaluationStatus(String evaluationStatus) {
		EvaluationStatus = evaluationStatus;
	}

	public AssignmentDetails getAssignmentDetails() {
		return assignmentDetails;
	}

	public void setAssignmentDetails(AssignmentDetails assignmentDetails) {
		this.assignmentDetails = assignmentDetails;
	}

	public StudentDetails getStudentDetails() {
		return studentDetails;
	}

	public void setStudentDetails(StudentDetails studentDetails) {
		this.studentDetails = studentDetails;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}