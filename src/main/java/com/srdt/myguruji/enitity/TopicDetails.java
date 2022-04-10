package com.srdt.myguruji.enitity;

import java.io.Serializable;
import java.util.ArrayList;
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

@Entity
@Table(name = "TopicDetails")
@NamedQueries({
	@NamedQuery(name="TopicDetails.deleteTopicDetailsByTopicId",query="delete from TopicDetails where TopicId in (:TopicId)"),
	@NamedQuery(name="TopicDetails.deleteByCourseDetails",query="delete from TopicDetails where TopicId not in(:TopicId) and courseDetails.CourseId=:CourseId"),
	@NamedQuery(name="TopicDetails.findTopicsDetailByCourseId",query="select t from TopicDetails t where t.courseDetails.CourseId=:CourseId and t.IsActive='Y'"),
	@NamedQuery(name="TopicDetails.findAllTopicDetails",query="select t from TopicDetails t"),
	@NamedQuery(name="TopicDetails.findAllActiveTopicDetails",query="select t from TopicDetails t where t.IsActive='Y'"),
	@NamedQuery(name="TopicDetails.findTopicDetailsById",query="select t from TopicDetails t where TopicId=:TopicId and IsActive='Y' order by TopicId asc"),
	@NamedQuery(name="TopicDetails.findTopicsByCourseIdAndTopicId",query="select t from TopicDetails t inner join fetch t.courseDetails u where u.CourseId=:CourseId and t.TopicId not in(:TopicId) "
	                                                                    +"and t.TopicId not in(select a.TopicId from SubUnitDetails a "
                                                                        +"inner join a.unitDetails c join c.coursePlan d "
                                                                        +"inner join d.courseDetails e where e.CourseId=:CourseId) order by t.TopicId asc"),
	@NamedQuery(name="TopicDetails.findTopicDetailsByCourseId",query="select new com.srdt.myguruji.model.Translate(c.TopicId,c.TopicTitle,c.TopicDescr,c.TopicDescr) from TopicDetails c where c.courseDetails.CourseId=:CourseId  order by c.TopicId asc"),
	@NamedQuery(name = "TopicDetails.removeTopics",query = "update TopicDetails set IsActive='N' where TopicId in(:TopicId)")	
})
public class TopicDetails extends SharedField implements Serializable{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(initialValue=1,allocationSize=1,name="TopicDetails_Sqr",sequenceName="TopicDetails_Sqr")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="TopicDetails_Sqr")
	@Column
	private long TopicId;
	@Column(length=2000,nullable=true)
	private String TopicTitle;
	@Column(length=2000,nullable=true)
	private String TopicDescr;
	@Column(length = 50,nullable = true)
	private String SysUnitId;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CourseId",referencedColumnName="CourseId",insertable=true,updatable=true,nullable=false)
	private CourseDetails courseDetails = new CourseDetails();

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="OutcomeId",referencedColumnName="OutcomeId",insertable=true,updatable=true)
	private CourseOutcome courseoutcome;
	

	@OneToMany(mappedBy="topicDetails",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<QuestionDetails> questionDetails = new ArrayList<>();

		
	public TopicDetails() {
		super();
	}

	public TopicDetails(String topicTitle, String topicDescr, CourseDetails courseDetails,String createdBy,String SysUnitId) {
		super();
		TopicTitle = topicTitle;
		TopicDescr = topicDescr;
		this.courseDetails = courseDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		this.SysUnitId = SysUnitId;
	}

	public TopicDetails(long topicId, String topicTitle, String topicDescr, CourseDetails courseDetails,String createdBy,String SysUnitId) {
		super();
		TopicId = topicId;
		TopicTitle = topicTitle;
		TopicDescr = topicDescr;
		this.courseDetails = courseDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		this.SysUnitId = SysUnitId;
	}

	public String getSysUnitId() {
		return SysUnitId;
	}

	public void setSysUnitId(String sysUnitId) {
		SysUnitId = sysUnitId;
	}

	public String getTopicTitle() {
		return TopicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		TopicTitle = topicTitle;
	}

	public String getTopicDescr() {
		return TopicDescr;
	}

	public void setTopicDescr(String topicDescr) {
		TopicDescr = topicDescr;
	}

	public List<QuestionDetails> getQuestionDetails() {
		return questionDetails;
	}

	public void setQuestionDetails(List<QuestionDetails> questionDetails) {
		this.questionDetails = questionDetails;
	}
	
	/*
	 * public CoursePlan getCoursePlan() { return coursePlan; }
	 * 
	 * public void setCoursePlan(CoursePlan coursePlan) { this.coursePlan =
	 * coursePlan; }
	 */
	  
	  public long getTopicId() { return TopicId; }
	 

	public void setTopicId(long topicId) {
		TopicId = topicId;
	}

	public String getShortDescr() {
		return TopicTitle;
	}

	public void setShortDescr(String topicTitle) {
		TopicTitle = topicTitle;
	}

	public String getLongDescr() {
		return TopicDescr;
	}

	public void setLongDescr(String topicDescr) {
		TopicDescr = topicDescr;
	}

	public CourseDetails getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(CourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}

	public CourseOutcome getCourseoutcome() {
		return courseoutcome;
	}

	public void setCourseoutcome(CourseOutcome courseoutcome) {
		this.courseoutcome = courseoutcome;
	}
}
