package com.srdt.myguruji.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.*;
import javax.transaction.Transactional;

import com.srdt.myguruji.enitity.StudentDetails;
import com.srdt.myguruji.model.*;
import com.srdt.myguruji.utility.Generation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srdt.myguruji.enitity.BatchDetails;
import com.srdt.myguruji.enitity.FacultyDetails;
import com.srdt.myguruji.enitity.FacultyTaggingDetails;
import com.srdt.myguruji.repository.BatchRepository;
import com.srdt.myguruji.repository.FacultyDetailsRepository;
import com.srdt.myguruji.repository.MappingRepository;

@RestController
@RequestMapping("/api/faculty")
public class FacutlyDetailsResource {

	@Autowired
	EntityManager em;
	@Autowired
	FacultyDetailsRepository facultyrep;
	@Autowired
	BatchRepository batchrep;
	
	@Autowired
	MappingRepository maprep;	
	
	@PostMapping("/syncfaculty")
	public List<Faculty> syncFaculty(@RequestBody List<Faculty> faculties)
	{
		return maprep.syncFaculty(faculties);
	}
	
	@PostMapping("/syncmapping")
	public List<FacultyTag> syncMapping(@RequestBody List<FacultyTag> tags)
	{
		return maprep.facultyTagging(tags);
	}
	
	@PostMapping("/create")
	public ResponceMessage create(@RequestBody Faculty faculties)
	{
		maprep.syncFaculty(Arrays.asList(faculties));
		return new ResponceMessage("Success");
	}
	
	@PostMapping("/search")
	public List<Faculty> search(@RequestBody Faculty faculty)
	{
		String fid = Long.toString(faculty.getFacultyid());
		if(faculty.getFacultyid() == 0)
		{
			fid = "";
		}
		return facultyrep.searchFaculty(fid, faculty.getFacultycode(), faculty.getEmplid(), faculty.getFirstname(), faculty.getEmailaddr(), faculty.getPrimarycontact(), faculty.getDesignation());
	}

	/*************************SANDHYA*******************/
	@GetMapping("/getFacultydetailsByEmplId/{emplid}")
	public FacultyInfo getfacultydetailByEmpliId(@PathVariable String emplid)
	{
		FacultyDetails facultyDetails=facultyrep.getFacultynameByEmplid(emplid);
		long facultyid=facultyDetails.getFacultyId();
		return findFacultyDetailsById(facultyid);
	}
	
	@GetMapping("/getfacultydetailsbyid/{FacultyId}")
	public FacultyInfo findFacultyDetailsById(@PathVariable("FacultyId") long FacultyId)
	{
		FacultyDetails facultyDetails = facultyrep.findByFacultyId(FacultyId);
		Faculty faculty = null;
		List<Batches> batches = null;
		List<FacultyTaggingDetails> facultyTaggingDetails = null;
		if(facultyDetails != null)
		{
			facultyTaggingDetails = facultyDetails.getFacultyTaggingDetails();
			if(facultyTaggingDetails != null)
			{
				batches = facultyTaggingDetails.stream()
				                     .map(x->{
				                    	 BatchDetails batchDetails = x.getBatchDetails();
				                    	 return new Batches(batchDetails.getCourseDetails().getCourseId(), batchDetails.getBatchId(), batchDetails.getBatchtitle(), batchDetails.getBatchCode(), batchDetails.getDescr(), batchDetails.getCreatedBy(), batchDetails.getBatchType(), batchDetails.getBatchYear(), batchDetails.getBatchSeq(),batchDetails.getSection());
				                     })
				                     .collect(Collectors.toList());
			}
			faculty = new Faculty(facultyDetails.getFacultyId(), facultyDetails.getFacultyCode(), facultyDetails.getEmplid(), facultyDetails.getDesignation(), facultyDetails.getPref(), facultyDetails.getFirstName(), facultyDetails.getMidleName(), facultyDetails.getLastName(), facultyDetails.getFullName(), facultyDetails.getEmailAddr(), facultyDetails.getPrimaryContact(), facultyDetails.getSecondaryContact(), facultyDetails.getCreatedBy());
		}
		return new FacultyInfo(faculty, batches);
	}
	
	//Shantanu
	@PostMapping("/deletetagging")
	public ResponceMessage deleteTagging(@RequestBody KeyIDsList ids)
	{
		ArrayList<Long> arr = new ArrayList<>();
		for(long i : ids.getIds())
			arr.add(i);
		
		int rows = maprep.deleteTagging( arr );
		return new ResponceMessage() {{setMessage("rows deleted : "+rows);}};
	}
	
	/*****SANDHYA*****/
	@PostMapping("/updateEmailAndContact/{email}/{contact}/{emplId}")
	@Transactional
	public ResponceMessage updateMailandPhone(@PathVariable String email,@PathVariable String contact,@PathVariable String emplId )
	{
		ResponceMessage msg=null;
		try {
		Query query=em.createNamedQuery("FacultyDetails.updateEmailPhone").setParameter("email", email)
				.setParameter("phone", contact).setParameter("emplid", emplId);
		if(query.executeUpdate()>0)
			msg= new ResponceMessage("Successfully update");
		}
		catch(Exception e) {		
			
			msg= new ResponceMessage("Something wrong");
		}
		return msg;
	}

	@PostMapping("/updateEmailByEmplId")
	public ResponceMessage updateMail(@RequestBody  List<UpdatePhoneEmail> contactlist)
	{
		ResponceMessage msg=null;
		try {
			for(UpdatePhoneEmail list:contactlist)
			{

				int count=0;
				if((list.getContact().trim().length()==0 || list.getContact()==null) && list.getEmplid().trim().length()!=0 && list.getEmplid()!=null)
				{
					 count=facultyrep.updateEmailByEmplId(list.getEmailid(), Generation.getCurrentDate(),list.getEmplid());
				}
				if((list.getEmailid().trim().length()==0 || list.getEmailid()==null) && list.getEmplid().trim().length()!=0 && list.getEmplid()!=null)
				{
					 count=facultyrep.updateContactByEmplId(list.getContact(),Generation.getCurrentDate(),list.getEmplid());
				}

				if(list.getContact().trim().length()>0 && list.getContact()!=null && list.getEmailid().trim().length()!=0 && list.getEmailid()!=null && list.getEmplid().trim().length()!=0 && list.getEmplid()!=null) {
					count=facultyrep.updateEmailPhone(list.getEmailid(),list.getContact(),Generation.getCurrentDate(),list.getEmplid());
				}
				if(count>0)
					msg= new ResponceMessage("Successfully update");
			}

		}
		catch(Exception e) {

			msg= new ResponceMessage("Something wrong::::"+e.getMessage());
		}
		return msg;
	}

	@GetMapping("/getFacultyDetailsForTagging/{emplid}/{batchcode}")
	public List<FacultyBatchInfo> getFacultyTaggingdetails(@PathVariable String emplid,@PathVariable String batchcode )
	{

		if(emplid.trim().length()==0||emplid.equalsIgnoreCase("-"))
			emplid="%";
		if(batchcode.trim().length()==0 ||batchcode.equalsIgnoreCase("-"))
			batchcode="%";

		Query query=em.createNativeQuery("select fd.emplid,concat(fd.first_name,' ',fd.last_name) as fullname, bd.batch_code,bd.descr from faculty_details fd left join faculty_tagging_details ft on fd.faculty_id=ft.faculty_id\n" +
				"left join batch_details bd on bd.batch_id=ft.batch_id\n" +
				"where  fd.emplid like :Emid and bd.batch_code like :BatchCode");

		List<Object[]> crsplan=query.setParameter("Emid",emplid).setParameter("BatchCode",batchcode).getResultList();

		return crsplan.stream().map(x->{
			return new FacultyBatchInfo(x[0].toString(), x[1].toString(), x[2].toString(), x[3].toString());
		}).collect(Collectors.toList());
	}

	@GetMapping("/getFacultyLoginsyncStatus/{emplid}")
	public DoubleResponseModel getFacultySyncStatus(@PathVariable String emplid)
	{
		String status;
		FacultyDetails faculty = (FacultyDetails)em.createQuery("select ft from FacultyDetails ft where ft.Emplid=:emplId")
				.setParameter("emplId",emplid).getSingleResult();
	
		String middlename;
		try{
			middlename=faculty.getMidleName();
		}
		catch(Exception e)
		{
			middlename="";
		}
		String fullname=String.join(" ",Arrays.asList(faculty.getFirstName(),middlename,faculty.getLastName()));
		Long usercount=((Number)(em.createNativeQuery("select count(*) from user_login where login_id=:loginid")
				.setParameter("loginid",emplid).getSingleResult())).longValue();
		if(usercount>0)
			status="Y";
		else
			status="N";

		String email=String.join(" : ",Arrays.asList(faculty.getEmailAddr(),status));
		return new DoubleResponseModel(fullname,email);
	}
	
	@PostMapping("/searchFacultyByCourseOrBatch")
	public List<Faculty> searchFacultyByCourseOrBatch(@RequestBody SearchByCourseBatch search)
	{
		List<FacultyDetails> resp=facultyrep.searchFacultyByCourseOrBatch(Long.parseLong(search.getCourseid()),Long.parseLong(search.getBatchid()));
		return resp.stream().map(x->{
			return new Faculty(x.getFacultyId(),x.getFacultyCode(), x.getEmplid(), x.getDesignation(), x.getPref(), x.getFirstName(), x.getMidleName(), x.getLastName(), x.getFullName(), x.getEmailAddr(), x.getPrimaryContact(), x.getSecondaryContact(), x.getCreatedBy());
		}).collect(Collectors.toList());		
	}
}