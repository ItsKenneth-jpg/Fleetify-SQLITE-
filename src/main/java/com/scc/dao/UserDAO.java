package com.scc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public boolean registerUser(String username, String password, String role) {
        // Assuming regular users register here
        String sql = "INSERT INTO users(username, password, role, status) VALUES(?, ?, ?, 'PENDING')";

        try (Connection conn = DatabaseConnection.getUserConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role.toUpperCase());
            pstmt.executeUpdate();
            System.out.println("-> Successful Registration, Waiting for Approval!");
            return true;

        } catch (SQLException e) {
            System.err.println("Failed: " + e.getMessage());
            return false;
        }
    }

    public boolean login(String username, String password) {

        // --- STEP 1: Check the ADMIN table first ---
        String adminSql = "SELECT * FROM admin WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getUserConnection();
             PreparedStatement pstmt = conn.prepareStatement(adminSql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Found in the admin table!
                    System.out.println("Login Success! Welcome admin.");
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error checking admin: " + e.getMessage());
        }

        // --- STEP 2: If not found in admin, check the USERS table ---
        String userSql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getUserConnection();
             PreparedStatement pstmt = conn.prepareStatement(userSql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String status = rs.getString("status");

                    // Check if regular user account is pending approval
                    if ("PENDING".equalsIgnoreCase(status)) {
                        System.out.println("Error: Your account is pending admin approval.");
                        return false;
                    }

                    // Regular user is active/approved
                    System.out.println("Login successful! Welcome, " + username + ".");
                    return true;

                } else {
                    // Not found in admin OR users table
                    System.out.println("Error: Invalid username or password.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error checking user: " + e.getMessage());
            return false;
        }
    }
}