package com.srdt.myguruji.model;

public class Credential {

	private String loginid;
	private String emplid;
	private String emailid;
	private String createdby;
	public Credential() {
		super();
	}
	
	public Credential(String loginid, String emplid, String emailid, String createdby) {
		super();
		this.loginid = loginid;
		this.emplid = emplid;
		this.emailid = emailid;
		this.createdby = createdby;
	}

	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getEmplid() {
		return emplid;
	}
	public void setEmplid(String emplid) {
		this.emplid = emplid;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
}
