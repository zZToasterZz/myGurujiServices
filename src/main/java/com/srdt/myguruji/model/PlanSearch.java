package com.srdt.myguruji.model;

public class PlanSearch {

	private long planid;	
	private String courseid;
	private String plancode;
	private String plantitle;
	private String emplid;
	private String coursecode;
	private String coursetitle;
	
	public PlanSearch() {
		super();
	}
	
	public PlanSearch(long courseid, long planid, String plancode, String plantitle, String coursecode,
			String coursetitle) {
		super();
		this.courseid = Long.toString(courseid) ;
		this.planid = planid;
		this.plancode = plancode;
		this.plantitle = plantitle;
		this.coursecode = coursecode;
		this.coursetitle = coursetitle;
	}
	
	public long getPlanid() {
		return planid;
	}

	public void setPlanid(long planid) {
		this.planid = planid;
	}

	public String getPlantitle() {
		return plantitle != null ? plantitle : "";
	}

	public void setPlantitle(String plantitle) {
		this.plantitle = plantitle;
	}

	public String getCoursetitle() {
		return coursetitle;
	}

	public void setCoursetitle(String coursetitle) {
		this.coursetitle = coursetitle;
	}

	public String getCourseid() {
		return courseid != null ? courseid : "";
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	public String getPlancode() {
		return plancode != null ? plancode : "";
	}
	public void setPlancode(String plancode) {
		this.plancode = plancode;
	}
	public String getEmplid() {
		return emplid != null ? emplid : "";
	}
	public void setEmplid(String emplid) {
		this.emplid = emplid;
	}
	public String getCoursecode() {
		return coursecode;
	}
	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}	
}
