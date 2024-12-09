package ua.crypto.coolCrypto.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.crypto.coolCrypto.entities.Subscription;
import ua.crypto.coolCrypto.exceptions.InvalidCryptocurrencyException;
import ua.crypto.coolCrypto.exceptions.ServiceUnavailableException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Set;

@Service
public class CryptoService {

    public String getInfoAboutAsset(String assetId){
        try {
            testConnection();
        }catch (Exception e){
            return "Sorry service is current unavailable";
        }
        String url = "https://api.coincap.io/v2/assets/"+assetId.toLowerCase();

        ResponseEntity<String> response = getRequest(url);

        String info =
                "───── ◉ ─────\n" +
                "Asset Id: " + assetId + "\n";

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.getBody());
            info += "Crypto name: " + jsonNode.get("data").get("name").asText() + "\n";
            info += "Price: " + jsonNode.get("data").get("priceUsd").asText() + "$" + "\n";
        }catch (Exception e){
            e.printStackTrace();
        }

        return info;
    }

    public boolean cryptoIsExist(String assetId) throws InvalidCryptocurrencyException, ServiceUnavailableException {
        testConnection();
        String url = "https://api.coincap.io/v2/assets/"+assetId.toLowerCase();

        try {
            ResponseEntity<String> response = getRequest(url);
            System.out.println(response.getStatusCode());
            return true;
        }catch (Exception e){
            throw new InvalidCryptocurrencyException("This asset does not exist");
        }
    }

    public String getInfoAboutSubscription(Subscription subscription ){
        return getInfoAboutAsset(subscription.getAssetId());
    }

    public String getInfoAboutSubscriptions(List<Subscription> subscriptions){
        String response = "";
        for (Subscription subscription : subscriptions){
            response += getInfoAboutSubscription(subscription);
        }
        return response;
    }

    private ResponseEntity<String> getRequest(String url){
        // Создаем объект RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForEntity(url, String.class);
    }

    private void testConnection() throws ServiceUnavailableException {
        try {
            URL url = new URL("https://api.coincap.io");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            System.out.println(responseCode);

        } catch (Exception e) {
            throw new ServiceUnavailableException("Couldn't connect to Coincap");
        }
    }

}
