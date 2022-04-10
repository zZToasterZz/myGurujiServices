package com.srdt.myguruji.model;

import java.util.List;

public class Assessment {

	private long assessmentid;
	private long courseid;
	private long templateid;
	private String title;
	private String code;
	private String Descr1;
	private String assessmendescr;
	private String createdby;
	private String coursedescr;
	private String templatedescr;
	private String type;
	private List<SectionDetails> sectiondetails;
	private String schedulestatus;
	private String evaluationstrategy;
	private String coursecode;
	private String coursetitle;
	private String createdon;

	public Assessment() {
		super();
	}
	public Assessment(long assessmentid,String title, String code, String assessmendescr) {
		super();
		this.assessmentid = assessmentid;
		this.title = title;
		this.code = code;
		this.assessmendescr = assessmendescr;
	}
	public Assessment(long assessmentid,String title, String code, String assessmendescr, String Descr1) {
		super();
		this.assessmentid = assessmentid;
		this.title = title;
		this.code = code;
		this.assessmendescr = assessmendescr;
		this.Descr1 = Descr1;
	}
	public Assessment(long assessmentid,long courseid,String title, String code, String assessmendescr,String coursedescr) {
		super();
		this.assessmentid = assessmentid;
		this.title = title;
		this.code = code;
		this.assessmendescr = assessmendescr;
		this.courseid = courseid;
		this.coursedescr = coursedescr;
	}

	public Assessment(long assessmentid, long courseid, long templateid, String title, String code, String assessmendescr,String coursedescr,String templatedescr,String createdby) {
		super();
		this.assessmentid = assessmentid;
		this.courseid = courseid;
		this.templateid = templateid;
		this.title = title;
		this.code = code;
		this.assessmendescr = assessmendescr;
		this.coursedescr = coursedescr;
		this.templatedescr = templatedescr;
		this.createdby = createdby;
	}
	
	public Assessment(long assessmentid, long courseid, long templateid, String title, String code, String assessmendescr,String coursedescr,String templatedescr,String createdby, String type) {
		super();
		this.assessmentid = assessmentid;
		this.courseid = courseid;
		this.templateid = templateid;
		this.title = title;
		this.code = code;
		this.assessmendescr = assessmendescr;
		this.coursedescr = coursedescr;
		this.templatedescr = templatedescr;
		this.createdby = createdby;
		this.type = type;
	}

	public Assessment(long assessmentid, long courseid, long templateid, String title, String code, String assessmendescr, String createdby, String coursedescr, String templatedescr, String type, String coursecode, String coursetitle,String createdon) {
		this.assessmentid = assessmentid;
		this.courseid = courseid;
		this.templateid = templateid;
		this.title = title;
		this.code = code;
		this.assessmendescr = assessmendescr;
		this.createdby = createdby;
		this.coursedescr = coursedescr;
		this.templatedescr = templatedescr;
		this.type = type;
		this.coursecode = coursecode;
		this.coursetitle = coursetitle;
		this.createdon=createdon;
	}

	@Override
	public String toString() {
		return "Assessment [assessmentid=" + assessmentid + ", courseid=" + courseid + ", templateid=" + templateid
				+ ", title=" + title + ", code=" + code + ", Descr1=" + Descr1 + ", assessmendescr=" + assessmendescr
				+ ", createdby=" + createdby + ", coursedescr=" + coursedescr + ", templatedescr=" + templatedescr
				+ ", type=" + type + ", sectiondetails=" + sectiondetails + "]";
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<SectionDetails> getSectiondetails() {
		return sectiondetails;
	}
	public void setSectiondetails(List<SectionDetails> sectiondetails) {
		this.sectiondetails = sectiondetails;
	}
	public String getTemplatedescr() {
		return templatedescr;
	}
	public void setTemplatedescr(String templatedescr) {
		this.templatedescr = templatedescr;
	}
	public String getAssessmendescr() {
		return assessmendescr;
	}
	public void setAssessmendescr(String assessmendescr) {
		this.assessmendescr = assessmendescr;
	}
	public String getCoursedescr() {
		return coursedescr;
	}
	public void setCoursedescr(String coursedescr) {
		this.coursedescr = coursedescr;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public long getAssessmentid() {
		return assessmentid;
	}
	public void setAssessmentid(long assessmentid) {
		this.assessmentid = assessmentid;
	}
	public long getCourseid() {
		return courseid;
	}
	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}
	public long getTemplateid() {
		return templateid;
	}
	public void setTemplateid(long templateid) {
		this.templateid = templateid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescr() {
		return assessmendescr;
	}
	public void setDescr(String assessmendescr) {
		this.assessmendescr = assessmendescr;
	}
	public String getDescr1() {
		return Descr1;
	}
	public void setDescr1(String descr1) {
		Descr1 = descr1;
	}

	public String getSchedulestatus() {
		return schedulestatus;
	}

	public void setSchedulestatus(String schedulestatus) {
		this.schedulestatus = schedulestatus;
	}

	public String getEvaluationstrategy() {
		return evaluationstrategy;
	}

	public void setEvaluationstrategy(String evaluationstrategy) {
		this.evaluationstrategy = evaluationstrategy;
	}

	public String getCoursecode() {
		return coursecode;
	}

	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}

	public String getCoursetitle() {
		return coursetitle;
	}

	public void setCoursetitle(String coursetitle) {
		this.coursetitle = coursetitle;
	}

	public String getCreatedon() {
		return createdon;
	}

	public void setCreatedon(String createdon) {
		this.createdon = createdon;
	}
}