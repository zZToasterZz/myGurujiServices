package com.srdt.myguruji.resource;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import com.srdt.myguruji.enitity.*;
import com.srdt.myguruji.model.*;
import com.srdt.myguruji.repository.*;
//import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srdt.myguruji.utility.Generation;

@RestController
@RequestMapping("/api/assessment")
public class AssessmentResource
{
	@Autowired
	CourseDetailsRepository crscrep;
	@Autowired
	AssessmentDetailsRepository assesrep;
	@Autowired
	AssessmentTemplateRepository assestmprep;
	@Autowired
	AssessmentSectionRepository secrep;
	@Autowired
	AutoSeqRepository seqrep;
	@Autowired
	QuestionDetailsRepository quesrep;
	@Autowired
	AssessmentQuestionRepository assessquesrep;
	@Autowired
	ScheduledAssessmentRepository schedulerep;
	@Autowired
	BatchRepository batchrep;
	@Autowired
	AssessmentResponceRepository assessresrep;
	@Autowired
	ScheduledAssessmentMappingRepository assessmaprepo;
	@Autowired
	EvaluationStrategyRepository evaluationrepo;
	@Autowired
	FacultyDetailsRepository facultyrepo;
	
	@Autowired
	EntityManager em;
	
	private String subtxt = "";
	private int marks = 0;
	
	@PostMapping("/createassessment")
	@Transactional
	public Assessment createAssessment(@RequestBody Assessment asses)
	{
		CourseDetails courseDetails = crscrep.findCourseDetailsByCourseId(asses.getCourseid());
		String code = "ASSESS_" + Generation.getAssessmentSeq(seqrep);
		AssessmentTemplate assessmentTemplate = null;
		
		AssessmentDetails assessmentDetails = new AssessmentDetails(asses.getTitle(),code, asses.getDescr(), courseDetails, asses.getCreatedby(), asses.getType());
		assessmentDetails.setTemplate(assessmentTemplate);
		assesrep.saveAndFlush(assessmentDetails);
		asses.setAssessmentid(assessmentDetails.getAssessmentId());
		asses.setCode(code);
		return asses;
	}
	
	@PostMapping("/createtemplate")
	@Transactional
	public TemplateDetails createTemplate(@RequestBody TemplateDetails template)
	{
		marks = 0;
		
		CourseDetails courseDetails = null;
		String code = "TMPLT_" + Generation.getTemplateSeq(seqrep);
		
	    AssessmentTemplate assessmentTemplate = new AssessmentTemplate(code, template.getTitle(), template.getDescr(), template.getNoofsection(), courseDetails,template.getCreatedby(),template.getMaxmarks());
	    
	    List<AssessmentSection> assessmentSections =  template.getSectiondetails().stream()
													                                .map(x->{
													                                	marks = marks + x.getSectionmarks();
													                                	return new AssessmentSection(x.getTitle(), x.getSectionmarks(), x.getSectionnote(), x.getTotalquestion(), x.getAttemptquestion(), x.getDescr(), assessmentTemplate, courseDetails, template.getCreatedby());
													                                	
													                                })
													                                .collect(Collectors.toList());
	    assessmentTemplate.setSections(assessmentSections);
	    if(template.getMaxmarks() == 0)
	    {
	    	assessmentTemplate.setMaxMarks(marks);
	    }
	    assestmprep.saveAndFlush(assessmentTemplate);
	    template.setTemplateid(assessmentTemplate.getTemplateId());
	    template.setTemplatecode(code);
		return template;
	}
	@GetMapping("/getAllassessment")
	public List<Assessment> findallAssessment()
	{
		Query qry=em.createQuery("select new com.srdt.myguruji.model.Assessment(a.AssessmentId,b.CourseId,t.TemplateId, a.Title,a.AssessmentCode,a.Descr,'',t.Title,a.CreatedBy,a.Descr1, " +
				"b.CourseCode,b.CourseTitle,'') from AssessmentDetails a inner join a.courseDetails b left join a.template t where a.template.TemplateId is not null");

		List<Assessment> assesslist=qry.getResultList();

		for(Assessment assess:assesslist)
		{

			AssessmentDetails ad=assesrep.findAssessmentDetailsById(assess.getAssessmentid());
			int count=schedulerep.getScheduleStatus(assess.getAssessmentid());
			if(count>0)
				assess.setSchedulestatus("Y");
			else
				assess.setSchedulestatus("N");
			FacultyDetails faculty=facultyrepo.getFacultynameByEmplid(assess.getTemplatedescr());//assess.getCreatedby());
			String middlename=" ";

			String fullname=String.join(" ", Arrays.asList(faculty.getFirstName(),faculty.getLastName()));
			String emplname=String.join(":",Arrays.asList(assess.getTemplatedescr(),fullname));
			assess.setCreatedby(emplname);
			assess.setCreatedon((Generation.dateAsString(ad.getCreatedDate())).substring(0,10));
		}
		return assesslist;
		//return null;

	}

	@GetMapping("/getassessmentbycourseid/{CourseId}")
	public List<Assessment> findAssessment(@PathVariable("CourseId") long CourseId)
	{
		List<Assessment> assesslist= assesrep.findAssessment(CourseId,"");
		for(Assessment assess:assesslist){
			AssessmentDetails ad=assesrep.findAssessmentDetailsById(assess.getAssessmentid());
			int count=schedulerep.getScheduleStatus(assess.getAssessmentid());
			if(count>0)
				assess.setSchedulestatus("Y");
			else
				assess.setSchedulestatus("N");

			FacultyDetails faculty=facultyrepo.getFacultynameByEmplid(assess.getCreatedby());
			String middlename=" ";
			if(faculty.getMidleName()!=null)
				middlename=faculty.getMidleName();
			String fullname=String.join(" ", Arrays.asList(faculty.getFirstName(),middlename,faculty.getLastName()));
			String emplname=String.join(":",Arrays.asList(assess.getCreatedby(),fullname));
			assess.setCreatedby(emplname);
			assess.setCreatedon((Generation.dateAsString(ad.getCreatedDate())).substring(0,10));
			assess.setCoursecode(ad.getCourseDetails().getCourseCode());
			assess.setCoursetitle(ad.getCourseDetails().getCourseTitle());
		}

		return assesslist;

	}	
	
	@GetMapping("/getassessmentbycourseid/{CourseId}/{Emplid}")
	public List<Assessment> findAssessment(@PathVariable("CourseId") long CourseId, @PathVariable("Emplid") String Emplid)
	{

		List<Assessment> assesslist= assesrep.findAssessment(CourseId,Emplid);
		for(Assessment list:assesslist)
		{
			AssessmentDetails ad=assesrep.findAssessmentDetailsById(list.getAssessmentid());
			int count=schedulerep.getScheduleStatus(list.getAssessmentid());
			if(count>0)
				list.setSchedulestatus("Y");
			else
				list.setSchedulestatus("N");

				FacultyDetails faculty=facultyrepo.getFacultynameByEmplid(list.getCreatedby());
				String middlename=" ";
				if(faculty.getMidleName()!=null)
					middlename=faculty.getMidleName();
				String fullname=String.join(" ", Arrays.asList(faculty.getFirstName(),middlename,faculty.getLastName()));
				String emplname=String.join(":",Arrays.asList(list.getCreatedby(),fullname));
				list.setCreatedby(emplname);
			list.setCreatedon((Generation.dateAsString(ad.getCreatedDate())).substring(0,10));
			list.setCoursecode(ad.getCourseDetails().getCourseCode());
			list.setCoursetitle(ad.getCourseDetails().getCourseTitle());


		}
		return assesslist;
	}


	
	@GetMapping("/gettemplatedetailsbyid/{Id}")
	public TemplateDetails findTemplateDetailsByTemplateId(@PathVariable("Id") long Id)
	{
		AssessmentTemplate template = assestmprep.findTemplateDetailsByTemplateId(Id);
		TemplateDetails details = new TemplateDetails(template.getTemplateId(), template.getTemplateCode(), template.getTitle(), template.getDescr(), template.getNoOfSection(), 0 , null,template.getCreatedBy(),template.getMaxMarks());
    	
   	    List<SectionDetails> sectiondetails = template.getSections().stream().map(y->{
			return new SectionDetails(y.getSectionId(), y.getTitle(), y.getSectionMarks(), y.getSectionNote(), y.getTotalQuestion(), y.getAttemptQuestion(), y.getDescr());
   	    }).collect(Collectors.toList());
   	                 
   	   details.setSectiondetails(sectiondetails);
   	   return details;
	}
	
	@GetMapping("/gettemplatebyemplid/{Emplid}")
	public List<TemplateDetails> findTemplateByEmplid(@PathVariable("Emplid") String Emplid)
	{
		return assestmprep.findTemplateByEmplid(Emplid).stream()
				.map(x->{
					return new TemplateDetails(x.getTemplateId(), x.getTemplateCode(), x.getTitle(), x.getDescr(), x.getNoOfSection(), 0 , null,x.getCreatedBy(),x.getMaxMarks());
				}).collect(Collectors.toList());
	}
	
    @GetMapping("/getassessmentdetailsbyid/{Id}")
    public Assessment getAssessmentDetailsById(@PathVariable("Id") long Id)
    {
    	AssessmentDetails details = assesrep.getAssessmentDetailsById(Id);
		FacultyDetails faculty=facultyrepo.getFacultynameByEmplid(details.getCreatedBy());
		String middlename=" ";
		if(faculty.getMidleName()!=null)
			middlename=faculty.getMidleName();
		String fullname=String.join(" ", Arrays.asList(faculty.getFirstName(),middlename,faculty.getLastName()));
		String emplname=String.join(":",Arrays.asList(details.getCreatedBy(),fullname));
    	Assessment assessment = new Assessment(details.getAssessmentId(), details.getCourseDetails().getCourseId(), details.getTemplate().getTemplateId(), details.getTitle(), details.getAssessmentCode(), details.getDescr(), details.getCourseDetails().getCourseTitle(), details.getTemplate().getDescr(), emplname, details.getDescr1());
    	//Assessment assessment = new Assessment(details.getAssessmentId(), details.getCourseDetails().getCourseId(), details.getTemplate().getTemplateId(), details.getTitle(), details.getAssessmentCode(), details.getDescr(), details.getCourseDetails().getCourseTitle(), details.getTemplate().getDescr(), details.getCreatedBy());
    	String evaluationstrategy=evaluationrepo.findEvaluationStrategy(details.getAssessmentId());
		assessment.setEvaluationstrategy(evaluationstrategy);
    	List<SectionDetails> sectionDetails = details.getTemplate().getSections().stream().map(x->{
    		                                                
    														List<AssessmentQuestion> questions = assesrep.getQuestionByAssessIdAndSecId(details.getAssessmentId(), x.getSectionId());
    														
    														SectionDetails sectionDetail = new SectionDetails(x.getSectionId(), x.getTitle(), x.getSectionMarks(), x.getSectionNote(), x.getTotalQuestion(), x.getAttemptQuestion(), x.getDescr());
    														
    														List<AddedQuestion> addedQuestions = questions.stream().map(y->{
    																								List<AddedQuestion> subquestions = null;
    																																																
    																								
    																								// AddedQuestion question = new AddedQuestion(x.getSectionId(), y.getQuestionDetails() != null ? y.getQuestionDetails().getQuestionId() : 0 , details.getAssessmentId(), y.getMarks(), y.getPartialMarking(), y.getPartialMarks(),y.getQuestionDetails().getQuestionText(),y.getQuestionDetails().getQuestionType().getType());
    																								AddedQuestion question = null;
									    															if(y.getIsSubQues().equals("Y"))
    																								{
									    																
									    																question = new AddedQuestion(x.getSectionId(), 0 , details.getAssessmentId(), y.getMarks(), y.getPartialMarking(), y.getPartialMarks(),"","");
    																								
    																									
    																									subquestions = assesrep.getSubqustionByAssessmentQuestionId(y.getAssessmentQuestionId()).stream().map(z->{
    																										
    																																		subtxt = z.getSubSectionNote();
    																																		
    																																		AnswerDetails ans = z.getQuestionDetails().getAnswerDetails();
    																																		if(ans != null)
    																																		{
    																																			return new AddedQuestion(x.getSectionId(), z.getQuestionDetails().getQuestionId(),y.getAssessmentQuestionId(), z.getMarks(), z.getPartialMarking(), z.getPartialMarks(),z.getQuestionDetails().getQuestionText(),z.getQuestionDetails().getQuestionType().getType(),ans.getOpt1(),ans.getOpt2(),ans.getOpt3(),ans.getOpt4());
    																																		}
    																																		else
    																																		{
    																																			return new AddedQuestion(x.getSectionId(), z.getQuestionDetails().getQuestionId(), y.getAssessmentQuestionId(), z.getMarks(), z.getPartialMarking(), z.getPartialMarks(),z.getQuestionDetails().getQuestionText(),z.getQuestionDetails().getQuestionType().getType());
    																																		}
    																																	}).collect(Collectors.toList());
    																									question.setSubquestions(subquestions);
    																									question.setQuestext(subtxt);
    																									question.setIssubques("Y");
    																								}
									    															else
									    															{
									    																AnswerDetails ans = y.getQuestionDetails().getAnswerDetails();
									    																if(ans != null)
									    																{
									    																	question = new AddedQuestion(x.getSectionId(), y.getQuestionDetails().getQuestionId() ,y.getAssessmentQuestionId(), y.getMarks(), y.getPartialMarking(), y.getPartialMarks(),y.getQuestionDetails().getQuestionText(),y.getQuestionDetails().getQuestionType().getType(),ans.getOpt1(),ans.getOpt2(),ans.getOpt3(),ans.getOpt4());
										    																question.setIssubques("N");
										    																question.setCorrectresponse(ans.getCurrectOpt());
										    																question.setBlmtaxonomy(y.getQuestionDetails().getBlmTaxonomy());
										    																question.setDifficulty(y.getQuestionDetails().getDifficultyLevel().getDiffLevel());
									    																}
									    																else
									    																{
									    																	question = new AddedQuestion(x.getSectionId(), y.getQuestionDetails().getQuestionId() , y.getAssessmentQuestionId(), y.getMarks(), y.getPartialMarking(), y.getPartialMarks(),y.getQuestionDetails().getQuestionText(),y.getQuestionDetails().getQuestionType().getType());
										    																question.setIssubques("N");
										    																question.setCorrectresponse("");
										    																question.setBlmtaxonomy(y.getQuestionDetails().getBlmTaxonomy());
										    																question.setDifficulty(y.getQuestionDetails().getDifficultyLevel().getDiffLevel());
									    																}
									    															}
									    															return question;
									    														}).collect(Collectors.toList());
    														sectionDetail.setQuestions(addedQuestions);
    														return sectionDetail;
    		
    	                                                }).collect(Collectors.toList());
    	
    	
    	
    	assessment.setSectiondetails(sectionDetails);
    	return assessment;
    }
    @PostMapping("/addquestion")
    @Transactional
    public ResponceMessage AddQuestion(@RequestBody AssessQuestion question)
    {
    	List<Long> quesid=new ArrayList<>();
    	List<AddedQuestion> quesList=new ArrayList<>();
    	for(AddedQuestion ques: question.getAdd())
    	{
    		//System.out.println("for");
    		if(!(quesid.contains(ques.getQuestionid())))
			{
				quesList.add(ques);
				quesid.add(ques.getQuestionid());
				//System.out.println("in if"+ques.getQuestionid());
			}
		}

    	Long  duplquescount=(Long)em.createQuery("select count(aq) from AssessmentQuestion aq where aq.assessmentDetails.AssessmentId=:assessid and aq.questionDetails.QuestionId=:quesid")
				.setParameter("assessid", question.getAssessmentid()).setParameter("quesid",quesList.get(0).getQuestionid()).getSingleResult();
		System.out.println("count:::"+duplquescount);

		Long  quescount=((Number)em.createNativeQuery("SELECT sum(total_question) FROM assessment_section where template_id=:templateid").setParameter("templateid",question.getTemplateid()).getSingleResult()).longValue();
		long questioncount=quesList.size();
		if(quescount==questioncount)
		{
			CourseDetails courseDetails = crscrep.findCourseDetailsByCourseId(question.getCourseid());
			AssessmentTemplate template = assestmprep.findTemplateDetailsByTemplateId(question.getTemplateid());
			if (template == null) {
				return new ResponceMessage("Template missing");
			}
			AssessmentDetails assessmentDetails = assesrep.findAssessmentDetailsById(question.getAssessmentid());
			assessmentDetails.setTemplate(template);
			assesrep.saveAndFlush(assessmentDetails);

			List<AssessmentQuestion> assessmentQuestions = question.getAdd().stream().map(x -> {
				AssessmentSection assessmentSection = secrep.findAssessmentSectionById(x.getSectionid());
				QuestionDetails questionDetails = quesrep.findQuestionDetailsById(x.getQuestionid());
				AssessmentQuestion assessmentQuestion = new AssessmentQuestion(x.getMarks(), x.getPartialmarks(), x.getPartialmarking(), questionDetails, assessmentDetails, assessmentSection, courseDetails, assessmentDetails.getCreatedBy(), x.getIssubques());
				List<AssessmentSubQuestion> subQuestions = null;
				if (x.getIssubques().equals("Y")) {

					subQuestions = x.getSubquestions().stream().map(z -> {
						QuestionDetails subquestionDetails = quesrep.findQuestionDetailsById(z.getQuestionid());

						return new AssessmentSubQuestion(x.getQuestext(), z.getMarks(), z.getPartialmarks(), z.getPartialmarking(), subquestionDetails, assessmentDetails, assessmentSection, courseDetails, assessmentQuestion, assessmentDetails.getCreatedBy());
					}).collect(Collectors.toList());
				}
				assessmentQuestion.setSubQuestions(subQuestions);
				return assessmentQuestion;
			}).collect(Collectors.toList());
			assessquesrep.saveAll(assessmentQuestions);
			return new ResponceMessage("Question Added");
		}
		else
			return new ResponceMessage("Error");
    }
   
    @PostMapping("/createschedule")
    @Transactional
    public AssessmentSchedule createSchedule(@RequestBody AssessmentSchedule schedule) throws ParseException
    {
    	String datept = "yyyy-MM-dd";
    	String timept = "yyyy-MM-dd HH:mm:ss";
    	AssessmentDetails assessmentDetails = assesrep.findAssessmentDetailsById(schedule.getAssessmentid());
    	CourseDetails courseDetails = crscrep.findCourseDetailsByCourseId(schedule.getCourseid()); 	
    	
    	
    	ScheduledAssessment scheduledAssessment = new ScheduledAssessment(Generation.toDate(schedule.getStartdate(), datept), Generation.toDate(schedule.getEnddate(), datept), Generation.toDate(schedule.getStartdatetime(), timept), Generation.toDate(schedule.getEnddatetime(), timept), schedule.getDescr(), courseDetails, assessmentDetails, schedule.getCreatedby(),schedule.getDuration());
    	
    	List<ScheduledAssessmentMapping> assessmentMappings = schedule.getBatchids().stream().map(x->
														    	                                   {
														    	                                	   BatchDetails batchDetails = batchrep.findBatchByBatchId(x.longValue());
														    	                                	   return new ScheduledAssessmentMapping(scheduledAssessment, batchDetails, courseDetails, schedule.getCreatedby());
														    	                                	   
														    	                                   }).collect(Collectors.toList());
    	
    	scheduledAssessment.setAssessmentMappings(assessmentMappings);
    	/*if(assessmentMappings != null && assessmentMappings.size() > 0)
    	{
    		schedulerep.saveAndFlush(scheduledAssessment);
    		schedule.setScheduledid(scheduledAssessment.getScheduledId());
    	}*/
    	if(assessmentMappings != null && assessmentMappings.size() > 0)
    	{
    		Calendar runCal = Calendar.getInstance();
    		    runCal.setTime(Generation.getCurrentDate());
    		 Calendar newCal = Calendar.getInstance();
    		    newCal.setTime(Generation.toDate(schedule.getStartdatetime(), "yyyy-MM-dd HH:mm:ss"));
    
    		long timediff=(newCal.getTimeInMillis() - runCal.getTimeInMillis());
    		
    		if(Generation.toDate(schedule.getStartdatetime(),"yyyy-MM-dd").before(Generation.getCurrentDate()) && timediff<0){
        		schedule.setScheduledid(0);
        		schedule.setResponsestatus("DateError");
        	}
    		else {
    		schedulerep.saveAndFlush(scheduledAssessment);
    		schedule.setScheduledid(scheduledAssessment.getScheduledId());
    		schedule.setResponsestatus("Success");
    		}
    	}
    	
    	return schedule;
    }

    /****************************SANDHYA*********************************/

    @PostMapping("/updateQuestionIdAssessment")
	public ResponceMessage updateQuestionIdforAssessment(@RequestBody DoubleResponseModel list)
	{
		try {
			for (DoubleResponseModel resp : list.getQuestiondetail()) {
				//message:questionId and Status:AssessmentQuestionId
				int count = assessquesrep.updatequestionid(Long.parseLong(resp.getMessage()), Long.parseLong(resp.getStatus()));
			}
			evaluationrepo.setEvaluationStrategy(list.getStatus(), Long.parseLong(list.getMessage()));
			return new ResponceMessage("Success");
		}
		catch(Exception e)
		{
			return new ResponceMessage("Error:"+e.getMessage());
		}
	}

    @PostMapping("/setresponce")
    @Transactional
    public ResponceMessage setAssessmentResponce(@RequestBody List<AssessRes> reses)
    {
    	AssessmentDetails assessmentDetails = assesrep.findAssessmentDetailsById(reses.get(0).getAssessmentid());
    	CourseDetails courseDetails = crscrep.findCourseDetailsByCourseId(reses.get(0).getCourseid());
    	
    	List<AssessmentResponce> responces = reses.stream().map(res->{
    										assessresrep.deleteAssessmentResponce(res.getEmplid(), res.getQuestionid(), res.getAssessmentid());
							    			QuestionDetails questionDetails = quesrep.findQuestionDetailsById(res.getQuestionid());	
							    			return new AssessmentResponce(res.getQuesres(), res.getSubres(), res.getQtype(), res.getEmplid(), assessmentDetails, questionDetails, courseDetails);
							    	    }).collect(Collectors.toList());
    	assessresrep.saveAll(responces);
    	
    	return new ResponceMessage("success");
    }
    
    @GetMapping("/getschedulesbyemplid/{Emplid}")
    public List<Schedule> getScheduleByEmplid(@PathVariable("Emplid") String Emplid)
    {
    	return assesrep.getScheduleByEmplid(Emplid).stream().map(x->{
																AssessmentDetails ad = x.getAssessmentDetails();
																CourseDetails cd = x.getCourseDetails();
																AssessmentTemplate template = x.getAssessmentDetails().getTemplate();
																Schedule temp =new Schedule(ad.getAssessmentId(), cd.getCourseId(), ad.getTitle(), ad.getAssessmentCode(), ad.getDescr(), cd.getCourseCode(), cd.getCourseDescr(), x.getStartDate().toString(), x.getEndDate().toString(), x.getStartDateTime().toString(), x.getEndDateTime().toString(),x.getDuration(),template.getMaxMarks());
																temp.setIsActive(x.getIsActive());
																//return new Schedule(ad.getAssessmentId(), cd.getCourseId(), ad.getTitle(), ad.getAssessmentCode(), ad.getDescr(), cd.getCourseCode(), cd.getCourseDescr(), x.getStartDate().toString(), x.getEndDate().toString(), x.getStartDateTime().toString(), x.getEndDateTime().toString(),x.getDuration(),template.getMaxMarks());
																return temp;
																
															}).collect(Collectors.toList());
    }
    
    @GetMapping("/gettodayschedulesbyemplid/{Emplid}")
    public List<Schedule> getTodayScheduleByEmplid(@PathVariable("Emplid") String Emplid)
    {
    	return assesrep.getTodayScheduleByEmplid(Emplid).stream().map(x->{
    																AssessmentDetails ad = x.getAssessmentDetails();
    																CourseDetails cd = x.getCourseDetails();
    																AssessmentTemplate template = x.getAssessmentDetails().getTemplate();
    																return new Schedule(ad.getAssessmentId(), cd.getCourseId(), ad.getTitle(), ad.getAssessmentCode(), ad.getDescr(), cd.getCourseCode(), cd.getCourseDescr(), x.getStartDate().toString(), x.getEndDate().toString(), x.getStartDateTime().toString(), x.getEndDateTime().toString(),x.getDuration(),template.getMaxMarks());
    																
    															}).collect(Collectors.toList());
    }
    
    @GetMapping("/getinstructionbyassessmenid/{AssessmenId}")
    public Schedule getInstructionByAssessmenId(@PathVariable("AssessmenId") long AssessmenId)
    {
		ScheduledAssessment x = assesrep.getInstructionByAssessmenId(AssessmenId);
		AssessmentDetails ad = x.getAssessmentDetails();
		CourseDetails cd = x.getCourseDetails();
		AssessmentTemplate template = x.getAssessmentDetails().getTemplate();
		Schedule schedule = new Schedule(ad.getAssessmentId(), cd.getCourseId(), ad.getTitle(), ad.getAssessmentCode(), ad.getDescr(), cd.getCourseCode(), cd.getCourseDescr(), x.getStartDate().toString(), x.getEndDate().toString(), x.getStartDateTime().toString(), x.getEndDateTime().toString(),x.getDuration(),template.getMaxMarks());
		
		
		
   	    List<SectionDetails> sectiondetails = template.getSections().stream().map(y->{
               		                 
               		  							return new SectionDetails(y.getSectionId(), y.getTitle(), y.getSectionMarks(), y.getSectionNote(), y.getTotalQuestion(), y.getAttemptQuestion(), y.getDescr());
               		  
               	  						   }).collect(Collectors.toList());
   	                 
   	   schedule.setSectionDetails(sectiondetails);
   	   return schedule;
    }
    
    
    @GetMapping("/getcondidatesbyassessmentId/{assessmentid}")
    public List<Student> getCondidatesByAssessmentId(@PathVariable("assessmentid") long assessmentid)
    {
    	return assesrep.getCondidatesByAssessmentId(assessmentid);
    }    
    
    @GetMapping("/getassessresponce/{emplid}/{assessmentid}")
    public Assessment getAssessResponce(@PathVariable("emplid") String emplid,@PathVariable("assessmentid") long assessmentid)
    {
    	AssessmentDetails details = assesrep.getAssessmentDetailsById(assessmentid);
    	Assessment assessment = new Assessment(details.getAssessmentId(), details.getCourseDetails().getCourseId(), details.getTemplate().getTemplateId(), details.getTitle(), details.getAssessmentCode(), details.getDescr(), details.getCourseDetails().getCourseTitle(), details.getTemplate().getDescr(), details.getCreatedBy());
    	List<SectionDetails> sectionDetails = details.getTemplate().getSections().stream().map(x->{
    		                                                
    														List<AssessmentQuestion> questions = assesrep.getQuestionReponceByAssessIdAndSecId(details.getAssessmentId(), x.getSectionId(),emplid);
    														
    														SectionDetails sectionDetail = new SectionDetails(x.getSectionId(), x.getTitle(), x.getSectionMarks(), x.getSectionNote(), x.getTotalQuestion(), x.getAttemptQuestion(), x.getDescr());
    														
    														List<AddedQuestion> addedQuestions = questions.stream().map(y->{
    																								List<AddedQuestion> subquestions = null;
    																								
    																								AddedQuestion question = null;
									    															if(y.getIsSubQues().equals("Y"))
    																								{
									    																question = new AddedQuestion(x.getSectionId(), 0 , details.getAssessmentId(), y.getMarks(), y.getPartialMarking(), y.getPartialMarks(),"","");
    																									subquestions = assesrep.getSubqustionResponceByAssessmentQuestionId(y.getAssessmentQuestionId(),emplid).stream().map(z->{
    																										
    																																		subtxt = z.getSubSectionNote();
    																																		
    																																		AnswerDetails ans = z.getQuestionDetails().getAnswerDetails();
    																																		System.out.println("TYPE1 : "+z.getQuestionDetails().getQuestionType());
    																																		if(ans != null)
    																																		{
    																																			AddedQuestion tmpQuestion = new AddedQuestion(x.getSectionId(), z.getQuestionDetails().getQuestionId(), details.getAssessmentId(), z.getMarks(), z.getPartialMarking(), z.getPartialMarks(),z.getQuestionDetails().getQuestionText(),z.getQuestionDetails().getQuestionType().getType(),ans.getOpt1(),ans.getOpt2(),ans.getOpt3(),ans.getOpt4());
    																																			if(z.getQuestionDetails().getAssessmentResponces() != null && z.getQuestionDetails().getAssessmentResponces().size() > 0)
    																																				tmpQuestion.setQuesresponce(z.getQuestionDetails().getAssessmentResponces().get(0).getQuesResponce()); 
    																																			return tmpQuestion;
    																																		}
    																																		else
    																																		{
    																																			AddedQuestion tmpQuestion =  new AddedQuestion(x.getSectionId(), z.getQuestionDetails().getQuestionId(), details.getAssessmentId(), z.getMarks(), z.getPartialMarking(), z.getPartialMarks(),z.getQuestionDetails().getQuestionText(),z.getQuestionDetails().getQuestionType().getType());
    																																			if(z.getQuestionDetails().getAssessmentResponces() != null && z.getQuestionDetails().getAssessmentResponces().size() > 0)
    																																			{
    																																				if( z.getQuestionDetails().getQuestionType().equals("FNB") )
    																																				{
    																																					tmpQuestion.setQuesresponce(z.getQuestionDetails().getAssessmentResponces().get(0).getQuesResponce());
    																																				}
    																																				else
    																																				{
    																																					tmpQuestion.setQuesresponce(z.getQuestionDetails().getAssessmentResponces().get(0).getSubjectiveResponce());
    																																				}
    																																			}
    																																			return tmpQuestion;
    																																		}
    																																	}).collect(Collectors.toList());
    																									question.setSubquestions(subquestions);
    																									question.setQuestext(subtxt);
    																									question.setIssubques("Y");
    																								}
									    															else
									    															{
									    																AnswerDetails ans = y.getQuestionDetails().getAnswerDetails();
									    																if(ans != null)
									    																{
									    																	
									    																	question = new AddedQuestion(x.getSectionId(), y.getQuestionDetails().getQuestionId() , details.getAssessmentId(), y.getMarks(), y.getPartialMarking(), y.getPartialMarks(),y.getQuestionDetails().getQuestionText(),y.getQuestionDetails().getQuestionType().getType(),ans.getOpt1(),ans.getOpt2(),ans.getOpt3(),ans.getOpt4());
										    																question.setIssubques("N");
										    																question.setCorrectresponse(ans.getCurrectOpt());
										    																question.setBlmtaxonomy(y.getQuestionDetails().getBlmTaxonomy());
										    																question.setDifficulty(y.getQuestionDetails().getDifficultyLevel().getDiffLevel());
										    																question.setObtainedMarks( y.getQuestionDetails().getAssessmentResponces().get(0).getMarksobtained() );
										    																if(y.getQuestionDetails().getAssessmentResponces() != null && y.getQuestionDetails().getAssessmentResponces().size() > 0)
										    																{
										    																	//question.setQuesresponce(y.getQuestionDetails().getAssessmentResponces().get(0).getQuesResponce());
										    																	if(y.getQuestionDetails().getQuestionType().getType().equals("FNB")
										    																			||y.getQuestionDetails().getQuestionType().getType().equals("SCQ")
										    																			||y.getQuestionDetails().getQuestionType().getType().equals("MCQ")
										    																			||y.getQuestionDetails().getQuestionType().getType().equals("TNF"))
										    																	{
										    																		question.setQuesresponce(y.getQuestionDetails().getAssessmentResponces().get(0).getQuesResponce());
										    																	}
										    																	else
										    																	{
										    																		question.setQuesresponce(y.getQuestionDetails().getAssessmentResponces().get(0).getSubjectiveResponce());
										    																	}
										    																}
									    																}
									    																else
									    																{
									    																	question = new AddedQuestion(x.getSectionId(), y.getQuestionDetails().getQuestionId() , details.getAssessmentId(), y.getMarks(), y.getPartialMarking(), y.getPartialMarks(),y.getQuestionDetails().getQuestionText(),y.getQuestionDetails().getQuestionType().getType());
										    																question.setIssubques("N");
										    																question.setCorrectresponse("");
										    																question.setBlmtaxonomy(y.getQuestionDetails().getBlmTaxonomy());
										    																question.setDifficulty(y.getQuestionDetails().getDifficultyLevel().getDiffLevel());
										    																question.setObtainedMarks( y.getQuestionDetails().getAssessmentResponces().get(0).getMarksobtained() );
										    																if(y.getQuestionDetails().getAssessmentResponces() != null && y.getQuestionDetails().getAssessmentResponces().size() > 0)
										    																{
										    																	if(y.getQuestionDetails().getQuestionType().getType().equals("FNB"))
										    																		question.setQuesresponce(y.getQuestionDetails().getAssessmentResponces().get(0).getQuesResponce());
										    																	else
										    																		question.setQuesresponce(y.getQuestionDetails().getAssessmentResponces().get(0).getSubjectiveResponce());
										    																}
									    																}
									    															}
									    															return question;
									    														}).collect(Collectors.toList());
    														sectionDetail.setQuestions(addedQuestions);
    														return sectionDetail;
    		
    	                                                }).collect(Collectors.toList());	
    	assessment.setSectiondetails(sectionDetails);
    	return assessment;
    }
    
    @GetMapping("/getschedulesbybatchid/{BatchId}")
    public List<Schedule> getSchedulesByBatchId(@PathVariable("BatchId") long BatchId)
    {
    	return null;
    }
    
    @GetMapping("/getschedulesbycourseid")
    public List<Schedule> getSchedulesByCourseId(@PathVariable("CourseId") long CourseId)
    {
    	return null;
    }
    
    @GetMapping("/getnotscheduledassessmentbycourseid/{CourseId}/{Emplid}")
	public List<Assessment> findNotScheduledAssessment(@PathVariable("CourseId") long CourseId, @PathVariable("Emplid") String Emplid)
	{
		return assesrep.findNotScheduledAssessment(CourseId,Emplid);
	}
	
	@GetMapping("/getnotscheduledassessmentbycourseid/{CourseId}")
	public List<Assessment> findNotScheduledAssessment(@PathVariable("CourseId") long CourseId)
	{
		return assesrep.findNotScheduledAssessment(CourseId,"");
	}
	
	@GetMapping("/getalreadyscheduledassessmentbycourseid/{CourseId}/{Emplid}")
	public List<Assessment> findAlreadyScheduledAssessment(@PathVariable("CourseId") long CourseId, @PathVariable("Emplid") String Emplid)
	{		
		return assesrep.findAlreadyScheduledAssessment(CourseId,Emplid);
	}
	
	@GetMapping("/getalreadyscheduledassessmentbycourseid/{CourseId}")
	public List<Assessment> findAlreadyScheduledAssessment(@PathVariable("CourseId") long CourseId)
	{
		return assesrep.findAlreadyScheduledAssessment(CourseId,"");
	}

	@PostMapping("/getStudentCountByBatchId")
	public ResponceMessage getStudentCount(@RequestBody List<Long> batchid)
	{
		Query qry=em.createNamedQuery("StudentEnrollment.getStudentCount");
		long stcount=((Number)qry.setParameter("batchId", batchid).getSingleResult()).longValue();
		return new ResponceMessage(String.valueOf(stcount));
	}


    @PostMapping("/getexistingschedulebybatchid")
    public List<Schedule> getExistingScheduleByBatchId(@RequestBody List<Long> batchid)
    {
    	Query qry=em.createNamedQuery("StudentEnrollment.getStudentCount");
    	long stcount=((Number)qry.setParameter("batchId", batchid).getSingleResult()).longValue();

    	return schedulerep.getExistingScheduleByBatchId(batchid).stream().map(x->{
			AssessmentDetails ad = x.getAssessmentDetails();
			CourseDetails cd = x.getCourseDetails();
			AssessmentTemplate template = x.getAssessmentDetails().getTemplate();
			String batchcodes = String.join(",", x.getAssessmentMappings().stream().map(m->{
				return m.getBatchDetails().getBatchCode();
			}).collect(Collectors.toList()));

			String batchids = String.join(",", x.getAssessmentMappings().stream().map(m->{
				return String.valueOf(m.getBatchDetails().getBatchId());
			}).collect(Collectors.toList()));

			String createdby = x.getCreatedBy();
			
			/*String createdby = String.join(",", x.getAssessmentMappings().stream().map(m->{
				return m.getCreatedBy();
			}).collect(Collectors.toList()));*/

			Schedule schedule= new Schedule(ad.getAssessmentId(), cd.getCourseId(), ad.getTitle(), ad.getAssessmentCode(), ad.getDescr(), cd.getCourseCode(), cd.getCourseDescr(), x.getStartDate().toString(), x.getEndDate().toString(), x.getStartDateTime().toString(), x.getEndDateTime().toString(),x.getDuration(),template.getMaxMarks(),batchcodes,createdby,x.getIsActive(),stcount);
			schedule.setBatchid(batchids);
			return schedule;
		}).collect(Collectors.toList());
    }
    
    @GetMapping("/getconductedassessmentbycourseid/{id}")
    public List<Assessment> getConductedAssessmentByCourseId(@PathVariable("id") long id)
    {
    	return assesrep.getConductedAssessmentByCourseId(id).stream().map(x->{
    		return new Assessment(x.getAssessmentId(), x.getTitle(), x.getAssessmentCode(), x.getDescr());
    	}).collect(Collectors.toList());
    }
    
    @GetMapping("/getbatchesbyassessmentid/{Id}")
    public List<Batches> getBatchesByAssessmentId(@PathVariable("Id") long id)
    {
    	return assesrep.getBatchesByAssessmentId(id);
    }
    
    @SuppressWarnings("unchecked")
	@GetMapping("/getassessresult/{assessid}/{batchid}")
    public List<AssessResult> getAssessmentResponceByBaches(@PathVariable("assessid") long assessid,@PathVariable("batchid") long batchid)
    {
    	//String query = "select student_id as studentid,first_name as firstname,last_name as lastname,ytr1.loginid,ytr1.password,ytr1.emplid,emailid, primary_contact as primarycontact,course,opt_marks as optmarks,ytr1.assessment_id as assessmentid,title as assesstitle, batch_id as batchid,batch_code as batchcode,section as batchsection from (select a.student_id,first_name,last_name,emplid as loginid,'Qwe@123' as password,emplid,email_addr as emailid, primary_contact,d.batchtitle as course,f.assessment_id,g.title,c.batch_id,d.batch_code,d.section from student_details a inner join user_login b on a.emplid=b.empl_id inner join student_enrollment c on a.student_id=c.student_id inner join batch_details d on c.batch_id=d.batch_id inner join scheduled_assessment_mapping e on e.batch_id=d.batch_id inner join scheduled_assessment f on e.scheduled_id=f.scheduled_id inner join assessment_details g on f.assessment_id=g.assessment_id where a.emplid!='mgstudent' and c.batch_id like case when batchid=0 then '%' else batchid end order by a.emplid) ytr1 inner join (select emplid,assessment_id,sum(opt_marks) as opt_marks from(select a.emplid,a.assessment_id,b.marks,a.ques_responce,c.currect_opt, case when  a.ques_responce=c.currect_opt then b.marks else 0 end as opt_marks from assessment_responce a inner join assessment_question b on a.question_id=b.question_id and a.assessment_id=b.assessment_id inner join answer_details c on a.question_id=c.question_id ) ytr where emplid!='mgstudent' group by emplid,assessment_id) ytr2 on ytr1.emplid=ytr2.emplid and ytr1.assessment_id=ytr2.assessment_id where ytr1.assessment_id in(assessid) order by ytr2.opt_marks desc";
    	StoredProcedureQuery sp = em.createStoredProcedureQuery("assessmentresults");
    	sp.registerStoredProcedureParameter("assessid", Long.class, ParameterMode.IN);
    	sp.registerStoredProcedureParameter("batchid", Long.class, ParameterMode.IN);
    	sp.setParameter("assessid", assessid);
    	sp.setParameter("batchid", batchid);
    	List<Object[]> results = (List<Object[]>) sp.getResultList();
    	
    	return results.stream().map(x->{
    		String x15 = "";
    		String x16 = "";
    		if(x[15] == null) x15=""; else x15 = x[15].toString();
    		if(x[16] == null) x16=""; else x16 = x[16].toString();
    		
    		return new AssessResult(Long.parseLong(x[0].toString()), x[1].toString(), x[2].toString(), x[3].toString(), x[4].toString(), x[5].toString(), x[6].toString(), x[7].toString(), x[8].toString(), Double.parseDouble(x[9].toString()), Long.parseLong(x[10].toString()), x[11].toString(), Long.parseLong(x[12].toString()), x[13].toString(), x[14].toString(), x15, x16);
    	}).collect(Collectors.toList());
    }
    
    /*********SHANTANU**********/
    @PostMapping("/setmarks")
    public ResponceMessage setMarks(@RequestBody AssessMarks assessMarks)
    {
    	ResponceMessage res = null;
    	try
    	{
    		assessresrep.setMarks(assessMarks.getMarks(), assessMarks.getAssessmentid(), assessMarks.getQuestionid(), assessMarks.getEmplid());
    		return new ResponceMessage() {{setMessage("success");}};
    	}
    	catch(Exception ex)
    	{
    		return new ResponceMessage() {{setMessage("error");}};
    	}
    }
    
    @PostMapping("/setallmarks")
    public List<ResponceMessage> setAllMarks(@RequestBody List<AssessMarks> allAssessMarks)
    {
    	List<ResponceMessage> res = new ArrayList<>();
    	
    	for(AssessMarks assessMarks : allAssessMarks)
    	{
	    	try
	    	{
	    		assessresrep.setMarks(assessMarks.getMarks(), assessMarks.getAssessmentid(), assessMarks.getQuestionid(), assessMarks.getEmplid());
	    		res.add(new ResponceMessage() {{setMessage("saved : "+assessMarks.getQuestionid());}});
	    	}
	    	catch(Exception ex)
	    	{
	    		res.add(new ResponceMessage() {{setMessage("error : "+assessMarks.getQuestionid());}});
	    	}
    	}
    	return res;
    }
    
    @GetMapping("/getassessresponserecord/{emplid}/{assessmentid}/{questionid}")
    public AssessResponceModel getRec(@PathVariable("emplid")String emplid, @PathVariable("assessmentid")long assessid, @PathVariable("questionid")long questionid)
    {
    	AssessmentResponce res = assessresrep.findAssessRecord(emplid,assessid,questionid);
    	
    	if(res == null)
    	{
    		return new AssessResponceModel();
    	}
    	else
    	{
    		return new AssessResponceModel() {{
    			setAssessmentid(res.getAssessmentDetails().getAssessmentId());
    			setCourseid(res.getCourseDetails().getCourseId());
    			setQuestionid(res.getQuestionDetails().getQuestionId());
    			setResponseid(res.getResponceId());
    			setQuestiontype(res.getQtype());
    			setEmplid(res.getEmplid());
    			setQuesResponse(res.getQuesResponce());
    			setSubResponse(res.getSubjectiveResponce());
    			try {
    				setMarksobtained( (res.getMarksobtained() == 0.0)?0.0:res.getMarksobtained() );
    			}catch(Exception e) {
    				setMarksobtained(0);
    			}
    		}};
    	}
    }
    
    /******* SANDHYA**********
    @PostMapping("/assesementreschedule")
    @Transactional
    public AssessmentSchedule reScheduleAssessment(@RequestBody AssessmentSchedule schedule) throws ParseException
    {
    	String datept = "yyyy-MM-dd";
    	String timept = "yyyy-MM-dd HH:mm:ss";
    	String resstatus="";
    	ScheduledAssessment assessmentDetails = em.find(ScheduledAssessment.class, schedule.getScheduledid());				//findAssessmentDetailsById(schedule.getAssessmentid());
    	
    	Query q=em.createNativeQuery("select date_format(start_date_time,'%Y-%m-%d %H:%i')-INTERVAL 5 MINUTE from mygurujitst.scheduled_assessment where scheduled_id=:sid");
    	//q.setParameter("startdatetime", Generation.toDate(schedule.getStartdatetime(), timept));
    	q.setParameter("sid", schedule.getScheduledid());
    	String starttime=(String)q.getSingleResult();
    	
    	long newtime=Generation.toDate(schedule.getStartdatetime(), timept).getTime();
    	long existingtime=Generation.toDate(starttime, timept).getTime();

    	if(((newtime-existingtime)/60000)<=5)
    		resstatus="can't schedule before 5 min:::ERROR";
    
    	if(Generation.toDate(schedule.getStartdatetime(), timept).before(Generation.getCurrentDate()))
    		resstatus="can't schedule in past date ";
    	
    	Query query=em.createNamedQuery("ScheduledAssessment.updateSchedule").setParameter("startdate", Generation.toDate(schedule.getStartdate(),datept)).setParameter("enddate", Generation.toDate(schedule.getEnddate(),datept))
    			.setParameter("starttime", Generation.toDate(schedule.getStartdatetime(),timept)).setParameter("endtime", Generation.toDate(schedule.getEnddatetime(),timept)).setParameter("duration", schedule.getDuration())
    			.setParameter("scheduleid", schedule.getScheduledid());
    	int count=query.executeUpdate();
    	if(count>0)
    	{ 
    		RescheduleHistory reschedule=new RescheduleHistory(0,Generation.toDate(schedule.getStartdate(),datept) , Generation.toDate(schedule.getEnddate(),datept),Generation.toDate(schedule.getStartdatetime(),timept), 
    				Generation.toDate(schedule.getEnddatetime(),timept),schedule.getComment(), schedule.getDuration(),schedule.getCreatedby(),schedule.getCreatedby());
    		reschedule.setRescheduledAssessment(assessmentDetails);
    		em.persist(reschedule);
    		resstatus= "Updated Successfully";
    	}
    	assessmentDetails.setLastUpdatedDate(Generation.getCurrentDate());
    	
    	schedule.setResponsestatus(resstatus);
    	return schedule;
    }*/
    
    /******* SANDHYA**********/
    @PostMapping("/assesementreschedule")
    @Transactional
    public AssessmentSchedule reScheduleAssessment(@RequestBody AssessmentSchedule schedule) throws ParseException
    {
    	String datept = "yyyy-MM-dd";
    	String timept = "yyyy-MM-dd HH:mm:ss";
    	String resstatus="";
    	long schId=(long)em.createQuery("select s.ScheduledId from ScheduledAssessment s where s.assessmentDetails.AssessmentId=:assessId")
    			.setParameter("assessId", schedule.getAssessmentid()).getSingleResult();
    	
    	ScheduledAssessment assessmentDetails = em.find(ScheduledAssessment.class, schId);		
    	Query q=em.createNativeQuery("select date_format(start_date_time,'%Y-%m-%d %H:%i')-INTERVAL 5 MINUTE from scheduled_assessment where scheduled_id=:sid");
    	q.setParameter("sid", schId);
    	//System.out.println((q.getSingleResult().toString()));
    	String starttime=(String)q.getSingleResult();
    	
    		
    	long newtime=Generation.toDate(schedule.getStartdatetime(), timept).getTime();
    	long existingtime=Generation.toDate(starttime, timept).getTime();
    	long futuretimecheck=Generation.getCurrentDate().getTime();

    	if(Math.abs((newtime-existingtime)/60000)<=5)
    		resstatus="can't schedule before 5 min";
    
    	if(Generation.toDate(schedule.getStartdatetime(), timept).before(Generation.getCurrentDate()))
    		resstatus="can't schedule in past date";

		if(Math.abs((newtime-futuretimecheck)/60000)<=15)
			resstatus="15minute";

		System.out.println("Error::"+Math.abs((newtime-futuretimecheck)/60000));
    	em.clear();em.flush();

		if(!(Generation.toDate(schedule.getStartdatetime(), timept).before(Generation.getCurrentDate())) && Math.abs((newtime-futuretimecheck)/60000)>15
		&& Math.abs((newtime-existingtime)/60000)>5) {
			Query query = em.createNamedQuery("ScheduledAssessment.updateReSchedule")
					.setParameter("startdate", Generation.toDate(schedule.getStartdate(), "yyyy-MM-dd"))
					.setParameter("enddate", Generation.toDate(schedule.getEnddate(), "yyyy-MM-dd"))
					.setParameter("starttime", Generation.toDate(schedule.getStartdatetime(), "yyyy-MM-dd HH:mm:ss"))
					.setParameter("endtime", Generation.toDate(schedule.getEnddatetime(), "yyyy-MM-dd HH:mm:ss"))
					.setParameter("duration", schedule.getDuration())
					.setParameter("assessId", schedule.getAssessmentid());


			if (query.executeUpdate() > 0) {
				RescheduleHistory reschedule = new RescheduleHistory(0, Generation.toDate(schedule.getStartdate(), datept), Generation.toDate(schedule.getEnddate(), datept), Generation.toDate(schedule.getStartdatetime(), timept),
						Generation.toDate(schedule.getEnddatetime(), timept), schedule.getComment(), schedule.getDuration(), schedule.getCreatedby(), schedule.getCreatedby());
				reschedule.setRescheduledAssessment(assessmentDetails);
				em.persist(reschedule);
				resstatus = "Updated Successfully";
			}
		}
    	assessmentDetails.setLastUpdatedDate(Generation.getCurrentDate());
    	ScheduledAssessment asdt = em.find(ScheduledAssessment.class, schId);
    	//System.out.println(asdt.getEndDateTime());
    	schedule.setResponsestatus(resstatus);
    	return schedule;
    }
    
    /****Sandhya****/
	@SuppressWarnings("unchecked")
	@GetMapping("/getScheduleByCourseandBatchId/{courseId}/{batchId}")
	public List<ScheduleListTable> getSchedulebyCourseIdandBatchid(@PathVariable long courseId,@PathVariable long batchId)
	{
		Query qr=em.createNativeQuery("select distinct f.course_id,f.course_title,se.batch_id,g.batch_code,g.batchtitle,"
				+ "d.scheduled_id,d.start_date_time, d.end_date_time,d.duration,d.assessment_id, e.title,e.descr,(case when d.start_date_time <=:currenttime then 'CONDUCTED' else 'NOT CONDUCTED' end) as status from student_details sd inner join student_enrollment se on sd.student_id=se.student_id"
				+ "    left join scheduled_assessment_mapping c on se.batch_id=c.batch_id"
				+ "    inner join scheduled_assessment d on c.scheduled_id=d.scheduled_id"
				+ "    inner join assessment_details e on d.assessment_id=e.assessment_id"
				+ "    inner join course_details f on e.course_id=f.course_id"
				+ "    inner join batch_details g on g.batch_id=c.batch_id where f.course_id=:courseid and g.batch_id=:batchid");
		
		qr.setParameter("currenttime", Generation.getCurrentDate()).setParameter("courseid", courseId).setParameter("batchid", batchId);
		
		List<Object[]> data = qr.getResultList();
		return data.stream().map(x -> {
			return new ScheduleListTable(String.valueOf(((BigInteger)x[0]).longValue()), (String)x[1], String.valueOf(((BigInteger)x[2]).longValue()),
					(String)x[3], (String)x[4], String.valueOf(((BigInteger)x[5]).longValue()),Generation.dateAsString((Date) x[6]), Generation.dateAsString((Date) x[7]),String.valueOf(x[8]),String.valueOf(((BigInteger)x[9]).longValue()),(String)x[10],(String)x[11],(String)x[12]);
		}).collect(Collectors.toList());
	
	}
	
	@GetMapping("/getScheduleforStudent/{studentId}/{courseId}/{batchId}")
	public List<ScheduleListTable> getScheduleForStudent(@PathVariable long studentId,@PathVariable long courseId,@PathVariable long batchId)
	{
		Query query=em.createNativeQuery("select distinct a.student_id,f.course_id,b.batch_id,g.batchtitle,d.scheduled_id,f.course_title,d.start_date_time,"
				+ " d.end_date_time,e.title,e.descr,"
				+ "    case when d.start_date_time < NOW() then 'CONDUCTED' else 'NOT CONDUCTED' end as status"
				+ "  from student_details a inner join student_enrollment b on a.student_id = b.student_id"
				+ "    left join scheduled_assessment_mapping c on b.batch_id = c.batch_id"
				+ "    inner join scheduled_assessment d on c.scheduled_id = d.scheduled_id"
				+ "    inner join assessment_details e on d.assessment_id = e.assessment_id"
				+ "    inner join course_details f on e.course_id = f.course_id"
				+ "    inner join batch_details g on g.batch_id = c.batch_id"
				+ " where a.student_id =:studentId and f.course_id =:courseId and g.batch_id =:batchId");
		
		query.setParameter("studentId", studentId).setParameter("courseId", courseId).setParameter("batchId", batchId);
		List<Object[]> data=query.getResultList();
		return data.stream().map(x -> {
			ScheduleListTable scheduletable= new ScheduleListTable(String.valueOf(((BigInteger)x[1]).longValue()), (String)x[2], String.valueOf(((BigInteger)x[3]).longValue()),
					(String)x[4], (String)x[5], String.valueOf(((BigInteger)x[6]).longValue()),Generation.dateAsString((Date) x[7]), Generation.dateAsString((Date) x[8]),String.valueOf(x[9]),String.valueOf(((BigInteger)x[10]).longValue()),(String)x[11],(String)x[12],(String)x[13]);		
			scheduletable.setStudentid(String.valueOf(((BigInteger)x[0]).longValue()));
			return scheduletable;
		}).collect(Collectors.toList());
	}
	
	/*******SANDHYA********/
	@PostMapping("/deleteSchedule/{assessmentId}")
	@Transactional
	public ResponceMessage deleteAssessmentSchedule(@PathVariable long assessmentId)
	{
		Query qr=em.createQuery("select sch from ScheduledAssessment sch where sch.assessmentDetails.AssessmentId=:assessId")
				.setParameter("assessId", assessmentId);
		ScheduledAssessment sa=(ScheduledAssessment)qr.getSingleResult();
		long scheduleId=sa.getScheduledId();
		ResponceMessage responsemsg=null;
		Query query=em.createNamedQuery("ScheduledAssessmentMapping.updateCancelScheduleStatus");
		query.setParameter("scheduleId", scheduleId);
		ScheduledAssessment schedule=em.find(ScheduledAssessment.class, scheduleId);
		Date starttime=schedule.getStartDateTime();
		schedule.setIsActive("N");
		if(starttime.after(Generation.getCurrentDate()))
		{
			if(query.executeUpdate()>0) {
				em.merge(schedule);
				responsemsg= new ResponceMessage("Schedule deleted");
				}
		}
		else
			responsemsg= new ResponceMessage("Schedule can't be deleted on previous date");
			
		return responsemsg;
	}
	
	/*****SHANTANU******/
	@GetMapping("/getbatchbyscheduleid/{scheduleid}")
	public List<ScheduleListTable> getbatchbyscheduleid(@PathVariable("scheduleid") long scheduleid)
	{
		return assessmaprepo.getBatchByScheduleId(scheduleid).stream().map(x -> {
			return new ScheduleListTable() {{
				setScheduleid(""+x.getScheduledAssessment().getScheduledId());
				setBatchid(""+x.getBatchDetails().getBatchId());
				setBatchcode(x.getBatchDetails().getBatchCode());
				setBatchtitle(x.getBatchDetails().getBatchtitle());
				setCourseid(""+x.getCourseDetails().getCourseId());
				setAssessmentid(""+x.getScheduledAssessment().getAssessmentDetails().getAssessmentId());
				setCoursetitle(x.getCourseDetails().getCourseTitle());
				setStartdatetime(Generation.dateAsString(x.getScheduledAssessment().getStartDateTime()));
				setEnddatetime(Generation.dateAsString(x.getScheduledAssessment().getEndDate()));
			}};
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/getbatchbyassessmentid/{assessmentid}")
	public List<ScheduleListTable> getbatchbyassessmentid(@PathVariable("assessmentid") long assessmentid)
	{
		return assessmaprepo.getBatchByAssessmentId(assessmentid).stream().map(x -> {
			return new ScheduleListTable() {{
				setScheduleid(""+x.getScheduledAssessment().getScheduledId());
				setBatchid(""+x.getBatchDetails().getBatchId());
				setBatchcode(x.getBatchDetails().getBatchCode());
				setBatchtitle(x.getBatchDetails().getBatchtitle());
				setCourseid(""+x.getCourseDetails().getCourseId());
				setAssessmentid(""+x.getScheduledAssessment().getAssessmentDetails().getAssessmentId());
				setCoursetitle(x.getCourseDetails().getCourseTitle());
				setStartdatetime(Generation.dateAsString(x.getScheduledAssessment().getStartDateTime()));
				setEnddatetime(Generation.dateAsString(x.getScheduledAssessment().getEndDate()));
			}};
		}).collect(Collectors.toList());
	}
	
	//@SuppressWarnings("unchecked")
	@RequestMapping("/evaluateAutoAssessment/{assessid}")
    public ResponceMessage evaluateAutoCheckAssessment(@PathVariable("assessid") long assessid)
    {
	    try
	    {
			StoredProcedureQuery sp = em.createStoredProcedureQuery("autoEvaluationByAssessmentId");
	    	sp.registerStoredProcedureParameter("assessmentid", Long.class, ParameterMode.IN);
	    	sp.setParameter("assessmentid", assessid);
	    	sp.execute();
	    	return new ResponceMessage() {{setMessage("SUCCESS");}};
    	}
	    catch(Exception e)
	    {
    		return new ResponceMessage() {{setMessage("ERROR");}};
    	}
    }
}