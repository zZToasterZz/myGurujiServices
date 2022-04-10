package com.srdt.myguruji.enitity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="Department")
public class Department extends SharedField implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(allocationSize=1,initialValue=1,name="Department_Sqr",sequenceName="Department_Sqr")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="Department_Sqr")
	@Column
	private long DepartId;
	@Column(length=30,unique=true,nullable=false)
	@NotBlank
	private String DepartCode;
	@Column(length=150,nullable=false)
	@NotBlank
	private String DepartName;
	@Column(length=500,nullable=true)
	private  String Descr;	
	
	public Department() {
		super();
	}

	public Department(String departCode, String departName, String descr,String createdBy) {
		super();
		DepartCode = departCode;
		DepartName = departName;
		Descr = descr;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}

	public long getDeparttId() {
		return DepartId;
	}

	public void setDeparttId(long departtId) {
		DepartId = departtId;
	}

	public String getDepartCode() {
		return DepartCode;
	}

	public void setDepartCode(String departCode) {
		DepartCode = departCode;
	}

	public String getDepartName() {
		return DepartName;
	}

	public void setDepartName(String departName) {
		DepartName = departName;
	}

	public String getDescr() {
		return Descr;
	}

	public void setDescr(String descr) {
		Descr = descr;
	}
}
