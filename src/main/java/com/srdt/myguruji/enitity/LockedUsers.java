package com.srdt.myguruji.enitity;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "LockedUsers")
@NamedQueries({
	@NamedQuery(name="LockedUsers.getStatusByStudentId", query = "select a from LockedUsers a where a.studentDetails.StudentId=:ID and a.lockStatus='Y' and a.lockstatusid =(select max(b.lockstatusid) from LockedUsers b where b.studentDetails.StudentId = :ID) "),
	@NamedQuery(name="LockedUsers.getStatuscountByStudentId", query = "select count(a) from LockedUsers a where a.studentDetails.StudentId=:ID and a.lockStatus='Y' and a.lockstatusid =(select max(b.lockstatusid) from LockedUsers b where b.studentDetails.StudentId = :ID) "),
	@NamedQuery(name="LockedUsers.unlockUser", query = "update LockedUsers a set a.lockStatus='N' where a.studentDetails.StudentId=:ID"),
	@NamedQuery(name="LockedUsers.getLockedUsers", query = "select a from LockedUsers a where a.lockStatus = 'Y'"),
	@NamedQuery(name="LockedUsers.getLockedUserStudentIdByBatch",query="select lu.studentDetails.StudentId from LockedUsers lu where lu.batchDetails.BatchId=:batchid and lu.lockStatus='Y'")
})
public class LockedUsers  extends SharedField
{
	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "LockedUsers_Sqr", sequenceName = "LockedUsers_Sqr")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LockedUsers_Sqr")
	@Column
	private long lockstatusid;
	
	@Column(length = 1)
	@NotNull
	private String lockStatus;
	
	@Column(length = 500)
	private String reason;
	
	@Column(length = 500)
	private String resolve;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CourseId", referencedColumnName = "CourseId", insertable = true, updatable=true, nullable=false)
	@NotNull
	private CourseDetails courseDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="BatchId", referencedColumnName = "BatchId", insertable = true, updatable = true, nullable = false)
	@NotNull
	private BatchDetails batchDetails;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="StudentId", referencedColumnName = "StudentId", insertable=true, updatable = true, nullable = false)
	@NotNull
	private StudentDetails studentDetails;
	
	public LockedUsers(long lockstatusid, String lockStatus, String reason, String resolve, CourseDetails courseDetails,
			BatchDetails batchDetails, StudentDetails studentDetails, String createdby) {
		super();
		this.lockstatusid = lockstatusid;
		this.lockStatus = lockStatus;
		this.reason = reason;
		this.resolve = resolve;
		this.courseDetails = courseDetails;
		this.batchDetails = batchDetails;
		this.studentDetails = studentDetails;
		setCreatedBy(createdby);
		setModifiedBy(createdby);
	}
	public long getLockstatusid() {
		return lockstatusid;
	}
	public void setLockstatusid(long lockstatusid) {
		this.lockstatusid = lockstatusid;
	}
	public String getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
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
	public StudentDetails getStudentDetails() {
		return studentDetails;
	}
	public void setStudentDetails(StudentDetails studentDetails) {
		this.studentDetails = studentDetails;
	}
	public LockedUsers() {
		super();
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getResolve() {
		return resolve;
	}
	public void setResolve(String resolve) {
		this.resolve = resolve;
	}
}