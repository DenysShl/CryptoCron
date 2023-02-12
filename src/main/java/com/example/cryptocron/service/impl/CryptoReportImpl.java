package com.example.cryptocron.service.impl;

import com.example.cryptocron.model.CryptName;
import com.example.cryptocron.model.dto.external.LastPriceDto;
import com.example.cryptocron.service.CryptoReport;
import com.example.cryptocron.service.CryptoService;
import java.io.IOException;
import java.io.Writer;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CryptoReportImpl implements CryptoReport {
    private final CryptoService cryptoService;

    public CryptoReportImpl(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @Override
    public void exportReportToCsv(Writer writer) {
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.MONGODB_CSV)) {
            for (CryptName cryptName : CryptName.values()) {
                csvPrinter.printRecord(
                        cryptName.name(),
                        cryptName.getValue(),
                        cryptoService.getByNameMinPrice(cryptName.name()).getPrice(),
                        cryptoService.getByNameMaxPrice(cryptName.name()).getPrice());
            }
        } catch (IOException e) {
            log.error("Error While writing CSV ", e);
        }
    }
}
