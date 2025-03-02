package com.example.myllm.api;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
class AIController {

    @Autowired
    private final ChatClient chatClient;


    AIController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping("/ai/simple")
    public String completion(@RequestBody Map<String, String> request) {
                return chatClient.prompt()
                .advisors(
                        new MessageChatMemoryAdvisor(chatMemory),
                        new QuestionAnswerAdvisor(vectorStore)
                )
                .user(request.get("message"))
                .call()
                .content();
    }

    @GetMapping("/ai/get/simple")
    public Map<String, String> completion(@RequestParam(value = "message", defaultValue = "你是谁 用的什么模型") String message) {
        return Map.of("completion", this.chatClient.prompt().user(message).call().content());
    }
}