package dao;

import database.DBConnection;
import model.Pharmacy;

import java.sql.*;
import java.util.ArrayList;

public class PharmacyDAO {

    // =========================
    // ADD MEDICINE
    // =========================

    public boolean addMedicine(Pharmacy p) {

        String sql =
                "INSERT INTO pharmacy " +
                        "(medicine_name, category, quantity, price, supplier, expiry_date) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, p.getMedicineName());
            ps.setString(2, p.getCategory());
            ps.setInt(3, p.getQuantity());
            ps.setDouble(4, p.getPrice());
            ps.setString(5, p.getSupplier());

            setExpiryDate(ps, 6, p.getExpiryDate());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }


    // =========================
    // GET ALL MEDICINES
    // =========================

    public ArrayList<Pharmacy> getMedicines() {

        ArrayList<Pharmacy> list = new ArrayList<>();

        String sql =
                "SELECT * FROM pharmacy ORDER BY id DESC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Pharmacy p = createPharmacyFromResultSet(rs);

                list.add(p);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }


    // =========================
    // SEARCH MEDICINE
    // =========================

    public ArrayList<Pharmacy> searchMedicine(String keyword) {

        ArrayList<Pharmacy> list = new ArrayList<>();

        String sql =
                "SELECT * FROM pharmacy " +
                        "WHERE medicine_name LIKE ? " +
                        "OR category LIKE ? " +
                        "OR supplier LIKE ? " +
                        "ORDER BY id DESC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            String search = "%" + keyword + "%";

            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Pharmacy p =
                            createPharmacyFromResultSet(rs);

                    list.add(p);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }


    // =========================
    // GET MEDICINE BY ID
    // =========================

    public Pharmacy getMedicineById(int id) {

        Pharmacy p = null;

        String sql =
                "SELECT * FROM pharmacy WHERE id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    p =
                            createPharmacyFromResultSet(rs);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return p;
    }


    // =========================
    // UPDATE MEDICINE
    // =========================

    public boolean updateMedicine(Pharmacy p) {

        String sql =
                "UPDATE pharmacy SET " +
                        "medicine_name=?, " +
                        "category=?, " +
                        "quantity=?, " +
                        "price=?, " +
                        "supplier=?, " +
                        "expiry_date=? " +
                        "WHERE id=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    p.getMedicineName()
            );

            ps.setString(
                    2,
                    p.getCategory()
            );

            ps.setInt(
                    3,
                    p.getQuantity()
            );

            ps.setDouble(
                    4,
                    p.getPrice()
            );

            ps.setString(
                    5,
                    p.getSupplier()
            );

            setExpiryDate(
                    ps,
                    6,
                    p.getExpiryDate()
            );

            ps.setInt(
                    7,
                    p.getId()
            );

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }


    // =========================
    // DELETE MEDICINE
    // =========================

    public boolean deleteMedicine(int id) {

        String sql =
                "DELETE FROM pharmacy WHERE id=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }


    // =========================
    // CREATE PHARMACY OBJECT
    // =========================

    private Pharmacy createPharmacyFromResultSet(
            ResultSet rs
    ) throws SQLException {

        Pharmacy p = new Pharmacy();

        p.setId(
                rs.getInt("id")
        );

        p.setMedicineName(
                rs.getString("medicine_name")
        );

        p.setCategory(
                rs.getString("category")
        );

        p.setQuantity(
                rs.getInt("quantity")
        );

        p.setPrice(
                rs.getDouble("price")
        );

        p.setSupplier(
                rs.getString("supplier")
        );

        p.setExpiryDate(
                getExpiryDateAsString(
                        rs,
                        "expiry_date"
                )
        );

        return p;
    }


    // =========================
    // READ EXPIRY DATE SAFELY
    // =========================

    private String getExpiryDateAsString(
            ResultSet rs,
            String column
    ) throws SQLException {

        Object value =
                rs.getObject(column);

        if (value == null) {

            return "";
        }

        String text =
                value.toString().trim();

        if (text.isEmpty()) {

            return "";
        }

        // Already YYYY-MM-DD
        if (text.matches(
                "\\d{4}-\\d{2}-\\d{2}"
        )) {

            return text;
        }

        // YYYY-MM-DD HH:mm:ss
        if (text.matches(
                "\\d{4}-\\d{2}-\\d{2}.*"
        )) {

            return text.substring(
                    0,
                    10
            );
        }

        // Unix timestamp in milliseconds
        try {

            long timestamp =
                    Long.parseLong(text);

            java.util.Date date =
                    new java.util.Date(timestamp);

            java.text.SimpleDateFormat format =
                    new java.text.SimpleDateFormat(
                            "yyyy-MM-dd"
                    );

            return format.format(date);

        } catch (NumberFormatException ignored) {

            return text;
        }
    }


    // =========================
    // SAVE EXPIRY DATE
    // =========================

    private void setExpiryDate(
            PreparedStatement ps,
            int index,
            String expiryDate
    ) throws SQLException {

        if (expiryDate == null ||
                expiryDate.trim().isEmpty()) {

            ps.setNull(
                    index,
                    Types.DATE
            );

            return;
        }

        String value =
                expiryDate.trim();

        try {

            Date date =
                    Date.valueOf(value);

            ps.setDate(
                    index,
                    date
            );

        } catch (IllegalArgumentException e) {

            ps.setString(
                    index,
                    value
            );
        }
    }
}