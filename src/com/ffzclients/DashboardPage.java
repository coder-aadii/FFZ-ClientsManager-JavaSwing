package com.ffzclients;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DashboardPage extends JPanel {
    private DatabaseManager dbManager;
    private JTextField nameField, phoneField, emailField;
    private JComboBox<String> fitnessTypeComboBox, feePackageComboBox;
    private JSpinner feeAmountSpinner;
    private JTable membersTable;
    private MembersTableModel tableModel;
    private JSpinner feePaidOnSpinner;

    public DashboardPage(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        setLayout(null);
        setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("Member Registration");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(200, 20, 400, 40);
        add(titleLabel);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBackground(new Color(255, 255, 255));
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "New Member Information"));
        inputPanel.setBounds(50, 70, 400, 400);
        add(inputPanel);

        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        String[] fitnessTypes = {"Yoga-Fitness", "Zumba-Fitness"};
        fitnessTypeComboBox = new JComboBox<>(fitnessTypes);
        String[] feePackages = {"One Month", "Three Months"};
        feePackageComboBox = new JComboBox<>(feePackages);
        feeAmountSpinner = new JSpinner(new SpinnerNumberModel(0.00, 0.00, 10000.00, 0.01));
        feePaidOnSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor feePaidEditor = new JSpinner.DateEditor(feePaidOnSpinner, "yyyy-MM-dd");
        feePaidOnSpinner.setEditor(feePaidEditor);

        // Set bounds for input fields
        nameField.setBounds(150, 30, 200, 25);
        phoneField.setBounds(150, 70, 200, 25);
        emailField.setBounds(150, 110, 200, 25);
        fitnessTypeComboBox.setBounds(150, 150, 200, 25);
        feePackageComboBox.setBounds(150, 190, 200, 25);
        feeAmountSpinner.setBounds(150, 230, 200, 25);
        feePaidOnSpinner.setBounds(150, 270, 200, 25);

        // Add labels for input fields
        inputPanel.add(new JLabel("Name:")).setBounds(20, 30, 100, 25);
        inputPanel.add(new JLabel("Phone:")).setBounds(20, 70, 100, 25);
        inputPanel.add(new JLabel("Email:")).setBounds(20, 110, 100, 25);
        inputPanel.add(new JLabel("Fitness Type:")).setBounds(20, 150, 100, 25);
        inputPanel.add(new JLabel("Fee Package:")).setBounds(20, 190, 100, 25);
        inputPanel.add(new JLabel("Fee Amount:")).setBounds(20, 230, 100, 25);
        inputPanel.add(new JLabel("Fee Paid On:")).setBounds(20, 270, 100, 25);

        // Add input fields to the input panel
        inputPanel.add(nameField);
        inputPanel.add(phoneField);
        inputPanel.add(emailField);
        inputPanel.add(fitnessTypeComboBox);
        inputPanel.add(feePackageComboBox);
        inputPanel.add(feeAmountSpinner);
        inputPanel.add(feePaidOnSpinner);

        JButton addButton = new JButton("Add Member");
        addButton.setBounds(150, 480, 150, 30);
        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(this::addMember);
        add(addButton);

        membersTable = new JTable();
        loadMembers();
        JScrollPane scrollPane = new JScrollPane(membersTable);
        scrollPane.setBounds(470, 70, 800, 400);
        add(scrollPane);

        JButton deleteButton = new JButton("Delete Selected Member");
        deleteButton.setBounds(470, 480, 200, 30);
        deleteButton.setBackground(new Color(255, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(this::deleteMember);
        add(deleteButton);

        JButton editButton = new JButton("Edit Selected Member");
        editButton.setBounds(680, 480, 200, 30);
        editButton.setBackground(new Color(0, 153, 0));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.addActionListener(this::editMember);
        add(editButton);

    }

    private void addMember(ActionEvent e) {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim().isEmpty() ? null : emailField.getText().trim();
        String fitnessType = (String) fitnessTypeComboBox.getSelectedItem();
        String feePackage = (String) feePackageComboBox.getSelectedItem();
        BigDecimal feeAmount = BigDecimal.valueOf((Double) feeAmountSpinner.getValue());

        // Retrieve dates from spinners
        Date feePaidOn = new Date(((java.util.Date) feePaidOnSpinner.getValue()).getTime());

        // Calculate the next fee payment date based on the selected fee package
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(feePaidOn);
        if ("One Month".equals(feePackage)) {
            calendar.add(Calendar.MONTH, 1);
        } else if ("Three Months".equals(feePackage)) {
            calendar.add(Calendar.MONTH, 3);
        }
        Date nextFeePaymentDate = new Date(calendar.getTimeInMillis());

        // Validate required fields
        if (name.isEmpty() || phone.isEmpty() || feeAmount.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(this, "Name, Phone, and Fee Amount must not be null or empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Debugging statements
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Adding member:");
        System.out.println("Name: " + name);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email); // This can be null
        System.out.println("Fitness Type: " + fitnessType);
        System.out.println("Fee Package: " + feePackage);
        System.out.println("Fee Amount: " + feeAmount);
        System.out.println("Fee Paid On: " + sdf.format(feePaidOn));
        System.out.println("Next Fee Payment Date: " + sdf.format(nextFeePaymentDate));

        try {
            dbManager.addMember(name, phone, email, new Date(System.currentTimeMillis()), fitnessType, feePackage, feeAmount, feePaidOn, nextFeePaymentDate);
            loadMembers();

            // Clear input fields after successful addition
            nameField.setText("");
            phoneField.setText("");
            emailField.setText("");
            fitnessTypeComboBox.setSelectedIndex(0);
            feePackageComboBox.setSelectedIndex(0);
            feeAmountSpinner.setValue(0.00);
            feePaidOnSpinner.setValue(new java.util.Date());

            JOptionPane.showMessageDialog(this, "Member added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to add member: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("Add Member button clicked.");
    }

    private void deleteMember(ActionEvent e) {
        int selectedRow = membersTable.getSelectedRow();
        if (selectedRow >= 0) {
            int memberId = (int) membersTable.getValueAt(selectedRow, 0); // Assuming first column is ID
            dbManager.deleteMember(memberId);
            loadMembers();
            JOptionPane.showMessageDialog(this, "Member deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a member to delete.", "Selection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editMember(ActionEvent e) {
        int selectedRow = membersTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Get the selected member's data
            int memberId = (int) membersTable.getValueAt(selectedRow, 0); // Assuming the first column is ID
            String name = (String) membersTable.getValueAt(selectedRow, 1);
            String phone = (String) membersTable.getValueAt(selectedRow, 2);
            String email = (String) membersTable.getValueAt(selectedRow, 3);
            String fitnessType = (String) membersTable.getValueAt(selectedRow, 4);
            String feePackage = (String) membersTable.getValueAt(selectedRow, 5);
            BigDecimal feeAmount = (BigDecimal) membersTable.getValueAt(selectedRow, 6);
            Date feePaidOn = (Date) membersTable.getValueAt(selectedRow, 7);

            // Populate the form with the selected member's data
            nameField.setText(name);
            phoneField.setText(phone);
            emailField.setText(email);
            fitnessTypeComboBox.setSelectedItem(fitnessType);
            feePackageComboBox.setSelectedItem(feePackage);
            feeAmountSpinner.setValue(feeAmount.doubleValue());
            feePaidOnSpinner.setValue(feePaidOn);

            // Show a confirmation dialog before updating the member
            int confirmation = JOptionPane.showConfirmDialog(this, "Do you want to save changes to this member?", "Confirm Edit", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                // Update the member in the database with new values
                try {
                    // Get updated values from the form
                    String updatedName = nameField.getText().trim();
                    String updatedPhone = phoneField.getText().trim();
                    String updatedEmail = emailField.getText().trim();
                    String updatedFitnessType = (String) fitnessTypeComboBox.getSelectedItem();
                    String updatedFeePackage = (String) feePackageComboBox.getSelectedItem();
                    BigDecimal updatedFeeAmount = BigDecimal.valueOf((Double) feeAmountSpinner.getValue());
                    Date updatedFeePaidOn = new Date(((java.util.Date) feePaidOnSpinner.getValue()).getTime());

                    // Calculate the new next fee payment date based on the updated fee package
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(updatedFeePaidOn);
                    if ("One Month".equals(updatedFeePackage)) {
                        calendar.add(Calendar.MONTH, 1);
                    } else if ("Three Months".equals(updatedFeePackage)) {
                        calendar.add(Calendar.MONTH, 3);
                    }
                    Date updatedNextFeePaymentDate = new Date(calendar.getTimeInMillis());

                    // Call the method to update the member in the database
                    dbManager.updateMember(memberId, updatedName, updatedPhone, updatedEmail, updatedFitnessType, updatedFeePackage, updatedFeeAmount, updatedFeePaidOn, updatedNextFeePaymentDate);
                    loadMembers(); // Reload the members table to reflect the changes
                    JOptionPane.showMessageDialog(this, "Member updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Clear the form after successful edit
                    nameField.setText("");
                    phoneField.setText("");
                    emailField.setText("");
                    fitnessTypeComboBox.setSelectedIndex(0);
                    feePackageComboBox.setSelectedIndex(0);
                    feeAmountSpinner.setValue(0.00);
                    feePaidOnSpinner.setValue(new java.util.Date());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Failed to update member: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a member to edit.", "Selection Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void loadMembers() {
        List<Member> members = dbManager.getAllMembers();
        tableModel = new MembersTableModel(members);
        membersTable.setModel(tableModel);
    }
}
