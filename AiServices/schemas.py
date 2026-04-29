from pydantic import BaseModel

class HRRequest(BaseModel):
    message: str


class AIResponse(BaseModel):
    intent: str
    employee_name: str
    action: str
    response: str
