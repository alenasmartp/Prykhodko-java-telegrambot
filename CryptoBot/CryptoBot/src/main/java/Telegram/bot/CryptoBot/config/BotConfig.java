package Telegram.bot.CryptoBot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

public class BotConfig {
    @Configuration
    @Data
    @PropertySource("application.properties")
    public class BotConfiguration {
        @Value("${bot.name}")
        String botName;
        @Value("${bot.token}")
        String token;
    }
}
