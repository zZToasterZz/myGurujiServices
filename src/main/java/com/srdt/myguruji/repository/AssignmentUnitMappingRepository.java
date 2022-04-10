package com.srdt.myguruji.repository;

import java.util.List;

import com.srdt.myguruji.enitity.AssignmentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.AssignmentUnitMapping;

@Repository
public interface AssignmentUnitMappingRepository extends JpaRepository<AssignmentUnitMapping, Long> {

	List<Long> getAssignmentIds(@Param("unitId") long unitId );
	List<AssignmentUnitMapping> getAssignmentDetail(@Param("levl") String levl, @Param("createdby") String createdby, @Param("courseplanId") long courseplanId);

	List<AssignmentUnitMapping> getAssignmentDetailsforUnit(@Param("createdby") String createdby,@Param("courseplanId") long courseplanId ,@Param("ids") List<Long> ids);

	int getFacultyAssignmentCountforCourseplan(@Param("emplid") String emplid ,@Param("crplanid") long crplanid );

	int getFacultyAssignmentCountforUnit(@Param("emplid") String emplid ,@Param("crplanid") long crplanid,@Param("unitid") long unitid);
}
