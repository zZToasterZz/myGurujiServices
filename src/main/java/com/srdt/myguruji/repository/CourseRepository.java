package com.srdt.myguruji.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.srdt.myguruji.model.Books;
import com.srdt.myguruji.model.Plan;
import com.srdt.myguruji.model.SubUnit;
import com.srdt.myguruji.model.Topic;
import com.srdt.myguruji.model.Unit;

@Repository
public interface CourseRepository
{
	long CreateCoursePlan(Plan plan);
	void AddSubUnits(List<SubUnit> subUnits);
	void setAptflag(boolean aptflag);
	void AddUnits(List<Unit> units);
	void AddBooks(List<Books> books);
	void addTopic(List<Topic> topics);
	List<Topic> syncTopic(List<Topic> topics);
}