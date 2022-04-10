package com.srdt.myguruji.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.CourseDetails;
import com.srdt.myguruji.model.Courses;
import com.srdt.myguruji.model.Translate;

@Repository
public interface CourseDetailsRepository extends JpaRepository<CourseDetails, Long> {
	
	@Modifying
	@Transactional
	void updateCourseDetails(@Param("CourseTitle") String CourseTitle,@Param("CourseDescr") String CourseDescr,@Param("CourseCode") String CourseCode,@Param("CourseId") long CourseId );
    @Transactional
    void deleteCourseDetailsByCourseId(@Param("CourseId") List<Long> CourseId);
    List<Courses> searchCourses(@Param("id")String id,@Param("code") String code,@Param("title") String title,@Param("Emplid") String Emplid);
    List<Courses> findAllCourseDetails(@Param("Emplid") String Emplid);
    List<Translate> findAllCourses(@Param("Emplid") String Emplid);
    Translate findCourseByCourseId(@Param("CourseId") long CourseId);
    CourseDetails findCourseDetailsByCourseId(@Param("CourseId") long CourseId);
}
