package com.srdt.myguruji.enitity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "AssignmentDetails")
@NamedQueries({
	@NamedQuery(name="AssignmentDetails.getAssignmentByAssignId",query="select ad from AssignmentDetails ad where ad.assignmentID=: assignId"),
	@NamedQuery(name="AssignmentDetails.getAssignmentByEmpId",query="select at from AssignmentDetails at where at.CreatedBy= : emplId and at.courseDetails.CourseId=: courseId"),
	//@NamedQuery(name="AssignmentDetails.getAssignmentDetail",query=" select a from AssignmentDetails a where a.level=:levl and a.CreatedBy=: createdby and a.courseDetails.CourseId=: courseId"),
	//@NamedQuery(name="AssignmentDetails.getAssignmentDetailsforUnit",query="select am from AssignmentDetails am where am.level='U'and am.CreatedBy=: createdby and am.courseDetails.CourseId=: courseId and am.assignmentID in (:ids)"),
	@NamedQuery(name = "AssignmentDetails.findAssignmentById", query = "select a from AssignmentDetails a where a.assignmentID=:id"),
	@NamedQuery(name="AssignmentDetails.setAttachmentstatus",query="update AssignmentDetails set hasQuestion='Y' where assignmentID=:assignId")
})
public class AssignmentDetails extends SharedField implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "AssignmentDetails_sqr", sequenceName = "AssignmentDetails_sqr", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "AssignmentDetails_sqr",strategy = GenerationType.SEQUENCE)
	private long assignmentID;
	
	@Column(length = 50, nullable = false)
	@NotBlank
	private String assignmentTitle;
	
	@Column(length = 500,nullable = true)
	private String assignmentDescr;
	
	@Column(length = 500,nullable = true)
	private String assignmentType;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dueDate;
	
	@Column(length = 1, nullable = false)
	@NotBlank
	private String hasPlan = "N";
	
	@Column(length = 1, nullable = false)
	@NotBlank
	private String hasUnit = "N";
	
	@Column(length = 1, nullable = false)
	@NotBlank
	private String hasQuestion = "N";
	
	@Column(length = 1, nullable = false)
	@NotBlank
	private String isPublished = "N";
	
	@Column(length = 1, nullable = false)
	@NotBlank
	private String level;
	
	@Column
	private double maxMarks = 0;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CourseId", referencedColumnName = "CourseId",insertable = true, updatable = true)
	@NotNull
	private CourseDetails courseDetails;
	
	@OneToMany(mappedBy="assignmentDetails",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<AssignmentPlanMapping> assignmentplan;
	
	public AssignmentDetails() {
		super();
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public AssignmentDetails(long assignmentID, @NotBlank String assignmentTitle, String assignmentDescr,
			String assignmentType, Date dueDate, @NotBlank String hasPlan, @NotBlank String hasUnit,
			@NotBlank String hasQuestion, @NotBlank String isPublished, @NotBlank String level, double maxMarks,
			@NotNull CourseDetails courseDetails) {
		super();
		this.assignmentID = assignmentID;
		this.assignmentTitle = assignmentTitle;
		this.assignmentDescr = assignmentDescr;
		this.assignmentType = assignmentType;
		this.dueDate = dueDate;
		this.hasPlan = hasPlan;
		this.hasUnit = hasUnit;
		this.hasQuestion = hasQuestion;
		this.isPublished = isPublished;
		this.level = level;
		this.maxMarks = maxMarks;
		this.courseDetails = courseDetails;
	}

	public AssignmentDetails(long assignmentID, @NotBlank String assignmentTitle, String assignmentDescr,
			String assignmentType, Date dueDate, @NotBlank String hasPlan, @NotBlank String hasUnit,
			@NotBlank String hasQuestion, @NotBlank String isPublished, double maxMarks,
			@NotNull CourseDetails courseDetails) {
		super();
		this.assignmentID = assignmentID;
		this.assignmentTitle = assignmentTitle;
		this.assignmentDescr = assignmentDescr;
		this.assignmentType = assignmentType;
		this.dueDate = dueDate;
		this.hasPlan = hasPlan;
		this.hasUnit = hasUnit;
		this.hasQuestion = hasQuestion;
		this.isPublished = isPublished;
		this.maxMarks = maxMarks;
		this.courseDetails = courseDetails;
	}

	public long getAssignmentID() {
		return assignmentID;
	}

	public void setAssignmentID(long assignmentID) {
		this.assignmentID = assignmentID;
	}

	public String getAssignmentTitle() {
		return assignmentTitle;
	}

	public void setAssignmentTitle(String assignmentTitle) {
		this.assignmentTitle = assignmentTitle;
	}

	public String getAssignmentDescr() {
		return assignmentDescr;
	}

	public void setAssignmentDescr(String assignmentDescr) {
		this.assignmentDescr = assignmentDescr;
	}

	public String getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getHasPlan() {
		return hasPlan;
	}

	public void setHasPlan(String hasPlan) {
		this.hasPlan = hasPlan;
	}

	public String getHasUnit() {
		return hasUnit;
	}

	public void setHasUnit(String hasUnit) {
		this.hasUnit = hasUnit;
	}

	public String getHasQuestion() {
		return hasQuestion;
	}

	public void setHasQuestion(String hasQuestion) {
		this.hasQuestion = hasQuestion;
	}

	public String getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(String isPublished) {
		this.isPublished = isPublished;
	}

	public double getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(double maxMarks) {
		this.maxMarks = maxMarks;
	}

	public CourseDetails getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(CourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}

	public List<AssignmentPlanMapping> getAssignmentplan() {
		return assignmentplan;
	}

	public void setAssignmentplan(List<AssignmentPlanMapping> assignmentplan) {
		this.assignmentplan = assignmentplan;
	}

	
}