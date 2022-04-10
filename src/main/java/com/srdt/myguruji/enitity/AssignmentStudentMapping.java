package com.srdt.myguruji.enitity;

import java.io.Serializable;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "AssignmentStudentMapping",uniqueConstraints = @UniqueConstraint(columnNames = {"AssignmentID","StudentId "}))
@NamedQueries({
	@NamedQuery(name="AssignmentStudentMapping.getUnitAssignmentCount",query="select count(*) from AssignmentStudentMapping asm where asm.coursePlan.CoursePlanId= : courseplanid and asm.studentDetails.StudentId=: emplId and asm.assignmentDetails.level='U' and asm.assignmentDetails.assignmentID in(:ids)"),
	@NamedQuery(name="AssignmentStudentMapping.getCoursewiseAssignmentCount",query="select count(*) from AssignmentStudentMapping asm where asm.coursePlan.CoursePlanId= : courseplanid and asm.studentDetails.StudentId=: emplId and asm.assignmentDetails.level='C'"),
	@NamedQuery(name="AssignmentStudentMapping.getStudentAssignment",query="select asm from AssignmentStudentMapping asm where asm.studentDetails.StudentId=: studentId and asm.assignmentDetails.courseDetails.CourseId=: assignId"),
	@NamedQuery(name="AssignmentStudentMapping.getStudentMappList",query=" select studentDetails.StudentId from AssignmentStudentMapping where assignmentDetails.assignmentID=:assignmntId")
})
public class AssignmentStudentMapping extends SharedField implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "AssignmentStudentMap_sqr", sequenceName = "AssignmentStudentMap_sqr", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "AssignmentStudentMap_sqr",strategy = GenerationType.SEQUENCE)
	private long AssignmentStudentMapID;
	
	@Column(length = 1)
	private String IsActive = "N";
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AssignmentID", referencedColumnName = "AssignmentID", insertable = true, updatable = true)
	@NotNull
	private AssignmentDetails assignmentDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AssignmentPlanMappingID", referencedColumnName = "AssignmentPlanMappingID", insertable = true, updatable = true)
	@NotNull
	private AssignmentPlanMapping assignmentPlanMapping;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CourseId", referencedColumnName = "CourseId",insertable = true, updatable = true)
	@NotNull
	private CourseDetails courseDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BatchId", referencedColumnName = "BatchId", insertable = true, updatable = true)
	@NotNull
	private BatchDetails batchDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CoursePlanId", referencedColumnName = "CoursePlanId", insertable = true, updatable = true)
	@NotNull
	private CoursePlan coursePlan;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "StudentId", referencedColumnName = "StudentId", insertable = true, updatable = true)
	@NotNull
	private StudentDetails studentDetails;

	public AssignmentStudentMapping() {
		super();
	}

	public AssignmentStudentMapping(long assignmentStudentMapID, String isActive,
			@NotNull AssignmentDetails assignmentDetails, @NotNull AssignmentPlanMapping assignmentPlanMapping,
			@NotNull CourseDetails courseDetails, @NotNull BatchDetails batchDetails, @NotNull CoursePlan coursePlan,
			@NotNull StudentDetails studentDetails,String createdBy) {
		super();
		AssignmentStudentMapID = assignmentStudentMapID;
		IsActive = isActive;
		this.assignmentDetails = assignmentDetails;
		this.assignmentPlanMapping = assignmentPlanMapping;
		this.courseDetails = courseDetails;
		this.batchDetails = batchDetails;
		this.coursePlan = coursePlan;
		this.studentDetails = studentDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}

	public long getAssignmentStudentMapID() {
		return AssignmentStudentMapID;
	}

	public void setAssignmentStudentMapID(long assignmentStudentMapID) {
		AssignmentStudentMapID = assignmentStudentMapID;
	}

	public String getIsActive() {
		return IsActive;
	}

	public void setIsActive(String isActive) {
		IsActive = isActive;
	}

	public AssignmentDetails getAssignmentDetails() {
		return assignmentDetails;
	}

	public void setAssignmentDetails(AssignmentDetails assignmentDetails) {
		this.assignmentDetails = assignmentDetails;
	}

	public AssignmentPlanMapping getAssignmentPlanMapping() {
		return assignmentPlanMapping;
	}

	public void setAssignmentPlanMapping(AssignmentPlanMapping assignmentPlanMapping) {
		this.assignmentPlanMapping = assignmentPlanMapping;
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

	public StudentDetails getStudentDetails() {
		return studentDetails;
	}

	public void setStudentDetails(StudentDetails studentDetails) {
		this.studentDetails = studentDetails;
	}
	
	
	
}