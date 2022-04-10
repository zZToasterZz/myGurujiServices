package com.srdt.myguruji.enitity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.NamedQueries;

@NamedQueries({
	@NamedQuery(name="ScheduledAssessmentMapping.deleteScheduleMapping", query="delete from ScheduledAssessmentMapping sam where sam.scheduledAssessment.ScheduledId=:scheduleId"),
	@NamedQuery(name="ScheduledAssessmentMapping.updateCancelScheduleStatus", query="update ScheduledAssessmentMapping sam set sam.IsActive='N' where sam.scheduledAssessment.ScheduledId=:scheduleId"),
	@NamedQuery(name = "ScheduledAssessmentMapping.getBatchByScheduleId",query = "select a from ScheduledAssessmentMapping a where a.scheduledAssessment.ScheduledId=:scheduleid"),
	@NamedQuery(name = "ScheduledAssessmentMapping.getBatchByAssessmentId",query = "select a from ScheduledAssessmentMapping a where a.scheduledAssessment.assessmentDetails.AssessmentId=:assessmentid")
})

@Entity
@Table(name = "ScheduledAssessmentMapping")
public class ScheduledAssessmentMapping extends SharedField implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ScheduledAssessmentMapping_Sqr",sequenceName = "ScheduledAssessmentMapping_Sqr",initialValue = 1,allocationSize = 1)
	@GeneratedValue(generator = "ScheduledAssessmentMapping_Sqr",strategy = GenerationType.SEQUENCE)
	private long AssessmentMapedId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ScheduledId",referencedColumnName = "ScheduledId",insertable = true,updatable = true)
	private ScheduledAssessment scheduledAssessment;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BatchId", referencedColumnName = "BatchId", insertable = true,updatable = true)
	private BatchDetails batchDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CourseId", referencedColumnName = "CourseId", insertable = true,updatable = true)
	private CourseDetails courseDetails;

	public ScheduledAssessmentMapping() {
		super();
	}

	public ScheduledAssessmentMapping(ScheduledAssessment scheduledAssessment, BatchDetails batchDetails,
			CourseDetails courseDetails,String createdBy) {
		super();
		this.scheduledAssessment = scheduledAssessment;
		this.batchDetails = batchDetails;
		this.courseDetails = courseDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}

	public long getAssessmentMapedId() {
		return AssessmentMapedId;
	}

	public void setAssessmentMapedId(long assessmentMapedId) {
		AssessmentMapedId = assessmentMapedId;
	}

	public ScheduledAssessment getScheduledAssessment() {
		return scheduledAssessment;
	}

	public void setScheduledAssessment(ScheduledAssessment scheduledAssessment) {
		this.scheduledAssessment = scheduledAssessment;
	}

	public BatchDetails getBatchDetails() {
		return batchDetails;
	}

	public void setBatchDetails(BatchDetails batchDetails) {
		this.batchDetails = batchDetails;
	}

	public CourseDetails getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(CourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}	
}
