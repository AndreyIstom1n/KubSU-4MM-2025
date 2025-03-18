package ru.kubsu.telegrambot.bot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import ru.kubsu.telegrambot.ai.deepseek.DeepSeekService;

@Service
public class BotService {

    @Value("${custom.telegram.bot.token}")
    public String telegramBotToken;

    private final DeepSeekService deepSeekService;

    @Autowired
    public BotService(final DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    @PostConstruct
    public void init() throws Exception {
        final TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
        botsApplication.registerBot(telegramBotToken,
                new MyAmazingBot(
                        new OkHttpTelegramClient(telegramBotToken),
                        deepSeekService));

    }
}