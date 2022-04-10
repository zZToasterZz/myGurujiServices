package com.srdt.myguruji.enitity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="AssessmentEvaluationStatus")
public class AssessmentEvaluationStatus
{
	@Id
	@SequenceGenerator(name = "AssessmentEvaluationStatus_sqr", sequenceName = "AssessmentEvaluationStatus_sqr", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "AssessmentEvaluationStatus_sqr", strategy = GenerationType.SEQUENCE)
	private long statusid;
	
	@Column(length = 4)
	private String status; //Y = done, N/null = not done
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AssessmentId", referencedColumnName = "AssessmentId", insertable = true, updatable = true)
	private AssessmentDetails assessmentDetails;

	public AssessmentEvaluationStatus(long statusid, String status, AssessmentDetails assessmentDetails) {
		super();
		this.statusid = statusid;
		this.status = status;
		this.assessmentDetails = assessmentDetails;
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

	public AssessmentDetails getAssessmentDetails() {
		return assessmentDetails;
	}

	public void setAssessmentDetails(AssessmentDetails assessmentDetails) {
		this.assessmentDetails = assessmentDetails;
	}
}