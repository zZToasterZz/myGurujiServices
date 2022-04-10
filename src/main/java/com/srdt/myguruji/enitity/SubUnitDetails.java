package com.srdt.myguruji.enitity;

import java.io.Serializable;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="SubUnitDetails")
@NamedQueries({
	@NamedQuery(name="SubUnitDetails.findUnitDetailsById",query="select u from UnitDetails u where u.UnitId=:UnitId"),
	@NamedQuery(name="SubUnitDetails.findAllSubUnitDetails",query="select s from SubUnitDetails s join fetch s.unitDetails"),
	@NamedQuery(name="SubUnitDetails.deleteAllBySubUnitId",query="delete from SubUnitDetails where SubUnitId in (:SubUnitId)"),
	@NamedQuery(name="SubUnitDetails.findAllSubUnitDetailsByUnitId",query="select s from SubUnitDetails s join fetch s.unitDetails t where t.UnitId=:UnitId"),
	@NamedQuery(name="SubUnitDetails.deleteSubUnitsByUnitId",query="delete from SubUnitDetails where SubUnitId not in(:SubUnitId) and unitDetails.UnitId=:UnitId"),
	@NamedQuery(name="SubUnitDetails.findSubUnitByUnitId",query="select s from SubUnitDetails s inner join fetch s.unitDetails p left join fetch p.objectiveMapings where p.UnitId=:UnitId")
})
public class SubUnitDetails extends SharedField implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(initialValue=1,allocationSize=1,name="SubUnitDetails_Sqr",sequenceName="SubUnitDetails_Sqr")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SubUnitDetails_Sqr")
	@Column
    private long SubUnitId;
	@Column(length=150,nullable=false) 
    private String SubUnitTitle;
	@Column(length=500,nullable=false)
    private String SubUnitDescr;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="UnitId",referencedColumnName="UnitId",insertable = true,updatable = true,nullable = false)
	@NotNull
	private UnitDetails unitDetails = new UnitDetails();
	
	/*
	 * @OneToOne(fetch=FetchType.LAZY)
	 * 
	 * @JoinColumn(name="TopicId",referencedColumnName="TopicId",insertable=true,
	 * updatable=true,nullable = true) private TopicDetails topicDetails = new
	 * TopicDetails();
	 */

	@Column(nullable = true)
	private long TopicId;
	
	
	public SubUnitDetails() {
		super();
	}
	
	public SubUnitDetails(String subUnitTitle, String subUnitDescr, @NotNull UnitDetails unitDetails,String createdBy) {
		super();
		SubUnitTitle = subUnitTitle;
		SubUnitDescr = subUnitDescr;
		this.unitDetails = unitDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}

	public SubUnitDetails(String subUnitTitle, String subUnitDescr, @NotNull UnitDetails unitDetails,String createdBy,long subUnitId) {
		super();
		SubUnitTitle = subUnitTitle;
		SubUnitDescr = subUnitDescr;
		this.unitDetails = unitDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		SubUnitId = subUnitId;
	}
	
	public SubUnitDetails(String subUnitTitle, String subUnitDescr, @NotNull UnitDetails unitDetails,String createdBy,long subUnitId,long topicId) {
		super();
		SubUnitTitle = subUnitTitle;
		SubUnitDescr = subUnitDescr;
		this.unitDetails = unitDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		SubUnitId = subUnitId;
		this.TopicId=topicId;
	}
	
	/*
	 * public TopicDetails getTopicDetails() { return topicDetails; }
	 * 
	 * public void setTopicDetails(TopicDetails topicDetails) { this.topicDetails =
	 * topicDetails; }
	 */

	public long getSubUnitId() {
		return SubUnitId;
	}

	public long getTopicId() {
		return TopicId;
	}

	public void setTopicId(long topicId) {
		TopicId = topicId;
	}

	public void setSubUnitId(long subUnitId) {
		SubUnitId = subUnitId;
	}

	public String getSubUnitTitle() {
		return SubUnitTitle;
	}

	public void setSubUnitTitle(String subUnitTitle) {
		SubUnitTitle = subUnitTitle;
	}

	public String getSubUnitDescr() {
		return SubUnitDescr;
	}

	public void setSubUnitDescr(String subUnitDescr) {
		SubUnitDescr = subUnitDescr;
	}

	public UnitDetails getUnitDetails() {
		return unitDetails;
	}

	public void setUnitDetails(UnitDetails unitDetails) {
		this.unitDetails = unitDetails;
	}
}