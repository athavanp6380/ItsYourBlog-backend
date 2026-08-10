package com.example.itsyourblog.aiservice.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiServiceImpl implements AiService{

    private final ChatClient chatClient;

    public AiServiceImpl(ChatClient.Builder chatClientBuilder)
    {
        this.chatClient = chatClientBuilder.build();
    }
    @Override
    public String chat(String message) {
        return chatClient
                .prompt()
                .user(message)
                .call()
                .content();
    }
}
