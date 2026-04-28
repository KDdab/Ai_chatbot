package com.emergencyai.Nemonia.Services;

import java.util.Map;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {
    public void triggerAlert(Map<String, Object> payload) {

        String webhookUrl =
                "https://your-n8n-webhook-url";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(payload, headers);

        restTemplate.postForObject(
                webhookUrl,
                entity,
                String.class
        );
    }
}
