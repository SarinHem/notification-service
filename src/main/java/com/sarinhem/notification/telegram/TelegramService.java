package com.sarinhem.notification.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TelegramService {
    private static final Logger log = LoggerFactory.getLogger(TelegramService.class);

    private final TelegramProperties props;
    private final RestTemplate rest;

    public TelegramService(TelegramProperties props, RestTemplate rest) {
        this.props = props;
        this.rest = rest;
    }

    /**
     * Sends a text message to a chatId using the Bot API.
     *
     * @param chatId the Telegram chat id (e.g. 123456789)
     * @param text   the message text
     * @return true if Telegram returned an HTTP 2xx response (success), false otherwise
     */
    public boolean sendMessage(String chatId, String text) {
        if (props.getToken() == null || props.getToken().isBlank()) {
            log.warn("Telegram bot token is not configured (telegram.bot.token). Message not sent.");
            return false;
        }
        String url = String.format("%s/bot%s/sendMessage", props.getApiUrl(), props.getToken());

        Map<String, Object> payload = new HashMap<>();
        payload.put("chat_id", chatId);
        payload.put("text", text);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> resp = rest.postForEntity(url, entity, String.class);
            boolean ok = resp.getStatusCode().is2xxSuccessful();
            if (!ok) {
                log.warn("Telegram sendMessage returned non-2xx: {} Body: {}", resp.getStatusCode(), resp.getBody());
            }
            return ok;
        } catch (RestClientException ex) {
            log.warn("Failed to send message via Telegram API", ex);
            return false;
        }
    }
}