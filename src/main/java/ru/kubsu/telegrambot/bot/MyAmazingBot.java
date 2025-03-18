package ru.kubsu.telegrambot.bot;

import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.kubsu.telegrambot.ai.deepseek.DeepSeekService;

import java.util.ArrayList;
import java.util.List;

public final class MyAmazingBot implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private final DeepSeekService deepSeekService;

    public MyAmazingBot(final TelegramClient telegramClient,
                        final DeepSeekService deepSeekService) {
        this.telegramClient = telegramClient;
        this.deepSeekService = deepSeekService;
    }

    @Override
    public void consume(final Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            final String messageText = update.getMessage().getText();
            System.out.println("RECEIVE: " + messageText);

            try {
                if (messageText.equals("/start")) {
                    final SendMessage sendMessage = new SendMessage(
                            update.getMessage().getChatId().toString(),
                            "\uD83D\uDE07");

                    final ReplyKeyboardMarkup keyboardMarkup = createKeyboard();
                    sendMessage.setReplyMarkup(keyboardMarkup);

                    telegramClient.execute(sendMessage);

                } else if (messageText.equals("/image")) {
                    final SendPhoto sendPhoto = new SendPhoto(
                            update.getMessage().getChatId().toString(),
                            new InputFile("https://icdn.lenta.ru/images/2024/03/18/12/20240318124428206/square_320_e25caa76d6cf400ae8b7503af4c22eb9.jpg")
                    );

                    telegramClient.execute(sendPhoto);

                } else {
                    final String aiAnswer = deepSeekService.ask(messageText);

                    final SendMessage sendMessage = new SendMessage(
                            update.getMessage().getChatId().toString(),
                            aiAnswer);
                    telegramClient.execute(sendMessage);

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private ReplyKeyboardMarkup createKeyboard() {
        final List<KeyboardRow> keyboard = new ArrayList<>();

        final ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        final KeyboardRow row1 = new KeyboardRow();
        row1.add("Что входит в базовые знания Computer Science?");
        row1.add("Средняя зарплата айтишника на рынке?");
        keyboard.add(row1);

        final KeyboardRow row2 = new KeyboardRow();
        row2.add("/image");
        row2.add("Отправь любой смайлик!");
        keyboard.add(row2);

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }
}