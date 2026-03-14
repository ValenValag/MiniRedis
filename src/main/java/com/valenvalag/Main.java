package com.valenvalag;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    private static Map<String, ValueEntry> store = new ConcurrentHashMap<>();
    private final static int PORT = 6379;

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("MiniRedis running on port " + PORT);

        while (true) {
            Socket client = server.accept();
            new Thread(() -> {
                try {
                    handleClient(client);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void handleClient(Socket client) throws Exception {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(client.getInputStream())
        );

        PrintWriter writer = new PrintWriter(
                client.getOutputStream(), true
        );

        String line;
        while ((line = reader.readLine()) != null) {
            String response = processCommand(line);
            writer.println(response);
        }

        client.close();
    }

    private static String processCommand(String input) {
        String[] parts = input.split(" ");
        String command = parts[0].toUpperCase();
        String key = parts[1];

        if (key == null) {
            return "ERROR: no key specified!";
        }

        switch (command) {
            case "SET" -> {
                if (parts[2] == null) {
                    return "ERROR: no value specified! Use SET <key> <value> <expiresIn>";
                }
                if (parts[3] == null) {
                    return "ERROR: no expires in time specified! Use SET <key> <value> <expiresIn>";
                }

                long ttlSeconds = Long.parseLong(parts[3]);
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
            default -> {
                return "ERROR: unknown command!";
            }
        }
    }

}