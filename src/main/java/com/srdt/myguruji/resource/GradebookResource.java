package com.srdt.myguruji.resource;


import com.srdt.myguruji.enitity.*;
import com.srdt.myguruji.model.*;
import com.srdt.myguruji.repository.*;
import com.srdt.myguruji.utility.Generation;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.service.ResponseMessage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gradebook")
public class GradebookResource {

    @Autowired
    @PersistenceContext
    EntityManager em;

    @Autowired
    GradebookRepository gradebookrepo;

    @Autowired
    GradebookDescriptionRepo gradebookDescrRepo;

    @Autowired
    StudentDetailsRepository studrepo;

    @Autowired
    CourseDetailsRepository crscrep;

    @Autowired
    BatchRepository batchrepo;

    @Autowired
    GradebookPSMarksRepo gradebookPSMarksrepo;

    @Autowired
    GradeBookMarksRepo repo;

    @Autowired
    GradeBookFreezeRepo freezestatusrepo;

    @Autowired
    GradeBookLamtypeRepo lamtyperepo;
/*

    @GetMapping("/assessmentAssignmentType/{batchid}")
    public List<String> assessmentandAssignmenttype(@PathVariable long batchid)
    {
        Query qry=em.createNativeQuery("select distinct  concat(descr_short,' (',marks_ou_of,')') " +
                "from grade_book_staging a inner join batch_details b on a.class_number=b.batch_code " +
                "where a.descr_short<>'' and b.batch_id=:BatchId").setParameter("BatchId",batchid);
        List<String> typelist=qry.getResultList();
        return typelist;
    }
*/
    @GetMapping("/assessmentAssignmentTitleList/{courseid}")
    public List<String> getTitlelist(@PathVariable long courseid)
    {
        Query qry=em.createNativeQuery("select concat(assessment_id,' : ',title,' (Assessment)') from assessment_details where course_id=:CourseId union   " +
                "select concat(assignmentid,' : ',assignment_title,' (Assignment)') from assignment_details where course_id=:CourseId").setParameter("CourseId",courseid);
        List<String> titlelist=qry.getResultList();
        return titlelist;

    }

    @GetMapping(value = "/getMarksById/{asid}/{astype}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DoubleResponseModel> getStudnetList(@PathVariable long asid,@PathVariable String  astype)
    {
        Query qry=null;

        if(astype.equalsIgnoreCase("assessment")) {
             qry = em.createQuery("select sum(marksobtained),Emplid from AssessmentResponce where assessmentDetails.AssessmentId=:assessmentId  group by Emplid")
                    .setParameter("assessmentId", asid);

        }
        else {
             qry = em.createQuery("select sum(MarksObtained),studentDetails.StudentId from AssignmentResponse where assignmentDetails.assignmentID=:assignmentId  group by studentDetails.StudentId")
                    .setParameter("assignmentId", asid);

        }

            List<Object[]> data = qry.getResultList();
            List<DoubleResponseModel> assessmentmarkslist = data.stream().map(x -> {
                StudentDetails stdnt=studrepo.findByStudentId(Long.parseLong(x[1].toString()));
                String semplid="";
                if(astype.equalsIgnoreCase("assessment"))
                    semplid=x[1].toString();
                else
                    semplid=stdnt.getEmplid();
                return new DoubleResponseModel(x[0].toString(),semplid );
            }).collect(Collectors.toList());
            return assessmentmarkslist;
    }

    @GetMapping("/assessmentAssignmentType/{batchid}")
    public List<CommonModelReport> assessmentandAssignmenttype(@PathVariable long batchid)
    {
        /*Query qry=em.createNativeQuery("select a.term,a.lam_type,a.marks_out_of,a.lam_weight,a.descr_short,a.sequence_no,concat(a.descr_short,' (',a.marks_out_of,')'),b.batch_type\n" +
                "from gradebook_description a left join batch_details b on a.batch_id=b.batch_id\n" +
                "where a.descr_short<>'' and a.batch_id=:BatchId\n" +
                "order by cast(substr(descr_short,2,length(descr_short)) as signed integer)").setParameter("BatchId",batchid);*/
    	Query qry = em.createNativeQuery(
				"select a.term,a.lam_type,a.marks_out_of,a.lam_weight,a.descr_short,c.sequence_no,concat(a.descr_short,' (',a.marks_out_of,')'),b.batch_type\r\n"
				+ "from gradebook_description a left join batch_details b on a.batch_id=b.batch_id\r\n"
				+ "left join grade_book_staging c on c.class_number=b.batch_code and c.descr_short=a.descr_short\r\n"
				+ "where a.descr_short<>'' and a.batch_id=:BatchId\r\n"
				+ "order by cast(substr(a.descr_short,2,length(a.descr_short)) as signed integer)")
				.setParameter("BatchId", batchid);
        List<Object[]> data=qry.getResultList();
        List<CommonModelReport> typelist=data.stream().map(x->{
            return new CommonModelReport(x[0].toString(), x[1].toString(), x[2].toString()
                    , x[3].toString(), x[4].toString(), x[5].toString(), x[6].toString(),x[7].toString());
        }).collect(Collectors.toList());
        return typelist;
    }

    @PostMapping("addGradebookPSMarksforPS")
    public ResponceMessage saveGradebookPSMarks(@RequestBody List<GradebookPSMarksModel> gradebookPSMarksModelList)
    {
        List<GradebookPSMarks> gradebookPSMarksList=gradebookPSMarksModelList.stream().map(x->{
            return new GradebookPSMarks(x.getGradebookpsmarksId(), x.getEmplid(),x.getClass_nbr(), x.getStrm(),x.getLam_type(), x.getDescrshort(), x.getSequence_no(), x.getMarks_out_of(), x.getStudent_grade(), x.getInstructor_id());
                    }).collect(Collectors.toList());
        try{
            gradebookPSMarksrepo.saveAll(gradebookPSMarksList)   ;
            return new ResponceMessage("Success");
        }
        catch(Exception e)
        {
            return new ResponceMessage("Error");
        }

    }

    @GetMapping("/getAllGradebookPSMarksList")
    public List<GradebookPSMarksModel> getGradebookPSMarksModelList()
    {
        return gradebookPSMarksrepo.getAllGradebookPSMarks().stream().map(x->{
            return new GradebookPSMarksModel(x.getGradebookPSMarksId(),x.getEmplId(),x.getClassNumber(),x.getStrm(),x.getLamtype(),x.getDescrShort(),x.getSequenceNo(),x.getMarksOutOf(),x.getStudenrGrade(),x.getInstructorId());
        }).collect(Collectors.toList());
    }

    /***********For Sync*************/
    @PostMapping("/addGradebookData")
    @Transactional
    public List<GradeBookStagingModel> addStagingtabledata(@RequestBody List<GradeBookStagingModel> gradebookdata)
    {

        List<GradeBookStaging> gradebook=gradebookdata.stream().map(x->{
            return new GradeBookStaging(0, x.getBatchsession(), x.getBatchseq(),x.getClassnumber(),x.getSsrcomponent()
                    , x.getCatalognbr(), x.getSequenceno(),x.getLamtype(),x.getDescr(), x.getDescrshort(), x.getMarksouof(), x.getLamweight(),x.getTerm());
        }).collect(Collectors.toList());

        try {
           // em.createNativeQuery("truncate grade_book_staging").executeUpdate();
            gradebookrepo.saveAll(gradebook);
            List<GradebookDescription> gradebookdescr=gradebookdata.stream().map(x->{
                BatchDetails batch=batchrepo.getBatchByBatchCode(x.getClassnumber());
                return new GradebookDescription(0,batch, x.getDescr(), x.getDescrshort(), x.getLamtype(), x.getLamweight(), Double.parseDouble(x.getMarksouof()), x.getBatchseq(), x.getTerm());
          }).collect(Collectors.toList());
            gradebookDescrRepo.saveAll(gradebookdescr);
            return getGradebookResponse(gradebook);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    @GetMapping("/getEmplidandMarksList/{batchid}")
    public HashMap<String,HashMap<String,String>> getStudentMarkslist(@PathVariable long batchid)
    {
        Query qry=em.createNativeQuery("select emplid,type,coalesce(round(sum((marks_obt/max_marks)*coalesce(marks_out_of,0))/count(assessment_id),2),0) marks_obt_avg  \n" +
                "from  \n" +
                "(select a.assessment_id, case a.descr1 when 'IT1' then 'UT1' when 'IT2' then 'UT2' when 'ES' then 'End Sem' when 'ESL' then 'End Sem L'\n" +
                "when 'CP' then 'CP1' when 'L1' then 'LT1' when 'L2' then 'LT2' else a.descr1 end type\n" +
                "  ,c.batch_id,e.emplid,sum(e.marksobtained) marks_obt  \n" +
                ",(select sum(marks) from assessment_question where assessment_id=a.assessment_id) max_marks  \n" +
                "  ,f.marks_out_of,f.lam_weight,f.lam_type  \n" +
                "from assessment_details a left join scheduled_assessment b on b.assessment_id=a.assessment_id  \n" +
                "left join scheduled_assessment_mapping c on c.scheduled_id=b.scheduled_id  \n" +
                "left join assessment_responce e on e.assessment_id=a.assessment_id  \n" +
                "left join gradebook_description f on f.batch_id=c.batch_id and  \n" +
                "f.descr_short=(case a.descr1 when 'IT1' then 'UT1' when 'IT2' then 'UT2' when 'ES' then 'End Sem' when 'ESL' then 'End Sem L'\n" +
                "when 'CP' then 'CP1' when 'L1' then 'LT1' when 'L2' then 'LT2' else a.descr1 end)\n" +
                "where b.is_active='Y' and c.batch_id=:batchId and e.emplid is not null\n" +
                "group by a.assessment_id,c.batch_id,e.emplid,f.marks_out_of,f.lam_weight,f.lam_type\n" +
                "union  \n" +
                "select a.assignmentid,case a.assignment_type when 'IT1' then 'UT1' when 'IT2' then 'UT2' when 'ES' then 'End Sem' when 'ESL' then 'End Sem L'\n" +
                "when 'CP' then 'CP1' when 'L1' then 'LT1' when 'L2' then 'LT2' else a.assignment_type end type\n" +
                ",b.batch_id,e.emplid,sum(d.marks_obtained) marks_obt  \n" +
                ",(select sum(marks) from assignment_questions where assignmentid=a.assignmentid) max_marks  \n" +
                "  ,coalesce(f.marks_out_of,0),f.lam_weight,f.lam_type  \n" +
                "from assignment_details a left join assignment_plan_mapping b on a.assignmentid=b.assignmentid  \n" +
                "left join assignment_response d on d.assignmentid=a.assignmentid  \n" +
                "left join student_details e on e.student_id=d.student_id  \n" +
                "left join gradebook_description f on f.batch_id=b.batch_id and  \n" +
                "f.descr_short=(case a.assignment_type when 'IT1' then 'UT1' when 'IT2' then 'UT2' when 'ES' then 'End Sem' when 'ESL' then 'End Sem L'\n" +
                "when 'CP' then 'CP1' when 'L1' then 'LT1' when 'L2' then 'LT2' else a.assignment_type end)\n" +
                "where a.is_published='Y' and b.batch_id=:batchId and e.emplid is not null\n" +
                "group by a.assignmentid,a.assignment_type,b.batch_id,e.emplid,coalesce(f.marks_out_of,0),f.lam_weight,f.lam_type) a  \n" +
                "group by emplid,type").setParameter("batchId",batchid);

        List<Object[]> data=qry.getResultList();

        HashMap<String,HashMap<String,String>> studentmaplist=new HashMap<>();
        List<CommonModelReport> list=data.stream().map(x->{
            return new CommonModelReport(x[0].toString(),x[1].toString(),x[2].toString());
        }).collect(Collectors.toList());

        List<String> emplid=new ArrayList();
        List<DoubleResponseModel> marktest=new ArrayList<>();
        for(CommonModelReport ob:list)
        {
            if(!(emplid.contains(ob.getDescr1())))
            {
                emplid.add(ob.getDescr1());
            }
        }

        for(String ob:emplid)
        {
            HashMap<String,String> maplist=new HashMap<>();

            for(CommonModelReport obj:list)
            {
                if(obj.getDescr1().equalsIgnoreCase(ob) && (!(maplist.containsKey(obj.getDescr2()))) ) {
                    maplist.put(obj.getDescr2(), obj.getDescr3());

                }
            }
           studentmaplist.put(ob,maplist);

        }
        return studentmaplist;
    }

    public List<GradeBookStagingModel> getGradebookResponse(List<GradeBookStaging> gradebook)
    {
        return gradebook.stream().map(x->{
                    return new GradeBookStagingModel(String.valueOf(x.getGradeBookStagingId()), x.getBatchSession(), x.getBatchSeq(), x.getClassNumber(), x.getSsrComponent(), x.getCatalogNbr(), x.getSequenceNo(), x.getLamType(), x.getDescr(), x.getDescrShort(), x.getMarksOuOf(), x.getLamWeight(),x.getTerm()) ;
        }).collect(Collectors.toList());

    }

    @PostMapping("/addgrdebookmarks")
    public ResponceMessage saveGradebookMarks(@RequestBody  List<GradebookResponseModel> gblist)
    {
        String term = (String) em.createNativeQuery("select term from grade_book_staging limit 1").getSingleResult();

        List<GradeBookMarks> studentlist=gblist.stream().map(x->{
            GradeBookMarks gradebook=null;

            CourseDetails course=crscrep.findCourseDetailsByCourseId(Long.parseLong(x.getCourseid()));
            BatchDetails batch=batchrepo.findBatchByBatchId(Long.parseLong(x.getBatchid()));
            StudentDetails student=studrepo.findStudentbyEmplid(x.getEmplid());
            if(repo.checkExisting(Long.parseLong(x.getBatchid()), Long.parseLong(x.getCourseid()), x.getPslamtype(),x.getEmplid())==0)
                gradebook= new GradeBookMarks(0, batch, course, x.getPslamtype(), x.getAssid(),x.getAsstype(), x.getMarks(), student,term) ;

            else
            {
                GradeBookMarks gradebk=repo.getGradebookMarks(Long.parseLong(x.getBatchid()), Long.parseLong(x.getCourseid()), x.getPslamtype(),x.getEmplid());
                gradebook= new GradeBookMarks(gradebk.getGradbookid(), batch, course, x.getPslamtype(), x.getAssid(),x.getAsstype(), x.getMarks(), student,term) ;
            }

            return gradebook;
        }).collect(Collectors.toList());
        repo.saveAll(studentlist);
        return new ResponceMessage("Success");
    }

    @GetMapping("/getGradebookList/{batchid}/{courseid}")
    public List<GradebookResponseModel> getGradebook(@PathVariable String batchid,@PathVariable String courseid)
    {
        List<GradeBookMarks> gradebook=repo.getGradebookMarksbyBatchid(Long.parseLong(batchid), Long.parseLong(courseid));
        return gradebook.stream().map(x->{
            String freezestatus=freezestatusrepo.findFreezeStatus(Long.parseLong(batchid),(Long.parseLong(courseid)));
            return new GradebookResponseModel(x.getGradbookid(), String.valueOf(x.getBatchDetails().getBatchId()), String.valueOf(x.getCourseDetails().getCourseId()), x.getPsLamType(), x.getAssid(), x.getAssType(), x.getMarks(), x.getStudentDetails().getEmplid(),freezestatus);
            }).collect(Collectors.toList());
    }

    @PostMapping("/freezeGradeBookStatus")
    @Transactional
    public ResponceMessage setGradebookFreezeStatus(@RequestBody DoubleResponseModel batchcourse) {
        String emp=batchcourse.getMessage();
        String[] batchEmpl=emp.split(":");
        long batchid=Long.parseLong(batchEmpl[0]);
        long courseId=Long.parseLong(batchcourse.getStatus());

        BatchDetails batch = batchrepo.findBatchByBatchId(batchid);
        CourseDetails course = (CourseDetails) em.createNamedQuery("CourseDetails.findCourseDetailsByCourseId").setParameter("CourseId", courseId).getSingleResult();
        String term = (String) em.createNativeQuery("select term from grade_book_staging limit 1").getSingleResult();
        try {

            long grbkstatus=freezestatusrepo.checkGradebookFreezeStatus(batchid,courseId,term);

            GradebookFreezeStatus grbkfreezstatus=null;
            if(grbkstatus==0) {
                grbkfreezstatus = new GradebookFreezeStatus(0, batch, course, "Y", term);
                grbkfreezstatus.setCreatedBy(batchEmpl[1]);
                grbkfreezstatus.setModifiedBy(batchEmpl[1]);
            }
            else
            {
                GradebookFreezeStatus grbkfreezestatus =freezestatusrepo.getGradebookFreezeStatus(batchid,courseId,term);
                grbkfreezstatus = new GradebookFreezeStatus(grbkfreezestatus.getGradebookFreezeStatusId(), batch, course, "Y", term);
                grbkfreezstatus.setModifiedBy(batchEmpl[1]);
                grbkfreezstatus.setCreatedBy(grbkfreezestatus.getCreatedBy());
                grbkfreezstatus.setLastUpdatedDate(Generation.getCurrentDate());
                long count=em.createNativeQuery("delete from grade_book_lamtype_mapping where batch_id=:batchid and course_id=:courseid and term=:trm")
                          .setParameter("batchid",batchid).setParameter("courseid",courseId).setParameter("trm",term).executeUpdate();
            }

            freezestatusrepo.save(grbkfreezstatus);
            ResponceMessage responsemsg=saveGradebookFreezeMapping(courseId,batchid);

            if(responsemsg.getMessage().equalsIgnoreCase("Success"))
                return new ResponceMessage("Success");
            else
                return new ResponceMessage("Error mapping");
        } catch (Exception e) {
            return new ResponceMessage("Error"+e.getMessage());
        }
    }

       public ResponceMessage saveGradebookFreezeMapping( long courseId, long batchid)
       {
            BatchDetails batch=batchrepo.findBatchByBatchId(batchid);
            CourseDetails course=(CourseDetails)em.createNamedQuery("CourseDetails.findCourseDetailsByCourseId").setParameter("CourseId",courseId).getSingleResult();
            List<Object[]>  lamtypelist=em.createNativeQuery("select distinct a.ps_lam_type,case a.ass_type when 'Assessment' then b.descr1 when 'Assignment' then c.assignment_type else a.ass_type end lms_lam_type " +
                    "From grade_book_marks a left join assessment_details b on a.assid=b.assessment_id " +
                    "left join assignment_details c on a.assid=c.assignmentid where a.course_id=:CourseId and a.batch_id=:BatchId")
                    .setParameter("CourseId",courseId).setParameter("BatchId",batchid).getResultList();
            String term=(String)em.createNativeQuery("select term from grade_book_staging limit 1").getSingleResult();

            List<GradeBookLamtypeMapping> lamtypemappinglist=lamtypelist.stream().map(x->{
                return new GradeBookLamtypeMapping(0,batch,course,x[0].toString(),x[1].toString(),term);
                    }).collect(Collectors.toList());

            try {
                lamtyperepo.saveAll(lamtypemappinglist);
                return new ResponceMessage("Success");
            }
            catch(Exception e)
            {
                return new ResponceMessage("Error");
            }
       }


       @GetMapping("/deleteGradeBookByBatchCode/{batchCode}")
       public ResponceMessage deleteGradeBookByBatchId(@PathVariable String batchCode)
       {

           BatchDetails batch=batchrepo.getBatchByBatchCode(batchCode);
           long batchId=batch.getBatchId();
           String batchcode=batch.getBatchCode();

           long repoCount=repo.getCountGradeBookMarksByBatchId(batchId);
           long freezestatusrepoCount=freezestatusrepo.getCountGradebookByBatchId(batchId);
           long lamtypeCount=lamtyperepo.getCountGradeBookLamtypeMappingByBatchId(batchId);
           long gradebookDescrCount=gradebookDescrRepo.getCountGradebookDescriptionByBatchId(batchId);
           long gradebookstagingCount=gradebookrepo.getCountGradeBookStagingByClassNumber(batchcode);
           long gradebookPSMarksCount=gradebookPSMarksrepo.getCountGradebookPSMarksByClassNumber(batchcode);

           int repodeleteCount=repo.deleteGradeBookMarksByBatchId(batchId);
           int freezestatusdeleteCount=freezestatusrepo.deleteGradebookByBatchId(batchId);
           int lamtypedeleteCount=lamtyperepo.deleteGradeBookLamtypeMappingByBatchId(batchId);
           int gradebookDescrdeleteCount=gradebookDescrRepo.deleteGradebookDescriptionByBatchId(batchId);
           int gradebookstagingdeleteCount=gradebookrepo.deleteGradeBookStagingByClassNumber(batchcode);
           int gradebookPSMarksdeleteCount=gradebookPSMarksrepo.deleteGradebookPSMarksByClassNumber(batchcode);

           if(repodeleteCount==repoCount && freezestatusdeleteCount==freezestatusrepoCount && lamtypedeleteCount==lamtypeCount &&
                   gradebookDescrdeleteCount==gradebookDescrCount && gradebookstagingdeleteCount==gradebookstagingCount && gradebookPSMarksdeleteCount== gradebookPSMarksCount)
               return new ResponceMessage("Success");
           else
               return new ResponceMessage("Error");

       }

}
