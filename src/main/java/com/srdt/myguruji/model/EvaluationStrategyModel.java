package com.srdt.myguruji.model;

public class EvaluationStrategyModel
{
	private long strategyid = 0;
	private long assessmentid;
	private String strategy;
	
	public EvaluationStrategyModel(long strategyid, long assessmentid, String strategy) {
		super();
		this.strategyid = strategyid;
		this.assessmentid = assessmentid;
		this.strategy = strategy;
	}
	public EvaluationStrategyModel() {
		super();
	}
	public long getStrategyid() {
		return strategyid;
	}
	public void setStrategyid(long strategyid) {
		this.strategyid = strategyid;
	}
	public long getAssessmentid() {
		return assessmentid;
	}
	public void setAssessmentid(long assessmentid) {
		this.assessmentid = assessmentid;
	}
	public String getStrategy() {
		return strategy;
	}
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	@Override
	public String toString() {
		return "EvaluationStrategyModel [strategyid=" + strategyid + ", assessmentid=" + assessmentid + ", strategy="
				+ strategy + "]";
	}
}