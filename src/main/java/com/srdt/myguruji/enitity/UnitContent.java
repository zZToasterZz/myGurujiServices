package com.srdt.myguruji.enitity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "UnitContent")
@NamedQueries({
	@NamedQuery(name="UnitContent.updateUnitContent" , query="update UnitContent set Descr=:dscr,Title=:title where UnitContentId=:unitId"),
	@NamedQuery(name="UnitContent.getDocUploadCount", query="select count(uc.Title) from UnitContent uc where uc.courseDetails.CourseId=:crsid and uc.unitDetails.UnitId=:unitid and uc.unitDetails.coursePlan.CoursePlanId=:crsplanid and uc.contentType.TypeId=1 and uc.CreatedDate='2021-01-13 00:00:00'"),
	@NamedQuery(name="UnitContent.getVideoUploadCount", query="select count(uc.Title) from UnitContent uc where uc.courseDetails.CourseId=:crsid and uc.unitDetails.UnitId=:unitid and uc.unitDetails.coursePlan.CoursePlanId=:crsplanid and uc.contentType.TypeId=2 and uc.CreatedDate='2021-01-13 00:00:00'")
})

public class UnitContent extends SharedField implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "UnitContent_Sqr", sequenceName = "UnitContent_Sqr", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "UnitContent_Sqr", strategy = GenerationType.SEQUENCE)
	private long UnitContentId;
	@Column(length = 255, nullable = false)
	@NotEmpty
	private String Title;
	@Lob
	@Column(length=16777000)
	private String Descr;
	@Column(length = 255)
	private String ContentPath;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CourseId",referencedColumnName = "CourseId",insertable = true,updatable = true )
	@NotNull
	private CourseDetails courseDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UnitId", referencedColumnName = "UnitId" ,insertable = true ,updatable = true)
	@NotNull
	private UnitDetails unitDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TypeId", referencedColumnName = "TypeId", insertable = true, updatable = true)
	@NotNull
	private ContentType contentType;
	
	public UnitContent() {
		super();
	}
	
	public UnitContent(long unitContentId, @NotEmpty String title, String descr, String contentPath,
			@NotNull CourseDetails courseDetails, @NotNull UnitDetails unitDetails,String createdBy,ContentType contentType) {
		super();
		UnitContentId = unitContentId;
		Title = title;
		Descr = descr;
		ContentPath = contentPath;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		this.courseDetails = courseDetails;
		this.unitDetails = unitDetails;
		this.contentType = contentType;
	}
	
	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public CourseDetails getCourseDetails() {
		return courseDetails;
	}
	public void setCourseDetails(CourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}
	public UnitDetails getUnitDetails() {
		return unitDetails;
	}
	public void setUnitDetails(UnitDetails unitDetails) {
		this.unitDetails = unitDetails;
	}
	public long getUnitContentId() {
		return UnitContentId;
	}
	public void setUnitContentId(long unitContentId) {
		UnitContentId = unitContentId;
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
	public String getContentPath() {
		return ContentPath;
	}
	public void setContentPath(String contentPath) {
		ContentPath = contentPath;
	}
}
