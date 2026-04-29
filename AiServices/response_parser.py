import json

def parse_response(text):
    try:
        return json.loads(text)
    except:
        return {
            "intent": "unknown",
            "employee_name": "unknown",
            "action": "none",
            "response": text
        }
