package com.srdt.myguruji.model;

import java.util.ArrayList;
import java.util.List;

public class SearchField {
	
	private long courseid,id;
	private List<Long> topicids = new ArrayList<>();
	private String code,title,emplid;	
	
	public SearchField() {
		super();
	}
	
	public String getEmplid() {
		return emplid != null ? emplid : "";
	}

	public void setEmplid(String emplid) {
		this.emplid = emplid;
	}

	public String getCode() {
		return code != null ? code : "";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title != null ? title : "";
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getCourseid() {
		return courseid;
	}
	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}
	public List<Long> getTopicids() {
		return topicids;
	}
	public void setTopicids(List<Long> topicids) {
		this.topicids = topicids;
	}
}
