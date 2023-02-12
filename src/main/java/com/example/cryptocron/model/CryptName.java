package com.example.cryptocron.model;

public enum CryptName {
    BTC("Bitcoin"),
    ETH("Ethereum"),
    XRP("Ripple"),
    USDT("Tether");

    private String value;

    CryptName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
