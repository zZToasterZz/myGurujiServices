package com.srdt.myguruji.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.SyllabusPlan;

@Repository
public interface SyllabusPlanRepository extends JpaRepository<SyllabusPlan, Long> {

}
