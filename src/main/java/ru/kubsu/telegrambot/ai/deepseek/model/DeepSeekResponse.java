package ru.kubsu.telegrambot.ai.deepseek.model;

import java.util.List;

public final class DeepSeekResponse {

    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(final List<Choice> choices) {
        this.choices = choices;
    }

    public static class Choice {

        private Message message;

        public Message getMessage() {
            return message;
        }

        public void setMessage(final Message message) {
            this.message = message;
        }
    }

    public static class Message {

        private String role;
        private String content;

        public String getRole() {
            return role;
        }

        public void setRole(final String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(final String content) {
            this.content = content;
        }
    }
}