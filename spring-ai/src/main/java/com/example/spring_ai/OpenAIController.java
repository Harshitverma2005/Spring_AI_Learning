package com.example.spring_ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class OpenAIController {

    private OpenAiChatModel openAiChatModel; //using Chat Model
//
//    public OpenAIController(OpenAiChatModel openAiChatModel)
//    {
//        this.openAiChatModel = openAiChatModel;
//    }

    private ChatClient client;

      public OpenAIController(OpenAiChatModel openAiChatModel) //use this when you use many models as onece and to specify it .
    {
      this.client=ChatClient.create(openAiChatModel);
    }

//    ChatMemory chatMemory = MessageWindowChatMemory.builder().build(); //for using model memory conversation
//
//    public OpenAIController(ChatClient.Builder builder)  //use this when you use only one model at the time.
//    {
//        this.client = builder
//                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
//                .build();
//    }


//    public OpenAIController(ChatClient.Builder builder)  //use this when you use only one model at the time.
//    {
//        this.client = builder.build();
//    }

//    @GetMapping("/api/{message}")
//    public ResponseEntity<String>  getResponse (@PathVariable String message)
//    {
//        String response = client.prompt(message)
//                .call()
//                .content();
//        return ResponseEntity.ok(response);
//    }

 //   @GetMapping("/api/{message}")    //  using ChatResponse
    public ResponseEntity<String>  getResponse (@PathVariable String message)
    {
        ChatResponse chatResponse = client.prompt(message).call().chatResponse();

        String response = chatResponse.getResult().getOutput().getText();

        System.out.println(chatResponse.getMetadata().getUsage());

        System.out.println(chatResponse.getMetadata().getModel());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/recommended")
    public String recommend(@RequestParam String type,@RequestParam String watch ,@RequestParam String year ,@RequestParam String lang,@RequestParam String genre)
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
}
