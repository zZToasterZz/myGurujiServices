package com.srdt.myguruji.model;

import java.util.List;

public class Unit {
	private String unittitle,unitdescr,createdby;
	private long unitid,courseplanid;
	private String objectives;
	private String topics;
	private String topicsid;
	private String objectivesid;
	private List<SubUnit> subUnits;
	private int contentcount;
	public Unit() {
		super();
	}
	public Unit(String unittitle, String unitdescr, String createdby, long courseplanid) {
		super();
		this.unittitle = unittitle;
		this.unitdescr = unitdescr;
		this.createdby = createdby;
		this.courseplanid = courseplanid;
	}
	public Unit(String unittitle, String unitdescr, String createdby, long unitid, long courseplanid) {
		super();
		this.unittitle = unittitle;
		this.unitdescr = unitdescr;
		this.createdby = createdby;
		this.unitid = unitid;
		this.courseplanid = courseplanid;
	}
	public Unit(String unittitle, String unitdescr, String createdby, long unitid, long courseplanid,List<SubUnit> subUnits) {
		super();
		this.unittitle = unittitle;
		this.unitdescr = unitdescr;
		this.createdby = createdby;
		this.unitid = unitid;
		this.courseplanid = courseplanid;
		this.subUnits = subUnits;
	}
	public Unit(String unittitle, String unitdescr, String createdby, long unitid, long courseplanid,List<SubUnit> subUnits,String objectives,String topics,String objectivesid,String topicsid) {
		super();
		this.unittitle = unittitle;
		this.unitdescr = unitdescr;
		this.createdby = createdby;
		this.unitid = unitid;
		this.courseplanid = courseplanid;
		this.subUnits = subUnits;
		this.objectives = objectives;
		this.topics = topics;
		this.objectivesid = objectivesid;
		this.topicsid = topicsid;
	}
	public Unit(String unittitle, String unitdescr, String createdby, long unitid, long courseplanid,List<SubUnit> subUnits,String objectives,String topics,String objectivesid,String topicsid,int contentcount) {
		super();
		this.unittitle = unittitle;
		this.unitdescr = unitdescr;
		this.createdby = createdby;
		this.unitid = unitid;
		this.courseplanid = courseplanid;
		this.subUnits = subUnits;
		this.objectives = objectives;
		this.topics = topics;
		this.objectivesid = objectivesid;
		this.topicsid = topicsid;
		this.contentcount = contentcount;
	}
	public int getContentcount() {
		return contentcount;
	}
	public void setContentcount(int contentcount) {
		this.contentcount = contentcount;
	}
	public String getTopicsid() {
		return topicsid;
	}
	public void setTopicsid(String topicsid) {
		this.topicsid = topicsid;
	}
	public String getObjectivesid() {
		return objectivesid;
	}
	public void setObjectivesid(String objectivesid) {
		this.objectivesid = objectivesid;
	}
	public String getObjectives() {
		return objectives;
	}
	public void setObjectives(String objectives) {
		this.objectives = objectives;
	}
	public String getTopics() {
		return topics;
	}
	public void setTopics(String topics) {
		this.topics = topics;
	}
	public List<SubUnit> getSubUnits() {
		return subUnits;
	}
	public void setSubUnits(List<SubUnit> subUnits) {
		this.subUnits = subUnits;
	}
	public String getUnittitle() {
		return unittitle;
	}
	public void setUnittitle(String unittitle) {
		this.unittitle = unittitle;
	}
	public String getUnitdescr() {
		return unitdescr;
	}
	public void setUnitdescr(String unitdescr) {
		this.unitdescr = unitdescr;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public long getUnitid() {
		return unitid;
	}
	public void setUnitid(long unitid) {
		this.unitid = unitid;
	}
	public long getCourseplanid() {
		return courseplanid;
	}
	public void setCourseplanid(long courseplanid) {
		this.courseplanid = courseplanid;
	}	
}
