package com.example.cryptocron.model;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum CryptName {
    BTC("Bitcoin"),
    ETH("Ethereum"),
    XRP("Ripple"),
    USDT("Tether");

    private static Map<String, CryptName> lookup
            = Arrays.stream(values())
            .collect(Collectors.toMap(Enum::name, Function.identity()));
    private String value;

    CryptName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CryptName getByName(String name) {
        return lookup.get(name);
    }
}
