package com.example.cryptocron.repository;

import com.example.cryptocron.model.CryptName;
import com.example.cryptocron.model.Crypto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoRepository extends MongoRepository<Crypto, String> {
    List<Crypto> findByCryptNameOrderByPriceDesc(String string);

    List<Crypto> findByCryptNameOrderByPriceAsc(String name);

    Page<Crypto> findAllByCryptName(CryptName cryptName, Pageable pageable);
}
