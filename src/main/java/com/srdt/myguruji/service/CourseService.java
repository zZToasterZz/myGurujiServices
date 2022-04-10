package com.srdt.myguruji.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srdt.myguruji.enitity.CourseBooks;
import com.srdt.myguruji.enitity.CourseDetails;
import com.srdt.myguruji.enitity.CourseObjectiveDetails;
import com.srdt.myguruji.enitity.CourseObjectiveMaping;
import com.srdt.myguruji.enitity.CoursePlan;
import com.srdt.myguruji.enitity.SubUnitDetails;
import com.srdt.myguruji.enitity.TopicDetails;
import com.srdt.myguruji.enitity.UnitDetails;
import com.srdt.myguruji.model.Books;
import com.srdt.myguruji.model.Plan;
import com.srdt.myguruji.model.SubUnit;
import com.srdt.myguruji.model.Topic;
import com.srdt.myguruji.model.Unit;
import com.srdt.myguruji.repository.BatchRepository;
import com.srdt.myguruji.repository.CourseObjectiveRepository;
import com.srdt.myguruji.repository.CoursePlanRepository;
import com.srdt.myguruji.repository.CourseRepository;
import com.srdt.myguruji.repository.SubUnitDetailsRepository;
import com.srdt.myguruji.repository.TopicDetailsRepository;

@Service
public class CourseService implements CourseRepository
{
	@Autowired
	@PersistenceContext
	EntityManager em;
	
	boolean aptflag = false;
	
	@Autowired
	CoursePlanRepository planrep;
	@Autowired
	SubUnitDetailsRepository subunitrep;
	@Autowired
	TopicDetailsRepository topipresp;
	@Autowired
	BatchRepository batchrep;
	@Autowired
	CourseObjectiveRepository crsobjrep;
	
	@Override
	@Transactional
	public long CreateCoursePlan(Plan plan)
	{
		// TODO Auto-generated method stub
		CoursePlan coursePlan;
		//System.out.println("aptflag : "+aptflag);
		if(aptflag)
		{
			List<Long> unitids = plan.getUnits().stream()
                    .map(x->{
                    	return new Long(x.getUnitid());
                    })
                    .collect(Collectors.toList());
			
            List<Long> bookids = plan.getBooks().stream()
                    .map(x->{
                    	return new Long(x.getBookid());
                    })
                    .collect(Collectors.toList());
            
			Query query = em.createNamedQuery("CoursePlan.deleteUnitDetailsByCoursePlanId");
			query.setParameter("CoursePlanId", plan.getCourseplanid());
			query.setParameter("UnitId", unitids);
			query.executeUpdate();
			
			query = em.createNamedQuery("CoursePlan.deleteCourseBookByCoursePlanId");
			query.setParameter("CoursePlanId", plan.getCourseplanid());
			query.setParameter("BookId", bookids);
			query.executeUpdate();
		}
		
		if(plan.getCourseplanid() == 0)
		{
			coursePlan = new CoursePlan(plan.getCourseplantitle(), plan.getCourseplandescr(), planrep.findCourseByCourseId(plan.getCourseid()), plan.getCreatedby(),plan.getPlancode(),plan.getLeccount());
		}
		else
		{
			coursePlan = em.find(CoursePlan.class, plan.getCourseplanid());
		}
		
		List<CourseBooks> courseBooks = plan.getBooks().stream()
		               .map(x->{
		            	   return new CourseBooks(x.getBooktitle(), x.getBookdescr(), x.getBookauthor(), plan.getCreatedby(), x.getBooktype(), coursePlan,x.getBookid());
		               })
		               .collect(Collectors.toList());
		
		List<UnitDetails> unitDetails = plan.getUnits()
				                            .stream()
				                            .map(x->{
				                            	return new UnitDetails(x.getUnittitle(), x.getUnitdescr(), coursePlan, plan.getCreatedby(),x.getUnitid(),x.getObjectives(),x.getTopics(),x.getObjectivesid(),x.getTopicsid());
				                            })
				                            .collect(Collectors.toList());
		if(aptflag)
		{
			em.flush();
			em.clear();
			coursePlan.setUnitDetails(unitDetails);
			coursePlan.setCourseBooks(courseBooks);
			coursePlan.setModifiedBy(plan.getCreatedby());
			coursePlan.setPlanCode(plan.getPlancode());
			coursePlan.setCoursePlanDescr(plan.getCourseplandescr());
			coursePlan.setCoursePlanTitle(plan.getCourseplantitle());
			em.merge(coursePlan);
		}
		else
		{
			coursePlan.setUnitDetails(unitDetails);
			coursePlan.setCourseBooks(courseBooks);
			coursePlan.setBatchDetails(batchrep.findBatchByBatchId(plan.getBatchid()));
			em.persist(coursePlan);	
		}
		//System.out.println("aptflag : "+aptflag);
		em.flush();
		em.clear();
		em.close();
		return coursePlan.getCoursePlanId();
	}
	
	@Transactional
	@Override
	public void AddSubUnits(List<SubUnit> subUnits)
	{
		List<Long> subUnitIds = subUnits.stream()
                .map(subUnit -> {
              	  return new Long(subUnit.getSubunitid());
                })
                .collect(Collectors.toList());
		
		Query query = em.createNamedQuery("SubUnitDetails.deleteSubUnitsByUnitId");
		query.setParameter("UnitId", subUnits.get(0).getUnitid());
		query.setParameter("SubUnitId", subUnitIds);
		query.executeUpdate();
		
		UnitDetails unitDetails = em.find(UnitDetails.class, subUnits.get(0).getUnitid());
		List<SubUnitDetails> subUnitDetails = subUnits.stream()
				                                      .map(subUnit -> {
				                                    	  return new SubUnitDetails(subUnit.getSubunittitle(), subUnit.getSubunitdescr(), unitDetails ,subUnit.getCreatedby(),subUnit.getSubunitid(),subUnit.getTopicid());
				                                      })
				                                      .collect(Collectors.toList());
		List<Long> mapid = new ArrayList<>();
		mapid.clear();
		mapid.add(new Long(0));
		List<CourseObjectiveMaping> objectiveMapings = subUnits.get(0).getObjectivemap().stream().map(x->{
			                                               CourseObjectiveDetails objectiveDetails = crsobjrep.CourseObjectiveById(x.getObjectiveid());
			                                               mapid.add(x.getObjectivemapid());
			                                               return new CourseObjectiveMaping(x.getObjectivemapid(),objectiveDetails, unitDetails);
		                                               }).collect(Collectors.toList());
		
		crsobjrep.deleteCourseObjectiveMapping(subUnits.get(0).getUnitid(), mapid);
		
		if(aptflag)
		{
		    em.flush();
		    em.clear();
		    unitDetails.setSubUnitDetails(subUnitDetails);
		    unitDetails.setObjectiveMapings(objectiveMapings);
		    em.merge(unitDetails);
		}
		else
		{			
			unitDetails.setSubUnitDetails(subUnitDetails);
			unitDetails.setObjectiveMapings(objectiveMapings);
			em.persist(unitDetails);
		}
		
		em.flush();
		em.clear();
		em.close();
	}

	@Override
	public void setAptflag(boolean aptflag) {
		// TODO Auto-generated method stub
		this.aptflag = aptflag;
	}

	@Override
	@Transactional
	public void AddUnits(List<Unit> units) {
		// TODO Auto-generated method stub
		
		
		if(aptflag)
		{
			List<Long> unitids = units.stream()
	                  .map(x->{
	                	  return new Long(x.getUnitid());
	                  })
	                  .collect(Collectors.toList());
			Query query = em.createNamedQuery("CoursePlan.deleteUnitDetailsByCoursePlanId");
			query.setParameter("CoursePlanId", units.get(0).getCourseplanid());
			query.setParameter("UnitId", unitids);
			query.executeUpdate();
		}
		
        CoursePlan coursePlan  = em.find(CoursePlan.class, units.get(0).getCourseplanid());
		
		List<UnitDetails> unitDetails = units
                .stream()
                .map(x->{
                	return new UnitDetails(x.getUnittitle(), x.getUnitdescr(), coursePlan, x.getCreatedby(),x.getUnitid(),x.getObjectives(),x.getTopics(),x.getObjectivesid(),x.getTopicsid());
                })
                .collect(Collectors.toList());
		if(aptflag)
		{
			em.flush();
			em.clear();
			coursePlan.setUnitDetails(unitDetails);
			em.merge(coursePlan);
		}
		else
		{
			coursePlan.setUnitDetails(unitDetails);
		}
		em.flush();
		em.clear();	
		em.close();
			
	}

	@Override
	@Transactional
	public void AddBooks(List<Books> books) {
		// TODO Auto-generated method stub
				
		if(aptflag)
		{
			List<Long> bookids = books.stream()
	                  .map(x->{
	                	  return new Long(x.getBookid());
	                  })
	                  .collect(Collectors.toList());
			Query query = em.createNamedQuery("CoursePlan.deleteCourseBookByCoursePlanId");
			query.setParameter("CoursePlanId", books.get(0).getCourseplanid());
			query.setParameter("BookId", bookids);
			query.executeUpdate();
		}
		
		CoursePlan coursePlan  = em.find(CoursePlan.class, books.get(0).getCourseplanid());
		List<CourseBooks> courseBooks = books.stream()
	               .map(x->{
	            	   return new CourseBooks(x.getBooktitle(), x.getBookdescr(), x.getBookauthor(), coursePlan.getCreatedBy(), x.getBooktype(), coursePlan,x.getBookid());
	               })
	               .collect(Collectors.toList());
		
		if(aptflag)
		{
			em.flush();
			em.clear();
			coursePlan.setCourseBooks(courseBooks);
			em.merge(coursePlan);
		}
		else
		{
			coursePlan.setCourseBooks(courseBooks);
		}
		em.flush();
		em.clear();	
		em.close();
	}
	
	@Override
	@Transactional
	public void addTopic(List<Topic> topics)
	{
		em.unwrap(Session.class).setJdbcBatchSize(30);
		em.setFlushMode(FlushModeType.COMMIT);
		long courseid = topics.get(0).getCourseid();
		CourseDetails courseDetails = em.find(CourseDetails.class, topics.get(0).getCourseid());
		List<Long> topicid = topics.stream()
                .map(x->{
             	   return new Long(x.getTopicid());
                })
                .collect(Collectors.toList());
		
		//Query query = em.createNamedQuery("TopicDetails.deleteByCourseDetails");
		//query.setParameter("CourseId", courseid);
		//query.setParameter("TopicId", topicid);
		//query.executeUpdate();
		topics.stream().forEach(x->{
			em.merge(new TopicDetails(x.getTopicid(),x.getTopictitle(), x.getTopicdescr(), courseDetails, x.getCreatedby(),x.getSysunitid()));			
		});
		em.flush();
		em.clear();
		em.close();
	}

	@Override
	@Transactional
	public List<Topic> syncTopic(List<Topic> topics) {
		// TODO Auto-generated method stub
		em.unwrap(Session.class).setJdbcBatchSize(30);
		em.setFlushMode(FlushModeType.COMMIT);
		long courseid = topics.get(0).getCourseid();
		
		List<Long> topicid = topics.stream()
                .map(x->{
             	   return new Long(x.getTopicid());
                })
                .collect(Collectors.toList());
		
		Query query = em.createNamedQuery("TopicDetails.deleteByCourseDetails");
		query.setParameter("CourseId", courseid);
		query.setParameter("TopicId", topicid);
		query.executeUpdate();
		topics = topics.stream().map(x->{
			CourseDetails courseDetails = em.find(CourseDetails.class, x.getCourseid());
			TopicDetails topicDetails = new TopicDetails(x.getTopicid(),x.getTopictitle(), x.getTopicdescr(), courseDetails, x.getCreatedby(),x.getSysunitid());
			em.merge(topicDetails);
			
			return new Topic(topicDetails.getTopicId(),x.getCourseid(),x.getTopictitle(), x.getTopicdescr(), x.getCreatedby(),x.getSysunitid());
			
		}).collect(Collectors.toList());
		em.flush();
		em.clear();
		em.close();
		return topics;
	}

}
