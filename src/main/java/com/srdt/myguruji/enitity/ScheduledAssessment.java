package com.srdt.myguruji.enitity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ScheduledAssessment",uniqueConstraints = @UniqueConstraint(columnNames = {"AssessmentId"}))
@NamedQueries({
	@NamedQuery(name = "ScheduledAssessment.deleteScheduledAssessmentByAssessmentId", query = "delete from ScheduledAssessment where ScheduledId=:ScheduledId"),
	@NamedQuery(name = "ScheduledAssessment.findScheduledAssessmentByAssessmentId",query = "select a from ScheduledAssessment a join fetch a.assessmentDetails b "
																						 + "join fetch a.assessmentMappings where b.AssessmentId=:AssessmentId"),
	@NamedQuery(name = "ScheduledAssessment.findScheduledAssessmentByCourseId",query = "select a from ScheduledAssessment a join fetch a.assessmentDetails b "
																						+ "join fetch a.courseDetails c "
			 																			+ "join fetch a.assessmentMappings where c.CourseId=:CourseId"),
	@NamedQuery(name = "ScheduledAssessment.findScheduledAssessmentByBatchId",query =  "select a from ScheduledAssessment a join fetch a.assessmentDetails b "
																						+ "join fetch a.assessmentMappings c join fetch a.courseDetails d "
																						+ "join fetch c.batchDetails e "
																						+ "where e.BatchId=:BatchId"),
	@NamedQuery(name = "ScheduledAssessment.findScheduledAssessmentByStudentId",query =  "select a from ScheduledAssessment a join fetch a.assessmentDetails b "
																							+ "join fetch a.assessmentMappings c join fetch a.courseDetails d "
																							+ "join fetch c.batchDetails e join e.studentEnrollments f "
																							+ "join f.student g "
																							+ "where g.StudentId=:StudentId"), 
	@NamedQuery(name = "ScheduledAssessment.getExistingScheduleByBatchId", query = "select a from ScheduledAssessment a join fetch a.courseDetails join fetch a.assessmentDetails e join fetch e.template "
			                                                                       +"where a.ScheduledId in(select distinct b.scheduledAssessment.ScheduledId from ScheduledAssessmentMapping b " 
			                                                                       +"where b.batchDetails.BatchId in(select distinct c.batchDetails.BatchId from StudentEnrollment c where c.student.StudentId "
			                                                                       +"in(select min(d.student.StudentId) from StudentEnrollment d where d.batchDetails.BatchId in(:BatchId) "
			                                                                       +"group by d.batchDetails.BatchId)))"),
	/****** Sandhya*********/
	@NamedQuery(name="ScheduledAssessment.updateReSchedule", query="update ScheduledAssessment set IsActive='Y', StartDate=:startdate,EndDate=:enddate,StartDateTime=:starttime,EndDateTime=:endtime, Duration=:duration where assessmentDetails.AssessmentId=:assessId"),
	@NamedQuery(name="ScheduledAssessment.getScheduleStatus", query="select count(sa) from ScheduledAssessment sa where sa.assessmentDetails.AssessmentId=:assessid and sa.IsActive='Y' ")
})
public class ScheduledAssessment extends SharedField implements Serializable {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "ScheduledAssessment_Sqr",sequenceName = "ScheduledAssessment_Sqr",initialValue = 1,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ScheduledAssessment_Sqr")
	private long ScheduledId;
	@Column
	@Temporal(TemporalType.DATE)
	private Date StartDate;
	@Temporal(TemporalType.DATE)
	private Date EndDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date StartDateTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date EndDateTime;
	@Column(length = 250)
	private String Descr;	
	@Column(columnDefinition = "int default 0")
	@Min(1)
	private int Duration;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CourseId", referencedColumnName = "CourseId", insertable = true, updatable = true)
	private CourseDetails courseDetails;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AssessmentId",referencedColumnName = "AssessmentId", insertable = true, updatable = true)
	private AssessmentDetails assessmentDetails;
	
	@OneToMany(mappedBy = "scheduledAssessment",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@NotNull
	private List<ScheduledAssessmentMapping> assessmentMappings;

	public ScheduledAssessment() {
		super();
	}

	public ScheduledAssessment(Date startDate, Date enddate, Date startDateTime, Date endDateTime, String descr,
			CourseDetails courseDetails, AssessmentDetails assessmentDetails,String createdBy,int Duration) {
		super();
		StartDate = startDate;
		EndDate = enddate;
		StartDateTime = startDateTime;
		EndDateTime = endDateTime;
		Descr = descr;
		this.courseDetails = courseDetails;
		this.assessmentDetails = assessmentDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		this.Duration = Duration;
	}

	public int getDuration() {
		return Duration;
	}

	public void setDuration(int duration) {
		Duration = duration;
	}

	public long getScheduledId() {
		return ScheduledId;
	}

	public void setScheduledId(long scheduledId) {
		ScheduledId = scheduledId;
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

	public void setEndDate(Date enddate) {
		EndDate = enddate;
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

	public String getDescr() {
		return Descr;
	}

	public void setDescr(String descr) {
		Descr = descr;
	}

	public CourseDetails getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(CourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}

	public AssessmentDetails getAssessmentDetails() {
		return assessmentDetails;
	}

	public void setAssessmentDetails(AssessmentDetails assessmentDetails) {
		this.assessmentDetails = assessmentDetails;
	}

	public List<ScheduledAssessmentMapping> getAssessmentMappings() {
		return assessmentMappings;
	}

	public void setAssessmentMappings(List<ScheduledAssessmentMapping> assessmentMappings) {
		this.assessmentMappings = assessmentMappings;
	}
}
