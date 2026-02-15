package com.example.demo.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.AppConstants;
import com.example.demo.constants.LogConstants;
import com.example.demo.dto.PlanDto;
import com.example.demo.dto.PlanResponceDto;
import com.example.demo.service.PlanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/plan")
public class PlanRestController {
	private Logger logger=LoggerFactory.getLogger(PlanRestController.class);
	
	@Autowired
	private PlanService planService;
	
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
   @PostMapping("/savePlan")
	public ResponseEntity<String> savePlan(@Valid @RequestBody PlanDto planDto) {
	logger.info(LogConstants.SAVE_PLAN_REQUEST, planDto);
    planService.savePlan(planDto); // AlreadyExistsException throw chestundi if duplicate
    logger.info(LogConstants.PLAN_SAVED_SUCCESS, planDto.getPlanName());
    return new ResponseEntity<>(AppConstants.PLAN_SAVED, HttpStatus.CREATED);
		
		}
      
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
     @GetMapping("/plans")
     public ResponseEntity<List<PlanResponceDto>> getPlans(){
    	 logger.info(LogConstants.FETCH_ALL_PLANS_REQUEST);
    	 List<PlanResponceDto> plans=planService.getPlans();
    	 logger.info(LogConstants.FETCH_ALL_PLANS_SUCCESS);
		 return new ResponseEntity<>(plans,HttpStatus.OK);
    	 
     }

     @PreAuthorize("hasAuthority('ROLE_ADMIN')")
     @PutMapping("/{planId}")
     public ResponseEntity<PlanResponceDto> updatePlan(@PathVariable Integer planId) {

         logger.info("Update plan request for planId {}", planId);

         PlanResponceDto responseDto = planService.updatePlan(planId);

         return new ResponseEntity<>(responseDto, HttpStatus.OK);
     }

     @PreAuthorize("hasAuthority('ROLE_ADMIN')")
     @PutMapping("/{planId}/{activeSw}")
     public ResponseEntity<String> updatePlanStatus (@PathVariable Integer planId,@PathVariable String activeSw){
    	 logger.info(LogConstants.UPDATE_PLAN_STATUS_REQUEST, planId, activeSw);

    	    boolean status = planService.updatePlan(planId, activeSw);

    	    if (status) {
    	        logger.info(LogConstants.UPDATE_PLAN_STATUS_CONTROLLER_SUCCESS, planId);
    	        return new ResponseEntity<>(AppConstants.STATUS_ACTIVE, HttpStatus.OK);
    	    } else {
    	        logger.error(LogConstants.UPDATE_PLAN_STATUS_CONTROLLER_FAILED, planId);
    	        return new ResponseEntity<>(
    	                AppConstants.STATUS_INACTIVE,
    	                HttpStatus.INTERNAL_SERVER_ERROR);
    	    }
    	 
     }
        
  
      


     

     @GetMapping("/active")
     public List<String> getPlanNames() {
         return planService.getActivePlanNames();
     }

}
