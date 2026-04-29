from fastapi import FastAPI
from schemas import HRRequest
from gemini_service import analyze_message
from intent_router import route_intent

app = FastAPI()


@app.post("/hr-assistant")
def hr_assistant(data: HRRequest):

    ai_result = analyze_message(data.message)

    # route intent → backend action
    action = route_intent(ai_result["intent"])

    return {
        "intent": ai_result["intent"],
        "employee_name": ai_result["employee_name"],
        "action": action,
        "response": ai_result["response"]
    }
