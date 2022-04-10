package com.srdt.myguruji.repository;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.StudentDetails;
import com.srdt.myguruji.model.Student;

import javax.transaction.Transactional;

@Repository
public interface StudentDetailsRepository extends JpaRepository<StudentDetails, Long> {

	List<Student> searchStudent(@Param("StudentId") String StudentId,@Param("Emplid") String EmplId,@Param("ApplNbr") String ApplNbr,
			                     @Param("CampusId") String CampusId,@Param("FirstName") String FirstName,
			                     @Param("EmailAddr") String EmailAddr,@Param("PrimaryContact") String PrimaryContact);
	StudentDetails findByStudentId(@Param("StudentId") long StudentId);
	
	List<Student> findAllStudents(Pageable pageable);
	StudentDetails findStudentById(@Param("StudentId") long StudentId);
	long findStudentidbyEmplid(@Param("emplid") String emplid);
	StudentDetails findStudentbyEmplid(@Param("emplid") String emplid);

	StudentDetails getStudentByCampusId(@Param("campusId") String campusId);

	@Transactional
	@Modifying
	int updateStudentEmailByEmplId(@Param("email") String email, @Param("modifieddate") Date modifieddate, @Param("emplid") String emplid);

	@Transactional
	@Modifying
	int updateStudentContactByEmplId(@Param("contact") String contact, @Param("modifieddate") Date modifieddate,@Param("emplid") String emplid);

	@Transactional
	@Modifying
	int updateStudentEmailandPhone(@Param("email") String email,@Param("phone") String phone, @Param("modifieddate") Date modifieddate,@Param("emplid") String emplid);


}