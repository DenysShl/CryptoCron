package com.example.cryptocron.service.impl;

import com.example.cryptocron.exception.ResourceNotFoundException;
import com.example.cryptocron.model.CryptName;
import com.example.cryptocron.model.Crypto;
import com.example.cryptocron.model.dto.CryptoResponse;
import com.example.cryptocron.model.dto.CryptoResponseDto;
import com.example.cryptocron.model.dto.external.LastPriceDto;
import com.example.cryptocron.model.mapper.impl.CryptoMapper;
import com.example.cryptocron.repository.CryptoRepository;
import com.example.cryptocron.service.CryptoService;
import com.example.cryptocron.service.HttpClient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CryptoServiceImpl implements CryptoService {
    private final CryptoRepository cryptoRepository;
    private final HttpClient httpClient;
    private final CryptoMapper cryptoMapper;

    @Autowired
    public CryptoServiceImpl(CryptoRepository cryptoRepository,
                             HttpClient httpClient,
                             CryptoMapper cryptoMapper) {
        this.cryptoRepository = cryptoRepository;
        this.httpClient = httpClient;
        this.cryptoMapper = cryptoMapper;
    }

    @Override
    public Crypto getByNameMinPrice(String name) {
        try {
            CryptName byName = CryptName.getByName(name);
            return cryptoRepository.findByCryptNameOrderByPriceAsc(byName).get(0);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new ResourceNotFoundException("Crypto", "Name", name);
        }
    }

    @Override
    public Crypto getByNameMaxPrice(String name) {
        try {
            CryptName byName = CryptName.getByName(name);
            return cryptoRepository.findByCryptNameOrderByPriceDesc(byName).get(0);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new ResourceNotFoundException("Crypto", "Name", name);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Override
    public void syncExternalCrypto() {
        log.info("syncExternalCryptos method was invoked at {}", LocalDateTime.now());

        for (CryptName cryptName : CryptName.values()) {
            LastPriceDto lastPriceDto = httpClient.get("https://cex.io/api/last_price/"
                    + cryptName.name() + "/USD", LastPriceDto.class);
            cryptoRepository.insert(getCrypto(cryptName, lastPriceDto.getLprice()));
        }

        log.info("syncExternalCryptos method was ended at {}", LocalDateTime.now());
    }

    @Override
    public CryptoResponse getAll(String name,
                                 int pageNo,
                                 int pageSize,
                                 String sortBy,
                                 String sortDir) {
        CryptName byName = CryptName.getByName(name);
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Crypto> cryptos = cryptoRepository.findAllByCryptName(byName, pageable);
        List<Crypto> cryptoList = cryptos.getContent();

        List<CryptoResponseDto> content = cryptoList.stream()
                .map(cryptoMapper::toDto)
                .collect(Collectors.toList());

        return getCryptoResponse(cryptos, content);
    }

    private CryptoResponse getCryptoResponse(
            Page<Crypto> cryptos,
            List<CryptoResponseDto> content) {
        CryptoResponse cryptoResponse = new CryptoResponse();
        cryptoResponse.setContent(content);
        cryptoResponse.setPageNo(cryptos.getNumber());
        cryptoResponse.setPageSize(cryptos.getSize());
        cryptoResponse.setTotalElements(cryptos.getTotalElements());
        cryptoResponse.setTotalPages(cryptos.getTotalPages());
        cryptoResponse.setLast(cryptos.isLast());
        return cryptoResponse;
    }

    private Crypto getCrypto(CryptName cryptName, BigDecimal price) {
        return new Crypto(
                cryptName,
                price,
                LocalDateTime.now()
        );
    }
}
