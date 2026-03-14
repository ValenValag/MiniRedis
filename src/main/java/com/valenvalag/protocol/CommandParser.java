package com.valenvalag.protocol;

import com.valenvalag.models.ValueEntry;

import static com.valenvalag.store.Store.store;

public class CommandParser {

    public static String processCommand(String input) {
        String[] parts = input.split(" ");
        String command = parts[0].toUpperCase();

        if (parts.length < 2) {
            return "ERROR: no key specified!";
        }
        String key = parts[1];

        switch (command) {
            case "SET" -> {
                if (parts.length < 3) {
                    return "ERROR: no value specified! Use SET <key> <value> <expiresIn>";
                }
                if (parts.length < 4) {
                    return "ERROR: no expires in time specified! Use SET <key> <value> <expiresIn>";
                }

                long ttlSeconds;
                try {
                    ttlSeconds = Long.parseLong(parts[3]);
                } catch (Exception e) {
                    return "ERROR: ttl must be a number";
                }

                long expiresAt = System.currentTimeMillis() + ttlSeconds * 1000;
                ValueEntry entry = new ValueEntry(parts[2], expiresAt);

                store.put(key, entry);
                return "OK";
            }
            case "GET" -> {
                ValueEntry entry = store.get(key);

                if (entry == null) {
                    return "NULL";
                }

                if (System.currentTimeMillis() > entry.expiresAt) {
                    store.remove(key);
                    System.out.println("- key expired: " + key);
                    return "NULL";
                }

                return entry.value;
            }
            case "DEL" -> {
                ValueEntry entry = store.get(key);

                if (entry == null) {
                    return "ERROR: Entry does not exist!";
                }

                store.remove(key);
                return "OK";

            }
            case "TTL" -> {
                ValueEntry entry = store.get(key);
                long timeLeft = Math.round((float) (entry.expiresAt - System.currentTimeMillis()) / 1000);

                return "" + timeLeft;
            }
            default -> {
                return "ERROR: unknown command!";
            }
        }
    }

}
