package com.srdt.myguruji.model;

import java.io.Serializable;
import java.util.Date;

public class StudentAssessmentStatus implements Serializable
{	
	private long studentAssessmentStatusId;
	private long studentId;
	private long assessmentId;
	//private long batchId;
	private String assessmentAttendenceStatus;
	private String assessmentSubmissionStatus;
	private Date  assessmentAttendenceTimestamp;
	private Date  assessmentSubmissionTimestamp;
	private String createdby;
	
	public StudentAssessmentStatus() {
		super();
	}

	/**
	 * @param studentId
	 * @param assessmentId
	 * @param batchId
	 * @param assessmentAttendenceStatus
	 * @param assessmentSubmissionStatus
	 * @param assessmentAttendenceTimestamp
	 * @param assessmentSubmissionTimestamp
	 * @param createdby
	 */
	 
	/*public StudentAssessmentStatus(long studentAssessmentStatusId,long studentId, long assessmentId, long batchId, String assessmentAttendenceStatus,
			String assessmentSubmissionStatus, Date assessmentAttendenceTimestamp, Date assessmentSubmissionTimestamp,
			String createdby) {
		super();
		this.studentAssessmentStatusId=studentAssessmentStatusId;
		this.studentId = studentId;
		this.assessmentId = assessmentId;
		this.batchId = batchId;
		this.assessmentAttendenceStatus = assessmentAttendenceStatus;
		this.assessmentSubmissionStatus = assessmentSubmissionStatus;
		this.assessmentAttendenceTimestamp = assessmentAttendenceTimestamp;
		this.assessmentSubmissionTimestamp = assessmentSubmissionTimestamp;
		this.createdby = createdby;
	}*/
	
	public StudentAssessmentStatus(long studentAssessmentStatusId,long studentId, long assessmentId, String assessmentAttendenceStatus,
			String assessmentSubmissionStatus, Date assessmentAttendenceTimestamp, Date assessmentSubmissionTimestamp,
			String createdby) {
		super();
		this.studentAssessmentStatusId=studentAssessmentStatusId;
		this.studentId = studentId;
		this.assessmentId = assessmentId;
		this.assessmentAttendenceStatus = assessmentAttendenceStatus;
		this.assessmentSubmissionStatus = assessmentSubmissionStatus;
		this.assessmentAttendenceTimestamp = assessmentAttendenceTimestamp;
		this.assessmentSubmissionTimestamp = assessmentSubmissionTimestamp;
		this.createdby = createdby;
	}
	
	public long getStudentId() {
		return studentId;
	}

	public long getAssessmentId() {
		return assessmentId;
	}

	/*public long getBatchId() {
		return batchId;
	}*/

	public String getAssessmentAttendenceStatus() {
		return assessmentAttendenceStatus;
	}

	public String getAssessmentSubmissionStatus() {
		return assessmentSubmissionStatus;
	}

	public Date getAssessmentAttendenceTimestamp() {
		return assessmentAttendenceTimestamp;
	}

	public Date getAssessmentSubmissionTimestamp() {
		return assessmentSubmissionTimestamp;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public void setAssessmentId(long assessmentId) {
		this.assessmentId = assessmentId;
	}

	/*public void setBatchId(long batchId) {
		this.batchId = batchId;
	}*/

	public void setAssessmentAttendenceStatus(String assessmentAttendenceStatus) {
		this.assessmentAttendenceStatus = assessmentAttendenceStatus;
	}

	public void setAssessmentSubmissionStatus(String assessmentSubmissionStatus) {
		this.assessmentSubmissionStatus = assessmentSubmissionStatus;
	}

	public void setAssessmentAttendenceTimestamp(Date assessmentAttendenceTimestamp) {
		this.assessmentAttendenceTimestamp = assessmentAttendenceTimestamp;
	}

	public void setAssessmentSubmissionTimestamp(Date assessmentSubmissionTimestamp) {
		this.assessmentSubmissionTimestamp = assessmentSubmissionTimestamp;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public long getStudentAssessmentStatusId() {
		return studentAssessmentStatusId;
	}

	public void setStudentAssessmentStatusId(long studentAssessmentStatusId) {
		this.studentAssessmentStatusId = studentAssessmentStatusId;
	}

	

	}
