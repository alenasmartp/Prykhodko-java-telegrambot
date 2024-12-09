package ua.crypto.coolCrypto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String assetId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    User user;

    public Subscription(String assetId, User user) {
        this.assetId = assetId;
        this.user = user;
    }

    public Subscription(String assetId) {
        this.assetId = assetId;
    }
}
