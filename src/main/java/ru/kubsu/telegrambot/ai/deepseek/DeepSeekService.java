package ru.kubsu.telegrambot.ai.deepseek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kubsu.telegrambot.ai.AiService;
import ru.kubsu.telegrambot.ai.deepseek.model.DeepSeekRequest;
import ru.kubsu.telegrambot.ai.deepseek.model.DeepSeekResponse;

import java.util.List;

@Service
public class DeepSeekService implements AiService {

    @Value("${custom.ai.deepseek.url}")
    public String deepSeekUrl;

    @Value("${custom.ai.deepseek.token}")
    public String deepSeekToken;

    private final RestTemplate restTemplate;

    @Autowired
    public DeepSeekService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String ask(final String question) {
        return doRequest(question);
    }

    private String doRequest(final String question) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + deepSeekToken);
        headers.set("Content-Type", "application/json");

        final DeepSeekRequest.Message message = new DeepSeekRequest.Message();
        message.setRole("user");
        message.setContent(question);

        final DeepSeekRequest request = new DeepSeekRequest();
        request.setModel("deepseek-chat");
        request.setMessages(List.of(message));
        request.setStream(false);

        final HttpEntity<DeepSeekRequest> entity = new HttpEntity<>(request, headers);

        final ResponseEntity<DeepSeekResponse> response = restTemplate.exchange(
                deepSeekUrl,
                HttpMethod.POST,
                entity,
                DeepSeekResponse.class);

        if (response.getBody() != null && !response.getBody().getChoices().isEmpty()) {
            return response.getBody().getChoices().get(0).getMessage().getContent();
        } else {
            throw new RuntimeException("DeepSeek request failed");
        }
    }
}