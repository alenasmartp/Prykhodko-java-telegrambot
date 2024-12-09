package ua.crypto.coolCrypto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ua.crypto.coolCrypto.bot.MainBot;
import ua.crypto.coolCrypto.repos.UserRepo;
import ua.crypto.coolCrypto.services.UserService;

@SpringBootApplication
public class CoolCryptoApplication {


	public static void main(String[] args) {
		SpringApplication.run(CoolCryptoApplication.class, args);

    }

    @Bean
    public TelegramBotsApi telegramBotsApi(MainBot myTelegramBot) {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(myTelegramBot);
            return api;
        } catch (TelegramApiException e) {
            throw new RuntimeException("Ошибка регистрации бота", e);
        }
    }
}
