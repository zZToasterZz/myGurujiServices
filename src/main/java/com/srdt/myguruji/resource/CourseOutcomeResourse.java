package com.srdt.myguruji.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srdt.myguruji.enitity.CourseDetails;
import com.srdt.myguruji.enitity.CourseOutcome;
import com.srdt.myguruji.model.ResponceMessage;
import com.srdt.myguruji.model.Translate;
import com.srdt.myguruji.repository.CourseDetailsRepository;
import com.srdt.myguruji.repository.CourseOutcomeRepository;
import com.srdt.myguruji.utility.MgException;

@RestController
@RequestMapping("/api/courseoutcome")
public class CourseOutcomeResourse {
	
	@Autowired
	protected EntityManager em;
	@Autowired
	protected CourseOutcomeRepository outrep;
	@Autowired
	protected CourseDetailsRepository crsrep;
	
	@PostMapping("/create")
	@MgException
	@Transactional
	public ResponceMessage create(@RequestBody List<Map<String, String>> outcomes)
	{
		List<CourseOutcome> courseOutcomes = outcomes.stream().map(x->{
			long id=0;
			try {
				id=Long.parseLong(x.get("id"));
			}catch(Exception e)
			{
				id=0;
			}
			CourseDetails courseDetails = crsrep.findCourseDetailsByCourseId(Long.parseLong(x.get("courseid")));			
			return new CourseOutcome(id, x.get("code"), x.get("title"), x.get("descr"), courseDetails);
		}).collect(Collectors.toList());
		outrep.saveAll(courseOutcomes);
		
		return new ResponceMessage("success");
	}
	
	@PostMapping("/synccourseoutcomes")
	@MgException
	@Transactional
	public List<Map<String, Object>> syncCourseOutcomes(@RequestBody List<Map<String, String>> outcomes)
	{
		
		
		List<CourseOutcome> courseOutcomes = outcomes.stream().map(x->{
			CourseDetails courseDetails = crsrep.findCourseDetailsByCourseId(Long.parseLong(x.get("courseid")));
			return new CourseOutcome(0, x.get("code"), x.get("title"), x.get("descr"), courseDetails);
		}).collect(Collectors.toList());
		return outrep.saveAll(courseOutcomes).stream().map(x->{
			Map<String, Object> outs = new HashMap<>();
			outs.put("courseid", x.getCourseDetails().getCourseId());
			outs.put("title", x.getOutcomeTitle());
			outs.put("code", x.getOutcomeCode());
			outs.put("descr", x.getOutcomeDescr());
			outs.put("outcomeid", x.getOutcomeId());
			return outs;
		}).collect(Collectors.toList());		
		
	}
	
	@GetMapping("/getcourseoutcomebycourseid/{courseid}")
	public List<Translate> getCourseOutcomeByCourseId(@PathVariable("courseid") long courseid)
	{
		return outrep.getCourseOutcomeByCourseId(courseid);
	}
	
	@PostMapping("/remove")
	public ResponceMessage remove(@RequestBody List<Long> ids)
	{
		outrep.removeById(ids);
		return new ResponceMessage("success");
	}
}
