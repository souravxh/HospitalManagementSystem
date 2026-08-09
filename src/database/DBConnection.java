package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:sqlite:hospital.db?busy_timeout=10000";

    static {
        try {

            Class.forName("org.sqlite.JDBC");

            System.out.println("✅ SQLite JDBC Driver Loaded");

        } catch (ClassNotFoundException e) {

            System.out.println("❌ SQLite JDBC Driver Not Found!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {

        try {

            Connection con =
                    DriverManager.getConnection(URL);

            // Enable foreign keys
            try (var stmt = con.createStatement()) {

                stmt.execute("PRAGMA foreign_keys = ON");
                stmt.execute("PRAGMA busy_timeout = 10000");
                stmt.execute("PRAGMA journal_mode = WAL");
            }

            System.out.println("✅ SQLite Database Connected");

            return con;

        } catch (SQLException e) {

            System.out.println(
                    "❌ SQLite Connection Failed!"
            );

            e.printStackTrace();

            return null;
        }
    }
}