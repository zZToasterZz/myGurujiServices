package com.srdt.myguruji.model;

public class Student
{
	private long studentid;
	private String emplid;
	private String applnbr;
	private String campusid;
	private String firstname;
	private String midlename;
	private String lastname;
	private String fullname;
	private String dob;
	private String emailaddr;
	private String primarycontact;
	private String secondarycontact;
	private String createdby;
	private String comment;
	private String assignstatus;
	
	public Student() {
		super();
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Student(String emplid, String applnbr, String campusid, String firstname, String midlename, String lastname,
			String fullname) {
		super();
		this.emplid = emplid;
		this.applnbr = applnbr;
		this.campusid = campusid;
		this.firstname = firstname;
		this.midlename = midlename;
		this.lastname = lastname;
		this.fullname = fullname.replace("null", "");
	}

	public Student(String emplid, String applnbr, String campusid, String firstname,String midlename, String lastname, String fullname,
			String dob, String emailaddr, String primarycotact, String secondarycontact,String createdby) {
		super();
		this.emplid = emplid;
		this.applnbr = applnbr;
		this.campusid = campusid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.fullname = fullname;
		this.dob = dob;
		this.emailaddr = emailaddr;
		this.primarycontact = primarycotact;
		this.secondarycontact = secondarycontact;
		this.midlename = midlename;
		this.createdby = createdby;
	}
	
	public Student(long studentid, String emplid, String applnbr, String campusid, String firstname, String midlename,
			String lastname, String fullname, String dob, String emailaddr, String primarycotact,
			String secondarycontact,String createdby, String comment) {
		super();
		this.studentid = studentid;
		this.emplid = emplid;
		this.applnbr = applnbr;
		this.campusid = campusid;
		this.firstname = firstname;
		this.midlename = midlename;
		this.lastname = lastname;
		this.fullname = fullname;
		this.dob = dob;
		this.emailaddr = emailaddr;
		this.primarycontact = primarycotact;
		this.secondarycontact = secondarycontact;
		this.createdby = createdby;
		this.comment = comment;
	}
	
	public Student(long studentid, String emplid, String applnbr, String campusid, String firstname, String midlename,
			String lastname, String fullname, String dob, String emailaddr, String primarycotact,
			String secondarycontact,String createdby) {
		super();
		this.studentid = studentid;
		this.emplid = emplid;
		this.applnbr = applnbr;
		this.campusid = campusid;
		this.firstname = firstname;
		this.midlename = midlename;
		this.lastname = lastname;
		this.fullname = fullname;
		this.dob = dob;
		this.emailaddr = emailaddr;
		this.primarycontact = primarycotact;
		this.secondarycontact = secondarycontact;
		this.createdby = createdby;
	}
	
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getMidlename() {
		return midlename;
	}
	public void setMidlename(String midlename) {
		this.midlename = midlename;
	}
	public long getStudentid() {
		return studentid;
	}
	public void setStudentid(long studentid) {
		this.studentid = studentid;
	}
	public String getEmplid() {
		return emplid;
	}
	public void setEmplid(String emplid) {
		this.emplid = emplid;
	}
	public String getApplnbr() {
		return applnbr;
	}
	public void setApplnbr(String applnbr) {
		this.applnbr = applnbr;
	}
	public String getCampusid() {
		return campusid;
	}
	public void setCampusid(String campusid) {
		this.campusid = campusid;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFullname() {
		return fullname.replace("null", "");
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getEmailaddr() {
		return emailaddr;
	}
	public void setEmailaddr(String emailaddr) {
		this.emailaddr = emailaddr;
	}
	public String getPrimarycontact() {
		return primarycontact;
	}
	public void setPrimarycontact(String primarycontact) {
		this.primarycontact = primarycontact;
	}
	public String getSecondarycontact() {
		return secondarycontact;
	}
	public void setSecondarycontact(String secondarycontact) {
		this.secondarycontact = secondarycontact;
	}

	public String getAssignstatus() {
		return assignstatus;
	}

	public void setAssignstatus(String assignstatus) {
		this.assignstatus = assignstatus;
	}
}
