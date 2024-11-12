package com.ffzclients;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LoginManager {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginManager(JTextField usernameField, JPasswordField passwordField) {
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        loadCredentials(); // Load credentials when the LoginManager is created
    }

    public void saveCredentials() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("credentials.txt"))) {
            writer.write(username);
            writer.newLine();
            writer.write(password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader("credentials.txt"))) {
            String username = reader.readLine();
            String password = reader.readLine();
            // Set the fields with the loaded credentials
            usernameField.setText(username);
            passwordField.setText(password);
        } catch (IOException e) {
            // Handle the case where the file does not exist
            // Optionally, you can log a message or show a dialog
        }
    }
}
