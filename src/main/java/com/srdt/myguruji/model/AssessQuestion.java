package com.srdt.myguruji.model;

import java.math.BigDecimal;
import java.util.List;

public class AssessQuestion {
	
	private long courseid;
	private long assessmentid;
	private long templateid;
	private List<AddedQuestion> add;
	public AssessQuestion() {
		super();
	}
	
	public AssessQuestion(long courseid, long assessmentid,long templateid, BigDecimal marks, String partialmarking,
			BigDecimal partialmarks, List<AddedQuestion> add) {
		super();
		this.courseid = courseid;
		this.assessmentid = assessmentid;
		this.templateid = templateid;
		this.add = add;
	}
	public long getCourseid() {
		return courseid;
	}
	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}
	public long getAssessmentid() {
		return assessmentid;
	}
	public void setAssessmentid(long assessmentid) {
		this.assessmentid = assessmentid;
	}
	public List<AddedQuestion> getAdd() {
		return add;
	}
	public void setAdd(List<AddedQuestion> add) {
		this.add = add;
	}

	public long getTemplateid() {
		return templateid;
	}

	public void setTemplateid(long templateid) {
		this.templateid = templateid;
	}    
}
