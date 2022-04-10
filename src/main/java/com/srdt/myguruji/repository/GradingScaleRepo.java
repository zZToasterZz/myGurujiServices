package com.srdt.myguruji.repository;

import com.srdt.myguruji.enitity.GradingScale;
import com.srdt.myguruji.model.GradebookMarksModel;
import com.srdt.myguruji.model.ResponceMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradingScaleRepo extends JpaRepository<GradingScale,Long> {

    List<String> getDistinctScaleName();
    List<GradingScale> getDistinctScaleByName(@Param("gradingname") String gradingname);
    long getDistinctScaleCountByName(@Param("gradingname") String gradingname);
}
