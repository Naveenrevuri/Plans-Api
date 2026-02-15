package com.example.demo.constants;

public class LogConstants {
	
	 // prevent object creation
    private LogConstants() {
    }

    /* =========================
       PLAN MODULE LOGS
       ========================= */

    public static final String SAVE_PLAN_REQUEST =
            "Request to save plan received: {}";

    public static final String PLAN_ALREADY_EXISTS_PRECHECK =
            "Plan already exists (pre-check): {}";

    public static final String PLAN_ALREADY_EXISTS_DB =
            "Plan already exists (DB constraint): {}";

    public static final String PLAN_SAVED_SUCCESS =
            "Plan saved successfully: {}";

    public static final String PLAN_SAVE_UNEXPECTED_ERROR =
            "Unexpected error occurred while saving plan: {}";
    
    
    
 // ---------- GET ALL PLANS ----------

    public static final String FETCH_ALL_PLANS_REQUEST =
            "Request to fetch all plans";

    public static final String FETCH_ALL_PLANS_SUCCESS =
            "Total plans fetched: {}";

    public static final String FETCH_ALL_PLANS_NOT_FOUND =
            "No plans found in system";

    public static final String FETCH_ALL_PLANS_ERROR =
            "Unexpected error occurred while fetching all plans";
    
    
    
 // ---------- GET PLAN BY ID ----------

    public static final String FETCH_PLAN_BY_ID_REQUEST =
            "Request to fetch plan by id: {}";

    public static final String PLAN_NOT_FOUND_BY_ID =
            "Plan not found for id: {}";

    public static final String FETCH_PLAN_BY_ID_SUCCESS =
            "Plan fetched successfully for id: {}";

    public static final String FETCH_PLAN_BY_ID_ERROR =
            "Unexpected error occurred while fetching plan by id: {}";
    public static final String INVALID_PLAN_STATUS =
            "Invalid plan status. Allowed values are ACTIVE or INACTIVE. Given: ";



    /* =========================
       USER MODULE LOGS (example)
       ========================= */

    public static final String SAVE_USER_REQUEST =
            "Request to save user received: {}";

    public static final String USER_SAVED_SUCCESS =
            "User saved successfully: {}";

    public static final String USER_ALREADY_EXISTS =
            "User already exists: {}";
    
    


    /* =========================
       COMMON / SYSTEM LOGS
       ========================= */

    public static final String VALIDATION_FAILED =
            "Validation failed: {}";

    public static final String SYSTEM_ERROR =
            "System error occurred";
    
    

    // Update plan status
    public static final String UPDATE_PLAN_STATUS_REQUEST =
            "Updating plan ID: {} with status: {}";
    public static final String UPDATE_PLAN_STATUS_INVALID =
            "Plan status invlid, ID: {}";
    public static final String UPDATE_PLAN_STATUS_SUCCESS =
            "Plan status updated successfully for ID: {}";
    public static final String UPDATE_PLAN_ID_NOT_FOUND =
            "update plan Id NotFound, ID: {}";
    
    
    public static final String  UPDATE_PLAN_STATUS_CONTROLLER_SUCCESS=
    		"updated Plan status controller successfully for ID: {}";
    public static final String UPDATE_PLAN_STATUS_CONTROLLER_FAILED=
    		"updated Plan status controller failed for ID: {}";
    		
    
    
    
    // Distinct start dates
    public static final String FETCH_DISTINCT_START_DATES_REQUEST =
            "Fetching distinct start dates ";
    public static final String FETCH_DISTINCT_START_DATES_NOT_FOUND =
            "No distinct start dates found";
    public static final String FETCH_DISTINCT_START_DATES_SUCCESS =
            "Distinct start dates fetched  successfully, count: {}";

    // Distinct end dates
    public static final String FETCH_DISTINCT_END_DATES_REQUEST =
            "Fetching distinct end dates";
    public static final String FETCH_DISTINCT_END_DATES_NOT_FOUND =
            "No distinct end dates found";
    public static final String FETCH_DISTINCT_END_DATES_SUCCESS =
            "Distinct end dates fetched successfully, count: {}";



}
