/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.sql.*;

/**
 * Helper class for managing database connections and queries.
 * Author: ASUS
 */
public class DBHelper {

    private static Connection koneksi;

    // Method untuk membuka koneksi ke database
    public static void bukaKoneksi() {
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/dbperpus"; // URL database
                String user = "root"; // Username database
                String password = ""; // Password database

                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, user, password);
            } catch (SQLException t) {
                System.out.println("Error koneksi: " + t.getMessage());
            }
        }
    }

    // Method untuk menjalankan query INSERT dan mendapatkan ID yang dihasilkan
    public static int insertQueryGetId(String query) {
        bukaKoneksi();
        int result = -1;

        try {
            Statement stmt = koneksi.createStatement();
            int num = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                result = rs.getInt(1); // Mendapatkan ID yang dihasilkan
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // Method untuk menjalankan query INSERT, UPDATE, atau DELETE
    public static boolean executeQuery(String query) {
        bukaKoneksi();
        boolean result = false;

        try {
            Statement stmt = koneksi.createStatement();
            stmt.executeUpdate(query);
            result = true;

            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // Method untuk menjalankan query SELECT dan mendapatkan hasilnya
    public static ResultSet selectQuery(String query) {
        bukaKoneksi();
        ResultSet rs = null;

        try {
            Statement stmt = koneksi.createStatement();
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }
}
