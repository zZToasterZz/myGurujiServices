package com.srdt.myguruji.model;

public class CourseObjective {
	
	private long objectiveid;
	private long courseid;
	private String descr;
	private String code;
	private String createdby;
	
	public CourseObjective() {
		super();
	}
	public CourseObjective(long objectiveid, long courseid, String descr) {
		super();
		this.objectiveid = objectiveid;
		this.courseid = courseid;
		this.descr = descr;
	}
	public CourseObjective(long courseid, String descr,String code) {
		super();
		this.courseid = courseid;
		this.descr = descr;
		this.code = code;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getObjectiveid() {
		return objectiveid;
	}
	public void setObjectiveid(long objectiveid) {
		this.objectiveid = objectiveid;
	}
	public long getCourseid() {
		return courseid;
	}
	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
}
