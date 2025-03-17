package ru.kubsu.telegrambot.bot;

import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.kubsu.telegrambot.ai.deepseek.DeepSeekClient;

import java.util.ArrayList;
import java.util.List;

public class MyAmazingBot implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private final DeepSeekClient deepSeekClient;

    public MyAmazingBot(TelegramClient telegramClient,
                        DeepSeekClient deepSeekClient) {
        this.telegramClient = telegramClient;
        this.deepSeekClient = deepSeekClient;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            final String messageText = update.getMessage().getText();
            System.out.println("RECEIVE: " + messageText);

            try {
                if (messageText.equals("/start")) {
                    SendMessage sendMessage = new SendMessage(
                            update.getMessage().getChatId().toString(),
                            "\uD83D\uDE07");

                    // Создаем клавиатуру
                    ReplyKeyboardMarkup keyboardMarkup = createKeyboard();
                    sendMessage.setReplyMarkup(keyboardMarkup);

                    telegramClient.execute(sendMessage);
                } else if (messageText.equals("/image")) {
                    // Отправляем изображение
                    SendPhoto sendPhoto = new SendPhoto(
                            update.getMessage().getChatId().toString(),
                            new InputFile("https://icdn.lenta.ru/images/2024/03/18/12/20240318124428206/square_320_e25caa76d6cf400ae8b7503af4c22eb9.jpg")
                    );

                    telegramClient.execute(sendPhoto);
                } else {
                    final String aiAnswer = deepSeekClient.ask(messageText);

                    SendMessage sendMessage = new SendMessage(
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
        // Создаем строки для клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboard);
        keyboardMarkup.setResizeKeyboard(true); // Автоматическое изменение размера клавиатуры
        keyboardMarkup.setOneTimeKeyboard(false); // Клавиатура остается активной после нажатия

        // Первая строка
        KeyboardRow row1 = new KeyboardRow();
        row1.add("2 + 2 = ?");
        row1.add("/image");
        keyboard.add(row1);

        // Вторая строка
        KeyboardRow row2 = new KeyboardRow();
        row2.add("Какой курс доллара?");
        row2.add("Отправь любой смайлик?");
        keyboard.add(row2);

        // Устанавливаем клавиатуру
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }
}