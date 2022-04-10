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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "CourseDetails")
@NamedQueries({
	@NamedQuery(name="CourseDetails.updateCourseDetails",query="update CourseDetails set CourseTitle=:CourseTitle,"
			                                     +"CourseDescr=:CourseDescr,CourseCode=:CourseCode where CourseId=:CourseId"),
	@NamedQuery(name="CourseDetails.deleteCourseDetailsByCourseId",query="delete from CourseDetails where CourseId in(:CourseId)"),
	@NamedQuery(name="CourseDetails.findAllCourseDetails",query="select distinct new com.srdt.myguruji.model.Courses(x.CourseCode, x.CourseTitle,"
	                                                           +"x.CourseDescr,x.CourseId, x.CreatedBy,y.PlanCode,y.CoursePlanId) "
	                                                           +"from CourseDetails x inner join x.coursePlan y "
	                                                           +"left join x.batches z left join z.facultyTaggingDetails a left join a.facultyDetails b "
	                                                           +"where coalesce(b.Emplid,'') like :Emplid and y.batchDetails.BatchId=z.BatchId"),
	@NamedQuery(name="CourseDetails.searchCourses",query="select distinct new com.srdt.myguruji.model.Courses(x.CourseCode, x.CourseTitle, x.CourseDescr,x.CourseId,"
	                                                    +"x.CreatedBy) from CourseDetails x left join x.batches y left join y.facultyTaggingDetails z left join z.facultyDetails a "
			                                            +"where x.CourseId like CONCAT('%',:id,'%') and x.CourseCode like CONCAT('%',:code,'%') "
	                                                    +"and x.CourseTitle like CONCAT('%',:title,'%') and coalesce(a.Emplid,'') like CONCAT('%',:Emplid,'%') order by x.CourseCode"),
	@NamedQuery(name="CourseDetails.findAllCourses",query="select distinct new com.srdt.myguruji.model.Translate(x.CourseId, x.CourseTitle,x.CourseCode, x.CourseDescr) "
                                                           +"from CourseDetails x left join x.batches y left join y.facultyTaggingDetails z left join z.facultyDetails a "
                                                           +"where coalesce(a.Emplid,'') like :Emplid"),
	@NamedQuery(name="CourseDetails.findCourseByCourseId",query="select new com.srdt.myguruji.model.Translate(c.CourseId,c.CourseTitle,c.CourseCode,c.CourseDescr) from CourseDetails c where c.CourseId=:CourseId"),
	@NamedQuery(name = "CourseDetails.findCourseDetailsByCourseId", query = "select c from CourseDetails c where c.CourseId=:CourseId")
})
public class CourseDetails extends SharedField implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(allocationSize = 1,initialValue=1,name = "CourseDetails_Sqr",sequenceName="CourseDetails_Sqr")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="CourseDetails_Sqr")
	@Column
	private long CourseId;
	@Column(unique = true,nullable = false,length = 50)
	private String CourseCode;
	@Column(length = 100,nullable = false)
	private String CourseTitle;
	@Column(length = 500, nullable=false)
	private String CourseDescr;
	
	@OneToMany(mappedBy="courseDetails",cascade=CascadeType.ALL,fetch=FetchType.LAZY)	
	private List<CoursePlan> coursePlan ;
	
	@OneToMany(mappedBy="courseDetails",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<TopicDetails> topicDetails ;
	
	@OneToMany(mappedBy="courseDetails",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<BatchDetails>  batches ;
	
	@OneToMany(mappedBy="courseDetails",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<QuestionDetails> questionDetails ;
	
	@OneToMany(mappedBy = "courseDetails",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<CourseObjectiveDetails> courseObjectiveDetails;
	
	@OneToMany(mappedBy = "courseDetails",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<AssessmentSubQuestion> assessmentSubQuestions;
	
	@OneToMany(mappedBy = "courseDetails",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<AssessmentDetails> assessmentDetails;
	
	@OneToMany(mappedBy = "courseDetails",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<AssessmentQuestion> assessmentQuestions;
	
	@OneToMany(mappedBy = "courseDetails",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<ScheduledAssessment> scheduledAssessments; 
	
	@OneToMany(mappedBy = "courseDetails",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<ScheduledAssessmentMapping> scheduledAssessmentMappings;
	
	@OneToMany(mappedBy = "courseDetails",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<AssessmentResponce> assessmentResponces;
	
	@OneToMany(mappedBy = "courseDetails", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<CourseContent> courseContents;
	
	@OneToMany(mappedBy = "courseDetails", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<LectureSchedule> lectureSchedules;
	
	public CourseDetails() {
		super();
	}

	public CourseDetails(String courseCode, String courseTitle, String courseDescr,String createdBy) {
		super();
		CourseCode = courseCode;
		CourseTitle = courseTitle;
		CourseDescr = courseDescr;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}

	public List<LectureSchedule> getLectureSchedules() {
		return lectureSchedules;
	}

	public void setLectureSchedules(List<LectureSchedule> lectureSchedules) {
		this.lectureSchedules = lectureSchedules;
	}

	public List<CourseContent> getCourseContents() {
		return courseContents;
	}

	public void setCourseContents(List<CourseContent> courseContents) {
		this.courseContents = courseContents;
	}

	public List<AssessmentResponce> getAssessmentResponces() {
		return assessmentResponces;
	}

	public void setAssessmentResponces(List<AssessmentResponce> assessmentResponces) {
		this.assessmentResponces = assessmentResponces;
	}

	public List<ScheduledAssessment> getScheduledAssessments() {
		return scheduledAssessments;
	}

	public void setScheduledAssessments(List<ScheduledAssessment> scheduledAssessments) {
		this.scheduledAssessments = scheduledAssessments;
	}

	public List<ScheduledAssessmentMapping> getScheduledAssessmentMappings() {
		return scheduledAssessmentMappings;
	}

	public void setScheduledAssessmentMappings(List<ScheduledAssessmentMapping> scheduledAssessmentMappings) {
		this.scheduledAssessmentMappings = scheduledAssessmentMappings;
	}

	public List<AssessmentQuestion> getAssessmentQuestions() {
		return assessmentQuestions;
	}

	public void setAssessmentQuestions(List<AssessmentQuestion> assessmentQuestions) {
		this.assessmentQuestions = assessmentQuestions;
	}

	public long getCourseId() {
		return CourseId;
	}

	public List<BatchDetails> getBatches() {
		return batches;
	}

	public void setBatches(List<BatchDetails> batches) {
		this.batches = batches;
	}

	public List<QuestionDetails> getQuestionDetails() {
		return questionDetails;
	}

	public void setQuestionDetails(List<QuestionDetails> questionDetails) {
		this.questionDetails = questionDetails;
	}

	public List<CourseObjectiveDetails> getCourseObjectiveDetails() {
		return courseObjectiveDetails;
	}

	public void setCourseObjectiveDetails(List<CourseObjectiveDetails> courseObjectiveDetails) {
		this.courseObjectiveDetails = courseObjectiveDetails;
	}

	public List<AssessmentSubQuestion> getAssessmentSubQuestions() {
		return assessmentSubQuestions;
	}

	public void setAssessmentSubQuestions(List<AssessmentSubQuestion> assessmentSubQuestions) {
		this.assessmentSubQuestions = assessmentSubQuestions;
	}

	public List<AssessmentDetails> getAssessmentDetails() {
		return assessmentDetails;
	}

	public void setAssessmentDetails(List<AssessmentDetails> assessmentDetails) {
		this.assessmentDetails = assessmentDetails;
	}

	public void setCourseId(long courseId) {
		CourseId = courseId;
	}

	public String getCourseCode() {
		return CourseCode;
	}

	public void setCourseCode(String courseCode) {
		CourseCode = courseCode;
	}

	public String getCourseTitle() {
		return CourseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		CourseTitle = courseTitle;
	}

	public String getCourseDescr() {
		return CourseDescr;
	}

	public void setCourseDescr(String courseDescr) {
		CourseDescr = courseDescr;
	}

	public List<CoursePlan> getCoursePlan() {
		return coursePlan;
	}

	public void setCoursePlan(List<CoursePlan> coursePlan) {
		this.coursePlan = coursePlan;
	}

	public List<TopicDetails> getTopicDetails() {
		return topicDetails;
	}

	public void setTopicDetails(List<TopicDetails> topicDetails) {
		this.topicDetails = topicDetails;
	}	
}
