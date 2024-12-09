package ua.crypto.coolCrypto.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.crypto.coolCrypto.entities.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByChatId(String chatId);
}
