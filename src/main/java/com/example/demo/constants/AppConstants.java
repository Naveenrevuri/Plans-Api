package com.example.demo.constants;

public final class AppConstants {
	 private AppConstants() {
	        // prevent instantiation
	    }
	
	// Success Messages
    public static final String PLAN_SAVED = "Plan saved successfully";
    public static final String PLAN_UPDATED = "Plan updated successfully";
    public static final String PLAN_DELETED = "Plan deleted successfully";
    
    

    // Retrieval Messages
    public static final String PLANS_RETRIEVED = "Plans retrieved successfully";
    public static final String START_DATES_RETRIEVED = "Distinct start dates retrieved successfully";
    public static final String END_DATES_RETRIEVED = "Distinct end dates retrieved successfully";
    

    // Error Messages
    public static final String PLAN_SAVE_FAILED = "Failed to save plan";
    public static final String PLAN_NOT_FOUND = "Plan not found";
    public static final String PLAN_UPDATE_FAILED = "Failed to update plan";
    public static final String INVALID_PLAN_NAME = "Plan name cannot be empty";
    public static final String NULL_PLAN_DATA = "Plan data cannot be null";
    public static final String FETCH_DISTINCT_START_DATES_NOT_FOUND =
            "No distinct start dates found";
    public static final String FETCH_DISTINCT_END_DATES_NOT_FOUND =
            "No distinct end dates found";
    
    
    // ---------- GET PLAN BY ID ----------

    public static final String FETCH_PLAN_BY_ID_REQUEST =
            "Request to fetch plan by id: {}";

    public static final String PLAN_NOT_FOUND_BY_ID =
            "Plan not found for id: {}";

    public static final String FETCH_PLAN_BY_ID_SUCCESS =
            "Plan fetched successfully for id: {}";
    public static final String UPDATE_PLAN_ID_NOT_FOUND =
            "update plan Id NotFound, ID: {}";
    

    
    //-------------Active/inactive-------------
    public static final String ACTIVE = "ACTIVE";
    public static final String INACTIVE = "INACTIVE";
    public static final String INVALID_PLAN_STATUS =
            "Invalid plan status. Allowed values are ACTIVE or INACTIVE. Given: ";
     
 // -------------------------
    // CONFLICT MESSAGES
    // -------------------------
    public static final String PLAN_ALREADY_EXISTS = "Plan already exists with name: ";


    // Status
    public static final String STATUS_ACTIVE = "Y";
    public static final String STATUS_INACTIVE = "N";

}
