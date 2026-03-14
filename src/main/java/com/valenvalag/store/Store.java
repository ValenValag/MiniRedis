package com.valenvalag.store;

import com.valenvalag.models.ValueEntry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {
    public final static Map<String, ValueEntry> store = new ConcurrentHashMap<>();
}
