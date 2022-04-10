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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CourseContent")
public class CourseContent extends SharedField implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "CourseContent_Sqr", sequenceName = "CourseContent_Sqr", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "CourseContent_Sqr", strategy = GenerationType.SEQUENCE)
	private long CourseContentId;
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
	@JoinColumn(name = "TypeId", referencedColumnName = "TypeId", insertable = true, updatable = true)
	@NotNull
	private ContentType contentType;

	public CourseContent() {
		super();
	}

	public CourseContent(long courseContentId, @NotEmpty String title, String descr, String contentPath,
			@NotNull CourseDetails courseDetails,String createdBy,ContentType contentType) {
		super();
		CourseContentId = courseContentId;
		Title = title;
		Descr = descr;
		ContentPath = contentPath;
		this.courseDetails = courseDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		this.contentType = contentType;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public long getCourseContentId() {
		return CourseContentId;
	}

	public void setCourseContentId(long courseContentId) {
		CourseContentId = courseContentId;
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

	public CourseDetails getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(CourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}	
}
