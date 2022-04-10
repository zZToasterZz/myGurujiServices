package com.srdt.myguruji.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.srdt.myguruji.model.Faculty;
import com.srdt.myguruji.model.FacultyTag;
import com.srdt.myguruji.model.Student;
import com.srdt.myguruji.model.StudentEnrol;

@Repository
public interface MappingRepository
{
	List<FacultyTag> facultyTagging(List<FacultyTag> tags);
	List<Faculty> syncFaculty(List<Faculty> faculties);
	List<Student> syncStudent(List<Student> students);
	List<StudentEnrol> studentEnrol(List<StudentEnrol> studentEnrols);
	int deleteEnrollment(List<Long> enrollID);
	int deleteTagging(List<Long> tagID);
	List<Student> syncStudent_new(List<Student> students);
}