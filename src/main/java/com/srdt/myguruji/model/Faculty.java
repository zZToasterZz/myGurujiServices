package com.srdt.myguruji.model;

public class Faculty {
	private long facultyid;
	private String facultycode;
	private String emplid;
	private String designation;
	private String pref;
	private String firstname;
	private String midlename;
	private String lastname;
	private String fullname;
	private String emailaddr;
	private String primarycontact;
	private String secondarycontact;
	private String createdby;
	public Faculty() {
		super();
	}
	public Faculty(String facultycode, String emplid, String designation, String pref, String firstname,
			String midlename, String lastname, String fullname, String emailaddr, String primarycotact,
			String secondarycontact, String createdby) {
		super();
		this.facultycode = facultycode;
		this.emplid = emplid;
		this.designation = designation;
		this.pref = pref;
		this.firstname = firstname;
		this.midlename = midlename;
		this.lastname = lastname;
		this.fullname = fullname;
		this.emailaddr = emailaddr;
		this.primarycontact = primarycotact;
		this.secondarycontact = secondarycontact;
		this.createdby = createdby;
	}
	public Faculty(long facultyid, String facultycode, String emplid, String designation, String pref, String firstname,
			String midlename, String lastname, String fullname, String emailaddr, String primarycotact,
			String secondarycontact, String createdby) {
		super();
		this.facultyid = facultyid;
		this.facultycode = facultycode;
		this.emplid = emplid;
		this.designation = designation;
		this.pref = pref;
		this.firstname = firstname;
		this.midlename = midlename;
		this.lastname = lastname;
		this.fullname = fullname;
		this.emailaddr = emailaddr;
		this.primarycontact = primarycotact;
		this.secondarycontact = secondarycontact;
		this.createdby = createdby;
	}
	public long getFacultyid() {
		return facultyid;
	}
	public void setFacultyid(long facultyid) {
		this.facultyid = facultyid;
	}
	public String getFacultycode() {
		return facultycode;
	}
	public void setFacultycode(String facultycode) {
		this.facultycode = facultycode;
	}
	public String getEmplid() {
		return emplid;
	}
	public void setEmplid(String emplid) {
		this.emplid = emplid;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getPref() {
		return pref;
	}
	public void setPref(String pref) {
		this.pref = pref;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getMidlename() {
		return midlename;
	}
	public void setMidlename(String midlename) {
		this.midlename = midlename;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
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
	public void setPrimarycontact(String primarycotact) {
		this.primarycontact = primarycotact;
	}
	public String getSecondarycontact() {
		return secondarycontact;
	}
	public void setSecondarycontact(String secondarycontact) {
		this.secondarycontact = secondarycontact;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}	
}
