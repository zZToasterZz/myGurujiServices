package com.srdt.myguruji.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.srdt.myguruji.enitity.StudentDetails;
import com.srdt.myguruji.model.Credential;
import com.srdt.myguruji.model.ResponceMessage;
import com.srdt.myguruji.repository.FacultyDetailsRepository;
import com.srdt.myguruji.repository.StudentDetailsRepository;

@RestController
@RequestMapping("/api/login")
public class ResourceLogin {

	@Value("${mg.sync.url}")
	private String url;
	
	
	RestTemplate rest = new RestTemplate();
	
	@Autowired
	FacultyDetailsRepository facrep;
	
	@Autowired
	StudentDetailsRepository studrep;
	
	@GetMapping("/facultylogin")
	public List<ResponceMessage> facultyLogin()
	{		
		/*
		 * List<FacultyDetails> details = facrep.findAll(); List<Credential> credentials
		 * = details.stream().map(x->{ return new
		 * Credential(x.getEmplid(),x.getEmplid(), x.getEmailAddr(), "Sync");
		 * }).collect(Collectors.toList());
		 * 
		 * HttpHeaders body = new HttpHeaders();
		 * body.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 * 
		 * HttpEntity<Credential> entity = new HttpEntity<Credential>(credentials,body);
		 * 
		 * ResponceMessage[] message = rest.exchange(url, HttpMethod.POST, entity,
		 * ResponceMessage[].class).getBody();
		 * 
		 * return Arrays.asList(message);
		 */
		return null;
	}
	
	@GetMapping("/studentlogin")
	public List<ResponceMessage> studentLogin()
	{
		List<StudentDetails> details = studrep.findAll();		
		List<Credential> credentials = details.stream().map(x->{
			return new Credential(x.getEmplid(),x.getEmplid(), x.getEmailAddr(), "Sync");
		}).collect(Collectors.toList());
		
		HttpHeaders body = new HttpHeaders();
		body.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		HttpEntity<List<Credential>> entity = new HttpEntity<List<Credential>>(credentials,body);
		
		ResponceMessage[] message = rest.exchange(url, HttpMethod.POST, entity, ResponceMessage[].class).getBody();
		
		return Arrays.asList(message);
	}
}
