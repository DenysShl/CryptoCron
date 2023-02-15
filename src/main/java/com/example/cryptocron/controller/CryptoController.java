package com.example.cryptocron.controller;

import com.example.cryptocron.model.dto.CryptoResponse;
import com.example.cryptocron.model.dto.CryptoResponseDto;
import com.example.cryptocron.model.mapper.impl.CryptoMapper;
import com.example.cryptocron.service.CryptoReport;
import com.example.cryptocron.service.CryptoService;
import com.example.cryptocron.utils.AppConstants;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/cryptocurrencies")
public class CryptoController {
    private final CryptoService cryptoService;
    private final CryptoMapper mapper;
    private final CryptoReport report;

    @Autowired
    public CryptoController(CryptoService cryptoService,
                            CryptoMapper mapper,
                            CryptoReport report) {
        this.cryptoService = cryptoService;
        this.mapper = mapper;
        this.report = report;
    }

    @GetMapping("/minprice")
    public ResponseEntity<CryptoResponseDto> getByNameMinPrice(
            @RequestParam("name") String name) {
        return ResponseEntity.ok(
                mapper.toDto(cryptoService.getByNameMinPrice(name))
        );
    }

    @GetMapping("/maxprice")
    public ResponseEntity<CryptoResponseDto> getByNameMaxPrice(
            @RequestParam("name") String name) {
        return ResponseEntity.ok(
                mapper.toDto(cryptoService.getByNameMaxPrice(name))
        );
    }

    @GetMapping(path = "/csv", produces = "text/csv")
    public ResponseEntity<String> createCsv(HttpServletResponse servletResponse)
            throws IOException {
        servletResponse.addHeader("Content-Disposition",
                "attachment; filename=\"cryptocurrencies.csv\"");
        report.exportReportToCsv(servletResponse.getWriter());
        return ResponseEntity.ok("Report created successes");
    }

    @GetMapping
    public ResponseEntity<CryptoResponse> getAll(
            @RequestParam("name") String name,
            @RequestParam(
                    value = "page",
                    defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(
                    value = "size",
                    defaultValue = AppConstants.DEFAULT_PAGE_SIZE_ELEMENT,
                    required = false) int pageSize,
            @RequestParam(
                    value = "sortBy",
                    defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(
                    value = "sortDir",
                    defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir) {
        return ResponseEntity.ok(cryptoService.getAll(name, pageNo, pageSize, sortBy, sortDir));
    }
}
