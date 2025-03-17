package ru.kubsu.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import ru.kubsu.telegrambot.ai.deepseek.DeepSeekClient;
import ru.kubsu.telegrambot.bot.MyAmazingBot;

@SpringBootApplication
public class TelegrambotApplication {

	private static final String TELEGRAM_BOT_TOKEN = "7983066682:AAGdJrGsDJjQu_gufxi5pOqTwUJCix8EjfI";
	public static void main(String[] args) throws Exception {
		// Spring
		SpringApplication.run(TelegrambotApplication.class, args);

		// Telegram
		TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
		botsApplication.registerBot(TELEGRAM_BOT_TOKEN,
				new MyAmazingBot(
						new OkHttpTelegramClient(TELEGRAM_BOT_TOKEN),
						new DeepSeekClient()));
	}
}