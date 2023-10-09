package com.example.tool;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class MainClass {

    // CLI
    // HTTP

    // java -jar ....jar
//    connect ...
//    send:queue1 message1
//    send:queue2 message2
//
    private  static Connection connection;
    private  static Channel channel;
    public static void main(String[] args) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        System.out.println("connect amqp://.... – to connect");
        System.out.println("send <currency> <price> – to send message");

        Scanner scanner;
        while (true){
            scanner = new Scanner(System.in);
            String command = scanner.nextLine();

            // connect amqp://guest:guest@localhost:5672
            if (command.startsWith("connect")){
                String url = command.split(" ")[1];
                connect(url);
            }

            // send USD 100.08
            if (command.startsWith("send")){
                String[] message = command.split(" ");
                sendMessage(message[1], message[2]);
            }

            if (command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("quit")){
                channel.close();
                connection.close();
                System.exit(0);
            }
        }

    }

    private static void sendMessage(String currency, String price) throws IOException {
        String finalMessage = "{\"currency\":\"" + currency + "\",\"price\": " + price + "}";
        channel.basicPublish("prices", "", null, finalMessage.getBytes());
    }

    private static void connect(String url) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(url);

        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare("prices", "fanout", true);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, "prices", "");
        System.out.println("Connected!");
    }
}
