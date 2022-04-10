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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "AssessmentSubQuestion")
public class AssessmentSubQuestion extends SharedField implements Serializable {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "AssessmentSubQuestion_Sqr", sequenceName = "AssessmentSubQuestion_Sqr")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "AssessmentSubQuestion_Sqr")
	private long AssessmentSubQuestionId;
	@Column(length = 500,nullable = false)
	@NotBlank
    private String SubSectionNote;
	
	@Column(precision = 10,scale = 2)
	private BigDecimal Marks;
	
	@Column(precision = 10,scale = 2)
	private BigDecimal PartialMarks;
	
	@Column(length = 1)
	private String PartialMarking="N";
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QuestionId", referencedColumnName = "QuestionId", insertable = true, updatable = true)
	private QuestionDetails questionDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AssessmentId", referencedColumnName = "AssessmentId", insertable = true, updatable = true)
	private AssessmentDetails assessmentDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SectionId", referencedColumnName = "SectionId", insertable = true, updatable = true)
	private AssessmentSection assessmentSection;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CourseId", referencedColumnName = "CourseId", insertable = true, updatable = true)
	private CourseDetails courseDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AssessmentQuestionId", referencedColumnName = "AssessmentQuestionId", insertable = true, updatable = true)
	private AssessmentQuestion assessmentQuestion;
	
	public AssessmentSubQuestion() {
		super();
	}
	
	public AssessmentSubQuestion(@NotBlank String subSectionNote, QuestionDetails questionDetails,
			AssessmentDetails assessmentDetails, AssessmentSection assessmentSection, CourseDetails courseDetails,String createdBy) {
		super();
		SubSectionNote = subSectionNote;
		this.questionDetails = questionDetails;
		this.assessmentDetails = assessmentDetails;
		this.assessmentSection = assessmentSection;
		this.courseDetails = courseDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}	

	public AssessmentSubQuestion(@NotBlank String subSectionNote, BigDecimal marks, BigDecimal partialMarks,
			String partialMarking, QuestionDetails questionDetails, AssessmentDetails assessmentDetails,
			AssessmentSection assessmentSection, CourseDetails courseDetails, AssessmentQuestion assessmentQuestion,String createdBy) {
		super();
		SubSectionNote = subSectionNote;
		Marks = marks;
		PartialMarks = partialMarks;
		PartialMarking = partialMarking;
		this.questionDetails = questionDetails;
		this.assessmentDetails = assessmentDetails;
		this.assessmentSection = assessmentSection;
		this.courseDetails = courseDetails;
		this.assessmentQuestion = assessmentQuestion;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}

	public BigDecimal getMarks() {
		return Marks;
	}

	public void setMarks(BigDecimal marks) {
		Marks = marks;
	}

	public BigDecimal getPartialMarks() {
		return PartialMarks;
	}

	public void setPartialMarks(BigDecimal partialMarks) {
		PartialMarks = partialMarks;
	}

	public String getPartialMarking() {
		return PartialMarking;
	}

	public void setPartialMarking(String partialMarking) {
		PartialMarking = partialMarking;
	}

	public AssessmentQuestion getAssessmentQuestion() {
		return assessmentQuestion;
	}

	public void setAssessmentQuestion(AssessmentQuestion assessmentQuestion) {
		this.assessmentQuestion = assessmentQuestion;
	}

	public long getAssessmentSubQuestionId() {
		return AssessmentSubQuestionId;
	}

	public void setAssessmentSubQuestionId(long assessmentSubQuestionId) {
		AssessmentSubQuestionId = assessmentSubQuestionId;
	}

	public String getSubSectionNote() {
		return SubSectionNote;
	}

	public void setSubSectionNote(String subSectionNote) {
		SubSectionNote = subSectionNote;
	}

	public QuestionDetails getQuestionDetails() {
		return questionDetails;
	}

	public void setQuestionDetails(QuestionDetails questionDetails) {
		this.questionDetails = questionDetails;
	}

	public AssessmentDetails getAssessmentDetails() {
		return assessmentDetails;
	}

	public void setAssessmentDetails(AssessmentDetails assessmentDetails) {
		this.assessmentDetails = assessmentDetails;
	}

	public AssessmentSection getAssessmentSection() {
		return assessmentSection;
	}

	public void setAssessmentSection(AssessmentSection assessmentSection) {
		this.assessmentSection = assessmentSection;
	}

	public CourseDetails getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(CourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}	
}
