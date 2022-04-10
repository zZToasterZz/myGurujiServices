package com.srdt.myguruji.model;

public class Search {

	private String courseid;
	private String topicid;
	private String difficultyid;
	private String typeid;
	public Search() {
		super();
	}
	public String getCourseid() {
		return courseid != null ? courseid : "";
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	public String getTopicid() {
		return topicid != null ? topicid : "";
	}
	public void setTopicid(String topicid) {
		this.topicid = topicid;
	}
	public String getDifficultyid() {
		return difficultyid != null ? difficultyid : "";
	}
	public void setDifficultyid(String difficultyid) {
		this.difficultyid = difficultyid;
	}
	public String getTypeid() {
		return typeid != null ? typeid : "";
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}	
}
