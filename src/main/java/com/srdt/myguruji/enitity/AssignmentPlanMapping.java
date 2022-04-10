package com.srdt.myguruji.enitity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "AssignmentPlanMapping")
@NamedQueries({
	@NamedQuery(name="AssignmentPlanMapping.getAssignmentbyCourseplanID",query="select apm from AssignmentPlanMapping apm where apm.coursePlan.CoursePlanId=: courseplanId"),
	@NamedQuery(name="AssignmentPlanMapping.getAssignmentplanbyAssignmentId",query="select plan from AssignmentPlanMapping plan where plan.assignmentDetails.assignmentID=: assignId")
})

public class AssignmentPlanMapping extends SharedField implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "AssignmentPlanMapping_sqr", sequenceName = "AssignmentPlanMapping_sqr", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "AssignmentPlanMapping_sqr",strategy = GenerationType.SEQUENCE)
	private long AssignmentPlanMappingID;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CourseId", referencedColumnName = "CourseId",insertable = true, updatable = true)
	@NotNull
	private CourseDetails courseDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BatchId", referencedColumnName = "BatchId", insertable = true, updatable = true)
	private BatchDetails batchDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CoursePlanId", referencedColumnName = "CoursePlanId", insertable = true, updatable = true)
	private CoursePlan coursePlan;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AssignmentID", referencedColumnName = "AssignmentID", insertable = true, updatable = true)
	private AssignmentDetails assignmentDetails;
	
	@OneToMany(mappedBy="assignmentPlanMapping",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<AssignmentUnitMapping> assignmentunit;

	public AssignmentPlanMapping() {
		super();
	}

	public AssignmentPlanMapping(long assignmentPlanMappingID, @NotNull CourseDetails courseDetails,
			@NotBlank BatchDetails batchDetails,CoursePlan coursePlan,
			@NotBlank AssignmentDetails assignmentDetails) {
		super();
		AssignmentPlanMappingID = assignmentPlanMappingID;
		this.courseDetails = courseDetails;
		this.batchDetails = batchDetails;
		this.coursePlan = coursePlan;
		this.assignmentDetails = assignmentDetails;
	}

	public long getAssignmentPlanMappingID() {
		return AssignmentPlanMappingID;
	}

	public void setAssignmentPlanMappingID(long assignmentPlanMappingID) {
		AssignmentPlanMappingID = assignmentPlanMappingID;
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

	public AssignmentDetails getAssignmentDetails() {
		return assignmentDetails;
	}

	public void setAssignmentDetails(AssignmentDetails assignmentDetails) {
		this.assignmentDetails = assignmentDetails;
	}

	public List<AssignmentUnitMapping> getAssignmentunit() {
		return assignmentunit;
	}

	public void setAssignmentunit(List<AssignmentUnitMapping> assignmentunit) {
		this.assignmentunit = assignmentunit;
	}
	
	
}