package com.example.spring_ai;

import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextToSpeech {
private OpenAiAudioSpeechModel openAiAudioSpeechModel;

public TextToSpeech (OpenAiAudioSpeechModel openAiAudioSpeechModel)
{
    this.openAiAudioSpeechModel = openAiAudioSpeechModel;
}
@PostMapping("api/TTS")
    public byte[] TTS(@RequestParam String text)
{
    return openAiAudioSpeechModel.call(text);
}

}
