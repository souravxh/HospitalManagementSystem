package database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement()) {

            // ==========================
            // USERS TABLE
            // ==========================

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL
                )
            """);

            // DEFAULT ADMIN USER
            stmt.executeUpdate("""
                INSERT OR IGNORE INTO users (username, password)
                VALUES ('admin', 'admin123')
            """);


            // ==========================
            // PATIENTS TABLE
            // ==========================

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS patients (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    age INTEGER,
                    gender TEXT,
                    disease TEXT,
                    phone TEXT,
                    address TEXT,
                    blood_group TEXT
                )
            """);


            // ==========================
            // DOCTORS TABLE
            // ==========================

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS doctors (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    specialization TEXT,
                    gender TEXT,
                    phone TEXT,
                    email TEXT,
                    experience INTEGER
                )
            """);


            // ==========================
            // APPOINTMENTS TABLE
            // ==========================

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS appointments (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    patient_id INTEGER,
                    doctor_id INTEGER,
                    appointment_date TEXT,
                    appointment_time TEXT,
                    status TEXT
                )
            """);


            // ==========================
            // MEDICINES TABLE
            // ==========================

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS medicines (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    medicine_name TEXT NOT NULL,
                    category TEXT,
                    supplier TEXT,
                    quantity INTEGER,
                    price REAL,
                    expiry_date TEXT,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);


            // ==========================
            // PHARMACY TABLE
            // ==========================

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS pharmacy (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    medicine_name TEXT NOT NULL,
                    category TEXT,
                    quantity INTEGER,
                    price REAL,
                    supplier TEXT,
                    expiry_date TEXT
                )
            """);


            // ==========================
            // BILLS TABLE
            // ==========================

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS bills (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    patient_id INTEGER,
                    doctor_charge REAL,
                    medicine_charge REAL,
                    room_charge REAL,
                    total_amount REAL,
                    payment_status TEXT,
                    bill_date TEXT
                )
            """);


            // ==========================
            // BEDS TABLE
            // ==========================

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS beds (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    status TEXT NOT NULL DEFAULT 'Available'
                )
            """);


            // ==========================
            // ADD SAMPLE BEDS
            // ==========================

            stmt.executeUpdate("""
                INSERT INTO beds (status)
                SELECT 'Available'
                WHERE NOT EXISTS (SELECT 1 FROM beds)
            """);


            System.out.println("========================================");
            System.out.println("✅ DATABASE INITIALIZED SUCCESSFULLY");
            System.out.println("========================================");

        } catch (Exception e) {

            System.out.println("❌ DATABASE INITIALIZATION FAILED");
            e.printStackTrace();
        }
    }
}