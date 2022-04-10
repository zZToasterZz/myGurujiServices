package com.srdt.myguruji.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.srdt.myguruji.enitity.AssessmentDetails;
import com.srdt.myguruji.enitity.BatchDetails;
import com.srdt.myguruji.enitity.CourseDetails;
import com.srdt.myguruji.enitity.StudentAssessmentStatusDetails;
import com.srdt.myguruji.enitity.StudentDetails;
import com.srdt.myguruji.enitity.SyllabusPlan;
import com.srdt.myguruji.model.Courses;
import com.srdt.myguruji.model.ResponceMessage;
import com.srdt.myguruji.model.StudentAssessmentStatus;
import com.srdt.myguruji.model.SysUnit;
import com.srdt.myguruji.repository.AssessmentDetailsRepository;
import com.srdt.myguruji.repository.BatchRepository;
import com.srdt.myguruji.repository.StudentAssessmentStatusRepository;
import com.srdt.myguruji.repository.StudentDetailsRepository;

@RestController
@RequestMapping("/api/studentassessment")
public class StudentAssessmentStatusResource
{
	@Autowired
	BatchRepository batchRep;	
	@Autowired
	StudentDetailsRepository studentDetailsRep;
	@Autowired
	AssessmentDetailsRepository assessmentRep;
	
	@Autowired
	StudentAssessmentStatusRepository studentAssessmentStatusRep;
	
	@PostMapping("/create")
	public ResponceMessage create(@Valid @RequestBody StudentAssessmentStatus studentAssessmentStatus)
	{
		try
		{
	    	StudentDetails student = studentDetailsRep.findStudentById(studentAssessmentStatus.getStudentId());
	 		AssessmentDetails assessment = assessmentRep.findAssessmentDetailsById(studentAssessmentStatus.getAssessmentId());
	 		StudentAssessmentStatusDetails studentAssessmentStatusDetails = new StudentAssessmentStatusDetails(studentAssessmentStatus.getAssessmentAttendenceStatus(),studentAssessmentStatus.getAssessmentSubmissionStatus(),studentAssessmentStatus.getAssessmentAttendenceTimestamp() , studentAssessmentStatus.getAssessmentSubmissionTimestamp(), studentAssessmentStatus.getCreatedby(), student,null, assessment);
			studentAssessmentStatusRep.saveAndFlush(studentAssessmentStatusDetails);
			
			return new ResponceMessage("Attendance Marked/ Record Added");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return new ResponceMessage("error");
		}
	}
	
	@PostMapping("/update")
	public ResponceMessage update(@RequestBody StudentAssessmentStatus studentAssessmentStatus)
	{
		studentAssessmentStatusRep.updateStudentAssessmentStatusDetail(studentAssessmentStatus.getStudentId(), studentAssessmentStatus.getAssessmentId(), studentAssessmentStatus.getAssessmentSubmissionStatus(), new java.util.Date());
		return new ResponceMessage("Assessment Submitted!");
	}
	
	@GetMapping("/getsubmitstatus/{StudentId}/{AssessmentId}")
	public StudentAssessmentStatus getSubmitStatus(@PathVariable("StudentId") long StudentId, @PathVariable("AssessmentId") long AssessmentId)
	{
		StudentAssessmentStatus res = studentAssessmentStatusRep.findStudentAssesmentStatus(AssessmentId, StudentId);
		return res;
	}
}