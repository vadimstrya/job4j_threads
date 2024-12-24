package ru.job4j.pool.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        final String username = user.getUsername();
        final String email = user.getEmail();
        final String subject = String.format("Notification %s to email %s", username, email);
        final String body = String.format("Add a new event to %s", username);

        pool.submit(() -> send(subject, body, email));
    }

    public void send(String subject, String body, String email) {
        System.out.printf("Send email to: %s, subject: %s, body: %s%n", email, subject, body);
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        EmailNotification sender = new EmailNotification();
        System.out.println("EmailNotification start!");
        for (int i = 1; i <= 16; i++) {
            sender.emailTo(new User("User" + i, "email" + i));
        }
        sender.close();
        System.out.println("EmailNotification finish!");
    }
}
