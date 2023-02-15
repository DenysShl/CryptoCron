package com.example.cryptocron.repository;

import com.example.cryptocron.model.CryptoName;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CryptoNameRepository extends MongoRepository<CryptoName, String> {
    Optional<CryptoName> findCryptoNameByName(String name);
}
