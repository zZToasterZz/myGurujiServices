package com.srdt.myguruji.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.AssignmentAttachments;
import com.srdt.myguruji.enitity.AssignmentResponse;

@Repository
public interface AssignmentAttachmentRepository extends JpaRepository<AssignmentAttachments, Long>
{
	//List<AssignmentAttachments> findResponseByStudentId(@Param("assignid") long assignid, @Param("stdentid") long stdentid, @Param("quesid") long quesid,@Param("responseid") long responseid);
	
	@Modifying
	@Transactional
	int deleteStudentAttachments(@Param("stdntid")long stdntid, @Param("asgnid")long asgnid, @Param("quesid")long quesid, @Param("atchid")List<Long> atchid);

	@Query(value="select * from assignment_attachments where assignment_attachmentid=(select Max(a.assignment_attachmentid) from assignment_attachments a where\n" +
			"a.assignmentid=:asgnid and a.question_id=:quesid and a.student_id=:stdntid and a.assignment_responseid=:responseid)", nativeQuery = true)
	AssignmentAttachments findResponseAttachment(@Param("stdntid")long stdntid, @Param("asgnid")long asgnid, @Param("quesid")long quesid, @Param("responseid") long responseid);
	
	
}