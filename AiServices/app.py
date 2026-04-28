from fastapi import FastAPI
from pydantic import BaseModel
from gemini_service import analyze_emergency

app = FastAPI()

class EmergencyRequest(BaseModel):
    message: str

@app.post("/analyze")
def analyze(data: EmergencyRequest):

    result = analyze_emergency(data.message)

    return result