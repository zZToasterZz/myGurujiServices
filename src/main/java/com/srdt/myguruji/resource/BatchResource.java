package com.srdt.myguruji.resource;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srdt.myguruji.enitity.BatchDetails;
import com.srdt.myguruji.enitity.CourseDetails;
import com.srdt.myguruji.enitity.FacultyDetails;
import com.srdt.myguruji.enitity.StudentDetails;
import com.srdt.myguruji.enitity.StudentEnrollment;
import com.srdt.myguruji.model.BatchInfo;
import com.srdt.myguruji.model.Batches;
import com.srdt.myguruji.model.CommonModelReport;
import com.srdt.myguruji.model.Faculty;
import com.srdt.myguruji.model.ResponceMessage;
import com.srdt.myguruji.model.SearchField;
import com.srdt.myguruji.model.Student;
import com.srdt.myguruji.repository.BatchRepository;
import com.srdt.myguruji.repository.CoursePlanRepository;
import com.srdt.myguruji.repository.FacultyDetailsRepository;
import com.srdt.myguruji.repository.GradeBookFreezeRepo;
import com.srdt.myguruji.utility.Generation;

@RestController
@RequestMapping("/api/batch")
public class BatchResource {
	
	@Autowired
	BatchRepository batchrep;
	@Autowired
	CoursePlanRepository planrep;
	@Autowired
	FacultyDetailsRepository facultyrep;
	@Autowired
	@PersistenceContext
	EntityManager em;
	@Autowired
	GradeBookFreezeRepo freezestatusrepo;
	
	@PostMapping("/syncbatch")
	public List<Batches> syncBatch(@RequestBody List<Batches> batches)
	{
		
		List<BatchDetails> batchDetails = batches.stream()
				                                 .map(x->{
				                                	 CourseDetails courseDetails = planrep.findCourseByCourseId(x.getCourseid());
				                                	 return new BatchDetails(x.getBatchcode(), x.getTitle(), x.getSeq(), x.getDescr(), x.getYear(), x.getType(), courseDetails, x.getCreatedby(),x.getSection());
				                                 })
				                                 .collect(Collectors.toList());
		batchrep.saveAll(batchDetails);
		batchrep.flush();
		batches = batchDetails.stream()
				                     .map(x->{
				                    	 return new Batches(x.getCourseDetails().getCourseId(), x.getBatchId(), x.getBatchtitle(), x.getBatchCode(), x.getDescr(), x.getCreatedBy(), x.getBatchType(), x.getBatchYear(), x.getBatchSeq(),x.getSection());
				                     })
				                     .collect(Collectors.toList());
		return batches;
	}
	
	@PostMapping("/create")
	public ResponceMessage create(@RequestBody List<Batches> batches)
	{		
		int batchcount = (int) batchrep.findBatchCountByCourseId(batches.get(0).getCourseid());
		String year = Generation.getCurrentYear();
		String term = Generation.getTerm();
		CourseDetails courseDetails = planrep.findCourseByCourseId(batches.get(0).getCourseid());
		
		AtomicInteger counter = new AtomicInteger(0);
		List<BatchDetails> batchDetails = batches.stream()
				                                 .map(x->{
				                                	 int seq = batchcount+counter.getAndIncrement();
				                                	 String batchSeq = Integer.toString(seq);
				                                	 
				                                	 if(seq >= 1 && seq <= 9)
				                                	 {
				                                		 batchSeq = "0" + batchSeq;
				                                	 }
				                                	 String code = courseDetails.getCourseCode() + year + term + batchSeq ;
				                                	 System.out.println("code : "+ code);
				                                	 return new BatchDetails(code, x.getTitle(),batchSeq, x.getDescr(), year, x.getType(), courseDetails, x.getCreatedby(),x.getSection()); 
				                                 })
				                                 .collect(Collectors.toList());
		batchrep.saveAll(batchDetails);
		batchrep.flush();
		return new ResponceMessage("Batch Created");
	}
	
	@GetMapping("/getbatches")
	public List<Batches> getBatches()
	{
		return batchrep.findAllBatches();
	}

	@PostMapping("/search")
	public List<Batches> search(@RequestBody SearchField field)
	{
		String id = Long.toString(field.getId());		
		if(field.getId() == 0)
		{
			id="";
		}
		return batchrep.search(id, field.getCode(), field.getTitle());
	}
	
	@GetMapping("/getbatchdetailsbyid/{BatchId}")
	public BatchInfo getBatchDetailsById(@PathVariable("BatchId") long BatchId)
	{
		BatchDetails batchDetailss = batchrep.findBatchDetailsByBatchId(BatchId);
		List<Student> students = null;
		Batches batch = null;
		List<Faculty> faculty = null;
		List<StudentEnrollment> enrollments = null;
		if(batchDetailss != null)
		{
			enrollments = batchDetailss.getStudentEnrollments();
			if(enrollments != null)
			{
				students =  enrollments
			              .stream()
			              .map(x->{
			              StudentDetails studentDetails = x.getStudent();
			               	  return new Student(studentDetails.getStudentId(), studentDetails.getEmplid(), studentDetails.getApplNbr(), studentDetails.getCampusId(), studentDetails.getFirstName(), studentDetails.getMidleName(), studentDetails.getLastName(), studentDetails.getFullName(), studentDetails.getDateOfBirth(), studentDetails.getEmailAddr(), studentDetails.getPrimaryContact(), studentDetails.getSecondaryContact(), studentDetails.getCreatedBy());
			              })
			              .collect(Collectors.toList());
			}
			
			faculty = batchDetailss.getFacultyTaggingDetails().stream()
					         .map(x->{
					        	 FacultyDetails facultyDetails = x.getFacultyDetails();
					        	 return new Faculty(facultyDetails.getFacultyId(), facultyDetails.getFacultyCode(), facultyDetails.getEmplid(), facultyDetails.getDesignation(), facultyDetails.getPref(), facultyDetails.getFirstName(), facultyDetails.getMidleName(), facultyDetails.getLastName(), facultyDetails.getFullName(), facultyDetails.getEmailAddr(), facultyDetails.getPrimaryContact(), facultyDetails.getSecondaryContact(), facultyDetails.getCreatedBy());
					         })
					         .collect(Collectors.toList());
			
			
			
			batch = new Batches(batchDetailss.getCourseDetails().getCourseId(), batchDetailss.getBatchId(), batchDetailss.getBatchtitle(), batchDetailss.getBatchCode(), batchDetailss.getDescr(), batchDetailss.getCreatedBy(), batchDetailss.getBatchType(), batchDetailss.getBatchYear(), batchDetailss.getBatchSeq(),batchDetailss.getSection());
			
		}
		
		return new BatchInfo(batch, faculty, students);
	}
	
	@GetMapping("/getbatchbycourseid/{CourseId}")
	public List<Batches> findBatchByCourseId(@PathVariable("CourseId") long CourseId)
	{
		return batchrep.findBatchByCourseId(CourseId);
	}
	
	@GetMapping("/getnotmapbatchbycourseid/{CourseId}/{emplid}")
	public List<Batches> findNotMapBatchByCourseId(@PathVariable("CourseId") long CourseId,@PathVariable("emplid") String emplid)
	{
		return batchrep.findNotMapBatchByCourseId(CourseId,emplid);
	}
	
	@GetMapping("getBatchDetailsbyBatchCode/{batchcode}")
	public CommonModelReport  getBatchDetails(@PathVariable String batchcode)
	{
		BatchDetails batch=batchrep.getBatchByBatchCode(batchcode);
		Query qry=em.createNamedQuery("FacultyTaggingDetails.getFacultyIds").setParameter("batchid",batch.getBatchId());
		List<Long> facultyid=qry.getResultList();
		List<String> facultyNamelist=facultyid.stream().map(x->{return facultyrep.findByFacultyId(x).getFullName();	}).collect(Collectors.toList());
		List<String> facultyErpIdlist=facultyid.stream().map(x->{return facultyrep.findByFacultyId(x).getEmplid();	}).collect(Collectors.toList());
		String facultyName=String.join(",",facultyNamelist);
		String facultyErpId=String.join(",",facultyErpIdlist);
		String pushstatus = freezestatusrepo.findPushStatus(batch.getBatchId(),batch.getCourseDetails().getCourseId());
		return new CommonModelReport(String.valueOf(batch.getBatchId()),batch.getBatchtitle(),batch.getBatchType()
				,batch.getCourseDetails().getCourseDescr(),facultyErpId,facultyName
				,batch.getBatchYear().substring(0,2)+batch.getBatchSeq(),pushstatus);
	}
}
