package com.example.cryptocron;

import com.example.cryptocron.model.CryptName;
import com.example.cryptocron.model.Crypto;
import com.example.cryptocron.repository.CryptoRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CryptoCronApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoCronApplication.class, args);
    }

    //    @Bean
    CommandLineRunner runner(CryptoRepository repository) {
        return args -> {
            repository.insert(List.of(
                    new Crypto(
                            CryptName.BTC,
                            BigDecimal.valueOf(19254.55),
                            LocalDateTime.now()
                    ),
                    new Crypto(
                            CryptName.ETH,
                            BigDecimal.valueOf(1554.55),
                            LocalDateTime.now()
                    ),
                    new Crypto(
                            CryptName.XRP,
                            BigDecimal.valueOf(0.3816),
                            LocalDateTime.now()
                    ),
                    new Crypto(
                            CryptName.BTC,
                            BigDecimal.valueOf(12254.55),
                            LocalDateTime.now()
                    ),
                    new Crypto(
                            CryptName.ETH,
                            BigDecimal.valueOf(1454.55),
                            LocalDateTime.now()
                    ),
                    new Crypto(
                            CryptName.XRP,
                            BigDecimal.valueOf(0.3116),
                            LocalDateTime.now()
                    )
            ));
        };
    }
}
