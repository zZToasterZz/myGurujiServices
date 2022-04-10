package com.srdt.myguruji.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srdt.myguruji.model.ResponceMessage;
import com.srdt.myguruji.model.Translate;
import com.srdt.myguruji.model.Unit;
import com.srdt.myguruji.model.SubUnit;
import com.srdt.myguruji.repository.CourseRepository;
import com.srdt.myguruji.repository.UnitDetailsRepository;

@RestController
@RequestMapping("/api/unit")
public class UnitDetailsResource {

	@Autowired
	UnitDetailsRepository resp;
	@Autowired
	CourseRepository courserep;
	
	@PostMapping("/create")
	public ResponceMessage create(@RequestBody List<Unit> units)
	{		
		courserep.setAptflag(false);
		courserep.AddUnits(units);
		return new ResponceMessage("Units created");				
	}
	
	@GetMapping("/getunitdetails")
	public List<Unit> getUnits()
	{
		return resp.findAllUnitDetails()
		    .stream()
		    .map(x->
		    {
		    	List<SubUnit> subUnits = x.getSubUnitDetails().stream()
		    			                 .map(y->{
		    			                	 return new SubUnit(y.getSubUnitTitle(), y.getSubUnitDescr(), y.getCreatedBy(), y.getSubUnitId(), x.getUnitId());
		    			                 })
		    			                 .collect(Collectors.toList());
		    	
		    	return new Unit(x.getUnitTitle(), x.getUnitDescr(), x.getCreatedBy(), x.getUnitId(), x.getCoursePlan().getCoursePlanId(),subUnits);
		    })
		    .collect(Collectors.toList());
	}
	
	@GetMapping("/getunitdetails/{CoursePlanId}")
	public List<Unit> getUnits(@PathVariable("CoursePlanId") long CoursePlanId)
	{
		return resp.findUnitDetailsByCoursePlanId(CoursePlanId)
			    .stream()
			    .map(x->
			    {
			    	List<SubUnit> subUnits = x.getSubUnitDetails().stream()
			    			                 .map(y->{
			    			                	 return new SubUnit(y.getSubUnitTitle(), y.getSubUnitDescr(), y.getCreatedBy(), y.getSubUnitId(), x.getUnitId());
			    			                 })
			    			                 .collect(Collectors.toList());
			    	
			    	return new Unit(x.getUnitTitle(), x.getUnitDescr(), x.getCreatedBy(), x.getUnitId(), x.getCoursePlan().getCoursePlanId(),subUnits);
			    })
			    .collect(Collectors.toList());
	}
	
	@PostMapping("/update")
	public ResponceMessage update(@RequestBody List<Unit> units)
	{
		courserep.setAptflag(true);
		courserep.AddUnits(units);
		return new ResponceMessage("Unit details updated");
	}
	
	@PostMapping("/remove")
	public ResponceMessage remove(@RequestBody List<Long> unitid)
	{
		resp.deleteUnitDetailsByUnitId(unitid);
		return new ResponceMessage("Selected units removed");
	}
	
	@GetMapping("/getunitsbyplanid/{planid}")
	public List<Translate> getUnitByCoursePlan(@PathVariable("planid") long planid)
	{
		return resp.findUnitsByCoursePlanId(planid);
	}
}
