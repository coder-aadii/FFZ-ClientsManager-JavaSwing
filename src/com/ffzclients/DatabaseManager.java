package com.ffzclients;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class DatabaseManager {
    private String url;
    private String userId;
    private String password;
    private Connection connection;

    // Default constructor using default credentials
    public DatabaseManager() {
        this.url = "jdbc:mysql://localhost:3306/fly_fitness_zone_db";
        this.userId = "root";
        this.password = "admin123";
        connect();
    }

    // Constructor with parameters for flexible database connection
    public DatabaseManager(String url, String userId, String password) {
        this.url = url;
        this.userId = userId;
        this.password = password;
        connect();
    }

    // Establishes the database connection
    private void connect() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(url, userId, password);
            System.out.println("Database connected successfully.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("MySQL JDBC Driver not found.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database.");
        }
    }

    public boolean validateUser(String username, String password) {
        String query = "SELECT * FROM admins WHERE username = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // returns true if a matching user is found
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createAdminAccount(String username, String password) {
        String query = "INSERT INTO admins (username, password) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            String hashedPassword = hashPassword(password); // Implement this method using a hashing library
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMember(String name, String phone, String email, Date registrationDate,
                          String fitnessType, String feePackage, BigDecimal feeAmount,
                          Date feePaidOn, Date nextFeePaymentDate) {
        String query = "INSERT INTO members (name, phone, email, registration_date, " +
                "fitness_type, fee_package, fee_amount, fee_paid_on, next_fee_payment_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, email);
            statement.setDate(4, registrationDate);
            statement.setString(5, fitnessType);
            statement.setString(6, feePackage);
            statement.setBigDecimal(7, feeAmount);
            statement.setDate(8, feePaidOn);
            statement.setDate(9, nextFeePaymentDate);
            statement.executeUpdate();
            System.out.println("Member added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String query = "SELECT * FROM members";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Member member = new Member();
                member.setId(resultSet.getInt("id"));
                member.setName(resultSet.getString("name"));
                member.setPhone(resultSet.getString("phone"));
                member.setEmail(resultSet.getString("email"));
                member.setRegistrationDate(resultSet.getDate("registration_date"));
                member.setFitnessType(resultSet.getString("fitness_type"));
                member.setFeePackage(resultSet.getString("fee_package"));
                member.setFeeAmount(resultSet.getBigDecimal("fee_amount"));
                member.setFeePaidOn(resultSet.getDate("fee_paid_on"));
                member.setNextFeePaymentDate(resultSet.getDate("next_fee_payment_date"));
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    public void deleteMember(int memberId) {
        String query = "DELETE FROM members WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, memberId);
            preparedStatement.executeUpdate();
            System.out.println("Member deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMember(int id, String name, String phone, String email, String fitnessType, String feePackage, BigDecimal feeAmount, Date feePaidOn, Date nextFeePaymentDate) throws Exception {
        String query = "UPDATE members SET name = ?, phone = ?, email = ?, fitness_type = ?, fee_package = ?, fee_amount = ?, fee_paid_on = ?, next_fee_payment_date = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, email);
            stmt.setString(4, fitnessType);
            stmt.setString(5, feePackage);
            stmt.setBigDecimal(6, feeAmount);
            stmt.setDate(7, feePaidOn);
            stmt.setDate(8, nextFeePaymentDate);
            stmt.setInt(9, id);
            stmt.executeUpdate();
        }
    }


    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to hash password (you should implement this)
    private String hashPassword(String password) {
        // Implement password hashing logic here (e.g., using bcrypt)
        return password;
    }
}
