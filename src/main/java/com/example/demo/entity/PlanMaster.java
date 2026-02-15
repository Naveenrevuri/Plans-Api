package com.example.demo.entity;

import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plan_master")
public class PlanMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer planId;
	
	@NotBlank(message = "Plan name is required")
	@Size(min = 3, max = 30)
	@Column( unique = true)
	private String planName;
	
	 @Size(max = 200, message = "Comments cannot exceed 200 characters")
	 @Nullable
	private String comments;
	
	 @NotNull(message = "Must be select startDate")
	 @JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate startDate;
	 
	@NotNull(message = "Must be select EndDate")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate endDate;
	
     @Pattern(regexp = "Y|N", message = "ActiveSw must be either Y or N")
     @NotBlank
	private String activeSw;
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	@UpdateTimestamp
	private LocalDate updatedDate;
	
	     
	private Integer createdBy;
	
	private Integer updatedBy;
	
	
	
	
}
