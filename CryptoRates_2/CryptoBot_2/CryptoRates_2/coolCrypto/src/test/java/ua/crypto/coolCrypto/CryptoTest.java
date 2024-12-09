package ua.crypto.coolCrypto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ua.crypto.coolCrypto.entities.Subscription;
import ua.crypto.coolCrypto.services.CryptoService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CryptoTest {

    CryptoService cryptoService = new CryptoService();

    @Test
    void testIsCryptoExists() {
        String assetId = "bitcoin";
        try {
            cryptoService.cryptoIsExist(assetId);
            assertTrue(true);
        }catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    void testIsCryptoNotExists() {
        String assetIdFalse = "bitcoikfdgdfn";
        try {
            cryptoService.cryptoIsExist(assetIdFalse);
            assertTrue(false);
        }catch (Exception e){
            assertTrue(true);
        }
    }

}
