package com.example.cryptocron;

import com.example.cryptocron.model.Crypto;
import com.example.cryptocron.model.CryptoName;
import com.example.cryptocron.repository.CryptoNameRepository;
import com.example.cryptocron.repository.CryptoRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CryptoCronApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoCronApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(CryptoRepository repository,
                             CryptoNameRepository cryptoNameRepository) {
        return args -> {
            CryptoName btc = cryptoNameRepository.insert(new CryptoName("BTC",
                    "Bitcoin",
                    LocalDateTime.now()));
            CryptoName eth = cryptoNameRepository.insert(new CryptoName("ETH",
                    "Ethereum",
                    LocalDateTime.now()));
            CryptoName xrp = cryptoNameRepository.insert(new CryptoName("XRP",
                    "Ripple",
                    LocalDateTime.now()
            ));
            repository.insert(List.of(
                    new Crypto(btc,
                            BigDecimal.valueOf(19254.55),
                            LocalDateTime.now()
                    ),
                    new Crypto(eth,
                            BigDecimal.valueOf(1554.55),
                            LocalDateTime.now()
                    ),
                    new Crypto(xrp,
                            BigDecimal.valueOf(0.3816),
                            LocalDateTime.now()
                    )
            ));
        };
    }
}
