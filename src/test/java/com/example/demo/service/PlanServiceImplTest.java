package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.constants.AppConstants;
import com.example.demo.dto.PlanDto;
import com.example.demo.dto.PlanResponceDto;
import com.example.demo.entity.PlanMaster;
import com.example.demo.exceptions.AlreadyExistsException;
import com.example.demo.exceptions.InvalidException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repo.PlanMasterRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PlanServiceImplTest {

    @InjectMocks
    private PlanServiceImpl planService;

    @Mock
    private PlanMasterRepo planMasterRepo;

    private PlanDto planDto;
    private PlanMaster planEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        planDto = new PlanDto();
        planDto.setPlanName("HealthPlan");
        planDto.setStartDate(LocalDate.now());
        planDto.setEndDate(LocalDate.now().plusDays(30));
        planDto.setActiveSw("Y");

        planEntity = new PlanMaster();
        planEntity.setPlanName("HealthPlan");
        planEntity.setActiveSw("Y");
    }

    // ================== SAVE PLAN ==================
    @Test
    void testSavePlan_Success() {
        when(planMasterRepo.existsByPlanNameIgnoreCase("HealthPlan")).thenReturn(false);
        when(planMasterRepo.save(any(PlanMaster.class))).thenReturn(planEntity);

        boolean result = planService.savePlan(planDto);
        assertTrue(result);
        verify(planMasterRepo, times(1)).save(any(PlanMaster.class));
    }

    @Test
    void testSavePlan_AlreadyExists_PreCheck() {
        when(planMasterRepo.existsByPlanNameIgnoreCase("HealthPlan")).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> planService.savePlan(planDto));
        verify(planMasterRepo, never()).save(any());
    }

    @Test
    void testSavePlan_AlreadyExists_DBException() {
        when(planMasterRepo.existsByPlanNameIgnoreCase("HealthPlan")).thenReturn(false);
        when(planMasterRepo.save(any(PlanMaster.class)))
                .thenThrow(new org.springframework.dao.DataIntegrityViolationException("Duplicate"));

        assertThrows(AlreadyExistsException.class, () -> planService.savePlan(planDto));
    }

    // ================== GET ALL PLANS ==================
    @Test
    void testGetPlans_Success() {
        List<PlanMaster> entities = new ArrayList<>();
        entities.add(planEntity);

        when(planMasterRepo.findAll()).thenReturn(entities);

        List<PlanResponceDto> dtos = planService.getPlans();
        assertEquals(1, dtos.size());
        assertEquals("HealthPlan", dtos.get(0).getPlanName());
    }

    @Test
    void testGetPlans_NotFound() {
        when(planMasterRepo.findAll()).thenReturn(new ArrayList<>());
        assertThrows(NotFoundException.class, () -> planService.getPlans());
    }

    // ================== GET PLAN BY ID ==================
    @Test
    void testGetPlan_Success() {
        when(planMasterRepo.findById(1)).thenReturn(Optional.of(planEntity));

        PlanResponceDto dto = planService.getPlan(1);
        assertEquals("HealthPlan", dto.getPlanName());
    }

    @Test
    void testGetPlan_NotFound() {
        when(planMasterRepo.findById(99)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> planService.getPlan(99));
    }

    // ================== UPDATE PLAN ==================
    @Test
    void testUpdatePlan_Success_Active() {
        when(planMasterRepo.findById(1)).thenReturn(Optional.of(planEntity));
        when(planMasterRepo.save(any(PlanMaster.class))).thenReturn(planEntity);

        boolean result = planService.updatePlan(1, "Y");
        assertTrue(result);
    }

    @Test
    void testUpdatePlan_Success_Inactive() {
        when(planMasterRepo.findById(1)).thenReturn(Optional.of(planEntity));
        when(planMasterRepo.save(any(PlanMaster.class))).thenReturn(planEntity);

        boolean result = planService.updatePlan(1, "N");
        assertTrue(result);
    }

    @Test
    void testUpdatePlan_InvalidStatus() {
        assertThrows(InvalidException.class, () -> planService.updatePlan(1, "X"));
    }

    @Test
    void testUpdatePlan_NotFound() {
        when(planMasterRepo.findById(99)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> planService.updatePlan(99, "Y"));
    }

    // ================== GET ACTIVE PLAN NAMES ==================
    @Test
    void testGetActivePlanNames() {
        List<String> names = List.of("HealthPlan");
        when(planMasterRepo.findActivePlanNames()).thenReturn(names);

        List<String> result = planService.getActivePlanNames();
        assertEquals(1, result.size());
        assertEquals("HealthPlan", result.get(0));
    }

    // ================== UPDATE PLAN STATUS ==================
    @Test
    void testUpdatePlanStatus_Success() {
        when(planMasterRepo.findById(1)).thenReturn(Optional.of(planEntity));

        PlanResponceDto dto = planService.updatePlanStatus(1);
        assertEquals("HealthPlan", dto.getPlanName());
    }

    @Test
    void testUpdatePlanStatus_NotFound() {
        when(planMasterRepo.findById(99)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> planService.updatePlanStatus(99));
    }
}



