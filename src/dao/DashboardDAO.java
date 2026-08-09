package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardDAO {

    // =========================================================
    // PATIENT COUNT
    // =========================================================

    public int getPatientCount() {
        return getCount("patients");
    }


    // =========================================================
    // DOCTOR COUNT
    // =========================================================

    public int getDoctorCount() {
        return getCount("doctors");
    }


    // =========================================================
    // APPOINTMENT COUNT
    // =========================================================

    public int getAppointmentCount() {
        return getCount("appointments");
    }


    // =========================================================
    // BILL COUNT
    // =========================================================

    public int getBillCount() {
        return getCount("bills");
    }


    // =========================================================
    // PHARMACY COUNT
    // =========================================================

    public int getPharmacyCount() {
        return getCount("pharmacy");
    }


    // =========================================================
    // AVAILABLE BED COUNT
    // =========================================================

    public int getAvailableBedCount() {

        String sql =
                "SELECT COUNT(*) " +
                        "FROM beds " +
                        "WHERE status = 'Available'";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error getting available beds: "
                            + e.getMessage()
            );
        }

        return 0;
    }


    // =========================================================
    // TOTAL REVENUE
    // =========================================================

    public double getTotalRevenue() {

        String sql =
                "SELECT COALESCE(SUM(total_amount), 0) " +
                        "FROM bills";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error getting total revenue: "
                            + e.getMessage()
            );
        }

        return 0.0;
    }


    // =========================================================
    // PAID BILLS
    // =========================================================

    public int getPaidBills() {

        String sql =
                "SELECT COUNT(*) " +
                        "FROM bills " +
                        "WHERE payment_status = 'Paid'";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error getting paid bills: "
                            + e.getMessage()
            );
        }

        return 0;
    }


    // =========================================================
    // PENDING BILLS
    // =========================================================

    public int getPendingBills() {

        String sql =
                "SELECT COUNT(*) " +
                        "FROM bills " +
                        "WHERE payment_status = 'Pending'";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error getting pending bills: "
                            + e.getMessage()
            );
        }

        return 0;
    }


    // =========================================================
    // GENERIC COUNT
    // =========================================================

    private int getCount(String tableName) {

        String sql =
                "SELECT COUNT(*) FROM " + tableName;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error getting count from "
                            + tableName
                            + ": "
                            + e.getMessage()
            );
        }

        return 0;
    }
}