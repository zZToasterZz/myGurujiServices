package com.srdt.myguruji.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.LockedUsers;
import com.srdt.myguruji.model.LockedUser;

@Repository
public interface LockedUsersRepository extends JpaRepository<LockedUsers, Long>
{
	LockedUsers getStatusByStudentId(@Param("ID")long id);

	long getStatuscountByStudentId(@Param("ID")long id);

	
	@Modifying
	@Transactional
	void unlockUser(@Param("ID") long id);
	
	List<LockedUsers> getLockedUsers();

	List<Long> getLockedUserStudentIdByBatch(@Param("batchid") long batchid);
}