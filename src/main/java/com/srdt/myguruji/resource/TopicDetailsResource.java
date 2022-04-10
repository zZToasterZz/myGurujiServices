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
import com.srdt.myguruji.enitity.SyllabusPlan;
import com.srdt.myguruji.model.ResponceMessage;
import com.srdt.myguruji.model.SearchField;
import com.srdt.myguruji.model.SysUnit;
import com.srdt.myguruji.model.Topic;
import com.srdt.myguruji.model.Translate;
import com.srdt.myguruji.repository.CoursePlanRepository;
import com.srdt.myguruji.repository.CourseRepository;
import com.srdt.myguruji.repository.SyllabusPlanRepository;
import com.srdt.myguruji.repository.TopicDetailsRepository;

@RestController
@RequestMapping("/api/topic")
public class TopicDetailsResource {

	@Autowired
	CourseRepository courserep;
	
	@Autowired
	TopicDetailsRepository topipresp;
	
	@Autowired
	SyllabusPlanRepository  sysrep;
	
	@Autowired
	CoursePlanRepository plnrep;
	
	@PostMapping("/create")
	public ResponceMessage create(@RequestBody List<Topic> topics)
	{
		courserep.addTopic(topics);
		return new ResponceMessage("Topics created");
	}
	
	@PostMapping("/synctopic")
	public List<Topic> syncTopic(@RequestBody List<Topic> topics)
	{
		return courserep.syncTopic(topics);
	}
	
	@PostMapping("/syncsysunit")
	public List<SysUnit> syncsysunit(@RequestBody List<SysUnit> units)
	{
		CourseDetails courseDetails = plnrep.findCourseByCourseId(units.get(0).getCourseid());
		List<SyllabusPlan> plans = units.stream().map(x->{
			return new SyllabusPlan(0, x.getSysunitid(),x.getTitle(),x.getDescr(), courseDetails);
		}).collect(Collectors.toList());
		return sysrep.saveAll(plans).stream().map(x->{
			return new SysUnit(x.getSysGenId(), units.get(0).getCourseid(), x.getSysUnitId(),x.getTitle(),x.getDescr());
		}).collect(Collectors.toList());
	}
	
	@PostMapping("/update")
	public ResponceMessage update()
	{
		return new ResponceMessage("Topics detail updated");
	}
	
	@PostMapping("/remove")
	public ResponceMessage remove(@RequestBody List<Long> topicid)
	{
		topipresp.removeTopics(topicid);
		return new ResponceMessage("Topics removed");
	}
	
	@GetMapping("/gettopics")
	public List<Topic> getTopics()
	{
		return topipresp.findAllTopicDetails().stream()
                .map(x->{
              	  return new Topic(x.getTopicId(),x.getCourseDetails().getCourseId(), x.getShortDescr(), x.getLongDescr(), x.getCreatedBy(),x.getSysUnitId());
                })
                .collect(Collectors.toList());
	}
	
	@GetMapping("/gettopicsbycourseid/{courseid}")
    public List<Translate> getTopics(@PathVariable("courseid") long courseid)
    {
    	return topipresp.findTopicDetailsByCourseId(courseid);
    }
	
	@PostMapping("/findtopicsbycourseidandtopicid")
	public List<Topic> findTopicsByCourseIdAndTopicId(@RequestBody SearchField field)
	{			
		if(field.getTopicids().size() == 0)
		{
			field.getTopicids().add(new Long(0));
		}
		return topipresp.findTopicsByCourseIdAndTopicId(field.getCourseid(),field.getTopicids()).stream()
                .map(x->{
              	  return new Topic(x.getTopicId(),x.getCourseDetails().getCourseId(), x.getShortDescr(), x.getLongDescr(), x.getCreatedBy(),x.getSysUnitId());
                })
                .collect(Collectors.toList());
	}
}
