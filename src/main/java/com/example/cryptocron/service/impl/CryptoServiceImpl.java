package com.example.cryptocron.service.impl;

import com.example.cryptocron.exception.ResourceNotFoundException;
import com.example.cryptocron.model.CryptName;
import com.example.cryptocron.model.Crypto;
import com.example.cryptocron.model.dto.external.LastPriceDto;
import com.example.cryptocron.repository.CryptoRepository;
import com.example.cryptocron.service.CryptoService;
import com.example.cryptocron.service.HttpClient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CryptoServiceImpl implements CryptoService {
    private final CryptoRepository repository;
    private final HttpClient httpClient;

    @Autowired
    public CryptoServiceImpl(CryptoRepository repository,
                             HttpClient httpClient) {
        this.repository = repository;
        this.httpClient = httpClient;
    }

    @Override
    public Crypto getByNameMinPrice(String name) {
        if (CryptName.valueOf(name).getValue().isEmpty()) {
            throw new ResourceNotFoundException("CryptName", "Name", name);
        }
        List<Crypto> cryptosMin = repository.findByCryptNameOrderByPriceAsc(name).orElseThrow(
                () -> new ResourceNotFoundException("Crypto", "Name", name)
        );
        return cryptosMin.get(0);
    }

    @Override
    public Crypto getByNameMaxPrice(String name) {
        if (CryptName.valueOf(name).getValue().isEmpty()) {
            throw new ResourceNotFoundException("CryptName", "Name", name);
        }
        List<Crypto> cryptosMax = repository.findByCryptNameOrderByPriceDesc(name).orElseThrow(
                () -> new ResourceNotFoundException("Crypto", "Name", name)
        );
        return cryptosMax.get(0);
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Override
    public void syncExternalCrypto() {
        log.info("syncExternalCryptos method was invoked at {}", LocalDateTime.now());

        for (CryptName cryptName : CryptName.values()) {
            LastPriceDto lastPriceDto = httpClient.get("https://cex.io/api/last_price/"
                    + cryptName.name() + "/USD", LastPriceDto.class);
            repository.insert(getCrypto(cryptName, lastPriceDto.getLprice()));
        }

        log.info("syncExternalCryptos method was ended at {}", LocalDateTime.now());
    }

    private Crypto getCrypto(CryptName cryptName, BigDecimal price) {
        return new Crypto(
                cryptName,
                price,
                LocalDateTime.now()
        );
    }
}
