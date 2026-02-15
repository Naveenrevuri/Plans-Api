package com.example.demo.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanDto {
	
	
	@NotBlank(message = "Plan name is required")
	@Size(min = 3, max = 30 ,message = "Plan name must be between 3 and 30 characters")
	@Column( unique = true)
	private String planName;
	
	@Size(max = 200, message = "Comments cannot exceed 200 characters")
	@Nullable
	private String comments;
	
	 @NotNull(message = "Start date is required")
	 @JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	
	 @NotNull(message = "End date is required")
	 @JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	
	 @Pattern(regexp = "Y|N", message = "ActiveSw must be either Y or N")
	 @NotBlank
	private String activeSw;



	


	
	

}
