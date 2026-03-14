package com.valenvalag.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static com.valenvalag.protocol.CommandParser.processCommand;

public class MiniRedisServer {

    private final static int PORT = 6379;

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("MiniRedis running on port " + PORT);

        while (true) {
            try {
                Socket client = server.accept();
                new Thread(() -> {
                    try {
                        handleClient(client);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void handleClient(Socket client) throws Exception {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(client.getInputStream())
        );

        PrintWriter writer = new PrintWriter(
                client.getOutputStream(), true
        );

        System.out.println("- client connected");

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("- command received");
            String response = processCommand(line);
            writer.println(response);
        }

        client.close();
    }



}