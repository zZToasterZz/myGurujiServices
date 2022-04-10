package com.srdt.myguruji.resource;

import com.netflix.discovery.converters.Auto;
import com.srdt.myguruji.enitity.FeedbackGradingParentMapping;
import com.srdt.myguruji.enitity.FeedbackParameter;
import com.srdt.myguruji.enitity.GradingScale;
import com.srdt.myguruji.model.*;
import com.srdt.myguruji.repository.FeedbackGradingParentMappingRepo;
import com.srdt.myguruji.repository.FeedbackParameterRepo;
import com.srdt.myguruji.repository.GradingScaleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackResource {

    @Autowired
    GradingScaleRepo feedbckrepo;

    @Autowired
    FeedbackGradingParentMappingRepo fdbclGradingPrntRepo;

    @Autowired
    FeedbackParameterRepo paramRepo;

    @PostMapping("/saveGradingScale")
    public ResponceMessage addGradingScale(@RequestBody List<GradingScaleModel> gradingscalelist)
    {
        List<GradingScale> gradinglist=gradingscalelist.stream().map(x-> {
            GradingScale gradingscale=new GradingScale(x.getGradingscaleid(),x.getGradingname(),x.getGradingpoint(),x.getGradingpointvalue());
            gradingscale.setCreatedBy(x.getCreatedby());
            gradingscale.setModifiedBy(x.getCreatedby());
            return gradingscale;
        }).collect(Collectors.toList());
        try
        {
            feedbckrepo.saveAll(gradinglist);
            return new ResponceMessage("Success");
        }
        catch(Exception e)
        {
            if(e.getMessage().contains("feedback_grading_scale.UKhxnqhurfgxuy97aokhl129px5"))
            return new ResponceMessage("Duplicate Entry");
            else
                return new ResponceMessage("Error");
        }

    }

    @GetMapping("/getFeedbackParent")
    public List<FeedbackGradeParent> getFeedbackParent()
    {
        return fdbclGradingPrntRepo.getFeedbackParent().stream().map(x->{
            return new FeedbackGradeParent(x.getFeedbackGradingParentMappingId(), x.getFeedbackTypeName(), x.getFeedbackParentPath(), x.getFeedbackParent(), x.getCreatedBy());
        }).collect(Collectors.toList());
    }


    @GetMapping("getGradingScalename")
    public List<ResponceMessage> getGradingbScaleName()
    {
        return feedbckrepo.getDistinctScaleName().stream().map(x->{return new ResponceMessage(x);}).collect(Collectors.toList());
    }
    @GetMapping("getGradingScaleByGradingName/{gradingname}")
    public List<GradingScaleModel> getGradingScaleByName(@PathVariable String gradingname)
    {
        return feedbckrepo.getDistinctScaleByName(gradingname).stream().map(x->{
            return new GradingScaleModel(x.getGradingScaleId(), x.getGradingName(), x.getGradingPoint(), x.getGradingPointValues(), x.getCreatedBy());
        }).collect(Collectors.toList());
    }

    @GetMapping("/getFeedbackGradingScaleAllVaues")
    public List<FeedbackGradingValue> getFeedbackGradingScaleValue()
    {
        List<String> gradingnamelist= feedbckrepo.getDistinctScaleName();
        List<FeedbackGradingValue> grlist=gradingnamelist.stream().map(x->{
            List<GradingScale> grscale=feedbckrepo.getDistinctScaleByName(x);
            List<String> fbgradingvalue=grscale.stream().map(y->{return y.getGradingPointValues();}).collect(Collectors.toList());
            try {
                return new FeedbackGradingValue(x, fbgradingvalue.get(0), fbgradingvalue.get(1), fbgradingvalue.get(2), fbgradingvalue.get(3), fbgradingvalue.get(4));
            }
            catch(Exception e)
            {
                return new FeedbackGradingValue(x, "NA", "NA","NA","NA","NA");
            }
        }).collect(Collectors.toList());
        return grlist;
    }

    @PostMapping("saveFeedbackGradingParent")
    public ResponceMessage addGradingParent(@RequestBody FeedbackGradeParent gradingparent)
    {
        FeedbackGradingParentMapping gradeparent= new FeedbackGradingParentMapping(gradingparent.getFeedbackgradeparentid(),gradingparent.getFeedbacktypename(),gradingparent.getFeedbackparentpath(),gradingparent.getFeedbackparent(), gradingparent.getCreatedby());
        if(gradingparent.getFeedbacktypename()==null ||gradingparent.getFeedbacktypename().trim().length()==0)
            return new ResponceMessage("Feedback_Name_Error");
        try {
            fdbclGradingPrntRepo.save(gradeparent);
            String id = String.valueOf(gradeparent.getFeedbackGradingParentMappingId());
            String parentpath = "";
            int count = 0;
            if (gradingparent.getFeedbackparent().equalsIgnoreCase("0")) {
                parentpath = id;
                count = fdbclGradingPrntRepo.updateParentPath(id, gradeparent.getFeedbackGradingParentMappingId());
            } else {
                parentpath = fdbclGradingPrntRepo.getParentPath(Long.parseLong(gradingparent.getFeedbackparent()));
                count = fdbclGradingPrntRepo.updateParentPath(String.join(".", Arrays.asList(parentpath, id)), gradeparent.getFeedbackGradingParentMappingId());

            }
            if (count > 0)
                return new ResponceMessage("Success:" + String.valueOf(gradeparent.getFeedbackGradingParentMappingId()));

        }
        catch(Exception e)
        {
            if(e.getMessage().contains("feedback_grading_scale.UKlrf1ljkxt7lrvwjc688fn4w65"))
                return new ResponceMessage("Duplicate Entry");
            else
                return new ResponceMessage("Error");
        }
            return new ResponceMessage("Error");
    }

    @GetMapping("/getFeedbackType")
    public List<FeedbackGradeParent> getFeetBackTypeList()
    {
        return fdbclGradingPrntRepo.getFeedbackType().stream().map(x->{
            return new FeedbackGradeParent(x.getFeedbackGradingParentMappingId(), x.getFeedbackTypeName(), x.getFeedbackParentPath(), x.getFeedbackParent(), x.getCreatedBy());
        }).collect(Collectors.toList());
    }

    @GetMapping("/getFeedbackTypeById/{id}")
    public FeedbackGradeParent getFeedbackGradeParentById(@PathVariable long id)
    {
        FeedbackGradingParentMapping x= fdbclGradingPrntRepo.getFeedbackParentById(id);
        return new FeedbackGradeParent(x.getFeedbackGradingParentMappingId(), x.getFeedbackTypeName(), x.getFeedbackParentPath(), x.getFeedbackParent(), x.getCreatedBy());
    }

    @PostMapping("/getFeedbackParentListById")
    public List<FeedbackGradeParent> getFeedbackParentList(@RequestBody List<Long> parentids)
    {
        return fdbclGradingPrntRepo.getFeedbackParentList(parentids).stream().map(x->{
            return new FeedbackGradeParent(x.getFeedbackGradingParentMappingId(), x.getFeedbackTypeName(), x.getFeedbackParentPath(), x.getFeedbackParent(), x.getCreatedBy());
        }).collect(Collectors.toList());
    }

    @GetMapping("/getFeedBackTypeParentByParentId/{parentId}")
    public List<FeedbackGradeParent> getFeedBackTypeParentByParentId(@PathVariable String parentId)
    {
        return fdbclGradingPrntRepo.getFeedbackTypeParent(parentId).stream().map(x->{
            return new FeedbackGradeParent(x.getFeedbackGradingParentMappingId(), x.getFeedbackTypeName(), x.getFeedbackParentPath(), x.getFeedbackParent(), x.getCreatedBy());
        }).collect(Collectors.toList());
    }

    @PostMapping("saveFeedbackParameter")
    public ResponceMessage addFeedbackParameter(@RequestBody List<FeedbackParameterModel> fbparam)
    {
        List<FeedbackParameter>  fbparameter=fbparam.stream().map(x->{
        FeedbackGradingParentMapping parentmap=fdbclGradingPrntRepo.getFeedbackParentById(x.getFeedbackparentid());
            return new FeedbackParameter(x.getFeedbackparameterid(),parentmap,x.getFeedbackchildid(), x.getFeedbackquestion(),x.getCreatedby()) ;
    }).collect(Collectors.toList());

        try{
            paramRepo.saveAll(fbparameter);
            return new ResponceMessage("Success");
        }
        catch(Exception e)
        {
            return new ResponceMessage("Error"+e.getMessage());
        }
    }

    @GetMapping("/getFeedbackParameterById/{fbparamid}")
    public FeedbackParameterModel getFeedbackParameterByParameterId(@PathVariable long fbparamid)
    {
        FeedbackParameter fb=paramRepo.getFeedbackParameterById(fbparamid);
        FeedbackParameterModel fbparam=new FeedbackParameterModel(fb.getFeedbackParameterId(), fb.getFeedbackParentId().getFeedbackGradingParentMappingId(), fb.getFeedbackChildId(), fb.getFeedbackQuestion(), fb.getCreatedBy());
        return fbparam;
    }

    @GetMapping("/getFeedbackParameterByParentId/{fbparamid}")
    public List<FeedbackParameterModel> getFeedbackParameterByParameterParentId(@PathVariable long fbparamid)
    {
        List<FeedbackParameter> fbp=paramRepo.getFeedbackParameterByParentId(fbparamid);
        return fbp.stream().map(fb-> {
            FeedbackGradeParent fbchld;
            try{
                FeedbackGradingParentMapping fbpchild=fdbclGradingPrntRepo.getFeedbackParentById(Long.parseLong(fb.getFeedbackChildId()));
                fbchld=new FeedbackGradeParent(fbpchild.getFeedbackGradingParentMappingId(),fbpchild.getFeedbackTypeName() ,fbpchild.getFeedbackParentPath(), fbpchild.getFeedbackParent(),fbpchild.getCreatedBy());
                }
            catch(Exception e)
            {
                fbchld=null;
            }
            FeedbackParameterModel feedbackParam=new FeedbackParameterModel(fb.getFeedbackParameterId(), fb.getFeedbackParentId().getFeedbackGradingParentMappingId(), fb.getFeedbackChildId(), fb.getFeedbackQuestion(), fb.getCreatedBy());
            feedbackParam.setFeedbackParameterchild(fbchld);
            return feedbackParam;
        }).collect(Collectors.toList());
    }

    @GetMapping("getFeedbackChildByParentId/{parentId}")
    public List<FeedbackGradeParent> getFeedbackTypeChildByParentId(@PathVariable String parentId)
    {
        return fdbclGradingPrntRepo.getFeedbackTypeChildByParentId(parentId).stream().map(x->{
            return new FeedbackGradeParent(x.getFeedbackGradingParentMappingId(), x.getFeedbackTypeName(), x.getFeedbackParentPath(), x.getFeedbackParent(), x.getCreatedBy());
        }).collect(Collectors.toList());
    }
    /*
    public List<FeedbackGradeParent> getFeedBackTypeParentByParentId(@PathVariable String parentId)
    {
        return fdbclGradingPrntRepo.getFeedbackTypeParent(parentId).stream().map(x->{
            return new FeedbackGradeParent(x.getFeedbackGradingParentMappingId(), x.getFeedbackTypeName(), x.getFeedbackParentPath(), x.getFeedbackParent(), x.getCreatedBy());
        }).collect(Collectors.toList());
    }*/


}
