package ua.crypto.coolCrypto.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import ua.crypto.coolCrypto.entities.Subscription;
import ua.crypto.coolCrypto.entities.User;
import ua.crypto.coolCrypto.exceptions.InvalidCryptocurrencyException;
import ua.crypto.coolCrypto.repos.SubscriptionRepo;
import ua.crypto.coolCrypto.repos.UserRepo;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    SubscriptionRepo subscriptionRepo;

    @Autowired
    CryptoService cryptoService;

    public User createNewUser(String chatId){
        for (User user : findAllUsers()){
            if(user.getChatId().equals(chatId)){
                user.setActive(true);
                userRepo.save(user);
                return user;
            }
        }
        return userRepo.save(new User(chatId));
    }

    public User findByChatId(String chatId) {
        return userRepo.findByChatId(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("User with "+ chatId + " wasn't found"));
    }

    @Transactional
    public void addSubscription(String chatId, String assetId) {
        User user = findByChatId(chatId);
        for(Subscription sub : user.getSubscriptions()) {
            if(sub.getAssetId().equals(assetId)) {
                return;
            }
        }
        Subscription subscription = new Subscription(assetId);

        subscription.setUser(user);
        subscriptionRepo.save(subscription);
    }


    @Transactional
    public void removeSubscription(String chatId, String assetId) {
        User user = findByChatId(chatId);
        List<Subscription> subscriptions = subscriptionRepo.findAllByUserId(user.getId());


        System.out.println("START");
        for (Subscription subscription : subscriptions) {
            System.out.println(subscription.getAssetId());
        }
        for (Subscription subscription : subscriptions) {
            if(subscription.getAssetId().equals(assetId)) {
                subscriptionRepo.delete(subscription);
            }
        }
    }

    public List<User> findAllUsers(){
        return userRepo.findAll();
    }

    public User findByUserId(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with "+ userId + " wasn't found"));
    }

    public void setUserIncative(String chatId) {
        User user = findByChatId(chatId);
        user.setActive(false);
        userRepo.save(user);
    }

    public List<Subscription> findSubsByUserId(Long id) {
        return subscriptionRepo.findAllByUserId(id);
    }
}
