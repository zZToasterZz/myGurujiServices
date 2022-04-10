package com.srdt.myguruji.enitity;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.srdt.myguruji.utility.Generation;

@Entity
@Table(name="StudentAssessmentStatusDetails",uniqueConstraints={@UniqueConstraint(columnNames={"StudentId","AssessmentId"})})
@NamedQueries({
	//@NamedQuery(name = "StudentAssessmentStatusDetails.findStudentAssesmentStatus", query = "select new com.srdt.myguruji.model.StudentAssessmentStatus(s.studentAssessmentStatusId,s.student.StudentId,s.assessment.AssessmentId,s.batch.BatchId,s.assesmentAttendenceStatus,s.assessmentSubmissionStatus,s.assessmentAttendenceTimestamp,s.assesstmentSubmissionTimestamp,s.createdby) from StudentAssessmentStatusDetails s where s.student.StudentId=:StudentId and s.batch.BatchId=:BatchId and s.assessment.AssessmentId=:AssessmentId"),
	@NamedQuery(name = "StudentAssessmentStatusDetails.findStudentAssesmentStatus", query = "select new com.srdt.myguruji.model.StudentAssessmentStatus(s.studentAssessmentStatusId,s.student.StudentId,s.assessment.AssessmentId,s.assesmentAttendenceStatus,s.assessmentSubmissionStatus,s.assessmentAttendenceTimestamp,s.assesstmentSubmissionTimestamp,s.createdby) from StudentAssessmentStatusDetails s where s.student.StudentId=:StudentId and s.assessment.AssessmentId=:AssessmentId"),
	//@NamedQuery(name = "StudentAssessmentStatusDetails.updateStudentAssessmentStatusDetail", query = "update  StudentAssessmentStatusDetails set assessmentSubmissionStatus=:assessmentSubmissionStatus,assesstmentSubmissionTimestamp=:assesstmentSubmissionTimestamp where student.StudentId=:StudentId and batch.BatchId=:BatchId and assessment.AssessmentId=:AssessmentId")
	@NamedQuery(name = "StudentAssessmentStatusDetails.updateStudentAssessmentStatusDetail", query = "update  StudentAssessmentStatusDetails set assessmentSubmissionStatus=:assessmentSubmissionStatus,assesstmentSubmissionTimestamp=:assesstmentSubmissionTimestamp where student.StudentId=:StudentId and assessment.AssessmentId=:AssessmentId")
})
public class StudentAssessmentStatusDetails implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "StudentAssessmentStatus_Sqr",sequenceName = "StudentAssessmentStatus_Sqr",initialValue = 1,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "StudentAssessmentStatus_Sqr")
	private long studentAssessmentStatusId;
	@Column(length=1,nullable=false)
	private String assesmentAttendenceStatus;
	@Column(length=1,nullable=true)
	private String assessmentSubmissionStatus="N";
	private Date  assessmentAttendenceTimestamp=Generation.getCurrentDate();
	private Date  assesstmentSubmissionTimestamp=Generation.getCurrentDate();
	
	private String createdby;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="StudentId",referencedColumnName="StudentId",insertable=true,updatable=true,nullable=false)
	@NotNull
	private StudentDetails student;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BatchId",referencedColumnName="BatchId",insertable=true,updatable=true,nullable=true)	
	private BatchDetails batch;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AssessmentId",referencedColumnName = "AssessmentId", insertable = true, updatable = true, nullable=false)
	private AssessmentDetails assessment;
	
	public StudentAssessmentStatusDetails() {
		super();
	}

	/**
	 * @param studentAssessmentStatusId
	 * @param assesmentAttendenceStatus
	 * @param assesstmentSubmissionStatus
	 * @param assessmentAttendenceTimestamp
	 * @param assesstmentSubmissionTimestamp
	 * @param createdby
	 * @param student
	 * @param batch
	 * @param assesment
	 */
	public StudentAssessmentStatusDetails(String assesmentAttendenceStatus,
			String assessmentSubmissionStatus, Date assessmentAttendenceTimestamp, Date assesstmentSubmissionTimestamp,
			String createdby, @NotNull StudentDetails student, BatchDetails batch,
			AssessmentDetails assessment) {
		super();
		
		this.assesmentAttendenceStatus = assesmentAttendenceStatus;
		this.assessmentSubmissionStatus = assessmentSubmissionStatus;
		this.assessmentAttendenceTimestamp = assessmentAttendenceTimestamp;
		this.assesstmentSubmissionTimestamp = assesstmentSubmissionTimestamp;
		this.createdby = createdby;
		this.student = student;
		this.batch = batch;
		this.assessment = assessment;
	}

	public long getStudentAssessmentStatusId() {
		return studentAssessmentStatusId;
	}

	public String getAssesmentAttendenceStatus() {
		return assesmentAttendenceStatus;
	}

	

	public Date getAssessmentAttendenceTimestamp() {
		return assessmentAttendenceTimestamp;
	}

	public Date getAssesstmentSubmissionTimestamp() {
		return assesstmentSubmissionTimestamp;
	}

	public String getCreatedby() {
		return createdby;
	}

	public StudentDetails getStudent() {
		return student;
	}

	public BatchDetails getBatch() {
		return batch;
	}

	public AssessmentDetails getAssessment() {
		return assessment;
	}

	public void setStudentAssessmentStatusId(long studentAssessmentStatusId) {
		this.studentAssessmentStatusId = studentAssessmentStatusId;
	}

	public void setAssesmentAttendenceStatus(String assesmentAttendenceStatus) {
		this.assesmentAttendenceStatus = assesmentAttendenceStatus;
	}

	

	public void setAssessmentAttendenceTimestamp(Date assessmentAttendenceTimestamp) {
		this.assessmentAttendenceTimestamp = assessmentAttendenceTimestamp;
	}

	public void setAssesstmentSubmissionTimestamp(Date assesstmentSubmissionTimestamp) {
		this.assesstmentSubmissionTimestamp = assesstmentSubmissionTimestamp;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public void setStudent(StudentDetails student) {
		this.student = student;
	}

	public void setBatch(BatchDetails batch) {
		this.batch = batch;
	}

	public void setAssessment(AssessmentDetails assessment) {
		this.assessment = assessment;
	}

	public String getAssessmentSubmissionStatus() {
		return assessmentSubmissionStatus;
	}

	public void setAssessmentSubmissionStatus(String assessmentSubmissionStatus) {
		this.assessmentSubmissionStatus = assessmentSubmissionStatus;
	}

	
}
