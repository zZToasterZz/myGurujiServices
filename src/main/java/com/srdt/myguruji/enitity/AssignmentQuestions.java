package com.srdt.myguruji.enitity;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Table(name = "AssignmentQuestions",uniqueConstraints = @UniqueConstraint(columnNames = {"AssignmentID","QuestionId"}))

@NamedQueries({
	@NamedQuery(name="AssignmentQuestions.checkExistingQues",query="select count(*) from AssignmentQuestions aq where aq.questionDetails.QuestionId=: quesId and aq.assignmentDetails.assignmentID=: assignId"),
	@NamedQuery(name="AssignmentQuestions.getAssignmentquestionByAssignId",query=" select aq from AssignmentQuestions aq where aq.assignmentDetails.assignmentID=: assignmentId ")
})

public class AssignmentQuestions extends SharedField implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "AssignmentQuestions_sqr", sequenceName = "AssignmentQuestions_sqr", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "AssignmentQuestions_sqr",strategy = GenerationType.SEQUENCE)
	private long AssignmentQuestionID;
	
	@Column(precision = 10,scale = 2)
	private double Marks;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AssignmentID", referencedColumnName = "AssignmentID", insertable = true, updatable = true)
	@NotNull
	private AssignmentDetails assignmentDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CourseId", referencedColumnName = "CourseId",insertable = true, updatable = true)
	@NotNull
	private CourseDetails courseDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QuestionId", referencedColumnName = "QuestionId",insertable = true, updatable = true)
	@NotNull
	private QuestionDetails questionDetails;

	public AssignmentQuestions() {
		super();
	}

	public AssignmentQuestions(long assignmentQuestionID, double marks,
			@NotNull AssignmentDetails assignmentDetails, @NotNull CourseDetails courseDetails,
			@NotNull QuestionDetails questionDetails,String createdby) {
		super();
		AssignmentQuestionID = assignmentQuestionID;
		Marks = marks;
		this.assignmentDetails = assignmentDetails;
		this.courseDetails = courseDetails;
		this.questionDetails = questionDetails;
		setCreatedBy(createdby);
		setModifiedBy(createdby);
	}
	
	

	public long getAssignmentQuestionID() {
		return AssignmentQuestionID;
	}

	public void setAssignmentQuestionID(long assignmentQuestionID) {
		AssignmentQuestionID = assignmentQuestionID;
	}

	public double getMarks() {
		return Marks;
	}

	public void setMarks(double marks) {
		Marks = marks;
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

	public QuestionDetails getQuestionDetails() {
		return questionDetails;
	}

	public void setQuestionDetails(QuestionDetails questionDetails) {
		this.questionDetails = questionDetails;
	}
	
	
	
	
	
}