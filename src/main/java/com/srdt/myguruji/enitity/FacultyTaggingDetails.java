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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
	@NamedQuery(name="FacultyTaggingDetails.getFacultyIds",query="select facultyDetails.FacultyId from FacultyTaggingDetails where  batchDetails.BatchId=:batchid")
})

@Table(name="FacultyTaggingDetails",uniqueConstraints={@UniqueConstraint(columnNames={"FacultyId","BatchId"})})

public class FacultyTaggingDetails extends SharedField implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(initialValue=1,allocationSize=1,name="FacultyTaggingDetails_Sqr",sequenceName="FacultyTaggingDetails_Sqr")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator ="FacultyTaggingDetails_Sqr")
	@Column
	private long TagId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FacultyId",referencedColumnName="FacultyId",insertable=true,updatable=true,nullable=false)
	@NotNull
	private FacultyDetails facultyDetails;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BatchId",referencedColumnName="BatchId",insertable=true,updatable=true,nullable=false)
	@NotNull
	private BatchDetails batchDetails;

	public FacultyTaggingDetails() {
		super();
	}

	public FacultyTaggingDetails(FacultyDetails facultyDetails, BatchDetails batchDetails,String createdBy) {
		super();
		this.facultyDetails = facultyDetails;
		this.batchDetails = batchDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}

	public long getTagId() {
		return TagId;
	}

	public void setTagId(long tagId) {
		TagId = tagId;
	}

	public FacultyDetails getFacultyDetails() {
		return facultyDetails;
	}

	public void setFacultyDetails(FacultyDetails facultyDetails) {
		this.facultyDetails = facultyDetails;
	}

	public BatchDetails getBatchDetails() {
		return batchDetails;
	}

	public void setBatchDetails(BatchDetails batchDetails) {
		this.batchDetails = batchDetails;
	}
	
}
