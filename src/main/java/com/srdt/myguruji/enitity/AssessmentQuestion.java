package com.srdt.myguruji.enitity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "AssessmentQuestion",uniqueConstraints = @UniqueConstraint(columnNames = {"AssessmentId","QuestionId"}))
@NamedQuery(name="AssessmentQuestion.updatequestionid",query="update AssessmentQuestion set  questionDetails.QuestionId=:quesid where AssessmentQuestionId=:assessId")
public class AssessmentQuestion extends SharedField implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "AssessmentQuestion_Sqr", sequenceName = "AssessmentQuestion_Sqr")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "AssessmentQuestion_Sqr")
	private long AssessmentQuestionId;
	
	@Column(precision = 10,scale = 2)
	private BigDecimal Marks;
	
	@Column(precision = 10,scale = 2)
	private BigDecimal PartialMarks;
	
	@Column(length = 1)
	private String PartialMarking="N";
	
	@Column(length = 1)
	private String IsSubQues="N";
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QuestionId", referencedColumnName = "QuestionId", insertable = true, updatable = true,nullable = false)
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
	
	@OneToMany(mappedBy = "assessmentQuestion",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<AssessmentSubQuestion> subQuestions;
	
	public AssessmentQuestion() {
		super();
	}	

	public AssessmentQuestion(BigDecimal marks, BigDecimal partialMarks, String partialMarking,
			QuestionDetails questionDetails, AssessmentDetails assessmentDetails, AssessmentSection assessmentSection,
			CourseDetails courseDetails,String createdBy,String IsSubQues) {
		super();
		Marks = marks;
		PartialMarks = partialMarks;
		PartialMarking = partialMarking;
		this.questionDetails = questionDetails;
		this.assessmentDetails = assessmentDetails;
		this.assessmentSection = assessmentSection;
		this.courseDetails = courseDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		this.IsSubQues = IsSubQues;
	}

	public String getIsSubQues() {
		return IsSubQues;
	}

	public void setIsSubQues(String isSubQues) {
		IsSubQues = isSubQues;
	}

	public BigDecimal getMarks() {
		return Marks;
	}

	public List<AssessmentSubQuestion> getSubQuestions() {
		return subQuestions;
	}

	public void setSubQuestions(List<AssessmentSubQuestion> subQuestions) {
		this.subQuestions = subQuestions;
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

	public long getAssessmentQuestionId() {
		return AssessmentQuestionId;
	}
	
	public void setAssessmentQuestionId(long assessmentQuestionId) {
		AssessmentQuestionId = assessmentQuestionId;
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
