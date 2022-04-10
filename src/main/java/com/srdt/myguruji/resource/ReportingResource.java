package com.srdt.myguruji.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import com.srdt.myguruji.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srdt.myguruji.enitity.CourseDetails;
import com.srdt.myguruji.enitity.FacultyDetails;
import com.srdt.myguruji.enitity.QuestionType;
import com.srdt.myguruji.model.reporting.ContentData;
import com.srdt.myguruji.model.reporting.ContentDataSet;
import com.srdt.myguruji.model.reporting.ContentDataSingle;
import com.srdt.myguruji.model.reporting.QuesBankData;
import com.srdt.myguruji.model.reporting.QuesBankSingle;
import com.srdt.myguruji.repository.FacultyDetailsRepository;
import com.srdt.myguruji.repository.QuestionTypeRepository;
import com.srdt.myguruji.service.BiReportingService;

@RestController
@RequestMapping("/api/bireports")
public class ReportingResource
{
	@Autowired
	BiReportingService repo;
	@Autowired
	QuestionTypeRepository qtr;
	@Autowired
	FacultyDetailsRepository facultyRepo;
	
	@Autowired
	EntityManager em;
	
	/*@GetMapping(value = "/contentdata", produces = {"application/xml","application/json"})
	public List<ContentData> contentdata()
	{
		return repo.contentdata();
	}*/
	
	@PostMapping(value = "/contentdata", produces = {"application/json","application/xml"})
	public List<ContentData> contentdata(@RequestBody ContentDataSet cds)
	{
		return repo.contentdata(cds.getPnum(),cds.getCrsecode(),cds.getBatchid());
	}
	
	@PostMapping(value = "/contentdatasingle", produces = {"application/json","application/xml"})
	public ContentDataSingle contentdatasingle(@RequestBody ContentDataSet cds)
	{
		return repo.contentdatasingle(cds.getPnum(),cds.getCrsecode(),cds.getBatchid());
	}
	
	@GetMapping(value = "/quesbankdata/{facid}/{crseid}", produces = {"application/json","application/xml"})
	public QuesBankData quesbankdata(@PathVariable("facid") String facid, @PathVariable("crseid") String crse_id)
	{
		int i=0;
		List<String> cols=new ArrayList<>();
		List<String[]> data=new ArrayList<String[]>();
		List<String> datas=new ArrayList<>();
		List<QuestionType> qt=qtr.getactivetype();
		cols.add("{\"title\": \" \"} ");
		//cols.add(\"Topic Description\");
		cols.add("{\"title\": \"Topic Description\"}");
		
		for(QuestionType x:qt)
			cols.add("{\"title\": \""+x.getType()+"\"}");
		cols.add("{\"title\":\"Total Questions in Topic\"}");
		String[] col=cols.toArray(new String[0]);
		
		List<Object[]> topic=repo.gettopics(crse_id);
		for(Object[] x:topic)
		{
			i++;
			datas.add(String.valueOf(i));
			datas.add(x[1].toString());
			int tcnt=0;
			for(QuestionType y:qt)
			{
				String cnt=repo.getquestioncount(x[0].toString(), ""+y.getTypeId());
				datas.add(cnt);
				tcnt+=Integer.parseInt(cnt);
			}
			datas.add(String.valueOf(tcnt));
			data.add(datas.toArray(new String[0]));
			datas.clear();
		}
		
		return new QuesBankData(col,data);
	}

	@GetMapping(value = "/quesbankdatabyfaculty/{facid}/{crseid}", produces = {"application/json","application/xml"})
	public QuesBankData quesbankdatabyfaculty(@PathVariable("facid") String facid, @PathVariable("crseid") String crse_id)
	{
		FacultyDetails f = facultyRepo.getFacultynameByEmplid(facid);
		String created_by = facid+" : "+f.getFirstName()+" "+f.getLastName();

		List<Courses> cd = new ArrayList<>();

		if(crse_id.equals("-"))
		{
			String fid = ""+f.getFacultyId();
			cd = facultyRepo.getCoursesByFacultyId(f.getEmplid());
		}

		int i=0;
		List<String> cols=new ArrayList<>();
		List<String[]> data=new ArrayList<String[]>();
		List<String> datas=new ArrayList<>();
		List<QuestionType> qt=qtr.getactivetype();
		cols.add("{\"title\": \" \"} ");
		cols.add("{\"title\": \"Created By\"}");
		cols.add("{\"title\": \"Course Description\"}");
		cols.add("{\"title\": \"Topic Description\"}");

		for(QuestionType x : qt)
			cols.add("{\"title\": \""+x.getType()+"\"}");
		cols.add("{\"title\":\"Total Questions in Topic\"}");
		String[] col=cols.toArray(new String[0]);

		List<Object[]> topic=new ArrayList<>();
		if(!crse_id.equals("-"))
		{
			topic.addAll(repo.gettopics(crse_id));
		}
		else
		{
			for(Courses c : cd)
			{
				topic.addAll(repo.gettopics(""+c.getCourseid()));
			}
		}

		for(Object[] x:topic)
		{
			i++;
			datas.add(String.valueOf(i));
			datas.add(created_by);
			datas.add(x[2].toString());
			datas.add(x[1].toString());
			int tcnt=0;
			for(QuestionType y:qt)
			{
				String cnt=repo.getQuestionCountByFaculty(x[0].toString(), ""+y.getTypeId(),facid);
				datas.add(cnt);
				tcnt+=Integer.parseInt(cnt);
			}
			datas.add(String.valueOf(tcnt));
			data.add(datas.toArray(new String[0]));
			datas.clear();
		}

		return new QuesBankData(col,data);
	}

	/*
	@GetMapping(value = "/quesbankdatabyfaculty/{facid}/{crseid}", produces = {"application/json","application/xml"})
	public QuesBankData quesbankdatabyfaculty(@PathVariable("facid") String facid, @PathVariable("crseid") String crse_id)
	{
		FacultyDetails f = facultyRepo.getFacultynameByEmplid(facid);
		String created_by = facid+" : "+f.getFirstName()+" "+f.getLastName();
		
		List<Courses> cd = new ArrayList<>();
		
		if(crse_id.equals("-"))
		{
			String fid = ""+f.getFacultyId();
			cd = facultyRepo.getCoursesByFacultyId(f.getEmplid());
		}
		
		int i=0;
		List<String> cols=new ArrayList<>();
		List<String[]> data=new ArrayList<String[]>();
		List<String> datas=new ArrayList<>();
		List<QuestionType> qt=qtr.getactivetype();
		cols.add("{\"title\": \" \"} ");
		cols.add("{\"title\": \"Created By\"}");
		cols.add("{\"title\": \"Topic Description\"}");
		
		for(QuestionType x : qt)
			cols.add("{\"title\": \""+x.getType()+"\"}");
		cols.add("{\"title\":\"Total Questions in Topic\"}");
		String[] col=cols.toArray(new String[0]);
		
		List<Object[]> topic=new ArrayList<>();
		if(!crse_id.equals("-"))
		{
			topic.addAll(repo.gettopics(crse_id));
		}
		else
		{
			for(Courses c : cd)
			{
				topic.addAll(repo.gettopics(""+c.getCourseid()));
			}
		}
		
		for(Object[] x:topic)
		{
			i++;
			datas.add(String.valueOf(i));
			datas.add(created_by);
			datas.add(x[1].toString());
			int tcnt=0;
			for(QuestionType y:qt)
			{
				String cnt=repo.getQuestionCountByFaculty(x[0].toString(), ""+y.getTypeId(),facid);
				datas.add(cnt);
				tcnt+=Integer.parseInt(cnt);
			}
			datas.add(String.valueOf(tcnt));
			data.add(datas.toArray(new String[0]));
			datas.clear();
		}
		
		return new QuesBankData(col,data);
	}
	*/
	
	@GetMapping(value = "/quesbankdatabycourse/{facid}/{crseid}", produces = {"application/json","application/xml"})
	public QuesBankData quesbankdatabycourse(@PathVariable("facid") String facid, @PathVariable("crseid") long crse_id)
	{
		List<FacultyDetails> factulies = new ArrayList<>();
		FacultyDetails f = new FacultyDetails();
		
		if(facid.equals("-"))
		{
			factulies.addAll(facultyRepo.getFacultyByCourseId(crse_id));
		}
		else
		{
			f = facultyRepo.getFacultynameByEmplid(facid);
		}
		
		int i=0;
		List<String> cols=new ArrayList<>();
		List<String[]> data=new ArrayList<String[]>();
		List<String> datas=new ArrayList<>();
		List<QuestionType> qt=qtr.getactivetype();
		cols.add("{\"title\": \" \"} ");
		cols.add("{\"title\": \"Created By\"}");
		cols.add("{\"title\": \"Course Description\"}");
		cols.add("{\"title\": \"Topic Description\"}");
		
		for(QuestionType x : qt)
			cols.add("{\"title\": \""+x.getType()+"\"}");
		cols.add("{\"title\":\"Total Questions in Topic\"}");
		String[] col=cols.toArray(new String[0]);
		
		List<Object[]> topic=new ArrayList<>();
		topic.addAll(repo.gettopics(""+crse_id));
		
		for(Object[] x:topic)
		{
			if(facid.equals("-"))
			{
				for(FacultyDetails t : factulies)
				{
					i++;
					datas.add(String.valueOf(i));
					datas.add(t.getEmplid()+" : "+t.getFirstName()+" "+t.getLastName());
					datas.add(x[2].toString());
					datas.add(x[1].toString());
					int tcnt=0;
					for(QuestionType y:qt)
					{
						String cnt=repo.getQuestionCountByFaculty(x[0].toString(), ""+y.getTypeId(),t.getEmplid());
						datas.add(cnt);
						tcnt+=Integer.parseInt(cnt);
					}
					datas.add(String.valueOf(tcnt));
					data.add(datas.toArray(new String[0]));
					datas.clear();
				}
			}
			else
			{
				i++;
				datas.add(String.valueOf(i));
				datas.add(f.getEmplid()+" : "+f.getFirstName()+" "+f.getLastName());
				datas.add(x[2].toString());
				datas.add(x[1].toString());
				int tcnt=0;
				for(QuestionType y:qt)
				{
					String cnt=repo.getQuestionCountByFaculty(x[0].toString(), ""+y.getTypeId(),f.getEmplid());
					datas.add(cnt);
					tcnt+=Integer.parseInt(cnt);
				}
				datas.add(String.valueOf(tcnt));
				data.add(datas.toArray(new String[0]));
				datas.clear();
			}
		}
		
		return new QuesBankData(col,data);
	}
	
	@GetMapping(value = "/quesbankdatasingle/{facid}/{crseid}", produces = {"application/json","application/xml"})
	public QuesBankSingle quesbankdatasingle(@PathVariable("facid") String facid, @PathVariable("crseid") String crse_id)
	{
		return repo.quesbankdatasingle(facid, crse_id);
	}


	@GetMapping("/SearchDetailsByEmplidCourseId/{emplid}/{courseid}")
	public List<ContentData> searchdetails(@PathVariable String emplid,@PathVariable String courseid)
	{
		return repo.searchByEmplidOrCourse(emplid,courseid);

	}

	@GetMapping("/getCourseDropdown/{emplid}/{courseid}")
	public List<CommonModelReport> getCourseList(@PathVariable String emplid, @PathVariable String courseid)
	{
		if(emplid.equalsIgnoreCase("-") || emplid.trim().length()==0)
			emplid="%";
		if(courseid.equalsIgnoreCase("-") || courseid.trim().length()==0)
			courseid="%";

		List<Object[]> courselist=em.createNativeQuery("select distinct bd.course_id,cd.course_descr,cd.course_code,concat(fd.emplid,' : ',fd.first_name,coalesce(fd.midle_name,' '),fd.last_name) as fullName from faculty_tagging_details ft \n" +
				"left join batch_details bd on ft.batch_id=bd.batch_id left join course_details cd on cd.course_id=bd.course_id\n" +
				"left join faculty_details fd on fd.faculty_id=ft.faculty_id where fd.emplid like :Emplid and cd.course_id like :CourseId")
				.setParameter("Emplid",emplid).setParameter("CourseId",courseid ).getResultList();
		return courselist.stream().map(x->{
			CommonModelReport cmreport= new CommonModelReport(x[0].toString(),x[1].toString(),x[3].toString());
			cmreport.setDescr4(x[2].toString());
			return cmreport;
		}).collect(Collectors.toList());

	}

	@GetMapping("/getIAQCassessmentstatus/{emplid}/{courseid}")
	public List<CommonModelReport> getAssessmentDetails(@PathVariable String emplid, @PathVariable String courseid)
	{
		if(emplid.equalsIgnoreCase("-") || emplid.trim().length()==0)
			emplid="%";
		if(courseid.equalsIgnoreCase("-") || courseid.trim().length()==0)
			courseid="%";

		List<Object[]> courselist=em.createNativeQuery(" select distinct ad.assessment_id,ad.template_id,ad.title,bd.course_id,concat(cd.course_code,' : ',cd.course_descr),coalesce(ad.descr1,''),\n" +
				"concat(fd.emplid,' : ',fd.first_name,coalesce(fd.midle_name,' '),fd.last_name) as fullName ,ad.created_date,\n" +
				"case when sa.is_active='Y' then 'Y' else 'N' end as schedulestatus\n" +
				" from faculty_tagging_details ft left join batch_details bd on ft.batch_id=bd.batch_id \n" +
				" left join course_details cd on cd.course_id=bd.course_id\n" +
				" left join assessment_details ad on ad.course_id=cd.course_id\n" +
				" left join scheduled_assessment sa on sa.assessment_id=ad.assessment_id\n" +
				"left join faculty_details fd on fd.faculty_id=ft.faculty_id where fd.emplid like :Emplid and cd.course_id like :CourseId and sa.is_active='Y' and ad.descr1 not like 'ES'")
				.setParameter("Emplid",emplid).setParameter("CourseId",courseid ).getResultList();
		return courselist.stream().map(x->{
			CommonModelReport cmreport= new CommonModelReport(x[0].toString(),x[1].toString(),x[2].toString(),x[3].toString(),x[4].toString(),x[5].toString(),x[6].toString(),x[7].toString());
			cmreport.setDescr9(x[8].toString());
			return cmreport;
		}).collect(Collectors.toList());

	}


	@GetMapping("/getFacultyList")
	public  List<ResponceMessage> getFacultyList()
	{
		List<String> facultylist=em.createNativeQuery("select concat(emplid,' : ',first_name,coalesce(midle_name,' '),last_name) as fullName from faculty_details where is_active='Y' order by emplid").getResultList();
		return facultylist.stream().map(y->{
			return new ResponceMessage(y);
		}).collect(Collectors.toList());
	}
}