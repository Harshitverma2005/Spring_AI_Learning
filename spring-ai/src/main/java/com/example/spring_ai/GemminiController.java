package com.example.spring_ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GemminiController {
    private ChatClient client;

    public GemminiController(VertexAiGeminiChatModel vertexAiGeminiChatModel)
    {
        this.client=ChatClient.create(vertexAiGeminiChatModel);
    }

    @GetMapping("/nonu/{message}")
            public String api(@PathVariable String message)
    {
        String res = client.prompt().user(message).call().content();
        return res;
    }
}
