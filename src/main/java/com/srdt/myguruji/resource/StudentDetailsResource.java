package com.srdt.myguruji.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.srdt.myguruji.model.*;
import com.srdt.myguruji.utility.Generation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srdt.myguruji.enitity.BatchDetails;
import com.srdt.myguruji.enitity.StudentDetails;
import com.srdt.myguruji.enitity.StudentEnrollment;
import com.srdt.myguruji.repository.MappingRepository;
import com.srdt.myguruji.repository.StudentDetailsRepository;

@RestController
@RequestMapping("/api/student")
public class StudentDetailsResource {

	@Autowired
	StudentDetailsRepository studentrep;
	
	@Autowired
	EntityManager em;
	
	@Autowired
	MappingRepository maprep;
	
	@PostMapping("/syncstudent")
	public List<Student> syncStudent(@RequestBody List<Student> students)
	{
		return maprep.syncStudent(students);
	}

	@PostMapping("/syncstudentnew")
	public List<Student> syncStudent_new(@RequestBody List<Student> students)
	{
		return maprep.syncStudent(students);
	}

	
	@PostMapping("/create")
	public ResponceMessage create(@RequestBody Student students)
	{
		StudentDetails studentDetails = new StudentDetails(students.getEmplid(), students.getApplnbr(), students.getCampusid(), students.getFirstname(), students.getMidlename(), students.getLastname(), students.getDob(), students.getEmailaddr(), students.getPrimarycontact(), students.getSecondarycontact(), students.getCreatedby());
		studentrep.save(studentDetails);
		studentrep.flush();
		return new ResponceMessage("Student Created");
	}
	
	@PostMapping("/enrollment")
	public List<StudentEnrol> enrollment(@RequestBody List<StudentEnrol> studentEnrols)
	{
		return maprep.studentEnrol(studentEnrols);
	}
	
	@PostMapping("/search")
	public List<Student> search(@RequestBody Student student)
	{
		String std = Long.toString(student.getStudentid());
		if(student.getStudentid() == 0)
		{
			std = "";
		}
		return studentrep.searchStudent(std, student.getEmplid(), student.getApplnbr(), student.getCampusid(), student.getFirstname(), student.getEmailaddr(), student.getPrimarycontact());		
	}

	/***************SANDHYA****************/
	@GetMapping("/getstudentbyEmplid/{emplid}")
	public StudentInfo findStudentByEmplId(@PathVariable("emplid") String emplid)
	{
		StudentDetails studentDetails = studentrep.findStudentbyEmplid(emplid);
		return findStudentById(studentDetails.getStudentId());
	}

	@GetMapping("/getstudentbyid/{StudentId}")
	public StudentInfo findStudentById(@PathVariable("StudentId") long StudentId)
	{
		StudentDetails studentDetails = studentrep.findByStudentId(StudentId);
		Student student = null;
		List<Batches> batches = null;
		List<StudentEnrollment> enrollments;
		if(studentDetails != null)
		{
			enrollments = studentDetails.getStudentEnrollments();
			if(enrollments != null)
			{
				batches = enrollments.stream()
				           .map(x->{
				        	   BatchDetails batchDetails = x.getBatches();
				        	   return new Batches(batchDetails.getCourseDetails().getCourseId(), batchDetails.getBatchId(), batchDetails.getBatchtitle(), batchDetails.getBatchCode(), batchDetails.getDescr(), batchDetails.getCreatedBy(), batchDetails.getBatchType(), batchDetails.getBatchYear(), batchDetails.getBatchSeq(),batchDetails.getSection());
				           })
				           .collect(Collectors.toList());
			}
			student = new Student(studentDetails.getStudentId(), studentDetails.getEmplid(), studentDetails.getApplNbr(), studentDetails.getCampusId(), studentDetails.getFirstName(), studentDetails.getMidleName(), studentDetails.getLastName(), studentDetails.getFullName(), studentDetails.getDateOfBirth(), studentDetails.getEmailAddr(), studentDetails.getPrimaryContact(), studentDetails.getSecondaryContact(), studentDetails.getCreatedBy());
		}
		
		return new StudentInfo(student, batches);
	}
	
	//Shantanu
	@PostMapping("/deleteenrollment")
	public ResponceMessage deleteEnrollment(@RequestBody KeyIDsList ids)
	{
		ArrayList<Long> arr = new ArrayList<>();
		for(long i : ids.getIds())
			arr.add(i);
		
		int rows = maprep.deleteEnrollment( arr );
		return new ResponceMessage() {{setMessage("rows deleted : "+rows);}};
	}
	
	/*****SANDHYA*****/
	@PostMapping("/updateEmailAndContact/{email}/{contact}/{campusId}")
	@Transactional
	public ResponceMessage updateMailandPhone(@PathVariable String email,@PathVariable String contact,@PathVariable String campusId )
	{
		ResponceMessage msg=null;
		try {
		Query query=em.createNamedQuery("StudentDetails.updateEmailandPhone").setParameter("email", email)
				.setParameter("phone", contact).setParameter("campusid", campusId);
	
		if(query.executeUpdate()>0)
			msg= new ResponceMessage("Successfully update");
		}
		catch(Exception e) {		
			
			msg= new ResponceMessage("Something wrong");
		}
		return msg;
	}

	@PostMapping("/updateStudentEmailContact")
	@Transactional
	public ResponceMessage updateStudentMailorPhone(@RequestBody List<UpdatePhoneEmail> contactlist)
	{
		ResponceMessage msg=null;

		int count=0;
		try {
			for (UpdatePhoneEmail list : contactlist) {
				if ((list.getContact().trim().length() == 0 || list.getContact() == null) && list.getEmplid().trim().length() != 0 && list.getEmplid() != null) {
					count = studentrep.updateStudentEmailByEmplId(list.getEmailid(), Generation.getCurrentDate(), list.getEmplid());
					System.out.println("Mail count:::" + count);
				}
				if ((list.getEmailid().trim().length() == 0 || list.getEmailid() == null) && list.getEmplid().trim().length() != 0 && list.getEmplid() != null) {
					count = studentrep.updateStudentContactByEmplId(list.getContact(), Generation.getCurrentDate(), list.getEmplid());
					System.out.println("contact count:::" + count);
				}

				if (list.getContact().trim().length() > 0 && list.getContact() != null && list.getEmailid().trim().length() != 0 && list.getEmailid() != null && list.getEmplid().trim().length() != 0 && list.getEmplid() != null) {
					count = studentrep.updateStudentEmailandPhone(list.getEmailid(), list.getContact(), Generation.getCurrentDate(), list.getEmplid());
				}
				if (count > 0)
					msg = new ResponceMessage("Successfully update");
			}
		}catch (Exception e)
		{
			msg=new ResponceMessage("Error:"+e.getMessage());
		}
		return msg;
	}

	@GetMapping("getStudentCourseplanMap/{campusId}")
	public List<StudentCoursePlanInfo> getStdentCoursePlanByCampusId(@PathVariable String campusId)
	{
		Query query=em.createNativeQuery("select bd.batch_id,bd.batch_code,cd.course_code,cd.course_descr,bd.batch_type, concat(fd.pref,' ',coalesce(fd.first_name,' '),' ',coalesce(fd.last_name,' ')) as full_name,bd.batchtitle  " +
				" from batch_details bd left join student_enrollment se on se.batch_id=bd.batch_id " +
				" left join student_details sd on sd.student_id=se.student_id  " +
				" left join course_details cd on cd.course_id=bd.course_id left join faculty_tagging_details ft on ft.batch_id=bd.batch_id " +
				" left join faculty_details fd on fd.faculty_id=ft.faculty_id where sd.campus_id=:campusid ");

		List<Object[]> crsplan=query.setParameter("campusid",campusId).getResultList();

		return crsplan.stream().map(x->{
			String courseplanstatus="N";
			Long crsplancount=((Number)em.createNativeQuery("select count(plan_code) from course_plan where batch_id=:batchid")
					.setParameter("batchid",x[0].toString()).getSingleResult()).longValue();
			if(crsplancount>0)
				 courseplanstatus="Y";

			StudentCoursePlanInfo crsinfo= new StudentCoursePlanInfo((String)x[1], (String)x[2],(String)x[3], (String)x[4], (String)x[5], courseplanstatus);
			crsinfo.setBatchtitle((String)x[6]);
			return crsinfo;
		}).collect(Collectors.toList());

	}

	@GetMapping("/getStudentLoginsyncStatus/{campusId}")
	public DoubleResponseModel getStudentSyncStatus(@PathVariable String campusId)
	{
		String status;
		StudentDetails student=studentrep.getStudentByCampusId(campusId);
		String middlename=(student.getMidleName()==null)?"":student.getMidleName();
		String fullname=String.join(" ",Arrays.asList(student.getFirstName(),middlename,student.getLastName()));
		Long usercount=((Number)(em.createNativeQuery("select count(*) from user_login where login_id=:loginid")
				.setParameter("loginid",campusId).getSingleResult())).longValue();
		if(usercount>0)
			status="Y";
		else
			status="N";

		String email=String.join(" : ",Arrays.asList(student.getEmailAddr(),status));
		return new DoubleResponseModel(fullname,email);
	}
}