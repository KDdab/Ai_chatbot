package com.emergencyai.Nemonia.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emergencyai.Nemonia.DTO.AIResponse;
import com.emergencyai.Nemonia.DTO.EmergencyRequest;
import com.emergencyai.Nemonia.Services.AIService;
import com.emergencyai.Nemonia.Services.RuleService;
import com.emergencyai.Nemonia.Services.WebhookService;

@RestController
@RequestMapping("/api")
public class EmerencyController {
    @Autowired
    private RuleService ruleService;

    @Autowired
    private AIService aiService;

    @Autowired
    private WebhookService webhookService;

    @PostMapping("/analyze")
    public Map<String, Object> analyze(
            @RequestBody EmergencyRequest request
    ) {

        String message = request.getMessage();

        int ruleSeverity =
                ruleService.calculateSeverity(message);

        AIResponse aiResponse =
                aiService.analyze(message);

        String severity = aiResponse.getSeverity() != null
                ? aiResponse.getSeverity() : "unknown";

        boolean triggerAlert =
                ruleSeverity >= 5 ||
                severity.equalsIgnoreCase("high");

        if(triggerAlert) {
            try {
                Map<String, Object> payload = new HashMap<>();

                payload.put("message", message);
                payload.put("severity", severity);
                payload.put("type", aiResponse.getType());

                webhookService.triggerAlert(payload);
            } catch (Exception e) {
                // Webhook failure should not block the response
                System.err.println("Webhook failed: " + e.getMessage());
            }
        }

        Map<String, Object> response = new HashMap<>();

        response.put("ruleSeverity", ruleSeverity);
        response.put("ai", aiResponse);
        response.put("alertTriggered", triggerAlert);

        return response;
    }
}
