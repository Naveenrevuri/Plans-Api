package com.example.demo.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
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
class PlanRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlanService planService;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------------- savePlan ----------------

    @Test
    void testSavePlan_Success() throws Exception {
    	 PlanDto dto = new PlanDto();
    	    dto.setPlanName("HealthPlan");
    	    dto.setComments("Test plan");
    	    dto.setStartDate(LocalDate.now());
    	    dto.setEndDate(LocalDate.now().plusDays(30));
    	    dto.setActiveSw("Y");   // 🔥 MOST IMPORTANT

    	    when(planService.savePlan(any(PlanDto.class))).thenReturn(true);

    	    mockMvc.perform(post("/savePlan")
    	            .contentType(MediaType.APPLICATION_JSON)
    	            .content(objectMapper.writeValueAsString(dto)))
    	            .andDo(print())   // debugging helper
    	            .andExpect(status().isCreated())
    	            .andExpect(content().string(AppConstants.PLAN_SAVED));
    }

    @Test
    void testSavePlan_AlreadyExists() throws Exception {
        PlanDto dto = new PlanDto();
        dto.setPlanName("Health");

        when(planService.savePlan(dto))
                .thenThrow(new AlreadyExistsException("Plan already exists"));

        mockMvc.perform(post("/savePlan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // ---------------- getPlans ----------------

    @Test
    void testGetPlans_Success() throws Exception {
    	PlanResponceDto dto = new PlanResponceDto();
        dto.setPlanName("Health");

        when(planService.getPlans()).thenReturn(List.of(dto));

        mockMvc.perform(get("/Plans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].planName").value("Health"));
    }

    @Test
    void testGetPlans_NotFound() throws Exception {
        when(planService.getPlans())
                .thenThrow(new NotFoundException("Plan not found"));

        mockMvc.perform(get("/Plans"))
                .andExpect(status().isNotFound());
    }

    // ---------------- getPlan by id ----------------

    @Test
    void testGetPlan_Success() throws Exception {
        PlanResponceDto dto=new PlanResponceDto();
        dto.setPlanName("Health");

        when(planService.getPlan(1)).thenReturn(dto);

        mockMvc.perform(get("/plan/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.planName").value("Health"));
    }

    @Test
    void testGetPlan_NotFound() throws Exception {
        when(planService.getPlan(99))
                .thenThrow(new NotFoundException("Plan not found"));

        mockMvc.perform(get("/plan/99"))
                .andExpect(status().isNotFound());
    }

    // ---------------- updatePlanStatus ----------------

    @Test
    void testUpdatePlanStatus_Success() throws Exception {
        when(planService.updatePlan(1, "ACTIVE"))
                .thenReturn(true);

        mockMvc.perform(put("/plan/1/ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(content().string(AppConstants.STATUS_ACTIVE));
    }

    @Test
    void testUpdatePlanStatus_InvalidStatus() throws Exception {
        when(planService.updatePlan(1, "N"))
                .thenThrow(new InvalidException("Invalid status"));

        mockMvc.perform(put("/plan/1/N"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdatePlanStatus_NotFound() throws Exception {
        when(planService.updatePlan(99, "ACTIVE"))
                .thenThrow(new NotFoundException("Plan not found"));

        mockMvc.perform(put("/plan/99/ACTIVE"))
                .andExpect(status().isNotFound());
    }

    
}
