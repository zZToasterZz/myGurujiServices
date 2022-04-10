package com.srdt.myguruji.enitity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "ContentType")
public class ContentType extends SharedField implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "ContentType_Sqr", sequenceName = "ContentType_Sqr",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "ContentType_Sqr",strategy = GenerationType.SEQUENCE)
	private long TypeId;
	@Column(length = 100,unique = true,nullable = false)
	@NotEmpty
	private String Title;
	@Column(length = 1000,nullable = false)
	private String Descr;
	
	@OneToMany(mappedBy = "contentType", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<CourseContent> courseContents;
	
	@OneToMany(mappedBy = "contentType", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<UnitContent> unitContents;
	
	public ContentType() {
		super();
	}
	public ContentType(@NotEmpty String title, String descr,String createdBy) {
		super();
		Title = title;
		Descr = descr;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}
	public ContentType(long typeId, @NotEmpty String title, String descr,String createdBy) {
		super();
		TypeId = typeId;
		Title = title;
		Descr = descr;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}
	public List<UnitContent> getUnitContents() {
		return unitContents;
	}
	public void setUnitContents(List<UnitContent> unitContents) {
		this.unitContents = unitContents;
	}
	public List<CourseContent> getCourseContents() {
		return courseContents;
	}
	public void setCourseContents(List<CourseContent> courseContents) {
		this.courseContents = courseContents;
	}
	public long getTypeId() {
		return TypeId;
	}
	public void setTypeId(long typeId) {
		TypeId = typeId;
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
}
