package com.srdt.myguruji.enitity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "AssessmentTemplate")
@NamedQueries({
	@NamedQuery(name = "AssessmentTemplate.findAssessmentTemplateByTemplateId",query = "select t from AssessmentTemplate t where "
			                                                                           +"TemplateId like CASE WHEN CONCAT('%',:TemplateId,'%')='%%' THEN '%%' ELSE :TemplateId END"),
	@NamedQuery(name = "AssessmentTemplate.findTemplateDetailsByTemplateId",query =  "select t from AssessmentTemplate t join fetch t.sections where t.TemplateId=:TemplateId"),
	@NamedQuery(name = "AssessmentTemplate.findTemplateDetailsByAssessmentId",query =  "select t from AssessmentTemplate t join fetch t.sections where t.TemplateId=(select b.TemplateId from AssessmentDetails a join a.template b where a.AssessmentId=:AssessmentId)"),
	@NamedQuery(name = "AssessmentTemplate.findTemplateByEmplid",query =  "select t from AssessmentTemplate t where t.CreatedBy=:CreatedBy")
})
public class AssessmentTemplate extends SharedField implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(allocationSize = 1,initialValue = 1,name = "AssessmentTemplate_Sqr",sequenceName = "AssessmentTemplate_Sqr")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "AssessmentTemplate_Sqr")
	private long TemplateId;
	@Column(length = 30)
	@NotBlank
	private String TemplateCode;
	@Column(length = 150)
	@NotBlank
	private String Title;
	@Column(length = 500)
	private String Descr;
	@Column
	@Min(value = 1,message = "No of section not less than zero")
	private int NoOfSection;

	@Column(columnDefinition = "int default 0")
	@Min(1)
	private int MaxMarks;
	
	@OneToMany(mappedBy = "template", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AssessmentDetails> assessmentDetails;
	
	@OneToMany(mappedBy = "template", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AssessmentSection> sections;
	
	public AssessmentTemplate() {
		super();
	}

	public AssessmentTemplate(@NotBlank String templateCode, @NotBlank String title, String descr,
			@Min(value = 1) int noOfSection,CourseDetails courseDetails,String createdBy,int MaxMarks) {
		super();
		TemplateCode = templateCode;
		Title = title;
		Descr = descr;
		NoOfSection = noOfSection;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		this.MaxMarks = MaxMarks;
	}

	public int getMaxMarks() {
		return MaxMarks;
	}

	public void setMaxMarks(int maxMarks) {
		MaxMarks = maxMarks;
	}

	public List<AssessmentSection> getSections() {
		return sections;
	}

	public void setSections(List<AssessmentSection> sections) {
		this.sections = sections;
	}

	public int getNoOfSection() {
		return NoOfSection;
	}

	public void setNoOfSection(int noOfSection) {
		NoOfSection = noOfSection;
	}

	public List<AssessmentDetails> getAssessmentDetails() {
		return assessmentDetails;
	}

	public void setAssessmentDetails(List<AssessmentDetails> assessmentDetails) {
		this.assessmentDetails = assessmentDetails;
	}

	public long getTemplateId() {
		return TemplateId;
	}

	public void setTemplateId(long templateId) {
		TemplateId = templateId;
	}

	public String getTemplateCode() {
		return TemplateCode;
	}

	public void setTemplateCode(String templateCode) {
		TemplateCode = templateCode;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescr() {
		return Descr;
	}

	public void setDescr(String descr) {
		Descr = descr;
	}
}
