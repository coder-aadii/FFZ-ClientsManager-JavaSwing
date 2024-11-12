package com.ffzclients;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import static com.ffzclients.MainFrame.montserratVariableFont;
import static com.ffzclients.MainFrame.satisfyFont;

public class CreateAccountWindow extends JPanel {
    private MainFrame mainFrame;
    private DatabaseManager dbManager; // Add DatabaseManager instance

    public CreateAccountWindow(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Initialize DatabaseManager
        String url = "jdbc:mysql://localhost:3306/fly_fitness_zone_db";
        String userId = "admin";
        String password = "root";
        dbManager = new DatabaseManager(url, userId, password);

        // Custom panel to draw background image
        JPanel backgroundPanel = createBackgroundPanel();

        // Create a panel for the account creation form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null); // Absolute positioning
        formPanel.setOpaque(false); // Make form panel transparent to show background

        JLabel label = new JLabel("Create New Account");
        label.setFont(satisfyFont);
        label.setForeground(Color.BLACK);
        label.setBounds(1060, 120, 300, 30);
        formPanel.add(label);

        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(montserratVariableFont);
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setBounds(1040, 200, 100, 20);
        formPanel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(1170, 200, 150, 25);
        formPanel.add(usernameField);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(montserratVariableFont);
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setBounds(1040, 250, 100, 20);
        formPanel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(1170, 250, 150, 25);
        formPanel.add(passwordField);

        // Confirm Password field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(montserratVariableFont);
        confirmPasswordLabel.setForeground(Color.BLACK);
        confirmPasswordLabel.setBounds(1040, 300, 110, 20);
        formPanel.add(confirmPasswordLabel);

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(1170, 300, 150, 25);
        formPanel.add(confirmPasswordField);

        // Create Account button
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setFont(montserratVariableFont);
        createAccountButton.setForeground(Color.BLACK);
        createAccountButton.setBounds(1080, 340, 200, 30);
        createAccountButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (password.equals(confirmPassword)) {
                    // Call the method to create admin account
                    dbManager.createAdminAccount(username, password);
                    JOptionPane.showMessageDialog(CreateAccountWindow.this, "Account Created Successfully!");
                    mainFrame.switchToLoginPage(); // Optionally switch back to login page
                } else {
                    JOptionPane.showMessageDialog(CreateAccountWindow.this, "Passwords do not match!");
                }
            }
        });
        formPanel.add(createAccountButton);

        // Back button to return to the login page
        JButton backButton = new JButton("Back to Login");
        backButton.setFont(montserratVariableFont);
        backButton.setForeground(Color.BLACK);
        backButton.setBounds(1105, 390, 150, 30);
        backButton.addActionListener(e -> mainFrame.switchToLoginPage());
        formPanel.add(backButton);

        // Add form panel on top of the background
        backgroundPanel.add(formPanel);
        add(backgroundPanel, BorderLayout.CENTER);
    }

    private JPanel createBackgroundPanel() {
        JPanel panel = new JPanel() {
            private Image bgImage;

            {
                try {
                    // Load the background image
                    bgImage = ImageIO.read(new File("src/assets/images/BG_Img.jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw background image
                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel.setLayout(new BorderLayout());
        return panel;
    }
}
