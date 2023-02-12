package com.example.cryptocron.repository;

import com.example.cryptocron.model.Crypto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoRepository extends MongoRepository<Crypto, String> {
    Optional<List<Crypto>> findByCryptNameOrderByPriceDesc(String string);

    Optional<List<Crypto>> findByCryptNameOrderByPriceAsc(String name);
}
