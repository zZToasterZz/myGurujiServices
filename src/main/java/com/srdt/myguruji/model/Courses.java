package com.srdt.myguruji.model;

public class Courses
{
	private String coursecode, coursetitle, coursedescr, createdby,plancode;
	private long courseid,planid;
    
	public Courses() {
		super();
	}
	public Courses(String courseCode, String courseTitle, String courseDescr,long courseid, String createdBy,String plancode,long planid) {
		super();
		this.coursecode = courseCode;
		this.coursetitle = courseTitle;
		this.coursedescr = courseDescr;
		this.createdby = createdBy;
		this.courseid = courseid;
		this.planid = planid;
		this.plancode = plancode;
	}
	public Courses(String courseCode, String courseTitle, String courseDescr,long courseid, String createdBy) {
		super();
		this.coursecode = courseCode;
		this.coursetitle = courseTitle;
		this.coursedescr = courseDescr;
		this.createdby = createdBy;
		this.courseid = courseid;
	}
	public String getPlancode() {
		return plancode;
	}

	public void setPlancode(String plancode) {
		this.plancode = plancode;
	}

	public long getPlanid() {
		return planid;
	}

	public void setPlanid(long planid) {
		this.planid = planid;
	}

	public long getCourseid() {
		return courseid;
	}
    
	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}

	public String getCoursecode() {
		return coursecode;
	}

	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}

	public String getCoursetitle() {
		return coursetitle;
	}

	public void setCoursetitle(String coursetitle) {
		this.coursetitle = coursetitle;
	}

	public String getCoursedescr() {
		return coursedescr;
	}

	public void setCoursedescr(String coursedescr) {
		this.coursedescr = coursedescr;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}	
}
