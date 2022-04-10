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

@Entity
@Table(name="BatchDetails")
@NamedQueries({
	@NamedQuery(name="BatchDetails.findBatchCountByCourseId",query="select count(*)+1 from BatchDetails b inner join b.courseDetails c where c.CourseId=:CourseId"),
	@NamedQuery(name="BatchDetails.findAllBatches", query="select new com.srdt.myguruji.model.Batches(c.CourseId,b.BatchId,b.Batchtitle,b.BatchCode,b.Descr,b.CreatedBy,b.BatchType,b.BatchYear,b.BatchSeq,b.Section) from BatchDetails b inner join b.courseDetails c"),
	@NamedQuery(name="BatchDetails.search", query="select new com.srdt.myguruji.model.Batches(c.CourseId,b.BatchId,b.Batchtitle,b.BatchCode,b.Descr,b.CreatedBy,b.BatchType,"
	                                                      +"b.BatchYear,b.BatchSeq,b.Section) from BatchDetails b inner join b.courseDetails c "
			                                              +"where b.BatchId like CONCAT('%',:id,'%') and b.Batchtitle like CONCAT('%',:title,'%') and b.BatchCode like CONCAT('%',:code,'%')"),
	@NamedQuery(name="BatchDetails.findBatchByBatchId",query="select b from BatchDetails b where b.BatchId=:BatchId"),
	@NamedQuery(name="BatchDetails.findBatchDetailsByBatchId",query="select b from BatchDetails b join fetch b.studentEnrollments c join fetch c.student where b.BatchId=:BatchId order by c.student.CampusId"),
	@NamedQuery(name="BatchDetails.findBatchByCourseId", query="select new com.srdt.myguruji.model.Batches(c.CourseId,b.BatchId,b.Batchtitle,b.BatchCode,b.Descr,b.CreatedBy,b.BatchType,b.BatchYear,b.BatchSeq,b.Section) from BatchDetails b inner join b.courseDetails c where c.CourseId=:CourseId order by b.BatchCode"),
	@NamedQuery(name="BatchDetails.findNotMapBatchByCourseId", query="select new com.srdt.myguruji.model.Batches(c.CourseId,b.BatchId,b.Batchtitle,b.BatchCode,b.Descr,b.CreatedBy,b.BatchType,b.BatchYear,b.BatchSeq,b.Section) from BatchDetails b inner join b.courseDetails c where c.CourseId=:CourseId "
	                                                            +"and b.BatchId not in(select x.batchDetails.BatchId from CoursePlan x where x.courseDetails.CourseId=:CourseId) and b.BatchId in(select t.batchDetails.BatchId from FacultyTaggingDetails t where t.facultyDetails.FacultyId=(select FacultyId from FacultyDetails where Emplid=:Emplid))"),
	@NamedQuery(name="BatchDetails.getBatchByBatchCode", query="select b from BatchDetails b where b.BatchCode=:batchcode")
})
public class BatchDetails extends SharedField implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(allocationSize=1,initialValue=1,name="Batches_Sqr",sequenceName="Batches_Sqr")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="Batches_Sqr")
	@Column
	private long BatchId;
	@Column(length=30,nullable=false,unique=true)
	private String BatchCode;
	@Column(length=100)
	private String Batchtitle;
	@Column(length=2,nullable=false)
	private String BatchSeq;
	@Column(length=150)
	private String Descr;
	@Column(length=10,nullable=false)
	private String BatchYear;
	@Column(length=25,nullable=false)
	private String BatchType;
	@Column(length=50,nullable=true)
	private String Section;
	
	@ManyToOne(fetch=FetchType.LAZY) 
	@JoinColumn(name="CourseId",insertable=true,updatable=true,referencedColumnName="CourseId",nullable=false)
	private CourseDetails courseDetails = new CourseDetails();
	
	@OneToMany(mappedBy="batchDetails",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	List<StudentEnrollment> studentEnrollments;
	
	@OneToMany(mappedBy="batchDetails",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private List<FacultyTaggingDetails> facultyTaggingDetails;
	
	@OneToOne(mappedBy="batchDetails",fetch=FetchType.LAZY ,cascade=CascadeType.ALL)
	private CoursePlan coursePlan;
	
	@OneToMany(mappedBy="batchDetails",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private List<ScheduledAssessmentMapping> scheduledAssessmentMappings;
	
	@OneToMany(mappedBy="batch",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private List<StudentAssessmentStatusDetails> studentAssessmentStatusDetails;
	
	
	public BatchDetails() {
		super();
	}

	public BatchDetails(String batchCode, String batchtitle, String batchSeq, String descr, String batchYear,
			String batchType, CourseDetails courseDetails,String createdBy,String section) {
		super();
		BatchCode = batchCode;
		Batchtitle = batchtitle;
		BatchSeq = batchSeq;
		Descr = descr;
		BatchYear = batchYear;
		BatchType = batchType;
		this.courseDetails = courseDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		Section=section;
	}

	public CoursePlan getCoursePlan() {
		return coursePlan;
	}

	public void setCoursePlan(CoursePlan coursePlan) {
		this.coursePlan = coursePlan;
	}

	public List<ScheduledAssessmentMapping> getScheduledAssessmentMappings() {
		return scheduledAssessmentMappings;
	}

	public void setScheduledAssessmentMappings(List<ScheduledAssessmentMapping> scheduledAssessmentMappings) {
		this.scheduledAssessmentMappings = scheduledAssessmentMappings;
	}

	
	public List<StudentAssessmentStatusDetails> getStudentAssessmentStatusDetails() {
		return studentAssessmentStatusDetails;
	}

	public void setStudentAssessmentStatusDetails(List<StudentAssessmentStatusDetails> studentAssessmentStatusDetails) {
		this.studentAssessmentStatusDetails = studentAssessmentStatusDetails;
	}

	public String getSection() {
		return Section;
	}

	public void setSection(String section) {
		Section = section;
	}
    
	public List<FacultyTaggingDetails> getFacultyTaggingDetails() {
		return facultyTaggingDetails;
	}

	public void setFacultyTaggingDetails(List<FacultyTaggingDetails> facultyTaggingDetails) {
		this.facultyTaggingDetails = facultyTaggingDetails;
	}

	public List<StudentEnrollment> getStudentEnrollments() {
		return studentEnrollments;
	}

	public void setStudentEnrollments(List<StudentEnrollment> studentEnrollments) {
		this.studentEnrollments = studentEnrollments;
	}

	public long getBatchId() {
		return BatchId;
	}

	public void setBatchId(long batchId) {
		BatchId = batchId;
	}

	public String getBatchCode() {
		return BatchCode;
	}

	public void setBatchCode(String batchCode) {
		BatchCode = batchCode;
	}

	public String getBatchtitle() {
		return Batchtitle;
	}

	public void setBatchtitle(String batchtitle) {
		Batchtitle = batchtitle;
	}

	public String getBatchSeq() {
		return BatchSeq;
	}

	public void setBatchSeq(String batchSeq) {
		BatchSeq = batchSeq;
	}

	public String getDescr() {
		return Descr;
	}

	public void setDescr(String descr) {
		Descr = descr;
	}

	public String getBatchYear() {
		return BatchYear;
	}

	public void setBatchYear(String batchYear) {
		BatchYear = batchYear;
	}

	public String getBatchType() {
		return BatchType;
	}

	public void setBatchType(String batchType) {
		BatchType = batchType;
	}

	public CourseDetails getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(CourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}
}
