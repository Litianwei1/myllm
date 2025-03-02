package com.example.myllm.api;

import com.example.myllm.base.CustomerSupportAssistant;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
class AIController {

    @Autowired
    private final ChatClient chatClient;

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private ChatMemory chatMemory;

    @Autowired
    private CustomerSupportAssistant customerSupportAssistant;



    AIController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @PostMapping("/ai/simple1")
    public String completion1(@RequestBody Map<String, String> request) {
        return handleChatResponse(request.get("chatId"),request.get("message"));
    }

    public String handleChatResponse(String chatId, String message) {
        // 调用 chat 方法获取 Flux 流，并直接收集为字符串
        return customerSupportAssistant.chat(chatId, message)
                .collect(Collectors.joining()) // 使用 Collectors.joining 拼接字符串
                .block(); // 阻塞直到流完成并返回结果
    }

    @PostMapping("/ai/simple")
    public String completion(@RequestBody Map<String, String> request) {
                return chatClient.prompt()
                .advisors(
                        new MessageChatMemoryAdvisor(chatMemory)
//                        new QuestionAnswerAdvisor(vectorStore) //目前未解决
                )
                .user(request.get("message"))
                .call()
                .content();
    }

//    @PostMapping("/ai/simple")
//    public Map<String, String> completion(@RequestBody Map<String,String> message) {
//        System.out.println(message);
//        return Map.of("completion", Objects.requireNonNull(this.chatClient.prompt().user(message.get("message")).call().content()));
//    }
}