package com.srdt.myguruji.model;

import java.util.List;

public class SectionDetails {
	
	private long sectionid;
	private String title;
	private int sectionmarks;
	private String sectionnote;
	private int totalquestion;
	private int attemptquestion;
	private String descr;
	private List<AddedQuestion> questions;
	public SectionDetails() {
		super();
	}
	public SectionDetails(long sectionid, String title, int sectionmarks, String sectionnote, int totalquestion,
			int attemptquestion, String descr) {
		super();
		this.sectionid = sectionid;
		this.title = title;
		this.sectionmarks = sectionmarks;
		this.sectionnote = sectionnote;
		this.totalquestion = totalquestion;
		this.attemptquestion = attemptquestion;
		this.descr = descr;
	}
	public SectionDetails(long sectionid, String title, int sectionmarks, String sectionnote, int totalquestion,
			int attemptquestion, String descr,List<AddedQuestion> questions) {
		super();
		this.sectionid = sectionid;
		this.title = title;
		this.sectionmarks = sectionmarks;
		this.sectionnote = sectionnote;
		this.totalquestion = totalquestion;
		this.attemptquestion = attemptquestion;
		this.descr = descr;
		this.questions = questions;
	}
	public List<AddedQuestion> getQuestions() {
		return questions;
	}
	public void setQuestions(List<AddedQuestion> questions) {
		this.questions = questions;
	}
	public long getSectionid() {
		return sectionid;
	}
	public void setSectionid(long sectionid) {
		this.sectionid = sectionid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getSectionmarks() {
		return sectionmarks;
	}
	public void setSectionmarks(int sectionmarks) {
		this.sectionmarks = sectionmarks;
	}
	public String getSectionnote() {
		return sectionnote;
	}
	public void setSectionnote(String sectionnote) {
		this.sectionnote = sectionnote;
	}
	public int getTotalquestion() {
		return totalquestion;
	}
	public void setTotalquestion(int totalquestion) {
		this.totalquestion = totalquestion;
	}
	public int getAttemptquestion() {
		return attemptquestion;
	}
	public void setAttemptquestion(int attemptquestion) {
		this.attemptquestion = attemptquestion;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
}
