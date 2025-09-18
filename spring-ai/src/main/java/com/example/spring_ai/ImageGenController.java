package com.example.spring_ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageGenController {

    private ChatClient client;
    private OpenAiImageModel openAiImageModel;

    public ImageGenController(OpenAiImageModel openAiImageModel, OpenAiChatModel openAiChatModel) {
        this.openAiImageModel = openAiImageModel;
      this.client = ChatClient.create(openAiChatModel);
    }
@GetMapping("/image/{query}")
    public  String genImage(@PathVariable String query)
{
    ImagePrompt imagePrompt = new ImagePrompt(query);

   ImageResponse imageResponse =  openAiImageModel.call(imagePrompt);

    return imageResponse.getResult().getOutput().getUrl();
}

@PostMapping("/image/describe")
public String descImage(@RequestParam String querry , @RequestParam MultipartFile file)
{
    return client.prompt()
            .user(promptUserSpec -> promptUserSpec.text(querry).media(MimeTypeUtils.IMAGE_JPEG,file.getResource()))
            .call().content();
}

}
