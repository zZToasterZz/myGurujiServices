package com.srdt.myguruji.repository;

import com.srdt.myguruji.enitity.GradebookPSMarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface GradebookPSMarksRepo extends JpaRepository<GradebookPSMarks, Long> {
    List<GradebookPSMarks> getAllGradebookPSMarks();

    @Transactional
    @Modifying
    int deleteGradebookPSMarksByClassNumber(@Param("classnbr") String classnbr);

    long getCountGradebookPSMarksByClassNumber(@Param("classnbr") String classnbr);
}
