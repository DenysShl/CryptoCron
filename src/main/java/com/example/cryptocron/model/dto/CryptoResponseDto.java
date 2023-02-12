package com.example.cryptocron.model.dto;

import com.example.cryptocron.model.CryptName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CryptoResponseDto {
    private String id;
    private CryptName cryptName;
    private String fullName;
    private BigDecimal price;
    private LocalDateTime dateCreated;
}
