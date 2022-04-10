package com.srdt.myguruji.model;

public class ContentDescr {

	private long contentid;
	private String title;
	private String descr;
	private String contentpath;
	public ContentDescr() {
		super();
	}
	public ContentDescr(long contentid, String title, String descr, String contentpath) {
		super();
		this.contentid = contentid;
		this.title = title;
		this.descr = descr;
		this.contentpath = contentpath;
	}
	public long getContentid() {
		return contentid;
	}
	public void setContentid(long contentid) {
		this.contentid = contentid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getContentpath() {
		return contentpath;
	}
	public void setContentpath(String contentpath) {
		this.contentpath = contentpath;
	}	
}
