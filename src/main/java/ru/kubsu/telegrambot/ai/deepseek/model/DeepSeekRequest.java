package ru.kubsu.telegrambot.ai.deepseek.model;

import java.util.List;

public final class DeepSeekRequest {

    private String model;
    private List<Message> messages;
    private boolean stream;

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(final List<Message> messages) {
        this.messages = messages;
    }

    public boolean isStream() {
        return stream;
    }

    public void setStream(final boolean stream) {
        this.stream = stream;
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