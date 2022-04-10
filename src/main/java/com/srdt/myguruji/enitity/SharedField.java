package com.srdt.myguruji.enitity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.srdt.myguruji.utility.Generation;


@MappedSuperclass
public class SharedField {

	@Column(length = 50)
	@NotNull
	private String CreatedBy;
	@Column(length = 50)
	@NotNull
	private String ModifiedBy;
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date CreatedDate = Generation.getCurrentDate();
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date LastUpdatedDate = Generation.getCurrentDate();
	@Column(length = 1)
	@NotNull
	private String IsActive = "Y";
	
	@Column(length=500,nullable=true)
	private String Descr1;
	@Column(length=500,nullable=true)
	private String Descr2;
	@Column(length=500,nullable=true)
	private String Descr3;
	@Column(length=500,nullable=true)
	private String Descr4;
	@Column(length=500,nullable=true)
	private String Descr5;	
	
	public SharedField() {
		
	}
	public SharedField(@NotNull String createdBy) {
		
		CreatedBy = createdBy;
		ModifiedBy = createdBy;
	}	
	
	public String getDescr1() {
		return Descr1;
	}
	public void setDescr1(String descr1) {
		Descr1 = descr1;
	}
	public String getDescr2() {
		return Descr2;
	}
	public void setDescr2(String descr2) {
		Descr2 = descr2;
	}
	public String getDescr3() {
		return Descr3;
	}
	public void setDescr3(String descr3) {
		Descr3 = descr3;
	}
	public String getDescr4() {
		return Descr4;
	}
	public void setDescr4(String descr4) {
		Descr4 = descr4;
	}
	public String getDescr5() {
		return Descr5;
	}
	public void setDescr5(String descr5) {
		Descr5 = descr5;
	}
	public String getCreatedBy() {
		return CreatedBy;
	}
	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}
	public String getModifiedBy() {
		return ModifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}
	public Date getCreatedDate() {
		return CreatedDate;
	}
	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}
	public Date getLastUpdatedDate() {
		return LastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		LastUpdatedDate = lastUpdatedDate;
	}
	public String getIsActive() {
		return IsActive;
	}
	public void setIsActive(String isActive) {
		IsActive = isActive;
	}	
}
