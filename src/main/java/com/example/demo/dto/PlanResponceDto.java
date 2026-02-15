package com.example.demo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanResponceDto {
	
	private Integer planId;
	private String planName;
	private String comments;
	private LocalDate startDate;
	private LocalDate endDate;
	private String activeSw;

}
