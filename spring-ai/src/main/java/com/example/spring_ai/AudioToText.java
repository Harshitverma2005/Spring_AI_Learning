package com.example.spring_ai;

import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AudioToText {

    private OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;

    public AudioToText(OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel)
    {
        this.openAiAudioTranscriptionModel = openAiAudioTranscriptionModel;
    }

    @PostMapping("/api/ATS")

            public String ATS(@RequestParam MultipartFile multipartFile)
    {
        return openAiAudioTranscriptionModel.call(multipartFile.getResource());
    }


}
