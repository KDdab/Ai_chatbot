from google import genai
from prompts import SYSTEM_PROMPT
import os
import json
from dotenv import load_dotenv

load_dotenv()

API_KEY = os.getenv("GEMINI_API_KEY")
client = genai.Client(api_key=API_KEY)


def analyze_message(message):
    prompt = f"""
{SYSTEM_PROMPT}

USER MESSAGE:
{message}
"""

    response = client.models.generate_content(
        model="gemini-2.5-flash",
        contents=[{"role": "user", "parts": [prompt]}]
    )

    text = response.text.strip()
    text = text.replace("```json", "").replace("```", "")

    try:
        data = json.loads(text)
        return {
            "intent": data.get("intent", "unknown"),
            "employee_name": data.get("employee_name", "unknown"),
            "action": data.get("action", "none"),
            "response": data.get("response", text)
        }
    except:
        return {
            "intent": "unknown",
            "employee_name": "unknown",
            "action": "none",
            "response": text
        }
