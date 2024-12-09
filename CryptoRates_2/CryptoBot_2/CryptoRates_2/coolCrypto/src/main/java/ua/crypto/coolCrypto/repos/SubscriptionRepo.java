package ua.crypto.coolCrypto.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.crypto.coolCrypto.entities.Subscription;

import java.util.List;

public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {

    List<Subscription> findAllByUserId(Long userId);
}
