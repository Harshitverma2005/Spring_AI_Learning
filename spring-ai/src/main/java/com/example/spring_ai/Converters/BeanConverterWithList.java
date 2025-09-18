package com.example.spring_ai.Converters;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@RestController
public class BeanConverterWithList {
    private ChatClient client;

    public BeanConverterWithList(OpenAiChatModel openAiChatModel)
    {
        this.client =ChatClient.create(openAiChatModel);
    }

    @GetMapping("/moviesList")
    public List<Movie> movies(@RequestParam String name)
    {
        String message = """
                retur only JSON format of Name top 5 best and famous movies of{name} and the format is {format}""";

        BeanOutputConverter<List<Movie>> outputConverter = new BeanOutputConverter<>
                (new ParameterizedTypeReference<List<Movie>>() {});

        PromptTemplate promptTemplate= new PromptTemplate(message);
        Prompt prompt = promptTemplate.create( Map.of("name",name,"format",outputConverter.getFormat()));

       List<Movie> movies = outputConverter.convert(client.prompt(prompt).call().content());
        return movies;
    }
}




