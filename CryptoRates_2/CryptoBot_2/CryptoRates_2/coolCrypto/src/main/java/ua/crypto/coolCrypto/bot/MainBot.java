package ua.crypto.coolCrypto.bot;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.crypto.coolCrypto.entities.User;
import ua.crypto.coolCrypto.exceptions.ServiceUnavailableException;
import ua.crypto.coolCrypto.services.CryptoService;
import ua.crypto.coolCrypto.services.UserService;

@Component
public class MainBot extends TelegramLongPollingBot {

    private final String BOT_USERNAME = "Prykhodko_java_test_bot";

    private UserService userService;
    private CryptoService cryptoService;
    Thread messageTimer;

    public MainBot(UserService userService, CryptoService cryptoService) {
        super("7869074983:AAG1JguZwcET3fbZ4dnnDyvKIQibqjn630o");
        this.userService = userService;
        this.cryptoService = cryptoService;
        messageTimer = new Thread(new MessageTimer(this));
        messageTimer.start();
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();



            switch (messageText.split(" ")[0]) {
                case "/start":
                    handleStartCommand(chatId);
                    break;
                case "/addSubscription":
                    try {
                        if (!userService.findByChatId(chatId).isActive()){
                            sendMessage(chatId, "You are not active! Send /start to start.");
                            return;
                        }
                    }catch (Exception e){
                        sendMessage(chatId, e.getMessage());
                        return;
                    }
                    if (messageText.split(" ").length < 2){
                        sendMessage(chatId, "Please enter asset name.");
                        return;
                    }
                    if (messageText.split(" ")[1].isBlank()) {
                        sendMessage(chatId, "Please enter asset name.");
                        return;
                    }
                    handleAddSubscription(chatId, messageText);
                    break;
                case "/removeSubscription":
                    try {
                        if (!userService.findByChatId(chatId).isActive()){
                            sendMessage(chatId, "You are not active! Send /start to start.");
                            return;
                        }
                    }catch (Exception e){
                        sendMessage(chatId, e.getMessage());
                        return;
                    }
                    handleRemoveSubscription(chatId, messageText);
                    break;
                case "/getAllSubscriptions":
                    try {
                        if (!userService.findByChatId(chatId).isActive()){
                            sendMessage(chatId, "You are not active! Send /start to start.");
                            return;
                        }
                    }catch (Exception e){
                        sendMessage(chatId, e.getMessage());
                        return;
                    }
                    handleGetAllSubscriptions(chatId);
                    break;
                case "/getAllUsers":
                    if (!userService.findByChatId(chatId).isAdmin()) break;
                    handleGetAllUsers(chatId);
                    break;
                case "/stop":
                    handleStopBot(chatId);
                    break;
                default:
                    sendMessage(chatId, "The command is not recognized. Available commands: /start, \n/addSubscription {crypto name}, \nexample: /addSubscription bitcoin, \n/getAllSubscriptions, \n/removeSubscription {crypto name}, \nexample: /removeSubscription bitcoin, \n/stop");
            }

        }
    }


    private void handleStopBot(String chatId) {
        userService.setUserIncative(chatId);
        sendMessage(chatId, "Your bot is inactive. If you want to use the bot again, enter /start");
    }

    private void handleGetAllUsers(String chatId) {
        StringBuilder result = new StringBuilder();
        for (User user : userService.findAllUsers()){
            result.append(user.toString() + "\n");
        }

        sendMessage(chatId, "Users list: " + result.toString());
    }

    public void onTimerTicked(){
        for (User user : userService.findAllUsers()){
            if (!user.isActive()) continue;
            String response = cryptoService.getInfoAboutSubscriptions(
                    user.getSubscriptions().stream().toList()
            );
            sendMessage(user.getChatId(), response);
        }
    }


    private void handleGetAllSubscriptions(String chatId) {
        String response = cryptoService.getInfoAboutSubscriptions(
                userService.findSubsByUserId(userService.findByChatId(chatId).getId())
        );

        sendMessage(chatId, response);
    }

    private void handleRemoveSubscription(String chatId, String messageText) {
        String assetId = messageText.split(" ")[1].toLowerCase();
        userService.removeSubscription(chatId, assetId);
        sendMessage(chatId, "Subscribe to " + assetId + " was removed!");
    }

    private void handleAddSubscription(String chatId, String messageText) {
        String assetId = messageText.split(" ")[1].toLowerCase();
        try {
            cryptoService.cryptoIsExist(assetId);
        }catch (ServiceUnavailableException serviceUnavailableException){
            sendMessage(chatId, "Sorry service is current unavailable");
            return;
        } catch (Exception e){
            sendMessage(chatId, "Crypto is unavailable " + assetId + "!");
            return;
        }
        userService.addSubscription(chatId, assetId);
        sendMessage(chatId, "Subscribe to " + assetId + " was added!\n" + cryptoService.getInfoAboutAsset(assetId));

    }

    private void handleStartCommand(String chatId) {
        userService.createNewUser(chatId);
        sendMessage(chatId, "Welcome to CryptoRatesBot! \nEnable commands: \n/addSubscription {crypto name}, \nexample: /addSubscription bitcoin, \n/getAllSubscriptions, \n/removeSubscription {crypto name}, \nexample: /removeSubscription bitcoin, \n/stop");
    }


    private void sendMessage(final String chatId, final String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
