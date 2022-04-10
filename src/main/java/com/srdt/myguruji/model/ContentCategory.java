package com.srdt.myguruji.model;

import java.util.List;

public class ContentCategory {
	
	private String contenttype;
	private List<ContentDescr> content;
	private long contentcount;
	public ContentCategory() {
		super();
	}
	public ContentCategory(String contenttype, List<ContentDescr> content) {
		super();
		this.contenttype = contenttype;
		this.content = content;
		this.contentcount = content.size();
	}

	public ContentCategory(String contenttype, long contentcount) {
		super();
		this.contenttype = contenttype;
		this.contentcount = contentcount;
	}
	public long getContentcount() {
		return contentcount;
	}
	public void setContentcount(long contentcount) {
		this.contentcount = contentcount;
	}
	public String getContenttype() {
		return contenttype;
	}
	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}
	public List<ContentDescr> getContent() {
		return content;
	}
	public void setContent(List<ContentDescr> content) {
		this.content = content;
	}
}
