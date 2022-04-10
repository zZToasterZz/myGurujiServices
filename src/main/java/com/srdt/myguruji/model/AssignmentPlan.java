package com.srdt.myguruji.model;

import java.util.List;

public class AssignmentPlan {

	private long assignmentplanid;
	private long courseid;
	private long batchid;
	private long assignmentid;
	private long courseplanid;
	List<AssignmentUnit> unitlist;
	
	
	public AssignmentPlan() {
		super();
	}


	public AssignmentPlan(long assignmentplanid, long courseid, long batchid, long assignmentid, long courseplanid) {
		super();
		this.assignmentplanid = assignmentplanid;
		this.courseid = courseid;
		this.batchid = batchid;
		this.assignmentid = assignmentid;
		this.courseplanid = courseplanid;
	}


	public long getAssignmentplanid() {
		return assignmentplanid;
	}


	public void setAssignmentplanid(long assignmentplanid) {
		this.assignmentplanid = assignmentplanid;
	}


	public long getCourseid() {
		return courseid;
	}


	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}


	public long getBatchid() {
		return batchid;
	}


	public void setBatchid(long batchid) {
		this.batchid = batchid;
	}


	public long getAssignmentid() {
		return assignmentid;
	}


	public void setAssignmentid(long assignmentid) {
		this.assignmentid = assignmentid;
	}


	public long getCourseplanid() {
		return courseplanid;
	}


	public void setCourseplanid(long courseplanid) {
		this.courseplanid = courseplanid;
	}


	public List<AssignmentUnit> getUnitlist() {
		return unitlist;
	}


	public void setUnitlist(List<AssignmentUnit> unitlist) {
		this.unitlist = unitlist;
	}

}
