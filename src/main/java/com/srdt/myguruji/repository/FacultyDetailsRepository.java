package com.srdt.myguruji.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.CourseDetails;
import com.srdt.myguruji.enitity.FacultyDetails;
import com.srdt.myguruji.model.Courses;
import com.srdt.myguruji.model.Faculty;

import javax.transaction.Transactional;

@Repository
public interface FacultyDetailsRepository extends JpaRepository<FacultyDetails, Long> { 

	FacultyDetails findByFacultyId(@Param("FacultyId") long FacultyId);
	List<Faculty> searchFaculty(@Param("FacultyId") String FacultyId,@Param("FacultyCode") String FacultyCode,
			                    @Param("Emplid") String EmplId,@Param("FirstName") String FirstName,
			                    @Param("EmailAddr") String EmailAddr,@Param("PrimaryContact") String PrimaryContact,
			                    @Param("Designation") String Designation);
	FacultyDetails getFacultynameByEmplid(@Param("emplid") String emplid);
	FacultyDetails getFacultyByEmplid(@Param("emplId") String emplId);

	@Transactional
	@Modifying
	int updateEmailByEmplId(@Param("email") String email, @Param("modifieddate") Date modifieddate, @Param("emplid") String emplid);

	@Transactional
	@Modifying
	int updateContactByEmplId(@Param("contact") String contact, @Param("modifieddate") Date modifieddate,@Param("emplid") String emplid);

	@Transactional
	@Modifying
	int updateEmailPhone(@Param("email") String email,@Param("phone") String phone, @Param("modifieddate") Date modifieddate,@Param("emplid") String emplid);
	
	List<Courses> getCoursesByFacultyId(@Param("emplid")String emplid);
	List<FacultyDetails> getFacultyByCourseId(@Param("courseid")long courseid);
	List<FacultyDetails> searchFacultyByCourseOrBatch(long courseid, long batchid);
}
