package com.example.itsyourblog.aiservice.service;

import org.springframework.stereotype.Service;

@Service
public interface AiService {
    String chat(String message);
}
