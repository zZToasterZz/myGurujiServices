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
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="FacultyDetails")
@NamedQueries({
	@NamedQuery(name="FacultyDetails.findByFacultyId",query="select f from FacultyDetails f where f.FacultyId=:FacultyId"),
	@NamedQuery(name="FacultyDetails.searchFaculty",query="select new com.srdt.myguruji.model.Faculty(f.FacultyId, f.FacultyCode, f.Emplid, f.Designation, f.Pref, f.FirstName,"
			                                        +"f.MidleName, f.LastName, f.FullName, f.EmailAddr, f.PrimaryContact, f.SecondaryContact,"
			                                        +"f.CreatedBy) from FacultyDetails f where f.FacultyId like concat('%',:FacultyId,'%') "
	                                                +"and f.FacultyCode like concat('%',:FacultyCode,'%') and f.Emplid like concat('%',:Emplid,'%') "
			                                        +"and f.FirstName like concat('%',:FirstName,'%') and f.EmailAddr like concat('%',:EmailAddr,'%') "
	                                                +"and f.PrimaryContact like concat('%',:PrimaryContact,'%') and f.Designation like concat('%',:Designation,'%')"),
	@NamedQuery(name="FacultyDetails.deleteByFacultyId",query="delete from FacultyDetails where FacultyId in(:FacultyId)"),
	@NamedQuery(name="FacultyDetails.updateEmailPhone", query="update FacultyDetails f set f.EmailAddr=:email, f.PrimaryContact=:phone, f.LastUpdatedDate=:modifieddate where f.Emplid=:emplid"),
	@NamedQuery(name="FacultyDetails.getFacultynameByEmplid", query="select fd from FacultyDetails fd where fd.Emplid=:emplid "),
	@NamedQuery(name="FacultyDetails.updateEmailByEmplId",query="update FacultyDetails set EmailAddr=:email, LastUpdatedDate=:modifieddate where Emplid=:emplid "),
	@NamedQuery(name="FacultyDetails.updateContactByEmplId",query="update FacultyDetails set PrimaryContact=:contact, LastUpdatedDate=:modifieddate where Emplid=:emplid "),
	@NamedQuery(name ="FacultyDetails.getCoursesByFacultyId",query = "select distinct new com.srdt.myguruji.model.Courses(d.CourseCode, d.CourseTitle, d.CourseDescr,d.CourseId, d.CreatedBy) from FacultyDetails a join a.facultyTaggingDetails b join b.batchDetails c join c.courseDetails d where a.Emplid=:emplid"),
	@NamedQuery(name = "FacultyDetails.getFacultyByCourseId",query = "select distinct a from FacultyDetails a join a.facultyTaggingDetails b join b.batchDetails c join c.courseDetails d where d.CourseId=:courseid"),
	@NamedQuery(name = "FacultyDetails.searchFacultyByCourseOrBatch",query = "select distinct a from FacultyDetails a join a.facultyTaggingDetails b join b.batchDetails c join c.courseDetails d where d.CourseId=:courseid and c.BatchId like case :batchid when '0' then '%' else :batchid end"),
	@NamedQuery(name = "FacultyDetails.getFacultyByEmplid",query = "select ft from FacultyDetails ft where ft.Emplid=:emplId")	

})
public class FacultyDetails extends SharedField implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(allocationSize=1,initialValue=1,name="Faculty_Sqr",sequenceName="Faculty_Sqr")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="Faculty_Sqr")
	@Column
	private long FacultyId;
	@Column(length=30,nullable=false,unique=true)
	@NotBlank(message="Faculty Code  can not blank")
	private String FacultyCode;
	@Column(length=30,nullable=false,unique=true)
	@NotBlank(message="Emplid  can not blank")
	private String Emplid;
	@Column(length=50,nullable=true)
	private String Pref;
	@Column(length=100,nullable=false)
	@NotBlank(message="First name  can not blank")
	private String FirstName;
	@Column(length=100,nullable=true)
	private String MidleName;
	@Column(length=100,nullable=true)
	private String LastName;
	@Column(length=300,nullable=true)
	private String FullName;
	@Column(length=150,nullable=false)
	@NotBlank(message="Email can not blank")
	private String EmailAddr;
	@Column(length=15,nullable=false)
	@NotBlank(message="PrimaryContact not blank")
	private String PrimaryContact;
	@Column(length=15,nullable=true)
	private String SecondaryContact;
	@Column(length=50,nullable=true)
	private String Designation;
	
	
	@OneToMany(mappedBy="facultyDetails",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private List<FacultyTaggingDetails> facultyTaggingDetails;
	
	public FacultyDetails() {
		super();
	}

	public FacultyDetails(String facultyCode, String emplid,String pref,String firstName, String midleName, String lastName,
			String emailAddr, String primaryContact, String secondaryContact,String designation,String createdBy) {
		super();
		FacultyCode = facultyCode;
		FirstName = firstName;
		MidleName = midleName;
		LastName = lastName;
		EmailAddr = emailAddr;
		PrimaryContact = primaryContact;
		SecondaryContact = secondaryContact;
		FullName = FirstName + " " + LastName;
		if(!MidleName.equals(""))
		{
			FullName = FirstName + " " + MidleName + " " + LastName;
		}
		
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		Emplid = emplid;
		Designation = designation;
		Pref=pref;
	}

	public String getPref() {
		return Pref;
	}

	public void setPref(String pref) {
		Pref = pref;
	}

	public String getDesignation() {
		return Designation;
	}

	public void setDesignation(String designation) {
		Designation = designation;
	}

	public String getEmplid() {
		return Emplid;
	}

	public void setEmplid(String emplid) {
		Emplid = emplid;
	}

	public List<FacultyTaggingDetails> getFacultyTaggingDetails() {
		return facultyTaggingDetails;
	}

	public void setFacultyTaggingDetails(List<FacultyTaggingDetails> facultyTaggingDetails) {
		this.facultyTaggingDetails = facultyTaggingDetails;
	}

	public long getFacultyId() {
		return FacultyId;
	}

	public void setFacultyId(long facultyId) {
		FacultyId = facultyId;
	}

	public String getFacultyCode() {
		return FacultyCode;
	}

	public void setFacultyCode(String facultyCode) {
		FacultyCode = facultyCode;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getMidleName() {
		return MidleName;
	}

	public void setMidleName(String midleName) {
		MidleName = midleName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getFullName() {
		return FullName;
	}

	public void setFullName(String fullName) {
		FullName = fullName;
	}

	public String getEmailAddr() {
		return EmailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		EmailAddr = emailAddr;
	}

	public String getPrimaryContact() {
		return PrimaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		PrimaryContact = primaryContact;
	}

	public String getSecondaryContact() {
		return SecondaryContact;
	}

	public void setSecondaryContact(String secondaryContact) {
		SecondaryContact = secondaryContact;
	}
    
}
