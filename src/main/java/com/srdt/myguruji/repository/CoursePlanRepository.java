package com.srdt.myguruji.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.CourseBooks;
import com.srdt.myguruji.enitity.CourseDetails;
import com.srdt.myguruji.enitity.CoursePlan;
import com.srdt.myguruji.model.PlanSearch;
import com.srdt.myguruji.model.Translate;

@Repository
public interface CoursePlanRepository extends JpaRepository<CoursePlan, Long> {
	
	CourseDetails findCourseByCourseId(@Param("CourseId") long CourseId);
	@Transactional
	@Modifying
	void deleteByCoursePlanId(@Param("CoursePlanId") List<Long> CoursePlanId);
	List<CoursePlan> findCoursePlanByCourseId(@Param("CourseId") long CourseId);
	List<CoursePlan> findCoursePlanDetailsbyCoursePlanId(@Param("CoursePlanId") long CoursePlanId);
	CourseBooks findCourseBookById(@Param("BookId") long BookId);
	List<CoursePlan> findCoursePlanDetails();
	List<Translate> findCoursePlan();
	List<Translate> findCoursePlanbyCourseId(@Param("CourseId") long CourseId);
	List<PlanSearch> findCoursePlanByEmplid(@Param("Emplid") String Emplid,@Param("plancode") String plancode,@Param("plantitle") String plantitle,@Param("courseid") String courseid);
	List<CoursePlan> getCourseBookByCourseId(@Param("CourseId") long courseid);	
	List<CoursePlan> findCoursePlanDetailsbyBatchId(@Param("BatchId") long BatchId);
	CoursePlan getCoursePlanById(@Param("courseplanId") long courseplanId);
}
