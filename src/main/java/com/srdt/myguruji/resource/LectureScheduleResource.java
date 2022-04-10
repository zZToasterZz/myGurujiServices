package com.srdt.myguruji.resource;

import java.text.ParseException;
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
import com.srdt.myguruji.enitity.LectureSchedule;
import com.srdt.myguruji.enitity.UnitDetails;
import com.srdt.myguruji.model.LecSchedule;
import com.srdt.myguruji.model.ResponceMessage;
import com.srdt.myguruji.repository.CoursePlanRepository;
import com.srdt.myguruji.repository.LectureScheduleRepository;
import com.srdt.myguruji.repository.UnitDetailsRepository;
import com.srdt.myguruji.utility.Generation;

@RestController
@RequestMapping("/api/lecschedule")
public class LectureScheduleResource {
	private static String dtpatern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	@Autowired
	protected LectureScheduleRepository schrep;
	@Autowired
	protected CoursePlanRepository planrep;
	@Autowired
	protected UnitDetailsRepository unitrep;
	
	@PostMapping("/create")
	public ResponceMessage create(@RequestBody LecSchedule schdl) throws ParseException
	{
		CourseDetails courseDetails = planrep.findCourseByCourseId(schdl.getCourseid());
		UnitDetails unitDetails = unitrep.findUnitByUnitId(schdl.getUnitid());
		schrep.saveAndFlush(new LectureSchedule(schdl.getLecschid(), schdl.getStarturl(), schdl.getJoinurl(), schdl.getDuration(), schdl.getDescr(), schdl.getTitle(), Generation.toDate(schdl.getStarttime(), dtpatern) , schdl.getUuid(), schdl.getHostid(), schdl.getMeetingid(), schdl.getTypes(), courseDetails, unitDetails, schdl.getCreatedby()));
		return new ResponceMessage("Schedule created");
	}
	
	@GetMapping("/remove/{id}")
	public ResponceMessage delete(@PathVariable("id") long id)
	{
		schrep.deleteLectureSchedule(id);
		return new ResponceMessage("Schedule deleted");
	}
	
	@GetMapping("/getschedulebycourseid/{id}")
	public List<LecSchedule> getByCourseId(@PathVariable("id") long id)
	{
		return schrep.getLectureScheduleByCourseId(id).stream().map(x->{
			return new LecSchedule(x.getLecSchId(), x.getStartUrl(), x.getJoinUrl(), x.getDuration(), x.getDescr(), x.getTitle(), Generation.dateAsString(x.getStartTime()), x.getUuid(), x.getHostId(), x.getMeetingId(), x.getTypes(), x.getCreatedBy(), x.getCourseDetails().getCourseId(), x.getUnitDetails().getUnitId(), x.getUnitDetails().getUnitTitle(), x.getCourseDetails().getCourseTitle());
		}).collect(Collectors.toList());
	}

	@GetMapping("/getschedulebyunitid/{id}")
	public List<LecSchedule> getByUnitId(@PathVariable("id") long id)
	{
		return schrep.getLectureScheduleByUnitId(id).stream().map(x->{
			return new LecSchedule(x.getLecSchId(), x.getStartUrl(), x.getJoinUrl(), x.getDuration(), x.getDescr(), x.getTitle(),Generation.dateAsString(x.getStartTime()), x.getUuid(), x.getHostId(), x.getMeetingId(), x.getTypes(), x.getCreatedBy(), x.getCourseDetails().getCourseId(), x.getUnitDetails().getUnitId(), x.getUnitDetails().getUnitTitle(), x.getCourseDetails().getCourseTitle());
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/getschedulebybatchid/{id}")
	public List<LecSchedule> getByBatchId(@PathVariable("id") long id)
	{
		return schrep.getLectureScheduleByBatchId(id).stream().map(x->{
			return new LecSchedule(x.getLecSchId(), x.getStartUrl(), x.getJoinUrl(), x.getDuration(), x.getDescr(), x.getTitle(), Generation.dateAsString(x.getStartTime()) , x.getUuid(), x.getHostId(), x.getMeetingId(), x.getTypes(), x.getCreatedBy(), x.getCourseDetails().getCourseId(), x.getUnitDetails().getUnitId(), x.getUnitDetails().getUnitTitle(), x.getCourseDetails().getCourseTitle());
		}).collect(Collectors.toList());
	}
	
	//SANDHYA
	@PostMapping("/updateLecture/{dscr}/{title}/{lecId}")
	public ResponceMessage updateLecture(@PathVariable("dscr")String dscr,@PathVariable("title") String title,@PathVariable("lecId") long lecId)
	{
		if(schrep.updateLectureSchedule(dscr,title,lecId)>0)
			return new ResponceMessage("Schedule updated");
		else
			return new ResponceMessage("not updated");
	}
}
