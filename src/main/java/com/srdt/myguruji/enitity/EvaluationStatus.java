package com.srdt.myguruji.enitity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "EvaluationStatus")
@NamedQueries({
	@NamedQuery(name = "EvaluationStatus.updateStatus", query = "update EvaluationStatus set status=:ST where statusid = :ID")
})
public class EvaluationStatus
{
	@Id
	@SequenceGenerator(name = "EvaluationStatus_Sqr",sequenceName = "EvaluationStatus_Sqr",initialValue = 1,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "EvaluationStatus_Sqr")
	private long statusid;
	
	@Column(length = 1, nullable = false)
	private String status; //T = saved, S = submitted
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "StudentId", referencedColumnName = "StudentId",insertable = true,updatable = true)
	@NotNull
	private StudentDetails studentDetails;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AssessmentId", referencedColumnName = "AssessmentId",insertable = true,updatable = true)
	@NotNull
	private AssessmentDetails assessmentDetails;
	
	public EvaluationStatus(long statusid, String status, @NotNull StudentDetails studentDetails,
			@NotNull AssessmentDetails assessmentDetails) {
		super();
		this.statusid = statusid;
		this.status = status;
		this.studentDetails = studentDetails;
		this.assessmentDetails = assessmentDetails;
	}
	public EvaluationStatus() {
		super();
	}
	public long getStatusid() {
		return statusid;
	}
	public void setStatusid(long statusid) {
		this.statusid = statusid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public StudentDetails getStudentDetails() {
		return studentDetails;
	}
	public void setStudentDetails(StudentDetails studentDetails) {
		this.studentDetails = studentDetails;
	}
	public AssessmentDetails getAssessmentDetails() {
		return assessmentDetails;
	}
	public void setAssessmentDetails(AssessmentDetails assessmentDetails) {
		this.assessmentDetails = assessmentDetails;
	}
}