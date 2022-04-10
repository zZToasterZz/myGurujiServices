package com.srdt.myguruji.resource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.srdt.myguruji.enitity.*;
import com.srdt.myguruji.model.*;
import com.srdt.myguruji.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srdt.myguruji.utility.Generation;

@RestController
@RequestMapping("/api/assignment")
@Transactional
public class AssignmentResource
{
	@Autowired
	CourseDetailsRepository crscrep;
	
	@Autowired
	AssignmentDetailsRepository assignmentDetailsRepo;
	
	@Autowired
	CoursePlanRepository crsplanrepo;

	@Autowired
	UnitContentRepository untcntrep;

	@Autowired
	StudentContentDowloadRepo contentdownloadrepo;
	
	@Autowired
	BatchRepository batchrepo;
	
	@Autowired
	AssignmentPlanMappingRepository planrepo;
	
	@Autowired
	UnitDetailsRepository unitrepo;
	
	@Autowired
	TopicDetailsRepository topicrepo;
	
	@Autowired
	AssignmentQuestionRepository quesrepo;

	@Autowired
	StudentDetailsResource studentresrc;
	
	@Autowired
	AssignmentStudentMapRepository studentmaprepo;
	
	@Autowired
	AssignmentUnitMappingRepository assignunitrepo;
	
	@Autowired
	QuestionDetailsRepository questionDtlRepo;
	
	@Autowired
	StudentDetailsRepository studrepo;
	@Autowired
	AssignmentResponseRepository asgnRespRepo;
	
	@Autowired
	AssignmentAttachmentRepository attachmentrepo;
	
	@Autowired
	AssignmentSubmissionStatusRepository submissionrepo;
	
	@Autowired
	@PersistenceContext
	EntityManager em;
	
	@PostMapping("/createassignment")
	public ResponceMessage createAssignment(@RequestBody Assignment assgn)
	{
		long id = 0;
		try
		{
//			System.out.println("DATE1 : "+assgn.getDueDate());
//			System.out.println("DATE2 : "+Generation.toDate(assgn.getDueDate(), "yyyy-MM-dd HH:mm:ss"));
			if(assgn.getAssignmentDescr().trim().length()==0 ||assgn.getAssignmentDescr()==null)
				return new ResponceMessage("ERROR:::::::Description");
			
			if(( Generation.toDate(assgn.getDueDate(), "yyyy-MM-dd HH:mm:ss").before(Generation.getCurrentDate())))
				return new ResponceMessage("ERROR::::::::::PastDate");
			
			
			AssignmentDetails assgnDtl = new AssignmentDetails(assgn.getAssignmentID(), assgn.getAssignmentTitle(), assgn.getAssignmentDescr(), assgn.getAssignmentType(), Generation.toDate(assgn.getDueDate(), "yyyy-MM-dd HH:mm:ss"), assgn.getHasPlan(), assgn.getHasUnit(), assgn.getHasQuestion(), assgn.getIsPublished(), assgn.getMaxMarks(), crscrep.findCourseDetailsByCourseId(assgn.getCourseID()));
			assgnDtl.setCreatedBy(assgn.getCreatedby());
			assgnDtl.setModifiedBy(assgn.getCreatedby());
			assgnDtl.setLevel(assgn.getLevel());
			
			List<AssignmentPlanMapping> planlist=new ArrayList<>();
			for(AssignmentPlan plan:assgn.getAssignplan()) {
				assgnDtl.setHasPlan("Y");
				plan.setAssignmentid(assignmentDetailsRepo.save(assgnDtl).getAssignmentID());	
				if(plan.getUnitlist().size()>0)
					assgnDtl.setHasUnit("Y");
				planlist.add(createAssignmentMapping(plan));
			}
			assgnDtl.setAssignmentplan(planlist);
			
			
			id = assignmentDetailsRepo.save(assgnDtl).getAssignmentID();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return new ResponceMessage("Error::::"+e.getMessage());
		}
		return new ResponceMessage("success : "+id);
	}
	
	public AssignmentPlanMapping createAssignmentMapping(@RequestBody AssignmentPlan assignplan)
	{
		CoursePlan cplan=crsplanrepo.getCoursePlanById(assignplan.getCourseplanid());
		//System.out.println(cplan.getCoursePlanId());
		AssignmentDetails assignment=assignmentDetailsRepo.getAssignmentByAssignId(assignplan.getAssignmentid());
		//System.out.println(assignment.getAssignmentID());
		CourseDetails courseDetails = crscrep.findCourseDetailsByCourseId(assignplan.getCourseid()); 
		//System.out.println(courseDetails.getCourseId());
		BatchDetails batch=batchrepo.findBatchByBatchId(assignplan.getBatchid());
		//System.out.println(batch.getBatchId());
		
		AssignmentPlanMapping assignmentplan=new AssignmentPlanMapping(0, courseDetails, batch, cplan, assignment);
		assignmentplan.setModifiedBy(assignment.getCreatedBy());
		assignmentplan.setCreatedBy(assignment.getCreatedBy());
		List<AssignmentUnitMapping> assignunit = assignplan.getUnitlist().stream()
                .map(x->{
                	AssignmentUnitMapping unitmap= new AssignmentUnitMapping(x.getAssignmentunitid(),x.getTopics(),x.getTopicsids(),assignment,courseDetails,batch,cplan,assignmentplan);
                	unitmap.setUnit(unitrepo.findUnitByUnitId(x.getUnitid()));
                	unitmap.setModifiedBy(assignment.getCreatedBy());
                	unitmap.setCreatedBy(assignment.getCreatedBy());
                	return unitmap;
                })
                .collect(Collectors.toList());
		
		assignmentplan.setAssignmentunit(assignunit);
		planrepo.save(assignmentplan);
		
		return assignmentplan;
		
	}
	
	@GetMapping("/getAssignmentByEmplIdandCourseId/{emplId}/{courseId}")
	public List<Assignment> getAssignmentsByEmpId(@PathVariable String emplId,@PathVariable long courseId)
	{
		//List<AssignmentDetails> assignment=assignmentDetailsRepo.getAssignmentByEmpId(emplId, courseId);
		return assignmentDetailsRepo.getAssignmentByEmpId(emplId, courseId).stream()
	              .map(x->
	              {
	            	  List<AssignmentPlan> plan = x.getAssignmentplan().stream()
	            			                              .map(y->{
	            			                            	  
	            			                            	  AssignmentPlan asplan= new AssignmentPlan(y.getAssignmentPlanMappingID(), y.getCourseDetails().getCourseId(), y.getBatchDetails().getBatchId(), y.getAssignmentDetails().getAssignmentID(), y.getCoursePlan().getCoursePlanId());
	            			                            	  asplan.setUnitlist(y.getAssignmentunit().stream().map(
	            			                            			  z->{
	            			                            				  return new AssignmentUnit(z.getCourseDetails().getCourseId(),z.getBatchDetails().getBatchId(),z.getAssignmentDetails().getAssignmentID(),z.getCoursePlan().getCoursePlanId(),z.getAssignmentUnitID(),z.getTopics(),z.getTopicIDs());
	            			                            			  } ).collect(Collectors.toList()));	
	            			                            	  return asplan;
	            			                              }).collect(Collectors.toList());            	 
   
	            	  Assignment assgnmnt= new Assignment(x.getCourseDetails().getCourseId(), x.getAssignmentID(), x.getAssignmentTitle(), x.getAssignmentDescr(), x.getAssignmentType(),Generation.dateAsString(x.getDueDate()), x.getHasPlan(), x.getHasUnit(), x.getHasQuestion(), x.getIsPublished(), x.getMaxMarks(), x.getCreatedBy(), x.getLevel());
	            	  assgnmnt.setAssignplan(plan);
	            	  return assgnmnt;
	              })
	              .collect(Collectors.toList());
	}
	
	@GetMapping("/getAssignmentByCourseplanID/{courseplanId}")
	public List<Assignment> getAssignmentByCourseplanId(@PathVariable long courseplanId)
	{
		List<AssignmentPlanMapping> assignmap=planrepo.getAssignmentbyCourseplanID(courseplanId);
		 return assignmap.stream().map(x->{
					return new Assignment(x.getCourseDetails().getCourseId(), x.getAssignmentDetails().getAssignmentID(), x.getAssignmentDetails().getAssignmentTitle(), x.getAssignmentDetails().getAssignmentDescr(), x.getAssignmentDetails().getAssignmentType(), Generation.dateAsString(x.getAssignmentDetails().getDueDate()), x.getAssignmentDetails().getHasPlan(), x.getAssignmentDetails().getHasUnit(), x.getAssignmentDetails().getHasQuestion(), x.getAssignmentDetails().getIsPublished(), x.getAssignmentDetails().getMaxMarks());
						}).collect(Collectors.toList());
	
	}
	
	@PostMapping("/createAssignmentQuestion")
	public ResponceMessage addAssignmentQuestion(@RequestBody List<AssignmentQuest> assgnques)
	{
		List<AssignmentQuestions> aq=new ArrayList<>();
		double maxmarks=0.0;
		AssignmentDetails assignment=assignmentDetailsRepo.getAssignmentByAssignId(assgnques.get(0).getAssignmentid());
		for(AssignmentQuest ques:assgnques)
		{
			
			CourseDetails courseDetails = crscrep.findCourseDetailsByCourseId(ques.getCourseid()); 
			QuestionDetails quesdetail=em.find(QuestionDetails.class, ques.getQuestionid());			
			AssignmentQuestions question=new AssignmentQuestions(0, ques.getMarks(), assignment, courseDetails, quesdetail,ques.getCreatedby());
			aq.add(question);	
			maxmarks+=ques.getMarks();
		}
		try {
		quesrepo.saveAll(aq);
		assignment.setMaxMarks(maxmarks);
		assignment.setHasQuestion("Y");
		assignmentDetailsRepo.save(assignment);	
		}
		catch(Exception e)
		{
			assignment.setHasQuestion("N");
			return new ResponceMessage("Error::::"+e.getMessage());
		}
		
		return new ResponceMessage("Success");
	}
	
	@PostMapping("/addStudentAssignmentMapp")
	public ResponceMessage addStudentAssignmentMapp(@RequestBody List<AssignmentStudent> studentmap)
	{
		List<AssignmentStudentMapping> student=new ArrayList<>();
		for(AssignmentStudent asnstudent:studentmap)
		{
			CoursePlan cplan=crsplanrepo.getCoursePlanById(asnstudent.getCourseplanid());
			BatchDetails batch=batchrepo.findBatchByBatchId(asnstudent.getBatchid());
			AssignmentDetails assignment=assignmentDetailsRepo.getAssignmentByAssignId(asnstudent.getAssignmentid());
			CourseDetails courseDetails = crscrep.findCourseDetailsByCourseId(asnstudent.getCourseid()); 
			//long studenId=studrepo.findStudentidbyEmplid(asnstudent.getEmplid());
			long studentId=Long.parseLong(asnstudent.getEmplid());
			StudentDetails stdetail=em.find(StudentDetails.class, studentId);
			
			AssignmentPlanMapping planmap=(AssignmentPlanMapping)em.createQuery("select ap from AssignmentPlanMapping ap where ap.assignmentDetails.assignmentID= :assessId")
					.setParameter("assessId", assignment.getAssignmentID()).getSingleResult();
			student.add(new AssignmentStudentMapping(0, "Y", assignment, planmap, courseDetails, batch, cplan, stdetail,asnstudent.getCreatedby()));
		}
		
		AssignmentDetails assign=assignmentDetailsRepo.getAssignmentByAssignId(studentmap.get(0).getAssignmentid());
		try {
		studentmaprepo.saveAll(student);		
		assign.setDescr1("Y");
		assignmentDetailsRepo.save(assign);
		}
		catch(Exception e)
		{
			assign.setDescr1("Y");
			return new ResponceMessage("Error::::"+e.getMessage());
		}
		return new ResponceMessage("Success");
	}
	
	@GetMapping("/getUnitAssignmentCount/{courseplanid}/{emplId}/{unitId}")
	public ResponceMessage getunitassignmentcount(@PathVariable long courseplanid,@PathVariable String emplId,@PathVariable long unitId) 
	{
		List<Long> assignIds=assignunitrepo.getAssignmentIds(unitId);
		assignIds.stream().forEach(System.out::println);
		long studentId=studrepo.findStudentidbyEmplid(emplId);
		return new ResponceMessage(String.valueOf(studentmaprepo.getUnitAssignmentCount(courseplanid, studentId,assignIds)));
	}

	@GetMapping("/getAssignmentCountForFaculty/{courseplanid}/{emplId}/{unitId}")
	public ResponceMessage getassignmentcountforFaculty(@PathVariable long courseplanid,@PathVariable String emplId,@PathVariable long unitId)
	{
		if(unitId==0)
			return new ResponceMessage(String.valueOf(assignunitrepo.getFacultyAssignmentCountforCourseplan(emplId,courseplanid)));
		else
			return new ResponceMessage(String.valueOf(assignunitrepo.getFacultyAssignmentCountforUnit(emplId,courseplanid,unitId)));
	}
	
	@GetMapping("getCourseAssignmentCount/{courseplanid}/{emplId}")
	public ResponceMessage getCourseAssignmentcount(@PathVariable long courseplanid,@PathVariable String emplId) 
	{
		long studentId=studrepo.findStudentidbyEmplid(emplId);
		return new ResponceMessage(String.valueOf(studentmaprepo.getCoursewiseAssignmentCount(courseplanid, studentId)));
	}
	
	@GetMapping("/getAssignmentDetail/{levl}/{createdby}/{courseplanId}/{unitID}")
	public List<Assignment> getAssigmentDetails(@PathVariable String levl,@PathVariable String createdby,@PathVariable long courseplanId,@PathVariable String unitID)
	{
		List<Assignment> assignlist=new ArrayList<>();
		List<Long> assiggnmentid=new ArrayList<>();

		if(unitID.equals("-"))
		{
			List<AssignmentUnitMapping> list= assignunitrepo.getAssignmentDetail(levl,createdby,courseplanId);
			List<Long> asids=new ArrayList<>();
			asids.add(new Long(0));
			for(AssignmentUnitMapping am:list)
			{
				if(asids.contains(am.getAssignmentDetails().getAssignmentID()))
					continue;
				else
					asids.add(am.getAssignmentDetails().getAssignmentID());
			}
			List<AssignmentDetails> aslist=em.createQuery("select ad from AssignmentDetails ad where ad.assignmentID in (:idss)")
			.setParameter("idss",asids).getResultList();
			assignlist=aslist.stream().map(x->{
				//AssignmentDetails x=y.getAssignmentDetails();
				System.out.println("ID:::"+x.getAssignmentID());
				AssignmentPlanMapping plan=planrepo.getAssignmentplanbyAssignmentId(x.getAssignmentID());
				Assignment assgn= new Assignment(x.getCourseDetails().getCourseId(), x.getAssignmentID(), x.getAssignmentTitle(), x.getAssignmentDescr(), x.getAssignmentType(), Generation.dateAsString(x.getDueDate()), x.getHasPlan(), x.getHasUnit(), x.getHasQuestion(), x.getIsPublished(), x.getMaxMarks(),x.getCreatedBy(),x.getLevel());
				assgn.setHasStudent(x.getDescr1());
				AssignmentPlan asplan=new AssignmentPlan(plan.getAssignmentPlanMappingID(),plan.getCourseDetails().getCourseId(),plan.getBatchDetails().getBatchId(),plan.getAssignmentDetails().getAssignmentID(),plan.getCoursePlan().getCoursePlanId());
				//asplan.setUnitlist(Arrays.asList(new AssignmentUnit(y.getCourseDetails().getCourseId(),y.getBatchDetails().getBatchId(), y.getAssignmentDetails().getAssignmentID(),y.getCoursePlan().getCoursePlanId(), y.getAssignmentUnitID(),y.getTopics(), y.getTopicIDs())));
				assgn.setAssignplan(Arrays.asList(asplan));
				return assgn;
					}).collect(Collectors.toList());
		}
		else 
		{
			//UnitDetails unitdt=em.find(UnitDetails.class, Long.parseLong(unitID));
			List<Long> assignmentids=assignunitrepo.getAssignmentIds(Long.parseLong(unitID));
			List<Long> assignid=new ArrayList<>();
			for(Long ids:assignmentids)
			{
				//System.out.println("Above if IDS:::"+ids);
				if((assignid.contains(ids)))
					continue;
				else{
					//System.out.println("IDS:::"+ids);
					assignid.add(ids);
				}
			}

			List<AssignmentUnitMapping> unitlist= assignunitrepo.getAssignmentDetailsforUnit(createdby,courseplanId,assignid);
			assignlist=unitlist.stream().map(y->{
				AssignmentDetails x=y.getAssignmentDetails();
				Assignment assgn= new Assignment(x.getCourseDetails().getCourseId(), x.getAssignmentID(), x.getAssignmentTitle(), x.getAssignmentDescr(), x.getAssignmentType(), Generation.dateAsString(x.getDueDate()), x.getHasPlan(), x.getHasUnit(), x.getHasQuestion(), x.getIsPublished(), x.getMaxMarks(),x.getCreatedBy(),x.getLevel());
				assgn.setHasStudent(x.getDescr1());
				AssignmentPlanMapping plan= y.getAssignmentPlanMapping();

				AssignmentPlan asplan=new AssignmentPlan(plan.getAssignmentPlanMappingID(),plan.getCourseDetails().getCourseId(),plan.getBatchDetails().getBatchId(),plan.getAssignmentDetails().getAssignmentID(),plan.getCoursePlan().getCoursePlanId());
				asplan.setUnitlist(Arrays.asList(new AssignmentUnit(y.getCourseDetails().getCourseId(),y.getBatchDetails().getBatchId(), y.getAssignmentDetails().getAssignmentID(),y.getCoursePlan().getCoursePlanId(), y.getAssignmentUnitID(),y.getTopics(), y.getTopicIDs())));
				assgn.setAssignplan(Arrays.asList(asplan));
				return assgn;		
			}).collect(Collectors.toList());
		}
	
		return assignlist;
		
	}

	@PostMapping("/publishAssignment/{assignmentId}")
	public ResponceMessage publishAssignmentforStudent(@PathVariable long assignmentId) 
	{
		AssignmentDetails assignment=em.find(AssignmentDetails.class, assignmentId);
		try {
		if(assignment.getHasPlan().equalsIgnoreCase("Y") && assignment.getHasUnit().equalsIgnoreCase("Y") && 
				assignment.getHasQuestion().equalsIgnoreCase("Y") && assignment.getDescr1().equalsIgnoreCase("Y"))
		{
			assignment.setIsPublished("Y");
			em.merge(assignment);		
		}
		}
		catch(Exception e)
		{
			return new ResponceMessage("ERROR:::::::"+e.getMessage());
		}
		return new ResponceMessage("Success");
	}



	@PostMapping("/saveassignmentresponse")
	public ResponceMessage saveResponses(@RequestBody List<AssignmentResponseModel> response)
	{
		List<AssignmentResponse> studentresponse=new ArrayList<>();
		for(AssignmentResponseModel res:response)
		{
			List<AssignmentAttachments> attch =new ArrayList<>();

			AssignmentResponse asgnRes = new AssignmentResponse(res.getAssignmentResponseID(), res.getObjectiveResponse(), res.getSubjectiveResponse(), res.getHasAttachment(), 0.0, studrepo.findByStudentId(res.getStudentid()), questionDtlRepo.findQuestionDetailsById(res.getQuestionid()), assignmentDetailsRepo.findAssignmentById(res.getAssignmentid()));

			/******** code to delete existing attachment after updating assignment response with attachment******/
			if(!(res.getAttachments()==null) )
			{
				List<Long> ids=new ArrayList<>();
				for(AssignmentAttachmentModel aam:res.getAttachments())
					ids.add(aam.getAssignmentAttachmentID());

				attachmentrepo.deleteStudentAttachments(res.getStudentid(), res.getAssignmentid(), res.getQuestionid(), ids);

				attch = res.getAttachments().stream().map(x -> {

					AssignmentAttachments attachment= new AssignmentAttachments(x.getAssignmentAttachmentID(), x.getOriginalFileName(), x.getGeneratedFileName(), x.getFileExtension(), x.getFilePath(), asgnRespRepo.findByResponseId(x.getAssignmentResponseID()), studrepo.findByStudentId(res.getStudentid()), questionDtlRepo.findQuestionDetailsById(res.getQuestionid()), assignmentDetailsRepo.findAssignmentById(res.getAssignmentid()));
					attachment.setAssignmentResponse(asgnRes);
					return attachment;
				}).collect(Collectors.toList());

			}
			asgnRes.setAttachments(attch);
			studentresponse.add(asgnRes);
		}
		AssignmentDetails assgnmnt= assignmentDetailsRepo.findAssignmentById(response.get(0).getAssignmentid());
		String timingStatus="";
		try {
			asgnRespRepo.saveAll(studentresponse);

			em.createNamedQuery("AssignmentDetails.setAttachmentstatus").setParameter("assignId",assgnmnt.getAssignmentID()).executeUpdate();

			if(assgnmnt.getDueDate().before(Generation.getCurrentDate()))
				timingStatus="L";
			else
				timingStatus="T";

			AssignmentStatusDetails assignmentstatus=new AssignmentStatusDetails();
			AssignmentStatusDetails statusdetail=submissionrepo.getExistingStudentStatus(assgnmnt.getAssignmentID(),response.get(0).getStudentid());
			if(statusdetail==null)
				assignmentstatus=new AssignmentStatusDetails(0, timingStatus,response.get(0).getResponsestatus() , "N", assgnmnt, studrepo.findByStudentId(response.get(0).getStudentid()));
			else {
				assignmentstatus.setAssignmentStatusID(statusdetail.getAssignmentStatusID());
				assignmentstatus.setEvaluationStatus(statusdetail.getEvaluationStatus());
				assignmentstatus.setStudentDetails(statusdetail.getStudentDetails());
				assignmentstatus.setSubmitStatus(response.get(0).getResponsestatus());
				assignmentstatus.setTimingStatus(statusdetail.getTimingStatus());
				assignmentstatus.setAssignmentDetails(statusdetail.getAssignmentDetails());
			}
			//assignmentstatus=new AssignmentStatusDetails(statusdetail.getAssignmentStatusID(), timingStatus,response.get(0).getResponsestatus() , "N", assgnmnt, studrepo.findByStudentId(response.get(0).getStudentid()));
			assignmentstatus.setCreatedBy(assgnmnt.getCreatedBy());
			assignmentstatus.setModifiedBy(assgnmnt.getCreatedBy());
			em.merge(assignmentstatus);

		}
		catch(Exception e)
		{
			return new ResponceMessage("ERROR:::::::"+e.getMessage());
		}

		/************Method call only for assignment marks calculation****************/

//		StudentDetails student= studrepo.findByStudentId(response.get(0).getStudentid());
//		if(timingStatus.equalsIgnoreCase("T"))
//			setAssignmentCpmarks(assgnmnt,student);

		return new ResponceMessage("Success");
	}

	 void setAssignmentCpmarks(AssignmentDetails assignment,StudentDetails student)
	 {
	 	double  marks=0.0;
		 StudentContentDowloadStatus stdownloadstatus=null;
		if(assignment.getLevel().equalsIgnoreCase("U"))
		{
			AssignmentUnitMapping assignmentunit=(AssignmentUnitMapping)em.createQuery("select asm from AssignmentUnitMapping asm where asm.assignmentDetails.assignmentID=:asgnId")
					.setParameter("asgnId", assignment.getAssignmentID()).getSingleResult();

			marks=setmarks(assignmentunit,student);
			stdownloadstatus=new StudentContentDowloadStatus(0, Generation.getCurrentDate(),null, "yes", marks,"assignment",
					assignmentunit.getCourseDetails(),assignmentunit.getBatchDetails(),student, assignmentunit.getCoursePlan(),assignmentunit.getUnit(),null," ");
		}
		else
		{
			List<AssignmentUnitMapping> assignmentunitmap=em.createQuery("select asm from AssignmentUnitMapping asm where asm.assignmentDetails.assignmentID=:asgnId")
					.setParameter("asgnId", assignment.getAssignmentID()).getResultList();

			for(AssignmentUnitMapping assignmentunit:assignmentunitmap)
			{
				marks+=setmarks(assignmentunit,student);
			}

			stdownloadstatus=new StudentContentDowloadStatus(0, Generation.getCurrentDate(),null, "yes", marks,"assignment",
					assignmentunitmap.get(0).getCourseDetails(),assignmentunitmap.get(0).getBatchDetails(),student, assignmentunitmap.get(0).getCoursePlan(),null,null,"");

		}


		 stdownloadstatus.setModifiedBy(student.getEmplid());
		 stdownloadstatus.setCreatedBy(student.getEmplid());
		 contentdownloadrepo.save(stdownloadstatus);



	 }

	 double setmarks(AssignmentUnitMapping assignmentunit,StudentDetails student)
	 {
		 double marks=0.0;

		 final Calendar cal = Calendar.getInstance();
		 cal.add(Calendar.DATE, -1);
		 System.out.println("Date:"+ cal.getTime());
		 Date prvs=cal.getTime();
		 String previousdate=Generation.dateAsString(cal.getTime());

		 long docCount=(Long)em.createQuery("select count(uc.Title) from UnitContent uc where uc.courseDetails.CourseId=:crsid and uc.unitDetails.UnitId=:unitid and uc.unitDetails.coursePlan.CoursePlanId=:crsplanid and uc.contentType.TypeId=1 and uc.CreatedDate>=:prvsdate")
				 .setParameter("crsid",assignmentunit.getCourseDetails().getCourseId()).setParameter("unitid",assignmentunit.getUnit().getUnitId()).setParameter("crsplanid",assignmentunit.getCoursePlan().getCoursePlanId())
				 .setParameter("prvsdate",prvs).getSingleResult();
		 long vidCount=untcntrep.getVideoUploadCount(assignmentunit.getCourseDetails().getCourseId(),assignmentunit.getUnit().getUnitId(),assignmentunit.getCoursePlan().getCoursePlanId(),prvs);

		 if(docCount==0 && vidCount==0)
		 {
			 /***** Query to get count of student assignment at course level*****/
			 Query query=em.createNativeQuery("select count(a.assignmentid) " +
					 " from assignment_student_mapping a left join assignment_details b on a.assignmentid=b.assignmentid " +
					 " where a.student_id=:stid and a.course_id=:crsid and a.course_plan_id=:crsplanid and b.level='C'")
					 .setParameter("stid",student.getStudentId())
					 .setParameter("crsid",assignmentunit.getCourseDetails().getCourseId())
					 .setParameter("crsplanid",assignmentunit.getCoursePlan().getCoursePlanId());

			 long courseassignmentcount=new BigInteger(String.valueOf(query.getSingleResult())).longValue();

			 /***** Query to get count of student assignment at unit level*****/
			 Query query1=em.createNativeQuery("select count(a.assignmentid) from assignment_student_mapping a\n" +
					 "left join assignment_details b on a.assignmentid=b.assignmentid left join assignment_unit_mapping c on a.assignmentid=c.assignmentid\n" +
					 "where a.student_id=:stid and a.course_id=:crsid and a.course_plan_id=:crsplanid and b.level='U' and c.unit_id=:unitid")
					 .setParameter("stid",student.getStudentId())
					 .setParameter("crsid",assignmentunit.getCourseDetails().getCourseId())
					 .setParameter("crsplanid",assignmentunit.getCoursePlan().getCoursePlanId())
					 .setParameter("unitid",assignmentunit.getUnit().getUnitId());

			 long unitassignmentcount=new BigInteger(String.valueOf(query1.getSingleResult())).longValue();

			 long studentdocdownload=contentdownloadrepo.getDocDownloadCount(assignmentunit.getBatchDetails().getBatchId(),assignmentunit.getCoursePlan().getCoursePlanId(),assignmentunit.getUnit().getUnitId(),student.getStudentId());
			 long studentvidDownlod=contentdownloadrepo.getVideoDownloadCount(assignmentunit.getBatchDetails().getBatchId(),assignmentunit.getCoursePlan().getCoursePlanId(),assignmentunit.getUnit().getUnitId(),student.getStudentId());

			 Query stquery=em.createNativeQuery("SELECT count(um.assignmentid) FROM assignment_status_details sd left join assignment_unit_mapping um on sd.assignmentid=um.assignmentid " +
					 "left join assignment_details ad on ad. assignmentid=um.assignmentid where sd.student_id=:stid and um.unit_id=:unitid and sd.timing_status='T' and ad.level=:lvl")
					 .setParameter("stid", student.getStudentId())
					 .setParameter("unitid",assignmentunit.getUnit().getUnitId());
			 stquery.setParameter("lvl","C");
			 long studentcourseassignmentcount=new BigInteger(String.valueOf(stquery.getSingleResult())).longValue();

			 /*******unit level assignment count********/
			 stquery.setParameter("lvl","U");
			 long studentuntassignmentcount=new BigInteger(String.valueOf(stquery.getSingleResult())).longValue();

			 //long studentassignmentcount=assignmentstatusrepo.getAssignmentCountforStudentId(downloadstatus.getStudentid(),downloadstatus.getUnitid());

			 double studentassignmentcount=(studentcourseassignmentcount/(double)courseassignmentcount)+(studentuntassignmentcount/(double)unitassignmentcount);

			 Double videocheck=new Double(studentvidDownlod / (double)vidCount);
			 Double doccheck=new Double(studentdocdownload /(double) docCount);
			 Double courseassignment=new Double(studentcourseassignmentcount/(double)courseassignmentcount);
			 Double unitassignment=new Double(studentuntassignmentcount/(double)unitassignmentcount);

			 Long count=(Long)em.createQuery("select count(ud) from UnitDetails ud where ud.coursePlan.CoursePlanId=:crsplanid")
					 .setParameter("crsplanid",assignmentunit.getCoursePlan().getCoursePlanId()).getSingleResult();

			 double assignmentmarks=0.0;

			 double cpu=0.0;
			 if(count==2)
				 cpu=2.5;
			 else if(count==3)
				 cpu=1.67;
			 else if(count==4)
				 cpu=1.25;
			 else
			 if(count==5)
				 cpu=1.0;
			 double questionassignment=0.4;

			 if(courseassignment.isNaN())
				 courseassignment=0.0;

			 if(unitassignment.isNaN())
				 unitassignment=0.0;

			 studentassignmentcount=courseassignment+unitassignment;
			 assignmentmarks=  (cpu * studentassignmentcount * questionassignment);
			 System.out.println("Assignment marks:::"+assignmentmarks);

			 marks =  assignmentmarks;


		 }


//		 System.out.println("Upload doc::::"+docCount);
//		 System.out.println("Upload video::::"+vidCount);
//		 System.out.println("Upload  Course Assigment::::"+courseassignmentcount);
//		 System.out.println("Upload  Unit  Assigment::::"+unitassignmentcount);
//		 System.out.println("Course Assignment count:"+studentcourseassignmentcount);
//
//		 System.out.println("/************************DOWNLOAD*****************************/");
//		 System.out.println("download doc::::"+studentdocdownload);
//		 System.out.println("download video::::"+studentvidDownlod);
//		 System.out.println("done Assigment::::"+studentassignmentcount);
//
//		 System.out.println("/************************DOWNLOAD Status after updation*****************************/");
//		 System.out.println("download doc::::"+studentdocdownload);
//		 System.out.println("download video::::"+studentvidDownlod);
//		 System.out.println("done Assigment::::"+studentassignmentcount);
//
//		 System.out.println("Course Assignment count:"+studentcourseassignmentcount);
//		 System.out.println("Unit Assignment count:"+studentuntassignmentcount);
//
//		 System.out.println("UNIT COUNT:::::::::"+count);
//		 System.out.println("Unit ID::::"+assignmentunit.getUnit().getUnitId());
//
//		 System.out.println("MArks:::::::"+marks);
		 return marks;

	 }


	@GetMapping("/getAssignmentdetailbyId/{assignmentId}")
	public Assignment getAssignmentdetailbyId(@PathVariable long assignmentId)
	{
		AssignmentDetails assignment=assignmentDetailsRepo.findAssignmentById(assignmentId);
		
		Assignment assign= new Assignment(assignment.getCourseDetails().getCourseId(), assignment.getAssignmentID(), assignment.getAssignmentTitle(), assignment.getAssignmentDescr(), assignment.getAssignmentType(), Generation.dateAsString(assignment.getDueDate()), assignment.getHasPlan(), assignment.getHasUnit(), assignment.getHasQuestion(), assignment.getIsPublished(), assignment.getMaxMarks(), assignment.getCreatedBy(), assignment.getLevel());
		assign.setHasStudent(assignment.getDescr1());
		List<AssignmentPlan> assignplan =new ArrayList<>();
		try {
			assignplan=assignment.getAssignmentplan().stream().map(y->{
			AssignmentPlan asgnplan=new AssignmentPlan(y.getAssignmentPlanMappingID(), y.getCourseDetails().getCourseId(), y.getBatchDetails().getBatchId(), y.getAssignmentDetails().getAssignmentID(), y.getCourseDetails().getCourseId());
			//System.out.println(asgnplan.getAssignmentplanid());
			List<AssignmentUnit> asgnunit=y.getAssignmentunit().stream().map(z->{
				//System.out.println(z.getAssignmentUnitID());
				AssignmentUnit unitdetail= new AssignmentUnit(y.getCourseDetails().getCourseId(),y.getBatchDetails().getBatchId(), y.getAssignmentDetails().getAssignmentID(), y.getCoursePlan().getCoursePlanId(), z.getAssignmentUnitID(), z.getTopics(), z.getTopicIDs());
			unitdetail.setUnitid(z.getUnit().getUnitId());
			return unitdetail;
			}).collect(Collectors.toList());
			
			asgnplan.setUnitlist(asgnunit);
			return asgnplan;
		}).collect(Collectors.toList());
		}
		catch(Exception e)
		{
			assignplan =new ArrayList<>();
		}
		assign.setAssignplan(assignplan);
		List<AssignmentQuestions> questionlist=quesrepo.getAssignmentquestionByAssignId(assignmentId);
		List<AddedQuestion> queslist=questionlist.stream().map(x->{
			QuestionDetails q=x.getQuestionDetails();
			
//			AddedQuestion addedques= new AddedQuestion(0, q.getQuestionId(), x.getAssignmentQuestionID(), new BigDecimal(x.getMarks(),MathContext.DECIMAL64), "N", 
//					new BigDecimal(0,MathContext.DECIMAL64), 0.0, q.getQuestionText(), q.getQuestionType().getType(), "N", q.getAnswerDetails().getOpt1(), 
//					q.getAnswerDetails().getOpt2(), q.getAnswerDetails().getOpt3(), q.getAnswerDetails().getOpt4(), null,"", q.getAnswerDetails().getCurrectOpt(),
//					q.getBlmTaxonomy(), q.getDifficultyLevel().getDiffLevel());
		
			AddedQuestion addedques=null;
			if(q.getQuestionType().getType().equalsIgnoreCase("SCQ")||q.getQuestionType().getType().equalsIgnoreCase("MCQ")||q.getQuestionType().getType().equalsIgnoreCase("TNF")) {
				addedques= new AddedQuestion(0, q.getQuestionId(), x.getAssignmentQuestionID(), new BigDecimal(x.getMarks(),MathContext.DECIMAL64), "N", 
						new BigDecimal(0,MathContext.DECIMAL64), 0.0, q.getQuestionText(), q.getQuestionType().getType(), "N", q.getAnswerDetails().getOpt1(), 
						q.getAnswerDetails().getOpt2(), q.getAnswerDetails().getOpt3(), q.getAnswerDetails().getOpt4(), null,"NA", q.getAnswerDetails().getCurrectOpt(),
						q.getBlmTaxonomy(), q.getDifficultyLevel().getDiffLevel());				
			}
			else {
				addedques= new AddedQuestion(0, q.getQuestionId(), x.getAssignmentQuestionID(), new BigDecimal(x.getMarks(),MathContext.DECIMAL64), "N", 
						new BigDecimal(0,MathContext.DECIMAL64), 0.0, q.getQuestionText(), q.getQuestionType().getType(), "N", "NA", 
						"NA", "NA", "NA", null,"", "NA",q.getBlmTaxonomy(), q.getDifficultyLevel().getDiffLevel());
			}
			
			addedques.setTopic(q.getTopicDetails().getTopicTitle());
			return addedques;
			}).collect(Collectors.toList());
		
		assign.setQuestionlist(queslist);
		
		return assign;

	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getAssignmentdetailbyStudentId/{assignmentId}/{emplid}")
	public Assignment getAssignmentdetailbystudentId(@PathVariable long assignmentId,@PathVariable String emplid)
	{
	
		long studentid=studrepo.findStudentidbyEmplid(emplid);
		//long studentid=Long.parseLong(emplid);
		StudentDetails student=em.find(StudentDetails.class, studentid);		
		
		AssignmentDetails assignment=assignmentDetailsRepo.findAssignmentById(assignmentId);
		
		Assignment assign= new Assignment(assignment.getCourseDetails().getCourseId(), assignment.getAssignmentID(), assignment.getAssignmentTitle(), assignment.getAssignmentDescr(), assignment.getAssignmentType(), Generation.dateAsString(assignment.getDueDate()), assignment.getHasPlan(), assignment.getHasUnit(), assignment.getHasQuestion(), assignment.getIsPublished(), assignment.getMaxMarks(), assignment.getCreatedBy(), assignment.getLevel());
		assign.setHasStudent(assignment.getDescr1());
		List<AssignmentQuestions> questionlist=quesrepo.getAssignmentquestionByAssignId(assignmentId);
		List<AddedQuestion> queslist=questionlist.stream().map(x->{
			QuestionDetails q=x.getQuestionDetails();
			AssignmentResponse assignmentresp=null;
			List<AssignmentAttachments> attachment=null;
			try {
				assignmentresp= asgnRespRepo.findResponseByStudentId(assignmentId,student.getStudentId(),q.getQuestionId());

//				attachment=em.createQuery("select ar from AssignmentAttachments ar where ar.assignmentDetails.assignmentID=:assignid and ar.studentDetails.StudentId=:stdentid and ar.questionDetails.QuestionId=:quesid and ar.assignmentResponse.AssignmentResponseID=:responseid")
//						.setParameter("assignid",assignmentId).setParameter("stdentid",studentid).setParameter("quesid",q.getQuestionId()).setParameter("responseid", assignmentresp.getAssignmentResponseID()).getResultList();
				if(assignmentresp.getHasAttachment().equalsIgnoreCase("Y")){
					System.out.println("In if:::::::::");
					AssignmentAttachments attachfile=attachmentrepo.findResponseAttachment(studentid,assignmentId,q.getQuestionId(),assignmentresp.getAssignmentResponseID());
					System.out.println("Attacment id:::::::::::"+attachfile.getAssignmentAttachmentID());
					attachment=Arrays.asList(attachfile);
				}

			}
			catch(Exception e)
			{
				assignmentresp=new AssignmentResponse(0, " ", " ", " ", 0.0, student, q, assignment);
				attachment=null;
			}
			
			String subjectiveresponse="";
			try {
				subjectiveresponse=assignmentresp.getSubjectiveResponse();
			}
			catch(Exception e) {
				subjectiveresponse="NA";
			}
		
			//attachment=attachmentrepo.findResponseByStudentId(assignmentId, studentid, q.getQuestionId(), assignmentresp.getAssignmentResponseID());
		
			AddedQuestion addedques=null;
			if(q.getQuestionType().getType().equalsIgnoreCase("SCQ")||q.getQuestionType().getType().equalsIgnoreCase("MCQ")||q.getQuestionType().getType().equalsIgnoreCase("TNF")) {
				addedques= new AddedQuestion(0, q.getQuestionId(), x.getAssignmentQuestionID(), new BigDecimal(x.getMarks(),MathContext.DECIMAL64), "N", 
						new BigDecimal(0,MathContext.DECIMAL64), 0.0, q.getQuestionText(), q.getQuestionType().getType(), "N", q.getAnswerDetails().getOpt1(), 
						q.getAnswerDetails().getOpt2(), q.getAnswerDetails().getOpt3(), q.getAnswerDetails().getOpt4(), null,"NA", q.getAnswerDetails().getCurrectOpt(),
						q.getBlmTaxonomy(), q.getDifficultyLevel().getDiffLevel());				
			}
			else {
				addedques= new AddedQuestion(0, q.getQuestionId(), x.getAssignmentQuestionID(), new BigDecimal(x.getMarks(),MathContext.DECIMAL64), "N", 
						new BigDecimal(0,MathContext.DECIMAL64), 0.0, q.getQuestionText(), q.getQuestionType().getType(), "N", "NA", 
						"NA", "NA", "NA", null,subjectiveresponse, "NA",q.getBlmTaxonomy(), q.getDifficultyLevel().getDiffLevel());
			}
			List<AssignmentAttachmentModel> attachmodel=new ArrayList<>();
			try {
				if(assignmentresp.getHasAttachment().equalsIgnoreCase("Y"))
					attachmodel=attachment.stream().map(m->{
					return new AssignmentAttachmentModel(m.getAssignmentAttachmentID(), m.getAssignmentResponse().getAssignmentResponseID(), assignmentId, q.getQuestionId(), studentid, m.getOriginalFileName(), m.getGeneratedFileName(), m.getFileExtension(), m.getFilePath());
				}).collect(Collectors.toList());
			}catch(Exception e){
				attachmodel=null;
			}
			AssignmentResponseModel response=null;
			try{
				response=new AssignmentResponseModel(assignmentresp.getAssignmentResponseID(), assignmentId, studentid, q.getQuestionId(), assignmentresp.getObjectiveResponse(), assignmentresp.getSubjectiveResponse(), assignmentresp.getHasAttachment(), String.valueOf(assignmentresp.getMarksObtained()), attachmodel);
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				response=new AssignmentResponseModel();
			}
			
			addedques.setAssignresponse(response);
			addedques.setTopic(q.getTopicDetails().getTopicTitle());
			return addedques;
			}).collect(Collectors.toList());
		
		assign.setQuestionlist(queslist);
		
		return assign;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getStudentAssignment/{courseplanId}/{emplid}")
	public List<Assignment> getStudentMapping(@PathVariable long courseplanId,@PathVariable long emplid )
	{
		List<AssignmentPlanMapping> planmap=em.createQuery("select sm from AssignmentPlanMapping sm where sm.coursePlan.CoursePlanId=: courseplanId and sm.assignmentDetails.level='C'")
				.setParameter("courseplanId", courseplanId).getResultList();
		List<Long> ids=new ArrayList<>();
		
		for(AssignmentPlanMapping apm:planmap) {
			if(apm.getAssignmentDetails().getIsPublished().equalsIgnoreCase("Y"))
				ids.add(apm.getAssignmentDetails().getAssignmentID());
			//System.out.println(apm.getAssignmentDetails().getAssignmentID());
		}
		
		List<AssignmentStudentMapping> studmap=em.createQuery("select asm from AssignmentStudentMapping asm where asm.studentDetails.StudentId=: studentId and asm.assignmentDetails.assignmentID in (:asgnmntId)")
				.setParameter("studentId", emplid).setParameter("asgnmntId",ids).getResultList();
		
		List<Assignment> assignmentlist=new ArrayList<>();
		try 
		{
			for(AssignmentStudentMapping asm:studmap) 
			{
				//System.out.println(asm.getCoursePlan().getCoursePlanId());
				String status="";
				AssignmentDetails assignment=assignmentDetailsRepo.findAssignmentById(asm.getAssignmentDetails().getAssignmentID());
				try {
					status=submissionrepo.getStudentStatus(assignment.getAssignmentID(), emplid);
				}
				catch(Exception e)
				{
					status="P";
				}
				Assignment assign= new Assignment(assignment.getCourseDetails().getCourseId(), assignment.getAssignmentID(), assignment.getAssignmentTitle(), assignment.getAssignmentDescr(), assignment.getAssignmentType(), Generation.dateAsString(assignment.getDueDate()), assignment.getHasPlan(), assignment.getHasUnit(), assignment.getHasQuestion(), assignment.getIsPublished(), assignment.getMaxMarks(), assignment.getCreatedBy(), assignment.getLevel());				
				assign.setHasStudent(assignment.getDescr1());
				assign.setResponseStatus(status);
				assignmentlist.add(assign);
			}
		}
		catch(Exception w)
		{
			System.out.println("ERROR:::::"+w.getMessage());
		}
		return assignmentlist;
	}
	


	@SuppressWarnings("unchecked")
	@GetMapping("/getStudentAssignmentforUnit/{courseplanId}/{emplid}/{unitId}")
	public List<Assignment> getStudentForUnit(@PathVariable long courseplanId,@PathVariable long emplid,@PathVariable long unitId )
	{
		List<AssignmentUnitMapping> planmap=em.createQuery("select sm from AssignmentUnitMapping sm where sm.coursePlan.CoursePlanId=: courseplanId and sm.assignmentDetails.level='U' and sm.unit.UnitId=: unitid")
				.setParameter("courseplanId", courseplanId).setParameter("unitid", unitId).getResultList();
		List<Long> ids=new ArrayList<>();
		
		for(AssignmentUnitMapping apm:planmap) {
			if(apm.getAssignmentDetails().getIsPublished().equalsIgnoreCase("Y"))
				ids.add(apm.getAssignmentDetails().getAssignmentID());
			//System.out.println(apm.getAssignmentDetails().getAssignmentID());
		}
		
		List<AssignmentStudentMapping> studmap=em.createQuery("select asm from AssignmentStudentMapping asm where asm.studentDetails.StudentId=: studentId and asm.assignmentDetails.assignmentID in (:asgnmntId)")
				.setParameter("studentId", emplid).setParameter("asgnmntId",ids).getResultList();
		
		List<Assignment> assignmentlist=new ArrayList<>();
		try 
		{
			for(AssignmentStudentMapping asm:studmap) 
			{
				//System.out.println(asm.getCoursePlan().getCoursePlanId());
				String rstatus="";
				AssignmentDetails assignment=assignmentDetailsRepo.findAssignmentById(asm.getAssignmentDetails().getAssignmentID());
				rstatus=submissionrepo.getStudentStatus(assignment.getAssignmentID(), emplid);
				if(rstatus==null)
					rstatus="NA";

				//System.out.println("Status::::::::::"+rstatus);
				Assignment assign= new Assignment(assignment.getCourseDetails().getCourseId(), assignment.getAssignmentID(), assignment.getAssignmentTitle(), assignment.getAssignmentDescr(), assignment.getAssignmentType(), Generation.dateAsString(assignment.getDueDate()), assignment.getHasPlan(), assignment.getHasUnit(), assignment.getHasQuestion(), assignment.getIsPublished(), assignment.getMaxMarks(), assignment.getCreatedBy(), assignment.getLevel());
				assign.setHasStudent(assignment.getDescr1());
				assign.setResponseStatus(rstatus);
				assignmentlist.add(assign);
			}
		}
		catch(Exception w)
		{
			System.out.println("ERROR:::::"+w.getMessage());
		}
		return assignmentlist;
	}
	
	
	
	@GetMapping("/getAssignmentByStudentIdandCourseId/{emplId}/{courseId}")
	public List<Assignment> getAssignmentsForStudentByEmpId(@PathVariable String emplId,@PathVariable long courseId)
	{
		//List<AssignmentDetails> assignment=assignmentDetailsRepo.getAssignmentByEmpId(emplId, courseId);
		return assignmentDetailsRepo.getAssignmentByEmpId(emplId, courseId).stream()
	              .map(x->
	              {
	            	  List<AssignmentPlan> plan = x.getAssignmentplan().stream()
	            			                              .map(y->{
	            			                            	  
	            			                            	  AssignmentPlan asplan= new AssignmentPlan(y.getAssignmentPlanMappingID(), y.getCourseDetails().getCourseId(), y.getBatchDetails().getBatchId(), y.getAssignmentDetails().getAssignmentID(), y.getCoursePlan().getCoursePlanId());
	            			                            	  asplan.setUnitlist(y.getAssignmentunit().stream().map(
	            			                            			  z->{
	            			                            				  return new AssignmentUnit(z.getCourseDetails().getCourseId(),z.getBatchDetails().getBatchId(),z.getAssignmentDetails().getAssignmentID(),z.getCoursePlan().getCoursePlanId(),z.getAssignmentUnitID(),z.getTopics(),z.getTopicIDs());
	            			                            			  } ).collect(Collectors.toList()));	
	            			                            	  return asplan;
	            			                              }).collect(Collectors.toList());            	 
   
	            	  Assignment assgnmnt= new Assignment(x.getCourseDetails().getCourseId(), x.getAssignmentID(), x.getAssignmentTitle(), x.getAssignmentDescr(), x.getAssignmentType(),Generation.dateAsString(x.getDueDate()), x.getHasPlan(), x.getHasUnit(), x.getHasQuestion(), x.getIsPublished(), x.getMaxMarks(), x.getCreatedBy(), x.getLevel());
	            	  assgnmnt.setAssignplan(plan);
	            	  return assgnmnt;
	              })
	              .collect(Collectors.toList());
	}
	
	
	@GetMapping("/getStudentAssignmentStatus/{assignId}/{studentId}")
	public ResponceMessage getAssignmentsubmitStatus(@PathVariable long assignId ,@PathVariable long studentId)
	{
		String status=submissionrepo.getStudentStatus(assignId, studentId);
		return new ResponceMessage(status);
	}
	
	@PostMapping("/updateStudentMarks")
	public ResponceMessage saveAssignmentMarks(@RequestBody List<AssignmentMarksUpdateModel> marksupdate)	
	{
		ResponceMessage responsemsg=null;
		double totalmarks=0.0;
		AssignmentDetails assgnment=assignmentDetailsRepo.getAssignmentByAssignId(marksupdate.get(0).getAssignmentId());
		AssignmentStatusDetails statusdetail=submissionrepo.getExistingStudentStatus(marksupdate.get(0).getAssignmentId(), marksupdate.get(0).getStudentId());
		for(AssignmentMarksUpdateModel updateresponse:marksupdate ) {
			totalmarks+=updateresponse.getMarks();
			int count=asgnRespRepo.saveMarks(updateresponse.getMarks(), updateresponse.getAssignmentId(), updateresponse.getStudentId(), updateresponse.getQuestionId(), updateresponse.getResponseId());

			if(count==0) {
				//responsemsg=new ResponceMessage("Error::::"+updateresponse.getResponseId());
				continue;
			}
			else 
				responsemsg=new ResponceMessage("Success");
		}
		int statusupdate=submissionrepo.setEvaluationStatus(marksupdate.get(0).getSubmitflag(),marksupdate.get(0).getAssignmentId(), marksupdate.get(0).getStudentId());
		if(totalmarks>assgnment.getMaxMarks())
			return new ResponceMessage("ERROR:::::Obtained marks > Maxmarks");
		else
			return responsemsg;
	}
	
	@GetMapping("/getStudentSubmissionStatusList/{courseid}/{assignId}/{batchid}")
	public List<AssignmentStudentSubmissionStatusList> getStudentList(@PathVariable long courseid,@PathVariable long assignId,@PathVariable long batchid)
	{
		List<StudentEnrollment> studentlist=em.createNamedQuery("StudentEnrollment.getStudentListByBatchId").setParameter("batchid", batchid).getResultList();
		List<Long> studentid=new ArrayList<>();
		for(StudentEnrollment se:studentlist)
			studentid.add(se.getStudent().getStudentId());
		
		List<AssignmentStudentSubmissionStatusList> studentstatuslist=new ArrayList<>();
		List<Long> maplist=studentmaprepo.getStudentMappList(assignId);
		for(Long ids:studentid)
		{
			String submitstatus="notassigned";
			String timelystatus="",evaluationstatus="";
			StudentDetails student=studrepo.findStudentById(ids);
			double obtainedmarks;
			try {
			 obtainedmarks=asgnRespRepo.getObtainedMarks(assignId, ids);
			}
			catch(Exception e)
			{
				obtainedmarks=0.0;
			}

			AssignmentStatusDetails statusdetail=null;
			if(maplist.contains(ids)) {
				try {
					statusdetail=submissionrepo.getExistingStudentStatus(assignId, ids);
					submitstatus=statusdetail.getSubmitStatus();
					timelystatus=statusdetail.getTimingStatus();System.out.println(timelystatus);
					evaluationstatus=statusdetail.getEvaluationStatus();System.out.println(evaluationstatus);
				}
				catch(Exception e)
				{			
					submitstatus="assigned";
					timelystatus="NA";
					evaluationstatus="NA";
				}
			}
			
			AssignmentDetails assgnment=assignmentDetailsRepo.getAssignmentByAssignId(assignId);
			String middlename=(student.getMidleName()==null)?"":student.getMidleName();
			String stname=String.join(" ", Arrays.asList(student.getFirstName(),middlename,student.getLastName()));
			studentstatuslist.add(new AssignmentStudentSubmissionStatusList(student.getCampusId(), stname, obtainedmarks, timelystatus, evaluationstatus, submitstatus, assgnment.getMaxMarks(),String.valueOf(ids))) ;
			
		}
		return studentstatuslist;
	}

	@GetMapping("/getAssignmentdetailsstudentstatus/{assignmentid}")
	public Assignment getAssignmentStudentdetails(@PathVariable long assignmentid)
	{
		List<Long> studentassignmapids=studentmaprepo.getStudentMappList(assignmentid);
		AssignmentDetails assign=assignmentDetailsRepo.findAssignmentById(assignmentid);
		long batchid=planrepo.getAssignmentplanbyAssignmentId(assignmentid).getBatchDetails().getBatchId();
		List<StudentEnrollment> stenrol=em.createNamedQuery("StudentEnrollment.getStudentListByBatchId").setParameter("batchid",batchid).getResultList();
		List<Long> stids=stenrol.stream().map(x->{return x.getStudent().getStudentId();}).collect(Collectors.toList());
		List<Student> studentlist=new ArrayList<>();
		for(long stntid:stids)
		{
			StudentDetails stinfo=studrepo.findByStudentId(stntid);
			Student stnt=new Student(stinfo.getEmplid(),stinfo.getApplNbr(),stinfo.getCampusId(),stinfo.getFirstName(),
					stinfo.getMidleName(),stinfo.getLastName(),String.join("",Arrays.asList(stinfo.getFirstName(),stinfo.getLastName())),
					stinfo.getDateOfBirth(),stinfo.getEmailAddr(),stinfo.getPrimaryContact(),stinfo.getSecondaryContact(),stinfo.getCreatedBy());
			if(studentassignmapids.contains(stntid))
				stnt.setAssignstatus("Y");
			else
				stnt.setAssignstatus("N");
			studentlist.add(stnt);
		}
		Assignment assignmnt=new Assignment(assign.getCourseDetails().getCourseId(),assignmentid,assign.getAssignmentTitle(),
				assign.getAssignmentDescr(),assign.getAssignmentType(),Generation.dateAsString(assign.getDueDate()),
				assign.getHasPlan(),assign.getHasUnit(),assign.getHasQuestion(),assign.getIsPublished(),assign.getMaxMarks(),
				assign.getCreatedBy(),assign.getLevel());
		assignmnt.setStudentinfo(studentlist);
		return assignmnt;

	}
		
	
	
}

