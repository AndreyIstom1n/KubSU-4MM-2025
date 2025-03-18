package ru.kubsu.telegrambot.ai.chatgpt;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import ru.kubsu.telegrambot.ai.AiService;

@Service
public class ChatGptService implements AiService {

    @Override
    public String ask(final String question) {
        throw new NotImplementedException("ChatGPT is not implemented");
    }
}