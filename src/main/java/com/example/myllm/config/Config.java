package com.example.myllm.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Config {


    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
//        return builder.defaultSystem("You are a friendly chat bot that answers question in the voice of a Pirate")
//                .build();


        return builder.defaultSystem("认真回答用户问的问题")
                .build();
    }

}