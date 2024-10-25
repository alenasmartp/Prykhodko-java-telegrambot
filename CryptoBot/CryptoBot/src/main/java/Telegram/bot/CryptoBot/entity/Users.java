package Telegram.bot.CryptoBot.entity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Users {
   private Long userId;
   private boolean isAdmin;
   private Date create_at;
   private boolean isActive;
}
