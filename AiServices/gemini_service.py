from google import genai
from prompts import SYSTEM_PROMPT
import os
import json
from dotenv import load_dotenv

load_dotenv()

API_KEY = os.getenv("GEMINI_API_KEY")

client = genai.Client(api_key=API_KEY)


def analyze_emergency(message):

    prompt = f"""
    {SYSTEM_PROMPT}

    Respond ONLY with a valid JSON object in this exact format:
    {{"type": "<emergency type>", "severity": "<low|medium|high>", "instructions": "<safety instructions>"}}

    USER MESSAGE:
    {message}
    """

    response = client.models.generate_content(
        model="gemini-2.5-flash",
        contents=prompt
    )

    text = response.text.strip()

    # Remove markdown code fences if present
    if text.startswith("```"):
        lines = text.split("\n")
        # Remove first line (```json) and last line (```)
        lines = [l for l in lines if not l.strip().startswith("```")]
        text = "\n".join(lines).strip()

    try:
        data = json.loads(text)
        return {
            "type": data.get("type", "unknown"),
            "severity": data.get("severity", "unknown"),
            "instructions": data.get("instructions", text)
        }
    except json.JSONDecodeError:
        return {
            "type": "unknown",
            "severity": "unknown",
            "instructions": text
        }