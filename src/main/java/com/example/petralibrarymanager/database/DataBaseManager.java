package com.example.petralibrarymanager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {
    public static String defaultURL = "jdbc:postgresql://localhost:5432/petralms";
    public static Connection conn = null;

    // we store the user credentials once they have successfully login.
    public static String identifier, username, email, password, role, createdAt;

    public static boolean connect(String user, String password) {
        try {
            conn = DriverManager.getConnection(defaultURL, user, password);
            System.out.println("Connected!");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true; // note @Adith: if there are no exceptions, then the connection has been established.
    }

    public static boolean close() {
        try {
            System.out.println("closing the connection...");
            conn.close();

        } catch (SQLException e) {
            System.out.println("failed to close connection.");
            return false;
        }

        System.out.println("connection has been closed.");
        return true;
    }
}
