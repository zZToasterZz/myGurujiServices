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
@Table(name="StudentEnrollment",uniqueConstraints={@UniqueConstraint(columnNames={"BatchId","StudentId"})})
@NamedQueries({
	@NamedQuery(name="StudentEnrollment.getStudentCount", query="select count(*) from StudentEnrollment where batchDetails.BatchId in (:batchId)"),
	@NamedQuery(name="StudentEnrollment.getStudentListByBatchId",query="select st from StudentEnrollment st where st.batchDetails.BatchId=: batchid")
})

public class StudentEnrollment extends SharedField implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(initialValue=1,allocationSize=1,name="StudentEnrollment_Sqr",sequenceName="StudentEnrollment_Sqr")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator ="StudentEnrollment_Sqr")
	@Column
	private long EnrollmentId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="StudentId",referencedColumnName="StudentId",insertable=true,updatable=true,nullable=false)
	@NotNull
	private StudentDetails student;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BatchId",referencedColumnName="BatchId",insertable=true,updatable=true,nullable=false)
	@NotNull
	private BatchDetails batchDetails;

	public StudentEnrollment() {
		super();
	}

	public StudentEnrollment(StudentDetails student, BatchDetails batchDetails,String createdBy) {
		super();
		this.student = student;
		this.batchDetails = batchDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}

	public long getEnrollmentId() {
		return EnrollmentId;
	}

	public void setEnrollmentId(long enrollmentId) {
		EnrollmentId = enrollmentId;
	}

	public StudentDetails getStudent() {
		return student;
	}

	public void setStudent(StudentDetails student) {
		this.student = student;
	}

	public BatchDetails getBatches() {
		return batchDetails;
	}

	public void setBatches(BatchDetails batchDetails) {
		this.batchDetails = batchDetails;
	}	
}
