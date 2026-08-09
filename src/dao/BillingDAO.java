package dao;

import database.DBConnection;
import model.Billing;

import java.sql.*;
import java.util.ArrayList;

public class BillingDAO {

    // ==========================
    // ADD BILL
    // ==========================

    public boolean addBill(Billing b) {

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "INSERT INTO bills " +
                            "(patient_id, doctor_charge, medicine_charge, room_charge, total_amount, payment_status, bill_date) " +
                            "VALUES (?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, b.getPatientId());
            ps.setDouble(2, b.getDoctorCharge());
            ps.setDouble(3, b.getMedicineCharge());
            ps.setDouble(4, b.getRoomCharge());
            ps.setDouble(5, b.getTotalAmount());
            ps.setString(6, b.getPaymentStatus());
            ps.setString(7, b.getBillDate());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================
    // GET ALL BILLS
    // ==========================

    public ArrayList<Billing> getBills() {

        ArrayList<Billing> list = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM bills ORDER BY id DESC";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Billing b = new Billing();

                b.setId(rs.getInt("id"));
                b.setPatientId(rs.getInt("patient_id"));
                b.setDoctorCharge(rs.getDouble("doctor_charge"));
                b.setMedicineCharge(rs.getDouble("medicine_charge"));
                b.setRoomCharge(rs.getDouble("room_charge"));
                b.setTotalAmount(rs.getDouble("total_amount"));
                b.setPaymentStatus(rs.getString("payment_status"));
                b.setBillDate(rs.getString("bill_date"));

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==========================
    // SEARCH BILL
    // ==========================

    public Billing getBillById(int id) {

        Billing b = null;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM bills WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                b = new Billing();

                b.setId(rs.getInt("id"));
                b.setPatientId(rs.getInt("patient_id"));
                b.setDoctorCharge(rs.getDouble("doctor_charge"));
                b.setMedicineCharge(rs.getDouble("medicine_charge"));
                b.setRoomCharge(rs.getDouble("room_charge"));
                b.setTotalAmount(rs.getDouble("total_amount"));
                b.setPaymentStatus(rs.getString("payment_status"));
                b.setBillDate(rs.getString("bill_date"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return b;
    }

    // ==========================
    // UPDATE BILL
    // ==========================

    public boolean updateBill(Billing b) {

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "UPDATE bills SET " +
                            "patient_id=?, doctor_charge=?, medicine_charge=?, room_charge=?, total_amount=?, payment_status=?, bill_date=? " +
                            "WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, b.getPatientId());
            ps.setDouble(2, b.getDoctorCharge());
            ps.setDouble(3, b.getMedicineCharge());
            ps.setDouble(4, b.getRoomCharge());
            ps.setDouble(5, b.getTotalAmount());
            ps.setString(6, b.getPaymentStatus());
            ps.setString(7, b.getBillDate());
            ps.setInt(8, b.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================
    // DELETE BILL
    // ==========================

    public boolean deleteBill(int id) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM bills WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}