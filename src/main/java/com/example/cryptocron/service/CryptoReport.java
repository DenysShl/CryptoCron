package com.example.cryptocron.service;

import java.io.Writer;

public interface CryptoReport {
    void exportReportToCsv(Writer writer);
}
