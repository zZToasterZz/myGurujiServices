package com.srdt.myguruji.resource;

import com.srdt.myguruji.model.CommonModelReport;
import com.srdt.myguruji.model.Login;
import com.srdt.myguruji.model.ResponceMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admindashboard/")
public class AdminDashboardResources {

    @Autowired
    @PersistenceContext
    EntityManager em;	

	@Value("${myguruji.outh.addr}")
	String url;
	
    @SuppressWarnings("unchecked")
	@GetMapping("getAsyncEmplIdsforStudents")
    public List<CommonModelReport> getStudentAsyncData()
    {
    	List<String> userlist = getLogins("S");
    	List<Object[]> asyncEmplLIst=em.createNativeQuery("select campus_id as loginid,emplid,email_addr as emailid,'sync' as createdby " +
                "from student_details where campus_id not in (:userlist) limit 50").setParameter("userlist", userlist).getResultList();
        return asyncEmplLIst.stream().map(x->{
            return new CommonModelReport(x[0].toString(),x[1].toString(),x[2].toString(),x[3].toString());
        }).collect(Collectors.toList());

    }

    @SuppressWarnings("unchecked")
	@GetMapping("getAsyncEmplIdsforFaculty")
    public List<CommonModelReport> getFacultyASyncData()
    {
    	List<String> userlist = getLogins("F");
    	List<Object[]> asyncEmplLIst=em.createNativeQuery("select emplid as loginid,emplid,email_addr as emailid,'sync' as createdby  " +
                "from faculty_details where emplid not in (:userlist)").setParameter("userlist", userlist).getResultList();
        return asyncEmplLIst.stream().map(x->{
            return new CommonModelReport(x[0].toString(),x[1].toString(),x[2].toString(),x[3].toString());
        }).collect(Collectors.toList());
    }

    List<String> getLogins(String type)
    {
    	List<String> userlist = new ArrayList<>();
    	try 
    	{
    		RestTemplate rest = new RestTemplate();
    		HttpHeaders headers = new HttpHeaders();
    		HttpEntity<String> entity = new HttpEntity<String>(headers);
    		Login[] login = rest.exchange(url, HttpMethod.GET, entity, Login[].class).getBody();
    		for(Login x : login)
        	{
        		if(type.equalsIgnoreCase("F") && x.getLoginid().substring(0,1).equalsIgnoreCase("E"))
        			userlist.add(x.getLoginid());
        		else if(type.equalsIgnoreCase("S") && !x.getLoginid().substring(0,1).equalsIgnoreCase("E"))
        			userlist.add(x.getLoginid());        			
        	}
        	return userlist;
    	} catch (Exception ex) {
    		System.out.println("Error: " + ex.getMessage());
    	}
    	
    	return null;
    }
    
    /*@SuppressWarnings("unchecked")
	@GetMapping("getAsyncEmplIdsforStudents")
    public List<CommonModelReport> getStudentAsyncData()
    {
    	List<Object[]> asyncEmplLIst=em.createNativeQuery("select campus_id as loginid,emplid,email_addr as emailid,'sync' as createdby " +
                "from student_details where campus_id not in (select login_id from user_login where login_id not like 'E%' and login_id != 'myguruji')")
    			.getResultList();
        return asyncEmplLIst.stream().map(x->{
            return new CommonModelReport(x[0].toString(),x[1].toString(),x[2].toString(),x[3].toString());
        }).collect(Collectors.toList());

    }

    @SuppressWarnings("unchecked")
	@GetMapping("getAsyncEmplIdsforFaculty")
    public List<CommonModelReport> getFacultyASyncData()
    {
    	List<Object[]> asyncEmplLIst=em.createNativeQuery("select emplid as loginid,emplid,email_addr as emailid,'sync' as createdby  " +
                "from faculty_details where emplid not in (select login_id from user_login where login_id != 'myguruji')")
    			.getResultList();
        return asyncEmplLIst.stream().map(x->{
            return new CommonModelReport(x[0].toString(),x[1].toString(),x[2].toString(),x[3].toString());
        }).collect(Collectors.toList());
    }*/
    
    @PostMapping("/studentRoleMapping")
    @Transactional
    public ResponceMessage saveStudentRolemapping()
    {
        long count=em.createNativeQuery("insert into user_role_mapping(role_map_id,created_by,is_active,modified_by,role_id,login_id) " +
                "select id,'sync','Y','sync',4,login_id from user_login " +
                "where empl_id in (select empl_id from user_login where login_id not in (select login_id from user_role_mapping) and " +
                "   empl_id not like 'E%' and login_id != 'myguruji' and login_id not like 'srmu%')").executeUpdate();
        if(count>0)
            return new ResponceMessage("Success");
        else
            return new ResponceMessage("Error");
    }

    @PostMapping("/facultyRoleMapping")
    @Transactional
    public ResponceMessage saveFacultyRolemapping()
    {
        long count=em.createNativeQuery("insert into user_role_mapping(role_map_id,created_by,is_active,modified_by,role_id,login_id) " +
                "select id,'sync','Y','sync',3,login_id from user_login " +
                "where empl_id in (select empl_id from user_login where login_id not in (select login_id from user_role_mapping) and " +
                "   empl_id like 'E%' and login_id != 'myguruji' and login_id not like 'srmu%')").executeUpdate();
        if(count>0)
            return new ResponceMessage("Success");
        else
            return new ResponceMessage("Error");
    }
}
