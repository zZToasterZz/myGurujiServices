package com.srdt.myguruji.enitity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "AssessmentResponce")
@NamedQueries({
	@NamedQuery(name = "AssessmentResponce.AssessmentResponceById", query = "select a from AssessmentResponce a where a.ResponceId=:Id"),
	@NamedQuery(name = "AssessmentResponce.deleteAssessmentResponce" ,query = "delete from AssessmentResponce a where Emplid=:Emplid and a.questionDetails.QuestionId=:QuestionId and a.assessmentDetails.AssessmentId=:AssessmentId"),
	@NamedQuery(name="AssessmentResponce.setMarks", query="update AssessmentResponce a set a.marksobtained=:MRK where a.assessmentDetails.AssessmentId=:AID and a.questionDetails.QuestionId=:QID and a.Emplid=:EID"),
	@NamedQuery(name = "AssessmentResponce.findAssessRecord", query = "select a from AssessmentResponce a where a.Emplid=:EID and a.assessmentDetails.AssessmentId=:AID and a.questionDetails.QuestionId=:QID"),
	@NamedQuery(name="AssessmentResponce.getStudentMarksgradebook",query="select sum(marksobtained),Emplid from AssessmentResponce where assessmentDetails.AssessmentId=:assessmentId  group by Emplid")
})
public class AssessmentResponce implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "AssessmentResponce_Sqr",sequenceName = "AssessmentResponce_Sqr",initialValue = 1,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "AssessmentResponce_Sqr")
	private long ResponceId;
	@Column(length = 50)
	private String QuesResponce;
	@Lob
	@Column(length=16777000)
	private String SubjectiveResponce;
	@Column(length = 10,nullable = false)
	@NotBlank
	private String Qtype;
	@Column(length = 50,nullable = false)
	@NotBlank
	private String Emplid;
	@Column
	private double marksobtained = 0;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AssessmentId", referencedColumnName = "AssessmentId",insertable = true,updatable = true)
	@NotNull
	private AssessmentDetails assessmentDetails;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QuestionId", referencedColumnName = "QuestionId",insertable = true,updatable = true)
	@NotNull
	private QuestionDetails questionDetails;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CourseId", referencedColumnName = "CourseId",insertable = true,updatable = true)
	@NotNull
	private CourseDetails courseDetails;
	
	public AssessmentResponce() {
		super();
	}

	public AssessmentResponce(String quesResponce, String subjectiveResponce, String qtype, String emplid,
			AssessmentDetails assessmentDetails, QuestionDetails questionDetails, CourseDetails courseDetails) {
		super();
		QuesResponce = quesResponce;
		SubjectiveResponce = subjectiveResponce;
		Qtype = qtype;
		Emplid = emplid;
		this.assessmentDetails = assessmentDetails;
		this.questionDetails = questionDetails;
		this.courseDetails = courseDetails;
	}
	public AssessmentResponce(long responceId, String quesResponce, String subjectiveResponce, @NotBlank String qtype,
			@NotBlank String emplid, double marksobtained, @NotNull AssessmentDetails assessmentDetails,
			@NotNull QuestionDetails questionDetails, @NotNull CourseDetails courseDetails) {
		super();
		ResponceId = responceId;
		QuesResponce = quesResponce;
		SubjectiveResponce = subjectiveResponce;
		Qtype = qtype;
		Emplid = emplid;
		this.marksobtained = marksobtained;
		this.assessmentDetails = assessmentDetails;
		this.questionDetails = questionDetails;
		this.courseDetails = courseDetails;
	}
	public String getEmplid() {
		return Emplid;
	}
	public void setEmplid(String emplid) {
		Emplid = emplid;
	}
	public long getResponceId() {
		return ResponceId;
	}
	public void setResponceId(long responceId) {
		ResponceId = responceId;
	}
	public String getQuesResponce() {
		return QuesResponce;
	}
	public void setQuesResponce(String quesResponce) {
		QuesResponce = quesResponce;
	}
	public String getSubjectiveResponce() {
		return SubjectiveResponce;
	}
	public void setSubjectiveResponce(String subjectiveResponce) {
		SubjectiveResponce = subjectiveResponce;
	}
	public String getQtype() {
		return Qtype;
	}
	public void setQtype(String qtype) {
		Qtype = qtype;
	}
	public AssessmentDetails getAssessmentDetails() {
		return assessmentDetails;
	}
	public void setAssessmentDetails(AssessmentDetails assessmentDetails) {
		this.assessmentDetails = assessmentDetails;
	}
	public QuestionDetails getQuestionDetails() {
		return questionDetails;
	}
	public void setQuestionDetails(QuestionDetails questionDetails) {
		this.questionDetails = questionDetails;
	}
	public CourseDetails getCourseDetails() {
		return courseDetails;
	}
	public void setCourseDetails(CourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}
	public double getMarksobtained() {
		return marksobtained;
	}
	public void setMarksobtained(double marksobtained)
	{
		try {
			this.marksobtained = marksobtained;
		}
		catch(Exception e) {
			this.marksobtained = 0;
		}
	}

	@Override
	public String toString() {
		return "AssessmentResponce [ResponceId=" + ResponceId + ", QuesResponce=" + QuesResponce
				+ ", SubjectiveResponce=" + SubjectiveResponce + ", Qtype=" + Qtype + ", Emplid=" + Emplid
				+ ", marksobtained=" + marksobtained + ", assessmentDetails=" + assessmentDetails + ", questionDetails="
				+ questionDetails + ", courseDetails=" + courseDetails + "]";
	}
}