package com.srdt.myguruji.resource;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import com.srdt.myguruji.enitity.*;
import com.srdt.myguruji.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srdt.myguruji.model.ContentCategory;
import com.srdt.myguruji.model.ContentDescr;
import com.srdt.myguruji.model.LearningContent;
import com.srdt.myguruji.model.ResponceMessage;
import com.srdt.myguruji.model.StudentDownloadRecord;
import com.srdt.myguruji.utility.Generation;

@RestController
@RequestMapping("/api/content")
public class ContentResource {
	
	@Autowired
	CourseContentRepository crscntrep;
	@Autowired
	CourseDetailsRepository crsrep;
	@Autowired
	ContentTypeRepository cnttyperep;
	@Autowired
	UnitContentRepository untcntrep;
	@Autowired 
	UnitDetailsRepository untrep;
	@Autowired
	CoursePlanRepository crsplanrepo;
	@Autowired
	BatchRepository batchrepo;
	@Autowired
	StudentDetailsRepository studrepo;
	@Autowired
	StudentContentDowloadRepo contentdownloadrepo;
	@Autowired
	AssignmentSubmissionStatusRepository assignmentstatusrepo;
	@Autowired
	@PersistenceContext
	EntityManager em;
	
	
	@PostMapping("/addcontentype")
	public ResponceMessage AddContentType(@RequestBody List<LearningContent> cnts)
	{
		cnts.stream().forEach(cnt->{
			cnttyperep.save(new ContentType(cnt.getTypeid(), cnt.getContenttype(), cnt.getDescr(), cnt.getCreatedby()));
		});
		
		return new ResponceMessage("Content types created");
	}
	
	@GetMapping("/getcontenttype")
	public List<Map<String, String>> getContentType()
	{
		return cnttyperep.getContentType().stream().map(x->{
			Map<String, String> map = new HashMap<>();
			map.put("typeid", Long.toString(x.getTypeId()));
			map.put("contenttype", x.getTitle());
			map.put("descr", x.getDescr());
			return map;
		}).collect(Collectors.toList());
	}
	
	@PostMapping("/addcoursecontent")
	public ResponceMessage AddCourseContent(@RequestBody LearningContent cnt)
	{		
		CourseDetails courseDetails = crsrep.findCourseDetailsByCourseId(cnt.getCourseid());
		crscntrep.saveAndFlush(new CourseContent(cnt.getContentid(), cnt.getTitle(), cnt.getDescr(), cnt.getContentpath(), courseDetails, cnt.getCreatedby(),cnttyperep.getContentTypeByTypeId(cnt.getTypeid())));
		return new ResponceMessage("Content Uploaded");
	}
	
	@GetMapping("/getcoursecontentbycourseid/{courseid}")
	public List<LearningContent> getCourseContentByCourseId(@PathVariable("courseid") long courseid)
	{
		return crscntrep.getCourseContentByCourseId(courseid).stream().map(x->{
			return new LearningContent(x.getCourseContentId(), x.getTitle(), x.getDescr(), x.getContentPath(), courseid, x.getCreatedBy(),x.getContentType().getTitle(),x.getContentType().getTypeId());
		}).collect(Collectors.toList());
		
	}

	@PostMapping("/addunitcontent")
	public ResponceMessage AddUnitContent(@RequestBody LearningContent cnt)
	{
		CourseDetails courseDetails = crsrep.findCourseDetailsByCourseId(cnt.getCourseid());
		UnitDetails unitDetails = untrep.findUnitByUnitId(cnt.getUnitid());
		ContentType contentType = cnttyperep.getContentTypeByTypeId(cnt.getTypeid());
		untcntrep.saveAndFlush(new UnitContent(cnt.getContentid(), cnt.getTitle(), cnt.getDescr(), cnt.getContentpath(), courseDetails, unitDetails, cnt.getCreatedby(), contentType));
		return new ResponceMessage("Content Uploaded");
	}
	
	@GetMapping("/getunitcontentbyunitid/{unitid}")
	public List<LearningContent> getUnitContentByUnitId(@PathVariable("unitid") long unitid)
	{
		return untcntrep.getUnitContentByUnitId(unitid).stream().map(x->{
			return new LearningContent(x.getUnitContentId(), x.getTitle(), x.getDescr(), x.getContentPath(), unitid, x.getCourseDetails().getCourseId(), x.getCreatedBy(),x.getContentType().getTitle(), x.getContentType().getTypeId());
		}).collect(Collectors.toList());
		
	}
	
	@PostMapping("/deleteunitcontent")
	public ResponceMessage deleteUnitContent(@RequestBody List<Long> unitid)
	{
		untcntrep.deleteUnitContent(unitid);
		return new ResponceMessage("Unit content deleted");
	}
	
	@PostMapping("/deletecoursecontent")
	public ResponceMessage deleteCourseContent(@RequestBody List<Long> courseid)
	{
		crscntrep.deleteCourseContent(courseid);
		return new ResponceMessage("Content deleted");
	}
	
	@GetMapping("/getcategorizedunitcontent/{unitid}")
	public List<ContentCategory> findUnitContentByUnitId(@PathVariable("unitid") long unitid)
	{
		return cnttyperep.findUnitContentByUnitId(unitid).stream().map(x->{
			List<ContentDescr> content =  x.getUnitContents().stream().map(y->{
				return new ContentDescr(y.getUnitContentId(), y.getTitle(), y.getDescr(), y.getContentPath());
			}).collect(Collectors.toList());
			return new ContentCategory(x.getTitle(), content);
		}).collect(Collectors.toList());
		
	}
	
	@GetMapping("/getcategorizedcoursecontent/{courseid}")
	public List<ContentCategory> findCourseContentByCourseId(@PathVariable("courseid") long courseid)
	{
		return cnttyperep.findCourseContentByCourseId(courseid).stream().map(x->{
			List<ContentDescr> content =  x.getCourseContents().stream().map(y->{
				return new ContentDescr(y.getCourseContentId(), y.getTitle(), y.getDescr(), y.getContentPath());
			}).collect(Collectors.toList());
			return new ContentCategory(x.getTitle(), content);
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/coursecontentanalysis/{courseid}")
	public List<ContentCategory> courseContentAnalysis(@PathVariable("courseid") long courseid)
	{
		return cnttyperep.courseContentAnalysis(courseid);
	}
	
	@GetMapping("/unitcontentanalysis/{unitid}")
	public List<ContentCategory> unitContentAnalysis(@PathVariable("unitid") long unitid)
	{
		return cnttyperep.unitContentAnalysis(unitid);
	}
	
	//SANDHYA
	@PostMapping("updateUnitContent/{dscr}/{title}/{unitId}")
	public ResponceMessage updateUnitContent(@PathVariable("dscr")String dscr,@PathVariable("title") String title,@PathVariable("unitId") long unitId)
	{
		if(untcntrep.updateUnitContent(dscr, title, unitId)>0)
			return new ResponceMessage("Updated");
		else
			return new ResponceMessage("Not Updated");
	}
	
	/*********SANDHYA************/
	@PostMapping("/saveStudentDownloadStatus")
	@Transactional
	public ResponceMessage saveStudentdownloadStatus(@RequestBody StudentDownloadRecord downloadstatus) throws ParseException {
		StudentDetails student= studrepo.findStudentById(downloadstatus.getStudentid());
		BatchDetails batch=batchrepo.findBatchDetailsByBatchId(downloadstatus.getBatchid());
		CourseDetails course= crsplanrepo.findCourseByCourseId(downloadstatus.getCourseid());
		UnitDetails unit=untrep.findUnitByUnitId(downloadstatus.getUnitid());
		UnitContent content= em.find(UnitContent.class, downloadstatus.getContentid());
		CoursePlan crsplan=crsplanrepo.getCoursePlanById(downloadstatus.getCourseplanid());

		String status="yes";
		double marks=0.0;
		Date endDate=Generation.toDate(downloadstatus.getDownloadtime(),"yyyy-MM-dd HH:mm:ss");

		Date startDate=null;
		if(downloadstatus.getTypedecr().equalsIgnoreCase("link"))
		{
			LectureSchedule  lecture=em.find(LectureSchedule.class,downloadstatus.getContentid());
			startDate = lecture.getCreatedDate();
		}
		else
			 startDate=content.getCreatedDate();

		//long docCount=untcntrep.getDocUploadCount(downloadstatus.getCourseid(),downloadstatus.getUnitid(),downloadstatus.getCourseplanid());
		long different = Math.abs(endDate.getTime() - startDate.getTime());

		long elapsedHours = different / (60*60*1000);

		if(startDate.after(endDate))
			return new ResponceMessage("Date Error");

		System.out.println("Hour difference:::::::"+elapsedHours);
		if(elapsedHours>24)
			status="No";

		/*********** set marks later**********/
//		if(status.equalsIgnoreCase("yes"))
//					marks=setmarks(downloadstatus);
//		System.out.println("Final marks:::::::"+marks);

		try {
			StudentContentDowloadStatus stdownloadstatus=new StudentContentDowloadStatus(0, Generation.toDate(downloadstatus.getDownloadtime(),"yyyy-MM-dd HH:mm:ss"),startDate, status, marks,downloadstatus.getTypedecr(),
					course,batch,student, crsplan,unit,content,String.valueOf(downloadstatus.getContentid()));
			stdownloadstatus.setModifiedBy(student.getEmplid());
			stdownloadstatus.setCreatedBy(student.getEmplid());
			contentdownloadrepo.save(stdownloadstatus);
			}
		catch(Exception e)
		{
			System.out.println("ERROR"+e.getMessage());
		}
	return new ResponceMessage("Success");
	}

	@PostMapping("setMarks")
	public ResponceMessage setmarks(@RequestBody  StudentDownloadRecord downloadstatus)
	{
		double marks=0.0;

		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		System.out.println("Date:"+ cal.getTime());
		Date prvs=cal.getTime();
		String previousdate=Generation.dateAsString(cal.getTime());

		long docCount=(Long)em.createQuery("select count(uc.Title) from UnitContent uc where uc.courseDetails.CourseId=:crsid and uc.unitDetails.UnitId=:unitid and uc.unitDetails.coursePlan.CoursePlanId=:crsplanid and uc.contentType.TypeId=1 and uc.CreatedDate>'2021-01-13 00:00:00'")
				.setParameter("crsid",downloadstatus.getCourseid()).setParameter("unitid",downloadstatus.getUnitid()).setParameter("crsplanid",downloadstatus.getCourseplanid())
				.setParameter("prvsdate",prvs).getSingleResult();
		long vidCount=untcntrep.getVideoUploadCount(downloadstatus.getCourseid(),downloadstatus.getUnitid(),downloadstatus.getCourseplanid(),prvs);

		/***** Query to get count of student assignment at course level*****/
		Query query=em.createNativeQuery("select count(a.assignmentid) " +
				" from assignment_student_mapping a left join assignment_details b on a.assignmentid=b.assignmentid " +
				" where a.student_id=:stid and a.course_id=:crsid and a.course_plan_id=:crsplanid and b.level='C'")
				.setParameter("stid",downloadstatus.getStudentid())
				.setParameter("crsid",downloadstatus.getCourseid())
				.setParameter("crsplanid",downloadstatus.getCourseplanid());

		long courseassignmentcount=new BigInteger(String.valueOf(query.getSingleResult())).longValue();

		/***** Query to get count of student assignment at unit level*****/
		Query query1=em.createNativeQuery("select count(a.assignmentid) from assignment_student_mapping a\n" +
				"left join assignment_details b on a.assignmentid=b.assignmentid left join assignment_unit_mapping c on a.assignmentid=c.assignmentid\n" +
				"where a.student_id=:stid and a.course_id=:crsid and a.course_plan_id=:crsplanid and b.level='U' and c.unit_id=:unitid")
				.setParameter("stid",downloadstatus.getStudentid())
				.setParameter("crsid",downloadstatus.getCourseid())
				.setParameter("crsplanid",downloadstatus.getCourseplanid())
				.setParameter("unitid",downloadstatus.getUnitid());

		long unitassignmentcount=new BigInteger(String.valueOf(query1.getSingleResult())).longValue();

		long studentdocdownload=contentdownloadrepo.getDocDownloadCount(downloadstatus.getBatchid(),downloadstatus.getCourseplanid(),downloadstatus.getUnitid(),downloadstatus.getStudentid());
		long studentvidDownlod=contentdownloadrepo.getVideoDownloadCount(downloadstatus.getBatchid(),downloadstatus.getCourseplanid(),downloadstatus.getUnitid(),downloadstatus.getStudentid());

		Query stquery=em.createNativeQuery("SELECT count(um.assignmentid) FROM assignment_status_details sd left join assignment_unit_mapping um on sd.assignmentid=um.assignmentid " +
				"left join assignment_details ad on ad. assignmentid=um.assignmentid where sd.student_id=:stid and um.unit_id=:unitid and sd.timing_status='T' and ad.level=:lvl")
				.setParameter("stid", downloadstatus.getStudentid())
				.setParameter("unitid",downloadstatus.getUnitid());
		stquery.setParameter("lvl","C");
		long studentcourseassignmentcount=new BigInteger(String.valueOf(stquery.getSingleResult())).longValue();

		/*******unit level assignment count********/
		stquery.setParameter("lvl","U");
		long studentuntassignmentcount=new BigInteger(String.valueOf(stquery.getSingleResult())).longValue();

		//long studentassignmentcount=assignmentstatusrepo.getAssignmentCountforStudentId(downloadstatus.getStudentid(),downloadstatus.getUnitid());

		if(downloadstatus.getTypedecr().equalsIgnoreCase("docs")||downloadstatus.getTypedecr().equalsIgnoreCase("documents"))
			studentdocdownload+=1;

		if(downloadstatus.getTypedecr().equalsIgnoreCase("vid")||downloadstatus.getTypedecr().equalsIgnoreCase("videos"))
			studentvidDownlod+=1;

		Double videocheck=new Double(studentvidDownlod / (double)vidCount);
		Double doccheck=new Double(studentdocdownload /(double) docCount);
		Double courseassignment=new Double(studentcourseassignmentcount/(double)courseassignmentcount);
		Double unitassignment=new Double(studentuntassignmentcount/(double)unitassignmentcount);

		Long count=(Long)em.createQuery("select count(ud) from UnitDetails ud where ud.coursePlan.CoursePlanId=:crsplanid")
				.setParameter("crsplanid",downloadstatus.getCourseplanid()).getSingleResult();

		double videomarks=0.0,docmarks=0.0,assignmentmarks=0.0;

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
		double videoweigtage=0.35,docweigtage=0.25,questionassignment=0.4;

		if(videocheck.isNaN() || videocheck.isInfinite())
			videomarks=0.0;
		else
			videomarks= (cpu * (studentvidDownlod / (double)vidCount) * videoweigtage);

		if(doccheck.isNaN() || doccheck.isInfinite())
			docmarks=0.0;
		else
			docmarks=  (cpu * (studentdocdownload /(double) docCount) * docweigtage);

		if(courseassignment.isNaN() || courseassignment.isInfinite())
			courseassignment=0.0;

		if(unitassignment.isNaN() || unitassignment.isInfinite())
			unitassignment=0.0;

			double studentassignmentcount=courseassignment+unitassignment;
			assignmentmarks=  (cpu * studentassignmentcount * questionassignment);
			System.out.println("Assignment marks:::"+assignmentmarks);

			marks = docmarks +videomarks + assignmentmarks;

		System.out.println("Upload doc::::"+docCount);
		System.out.println("Upload video::::"+vidCount);
		System.out.println("Upload  Course Assigment::::"+courseassignmentcount);
		System.out.println("Upload  Unit  Assigment::::"+unitassignmentcount);
		System.out.println("Course Assignment count:"+studentcourseassignmentcount);

		System.out.println("/************************DOWNLOAD*****************************/");
		System.out.println("download doc::::"+studentdocdownload);
		System.out.println("download video::::"+studentvidDownlod);
		System.out.println("done Assigment::::"+studentassignmentcount);

		System.out.println("/************************DOWNLOAD Status after updation*****************************/");
		System.out.println("download doc::::"+studentdocdownload);
		System.out.println("download video::::"+studentvidDownlod);
		System.out.println("done Assigment::::"+studentassignmentcount);

		System.out.println("Course Assignment count:"+studentcourseassignmentcount);
		System.out.println("Unit Assignment count:"+studentuntassignmentcount);

		System.out.println("UNIT COUNT:::::::::"+count);
		System.out.println("Unit ID::::"+downloadstatus.getUnitid());

		System.out.println("MArks:::::::"+marks);

		return new ResponceMessage(String.valueOf(marks));
	}

	@GetMapping("/getdate")
	public void displaydate()
	{
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		System.out.print("Date:"+ cal.getTime());
		String previousdate=Generation.dateAsString(cal.getTime());
		System.out.println("previous date"+previousdate);
		System.out.println("/*****************************************************************************/");
		Double zerobyzero=new Double(0/(double)0);
		System.out.println("0/0"+zerobyzero);
		Double dividebyzero=new Double(4/(double)0);
		System.out.println("3/0"+dividebyzero.toString());
		System.out.println("/*****************************************************************************/");


	}

	@GetMapping("/getContentDowloadStatus/{studentid}")
	public List<StudentDownloadRecord> getStudentContentDownloadRecord(@PathVariable long studentid)
	{
		List<StudentContentDowloadStatus> studentrecord=em.createQuery("select cds from StudentContentDowloadStatus cds where cds.studentDetails.StudentId=:studid ")
				.setParameter("studid",studentid).getResultList();
		return studentrecord.stream().map(x->{
			return new StudentDownloadRecord(x.getContentDowloadStatusId(), x.getContentid().getUnitContentId(), Generation.dateAsString(x.getDownloadTime()),
					x.getCourseDetails().getCourseId(),x.getBatchDetails().getBatchId() , x.getUnit().getUnitId(),x.getContentType() , x.getCoursePlan().getCoursePlanId(),
					x.getStudentDetails().getStudentId(), Generation.dateAsString(x.getUploadTime()), x.getStatus(), x.getMarks()) ;
		}).collect(Collectors.toList());

	}
	
}