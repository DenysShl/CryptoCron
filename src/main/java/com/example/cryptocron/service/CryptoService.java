package com.example.cryptocron.service;

import com.example.cryptocron.model.Crypto;

public interface CryptoService {
    Crypto getByNameMinPrice(String name);

    Crypto getByNameMaxPrice(String name);

    void syncExternalCrypto();
}
