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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="QuestionDetails")
@NamedQueries({
	@NamedQuery(name="QuestionDetails.findDiffLevelById",query="select d from DifficultyLevel d where d.DifficultyId=:DiffId"),
	@NamedQuery(name="QuestionDetails.findQuestionTypeById",query="select q from QuestionType q where q.TypeId=:TypeId"),
	@NamedQuery(name="QuestionDetails.findAllDifficultyLevel",query="select d from DifficultyLevel d"),
	@NamedQuery(name="QuestionDetails.findAllQuestionTypes",query="select q from QuestionType q"),
	@NamedQuery(name="QuestionDetails.findQuestionDetailsById",query="select q from QuestionDetails q where q.QuestionId=:QuestionId and q.IsActive='Y'"),
	@NamedQuery(name="QuestionDetails.searchQuestion", query="select q from QuestionDetails q join fetch q.topicDetails r join fetch q.questionType s join fetch q.courseDetails t join fetch q.difficultyLevel u left join fetch q.answerDetails v where q.IsActive='Y' and "
	                                                         +"r.TopicId like case when concat('%',:TopicId,'%')='%%' then '%%' else cast(:TopicId as long) end and "
			                                                 +"s.TypeId like case when concat('%',:TypeId,'%')='%%' then '%%' else cast(:TypeId as long) end and "
	                                                         +"t.CourseId like case when concat('%',:CourseId,'%')='%%' then '%%' else cast(:CourseId as long) end and "
			                                                 +"u.DifficultyId like case when concat('%',:DifficultyId,'%')='%%' then '%%' else cast(:DifficultyId as long) end"),
	@NamedQuery(name="QuestionDetails.findQuestionBankCount",query="select new com.srdt.myguruji.model.QuestionBankCount("
	                                                                +"q.courseDetails.CourseId,topicDetails.TopicId,q.questionType.TypeId,"
			                                                        +"q.courseDetails.CourseCode,q.questionType.Descr,topicDetails.TopicDescr,count(*),q.courseDetails.CourseDescr) from QuestionDetails q where q.IsActive='Y' and  "
                                                                    +"q.topicDetails.TopicId like case when concat('%',:TopicId,'%')='%%' then '%%' else cast(:TopicId as long) end and "
                                                                    +"q.questionType.TypeId like case when concat('%',:TypeId,'%')='%%' then '%%' else cast(:TypeId as long) end and "
                                                                    +"q.courseDetails.CourseId like case when concat('%',:CourseId,'%')='%%' then '%%' else cast(:CourseId as long) end "
                                                                    +"group by q.courseDetails.CourseId,topicDetails.TopicId,q.questionType.TypeId "
                                                                    +"order by q.courseDetails.CourseId,topicDetails.TopicId,q.questionType.TypeId"),

	@NamedQuery(name="QuestionDetails.setQuestionStatus",query="update QuestionDetails set IsActive='N' where QuestionId=:questionid")
})
public class QuestionDetails extends SharedField implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(initialValue=1,allocationSize=1,name = "QuestionDetails_Sqr",sequenceName="QuestionDetails_Sqr")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="QuestionDetails_Sqr")
	@Column
	private long QuestionId;
	@Lob
	@Column(length=16777000,nullable=false)
	@NotBlank
	private String QuestionText;
	
	@Column(length = 100)
	private String SrcLec;
	@Column(length = 100)
	private String BlmTaxonomy;
	@Column(length = 100)
	private String CourseObj;
	@Column(length = 100)
	private String ReferId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TopicId",referencedColumnName="TopicId",insertable=true,updatable=true,nullable=false)
	TopicDetails topicDetails;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TypeId",referencedColumnName="TypeId",insertable=true,updatable=true,nullable=false)
	QuestionType questionType;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CourseId",referencedColumnName="CourseId",insertable=true,updatable=true,nullable=false)
	CourseDetails courseDetails;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DifficultyId",referencedColumnName="DifficultyId",insertable=true,updatable=true,nullable=false)
	DifficultyLevel difficultyLevel;
	
	@OneToOne(mappedBy="questionDetails",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private AnswerDetails answerDetails;
	
	@OneToMany(mappedBy="questionDetails",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<AssessmentSubQuestion> assessmentSubQuestions;

	@OneToMany(mappedBy="questionDetails",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<AssessmentQuestion> assessmentQuestions;
	
	@OneToMany(mappedBy="questionDetails",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<AssessmentResponce> assessmentResponces;
	
	public QuestionDetails() {
		super();
	}

	public QuestionDetails(@NotBlank String questionText, TopicDetails topicDetails, QuestionType questionType,
			CourseDetails courseDetails, DifficultyLevel difficultyLevel,String createdBy,String referId,String srcLec,String blmTaxonomy,String courseObj) {
		super();
		QuestionText = questionText;
		this.topicDetails = topicDetails;
		this.questionType = questionType;
		this.courseDetails = courseDetails;
		this.difficultyLevel = difficultyLevel;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		ReferId=referId;
		SrcLec = srcLec;
		BlmTaxonomy = blmTaxonomy;
		CourseObj = courseObj;
	}

	public List<AssessmentResponce> getAssessmentResponces() {
		return assessmentResponces;
	}

	public void setAssessmentResponces(List<AssessmentResponce> assessmentResponces) {
		this.assessmentResponces = assessmentResponces;
	}

	public List<AssessmentQuestion> getAssessmentQuestions() {
		return assessmentQuestions;
	}

	public void setAssessmentQuestions(List<AssessmentQuestion> assessmentQuestions) {
		this.assessmentQuestions = assessmentQuestions;
	}

	public List<AssessmentSubQuestion> getAssessmentSubQuestions() {
		return assessmentSubQuestions;
	}

	public void setAssessmentSubQuestions(List<AssessmentSubQuestion> assessmentSubQuestions) {
		this.assessmentSubQuestions = assessmentSubQuestions;
	}

	public String getSrcLec() {
		return SrcLec;
	}

	public void setSrcLec(String srcLec) {
		SrcLec = srcLec;
	}

	public String getBlmTaxonomy() {
		return BlmTaxonomy;
	}

	public void setBlmTaxonomy(String blmTaxonomy) {
		BlmTaxonomy = blmTaxonomy;
	}

	public String getCourseObj() {
		return CourseObj;
	}

	public void setCourseObj(String courseObj) {
		CourseObj = courseObj;
	}

	public String getReferId() {
		return ReferId;
	}

	public void setReferId(String referId) {
		ReferId = referId;
	}

	public long getQuestionId() {
		return QuestionId;
	}

	public void setQuestionId(long questionId) {
		QuestionId = questionId;
	}

	public String getQuestionText() {
		return QuestionText;
	}

	public void setQuestionText(String questionText) {
		QuestionText = questionText;
	}

	public TopicDetails getTopicDetails() {
		return topicDetails;
	}

	public void setTopicDetails(TopicDetails topicDetails) {
		this.topicDetails = topicDetails;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public CourseDetails getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(CourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}

	public DifficultyLevel getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public AnswerDetails getAnswerDetails() {
		return answerDetails;
	}

	public void setAnswerDetails(AnswerDetails answerDetails) {
		this.answerDetails = answerDetails;
	}	
}
