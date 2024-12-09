package ua.crypto.coolCrypto.bot;

import ua.crypto.coolCrypto.entities.User;
import ua.crypto.coolCrypto.services.UserService;

import java.util.Date;

public class MessageTimer implements Runnable{

    MainBot mainBot;

    public MessageTimer(MainBot mainBot) {
        this.mainBot = mainBot;
    }

    public static long DELAY = 8 * 60 * 60 * 1000;

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(DELAY);
                mainBot.onTimerTicked();
            } catch (InterruptedException e) {
                System.out.println("Error of waiting!");
            }
        }
    }

}
