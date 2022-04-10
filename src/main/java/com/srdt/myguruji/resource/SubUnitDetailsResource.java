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
import com.srdt.myguruji.model.SubUnit;
import com.srdt.myguruji.model.Translate;
import com.srdt.myguruji.repository.CourseRepository;
import com.srdt.myguruji.repository.SubUnitDetailsRepository;
import com.srdt.myguruji.enitity.CourseObjectiveMaping;
import com.srdt.myguruji.model.ObjectiveMapping;

@RestController
@RequestMapping("/api/subunit")
public class SubUnitDetailsResource {

	@Autowired
	SubUnitDetailsRepository resp;
	
	@Autowired
	CourseRepository courserep;
	
	@PostMapping("/create")
	public ResponceMessage create(@RequestBody List<SubUnit> subUnits)
	{
		courserep.setAptflag(false);
		courserep.AddSubUnits(subUnits);
		return new ResponceMessage("Sub units created");
	}
	
	@GetMapping("/getsubunit")
	public List<SubUnit> getSubUnit()
	{
		return resp.findAllSubUnitDetails()
				   .stream()
				   .map(x->
				   {
					   return new SubUnit(x.getSubUnitTitle(), x.getSubUnitDescr(), x.getCreatedBy(), x.getSubUnitId(), x.getUnitDetails().getUnitId());
				   })
				   .collect(Collectors.toList());				   
	}
	
	@GetMapping("/getsubunitdetailsbyunitid/{unitid}")
	public List<SubUnit> getSubUnit(@PathVariable("unitid") long unitid)
	{
		return resp.findSubUnitByUnitId(unitid)
				   .stream()
				   .map(x->
				   {
					   List<ObjectiveMapping> objectiveMappings = null;
					   List<CourseObjectiveMaping> objectiveMapings = x.getUnitDetails().getObjectiveMapings();
					   if(objectiveMapings != null && objectiveMapings.size() > 0)
					   {
						   objectiveMappings = objectiveMapings.stream().map(y->{
                               return new ObjectiveMapping(y.getObjectiveMapingId(), y.getObjectiveDetails().getObjectiveId(), y.getObjectiveDetails().getDescr(), y.getObjectiveDetails().getObjectiveCode());
                            }).collect(Collectors.toList());
					   }
					    
					   SubUnit su = new SubUnit(x.getSubUnitTitle(), x.getSubUnitDescr(), x.getCreatedBy(), x.getSubUnitId(), x.getUnitDetails().getUnitId());
					   su.setObjectivemap(objectiveMappings);
					   return su;
				   })
				   .collect(Collectors.toList());				   
	}
	
	@PostMapping("/update")
	public ResponceMessage update(@RequestBody List<SubUnit> subUnits)
	{
		courserep.setAptflag(true);
		courserep.AddSubUnits(subUnits);
		return new ResponceMessage("Subunit details updated");
	}
	
	@PostMapping("/remove")
	public ResponceMessage remove(@RequestBody List<Long> subunitid)
	{
		resp.deleteAllBySubUnitId(subunitid);
		return new ResponceMessage("Selected subunits removed");		
	}
	
	@GetMapping("/getsubunitbyunitid/{unitid}")
	public List<Translate> getSubunitsByUnitId(@PathVariable("unitid") long unitid)
	{
		return resp.findAllSubUnitDetailsByUnitId(unitid)
				   .stream()
				   .map(x->{
					   return new Translate(x.getSubUnitId(), x.getSubUnitTitle());
				   })
				   .collect(Collectors.toList());
	}
}
