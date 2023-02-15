package com.example.cryptocron.model.mapper.impl;

import com.example.cryptocron.model.Crypto;
import com.example.cryptocron.model.dto.CryptoResponseDto;
import com.example.cryptocron.model.mapper.MapperToDto;
import org.springframework.stereotype.Component;

@Component
public class CryptoMapper implements MapperToDto<CryptoResponseDto, Crypto> {

    @Override
    public CryptoResponseDto toDto(Crypto crypto) {
        return new CryptoResponseDto(
                crypto.getId(),
                crypto.getCryptoName(),
                crypto.getCryptoName().getDescription(),
                crypto.getPrice(),
                crypto.getDateCreated()
        );
    }
}
