package com.srdt.myguruji.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srdt.myguruji.enitity.CourseDetails;
import com.srdt.myguruji.enitity.CourseObjectiveDetails;
import com.srdt.myguruji.model.CourseObjective;
import com.srdt.myguruji.model.Courses;
import com.srdt.myguruji.model.ResponceMessage;
import com.srdt.myguruji.model.SearchField;
import com.srdt.myguruji.model.Translate;
import com.srdt.myguruji.repository.CourseDetailsRepository;
import com.srdt.myguruji.repository.CourseObjectiveRepository;

@RestController
@RequestMapping("/api/course")
public class CourseDetailsResource {

	@Autowired
	CourseDetailsRepository coursedetailsrep;
	
	@Autowired
	CourseObjectiveRepository objrep;
	
	@PostMapping("/synccourse")
	public List<Translate> syncCourse(@RequestBody List<Courses> courses)
	{	
		List<CourseDetails> courseDetails = courses.stream()
				                                    .map(x->{
				                                    	return new CourseDetails(x.getCoursecode(), x.getCoursetitle(), x.getCoursedescr(), x.getCreatedby());
				                                    })
				                                    .collect(Collectors.toList());
		coursedetailsrep.saveAll(courseDetails);
		coursedetailsrep.flush();
		List<Translate> newcourses = courseDetails.stream()
				                      .map(x->{
				                    	  return new Translate(x.getCourseId(), x.getCourseTitle(), x.getCourseCode(), x.getCourseDescr());
				                      })
				                      .collect(Collectors.toList());
		return newcourses;
	}
	
	@PostMapping("/create")
	public ResponceMessage create(@RequestBody List<Courses> courses)
	{	
		List<CourseDetails> courseDetails = courses.stream()
				                                    .map(x->{
				                                    	return new CourseDetails(x.getCoursecode(), x.getCoursetitle(), x.getCoursedescr(), x.getCreatedby());
				                                    })
				                                    .collect(Collectors.toList());
		
		coursedetailsrep.saveAll(courseDetails);
		coursedetailsrep.flush();		
		return new ResponceMessage("Course Created");
	}
	
	@GetMapping("/getcourse")
	public List<Translate> getcourse()
	{
		return coursedetailsrep.findAllCourses("%");		
	}
	
	@GetMapping("/getcoursebyemplid/{emplid}")
	public List<Translate> getcourse(@PathVariable("emplid") String emplid)
	{
		return coursedetailsrep.findAllCourses(emplid);
	}
	
	@GetMapping("/getcourse/{CourseId}")
	public Translate getcourse(@PathVariable("CourseId") long CourseId)
	{
		return coursedetailsrep.findCourseByCourseId(CourseId);		
	}
	
	@GetMapping("/getcoursedetails")
	public List<Courses> getcoursedetails()
	{
		return coursedetailsrep.findAllCourseDetails("%");		
	}
	
	@GetMapping("/getcoursedetailsbyemplid/{Emplid}")
	public List<Courses> getcoursedetails(@PathVariable("Emplid") String Emplid)
	{
		return coursedetailsrep.findAllCourseDetails(Emplid);		
	}
	
	@PostMapping("/search")
	public List<Courses> search(@RequestBody SearchField search)
	{
		String id = "";
		if(search.getId() != 0)
		{
			id = Long.toString(search.getId());
		}
		return coursedetailsrep.searchCourses(id,search.getCode(),search.getTitle(),search.getEmplid());		
	}
	
	@PostMapping("/update")
	public ResponceMessage update(@RequestBody Courses courses)
	{
		coursedetailsrep.updateCourseDetails(courses.getCoursetitle(), courses.getCoursedescr(), courses.getCoursecode(), courses.getCourseid());
		return new ResponceMessage("Details Updated");
	}
	
	@PostMapping("/remove")
	public ResponceMessage remove(@RequestBody List<Long> courseid)
	{
		coursedetailsrep.deleteCourseDetailsByCourseId(courseid);
		return new ResponceMessage("Course details removed");
	}
	
	@PostMapping("/addcourseobjective")
	public List<CourseObjective> addCourseObjective(@RequestBody List<CourseObjective> objectives)
	{
		
		List<CourseObjectiveDetails> courseObjectiveDetails =  objectives.stream().map(x->
											                   {
											                	   long courseid = x.getCourseid();
											               		   CourseDetails courseDetails = coursedetailsrep.findCourseDetailsByCourseId(courseid); 
											                	   return new CourseObjectiveDetails(x.getDescr(), courseDetails, x.getCode(), x.getCreatedby());
											                   })
											                    .collect(Collectors.toList());
		courseObjectiveDetails = objrep.saveAll(courseObjectiveDetails);
		return courseObjectiveDetails.stream()
		                       .map(x->{
		                    	   return new CourseObjective(x.getObjectiveId(), x.getCourseDetails().getCourseId(), x.getDescr());
		                       })
		                       .collect(Collectors.toList());		
	}
	
	@GetMapping("/getcourseobjectivebycourseid/{CourseId}")
	public List<Translate> findCourseObjectiveByCourseId(@PathVariable("CourseId") long CourseId)
	{
		return objrep.findCourseObjectiveByCourseId(CourseId);
	}
	
	@PostMapping("/removecourseobjective")
	public ResponceMessage removeCourseObjective(@RequestBody List<Long> CourseId)
	{
		objrep.deleteByObjectiveId(CourseId);
		return new ResponceMessage("Selected course objective removed");
	}
}
