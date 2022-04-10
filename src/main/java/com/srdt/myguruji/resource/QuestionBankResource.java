package com.srdt.myguruji.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.srdt.myguruji.enitity.AnswerDetails;
import com.srdt.myguruji.enitity.CourseDetails;
import com.srdt.myguruji.enitity.DifficultyLevel;
import com.srdt.myguruji.enitity.QuestionDetails;
import com.srdt.myguruji.enitity.QuestionType;
import com.srdt.myguruji.enitity.TopicDetails;
import com.srdt.myguruji.model.Question;
import com.srdt.myguruji.model.QuestionBankCount;
import com.srdt.myguruji.model.ResponceMessage;
import com.srdt.myguruji.model.Search;
import com.srdt.myguruji.model.Translate;
import com.srdt.myguruji.repository.CoursePlanRepository;
import com.srdt.myguruji.repository.QuestionDetailsRepository;
import com.srdt.myguruji.repository.TopicDetailsRepository;

@RestController
@RequestMapping("/api/question")
public class QuestionBankResource {
	
	@Autowired
	CoursePlanRepository planrep;	
	@Autowired
	TopicDetailsRepository topicrep;
	@Autowired
	QuestionDetailsRepository quesrep;
	
	@PostMapping("/create")
	public ResponceMessage create(@Valid @RequestBody List<Question> questions)
	{
		questions.stream()
		         .forEach(question ->{
		        	CourseDetails courseDetails = planrep.findCourseByCourseId(question.getCourseid());
		     		TopicDetails topicDetails = topicrep.findTopicDetailsById(question.getTopicid());
		     		DifficultyLevel difficultyLevel = quesrep.findDiffLevelById(question.getDifficultyid());
		     		QuestionType questionType = quesrep.findQuestionTypeById(question.getTypeid());
		     		
		     		QuestionDetails questionDetails = new QuestionDetails(question.getQuestiontext(), topicDetails, questionType, courseDetails, difficultyLevel, question.getCreatedby(),question.getReferid(),question.getSrclec(),question.getBlmtaxonomy(),question.getCourseobj());
		     		if(question.getTypeid() != Long.parseLong("6") && question.getTypeid() != Long.parseLong("4") && question.getTypeid() != Long.parseLong("5") && question.getTypeid() != Long.parseLong("7"))
		     		{		
		     		    AnswerDetails answerDetails = new AnswerDetails(question.getOpt1(), question.getOpt2(), question.getOpt3(), question.getOpt4(), question.getOpt5(), question.getOpt6(), question.getCurrectopt(), questionDetails, question.getCreatedby());
		     		    questionDetails.setAnswerDetails(answerDetails);
		     		}		
		     		quesrep.saveAndFlush(questionDetails);
		         });
		
		
		
		return new ResponceMessage("Questions Added");
	}
	
	@PostMapping("/search")
	public List<Question> search(@RequestBody Search search)
	{
		return quesrep.searchQuestion(search.getTopicid(),search.getTypeid(),search.getCourseid(),search.getDifficultyid())
		       .stream()
		       .map(x->{
				   Question ques=null;
		    	   AnswerDetails answerDetails = x.getAnswerDetails();
		    	   if(answerDetails == null)
		    	   {
		    		   ques= new Question(x.getQuestionId(),x.getCourseDetails().getCourseId(), x.getTopicDetails().getTopicId(), x.getDifficultyLevel().getDifficultyId(), x.getQuestionType().getTypeId(), x.getQuestionText(), x.getCreatedBy(),x.getSrcLec(),x.getBlmTaxonomy(),x.getCourseObj(),x.getReferId(),x.getTopicDetails().getTopicTitle(),x.getCourseDetails().getCourseTitle(),x.getDifficultyLevel().getDiffLevel(),x.getQuestionType().getType());
		    	   }
		    	   else
		    	   {
		    		   ques= new Question(x.getQuestionId(),x.getCourseDetails().getCourseId(), x.getTopicDetails().getTopicId(), x.getDifficultyLevel().getDifficultyId(), x.getQuestionType().getTypeId(), x.getQuestionText(), answerDetails.getOpt1(), answerDetails.getOpt2(), answerDetails.getOpt3(), answerDetails.getOpt4(), answerDetails.getOpt5(), answerDetails.getOpt6(), answerDetails.getCurrectOpt(), x.getCreatedBy(),x.getSrcLec(),x.getBlmTaxonomy(),x.getCourseObj(),x.getReferId(),x.getTopicDetails().getTopicTitle(),x.getCourseDetails().getCourseTitle(),x.getDifficultyLevel().getDiffLevel(),x.getQuestionType().getType());
		    	   }
		    	   ques.setBlmtaxonomy(x.getBlmTaxonomy());
		    	   ques.setCourseobj(x.getCourseObj());
		    	   ques.setReferid(x.getReferId());
		    	   return ques;
		       })
		       .collect(Collectors.toList());
		
	}
	
	@GetMapping("/getdifficultylevel")
	public List<Translate> getDifficultilevel()
	{
		return quesrep.findAllDifficultyLevel().stream()
				                               .map(x->{
				                            	   return new Translate(x.getDifficultyId(), x.getDiffLevel());
				                               })
				                               .collect(Collectors.toList());
	}
	
	@GetMapping("/getquestiontype")
	public List<Translate> getQuestionTypes()
	{
		return quesrep.findAllQuestionTypes().stream()
				                             .map(x->{
				                            	 return new Translate(x.getTypeId(), x.getType(),x.getDescr());
				                             })
				                             .collect(Collectors.toList());
	}
	
	@PostMapping("/getquestioncount")
	public List<QuestionBankCount> getQuestionCount(@RequestBody Search search)
	{
		return quesrep.findQuestionBankCount(search.getTopicid(),search.getTypeid(),search.getCourseid());
		       
	}

	@GetMapping("/setIsActiveStatus/{questionid}")
	public ResponceMessage setStatus(@PathVariable long questionid)
	{
		if(quesrep.setQuestionStatus(questionid)>0)
			return new ResponceMessage("Success");
		else
			return new ResponceMessage("Error");
	}

}
