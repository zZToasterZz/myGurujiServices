package com.srdt.myguruji.model;

import java.util.List;

public class Plan {
	
	private String courseplantitle,courseplandescr,createdby,courseplancode;
	private long courseid,courseplanid,batchid;
	private String leccount;
	private List<Books> books;
	private List<Unit> units;
	private int contentcount;
	private String batchtitle;
	private String coursetitle;
	private String batchcode;
	public Plan() {
		super();
	}
	public Plan(String courseplantitle, String courseplandescr, String createdby, long courseid, long courseplanid,List<Books> books) {
		super();
		this.courseplantitle = courseplantitle;
		this.courseplandescr = courseplandescr;
		this.createdby = createdby;
		this.courseid = courseid;
		this.courseplanid = courseplanid;
		this.books = books;
	}
	public Plan(String courseplantitle, String courseplandescr, String createdby, long courseid, long courseplanid,List<Books> books,List<Unit> units,String plancode) {
		super();
		this.courseplantitle = courseplantitle;
		this.courseplandescr = courseplandescr;
		this.createdby = createdby;
		this.courseid = courseid;
		this.courseplanid = courseplanid;
		this.books = books;
		this.units = units;
		this.courseplancode = plancode;
	}
	public Plan(String courseplantitle, String courseplandescr, String createdby, long courseid, long courseplanid,List<Books> books,List<Unit> units,String plancode,String leccount,long batchid) {
		super();
		this.courseplantitle = courseplantitle;
		this.courseplandescr = courseplandescr;
		this.createdby = createdby;
		this.courseid = courseid;
		this.courseplanid = courseplanid;
		this.books = books;
		this.units = units;
		this.courseplancode = plancode;
		this.leccount=leccount;
		this.batchid = batchid;
	}
	public Plan(String courseplantitle, String courseplandescr, String createdby, long courseid, long courseplanid,List<Books> books,List<Unit> units,String plancode,String leccount,long batchid,int contentcount,String batchtitle,String coursetitle,String batchcode) {
		super();
		this.courseplantitle = courseplantitle;
		this.courseplandescr = courseplandescr;
		this.createdby = createdby;
		this.courseid = courseid;
		this.courseplanid = courseplanid;
		this.books = books;
		this.units = units;
		this.courseplancode = plancode;
		this.leccount=leccount;
		this.batchid = batchid;
		this.contentcount = contentcount;
		this.batchtitle = batchtitle;
		this.coursetitle = coursetitle;
		this.batchcode = batchcode;
	}	
	
	public String getBatchcode() {
		return batchcode;
	}
	public void setBatchcode(String batchcode) {
		this.batchcode = batchcode;
	}
	public String getBatchtitle() {
		return batchtitle;
	}
	public void setBatchtitle(String batchtitle) {
		this.batchtitle = batchtitle;
	}
	public String getCoursetitle() {
		return coursetitle;
	}
	public void setCoursetitle(String coursetitle) {
		this.coursetitle = coursetitle;
	}
	public int getContentcount() {
		return contentcount;
	}
	public void setContentcount(int contentcount) {
		this.contentcount = contentcount;
	}
	public long getBatchid() {
		return batchid;
	}
	public void setBatchid(long batchid) {
		this.batchid = batchid;
	}
	public String getCourseplancode() {
		return courseplancode;
	}
	public void setCourseplancode(String courseplancode) {
		this.courseplancode = courseplancode;
	}
	public String getLeccount() {
		return leccount;
	}
	public void setLeccount(String leccount) {
		this.leccount = leccount;
	}
	public String getPlancode() {
		return courseplancode;
	}
	public void setPlancode(String plancode) {
		this.courseplancode = plancode;
	}
	public List<Unit> getUnits() {
		return units;
	}
	public void setUnits(List<Unit> units) {
		this.units = units;
	}
	public List<Books> getBooks() {
		return books;
	}
	public void setBooks(List<Books> books) {
		this.books = books;
	}
	public String getCourseplantitle() {
		return courseplantitle;
	}
	public void setCourseplantitle(String courseplantitle) {
		this.courseplantitle = courseplantitle;
	}
	public String getCourseplandescr() {
		return courseplandescr;
	}
	public void setCourseplandescr(String courseplandescr) {
		this.courseplandescr = courseplandescr;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public long getCourseid() {
		return courseid;
	}
	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}
	public long getCourseplanid() {
		return courseplanid;
	}
	public void setCourseplanid(long courseplanid) {
		this.courseplanid = courseplanid;
	}    
}
