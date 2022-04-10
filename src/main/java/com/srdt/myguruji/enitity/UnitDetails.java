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
import javax.validation.constraints.NotNull;

@Entity
@Table(name="UnitDetails")
@NamedQueries({
	@NamedQuery(name="UnitDetails.findCoursePlanById",query="select c from CoursePlan c where c.CoursePlanId=:CoursePlanId"),
	@NamedQuery(name="UnitDetails.deleteUnitDetailsByUnitId",query="delete from UnitDetails where UnitId in(:UnitId)"),
	@NamedQuery(name="UnitDetails.findAllUnitDetails",query="select u from UnitDetails u join fetch u.coursePlan"),
	@NamedQuery(name="UnitDetails.findUnitDetailsByCoursePlanId",query="select u from UnitDetails u join fetch u.coursePlan v where v.CoursePlanId=:CoursePlanId"),
	@NamedQuery(name="UnitDetails.findUnitsByCoursePlanId",query="select new com.srdt.myguruji.model.Translate(u.UnitId,u.UnitTitle,u.UnitTitle,u.UnitDescr) from UnitDetails u inner join u.coursePlan v where v.CoursePlanId=:CoursePlanId"),
	@NamedQuery(name = "UnitDetails.findUnitByUnitId", query = "select u from UnitDetails u where u.UnitId=:UnitId")
})
public class UnitDetails extends SharedField implements Serializable {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(initialValue=1,allocationSize=1,name="UnitDetails_Sqr",sequenceName="UnitDetails_Sqr")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="UnitDetails_Sqr")
	@Column
    private long UnitId;
	@Column(length=1500,nullable=false)
    private String UnitTitle;
	@Column(length=5000,nullable=false)
    private String UnitDescr;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CoursePlanId",referencedColumnName="CoursePlanId",insertable = true,updatable = true,nullable = false)
	@NotNull
    private CoursePlan coursePlan;
	@Column(length = 2000)
	private String Topics;
	@Column(length = 2000)
	private String Objectives;
	@Column(length = 2000)
	private String TopicsId;
	@Column(length = 2000)
	private String ObjectivesId;
    
    @OneToMany(mappedBy="unitDetails",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    private List<SubUnitDetails> subUnitDetails = new ArrayList<>();	
	
	@OneToMany(mappedBy = "unitDetails",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<CourseObjectiveMaping> objectiveMapings;
	
	@OneToMany(mappedBy = "unitDetails", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<UnitContent> unitContents;
    
	@OneToMany(mappedBy = "unitDetails", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<LectureSchedule> lectureSchedules;
	
	public UnitDetails() {
		super();
	}
	public UnitDetails(String unitTitle, String unitDescr, @NotNull CoursePlan coursePlan,String createdBy) {
		super();
		UnitTitle = unitTitle;
		UnitDescr = unitDescr;
		this.coursePlan = coursePlan;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}
	public UnitDetails(String unitTitle, String unitDescr, @NotNull CoursePlan coursePlan,String createdBy,long unitId,String objectives,String topics,String objectivesid,String topicsid) {
		super();
		UnitTitle = unitTitle;
		UnitDescr = unitDescr;
		this.coursePlan = coursePlan;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		this.UnitId = unitId;
		Topics = topics;
		Objectives = objectives;
		TopicsId = topicsid;
		ObjectivesId = objectivesid;
	}
	
	public List<LectureSchedule> getLectureSchedules() {
		return lectureSchedules;
	}
	public void setLectureSchedules(List<LectureSchedule> lectureSchedules) {
		this.lectureSchedules = lectureSchedules;
	}
	public List<UnitContent> getUnitContents() {
		return unitContents;
	}
	public void setUnitContents(List<UnitContent> unitContents) {
		this.unitContents = unitContents;
	}
	public String getTopicsId() {
		return TopicsId;
	}
	public void setTopicsId(String topicsId) {
		TopicsId = topicsId;
	}
	public String getObjectivesId() {
		return ObjectivesId;
	}
	public void setObjectivesId(String objectivesId) {
		ObjectivesId = objectivesId;
	}
	public String getTopics() {
		return Topics;
	}
	public void setTopics(String topics) {
		Topics = topics;
	}
	public String getObjectives() {
		return Objectives;
	}
	public void setObjectives(String objectives) {
		Objectives = objectives;
	}
	public List<CourseObjectiveMaping> getObjectiveMapings() {
		return objectiveMapings;
	}
	public void setObjectiveMapings(List<CourseObjectiveMaping> objectiveMapings) {
		this.objectiveMapings = objectiveMapings;
	}
	public long getUnitId() {
		return UnitId;
	}
	public void setUnitId(long unitId) {
		UnitId = unitId;
	}
	public String getUnitTitle() {
		return UnitTitle;
	}
	public void setUnitTitle(String unitTitle) {
		UnitTitle = unitTitle;
	}
	public String getUnitDescr() {
		return UnitDescr;
	}
	public void setUnitDescr(String unitDescr) {
		UnitDescr = unitDescr;
	}
	public CoursePlan getCoursePlan() {
		return coursePlan;
	}
	public void setCoursePlan(CoursePlan coursePlan) {
		this.coursePlan = coursePlan;
	}
	public List<SubUnitDetails> getSubUnitDetails() {
		return subUnitDetails;
	}
	public void setSubUnitDetails(List<SubUnitDetails> subUnitDetails) {
		this.subUnitDetails = subUnitDetails;
	}    
}