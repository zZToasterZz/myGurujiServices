package com.srdt.myguruji.enitity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CourseObjectiveMaping")
public class CourseObjectiveMaping implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "CourseObjectiveMaping_Sqr", sequenceName = "CourseObjectiveMaping_Sqr")
	@GeneratedValue(generator = "CourseObjectiveMaping_Sqr", strategy = GenerationType.SEQUENCE)
	private long ObjectiveMapingId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ObjectiveId",referencedColumnName = "ObjectiveId",insertable = true,updatable = true)
	@NotNull
	private CourseObjectiveDetails objectiveDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UnitId",referencedColumnName = "UnitId",insertable = true,updatable = true)
	private UnitDetails unitDetails;
	
	public CourseObjectiveMaping() {
		super();
	}
	
	public CourseObjectiveMaping(@NotNull CourseObjectiveDetails objectiveDetails, UnitDetails unitDetails) {
		super();
		this.objectiveDetails = objectiveDetails;
		this.unitDetails = unitDetails;
	}

	public CourseObjectiveMaping(long objectiveMapingId, @NotNull CourseObjectiveDetails objectiveDetails,
			UnitDetails unitDetails) {
		super();
		ObjectiveMapingId = objectiveMapingId;
		this.objectiveDetails = objectiveDetails;
		this.unitDetails = unitDetails;
	}

	public long getObjectiveMapingId() {
		return ObjectiveMapingId;
	}
	public void setObjectiveMapingId(long objectiveMapingId) {
		ObjectiveMapingId = objectiveMapingId;
	}
	public CourseObjectiveDetails getObjectiveDetails() {
		return objectiveDetails;
	}
	public void setObjectiveDetails(CourseObjectiveDetails objectiveDetails) {
		this.objectiveDetails = objectiveDetails;
	}
	public UnitDetails getUnitDetails() {
		return unitDetails;
	}

	public void setUnitDetails(UnitDetails unitDetails) {
		this.unitDetails = unitDetails;
	}
}
