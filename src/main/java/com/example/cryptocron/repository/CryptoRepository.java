package com.example.cryptocron.repository;

import com.example.cryptocron.model.Crypto;
import com.example.cryptocron.model.CryptoName;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoRepository extends MongoRepository<Crypto, String> {
    List<Crypto> findByCryptoNameOrderByPriceDesc(CryptoName cryptoName);

    List<Crypto> findByCryptoNameOrderByPriceAsc(CryptoName cryptoName);

    Page<Crypto> findAllByCryptoName(CryptoName cryptoName, Pageable pageable);
}
