package com.example.cryptocron.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(value = "crypto_names")
public class CryptoName {
    @Id
    private String id;
    private String name;
    private String description;
    private LocalDateTime createAt;

    public CryptoName(String name,
                      String description,
                      LocalDateTime createAt) {
        this.name = name;
        this.description = description;
        this.createAt = LocalDateTime.now();
    }
}
