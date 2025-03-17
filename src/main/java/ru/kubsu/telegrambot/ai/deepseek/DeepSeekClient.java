package ru.kubsu.telegrambot.ai.deepseek;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class DeepSeekClient {

    public static final String DEEP_SEEK_BASE_URL = "https://api.deepseek.com/chat/completions";
    public static final String DEEP_SEEK_API = "sk-e600bd8c7528487ea3659608af82ffa6";

    private final RestTemplate restTemplate = new RestTemplate();

    public String ask(String text) {
        // Заголовки запроса
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + DEEP_SEEK_API);
        headers.set("Content-Type", "application/json");

        // Тело запроса
        DeepSeekRequest.Message message = new DeepSeekRequest.Message();
        message.setRole("user");
        message.setContent(text);

        DeepSeekRequest request = new DeepSeekRequest();
        request.setModel("deepseek-chat");
        request.setMessages(List.of(message));
        request.setStream(false);

        // Создаем HTTP-сущность
        HttpEntity<DeepSeekRequest> entity = new HttpEntity<>(request, headers);

        // Отправляем запрос
        ResponseEntity<DeepSeekResponse> response = restTemplate.exchange(
                DEEP_SEEK_BASE_URL,
                HttpMethod.POST,
                entity,
                DeepSeekResponse.class
        );

        // Обрабатываем ответ
        if (response.getBody() != null && !response.getBody().getChoices().isEmpty()) {
            return response.getBody().getChoices().get(0).getMessage().getContent();
        } else {
            return "Ошибка: ответ от DeepSeek пуст.";
        }
    }

    public static final class DeepSeekRequest {
        private String model;
        private List<Message> messages;
        private boolean stream;

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public List<Message> getMessages() {
            return messages;
        }

        public void setMessages(List<Message> messages) {
            this.messages = messages;
        }

        public boolean isStream() {
            return stream;
        }

        public void setStream(boolean stream) {
            this.stream = stream;
        }

        public static class Message {
            private String role;
            private String content;

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }

    public static final class DeepSeekResponse {
        private List<Choice> choices;

        public List<Choice> getChoices() {
            return choices;
        }

        public void setChoices(List<Choice> choices) {
            this.choices = choices;
        }

        public static class Choice {
            private Message message;

            public Message getMessage() {
                return message;
            }

            public void setMessage(Message message) {
                this.message = message;
            }
        }

        public static class Message {
            private String role;
            private String content;

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}