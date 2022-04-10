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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "LectureSchedule") 
@NamedQueries({
	@NamedQuery(name = "LectureSchedule.deleteLectureSchedule", query = "delete from LectureSchedule where LecSchId=:id"),
	@NamedQuery(name = "LectureSchedule.getLectureScheduleByCourseId", query = "select a from LectureSchedule a join fetch a.courseDetails b join fetch a.unitDetails c where b.CourseId=:id"),
	@NamedQuery(name = "LectureSchedule.getLectureScheduleByUnitId", query = "select a from LectureSchedule a join fetch a.unitDetails b join fetch a.courseDetails c where b.UnitId=:id"),
	@NamedQuery(name = "LectureSchedule.getLectureScheduleByBatchId", query = "select a from LectureSchedule a join fetch a.courseDetails b join fetch a.unitDetails c where c.UnitId in(select a.UnitId from UnitDetails a join a.coursePlan b join b.batchDetails c where c.BatchId=:id)"),
	//Sandhya
	@NamedQuery(name="LectureSchedule.updateLectureSchedule" , query="update LectureSchedule set Descr=:dscr,Title=:title where LecSchId=:lecId  ")
})
public class LectureSchedule extends SharedField implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "LectureSchedule_Sqr", sequenceName = "LectureSchedule_Sqr", allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "LectureSchedule_Sqr", strategy = GenerationType.SEQUENCE)
	private long LecSchId;
	@Column(length = 2000)
	private String StartUrl;
	@Column(length = 2000)
	private String JoinUrl;
	@Column
	private long Duration;
	@Column(length = 2000)
	private String Descr;
	@Column(length = 255)
	private String Title;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date StartTime;
	@Column(length = 255)
	private String Uuid;
	@Column(length = 255)
	private String HostId;
	@Column(length = 255)
	private String MeetingId;
	@Column(length = 50)
	private String Types;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CourseId", referencedColumnName = "CourseId", insertable = true, updatable = true)
	@NotNull
	private CourseDetails courseDetails; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UnitId", referencedColumnName = "UnitId", insertable = true, updatable = true)
	@NotNull
	private UnitDetails unitDetails;

	public LectureSchedule() {
		super();
	}

	public LectureSchedule(long lecSchId, String startUrl, String joinUrl, long duration, String descr, String title,
			Date startTime, String uuid, String hostId, String meetingId, String types, CourseDetails courseDetails,
			UnitDetails unitDetails,String createdBy) {
		super();
		LecSchId = lecSchId;
		StartUrl = startUrl;
		JoinUrl = joinUrl;
		Duration = duration;
		Descr = descr;
		Title = title;
		StartTime = startTime;
		Uuid = uuid;
		HostId = hostId;
		MeetingId = meetingId;
		Types = types;
		this.courseDetails = courseDetails;
		this.unitDetails = unitDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}

	public long getLecSchId() {
		return LecSchId;
	}

	public void setLecSchId(long lecSchId) {
		LecSchId = lecSchId;
	}

	public String getStartUrl() {
		return StartUrl;
	}

	public void setStartUrl(String startUrl) {
		StartUrl = startUrl;
	}

	public String getJoinUrl() {
		return JoinUrl;
	}

	public void setJoinUrl(String joinUrl) {
		JoinUrl = joinUrl;
	}

	public long getDuration() {
		return Duration;
	}

	public void setDuration(long duration) {
		Duration = duration;
	}

	public String getDescr() {
		return Descr;
	}

	public void setDescr(String descr) {
		Descr = descr;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public Date getStartTime() {
		return StartTime;
	}

	public void setStartTime(Date startTime) {
		StartTime = startTime;
	}

	public String getUuid() {
		return Uuid;
	}

	public void setUuid(String uuid) {
		Uuid = uuid;
	}

	public String getHostId() {
		return HostId;
	}

	public void setHostId(String hostId) {
		HostId = hostId;
	}

	public String getMeetingId() {
		return MeetingId;
	}

	public void setMeetingId(String meetingId) {
		MeetingId = meetingId;
	}

	public String getTypes() {
		return Types;
	}

	public void setTypes(String types) {
		Types = types;
	}

	public CourseDetails getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(CourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}

	public UnitDetails getUnitDetails() {
		return unitDetails;
	}

	public void setUnitDetails(UnitDetails unitDetails) {
		this.unitDetails = unitDetails;
	}
}
