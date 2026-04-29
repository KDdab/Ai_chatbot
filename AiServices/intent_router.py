def route_intent(intent):
    mapping = {
        "employee_details": "FETCH_EMPLOYEE",
        "leave_request": "CREATE_LEAVE",
        "leave_status": "APPROVE_LEAVE",
        "leave_balance": "CHECK_LEAVE",
        "performance_rating": "FETCH_PERFORMANCE",
        "promotion_query": "CHECK_PROMOTION",
        "candidate_shortlisting": "SHORTLIST_CANDIDATE",
        "interview_schedule": "SCHEDULE_INTERVIEW",
        "salary_query": "FETCH_SALARY"
    }

    return mapping.get(intent, "GENERAL_QUERY")
