package com.srdt.myguruji.resource;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.srdt.myguruji.enitity.StudentEnrollment;
import com.srdt.myguruji.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.srdt.myguruji.enitity.BatchDetails;
import com.srdt.myguruji.enitity.CourseDetails;
import com.srdt.myguruji.enitity.LockedUsers;
import com.srdt.myguruji.repository.BatchRepository;
import com.srdt.myguruji.repository.CoursePlanRepository;
import com.srdt.myguruji.repository.CourseRepository;
import com.srdt.myguruji.repository.LockedUsersRepository;
import com.srdt.myguruji.repository.StudentDetailsRepository;

@RestController
@RequestMapping("/api/lockuser")
public class LockedUserResource
{
	@Autowired
	LockedUsersRepository lockedUsersRepository;
	
	@Autowired
	StudentDetailsRepository stdntRepo;
	
	@Autowired
	BatchRepository batchRepo;
	
	@Autowired
	CoursePlanRepository crseRepo;
	
	@Autowired
	EntityManager em;
	
	@Transactional
	@RequestMapping(value = "/lockStudent", method=RequestMethod.POST)
	public ResponceMessage lockUser(@RequestBody List<LockedUser> userinfo)
	{
		for(LockedUser x : userinfo)
		{
			LockedUsers lockedUsers = new LockedUsers(x.getLockstatusid(), x.getLockstatus(), x.getReason(), x.getResolve(), crseRepo.findCourseByCourseId(x.getCourseid()), batchRepo.findBatchByBatchId(x.getBatchid()), stdntRepo.findStudentById(x.getStudentid()), x.getCreatedby());
			lockedUsersRepository.save(lockedUsers);
		}
		
		return new ResponceMessage("user locked");
	}
	
	@RequestMapping(value = "/getStudentLockStatus/{ID}", method=RequestMethod.GET)
	public LockedStatusResponse getStatus(@PathVariable("ID")long id)
	{
		LockedUsers status =  lockedUsersRepository.getStatusByStudentId(id);
		
		if(status == null)
		{
			return new LockedStatusResponse();
		}
		
		LockedStatusResponse res = new LockedStatusResponse();
		
		res.setCourseid(status.getCourseDetails().getCourseId());
		res.setBatchid(status.getBatchDetails().getBatchId());
		res.setStudentid(status.getStudentDetails().getStudentId());
		res.setLockstatus(status.getLockStatus());
		res.setLockstatusid(status.getLockstatusid());
		res.setCreated_date(status.getCreatedDate());
		res.setReason(status.getReason());
		res.setResolve(status.getResolve());
		
		return res;
	}
	
	@RequestMapping(value = "/unlockStudent/{ID}", method=RequestMethod.GET)
	public ResponceMessage unlockStudent(@PathVariable("ID") long id)
	{
		lockedUsersRepository.unlockUser(id);
		ResponceMessage res = new ResponceMessage();
		res.setMessage("user unlocked");
		return res;
	}


	
	@RequestMapping(value = "/getLockedUsersList", method=RequestMethod.GET)
	public List<LockedUser> getLockedUsersList()
	{
		return lockedUsersRepository.getLockedUsers().stream().map(x ->{
			return new LockedUser() {{
				setLockstatusid(x.getLockstatusid());
				setBatchid(x.getBatchDetails().getBatchId());
				setCourseid(x.getCourseDetails().getCourseId());
				setLockstatus(x.getLockStatus());
				setReason(x.getReason());
				setResolve(x.getResolve());
				setStudentid(x.getStudentDetails().getStudentId());
				setCampusid(x.getStudentDetails().getCampusId());
				String fullname=String.join(" ", Arrays.asList(x.getStudentDetails().getFirstName(),x.getStudentDetails().getLastName()));
				setFullname(fullname);
				setCreatedby(x.getCreatedBy());
			}};
		}).collect(Collectors.toList());
	}


	@GetMapping("/getLockedUsersDetailsByBatchId/{batchid}")
	public List<CommonModelReport> getLockedStudentsDetails(@PathVariable long batchid)
	{

		List<StudentEnrollment> studntenrol=new ArrayList<>();
		try{
			studntenrol=em.createNamedQuery("StudentEnrollment.getStudentListByBatchId").setParameter("batchid",batchid).getResultList();
		}
		catch(Exception e)
		{
			System.out.println("ERROR::"+e.getMessage());
		}
		List<Long> studentids=lockedUsersRepository.getLockedUserStudentIdByBatch(batchid);

		List<CommonModelReport> studentlist=null;
				 studentlist = studentids.stream().map(x -> {
				LockedUsers lockst = lockedUsersRepository.getStatusByStudentId(x);
				String fullname = String.join(" ", Arrays.asList(lockst.getStudentDetails().getFirstName(), lockst.getStudentDetails().getLastName()));
				return new CommonModelReport(lockst.getStudentDetails().getCampusId(), fullname, lockst.getLockStatus(), lockst.getReason(), lockst.getResolve(), String.valueOf(lockst.getStudentDetails().getStudentId()));
			}).collect(Collectors.toList());

		for(StudentEnrollment stdnt:studntenrol)
		{
			if(!(studentids.contains(stdnt.getStudent().getStudentId())))
			{
				String fullname=String.join(" ",Arrays.asList(stdnt.getStudent().getFirstName(),stdnt.getStudent().getLastName()));
				studentlist.add(new CommonModelReport(stdnt.getStudent().getCampusId(),fullname,"N","","",String.valueOf(stdnt.getStudent().getStudentId())));
			}

		}
	return studentlist;
	}
}