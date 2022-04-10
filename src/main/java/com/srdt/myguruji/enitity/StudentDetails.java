package com.srdt.myguruji.enitity;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="StudentDetails")
@NamedQueries({
	@NamedQuery(name="StudentDetails.searchStudent",query="select new com.srdt.myguruji.model.Student(s.StudentId, s.Emplid, s.ApplNbr,"
			                                               +"s.CampusId, s.FirstName, s.MidleName, s.LastName, s.FullName,s.DateOfBirth,"
			                                               +"s.EmailAddr, s.PrimaryContact, s.SecondaryContact, s.CreatedBy) from "
			                                               +"StudentDetails s where StudentId like concat('%',:StudentId,'%') and "
			                                               +"Emplid like concat('%',:Emplid,'%') and ApplNbr like concat('%',:ApplNbr,'%') "
			                                               +"and CampusId like concat('%',:CampusId,'%') and FirstName like concat('%',:FirstName,'%') and "
			                                               +"EmailAddr like concat('%',:EmailAddr,'%') and PrimaryContact like concat('%',:PrimaryContact,'%')"),
	@NamedQuery(name="StudentDetails.findByStudentId",query="select s from StudentDetails s join fetch s.studentEnrollments t join fetch t.batchDetails u join fetch u.courseDetails where s.StudentId=:StudentId"),
	@NamedQuery(name="StudentDetails.findAllStudents",query="select s from StudentDetails s"),
	@NamedQuery(name="StudentDetails.findStudentById",query="select s from StudentDetails s where s.StudentId=:StudentId"),
	@NamedQuery(name="StudentDetails.findStudentidbyEmplid",query="select s.StudentId from StudentDetails s where s.Emplid=: emplid"),
	@NamedQuery(name="StudentDetails.findStudentbyEmplid",query="select s from StudentDetails s where s.Emplid=: emplid"),
	@NamedQuery(name="StudentDetails.updateStudentEmailandPhone", query="update StudentDetails st set st.EmailAddr=:email, st.PrimaryContact=:phone, st.LastUpdatedDate=:modifieddate where st.Emplid=:emplid "),
	@NamedQuery(name="StudentDetails.updateStudentEmailByEmplId",query="update StudentDetails set EmailAddr=:email, LastUpdatedDate=:modifieddate where Emplid=:emplid "),
	@NamedQuery(name="StudentDetails.updateStudentContactByEmplId",query="update StudentDetails set PrimaryContact=:contact, LastUpdatedDate=:modifieddate where Emplid=:emplid "),
	@NamedQuery(name="StudentDetails.getStudentByCampusId", query="select st from StudentDetails st where st.CampusId=:campusId")
})
public class StudentDetails extends SharedField implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(allocationSize=1,initialValue=1,name="Student_Sqr",sequenceName="Student_Sqr")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="Student_Sqr")
	@Column
	private long StudentId;
	@Column(length=30,unique=true,nullable=false)
	@NotBlank
	private String Emplid;
	@Column(length=30,unique=true,nullable=false)
	@NotBlank
	private String ApplNbr;
	@Column(length=30)
	private String CampusId;
	@Column(length=100,nullable=false)
	@NotBlank
	private String FirstName;
	@Column(length=100,nullable=true)
	private String MidleName;
	@Column(length=100)
	@NotBlank
	private String LastName;
	@Column(length=300)
	@NotBlank
	private String FullName;
	@Column(length=10)
	private String DateOfBirth;
	@Column(length=150)
	@Email
	private String EmailAddr;
	@Column(length=15,nullable=false)
	@NotBlank
	private String PrimaryContact;
	@Column(length=15,nullable=true)
	private String SecondaryContact;
	
	@OneToMany(mappedBy="student",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	List<StudentEnrollment> studentEnrollments = new ArrayList<>();
	
	@OneToMany(mappedBy="student",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	List<StudentAssessmentStatusDetails> studentAssesmentStatusDetails=new ArrayList<>();
	
	public StudentDetails() {
		super();
	}
	
	public StudentDetails(@NotBlank String emplid, @NotBlank String applNbr, String campusId, @NotBlank String firstName,
			String midleName, @NotBlank String lastName, String dateOfBirth, @Email String emailAddr,
			@NotBlank String primaryContact, String secondaryContact,String createdBy) {
		super();
		Emplid = emplid;
		ApplNbr = applNbr;
		CampusId = campusId;
		FirstName = firstName;
		MidleName = midleName;
		LastName = lastName;
		DateOfBirth = dateOfBirth;
		EmailAddr = emailAddr;
		PrimaryContact = primaryContact;
		SecondaryContact = secondaryContact;
		FullName = FirstName + " " + MidleName + " " + LastName;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}
    
	public List<StudentEnrollment> getStudentEnrollments() {
		return studentEnrollments;
	}

	public void setStudentEnrollments(List<StudentEnrollment> studentEnrollments) {
		this.studentEnrollments = studentEnrollments;
	}

	
	public List<StudentAssessmentStatusDetails> getStudentAssesmentStatusDetails() {
		return studentAssesmentStatusDetails;
	}

	public void setStudentAssesmentStatusDetails(List<StudentAssessmentStatusDetails> studentAssesmentStatusDetails) {
		this.studentAssesmentStatusDetails = studentAssesmentStatusDetails;
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

	public void setPrimaryCotact(String primaryContact) {
		PrimaryContact = primaryContact;
	}

	public String getSecondaryContact() {
		return SecondaryContact;
	}

	public void setSecondaryContact(String secondaryContact) {
		SecondaryContact = secondaryContact;
	}

	public long getStudentId() {
		return StudentId;
	}

	public void setStudentId(long studentId) {
		StudentId = studentId;
	}

	public String getEmplid() {
		return Emplid;
	}

	public void setEmplid(String emplid) {
		Emplid = emplid;
	}

	public String getApplNbr() {
		return ApplNbr;
	}

	public void setApplNbr(String applNbr) {
		ApplNbr = applNbr;
	}

	public String getCampusId() {
		return CampusId;
	}

	public void setCampusId(String campusId) {
		CampusId = campusId;
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

	public String getDateOfBirth() {
		return DateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}	
}
