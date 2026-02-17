package com.example.demo.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.constants.AppConstants;
import com.example.demo.dto.PlanDto;
import com.example.demo.dto.PlanResponceDto;
import com.example.demo.exceptions.AlreadyExistsException;
import com.example.demo.exceptions.InvalidException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.service.PlanService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PlanRestController.class)
@AutoConfigureMockMvc(addFilters = false) // bypass security for unit tests
class PlanRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanService planService;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------------- SAVE PLAN ----------------
    @Test
    void testSavePlan_Success() throws Exception {
        PlanDto planDto = new PlanDto();
        planDto.setPlanName("HealthPlan");
        planDto.setActiveSw("Y");
        planDto.setStartDate(LocalDate.now());
        planDto.setEndDate(LocalDate.now().plusDays(30));

        when(planService.savePlan(any(PlanDto.class))).thenReturn(true);

        mockMvc.perform(post("/plan/savePlan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(planDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testSavePlan_AlreadyExists() throws Exception {
        PlanDto planDto = new PlanDto();
        planDto.setPlanName("HealthPlan");
        planDto.setActiveSw("Y");
        planDto.setStartDate(LocalDate.now());
        planDto.setEndDate(LocalDate.now().plusDays(30));

        when(planService.savePlan(any(PlanDto.class)))
                .thenThrow(new AlreadyExistsException("Plan already exists"));

        mockMvc.perform(post("/plan/savePlan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(planDto)))
                .andExpect(status().isConflict());
    }

    // ---------------- GET ALL PLANS ----------------
    @Test
    void testGetPlans_Success() throws Exception {
        PlanResponceDto dto = new PlanResponceDto();
        dto.setPlanName("HealthPlan");

        when(planService.getPlans()).thenReturn(List.of(dto));

        mockMvc.perform(get("/plan/plans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].planName").value("HealthPlan"));
    }

    @Test
    void testGetPlans_NotFound() throws Exception {
        when(planService.getPlans()).thenThrow(new NotFoundException("Plans not found"));

        mockMvc.perform(get("/plan/plans"))
                .andExpect(status().isNotFound());
    }

    // ---------------- UPDATE PLAN (GET PLAN DATA) ----------------
    @Test
    void testUpdatePlan_Success() throws Exception {
        PlanResponceDto dto = new PlanResponceDto();
        dto.setPlanName("HealthPlan");

        when(planService.updatePlanStatus(1)).thenReturn(dto);

        mockMvc.perform(put("/plan/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.planName").value("HealthPlan"));
    }

    @Test
    void testUpdatePlan_NotFound() throws Exception {
        when(planService.updatePlanStatus(99)).thenThrow(new NotFoundException("Plan not found"));

        mockMvc.perform(put("/plan/99"))
                .andExpect(status().isNotFound());
    }

    // ---------------- UPDATE STATUS ----------------
    @Test
    void testUpdatePlanStatus_Success() throws Exception {
        when(planService.updatePlan(eq(1), eq("Y"))).thenReturn(true);

        mockMvc.perform(put("/plan/1/Y"))
                .andExpect(status().isOk())
                .andExpect(content().string(AppConstants.STATUS_ACTIVE));
    }

    @Test
    void testUpdatePlanStatus_Failure_InternalError() throws Exception {
        when(planService.updatePlan(eq(1), eq("N"))).thenReturn(false);

        mockMvc.perform(put("/plan/1/N"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(AppConstants.STATUS_INACTIVE));
    }

    @Test
    void testUpdatePlanStatus_InvalidStatus() throws Exception {
        when(planService.updatePlan(eq(1), eq("X"))).thenThrow(new InvalidException("Invalid status"));

        mockMvc.perform(put("/plan/1/X"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdatePlanStatus_NotFound() throws Exception {
        when(planService.updatePlan(eq(99), eq("Y"))).thenThrow(new NotFoundException("Plan not found"));

        mockMvc.perform(put("/plan/99/Y"))
                .andExpect(status().isNotFound());
    }

    // ---------------- GET ACTIVE PLAN NAMES ----------------
    @Test
    void testGetActivePlanNames() throws Exception {
        when(planService.getActivePlanNames()).thenReturn(List.of("HealthPlan"));

        mockMvc.perform(get("/plan/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0]").value("HealthPlan"));
    }

    // ---------------- ACCESS DENIED TEST ----------------
    @Test
    void testAccessDeniedException() throws Exception {
        when(planService.getPlans())
                .thenThrow(new AccessDeniedException("Access denied"));

        mockMvc.perform(get("/plan/plans"))
                .andExpect(status().isForbidden());
    }
}
