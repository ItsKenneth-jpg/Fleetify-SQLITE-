package com.scc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public boolean registerUser(String username, String password, String role){

        String sql = "INSERT INTO users(username, password, roles, status) VALUES(?, ?, ?, 'PENDING')";

        try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role.toUpperCase());
            pstmt.executeUpdate();
            System.out.println("-> Successful Registration, Waiting for Approval!");
            return true;

        } catch (SQLException e) {
            System.err.println("Failed:"+ e.getMessage());
            return false;

        }


    }
    public boolean login(String username, String password) {
        // Query checks both credentials and whether the account is approved (not PENDING)
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password); // Note: In production, verify hashed passwords using BCrypt

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String status = rs.getString("status");
                    if ("PENDING".equalsIgnoreCase(status)) {
                        System.out.println("Error: Your account is pending admin approval.");
                        return false;
                    }
                    System.out.println("Login successful! Welcome, " + username + ".");
                    return true;
                } else {
                    System.out.println("Error: Invalid username or password.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error during login: " + e.getMessage());
            return false;
        }
    }
}
