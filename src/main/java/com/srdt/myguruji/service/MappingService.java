package com.srdt.myguruji.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.srdt.myguruji.enitity.BatchDetails;
import com.srdt.myguruji.enitity.FacultyDetails;
import com.srdt.myguruji.enitity.FacultyTaggingDetails;
import com.srdt.myguruji.enitity.StudentDetails;
import com.srdt.myguruji.enitity.StudentEnrollment;
import com.srdt.myguruji.model.Faculty;
import com.srdt.myguruji.model.FacultyTag;
import com.srdt.myguruji.model.Student;
import com.srdt.myguruji.model.StudentEnrol;
import com.srdt.myguruji.repository.BatchRepository;
import com.srdt.myguruji.repository.FacultyDetailsRepository;
import com.srdt.myguruji.repository.MappingRepository;
import com.srdt.myguruji.repository.StudentDetailsRepository;

@Service
public class MappingService implements MappingRepository
{
	@Autowired
	EntityManager em;
	
	@Autowired
	FacultyDetailsRepository facultyrep;
	@Autowired
	BatchRepository batchrep;
	@Autowired
	StudentDetailsRepository studentrep;
	
	@Override
	@Transactional
	public List<FacultyTag> facultyTagging(List<FacultyTag> tags) {
		// TODO Auto-generated method stub		
		em.unwrap(Session.class).setJdbcBatchSize(30);
		em.setFlushMode(FlushModeType.COMMIT);
		
		tags = tags.stream().map(x->{
			       BatchDetails batchDetails = batchrep.findBatchByBatchId(x.getBatchid());
        	       FacultyDetails facultyDetails = facultyrep.findByFacultyId(x.getFacultyid());
        	       FacultyTaggingDetails details = new FacultyTaggingDetails(facultyDetails, batchDetails, x.getCreatedby());
        	       em.persist(details);
        	       return new FacultyTag(details.getTagId(),x.getFacultyid(), x.getBatchid(), x.getCreatedby());
        })
	    .collect(Collectors.toList());		
		em.flush();
		em.clear();
		em.close();
		return tags;
	}

	@Override
	@Transactional
	public List<Faculty> syncFaculty(List<Faculty> faculties) {
		// TODO Auto-generated method stub
		em.unwrap(Session.class).setJdbcBatchSize(30);
		em.setFlushMode(FlushModeType.COMMIT);
		faculties = faculties.stream()
                .map(x->{
                	FacultyDetails facultyDetails = new FacultyDetails(x.getFacultycode(), x.getEmplid(), x.getPref(), x.getFirstname(), x.getMidlename(), x.getLastname(), x.getEmailaddr(), x.getPrimarycontact(), x.getSecondarycontact(), x.getDesignation(), x.getCreatedby());
                	em.persist(facultyDetails);
                	return new Faculty(facultyDetails.getFacultyId(), x.getFacultycode(), x.getEmplid(), x.getDesignation(), x.getPref(), x.getFirstname(), x.getMidlename(), x.getLastname(), facultyDetails.getFullName(), x.getEmailaddr(), x.getPrimarycontact(), x.getSecondarycontact(), x.getCreatedby());
                })
                .collect(Collectors.toList());

		em.flush();
		em.clear();
		em.close();
		return faculties;
	}

	@Override
	@Transactional
	public List<Student> syncStudent(List<Student> students) {
		// TODO Auto-generated method stub
		em.unwrap(Session.class).setJdbcBatchSize(30);
		em.setFlushMode(FlushModeType.COMMIT);
		
		
		students = students.stream()
                .map(x->{
                	StudentDetails studentDetails = new StudentDetails(x.getEmplid(), x.getApplnbr(), x.getCampusid(), x.getFirstname(), x.getMidlename(), x.getLastname(), x.getDob(), x.getEmailaddr(), x.getPrimarycontact(), x.getSecondarycontact(), x.getCreatedby());
                	em.persist(studentDetails);
                	return new Student(studentDetails.getStudentId(), x.getEmplid(), x.getApplnbr(), x.getCampusid(), x.getFirstname(), x.getMidlename(), x.getLastname(), studentDetails.getFullName(),x.getDob(), x.getEmailaddr(), x.getPrimarycontact(), x.getSecondarycontact(), x.getCreatedby());
                })
                .collect(Collectors.toList());

		
		em.flush();
		em.clear();
		em.close();
		
		return students;
	}

	@Override
	@Transactional
	public List<Student> syncStudent_new(List<Student> students)
	{
		em.unwrap(Session.class).setJdbcBatchSize(30);
		em.setFlushMode(FlushModeType.COMMIT);


		students = students.stream()
				.map(x->{

					long sid = 0;
					try
					{
						sid = studentrep.findStudentidbyEmplid(x.getEmplid());
					}
					catch(Exception e)
					{
						sid = 0;
					}
					if(sid == 0)
					{
						StudentDetails studentDetails = new StudentDetails(x.getEmplid(), x.getApplnbr(), x.getCampusid(), x.getFirstname(), x.getMidlename(), x.getLastname(), x.getDob(), x.getEmailaddr(), x.getPrimarycontact(), x.getSecondarycontact(), x.getCreatedby());
						em.persist(studentDetails);
						return new Student(studentDetails.getStudentId(), x.getEmplid(), x.getApplnbr(), x.getCampusid(), x.getFirstname(), x.getMidlename(), x.getLastname(), studentDetails.getFullName(),x.getDob(), x.getEmailaddr(), x.getPrimarycontact(), x.getSecondarycontact(), x.getCreatedby(),"SAVED");
					}
					else
					{
						StudentDetails studentDetails = studentrep.findByStudentId(sid);
						return new Student(studentDetails.getStudentId(), studentDetails.getEmplid(), studentDetails.getApplNbr(), studentDetails.getCampusId(), studentDetails.getFirstName(), studentDetails.getMidleName(), studentDetails.getLastName(), studentDetails.getFullName(),studentDetails.getDateOfBirth(), studentDetails.getEmailAddr(), studentDetails.getPrimaryContact(), studentDetails.getSecondaryContact(), studentDetails.getCreatedBy(),"EXISTS");
					}


				/*StudentDetails studentDetails = new StudentDetails(x.getEmplid(), x.getApplnbr(), x.getCampusid(), x.getFirstname(), x.getMidlename(), x.getLastname(), x.getDob(), x.getEmailaddr(), x.getPrimarycontact(), x.getSecondarycontact(), x.getCreatedby());
				em.persist(studentDetails);
				return new Student(studentDetails.getStudentId(), x.getEmplid(), x.getApplnbr(), x.getCampusid(), x.getFirstname(), x.getMidlename(), x.getLastname(), studentDetails.getFullName(),x.getDob(), x.getEmailaddr(), x.getPrimarycontact(), x.getSecondarycontact(), x.getCreatedby());*/
				})
				.collect(Collectors.toList());

		em.flush();
		em.clear();
		em.close();

		return students;
	}




	@Override
	@Transactional
	public List<StudentEnrol> studentEnrol(List<StudentEnrol> studentEnrols) {
		// TODO Auto-generated method stub
		em.unwrap(Session.class).setJdbcBatchSize(30);
		em.setFlushMode(FlushModeType.COMMIT);
		
		studentEnrols = studentEnrols.stream()
				                     .map(x->{
				                    	 BatchDetails batchDetails = batchrep.findBatchByBatchId(x.getBatchid());
				                    	 StudentDetails student = studentrep.findStudentById(x.getStudentid());
				                    	 StudentEnrollment studentEnrollment = new StudentEnrollment(student, batchDetails,x.getCreatedby());
				                    	 em.persist(studentEnrollment);
				                    	 return new StudentEnrol(x.getStudentid(), x.getBatchid(), studentEnrollment.getEnrollmentId(), x.getCreatedby());
				                     })
				                     .collect(Collectors.toList());		
		
		em.flush();
		em.clear();
		em.close();
		return studentEnrols;
	}

	//Shantanu
	@Override
	@Transactional
	@Modifying
	public int deleteEnrollment(List<Long> enrollID)
	{
		em.unwrap(Session.class).setJdbcBatchSize(30);
		em.setFlushMode(FlushModeType.COMMIT);
		
		int total = 0;
		for(long i : enrollID)
		{
			StudentEnrollment x = em.find(StudentEnrollment.class, i);
			if(x != null)
			{
				try{
					em.remove( x );
					++total;
				}catch(Exception e){
				}
			}
		}
		
		em.flush();
		em.clear();
		em.close();
		return total;
	}
	
	//Shantanu
	@Override
	@Transactional
	@Modifying
	public int deleteTagging(List<Long> tagID)
	{
		em.unwrap(Session.class).setJdbcBatchSize(30);
		em.setFlushMode(FlushModeType.COMMIT);
		
		int total = 0;
		for(long i : tagID)
		{
			FacultyTaggingDetails x = em.find(FacultyTaggingDetails.class, i);
			if(x != null)
			{
				try{
					em.remove( x );
					++total;
				}catch(Exception e){
				}
			}
		}
		
		em.flush();
		em.clear();
		em.close();
		return total;
	}
}