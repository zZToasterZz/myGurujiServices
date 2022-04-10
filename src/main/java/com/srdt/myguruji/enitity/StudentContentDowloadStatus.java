package com.srdt.myguruji.enitity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "StudentContentDowloadStatus",uniqueConstraints = @UniqueConstraint(columnNames = {"ContentId","StudentId"}))
@NamedQueries({
	@NamedQuery(name="StudentContentDowloadStatus.getDocDownloadCount",query="select count(scd.DownloadTime) from StudentContentDowloadStatus scd where scd.batchDetails.BatchId=:batchid and scd.coursePlan.CoursePlanId=:crsplanid and scd.unit.UnitId=:unid and (scd.ContentType='Documents' or scd.ContentType='docs') and scd.studentDetails.StudentId=:studentid and scd.status='yes'"),
	@NamedQuery(name="StudentContentDowloadStatus.getVideoDownloadCount",query="select count(scd.DownloadTime) from StudentContentDowloadStatus scd where scd.batchDetails.BatchId=:batchid and scd.coursePlan.CoursePlanId=:crsplanid and scd.unit.UnitId=:unid and (scd.ContentType='Videos' or scd.ContentType='vid') and scd.studentDetails.StudentId=:studentid and scd.status='yes'"),
})

public class StudentContentDowloadStatus extends SharedField implements Serializable {

	@Id
	@SequenceGenerator(name = "StudentContentDowloadStatus_sqr", sequenceName = "StudentContentDowloadStatus_sqr", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "StudentContentDowloadStatus_sqr",strategy = GenerationType.SEQUENCE)
	private long ContentDowloadStatusId;
	
	@Column
	private Date DownloadTime;

	@Column
	private Date UploadTime;

	@Column
	private String status;

	@Column
	private double marks;
	
	@Column
	private String ContentType;

	@Column
	private String FileContentId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CourseId", referencedColumnName = "CourseId",insertable = true, updatable = true)
	@NotNull
	private CourseDetails courseDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BatchId", referencedColumnName = "BatchId", insertable = true, updatable = true)
	@NotNull
	private BatchDetails batchDetails;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "StudentId", referencedColumnName = "StudentId", insertable = true, updatable = true)
	@NotNull
	private StudentDetails studentDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CoursePlanId", referencedColumnName = "CoursePlanId", insertable = true, updatable = true)
	@NotNull
	private CoursePlan coursePlan;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UnitId", referencedColumnName = "UnitId", insertable = true, updatable = true)
	@NotNull
	private UnitDetails unit;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ContentId", referencedColumnName = "UnitContentId", insertable = true, updatable = true)
	@NotNull
	private UnitContent contentid;

	public StudentContentDowloadStatus() {
		super();
	}

	public StudentContentDowloadStatus(long contentDowloadStatusId, Date downloadTime, Date uploadTime, String status, double marks, String contentType, @NotNull CourseDetails courseDetails, @NotNull BatchDetails batchDetails, @NotNull StudentDetails studentDetails, @NotNull CoursePlan coursePlan, @NotNull UnitDetails unit, @NotNull UnitContent contentid,String fileContentId) {
		ContentDowloadStatusId = contentDowloadStatusId;
		DownloadTime = downloadTime;
		UploadTime = uploadTime;
		this.status = status;
		this.marks = marks;
		ContentType = contentType;
		this.courseDetails = courseDetails;
		this.batchDetails = batchDetails;
		this.studentDetails = studentDetails;
		this.coursePlan = coursePlan;
		this.unit = unit;
		this.contentid = contentid;
		FileContentId=fileContentId;
		setModifiedBy("SANDHYA");
		setCreatedBy("SANDHYA");
	}

	public long getContentDowloadStatusId() {
		return ContentDowloadStatusId;
	}

	public void setContentDowloadStatusId(long contentDowloadStatusId) {
		ContentDowloadStatusId = contentDowloadStatusId;
	}

	public Date getDownloadTime() {
		return DownloadTime;
	}

	public void setDownloadTime(Date downloadTime) {
		DownloadTime = downloadTime;
	}

	public String getContentType() {
		return ContentType;
	}

	public void setContentType(String contentType) {
		ContentType = contentType;
	}

	public CourseDetails getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(CourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}

	public BatchDetails getBatchDetails() {
		return batchDetails;
	}

	public void setBatchDetails(BatchDetails batchDetails) {
		this.batchDetails = batchDetails;
	}

	public CoursePlan getCoursePlan() {
		return coursePlan;
	}

	public void setCoursePlan(CoursePlan coursePlan) {
		this.coursePlan = coursePlan;
	}

	public UnitDetails getUnit() {
		return unit;
	}

	public void setUnit(UnitDetails unit) {
		this.unit = unit;
	}

	public UnitContent getContentid() {
		return contentid;
	}

	public void setContentid(UnitContent contentid) {
		this.contentid = contentid;
	}

	public Date getUploadTime() {
		return UploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		UploadTime = uploadTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getMarks() {
		return marks;
	}

	public void setMarks(double marks) {
		this.marks = marks;
	}

	public StudentDetails getStudentDetails() {
		return studentDetails;
	}

	public void setStudentDetails(StudentDetails studentDetails) {
		this.studentDetails = studentDetails;
	}

	public String getFileContentId() {
		return FileContentId;
	}

	public void setFileContentId(String fileContentId) {
		FileContentId = fileContentId;
	}
}
