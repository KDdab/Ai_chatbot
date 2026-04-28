package com.emergencyai.Nemonia.Services;

import org.springframework.stereotype.Service;

@Service
public class RuleService {
    public int calculateSeverity(String message) {

        int score = 0;

        message = message.toLowerCase();

        if(message.contains("fire")) {
            score += 5;
        }

        if(message.contains("smoke")) {
            score += 3;
        }

        if(message.contains("help")) {
            score += 2;
        }

        return score;
    }
}

