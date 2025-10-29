package com.sarinhem.notification.telegram;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "telegram.bot")
public class TelegramProperties {
    /**
     * The bot token from BotFather (format: 123456:ABC-DEF...)
     */
    private String token;

    /**
     * Base API URL. You rarely need to change this, but it's configurable for tests or proxies.
     */
    private String apiUrl = "https://api.telegram.org";

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}