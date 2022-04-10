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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "SyllabusPlan")
public class SyllabusPlan implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(allocationSize = 1,initialValue = 1,name = "SyllabusPlan_Sqr",sequenceName = "SyllabusPlan_Sqr")
	@GeneratedValue(generator = "SyllabusPlan_Sqr",strategy = GenerationType.SEQUENCE)
	private long SysGenId;
	@Column(length = 50)
	private String SysUnitId;
	@Column(length = 250)
	@NotBlank
	private String Title;
	@Column(length = 500)
	private String Descr;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CourseId", referencedColumnName = "CourseId", insertable = true, updatable = true)
	private CourseDetails courseDetails;

	public SyllabusPlan() {
		super();
	}

	public SyllabusPlan(long sysGenId, String sysUnitId, @NotBlank String title, String descr,
			CourseDetails courseDetails) {
		super();
		SysGenId = sysGenId;
		SysUnitId = sysUnitId;
		Title = title;
		Descr = descr;
		this.courseDetails = courseDetails;
	}



	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescr() {
		return Descr;
	}

	public void setDescr(String descr) {
		Descr = descr;
	}

	public long getSysGenId() {
		return SysGenId;
	}

	public void setSysGenId(long sysGenId) {
		SysGenId = sysGenId;
	}

	public String getSysUnitId() {
		return SysUnitId;
	}

	public void setSysUnitId(String sysUnitId) {
		SysUnitId = sysUnitId;
	}

	public CourseDetails getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(CourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}

}
