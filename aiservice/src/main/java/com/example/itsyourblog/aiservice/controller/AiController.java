package com.example.itsyourblog.aiservice.controller;

import com.example.itsyourblog.aiservice.dto.ChatRequest;
import com.example.itsyourblog.aiservice.dto.ChatResponse;
import com.example.itsyourblog.aiservice.service.AiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AiController {
    private final AiService aiService;

    public AiController(AiService aiService)
    {
        this.aiService = aiService;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request)
    {
       String response = aiService.chat(request.getMessage());
       return  new ChatResponse(response);
    }
}
