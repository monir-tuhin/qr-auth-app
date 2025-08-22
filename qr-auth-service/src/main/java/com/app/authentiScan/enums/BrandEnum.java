package com.app.authentiScan.enums;

public enum BrandEnum {
    WHITE_TONE("WT", "White Tone"),
    OSSUM("OM", "Ossum"),
    REALMAN("RM", "Realman"),
    FOGG("FG", "Fogg");

    private final String key;
    private final String value;

    BrandEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getEnumKey() {
        return key;
    }

    public String getEnumValue() {
        return value;
    }
}
