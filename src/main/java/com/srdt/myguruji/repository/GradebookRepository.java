package com.srdt.myguruji.repository;

import com.srdt.myguruji.enitity.GradeBookStaging;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface GradebookRepository extends JpaRepository<GradeBookStaging,Long> {


    @Transactional
    @Modifying
    int deleteGradeBookStagingByClassNumber(@Param("classnbr") String classnbr);

    long getCountGradeBookStagingByClassNumber(@Param("classnbr") String classnbr);
}
