package com.valenvalag;

public class ValueEntry {
    String value;
    long expiresAt;

    ValueEntry (String value, long expiresAt) {
        this.value = value;
        this.expiresAt = expiresAt;
    }
}
