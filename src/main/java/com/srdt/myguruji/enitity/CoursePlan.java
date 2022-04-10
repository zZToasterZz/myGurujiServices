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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CoursePlan",uniqueConstraints = @UniqueConstraint(columnNames = {"BatchId"}))
@NamedQueries({
	@NamedQuery(name="CoursePlan.findCourseByCourseId",query="select c from CourseDetails c where c.CourseId=:CourseId"),
	@NamedQuery(name="CoursePlan.deleteByCoursePlanId",query="delete from CoursePlan where CoursePlanId in(:CoursePlanId)"),
	@NamedQuery(name="CoursePlan.findCoursePlanByCourseId",query="select c from CoursePlan c join fetch c.courseDetails d where d.CourseId=:CourseId"),
	@NamedQuery(name="CoursePlan.findCourseBookById",query="select b from CourseBooks b where b.BookId=:BookId"),
	@NamedQuery(name="CoursePlan.deleteCourseBookByCoursePlanId",query="delete from CourseBooks where BookId not in(:BookId) and coursePlan.CoursePlanId=:CoursePlanId"),
	@NamedQuery(name="CoursePlan.deleteUnitDetailsByCoursePlanId",query="delete from UnitDetails where UnitId not in(:UnitId) and coursePlan.CoursePlanId=:CoursePlanId"),
	@NamedQuery(name="CoursePlan.findCoursePlanDetails",query="select c from CoursePlan c"),
	@NamedQuery(name="CoursePlan.findCoursePlanDetailsbyCoursePlanId",query="select distinct c from CoursePlan c join fetch c.courseDetails "
	                                                                        +"join fetch c.unitDetails where c.CoursePlanId =:CoursePlanId"),

	@NamedQuery(name="CoursePlan.findCoursePlanDetailsbyBatchId",query="select distinct c from CoursePlan c join fetch c.courseDetails "
	                                                                        +"join fetch c.unitDetails where c.batchDetails.BatchId =:BatchId"),
	@NamedQuery(name="CoursePlan.findCoursePlan",query="select new com.srdt.myguruji.model.Translate(c.CoursePlanId,c.CoursePlanTitle,c.PlanCode,c.CoursePlanDescr) from CoursePlan c inner join c.courseDetails d"),
	@NamedQuery(name="CoursePlan.findCoursePlanbyCourseId",query="select new com.srdt.myguruji.model.Translate(c.CoursePlanId,c.CoursePlanTitle,c.PlanCode,c.CoursePlanDescr) from CoursePlan c inner join c.courseDetails d where d.CourseId =:CourseId"),
	@NamedQuery(name="CoursePlan.findPlandIdByEmplid",query="select distinct new com.srdt.myguruji.model.Translate(p.CoursePlanId,p.CoursePlanTitle) from CoursePlan p inner join p.batchDetails q inner join q.facultyTaggingDetails r "
	                                                        +"inner join r.facultyDetails s where s.Emplid=:Emplid"),
	@NamedQuery(name="CoursePlan.findCoursePlanByEmplid",query="select new com.srdt.myguruji.model.PlanSearch(d.CourseId,c.CoursePlanId,c.PlanCode,c.CoursePlanTitle,d.CourseCode,d.CourseTitle) "
			                                                  +"from CoursePlan c inner join c.courseDetails d where c.CoursePlanId "
	                                                          +"in(select p.CoursePlanId from CoursePlan p inner join p.batchDetails q "
			                                                  +"inner join q.facultyTaggingDetails r "
	                                                          +"inner join r.facultyDetails s where s.Emplid like CASE WHEN CONCAT('%',:Emplid,'%')='%%' THEN '%%' ELSE :Emplid END) and "
			                                                  +"c.PlanCode like CONCAT('%',:plancode,'%') and c.CoursePlanTitle like CONCAT('%',:plantitle,'%') and d.CourseId like CASE WHEN CONCAT('%',:courseid,'%')='%%' THEN '%%' ELSE CAST(:courseid as long) END"),
	@NamedQuery(name = "CoursePlan.getCourseBookByCourseId", query = "select c from CoursePlan c join fetch c.courseBooks where c.CoursePlanId in(select a.CoursePlanId from CoursePlan a where a.courseDetails.CourseId=:CourseId)"),
	@NamedQuery(name="CoursePlan.getCoursePlanById",query="select cp from CoursePlan cp where cp.CoursePlanId=: courseplanId")
})
public class CoursePlan extends SharedField implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(allocationSize=1,initialValue=1,name="CoursePlan_Sqr",sequenceName="CoursePlan_Sqr")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="CoursePlan_Sqr")
	@Column
	private long CoursePlanId;
	@Column(length=150,nullable=false)
	private String CoursePlanTitle;
	@Column(length=50,unique=true,nullable=false)
	private String PlanCode;
	@Column(length=5000,nullable=false)
	private String CoursePlanDescr;
	@Column(length=2,nullable=true)
	private String LecCount;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "CourseId",referencedColumnName = "CourseId",insertable = true,updatable = true,nullable = false)
	@NotNull
	private CourseDetails courseDetails;

	@OneToMany(mappedBy="coursePlan",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<UnitDetails> unitDetails;
	
	@OneToMany(mappedBy="coursePlan",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<CourseBooks> courseBooks;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="BatchId",referencedColumnName="BatchId",nullable = false,insertable=true,updatable=true)
	@NotNull
	private BatchDetails batchDetails;	

	public CoursePlan() {
		super();
	}
	
	public CoursePlan(String coursePlanTitle, String coursePlanDescr, @NotNull CourseDetails courseDetails,String createdBy,String planCode,String lecCount) {
		super();
		CoursePlanTitle = coursePlanTitle;
		CoursePlanDescr = coursePlanDescr;
		this.courseDetails = courseDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		PlanCode=planCode;
		LecCount = lecCount;
	} 

	public BatchDetails getBatchDetails() {
		return batchDetails;
	}

	public void setBatchDetails(BatchDetails batchDetails) {
		this.batchDetails = batchDetails;
	}
	public String getLecCount() {
		return LecCount;
	}

	public void setLecCount(String lecCount) {
		LecCount = lecCount;
	}

	public List<CourseBooks> getCourseBooks() {
		return courseBooks;
	}

	public void setCourseBooks(List<CourseBooks> courseBooks) {
		this.courseBooks = courseBooks;
	}	
	
	public String getPlanCode() {
		return PlanCode;
	}

	public void setPlanCode(String planCode) {
		PlanCode = planCode;
	}

	public long getCoursePlanId() {
		return CoursePlanId;
	}

	public void setCoursePlanId(long coursePlanId) {
		CoursePlanId = coursePlanId;
	}

	public String getCoursePlanTitle() {
		return CoursePlanTitle;
	}

	public void setCoursePlanTitle(String coursePlanTitle) {
		CoursePlanTitle = coursePlanTitle;
	}

	public String getCoursePlanDescr() {
		return CoursePlanDescr;
	}

	public void setCoursePlanDescr(String coursePlanDescr) {
		CoursePlanDescr = coursePlanDescr;
	}

	public CourseDetails getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(CourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}

	public List<UnitDetails> getUnitDetails() {
		return unitDetails;
	}

	public void setUnitDetails(List<UnitDetails> unitDetails) {
		this.unitDetails = unitDetails;
	}
}
