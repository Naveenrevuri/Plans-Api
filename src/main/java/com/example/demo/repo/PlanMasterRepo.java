package com.example.demo.repo;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.PlanMaster;

public interface PlanMasterRepo extends JpaRepository<PlanMaster, Integer>{
	

    Logger logger = LoggerFactory.getLogger(PlanMasterRepo.class);

    @Query("select distinct p.startDate from PlanMaster p")
    List<LocalDate> findDistinctStartDates();

    @Query("select distinct p.endDate from PlanMaster p")
    List<LocalDate> findDistinctEndDates();

    boolean existsByPlanNameIgnoreCase(String planName);
    @Query("select p.planName from PlanMaster p where p.activeSw = 'Y'")
    List<String> findActivePlanNames();

}
