package com.scc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class initializeDAO {


    public static boolean registerVehicle(String id, String plateNumber, int capacity, String status) {
        String sql = "INSERT INTO vehicle (id, plate_number, capacity, status) VALUES (?, ?, ?, 'IDLE')";

        try (Connection conn = DatabaseConnection.getVehicleConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, plateNumber);
            pstmt.setInt(3, capacity);


            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Database Error registering vehicle: " + e.getMessage());
            return false;
        }
    }
    public static boolean registerDriver(String id, String name, String status){
        String sql = "INSERT INTO driver (id, name, status) VALUES (?, ?, 'IDLE')";

        try (Connection conn = DatabaseConnection.getDriverConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            
        }
    }
}