package Telegram.bot.CryptoBot.entity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Subscriptions {
    private Long subscriptionId;
    private Long userId;
    private String assetsId;
    private Date timeInterval;
}
