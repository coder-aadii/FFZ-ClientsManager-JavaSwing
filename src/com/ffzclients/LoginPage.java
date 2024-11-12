package com.ffzclients;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class LoginPage extends JPanel {
    private MainFrame mainFrame;
    private DatabaseManager dbManager;
    private LoginManager loginManager;

    private JTextField userIdField; // Declare userIdField and passwordField as class members
    private JPasswordField passwordField;
    private JCheckBox rememberCheckbox;

    public LoginPage(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.dbManager = new DatabaseManager(); // Initialize DatabaseManager
        setLayout(new BorderLayout());

        // Load custom Satisfy font
        Font satisfyFont = loadFont("src/assets/fonts/Satisfy-Regular.ttf", Font.BOLD, 20);
        // Load Montserrat-VariableFont_wght font
        Font montserratVariableFont = loadFont("src/assets/fonts/Montserrat-VariableFont_wght.ttf", Font.BOLD, 12);

        // Custom panel to draw background image
        JPanel backgroundPanel = createBackgroundPanel();

        // Create a panel for the form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null); // Absolute positioning
        formPanel.setOpaque(false); // Make form panel transparent to show background

        JLabel rightLabel = new JLabel("Fly Fitness Zone Clients Manager");
        Font largerFont = satisfyFont.deriveFont(40f);
        rightLabel.setFont(largerFont);
        rightLabel.setForeground(Color.BLUE);
        rightLabel.setBounds(180, 300, 600, 60);
        formPanel.add(rightLabel);

        JLabel loginMessage = new JLabel("Log in to your account");
        Font loginMessageFont = montserratVariableFont.deriveFont(20f);
        loginMessage.setFont(loginMessageFont);
        loginMessage.setForeground(Color.BLUE);
        loginMessage.setBounds(310, 360, 400, 30);
        formPanel.add(loginMessage);

        // Greeting message
        JLabel greetingLabel = new JLabel("Welcome to FFZ Clients");
        greetingLabel.setFont(satisfyFont); // Use the custom Satisfy font
        greetingLabel.setForeground(Color.BLACK);
        greetingLabel.setBounds(1090, 120, 300, 30); // Adjust position to the right
        formPanel.add(greetingLabel);

        // Initialize LoginManager with username and password fields
        userIdField = createInputField(montserratVariableFont, formPanel);
        passwordField = new JPasswordField(20);
        passwordField.setBounds(1170, 250, 150, 25);
        formPanel.add(passwordField);

        rememberCheckbox = new JCheckBox("Remember me");
        rememberCheckbox.setBounds(1040, 290, 125, 20);
        rememberCheckbox.setFont(montserratVariableFont);
        rememberCheckbox.setOpaque(false); // Make checkbox background transparent
        rememberCheckbox.setForeground(Color.BLACK);
        formPanel.add(rememberCheckbox);

        JButton forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.setBounds(1170, 290, 150, 20);
        forgotPasswordButton.setFont(montserratVariableFont);
        formPanel.add(forgotPasswordButton);

        // Create buttons
        createButtons(montserratVariableFont, formPanel);

        // Add form panel on top of the background
        backgroundPanel.add(formPanel);
        add(backgroundPanel, BorderLayout.CENTER);

        // Initialize LoginManager
        loginManager = new LoginManager(userIdField, passwordField);
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

    private JTextField createInputField(Font montserratVariableFont, JPanel formPanel) {
        JLabel userIdLabel = new JLabel("User ID");
        userIdLabel.setForeground(Color.BLACK);
        userIdLabel.setFont(montserratVariableFont);
        userIdLabel.setBounds(1050, 200, 100, 20);
        formPanel.add(userIdLabel);

        JTextField userIdField = new JTextField(20);
        userIdField.setBounds(1170, 200, 150, 25);
        formPanel.add(userIdField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setFont(montserratVariableFont);
        passwordLabel.setBounds(1050, 250, 100, 20);
        formPanel.add(passwordLabel);

        return userIdField; // Return the userIdField for further use
    }

    private void createButtons(Font montserratVariableFont, JPanel formPanel) {
        JButton submitButton = new JButton("SUBMIT");
        submitButton.setFont(montserratVariableFont);
        submitButton.setBounds(1080, 340, 200, 30);
        formPanel.add(submitButton);

        // Action listener for the submit button
        submitButton.addActionListener((ActionEvent e) -> {
            String username = userIdField.getText();
            String password = new String(passwordField.getPassword());

            // Validate credentials
            if (dbManager.validateUser(username, password)) {
//                JOptionPane.showMessageDialog(LoginPage.this, "Login Successful!");

                // Save credentials if "Remember Me" is checked
                if (rememberCheckbox.isSelected()) {
                    loginManager.saveCredentials();
                }

                // Switch to Dashboard
                mainFrame.switchToDashboardPage(); // Make sure this line is executed
            } else {
                JOptionPane.showMessageDialog(LoginPage.this, "Invalid username or password.");
            }
        });

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setFont(montserratVariableFont);
        createAccountButton.setBounds(1105, 390, 150, 30);
        createAccountButton.addActionListener((ActionEvent e) -> mainFrame.switchToCreateAccountPage());
        formPanel.add(createAccountButton);
    }

    private Font loadFont(String path, int style, float size) {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(style, size);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return font;
    }
}
