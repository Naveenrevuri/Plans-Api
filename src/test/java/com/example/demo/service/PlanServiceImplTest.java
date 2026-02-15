package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.demo.constants.AppConstants;
import com.example.demo.dto.PlanDto;
import com.example.demo.dto.PlanResponceDto;
import com.example.demo.entity.PlanMaster;
import com.example.demo.exceptions.AlreadyExistsException;
import com.example.demo.exceptions.InvalidException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repo.PlanMasterRepo;

@ExtendWith(MockitoExtension.class)
class PlanServiceImplTest {

    @Mock
    private PlanMasterRepo planMasterRepo;

    @InjectMocks
    private PlanServiceImpl planService;

    // ---------------- savePlan ----------------

    @Test
    void testSavePlan_Success() {
        PlanDto dto = new PlanDto();
        dto.setPlanName("Health");

        when(planMasterRepo.existsByPlanNameIgnoreCase("Health"))
                .thenReturn(false);

        when(planMasterRepo.save(any(PlanMaster.class)))
                .thenReturn(new PlanMaster());

        boolean result = planService.savePlan(dto);

        assertTrue(result);
    }

    @Test
    void testSavePlan_AlreadyExists_Precheck() {
        PlanDto dto = new PlanDto();
        dto.setPlanName("Health");

        when(planMasterRepo.existsByPlanNameIgnoreCase("Health"))
                .thenReturn(true);

        AlreadyExistsException ex =
                assertThrows(AlreadyExistsException.class,
                        () -> planService.savePlan(dto));

        assertTrue(ex.getMessage().contains("Health"));
    }

    @Test
    void testSavePlan_AlreadyExists_DB() {
        PlanDto dto = new PlanDto();
        dto.setPlanName("Health");

        when(planMasterRepo.existsByPlanNameIgnoreCase("Health"))
                .thenReturn(false);

        when(planMasterRepo.save(any()))
                .thenThrow(new DataIntegrityViolationException("DB error"));

        assertThrows(AlreadyExistsException.class,
                () -> planService.savePlan(dto));
    }

    // ---------------- getPlans ----------------

    @Test
    void testGetPlans_Success() {
        PlanMaster entity = new PlanMaster();
        entity.setPlanName("Health");

        when(planMasterRepo.findAll())
                .thenReturn(List.of(entity));

        List<PlanResponceDto> result = planService.getPlans();

        assertEquals(1, result.size());
    }

    @Test
    void testGetPlans_NotFound() {
        when(planMasterRepo.findAll())
                .thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class,
                () -> planService.getPlans());
    }

    // ---------------- getPlan ----------------

    @Test
    void testGetPlan_Success() {
        PlanMaster entity = new PlanMaster();
        entity.setPlanName("Health");

        when(planMasterRepo.findById(1))
                .thenReturn(Optional.of(entity));

        PlanResponceDto dto = planService.getPlan(1);

        assertEquals("Health", dto.getPlanName());
    }

    @Test
    void testGetPlan_NotFound() {
        when(planMasterRepo.findById(99))
                .thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> planService.getPlan(99));

        assertTrue(ex.getMessage().contains("99"));
    }

    // ---------------- updatePlan ----------------

    @Test
    void testUpdatePlan_Success() {
        PlanMaster entity = new PlanMaster();

        when(planMasterRepo.findById(1))
                .thenReturn(Optional.of(entity));

        boolean result = planService.updatePlan(1, AppConstants.ACTIVE);

        assertTrue(result);
        verify(planMasterRepo).save(entity);
    }

    @Test
    void testUpdatePlan_InvalidStatus() {
        assertThrows(InvalidException.class,
                () -> planService.updatePlan(1, "N"));
     // Optional but strong assertion
        verify(planMasterRepo, never()).findById(anyInt());
        verify(planMasterRepo, never()).save(any());
    }

    @Test
    void testUpdatePlan_NotFound() {
        when(planMasterRepo.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> planService.updatePlan(99, AppConstants.ACTIVE));
        verify(planMasterRepo).findById(99);
        verify(planMasterRepo, never()).save(any());
    }

    
   
}
