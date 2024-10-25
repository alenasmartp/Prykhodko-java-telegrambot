package Telegram.bot.CryptoBot.service;

import jakarta.annotation.PostConstruct;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Component
public class TelegramBot extends TelegramLongPollingBot {

        private final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        @Value("${bot.token}")
        private String botToken;

        @PostConstruct
        private void init() throws TelegramApiException {
            telegramBotsApi.registerBot(this);
        }

        public BotComponent() throws TelegramApiException {}

        @Override
        public void onUpdateReceived(Update update) {
            System.out.println(update.getMessage().getText());
        }

        @Override
        public String getBotName() {
            return "botName";
        }

        @Override
        public String getBotToken() {
            return botToken;
        }
    }
}
