package com.example.spring_ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class OllamaController {

    private ChatClient client;

//    @Autowired
//    private EmbeddingModel embeddingModel  ;

    public OllamaController(OllamaChatModel ollamaChatModel)
    {
        this.client=ChatClient.create(ollamaChatModel);
    }

     @GetMapping("/api/{message}")    //  using ChatResponse
    public ResponseEntity<String> getResponse (@PathVariable String message)
    {
        ChatResponse chatResponse = client.prompt(message).call().chatResponse();

        String response = chatResponse.getResult().getOutput().getText();

        System.out.println(chatResponse.getMetadata().getUsage());

        System.out.println(chatResponse.getMetadata().getModel());

        return ResponseEntity.ok(response);
    }

  //  @PostMapping("/api/recommended")
    public String recommend(@RequestParam String type, @RequestParam String watch , @RequestParam String year , @RequestParam String lang, @RequestParam String genre)
    {

        String text = """
                        I want to watch {watch} type of {type} with good rating and the genre {genre},
                        also it is around the year {year} and the language for it is {lang} ,
                        Give me best of best to watch.
                        also mentions:
                        episodes
                        ratings. 
                """;

        PromptTemplate promptTemplate = new PromptTemplate(text);
        Prompt prompt = promptTemplate.create(Map.of("type",type,"lang",lang,"watch",watch,"year",year,"genre",genre));


        String response = client.prompt(prompt).call().content();

        return response;

    }

    @PostMapping("/api/product")
    public String products(@RequestParam String text)
    {
        return "";
    }
}
