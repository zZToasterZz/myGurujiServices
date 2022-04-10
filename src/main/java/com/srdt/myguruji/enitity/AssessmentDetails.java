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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "AssessmentDetails")
/*
 * @NamedStoredProcedureQueries({
 * 
 * @NamedStoredProcedureQuery(name =
 * "getAssessmentResponceByBaches",procedureName =
 * "assessmentresults",parameters = {
 * 
 * @StoredProcedureParameter(mode = ParameterMode.IN,name = "assessid",type =
 * Long.class),
 * 
 * @StoredProcedureParameter(mode = ParameterMode.IN,name = "batchid",type =
 * Long.class) }) })
 */

@NamedQueries({
	@NamedQuery(name = "AssessmentDetails.findAssessment", query = "select new com.srdt.myguruji.model.Assessment(a.AssessmentId,b.CourseId,coalesce(c.TemplateId,0),a.Title,a.AssessmentCode,a.Descr,b.CourseTitle,coalesce(c.Title),a.CreatedBy, a.Descr1) "
																	+"from AssessmentDetails a inner join a.courseDetails b "
																	+"left join a.template c where b.CourseId=:CourseId "
			                                                        +"and a.CreatedBy like case when concat('%',:CreatedBy,'%')='%%' then '%%' else :CreatedBy end"),

	@NamedQuery(name = "AssessmentDetails.findNotScheduledAssessment", query = "select new com.srdt.myguruji.model.Assessment(a.AssessmentId,b.CourseId,coalesce(c.TemplateId,0),a.Title,a.AssessmentCode,a.Descr,b.CourseTitle,coalesce(c.Title),a.CreatedBy, a.Descr1) "
																		+"from AssessmentDetails a inner join a.courseDetails b "
																		+"left join a.template c where b.CourseId=:CourseId "
															            +"and a.CreatedBy like case when concat('%',:CreatedBy,'%')='%%' then '%%' else :CreatedBy end "
																		+"and true=(select count(aq) > 0 from AssessmentQuestion aq where aq.assessmentDetails.AssessmentId=a.AssessmentId)"
															            +"and true!=(select count(am) > 0 from ScheduledAssessment am where am.assessmentDetails.AssessmentId=a.AssessmentId)"),
	@NamedQuery(name = "AssessmentDetails.findAlreadyScheduledAssessment", query = "select new com.srdt.myguruji.model.Assessment(a.AssessmentId,b.CourseId,coalesce(c.TemplateId,0),a.Title,a.AssessmentCode,a.Descr,b.CourseTitle,coalesce(c.Title),a.CreatedBy, a.Descr1) "
																				+"from AssessmentDetails a inner join a.courseDetails b "
																				+"left join a.template c where b.CourseId=:CourseId "
																	            +"and a.CreatedBy like case when concat('%',:CreatedBy,'%')='%%' then '%%' else :CreatedBy end "
																				+"and true=(select count(aq) > 0 from AssessmentQuestion aq where aq.assessmentDetails.AssessmentId=a.AssessmentId)"
																	            +"and true=(select count(am) > 0 from ScheduledAssessment am where am.assessmentDetails.AssessmentId=a.AssessmentId)"),
	@NamedQuery(name = "AssessmentDetails.findAssessmentDetailsById", query = "select a from AssessmentDetails a where a.AssessmentId=:AssessmentId"),
	@NamedQuery(name = "AssessmentDetails.getAssessmentDetailsById", query = "select a from AssessmentDetails a join fetch a.template b join fetch a.courseDetails e " 
																			 +"join fetch b.sections c "
																			 +"where a.AssessmentId=:AssessmentId"),
	/*
		@NamedQuery(name = "AssessmentDetails.getQuestionByAssessIdAndSecId", query = "select a from AssessmentQuestion a left join fetch a.questionDetails b "
																					+"left join b.questionType c left join fetch a.assessmentDetails d "
																					+"inner join a.assessmentSection f "
																					+"where d.AssessmentId=:AssessmentId and f.SectionId=:SectionId"),*/
		@NamedQuery(name = "AssessmentDetails.getQuestionByAssessIdAndSecId", query = "select a from AssessmentQuestion a left join fetch a.questionDetails b "
				+"left join b.questionType c left join fetch a.assessmentDetails d inner join a.assessmentSection f "
				+"where d.AssessmentId=:AssessmentId and f.SectionId=:SectionId and " +
				"a.AssessmentQuestionId=(select Max(aq.AssessmentQuestionId) from AssessmentQuestion aq where aq.assessmentDetails.AssessmentId=:AssessmentId and aq.questionDetails.QuestionId=b.QuestionId)"),

	@NamedQuery(name = "AssessmentDetails.getSubqustionByAssessmentQuestionId", query = "select a from AssessmentSubQuestion a join fetch a.assessmentQuestion f "
																						+"join fetch a.questionDetails b left join b.questionType c "
																						+"left join fetch a.assessmentDetails d inner join a.assessmentSection e "
																						+"where f.AssessmentQuestionId=:AssessmentQuestionId"),
	@NamedQuery(name = "AssessmentDetails.getScheduleByEmplid", query = "select a from ScheduledAssessment a join a.assessmentMappings b "
																		+ "join fetch a.assessmentDetails g "
																		+ "join fetch g.template "
																		+ "join fetch a.courseDetails c join b.batchDetails d join d.studentEnrollments e "
								                                        + "join e.student f where f.Emplid=:Emplid"),
	@NamedQuery(name = "AssessmentDetails.getTodayScheduleByEmplid", query = "select a from ScheduledAssessment a join a.assessmentMappings b "
																		+ "join fetch a.assessmentDetails g "
																		+ "join fetch g.template "
																		+ "join fetch a.courseDetails c join b.batchDetails d join d.studentEnrollments e "
															            + "join e.student f where f.Emplid=:Emplid and g.AssessmentId not in(select x.assessmentDetails.AssessmentId from AssessmentResponce x where x.Emplid=:Emplid)"),
	@NamedQuery(name = "AssessmentDetails.getScheduleByBatchId", query = "select a from ScheduledAssessment a join a.assessmentMappings b "
																	+ "join fetch a.assessmentDetails g "
																	+ "join fetch g.template "
																	+ "join fetch a.courseDetails c join b.batchDetails d "
														            + "where d.BatchId=:BatchId"),
	@NamedQuery(name = "AssessmentDetails.getScheduleByCourseId", query = "select a from ScheduledAssessment a join a.assessmentMappings b "
																			+ "join fetch a.assessmentDetails g "
																			+ "join fetch g.template "
																			+ "join fetch a.courseDetails c join b.batchDetails d "
																            + "where c.CourseId=:CourseId"),
	@NamedQuery(name = "AssessmentDetails.getInstructionByAssessmenId", query = "select a from ScheduledAssessment a "
																				+ "join fetch a.assessmentDetails b "
																				+ "join fetch b.template c join fetch c.sections "
																				+ "join fetch a.courseDetails "
																				+ "where b.AssessmentId=:AssessmentId"),
	@NamedQuery(name = "AssessmentDetails.getCondidatesByAssessmentId",query = "select new com.srdt.myguruji.model.Student(s.Emplid,s.ApplNbr,s.CampusId,s.FirstName,coalesce(s.MidleName,''),coalesce(s.LastName,''),s.FullName) from StudentDetails s "
																			 + "where s.Emplid in(select DISTINCT(a.Emplid) from AssessmentResponce a where a.assessmentDetails.AssessmentId=:AssessmentId)"),
	@NamedQuery(name = "AssessmentDetails.getQuestionReponceByAssessIdAndSecId", query = "select a from AssessmentQuestion a left join fetch a.questionDetails b "
																					+"left join b.questionType c left join fetch a.assessmentDetails d "
																					+"join fetch a.assessmentSection f left join fetch b.assessmentResponces g "
																					+"where g.assessmentDetails.AssessmentId=d.AssessmentId and d.AssessmentId=:AssessmentId and f.SectionId=:SectionId and g.Emplid=:Emplid"),
	@NamedQuery(name = "AssessmentDetails.getSubqustionResponceByAssessmentQuestionId", query = "select a from AssessmentSubQuestion a join fetch a.assessmentQuestion f "
																					+"join fetch a.questionDetails b left join b.questionType c left join fetch b.assessmentResponces g "
																					+"left join fetch a.assessmentDetails d inner join a.assessmentSection e "
																					+"where g.assessmentDetails.AssessmentId=d.AssessmentId and f.AssessmentQuestionId=:AssessmentQuestionId and g.Emplid=:Emplid"),
	@NamedQuery(name = "AssessmentDetails.getConductedAssessmentByCourseId",query = "select a from AssessmentDetails a inner join a.scheduledAssessments b where a.courseDetails.CourseId=:Id and b.EndDateTime < CURRENT_TIMESTAMP"),
	@NamedQuery(name = "AssessmentDetails.getBatchesByAssessmentId",query="select new com.srdt.myguruji.model.Batches(c.CourseId,b.BatchId,b.Batchtitle,b.BatchCode,b.Descr,b.CreatedBy,b.BatchType,b.BatchYear,b.BatchSeq,b.Section) from BatchDetails b inner join b.courseDetails c inner join b.scheduledAssessmentMappings d inner join d.scheduledAssessment e inner join e.assessmentDetails f where f.AssessmentId=:Id")
})
public class AssessmentDetails extends SharedField implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(initialValue = 1 ,allocationSize = 1,name = "AssessmentDetails_Sqr",sequenceName = "AssessmentDetails_Sqr")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "AssessmentDetails_Sqr")
	private long AssessmentId;
	
	@Column(length = 150,nullable = false)
	@NotBlank
	private String Title;
	
	@Column(length = 30,nullable = false)
	@NotBlank
	private String AssessmentCode;
	
	@Column(length = 500,nullable = true)
	private String Descr;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CourseId", referencedColumnName = "CourseId",insertable = true, updatable = true)
	@NotNull
	private CourseDetails courseDetails;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TemplateId", referencedColumnName = "TemplateId", insertable = true, updatable = true)
	private AssessmentTemplate template;
	
	@OneToMany(mappedBy = "assessmentDetails",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<AssessmentSubQuestion> assessmentSubQuestions;
	
	@OneToMany(mappedBy = "assessmentDetails",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<AssessmentQuestion> assessmentQuestions;
	
	@OneToOne(mappedBy = "assessmentDetails",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private ScheduledAssessment scheduledAssessments;
	
	@OneToMany(mappedBy = "assessmentDetails", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<AssessmentResponce> assessmentResponces;
	
	@OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<StudentAssessmentStatusDetails> studentAssessmentStatusDetails;
	
	
	
	public AssessmentDetails() {
		super();
	}

	public AssessmentDetails(@NotBlank String title, @NotBlank String assessmentCode, String descr,
			@NotNull CourseDetails courseDetails,String createdBy) {
		super();
		Title = title;
		AssessmentCode = assessmentCode;
		Descr = descr;
		this.courseDetails = courseDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}
	
	public AssessmentDetails(@NotBlank String title, @NotBlank String assessmentCode, String descr,
			@NotNull CourseDetails courseDetails,String createdBy, String Descr1) {
		super();
		Title = title;
		AssessmentCode = assessmentCode;
		Descr = descr;
		this.courseDetails = courseDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		setDescr1(Descr1);
	}

	public List<AssessmentResponce> getAssessmentResponces() {
		return assessmentResponces;
	}

	public void setAssessmentResponces(List<AssessmentResponce> assessmentResponces) {
		this.assessmentResponces = assessmentResponces;
	}

	public ScheduledAssessment getScheduledAssessments() {
		return scheduledAssessments;
	}

	public void setScheduledAssessments(ScheduledAssessment scheduledAssessments) {
		this.scheduledAssessments = scheduledAssessments;
	}

	public List<StudentAssessmentStatusDetails> getStudentAssessmentStatusDetails() {
		return studentAssessmentStatusDetails;
	}

	public void setStudentAssessmentStatusDetails(List<StudentAssessmentStatusDetails> studentAssessmentStatusDetails) {
		this.studentAssessmentStatusDetails = studentAssessmentStatusDetails;
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

	public AssessmentTemplate getTemplate() {
		return template;
	}

	public void setTemplate(AssessmentTemplate template) {
		this.template = template;
	}

	public long getAssessmentId() {
		return AssessmentId;
	}

	public void setAssessmentId(long assessmentId) {
		AssessmentId = assessmentId;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getAssessmentCode() {
		return AssessmentCode;
	}

	public void setAssessmentCode(String assessmentCode) {
		AssessmentCode = assessmentCode;
	}

	public String getDescr() {
		return Descr;
	}

	public void setDescr(String descr) {
		Descr = descr;
	}

	public CourseDetails getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(CourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}    
}
