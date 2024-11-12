package com.ffzclients;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private DatabaseManager dbManager;

    // Declare font variables as static
    public static Font satisfyFont;
    public static Font montserratVariableFont;

    public MainFrame() {
        loadFonts();

        setTitle("FFZ Clients");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen with title bar

        DatabaseManager dbManager = new DatabaseManager(); // Initialize here
        DashboardPage dashboardPage = new DashboardPage(dbManager); // Pass it to DashboardPage

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add the LoginPage and CreateAccountPage to the CardLayout
        cardPanel.add(new LoginPage(this), "LoginPage");
        cardPanel.add(new CreateAccountWindow(this), "CreateAccountPage");
        cardPanel.add(new DashboardPage(dbManager), "DashboardPage");


        add(cardPanel);
        cardLayout.show(cardPanel, "LoginPage"); // Show login page first
    }

    private void loadFonts() {
        try {
            satisfyFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/fonts/Satisfy-Regular.ttf"))
                    .deriveFont(Font.BOLD, 20);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(satisfyFont);

            montserratVariableFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/fonts/Montserrat-VariableFont_wght.ttf"))
                    .deriveFont(Font.BOLD, 12);
            ge.registerFont(montserratVariableFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToCreateAccountPage() {
        cardLayout.show(cardPanel, "CreateAccountPage");
    }

    public void switchToLoginPage() {
        cardLayout.show(cardPanel, "LoginPage");
    }

    public void switchToDashboardPage() {
        cardLayout.show(cardPanel, "DashboardPage");
    }


    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }

}
