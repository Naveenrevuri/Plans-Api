package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.PlanDto;
import com.example.demo.dto.PlanResponceDto;

public interface PlanService {
	
	public boolean savePlan (PlanDto planDto);
	
	public List<PlanResponceDto> getPlans();
	
	public PlanResponceDto getPlan(Integer planId);
	
	public boolean updatePlan(Integer planId,String status);
	
	public   PlanResponceDto updatePlanStatus(Integer planId);
    
	 List<String> getActivePlanNames();
}
