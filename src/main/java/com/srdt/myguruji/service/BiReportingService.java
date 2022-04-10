package com.srdt.myguruji.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.srdt.myguruji.model.StudentCoursePlanInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srdt.myguruji.model.reporting.ContentData;
import com.srdt.myguruji.model.reporting.ContentDataSingle;
import com.srdt.myguruji.model.reporting.QuesBankSingle;

@Service
public class BiReportingService
{
	@Autowired
	@PersistenceContext
	EntityManager em;
	
	public List<ContentData> contentdata(String pnum,String crsecode,List<String> batchid)
	{
		Query query = em.createNativeQuery("select a.faculty_code,a.full_name,e.course_code,course_descr,c.batch_code,c.batch_type,'' rn,g.unit_title\r\n"
				+ ",count(distinct case when h.type_id='1' then h.unit_content_id end) docs\r\n"
				+ ",count(distinct case when h.type_id='2' then h.unit_content_id end) vids\r\n"
				+ ",count(distinct case when start_url is not null then start_url end) link\r\n"
				+ ",count(distinct d.student_id) stdnt,c.batch_id\r\n"
				+ "from faculty_details a\r\n"
				+ "left join faculty_tagging_details b on a.faculty_id=b.faculty_id\r\n"
				+ "left join batch_details c on c.batch_id=b.batch_id\r\n"
				+ "left join student_enrollment d on d.batch_id=c.batch_id\r\n"
				+ "left join course_details e on e.course_id=c.course_id\r\n"
				+ "inner join course_plan f on f.course_id=c.course_id and f.batch_id=b.batch_id\r\n"
				+ "left join unit_details g on g.course_plan_id=f.course_plan_id\r\n"
				+ "left join unit_content h on h.unit_id=g.unit_id\r\n"
				+ "left join lecture_schedule i on i.course_id=e.course_id and i.unit_id=g.unit_id\r\n"
				+ "where a.emplid=:pnum and e.course_id=:crsecode and c.batch_id in(:batchid)\r\n"
				+ "group by a.emplid,b.batch_id,f.course_plan_id,g.unit_id\r\n"
				+ "order by b.batch_id,g.unit_id")
				.setParameter("pnum", pnum)
				.setParameter("crsecode", crsecode)
				.setParameter("batchid", batchid);
		
		List<Object[]> data = query.getResultList();
		List<ContentData> res= data.stream().map(x -> {
			return new ContentData(""+x[0], ""+x[1], ""+x[2], ""+x[3], ""+x[4], ""+x[5], ""+x[6], ""+x[7], ""+x[8]
					, ""+x[9],""+x[10],""+x[11],""+x[12]);
		}).collect(Collectors.toList());
		
		int a=0;
		for(String value : batchid)
		{
			a=0;
			for(ContentData x:res)
	        {
				a++;
				if(x.getBatch_id().equals(value))
				{
					x.setRn(String.valueOf(a));
				}
				else
				{
					a=0;
				}
	        }
		}
		
		return res;
	}
	
	public ContentDataSingle contentdatasingle(String pnum,String crsecode,List<String> batchid)
	{
		Query query = em.createNativeQuery("select a.faculty_code, a.full_name,f.course_descr,group_concat(distinct c.batchtitle order by c.batchtitle),group_concat(distinct c.batch_code order by c.batch_code)\r\n"
				+ ",group_concat(distinct c.batch_type order by c.batch_code)\r\n"
				+ ",COUNT(DISTINCT e.student_id) Students_Count\r\n"
				+ "from faculty_details a\r\n"
				+ "LEFT OUTER JOIN faculty_tagging_details b on a.faculty_id=b.faculty_id\r\n"
				+ "LEFT OUTER JOIN batch_details c on b.batch_id=c.batch_id\r\n"
				+ "LEFT OUTER JOIN student_enrollment d on c.batch_id=d.batch_id\r\n"
				+ "LEFT OUTER JOIN student_details e on d.student_id=e.student_id\r\n"
				+ "LEFT OUTER JOIN course_details f on c.course_id=f.course_id\r\n"
				+ "LEFT OUTER JOIN course_plan g on g.course_id=c.course_id and c.batch_id=g.batch_id\r\n"
				+ "LEFT OUTER JOIN unit_content h on f.course_id=h.course_id\r\n"
				+ "LEFT OUTER JOIN unit_details j on  g.course_plan_id=j.course_plan_id and h.unit_id=j.unit_id\r\n"
				+ "LEFT OUTER JOIN content_type i on i.type_id=h.type_id\r\n"
				+ "where a.faculty_code=:pnum and f.course_id=:crsecode and c.batch_id in (:batchid)\r\n"
				+ "group by a.faculty_code" +";")
				.setParameter("pnum", pnum)
				.setParameter("crsecode", crsecode)
				.setParameter("batchid", batchid);

		List<Object[]> x = query.getResultList();
				
		return new ContentDataSingle(x.get(0)[0].toString(),x.get(0)[1].toString(),x.get(0)[2].toString()
				,x.get(0)[3].toString(),x.get(0)[4].toString(),x.get(0)[5].toString(),x.get(0)[6].toString());
	}
	
	public QuesBankSingle quesbankdatasingle(String facid,String crse_id)
	{
		Object[] x=(Object[]) em.createNativeQuery("select a.faculty_code,a.full_name,d.course_descr,count(distinct e.topic_id)\r\n"
				+ "from faculty_details a\r\n"
				+ "left join faculty_tagging_details b on a.faculty_id=b.faculty_id\r\n"
				+ "left join batch_details c on c.batch_id=b.batch_id\r\n"
				+ "left join course_details d on d.course_id=c.course_id\r\n"
				+ "left join topic_details e on e.course_id=d.course_id\r\n"
				+ "where a.faculty_code=:facid and d.course_id=:crseid\r\n"
				+ "group by a.faculty_id,a.full_name,d.course_id")
				.setParameter("facid", facid)
				.setParameter("crseid", crse_id)
				.getSingleResult();
		
		
		return new QuesBankSingle(x[0].toString(),x[1].toString(),x[2].toString(),x[3].toString());
	}
	/****
	@SuppressWarnings("unchecked")
	public List<Object[]> gettopics(String crse_id)
	{
		return (List<Object[]>)em.createNativeQuery("select distinct a.topic_id,a.topic_descr from topic_details a \r\n"
				+ "inner join question_details b on a.topic_id=b.topic_id where a.course_id=:crseid")
				.setParameter("crseid", crse_id)
				.getResultList();
	}
	***/
	@SuppressWarnings("unchecked")
	public List<Object[]> gettopics(String crse_id)
	{
		return (List<Object[]>)em.createNativeQuery("select distinct a.topic_id,a.topic_descr,concat(c.course_code,' : ',c.course_descr) from topic_details a \r\n"
				+ "left join course_details c on c.course_id=a.course_id inner join question_details b on a.topic_id=b.topic_id where a.course_id=:crseid and b.is_active='Y'")
				.setParameter("crseid", crse_id)
				.getResultList();
	}
	
	public String getquestioncount(String topic_id,String type_id)
	{
		try 
		{
			return em.createNativeQuery("select count(question_id)\r\n"
					+ "from question_details a \r\n"
					+ "where a.topic_id=:topicid and a.type_id=:typeid and a.is_active='Y' \r\n"
					+ "group by topic_id,type_id")
					.setParameter("topicid", topic_id)
					.setParameter("typeid", type_id)
					.getSingleResult().toString();
		}
		catch(Exception e)
		{
			return "0";
		}		
	}
	
	public String getQuestionCountByFaculty(String topic_id,String type_id, String emplid)
	{
		try 
		{
			return em.createNativeQuery("select count(question_id)\r\n"
					+ "from question_details a \r\n"
					+ "where a.topic_id=:topicid and a.type_id=:typeid and a.is_active='Y' \r\n"
					+ " and a.created_by = :emplid "
					+ "group by topic_id,type_id")
					.setParameter("topicid", topic_id)
					.setParameter("typeid", type_id)
					.setParameter("emplid", emplid)
					.getSingleResult().toString();
		}
		catch(Exception e)
		{
			return "0";
		}		
	}

	public List<ContentData> searchByEmplidOrCourse(String emplid, String courseid)
	{
		if(emplid.equalsIgnoreCase("-") || emplid.trim().length()==0)
			emplid="%";
		if(courseid.equalsIgnoreCase("-") || courseid.trim().length()==0)
			courseid="%";

		List<Object[]> courselist=em.createNativeQuery("select a.faculty_code,a.full_name,e.course_code,course_descr,c.batch_code,c.batch_type,'' rn,g.unit_title  " +
				" ,count(distinct case when h.type_id='1' then h.unit_content_id end) docs  " +
				"  ,count(distinct case when h.type_id='2' then h.unit_content_id end) vids  " +
				"   ,count(distinct case when start_url is not null then start_url end) link  " +
				"   ,count(distinct d.student_id) stdnt,c.batch_id  " +
				"   from faculty_details a  " +
				"   left join faculty_tagging_details b on a.faculty_id=b.faculty_id  " +
				"   left join batch_details c on c.batch_id=b.batch_id  " +
				"   left join student_enrollment d on d.batch_id=c.batch_id  " +
				"   left join course_details e on e.course_id=c.course_id  " +
				"   inner join course_plan f on f.course_id=c.course_id and f.batch_id=b.batch_id  " +
				"   left join unit_details g on g.course_plan_id=f.course_plan_id  " +
				"   left join unit_content h on h.unit_id=g.unit_id  " +
				"   left join lecture_schedule i on i.course_id=e.course_id and i.unit_id=g.unit_id  " +
				"   where a.emplid like :Empid and e.course_id like :CoursId  group by a.emplid,b.batch_id,f.course_plan_id,g.unit_id  " +
				"   order by b.batch_id,g.unit_id").setParameter("Empid",emplid).setParameter("CoursId",courseid).getResultList();

		return courselist.stream().map(x->{
			return new  ContentData(x[0].toString(),x[1].toString(),x[2].toString(),x[3].toString(),x[4].toString(),x[5].toString(),x[6].toString(),x[7].toString(),x[8].toString(),x[9].toString(),x[10].toString(),x[11].toString(),x[12].toString());
		}).collect(Collectors.toList());

	}
}