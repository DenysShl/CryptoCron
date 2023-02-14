package com.example.cryptocron.service;

import com.example.cryptocron.model.Crypto;
import com.example.cryptocron.model.dto.CryptoResponse;

public interface CryptoService {
    Crypto getByNameMinPrice(String name);

    Crypto getByNameMaxPrice(String name);

    void syncExternalCrypto();

    CryptoResponse getAll(String name, int pageNo, int pageSize, String sortBy, String sortDir);
}
