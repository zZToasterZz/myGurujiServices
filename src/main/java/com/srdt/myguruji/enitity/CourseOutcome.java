package com.srdt.myguruji.enitity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "CourseOutcome",uniqueConstraints = {@UniqueConstraint(columnNames = {"CourseId","OutcomeCode"}),@UniqueConstraint(columnNames = {"CourseId","OutcomeTitle"})})
@NamedQueries({
	@NamedQuery(name = "CourseOutcome.getCourseOutcomeByCourseId", query = "select new com.srdt.myguruji.model.Translate(a.OutcomeId,a.OutcomeTitle,a.OutcomeCode,a.OutcomeDescr) from CourseOutcome a where a.courseDetails.CourseId=:courseid"),
	@NamedQuery(name = "CourseOutcome.removeById", query = "delete from CourseOutcome where OutcomeId in(:id)")
})
public class CourseOutcome implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "CourseOutcome_Sqr", sequenceName = "CourseOutcome_Sqr")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CourseOutcome_Sqr")
	private long OutcomeId;
	@Column(length = 100)
	@NotBlank
	private String OutcomeTitle;
	@Column(length = 100)
	@NotEmpty
	private String OutcomeCode;
	@Column(length = 2000)
	private String OutcomeDescr;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CourseId", referencedColumnName = "CourseId", insertable = true, updatable = true)
	private CourseDetails courseDetails;

	public CourseOutcome() {
		super();
	}

	public CourseOutcome(long outcomeId, @NotBlank String outcomeTitle, @NotEmpty String outcomeCode,
			String outcomeDescr, CourseDetails courseDetails) {
		super();
		OutcomeId = outcomeId;
		OutcomeTitle = outcomeTitle;
		OutcomeCode = outcomeCode;
		OutcomeDescr = outcomeDescr;
		this.courseDetails = courseDetails;
	}

	public long getOutcomeId() {
		return OutcomeId;
	}

	public void setOutcomeId(long outcomeId) {
		OutcomeId = outcomeId;
	}

	public String getOutcomeTitle() {
		return OutcomeTitle;
	}

	public void setOutcomeTitle(String outcomeTitle) {
		OutcomeTitle = outcomeTitle;
	}

	public String getOutcomeCode() {
		return OutcomeCode;
	}

	public void setOutcomeCode(String outcomeCode) {
		OutcomeCode = outcomeCode;
	}

	public String getOutcomeDescr() {
		return OutcomeDescr;
	}

	public void setOutcomeDescr(String outcomeDescr) {
		OutcomeDescr = outcomeDescr;
	}

	public CourseDetails getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(CourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}
}
