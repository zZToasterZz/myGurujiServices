package com.srdt.myguruji.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(1)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter
{
	    @Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring()
			.antMatchers("/api-docs**")
			.antMatchers("/api/student/deleteenrollment","/api/faculty/deletetagging","/api/course/synccourse"
					,"/api/student/syncstudent","/api/student/syncstudentnew","/api/faculty/syncfaculty"
					,"/api/topic/synctopic","/api/batch/syncbatch","/api/faculty/syncmapping","/api/student/enrollment"
					,"/api/login/facultylogin","/api/login/studentlogin","/api/course/addcourseobjective"
					,"/api/courseoutcome/synccourseoutcomes","/api/topic/syncsysunit"
					,"/api/gradebook/addGradebookData")
			.antMatchers(
			        "/swagger-resources/**",
			        "/api-docs-ui.html**",
			        "/webjars/**",
			        "/swagger-ui/**");
		}
	    
	    @Override 
	    protected void configure(HttpSecurity http) throws Exception {	        
	         
	         http
	         .requestMatchers()
	         .antMatchers(HttpMethod.POST,"/api/student/deleteenrollment","/api/faculty/deletetagging","/api/course/synccourse","/api/student/syncstudent","/api/student/syncstudentnew","/api/faculty/syncfaculty","/api/topic/synctopic","/api/batch/syncbatch","/api/faculty/syncmapping","/api/student/enrollment","/api/login/facultylogin","/api/login/studentlogin","/api/course/addcourseobjective","/api/courseoutcome/synccourseoutcomes","/api/topic/syncsysunit","/api/gradebook/addGradebookData")
	         .antMatchers(HttpMethod.GET,"/api/login/facultylogin","/api/login/studentlogin")
	         .and()
	         .authorizeRequests()
	         .antMatchers(HttpMethod.POST,"/api/student/deleteenrollment","/api/faculty/deletetagging","/api/course/synccourse","/api/student/syncstudent","/api/student/syncstudentnew","/api/faculty/syncfaculty","/api/topic/synctopic","/api/batch/syncbatch","/api/faculty/syncmapping","/api/student/enrollment","/api/login/facultylogin","/api/login/studentlogin","/api/course/addcourseobjective","/api/courseoutcome/synccourseoutcomes","/api/topic/syncsysunit","/api/gradebook/addGradebookData")
	         .permitAll()
	         .antMatchers(HttpMethod.GET,"/api/login/facultylogin","/api/login/studentlogin")
	         .permitAll()
	         ;
	     }
}