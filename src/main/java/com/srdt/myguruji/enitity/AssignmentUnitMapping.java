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
@Table(name = "AssignmentUnitMapping",uniqueConstraints = @UniqueConstraint(columnNames = {"AssignmentID","UnitId"}))
@NamedQueries({
	@NamedQuery(name="AssignmentUnitMapping.getAssignmentIds",query="select distinct am.assignmentDetails.assignmentID from AssignmentUnitMapping am where am.unit.UnitId=:unitId"),
	@NamedQuery(name="AssignmentUnitMapping.getAssignmentDetail",query=" select distinct a from AssignmentUnitMapping a where a.assignmentDetails.level=:levl and a.CreatedBy=: createdby and a.coursePlan.CoursePlanId=: courseplanId and a.assignmentDetails.assignmentID in (select distinct am.assignmentDetails.assignmentID from AssignmentUnitMapping am)")	,
	@NamedQuery(name="AssignmentUnitMapping.getAssignmentDetailsforUnit",query="select am from AssignmentUnitMapping am where am.assignmentDetails.level='U'and am.CreatedBy=: createdby and am.coursePlan.CoursePlanId=: courseplanId and am.assignmentDetails.assignmentID in (:ids)"),
	@NamedQuery(name="AssignmentUnitMapping.getFacultyAssignmentCountforCourseplan",query="select count(distinct am.assignmentDetails.assignmentID) from AssignmentUnitMapping am where am.CreatedBy=:emplid and am.coursePlan.CoursePlanId=:crplanid and am.assignmentDetails.level='C'")	,
	@NamedQuery(name="AssignmentUnitMapping.getFacultyAssignmentCountforUnit",query="select count(distinct am.assignmentDetails.assignmentID) from AssignmentUnitMapping am where am.CreatedBy=:emplid and am.coursePlan.CoursePlanId=:crplanid and am.unit.UnitId=:unitid and am.assignmentDetails.level='U'")
})
public class AssignmentUnitMapping extends SharedField implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "AssignmentUnitMapping_sqr", sequenceName = "AssignmentUnitMapping_sqr", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "AssignmentUnitMapping_sqr",strategy = GenerationType.SEQUENCE)
	private long AssignmentUnitID;
	
	@Column(length = 2000,nullable = true)
	private String Topics;
	
	@Column(length = 500,nullable = true)
	private String TopicIDs;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AssignmentID", referencedColumnName = "AssignmentID", insertable = true, updatable = true)
	private AssignmentDetails assignmentDetails;
	
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
	@JoinColumn(name = "UnitId", referencedColumnName = "UnitId", insertable = true, updatable = true)
	private UnitDetails unit;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AssignmentPlanMappingID", referencedColumnName = "AssignmentPlanMappingID", insertable = true, updatable = true)
	private AssignmentPlanMapping assignmentPlanMapping;

	public AssignmentUnitMapping() {
		super();
	}

	public AssignmentUnitMapping(long assignmentUnitID, String topics, String topicIDs,
			@NotBlank AssignmentDetails assignmentDetails, @NotNull CourseDetails courseDetails,
			@NotBlank BatchDetails batchDetails, @NotBlank CoursePlan coursePlan,
			@NotBlank AssignmentPlanMapping assignmentPlanMapping) {
		super();
		AssignmentUnitID = assignmentUnitID;
		Topics = topics;
		TopicIDs = topicIDs;
		this.assignmentDetails = assignmentDetails;
		this.courseDetails = courseDetails;
		this.batchDetails = batchDetails;
		this.coursePlan = coursePlan;
		this.assignmentPlanMapping = assignmentPlanMapping;
	}

	public long getAssignmentUnitID() {
		return AssignmentUnitID;
	}

	public void setAssignmentUnitID(long assignmentUnitID) {
		AssignmentUnitID = assignmentUnitID;
	}

	public String getTopics() {
		return Topics;
	}

	public void setTopics(String topics) {
		Topics = topics;
	}

	public String getTopicIDs() {
		return TopicIDs;
	}

	public void setTopicIDs(String topicIDs) {
		TopicIDs = topicIDs;
	}

	public AssignmentDetails getAssignmentDetails() {
		return assignmentDetails;
	}

	public void setAssignmentDetails(AssignmentDetails assignmentDetails) {
		this.assignmentDetails = assignmentDetails;
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

	public AssignmentPlanMapping getAssignmentPlanMapping() {
		return assignmentPlanMapping;
	}

	public void setAssignmentPlanMapping(AssignmentPlanMapping assignmentPlanMapping) {
		this.assignmentPlanMapping = assignmentPlanMapping;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UnitDetails getUnit() {
		return unit;
	}

	public void setUnit(UnitDetails unit) {
		this.unit = unit;
	}
	
	
}