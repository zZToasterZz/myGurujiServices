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
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "CourseObjectiveDetails")
@NamedQueries({
	@NamedQuery(name = "CourseObjectiveDetails.findCourseObjectiveByCourseId", query = "select new com.srdt.myguruji.model.Translate(o.ObjectiveId,o.ObjectiveCode,o.ObjectiveCode,o.Descr) from CourseObjectiveDetails o inner join o.courseDetails c where c.CourseId=:CourseId"),
	@NamedQuery(name = "CourseObjectiveDetails.deleteByObjectiveId", query = "delete from CourseObjectiveDetails where CourseId in(:CourseId)"),
	@NamedQuery(name = "CourseObjectiveDetails.CourseObjectiveById", query = "select a from CourseObjectiveDetails a where a.ObjectiveId=:ObjectiveId"),
	@NamedQuery(name = "CourseObjectiveDetails.deleteCourseObjectiveMapping", query = "delete from CourseObjectiveMaping a where a.unitDetails.UnitId=:UnitId and a.ObjectiveMapingId not in(:ObjectiveMapingId)")
})
public class CourseObjectiveDetails extends SharedField implements Serializable {

	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "CourseObjectiveDetails_Sqr", sequenceName = "CourseObjectiveDetails_Sqr")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CourseObjectiveDetails_Sqr")
	private long ObjectiveId;
	@Column(length = 2000)
	@NotBlank
	private String Descr;
	
	@Column(length = 100, nullable = true)
	private String ObjectiveCode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CourseId", referencedColumnName = "CourseId", insertable = true, updatable = true)
	private CourseDetails courseDetails;

	@OneToMany(mappedBy = "objectiveDetails",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<CourseObjectiveMaping> objectiveMapings;
	
	public CourseObjectiveDetails() {
		super();
	}

	public CourseObjectiveDetails(String descr, CourseDetails courseDetails,String objectiveCode,String createdBy) {
		super();
		Descr = descr;
		this.courseDetails = courseDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		ObjectiveCode = objectiveCode;
	}
    
	public List<CourseObjectiveMaping> getObjectiveMapings() {
		return objectiveMapings;
	}

	public void setObjectiveMapings(List<CourseObjectiveMaping> objectiveMapings) {
		this.objectiveMapings = objectiveMapings;
	}

	public String getObjectiveCode() {
		return ObjectiveCode;
	}

	public void setObjectiveCode(String objectiveCode) {
		ObjectiveCode = objectiveCode;
	}

	public long getObjectiveId() {
		return ObjectiveId;
	}

	public void setObjectiveId(long objectiveId) {
		ObjectiveId = objectiveId;
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
