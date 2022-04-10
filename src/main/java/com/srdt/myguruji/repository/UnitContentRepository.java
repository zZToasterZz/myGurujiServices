package com.srdt.myguruji.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.UnitContent;

@Repository
public interface UnitContentRepository extends JpaRepository<UnitContent, Long>
{	
	List<UnitContent> getUnitContentByUnitId(@Param("UnitId") long unitid);
	@Transactional
	@Modifying
	void deleteUnitContent(@Param("UnitId") List<Long> UnitId);
    BigInteger getUnitContentCount(@Param("UnitId") long UnitId);
    
    //sandhya
  	@Transactional
  	@Modifying
  	int updateUnitContent(@Param("dscr")String dscr,@Param("title") String title,@Param("unitId") long unitId);

	int getDocUploadCount(@Param("crsid") long crsid,@Param("unitid") long unitid, @Param("crsplanid") long crsplanid,@Param("prvdate") Date prvdate  );

	int getVideoUploadCount(@Param("crsid") long crsid,@Param("unitid") long unitid, @Param("crsplanid") long crsplanid,@Param("prvdate") Date prvdate );
}