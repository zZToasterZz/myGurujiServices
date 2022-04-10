package com.srdt.myguruji.model;

import java.util.List;

public class SubUnit {
	
	private String subunittitle,subunitdescr,createdby;
	private long subunitid,unitid,topicid;
	private List<ObjectiveMapping> objectivemap;
	public SubUnit() {
		super();
	}
	public SubUnit(String subunittitle, String subunitdescr, String createdby, long unitid) {
		super();
		this.subunittitle = subunittitle;
		this.subunitdescr = subunitdescr;
		this.createdby = createdby;
		this.unitid = unitid;
	}
	public SubUnit(String subunittitle, String subunitdescr, String createdby, long subunitid, long unitid) {
		super();
		this.subunittitle = subunittitle;
		this.subunitdescr = subunitdescr;
		this.createdby = createdby;
		this.subunitid = subunitid;
		this.unitid = unitid;
	}
	public SubUnit(String subunittitle, String subunitdescr, String createdby, long subunitid, long unitid,long topicid) {
		super();
		this.subunittitle = subunittitle;
		this.subunitdescr = subunitdescr;
		this.createdby = createdby;
		this.subunitid = subunitid;
		this.unitid = unitid;
		this.topicid = topicid;
	}
	public SubUnit(String subunittitle, String subunitdescr, String createdby, long subunitid, long unitid,long topicid,List<ObjectiveMapping> objectivemap) {
		super();
		this.subunittitle = subunittitle;
		this.subunitdescr = subunitdescr;
		this.createdby = createdby;
		this.subunitid = subunitid;
		this.unitid = unitid;
		this.topicid = topicid;
		this.objectivemap = objectivemap;
	}
	public List<ObjectiveMapping> getObjectivemap() {
		return objectivemap;
	}
	public void setObjectivemap(List<ObjectiveMapping> objectivemap) {
		this.objectivemap = objectivemap;
	}
	public long getTopicid() {
		return topicid;
	}
	public void setTopicid(long topicid) {
		this.topicid = topicid;
	}
	public String getSubunittitle() {
		return subunittitle;
	}
	public void setSubunittitle(String subunittitle) {
		this.subunittitle = subunittitle;
	}
	public String getSubunitdescr() {
		return subunitdescr;
	}
	public void setSubunitdescr(String subunitdescr) {
		this.subunitdescr = subunitdescr;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public long getSubunitid() {
		return subunitid;
	}
	public void setSubunitid(long subunitid) {
		this.subunitid = subunitid;
	}
	public long getUnitid() {
		return unitid;
	}
	public void setUnitid(long unitid) {
		this.unitid = unitid;
	}	
}
