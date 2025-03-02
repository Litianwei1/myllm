package com.example.myllm.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.model.ApiKey;
import org.springframework.ai.model.SimpleApiKey;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
class Config {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.base-url}")
    private String baseUrl;


    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
//        return builder.defaultSystem("You are a friendly chat bot that answers question in the voice of a Pirate")
//                .build();


        return builder.defaultSystem("认真回答用户问的问题")
                .build();
    }

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        // 创建并返回 SimpleVectorStore 实例
        return SimpleVectorStore.builder(embeddingModel).build();
    }


    @Bean
    public OpenAiApi openAiApi() {
        // 创建 API 密钥对象
        ApiKey apikey = new SimpleApiKey(apiKey);

        // 自定义请求头（可选）
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apiKey);
        headers.add("Model", "deepseek-chat");

        // 使用新的构造函数创建 OpenAiApi 实例
        return new OpenAiApi(
                baseUrl,                              // 基础 URL
                apikey,                               // API 密钥
                headers,                              // 自定义请求头
                "/v1/chat/completions",               // 完成路径
                "/v1/embeddings",                     // 嵌入路径
                RestClient.builder(),                 // REST 客户端构建器
                WebClient.builder(),                  // Web 客户端构建器
                new DefaultResponseErrorHandler() // 错误处理器（可选）
        );
    }


    @Bean
    public EmbeddingModel embeddingModel(OpenAiApi openAiApi) {
        // 使用 OpenAI 的嵌入模型
        return new OpenAiEmbeddingModel(openAiApi, MetadataMode.EMBED);
    }

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }
}