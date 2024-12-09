package ua.crypto.coolCrypto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.crypto.coolCrypto.entities.Subscription;
import ua.crypto.coolCrypto.entities.User;
import ua.crypto.coolCrypto.repos.SubscriptionRepo;
import ua.crypto.coolCrypto.repos.UserRepo;
import ua.crypto.coolCrypto.services.UserService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
class UserTest {


    private User user;



    @BeforeEach
    void setup() {
        user = new User("12345");
    }

    @Test
    void testChatId() {
        assertEquals("12345", user.getChatId());
    }


}

