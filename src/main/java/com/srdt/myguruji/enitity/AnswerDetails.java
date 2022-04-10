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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="AnswerDetails")
public class AnswerDetails extends SharedField implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(initialValue=1,allocationSize=1,name="AnswerDetails_Sqr",sequenceName="AnswerDetails_Sqr")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="AnswerDetails_Sqr")
	@Column
	private long AnswerId;
	@Lob
	@Column(length=16777000,nullable=false)
	private String Opt1;
	@Lob
	@Column(length=16777000,nullable=false)
	private String Opt2;
	@Lob
	@Column(length=16777000,nullable=false)
	private String Opt3;
	@Lob
	@Column(length=16777000,nullable=false)
	private String Opt4;
	@Lob
	@Column(length=16777000,nullable=false)
	private String Opt5;
	@Lob
	@Column(length=100000,nullable=false)
	private String Opt6;
	@Column(length=250,nullable=false)
	@NotBlank
	private String CurrectOpt;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="QuestionId",referencedColumnName="QuestionId",insertable=true,updatable=true,unique=true)
	private QuestionDetails questionDetails;

	public AnswerDetails() {
		super();
	}

	public AnswerDetails(String opt1, String opt2, String opt3, String opt4, String opt5, String opt6,
			String currectOpt, QuestionDetails questionDetails,String createdBy) {
		super();
		Opt1 = opt1;
		Opt2 = opt2;
		Opt3 = opt3;
		Opt4 = opt4;
		Opt5 = opt5;
		Opt6 = opt6;
		CurrectOpt = currectOpt;
		this.questionDetails = questionDetails;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}

	public long getAnswerId() {
		return AnswerId;
	}

	public void setAnswerId(long answerId) {
		AnswerId = answerId;
	}

	public String getOpt1() {
		return Opt1;
	}

	public void setOpt1(String opt1) {
		Opt1 = opt1;
	}

	public String getOpt2() {
		return Opt2;
	}

	public void setOpt2(String opt2) {
		Opt2 = opt2;
	}

	public String getOpt3() {
		return Opt3;
	}

	public void setOpt3(String opt3) {
		Opt3 = opt3;
	}

	public String getOpt4() {
		return Opt4;
	}

	public void setOpt4(String opt4) {
		Opt4 = opt4;
	}

	public String getOpt5() {
		return Opt5;
	}

	public void setOpt5(String opt5) {
		Opt5 = opt5;
	}

	public String getOpt6() {
		return Opt6;
	}

	public void setOpt6(String opt6) {
		Opt6 = opt6;
	}

	public String getCurrectOpt() {
		return CurrectOpt;
	}

	public void setCurrectOpt(String currectOpt) {
		CurrectOpt = currectOpt;
	}

	public QuestionDetails getQuestionDetails() {
		return questionDetails;
	}

	public void setQuestionDetails(QuestionDetails questionDetails) {
		this.questionDetails = questionDetails;
	}	
}
