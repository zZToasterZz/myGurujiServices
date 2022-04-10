package com.srdt.myguruji.enitity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "EvaluationStrategy")
@NamedQueries({
		@NamedQuery(name="EvaluationStrategy.setEvaluationStrategy", query="update EvaluationStrategy set strategy=:strategy where assessmentDetails.AssessmentId=:assessid"),
		@NamedQuery(name="EvaluationStrategy.findEvaluationStrategy", query="select strategy from EvaluationStrategy  where strategyid =(select Max(es.strategyid) from EvaluationStrategy es where es.assessmentDetails.AssessmentId=:assessid)")
})

public class EvaluationStrategy
{
	@Id
	@SequenceGenerator(name = "EvaluationStrategy_Sqr",sequenceName = "EvaluationStrategy_Sqr",initialValue = 1,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "EvaluationStrategy_Sqr")
	private long strategyid;
	
	@Column(length = 1, nullable = false)
	private String strategy = "M"; //M = Manual, A = Auto
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AssessmentId", referencedColumnName = "AssessmentId",insertable = true,updatable = true)
	@NotNull
	private AssessmentDetails assessmentDetails;

	public EvaluationStrategy(long strategyid, String strategy, @NotNull AssessmentDetails assessmentDetails) {
		super();
		this.strategyid = strategyid;
		this.strategy = strategy;
		this.assessmentDetails = assessmentDetails;
	}

	public EvaluationStrategy() {
		super();
	}

	public long getStrategyid() {
		return strategyid;
	}

	public void setStrategyid(long strategyid) {
		this.strategyid = strategyid;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public AssessmentDetails getAssessmentDetails() {
		return assessmentDetails;
	}

	public void setAssessmentDetails(AssessmentDetails assessmentDetails) {
		this.assessmentDetails = assessmentDetails;
	}
}