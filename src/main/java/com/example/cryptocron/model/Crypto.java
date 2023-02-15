package com.example.cryptocron.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "cryptos")
public class Crypto {
    @Id
    private String id;
    @DBRef(db = "crypto_names", lazy = true)
    @Indexed(unique = true)
    @Field(value = "name_crypto")
    private CryptoName cryptoName;
    private BigDecimal price;
    @Field(value = "date_created")
    private LocalDateTime dateCreated;

    public Crypto(CryptoName cryptoName,
                  BigDecimal price,
                  LocalDateTime dateCreated) {
        this.cryptoName = cryptoName;
        this.price = price;
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Crypto crypto = (Crypto) o;

        if (!Objects.equals(id, crypto.id)) {
            return false;
        }
        if (!Objects.equals(cryptoName, crypto.cryptoName)) {
            return false;
        }
        if (!Objects.equals(price, crypto.price)) {
            return false;
        }

        return Objects.equals(dateCreated, crypto.dateCreated);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cryptoName != null ? cryptoName.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Crypto{"
                + "id=" + id
                + ", name='" + cryptoName + '\''
                + ", price=" + price
                + ", dateCreated=" + dateCreated
                + '}';
    }
}
