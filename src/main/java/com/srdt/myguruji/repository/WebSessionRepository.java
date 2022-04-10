package com.srdt.myguruji.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.srdt.myguruji.enitity.WebSession;

public interface WebSessionRepository extends JpaRepository<WebSession, Long> {

}
