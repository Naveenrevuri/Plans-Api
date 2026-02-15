package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.constants.AppConstants;
import com.example.demo.constants.LogConstants;
import com.example.demo.dto.PlanDto;
import com.example.demo.dto.PlanResponceDto;
import com.example.demo.entity.PlanMaster;
import com.example.demo.exceptions.AlreadyExistsException;
import com.example.demo.exceptions.InvalidException;
import com.example.demo.exceptions.NotFoundException;

import com.example.demo.repo.PlanMasterRepo;

import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanServiceImpl implements PlanService {
	
	
	private static final Logger logger = LoggerFactory.getLogger(PlanServiceImpl.class);

  
	
	@Autowired
	private PlanMasterRepo planMasterRepo;

   

	@Override
	// save plan
	@Transactional
	public boolean savePlan(PlanDto planDto) {
		 logger.info(LogConstants.SAVE_PLAN_REQUEST, planDto.getPlanName());

		    if (planMasterRepo.existsByPlanNameIgnoreCase(planDto.getPlanName())) {
		        logger.warn(LogConstants.PLAN_ALREADY_EXISTS_PRECHECK, planDto.getPlanName());
		        throw new AlreadyExistsException(
		                AppConstants.PLAN_ALREADY_EXISTS + planDto.getPlanName());
		    }

		    PlanMaster entity = new PlanMaster();
		    BeanUtils.copyProperties(planDto, entity);
		    entity.setActiveSw(AppConstants.STATUS_ACTIVE);

		    try {
		        PlanMaster savedPlan = planMasterRepo.save(entity);
		        logger.info(LogConstants.PLAN_SAVED_SUCCESS, savedPlan.getPlanName());
		        return true; //  method returns success
		    } catch (DataIntegrityViolationException ex) {
		        logger.warn(LogConstants.PLAN_ALREADY_EXISTS_DB, planDto.getPlanName());
		        throw new AlreadyExistsException(
		                AppConstants.PLAN_ALREADY_EXISTS + planDto.getPlanName(), ex);
		    }
	    }
	
	
	
	@Override
	// get all records
	@Transactional(readOnly = true)
	public List<PlanResponceDto> getPlans() {
		logger.info(LogConstants.FETCH_ALL_PLANS_REQUEST);

	    List<PlanMaster> entities = planMasterRepo.findAll();

	    if (entities.isEmpty()) {
	        logger.warn(LogConstants.FETCH_ALL_PLANS_NOT_FOUND);
	        throw new NotFoundException(AppConstants.PLAN_NOT_FOUND);
	    }

	    List<PlanResponceDto> dtos = new ArrayList<>();
	    for (PlanMaster entity : entities) {
	    	PlanResponceDto dto = new PlanResponceDto();
	        BeanUtils.copyProperties(entity, dto);
	        dtos.add(dto);
	    }

	    logger.info(LogConstants.FETCH_ALL_PLANS_SUCCESS, dtos.size());
	    return dtos;

	}
	
	
	
	@Override
	// get plan by id
	@Transactional(readOnly = true)
	public PlanResponceDto getPlan(Integer planId) {
		logger.info(LogConstants.FETCH_PLAN_BY_ID_REQUEST, planId);

	    PlanMaster entity = planMasterRepo.findById(planId)
	            .orElseThrow(() -> {
	                logger.warn(LogConstants.FETCH_PLAN_BY_ID_REQUEST, planId);
	                return new NotFoundException(
	                        AppConstants.PLAN_NOT_FOUND + planId);
	            });

	    PlanResponceDto dto=new PlanResponceDto();
	    BeanUtils.copyProperties(entity, dto);

	    logger.info(LogConstants.FETCH_PLAN_BY_ID_SUCCESS, dto.getPlanName());
	    return dto;
	}
	

	

	@Override
	@Transactional
	public boolean updatePlan(Integer planId, String status) {

	    logger.info(LogConstants.UPDATE_PLAN_STATUS_REQUEST, planId, status);

	    // 1️⃣ Normalize status (Y/N → ACTIVE/INACTIVE)
	    if ("Y".equalsIgnoreCase(status)) {
	        status = AppConstants.ACTIVE;
	    } else if ("N".equalsIgnoreCase(status)) {
	        status = AppConstants.INACTIVE;
	    }

	    // 2️⃣ Validate status
	    if (!AppConstants.ACTIVE.equalsIgnoreCase(status)
	            && !AppConstants.INACTIVE.equalsIgnoreCase(status)) {

	        logger.warn(LogConstants.UPDATE_PLAN_STATUS_INVALID, status, planId);
	        throw new InvalidException(
	                AppConstants.INVALID_PLAN_STATUS + status);
	    }

	    // 3️⃣ Fetch plan
	    PlanMaster entity = planMasterRepo.findById(planId)
	            .orElseThrow(() -> {
	                logger.warn(LogConstants.UPDATE_PLAN_ID_NOT_FOUND, planId);
	                return new NotFoundException(
	                        AppConstants.UPDATE_PLAN_ID_NOT_FOUND + planId);
	            });

	    // 4️⃣ DB expects Y/N
	    entity.setActiveSw(
	            AppConstants.ACTIVE.equalsIgnoreCase(status) ? "Y" : "N"
	    );

	    planMasterRepo.save(entity);

	    logger.info(LogConstants.UPDATE_PLAN_STATUS_SUCCESS, planId);
	    return true;
	}


	


	@Override
	public List<String> getActivePlanNames() {
		
		return planMasterRepo.findActivePlanNames();
	}



	@Override
	public PlanResponceDto updatePlan(Integer planId) {
		PlanMaster entity = planMasterRepo.findById(planId)
                .orElseThrow(() -> new NotFoundException(
                        "Plan not found with id : " + planId));
		
		 PlanResponceDto dto=new PlanResponceDto();
		BeanUtils.copyProperties(entity, dto);
		
		return dto;
	}


}

	


