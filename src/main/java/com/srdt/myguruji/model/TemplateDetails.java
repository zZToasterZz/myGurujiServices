package com.srdt.myguruji.model;

import java.util.List;

public class TemplateDetails {
	
	private long templateid;
	private String templatecode;
	private String title;
	private String descr;
	private int noofsection;
	private long courseid;
	private String createdby;
	private int maxmarks;
	private List<SectionDetails> sectiondetails;
	public TemplateDetails() {
		super();
	}
	
	public TemplateDetails(long templateid, String templatecode, String title, String descr, int noofsection,
			long courseid, List<SectionDetails> sectiondetails,String createdby,int maxmarks) {
		super();
		this.templateid = templateid;
		this.templatecode = templatecode;
		this.title = title;
		this.descr = descr;
		this.noofsection = noofsection;
		this.courseid = courseid;
		this.sectiondetails = sectiondetails;
		this.createdby = createdby;
		this.maxmarks = maxmarks;
	}

	public int getMaxmarks() {
		return maxmarks;
	}

	public void setMaxmarks(int maxmarks) {
		this.maxmarks = maxmarks;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public long getTemplateid() {
		return templateid;
	}

	public void setTemplateid(long templateid) {
		this.templateid = templateid;
	}

	public String getTemplatecode() {
		return templatecode;
	}

	public void setTemplatecode(String templatecode) {
		this.templatecode = templatecode;
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

	public int getNoofsection() {
		return noofsection;
	}

	public void setNoofsection(int noofsection) {
		this.noofsection = noofsection;
	}

	public long getCourseid() {
		return courseid;
	}

	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}

	public List<SectionDetails> getSectiondetails() {
		return sectiondetails;
	}

	public void setSectiondetails(List<SectionDetails> sectiondetails) {
		this.sectiondetails = sectiondetails;
	}    
}
