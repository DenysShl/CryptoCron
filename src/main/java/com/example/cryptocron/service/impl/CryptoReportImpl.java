package com.example.cryptocron.service.impl;

import com.example.cryptocron.model.CryptoName;
import com.example.cryptocron.repository.CryptoNameRepository;
import com.example.cryptocron.service.CryptoReport;
import com.example.cryptocron.service.CryptoService;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CryptoReportImpl implements CryptoReport {
    private final CryptoService cryptoService;
    private final CryptoNameRepository cryptoNameRepository;

    public CryptoReportImpl(CryptoService cryptoService,
                            CryptoNameRepository cryptoNameRepository) {
        this.cryptoService = cryptoService;
        this.cryptoNameRepository = cryptoNameRepository;
    }

    @Override
    public void exportReportToCsv(Writer writer) {
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.MONGODB_CSV)) {
            List<CryptoName> cryptoNames = cryptoNameRepository.findAll();
            for (CryptoName cryptoName : cryptoNames) {
                csvPrinter.printRecord(
                        cryptoName.getName(),
                        cryptoName.getDescription(),
                        cryptoService.getByNameMinPrice(cryptoName.getName()).getPrice(),
                        cryptoService.getByNameMaxPrice(cryptoName.getName()).getPrice());
            }
        } catch (IOException e) {
            log.error("Error While writing CSV ", e);
        }
    }
}
