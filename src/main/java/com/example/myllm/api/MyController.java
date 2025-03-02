package com.example.myllm.api;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MyController {

    private final ChatClient chatClient;

    public MyController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
        System.out.println("ChatClient.Builder injected: " + (chatClientBuilder != null));
    }

    @GetMapping("/ai")
    String generation() {
        ChatResponse chatResponse = chatClient.prompt()
                .user("Tell me a joke")
                .call()
                .chatResponse();
        chatResponse.getMetadata();
        return chatResponse.getMetadata().toString();
    }
}