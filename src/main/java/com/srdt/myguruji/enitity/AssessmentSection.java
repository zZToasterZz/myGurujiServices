package com.srdt.myguruji.enitity;

import java.io.Serializable;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "AssessmentSection")
@NamedQueries({
	@NamedQuery(name = "AssessmentSection.findAssessmentSectionById",query = "select a from AssessmentSection a where a.SectionId=:SectionId")
})
public class AssessmentSection extends SharedField implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "AssessmentSection_Sqr", sequenceName = "AssessmentSection_Sqr")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AssessmentSection_Sqr")
	private long SectionId;
	@Column(length = 150)
	@NotBlank
	private String Title;
	@Column
	@Min(value = 1)
	private int SectionMarks;
	@Column(length = 600)
	@NotBlank
	private String SectionNote;
	@Column
	@Min(value = 1)
	private int TotalQuestion;
	@Column
	@Min(value = 1)
	private int AttemptQuestion;
	@Column(length = 500,nullable = false)
	private String Descr;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TemplateId", referencedColumnName = "TemplateId", insertable = true,updatable = true)
	private AssessmentTemplate template;

	@OneToMany(mappedBy = "assessmentSection",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<AssessmentSubQuestion> assessmentSubQuestions;
		
	@OneToMany(mappedBy = "assessmentSection",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<AssessmentQuestion> assessmentQuestions;
	
	public AssessmentSection() {
		super();
	}

	public AssessmentSection(@NotBlank String title, @Min(1) int sectionMarks, @NotBlank String sectionNote,
			@Min(1) int totalQuestion, @Min(1) int attemptQuestion, String descr, AssessmentTemplate template,CourseDetails courseDetails,String createdBy) {
		super();
		Title = title;
		SectionMarks = sectionMarks;
		SectionNote = sectionNote;
		TotalQuestion = totalQuestion;
		AttemptQuestion = attemptQuestion;
		Descr = descr;
		this.template = template;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}

	public long getSectionId() {
		return SectionId;
	}

	public void setSectionId(long sectionId) {
		SectionId = sectionId;
	}

	public List<AssessmentSubQuestion> getAssessmentSubQuestions() {
		return assessmentSubQuestions;
	}

	public void setAssessmentSubQuestions(List<AssessmentSubQuestion> assessmentSubQuestions) {
		this.assessmentSubQuestions = assessmentSubQuestions;
	}

	public List<AssessmentQuestion> getAssessmentQuestions() {
		return assessmentQuestions;
	}

	public void setAssessmentQuestions(List<AssessmentQuestion> assessmentQuestions) {
		this.assessmentQuestions = assessmentQuestions;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public int getSectionMarks() {
		return SectionMarks;
	}

	public void setSectionMarks(int sectionMarks) {
		SectionMarks = sectionMarks;
	}

	public String getSectionNote() {
		return SectionNote;
	}

	public void setSectionNote(String sectionNote) {
		SectionNote = sectionNote;
	}

	public int getTotalQuestion() {
		return TotalQuestion;
	}

	public void setTotalQuestion(int totalQuestion) {
		TotalQuestion = totalQuestion;
	}

	public int getAttemptQuestion() {
		return AttemptQuestion;
	}

	public void setAttemptQuestion(int attemptQuestion) {
		AttemptQuestion = attemptQuestion;
	}

	public String getDescr() {
		return Descr;
	}

	public void setDescr(String descr) {
		Descr = descr;
	}

	public AssessmentTemplate getTemplate() {
		return template;
	}

	public void setTemplate(AssessmentTemplate template) {
		this.template = template;
	}
}
