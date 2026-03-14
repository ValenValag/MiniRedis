package com.valenvalag.models;

public class ValueEntry {
    public String value;
    public long expiresAt;

    public ValueEntry(String value, long expiresAt) {
        this.value = value;
        this.expiresAt = expiresAt;
    }
}
