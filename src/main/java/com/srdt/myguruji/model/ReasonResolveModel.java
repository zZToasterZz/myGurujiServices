package com.srdt.myguruji.model;

import java.util.Date;

public class ReasonResolveModel
{
	private String reason;
	private String resolve;
	private Date createddate;
	
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	public ReasonResolveModel(String reason, String resolve, Date createddate) {
		super();
		this.reason = reason;
		this.resolve = resolve;
		this.createddate = createddate;
	}
	public ReasonResolveModel() {
		super();
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getResolve() {
		return resolve;
	}
	public void setResolve(String resolve) {
		this.resolve = resolve;
	}
}