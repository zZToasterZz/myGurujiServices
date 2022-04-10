package com.srdt.myguruji.resource;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.srdt.myguruji.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srdt.myguruji.enitity.CourseDetails;
import com.srdt.myguruji.enitity.CoursePlan;
import com.srdt.myguruji.repository.CourseContentRepository;
import com.srdt.myguruji.repository.CoursePlanRepository;
import com.srdt.myguruji.repository.CourseRepository;
import com.srdt.myguruji.repository.UnitContentRepository;

@RestController
@RequestMapping("api/courseplan")
public class CoursePlanResource {
	
	@Autowired
	CoursePlanRepository resp;
	
	@Autowired
	EntityManager em;
	
	@Autowired
	CourseRepository courserep;
	
	@Autowired
	UnitContentRepository untcntrep;
	
	@Autowired
	CourseContentRepository crscntrep;
	
	@PostMapping("/create")
	public ResponceMessage create(@RequestBody Plan plan) throws ParseException
	{	
		courserep.setAptflag(false);
		long id = courserep.CreateCoursePlan(plan);
		return new ResponceMessage("Succcess : "+id);
	}	
	
	@GetMapping("/getcourseplandetails/{planid}")
	public List<Plan> findCoursePlanDetails(@PathVariable("planid") long planid)
	{
		return resp.findCoursePlanDetailsbyCoursePlanId(planid).stream()
		              .map(x->
		              {
		            	  List<Books> books = x.getCourseBooks().stream()
		            			                              .map(y->{
		            			                            	  return new Books(y.getBookId(),y.getBookTitle(), y.getBookDescr(), y.getBookAuthor(), y.getBookType(), x.getCoursePlanId());
		            			                              }).collect(Collectors.toList());
		            	 
		            	  
		            	  List<Unit> unitDetails = x.getUnitDetails().stream()
		            			                                     .map(y->{
		            			                                    	 BigInteger ucc = untcntrep.getUnitContentCount(y.getUnitId());
		            			                                    	 return new Unit(y.getUnitTitle(), y.getUnitDescr(), y.getCreatedBy(), y.getUnitId(), y.getCoursePlan().getCoursePlanId(),null,y.getObjectives(),y.getTopics(),y.getObjectivesId(),y.getTopicsId(),ucc.intValue());
		            			                                     })
		            			                                     .collect(Collectors.toList());
		            	  CourseDetails courseDetails = x.getCourseDetails();
		            	  BigInteger ccc = crscntrep.getCourseContentCount(courseDetails.getCourseId());
		            	  return new Plan(x.getCoursePlanTitle(),x.getCoursePlanDescr(), x.getCreatedBy(), x.getCourseDetails().getCourseId(), x.getCoursePlanId(),books,unitDetails,x.getPlanCode(),x.getLecCount(),x.getBatchDetails().getBatchId(),ccc.intValue(),x.getBatchDetails().getBatchtitle(),courseDetails.getCourseTitle(),x.getBatchDetails().getBatchCode());
		              })
		              .collect(Collectors.toList());
	}
	@GetMapping("/getcourseplandetailsbybatchid/{batchid}")
	public List<Plan> findCoursePlanDetailsByBatchId(@PathVariable("batchid") long batchid)
	{
		return resp.findCoursePlanDetailsbyBatchId(batchid).stream()
		              .map(x->
		              {
		            	  List<Books> books = x.getCourseBooks().stream()
		            			                              .map(y->{
		            			                            	  return new Books(y.getBookId(),y.getBookTitle(), y.getBookDescr(), y.getBookAuthor(), y.getBookType(), x.getCoursePlanId());
		            			                              }).collect(Collectors.toList());
		            	 
		            	  
		            	  List<Unit> unitDetails = x.getUnitDetails().stream()
		            			                                     .map(y->{
		            			                                    	 BigInteger ucc = untcntrep.getUnitContentCount(y.getUnitId());
		            			                                    	 return new Unit(y.getUnitTitle(), y.getUnitDescr(), y.getCreatedBy(), y.getUnitId(), y.getCoursePlan().getCoursePlanId(),null,y.getObjectives(),y.getTopics(),y.getObjectivesId(),y.getTopicsId(),ucc.intValue());
		            			                                     })
		            			                                     .collect(Collectors.toList());
		            	  CourseDetails courseDetails = x.getCourseDetails();
		            	  BigInteger ccc = crscntrep.getCourseContentCount(courseDetails.getCourseId());
		            	  return new Plan(x.getCoursePlanTitle(),x.getCoursePlanDescr(), x.getCreatedBy(), x.getCourseDetails().getCourseId(), x.getCoursePlanId(),books,unitDetails,x.getPlanCode(),x.getLecCount(),x.getBatchDetails().getBatchId(),ccc.intValue(),x.getBatchDetails().getBatchtitle(),courseDetails.getCourseTitle(),x.getBatchDetails().getBatchCode());
		              })
		              .collect(Collectors.toList());
	}
	@GetMapping("/getcourseplanbycourseid/{courseid}")
	public List<Plan> findCoursePlanByCourseId(@PathVariable long courseid)
	{
		return resp.findCoursePlanByCourseId(courseid).stream()
		              .map(x->
		              {
		            	  List<Books> books = x.getCourseBooks().stream()
		            			                              .map(y->{
		            			                            	  return new Books(y.getBookId(),y.getBookTitle(), y.getBookDescr(), y.getBookAuthor(), y.getBookType(), x.getCoursePlanId());
		            			                              }).collect(Collectors.toList());
		            	 
		            	  
		            	  List<Unit> unitDetails = x.getUnitDetails().stream()
		            			                                     .map(y->{	
		            			                                    	 return new Unit(y.getUnitTitle(), y.getUnitDescr(), y.getCreatedBy(), y.getUnitId(), y.getCoursePlan().getCoursePlanId(),null,y.getObjectives(),y.getTopics(),y.getObjectivesId(),y.getTopicsId());
		            			                                     })
		            			                                     .collect(Collectors.toList());
		            	  CourseDetails courseDetails = x.getCourseDetails();
		            	  return new Plan(courseDetails.getCourseTitle(), courseDetails.getCourseDescr(), x.getCreatedBy(), x.getCourseDetails().getCourseId(), x.getCoursePlanId(),books,unitDetails,x.getPlanCode(),x.getLecCount(),x.getBatchDetails().getBatchId());
		              })
		              .collect(Collectors.toList());
	}
	
	@GetMapping("/getcourseplan")
	public List<Translate> getCoursePlan()
	{
		return resp.findCoursePlan();
	}
	
	@GetMapping("/getcourseplan/{courseid}")
	public List<Translate> getCoursePlan(@PathVariable("courseid") long courseid)
	{
		return resp.findCoursePlanbyCourseId(courseid);
	}
	
	@PostMapping("/update")
    public ResponceMessage update(@RequestBody Plan plan) throws ParseException
    {
		courserep.setAptflag(true);
		courserep.CreateCoursePlan(plan);
    	return new ResponceMessage("Course plan updated");
    }
	
	@GetMapping("/remove/{planid}")
	@Transactional
	public ResponceMessage remove(@PathVariable("planid") long planid)
	{
		CoursePlan plan = em.find(CoursePlan.class, planid);
		em.remove(plan);
		return new ResponceMessage("Course plan deleted");
	}
	
	@PostMapping("/search")
	public List<PlanSearch> search(@RequestBody PlanSearch search)
	{			
		return resp.findCoursePlanByEmplid(search.getEmplid(), search.getPlancode(), search.getPlantitle(), search.getCourseid());
	}
	
	@GetMapping("/getcoursebooksbycourseid/{courseid}")
	public Set<Translate> getCourseBookByCourseId(@PathVariable("courseid") long courseid)
	{
		final Set<Translate> translates = new HashSet<>();
		translates.clear();
		resp.getCourseBookByCourseId(courseid).stream().forEach(x->{
			x.getCourseBooks().stream().forEach(y->{
				translates.add(new Translate(y.getBookId(), y.getBookTitle()));
			});
		});
		return translates;
	}


	/******** FOR ADMIN PANEL***************/

	@GetMapping("getCoursePlanByCourseCodeandBatchcode/{coursecode}/{batchcode}")
	public List<CoursePlanInfo> getCoursePlanInfo(@PathVariable String coursecode, @PathVariable String batchcode)
	{
		if(coursecode.trim().length()==0||coursecode.equalsIgnoreCase("-"))
			coursecode="%";
		if(batchcode.trim().length()==0 ||batchcode.equalsIgnoreCase("-"))
			batchcode="%";

		Query query=em.createNativeQuery("select c.course_plan_id,c.course_plan_title, c.plan_code,c.course_plan_descr,d.course_id,d.course_code,b.batch_code, " +
				"  concat(c.created_by,' : ',f.first_name,' ',f.last_name) as created_by from course_plan c left join course_details d on c.course_id=d.course_id " +
				"  left join batch_details b on b.batch_id=c.batch_id left join faculty_details f on f.emplid=c.created_by " +
				"  where d.course_code like :CourseCode and b.batch_code like :BatchCode");
		List<Object[]> crsplan=query.setParameter("CourseCode",coursecode).setParameter("BatchCode",batchcode).getResultList();

		return crsplan.stream().map(x->{
			Long rowcnt=((Number)em.createNativeQuery("select count(*) from question_details where course_id=:crsid")
					.setParameter("crsid",x[4].toString()).getSingleResult()).longValue();
			String questionstatus="N";
			if(rowcnt>0)
				questionstatus="Y";
			//CoursePlanInfo(String planid, String plantitle, String coursecode, String createdby, String questionstatus, String batchcode, String plancode) {
			//cplanid-0,plan-title--1,plan-code-2,plan-desc-3,coursecode-4,batch-code-5,createdby-6
			return new CoursePlanInfo(x[0].toString(), x[1].toString(), x[5].toString(), x[7].toString(), questionstatus, x[6].toString(), x[2].toString());
		}).collect(Collectors.toList());

	}
}
