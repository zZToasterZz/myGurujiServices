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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "ReScheduledAssessmentHistory")
public class RescheduleHistory  extends SharedField implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ReScheduledAssessment_Sqr",sequenceName = "ReScheduledAssessment_Sqr",initialValue = 1,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ReScheduledAssessment_Sqr")
	private long RescheduleHistoryId;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date StartDate;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date EndDate;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date StartDateTime;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date EndDateTime;
	
	@NotNull
	@Column(length=200)
	private String Comment;
	
	@Min(1)
	@Column(columnDefinition = "int default 0")
	private int Duration;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ScheduledId",referencedColumnName = "ScheduledId",insertable = true,nullable = false)
	private ScheduledAssessment rescheduledAssessment;
	
	public RescheduleHistory() {
		super();
	}


	public RescheduleHistory(long rescheduleHistoryId, Date startDate, Date endDate, Date startDateTime,
			Date endDateTime, @NotNull String comment, @Min(1) int duration,String createdBy, String modifiedBy) {
		super();
		RescheduleHistoryId = rescheduleHistoryId;
		StartDate = startDate;
		EndDate = endDate;
		StartDateTime = startDateTime;
		EndDateTime = endDateTime;
		Comment = comment;
		Duration = duration;
		setCreatedBy(createdBy);
		setModifiedBy(modifiedBy);
	}


	public long getRescheduleHistoryId() {
		return RescheduleHistoryId;
	}


	public void setRescheduleHistoryId(long rescheduleHistoryId) {
		RescheduleHistoryId = rescheduleHistoryId;
	}


	public Date getStartDate() {
		return StartDate;
	}


	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}


	public Date getEndDate() {
		return EndDate;
	}


	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}


	public Date getStartDateTime() {
		return StartDateTime;
	}


	public void setStartDateTime(Date startDateTime) {
		StartDateTime = startDateTime;
	}


	public Date getEndDateTime() {
		return EndDateTime;
	}


	public void setEndDateTime(Date endDateTime) {
		EndDateTime = endDateTime;
	}


	public String getComment() {
		return Comment;
	}


	public void setComment(String comment) {
		Comment = comment;
	}


	public int getDuration() {
		return Duration;
	}


	public void setDuration(int duration) {
		Duration = duration;
	}


	public ScheduledAssessment getRescheduledAssessment() {
		return rescheduledAssessment;
	}


	public void setRescheduledAssessment(ScheduledAssessment rescheduledAssessment) {
		this.rescheduledAssessment = rescheduledAssessment;
	}
	
	

}
