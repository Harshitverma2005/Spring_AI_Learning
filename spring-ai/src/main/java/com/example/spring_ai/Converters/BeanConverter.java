package com.example.spring_ai.Converters;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BeanConverter {
    private ChatClient client;

    public BeanConverter(OpenAiChatModel openAiChatModel)
    {
        this.client =ChatClient.create(openAiChatModel);
    }

    @GetMapping("/movie")
    public Movie movies(@RequestParam String name)
    {
        String message = """
                Name the best and famous movie of{name} and the format is {format}""";

        BeanOutputConverter<Movie> outputConverter = new BeanOutputConverter<>(Movie.class);

        PromptTemplate promptTemplate= new PromptTemplate(message);
        Prompt prompt = promptTemplate.create( Map.of("name",name,"format",outputConverter.getFormat()));

        Movie movies = outputConverter.convert(client.prompt(prompt).call().content());
        return movies;
    }
}


