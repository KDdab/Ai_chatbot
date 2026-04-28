package com.emergencyai.Nemonia.Services;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.emergencyai.Nemonia.DTO.AIResponse;
import com.emergencyai.Nemonia.DTO.EmergencyRequest;

@Service 
public class AIService {
    public AIResponse analyze(String message) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8000/analyze";

        EmergencyRequest request = new EmergencyRequest();
        request.setMessage(message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EmergencyRequest> entity =
                new HttpEntity<>(request, headers);

        try {
            ResponseEntity<AIResponse> response =
                    restTemplate.postForEntity(
                            url,
                            entity,
                            AIResponse.class
                    );

            return response.getBody();
        } catch (Exception e) {
            AIResponse fallback = new AIResponse();
            fallback.setType("error");
            fallback.setSeverity("unknown");
            fallback.setInstructions("AI service is unavailable. Please ensure the Python AI service is running on port 8000. Error: " + e.getMessage());
            return fallback;
        }
    }
}
