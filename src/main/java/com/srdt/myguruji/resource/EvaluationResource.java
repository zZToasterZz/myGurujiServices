package com.srdt.myguruji.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srdt.myguruji.enitity.EvaluationStatus;
import com.srdt.myguruji.enitity.EvaluationStrategy;
import com.srdt.myguruji.model.AssessResultList;
import com.srdt.myguruji.model.EvaluationStatusModel;
import com.srdt.myguruji.model.EvaluationStrategyModel;
import com.srdt.myguruji.model.ResponceMessage;
import com.srdt.myguruji.repository.AssessmentDetailsRepository;
import com.srdt.myguruji.repository.EvaluationStatusRepository;
import com.srdt.myguruji.repository.EvaluationStrategyRepository;
import com.srdt.myguruji.repository.StudentDetailsRepository;

@RestController
@RequestMapping("/api/evaluation")
public class EvaluationResource
{
	@Autowired
	EvaluationStrategyRepository evalRepo;
	
	@Autowired
	AssessmentDetailsRepository assessRepo;
	
	@Autowired
	EvaluationStatusRepository evalStatRepo;
	
	@Autowired
	StudentDetailsRepository stdntRepo;
	
	@Autowired
	@PersistenceContext
	EntityManager em;
	
	@PostMapping("/setstrategy")
	@Transactional
	public ResponceMessage setStrategy(@RequestBody EvaluationStrategyModel request)
	{
		/*
		try
		{
			EvaluationStrategyModel check = getStrategy(request.getAssessmentid());
			if(check.getAssessmentid() == 0 || check.getStrategyid() == 0 || check.getStrategy() == null)
			{
				EvaluationStrategy strat = new EvaluationStrategy(request.getStrategyid(),request.getStrategy(),assessRepo.findAssessmentDetailsById(request.getAssessmentid()));
				evalRepo.save(strat);
			}
			else
			{
				return new ResponceMessage() {{setMessage("duplicate");}};
			}
		}
		catch(Exception e)
		{
			return new ResponceMessage() {{setMessage("error");}};
		}
		return new ResponceMessage() {{setMessage("success");}};
		*/
		EvaluationStrategy data=null;
		try
		{
			 data = evalRepo.getStrategyByAssessmentId(request.getAssessmentid());
			 System.out.println("Data:::"+data);
		}
		catch(Exception e) {
			data=null;
		}

		EvaluationStrategy strat=null;
		try
		{
			if(data==null)
			{
				strat = new EvaluationStrategy(request.getStrategyid(), request.getStrategy(), assessRepo.findAssessmentDetailsById(request.getAssessmentid()));
			}
			else
			if (data.getStrategyid()!= 0 || data !=null)
			{
				strat =em.find(EvaluationStrategy.class,data.getStrategyid());
				strat.setStrategyid(data.getStrategyid());
				strat.setStrategy(request.getStrategy());
				strat.setAssessmentDetails(assessRepo.findAssessmentDetailsById(request.getAssessmentid()));
			}
			evalRepo.save(strat);
			return new ResponceMessage("Success");
		}
		catch(Exception e)
		{
			return new ResponceMessage("Error:"+e.getMessage());
		}

	}
	
	@GetMapping("/getstrategy/{assessmentid}")
	public EvaluationStrategyModel getStrategy(@PathVariable("assessmentid") long assessmentid)
	{
		EvaluationStrategy data = evalRepo.getStrategyByAssessmentId(assessmentid);
		EvaluationStrategyModel res = new EvaluationStrategyModel();
		try {
			res.setStrategyid(data.getStrategyid());
			res.setAssessmentid(data.getAssessmentDetails().getAssessmentId());
			res.setStrategy(data.getStrategy());
		}catch(Exception e) {
			return res;
		}
		return res;
	}
	
	@PostMapping("/setevaluationstatus")
	public ResponceMessage setStatus(@RequestBody EvaluationStatusModel request)
	{
		try
		{
			EvaluationStatusModel data = getStatus(request.getAssessmentid(), request.getStudentid());
			if(data.getAssessmentid() == 0 || data.getStudentid() == 0 || data.getStatusid() == 0)
			{
				EvaluationStatus stat = new EvaluationStatus(request.getStatusid(), request.getStatus(), stdntRepo.findByStudentId(request.getStudentid()), assessRepo.findAssessmentDetailsById(request.getAssessmentid()));
				evalStatRepo.save(stat);
			}
			else
			{
				if(data.getStatus().equals("S"))
				{
					return new ResponceMessage() {{setMessage("not_changed");}};
				}
				evalStatRepo.updateStatus(request.getStatus(), data.getStatusid());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return new ResponceMessage() {{setMessage("error");}};
		}
		return new ResponceMessage() {{setMessage("success");}};
	}
	
	@GetMapping("/getevaluationstatus/{assessmentid}/{studentid}")
	public EvaluationStatusModel getStatus(@PathVariable("assessmentid") long assessmentid, @PathVariable("studentid") long studentid)
	{
		EvaluationStatus data = evalStatRepo.getStatusByAssessmentId(assessmentid, studentid);
		EvaluationStatusModel res = new EvaluationStatusModel();
		try
		{
			res.setStatusid(data.getStatusid());
			res.setAssessmentid(data.getAssessmentDetails().getAssessmentId());
			res.setStudentid(data.getStudentDetails().getStudentId());
			res.setStatus(data.getStatus());
		}
		catch(Exception e)
		{
			return res;
		}
		return res;
	}
	
	@GetMapping("/getresultlist/{assessmentid}/{courseid}/{batchid}")
	public List<AssessResultList> getResultList(@PathVariable("assessmentid")String assessmentid,@PathVariable("courseid")String courseid, @PathVariable("batchid")String batchid)
	{
		Query query = em.createNativeQuery("select "
				+ "	g.assessment_id,a.student_id,g.emplid,a.campus_id,concat(a.first_name,' ',a.last_name) as name,g.course_id,c.batch_id,c.batch_code,c.batch_type,sum(g.marksobtained) as marks, "
				+ " h.assesment_attendence_status, h.assessment_submission_status,k.max_marks,case when j.status is null then 'N' else j.status end as status "
				+ "from "
				+ "	student_details a "
				+ "    inner join student_enrollment b on a.student_id = b.student_id "
				+ "    inner join batch_details c on c.batch_id = b.batch_id "
				+ "    inner join scheduled_assessment_mapping d on c.batch_id = d.batch_id "
				+ "    inner join scheduled_assessment e on d.scheduled_id = e.scheduled_id "
				+ "    inner join assessment_details f on e.assessment_id = f.assessment_id "
				+ "    inner join assessment_responce g on g.assessment_id = f.assessment_id and g.emplid = a.emplid and g.course_id = c.course_id "
				+ "    left join student_assessment_status_details h on h.assessment_id = f.assessment_id and h.student_id = a.student_id "
				+ "    left join evaluation_status j on f.assessment_id = j.assessment_id and a.student_id = j.student_id "
				+ "    inner join assessment_template k on f.template_id = k.template_id "
				+ "where "
			//	+ "	g.assessment_id = '"+assessmentid+"' and g.course_id = '"+courseid+"' and c.batch_type = 'LEC' and c.batch_id = '"+batchid+"' "
				+ "	g.assessment_id = '"+assessmentid+"' and g.course_id = '"+courseid+"' and c.batch_id = '"+batchid+"' "

				+ "group by "
				+ "	g.assessment_id,a.student_id,g.emplid,a.campus_id,concat(a.first_name,' ',a.last_name),g.course_id,c.batch_id,c.batch_type, "
				+ "case when j.status is null then 'N' else j.status end "
				+ ";");
		List<Object[]> data = query.getResultList();
		
		return data.stream().map(x -> {
			return new AssessResultList() {{
				setAssessmentid(x[0].toString());
				setStudentid(x[1].toString());
				setEmplid(x[2].toString());
				setCampus_id(x[3].toString());
				setName(x[4].toString());
				setCourse_id(x[5].toString());
				setBatch_id(x[6].toString());
				setBatch_code(x[7].toString());
				setBatch_type(x[8].toString());
				setMarks(x[9].toString());
				if(x[10] == null) {
					x[10] = "Y";
				}
				if(x[11] == null) {
					x[11] = "Y";
				}
				setAttendance( x[10].toString() );
				setSubmission( x[11].toString() );
				setTotalmarks(x[12].toString());
				setStatus(x[13].toString());
			}};
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/getAssessmentTotalMarks/{assessid}")
	public ResponceMessage getTotalMarks(@PathVariable("assessid")String assessid)
	{
		Query query = em.createNativeQuery("select b.max_marks from assessment_details a inner join assessment_template b on a.template_id = b.template_id where assessment_id = '"+assessid+"';");
		try
		{
			Object data = query.getResultList().get(0);
			if(data == null)
				return new ResponceMessage() {{setMessage( "0" );}};
			return new ResponceMessage() {{setMessage( data.toString() );}};
		}
		catch (Exception e)
		{
			return new ResponceMessage() {{setMessage( "0" );}};
		}
	}
}