package com.srdt.myguruji.model;

public class ObjectiveMapping {
	
	private long objectivemapid;
	private long objectiveid;
	private String descr;
	private String code;
	public ObjectiveMapping() {
		super();
	}
	public ObjectiveMapping(long objectivemapid, long objectiveid, String descr, String code) {
		super();
		this.objectivemapid = objectivemapid;
		this.objectiveid = objectiveid;
		this.descr = descr;
		this.code = code;
	}
	public long getObjectivemapid() {
		return objectivemapid;
	}
	public void setObjectivemapid(long objectivemapid) {
		this.objectivemapid = objectivemapid;
	}
	public long getObjectiveid() {
		return objectiveid;
	}
	public void setObjectiveid(long objectiveid) {
		this.objectiveid = objectiveid;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
