package com.example.spring_ai.Converters;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ListConverter {

    private ChatClient client;

    public ListConverter(OpenAiChatModel openAiChatModel)
    {
        this.client =ChatClient.create(openAiChatModel);
    }

    @GetMapping("/movies")
    public List<String> movies(@RequestParam String name)
    {
        String message = """
                Name of 5 best and famous movies of{name} and the format is {format}""";

        ListOutputConverter listOutputConverter = new ListOutputConverter(new DefaultConversionService());

        PromptTemplate promptTemplate= new PromptTemplate(message);
        Prompt prompt = promptTemplate.create( Map.of("name",name,"format",listOutputConverter.getFormat()));

        List<String> movies = listOutputConverter.convert(client.prompt(prompt).call().content());
        return movies;
    }
}
