package com.srdt.myguruji.resource;

import java.text.ParseException;
import java.util.HashMap;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.srdt.myguruji.enitity.WebSession;
import com.srdt.myguruji.model.ResponceMessage;
import com.srdt.myguruji.repository.WebSessionRepository;

@RestController
@RequestMapping("/api/web")
@EnableAsync
public class WebSessionResource
{
	@Autowired
	EntityManager em;
	
	@Autowired
	protected WebSessionRepository webrep;
	@Value("${api.session.addr}")
	private String api;
	private String delay = "600000";
	
	@GetMapping("/syncsession")
	@SuppressWarnings({ "rawtypes" })
	@Scheduled(fixedDelay = 120000)
	public ResponceMessage syncSession()
	{
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
	    
		ResponseEntity<HashMap> web = template.exchange(api, HttpMethod.GET, entity, HashMap.class);
		 
		webrep.saveAndFlush(new WebSession(Long.parseLong(web.getBody().get("totalsession").toString()),(long)Long.parseLong(web.getBody().get("logingsession").toString())));
		System.out.println("Sync Data");
		return new ResponceMessage("Saved");
	}
	
	/*@GetMapping("/syncassessmentresponses")
	@SuppressWarnings({ "rawtypes" })
	@Scheduled(fixedDelay = (1000*60*60*3))
	public ResponceMessage syncAssessmentResponses()
	{
		
		return new ResponceMessage("Saved");
	}*/
	
	@GetMapping("/syncassessmentresponses")
	@SuppressWarnings({ "rawtypes" })
	@Scheduled(fixedDelay = (1000*60*60*2))	
	public ResponceMessage syncAssessmentResponses()
	{
		System.out.println("cron");
		String cronstatus=(String)em.createNativeQuery("select cronstatus from cron_job_status").getSingleResult();
		if(cronstatus.equalsIgnoreCase("T"))
				em.createStoredProcedureQuery("autoEvaluationProcess").execute();
		
		return new ResponceMessage("Saved");
		
	}
}