package dao;

import database.DBConnection;
import model.Medicine;

import java.sql.*;
import java.util.ArrayList;

public class MedicineDAO {

    // =========================
    // ADD MEDICINE
    // =========================

    public boolean addMedicine(Medicine m) {

        String sql =
                "INSERT INTO medicines " +
                        "(medicine_name, category, supplier, quantity, price, expiry_date) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, m.getMedicineName());
            ps.setString(2, m.getCategory());
            ps.setString(3, m.getSupplier());
            ps.setInt(4, m.getQuantity());
            ps.setDouble(5, m.getPrice());

            if (m.getExpiryDate() == null ||
                    m.getExpiryDate().trim().isEmpty()) {

                ps.setNull(6, Types.DATE);

            } else {

                ps.setDate(
                        6,
                        Date.valueOf(m.getExpiryDate())
                );
            }

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }


    // =========================
    // GET ALL MEDICINES
    // =========================

    public ArrayList<Medicine> getMedicines() {

        ArrayList<Medicine> list = new ArrayList<>();

        String sql =
                "SELECT * FROM medicines ORDER BY id DESC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Medicine m = new Medicine();

                m.setId(
                        rs.getInt("id")
                );

                m.setMedicineName(
                        rs.getString("medicine_name")
                );

                m.setCategory(
                        rs.getString("category")
                );

                m.setSupplier(
                        rs.getString("supplier")
                );

                m.setQuantity(
                        rs.getInt("quantity")
                );

                m.setPrice(
                        rs.getDouble("price")
                );

                Date expiryDate =
                        rs.getDate("expiry_date");

                if (expiryDate != null) {

                    m.setExpiryDate(
                            expiryDate.toString()
                    );

                } else {

                    m.setExpiryDate("");
                }

                Timestamp createdAt =
                        rs.getTimestamp("created_at");

                if (createdAt != null) {

                    m.setCreatedAt(
                            createdAt.toString()
                    );

                } else {

                    m.setCreatedAt("");
                }

                list.add(m);
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return list;
    }


    // =========================
    // GET MEDICINE BY ID
    // =========================

    public Medicine getMedicineById(int id) {

        Medicine m = null;

        String sql =
                "SELECT * FROM medicines WHERE id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    m = new Medicine();

                    m.setId(
                            rs.getInt("id")
                    );

                    m.setMedicineName(
                            rs.getString("medicine_name")
                    );

                    m.setCategory(
                            rs.getString("category")
                    );

                    m.setSupplier(
                            rs.getString("supplier")
                    );

                    m.setQuantity(
                            rs.getInt("quantity")
                    );

                    m.setPrice(
                            rs.getDouble("price")
                    );

                    Date expiryDate =
                            rs.getDate("expiry_date");

                    if (expiryDate != null) {

                        m.setExpiryDate(
                                expiryDate.toString()
                        );

                    } else {

                        m.setExpiryDate("");
                    }

                    Timestamp createdAt =
                            rs.getTimestamp("created_at");

                    if (createdAt != null) {

                        m.setCreatedAt(
                                createdAt.toString()
                        );

                    } else {

                        m.setCreatedAt("");
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return m;
    }


    // =========================
    // SEARCH MEDICINES
    // =========================

    public ArrayList<Medicine> searchMedicines(
            String keyword
    ) {

        ArrayList<Medicine> list =
                new ArrayList<>();

        String sql =
                "SELECT * FROM medicines " +
                        "WHERE medicine_name LIKE ? " +
                        "OR category LIKE ? " +
                        "OR supplier LIKE ? " +
                        "ORDER BY id DESC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            String search =
                    "%" + keyword + "%";

            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Medicine m =
                            new Medicine();

                    m.setId(
                            rs.getInt("id")
                    );

                    m.setMedicineName(
                            rs.getString("medicine_name")
                    );

                    m.setCategory(
                            rs.getString("category")
                    );

                    m.setSupplier(
                            rs.getString("supplier")
                    );

                    m.setQuantity(
                            rs.getInt("quantity")
                    );

                    m.setPrice(
                            rs.getDouble("price")
                    );

                    Date expiryDate =
                            rs.getDate("expiry_date");

                    if (expiryDate != null) {

                        m.setExpiryDate(
                                expiryDate.toString()
                        );

                    } else {

                        m.setExpiryDate("");
                    }

                    Timestamp createdAt =
                            rs.getTimestamp("created_at");

                    if (createdAt != null) {

                        m.setCreatedAt(
                                createdAt.toString()
                        );

                    } else {

                        m.setCreatedAt("");
                    }

                    list.add(m);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return list;
    }


    // =========================
    // UPDATE MEDICINE
    // =========================

    public boolean updateMedicine(Medicine m) {

        String sql =
                "UPDATE medicines SET " +
                        "medicine_name = ?, " +
                        "category = ?, " +
                        "supplier = ?, " +
                        "quantity = ?, " +
                        "price = ?, " +
                        "expiry_date = ? " +
                        "WHERE id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, m.getMedicineName());
            ps.setString(2, m.getCategory());
            ps.setString(3, m.getSupplier());
            ps.setInt(4, m.getQuantity());
            ps.setDouble(5, m.getPrice());

            if (m.getExpiryDate() == null ||
                    m.getExpiryDate().trim().isEmpty()) {

                ps.setNull(6, Types.DATE);

            } else {

                ps.setDate(
                        6,
                        Date.valueOf(m.getExpiryDate())
                );
            }

            ps.setInt(7, m.getId());

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
                "DELETE FROM medicines WHERE id = ?";

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
    // LOW STOCK MEDICINES
    // =========================

    public ArrayList<Medicine> getLowStockMedicines(
            int limit
    ) {

        ArrayList<Medicine> list =
                new ArrayList<>();

        String sql =
                "SELECT * FROM medicines " +
                        "WHERE quantity <= ? " +
                        "ORDER BY quantity ASC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, limit);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Medicine m =
                            new Medicine();

                    m.setId(
                            rs.getInt("id")
                    );

                    m.setMedicineName(
                            rs.getString("medicine_name")
                    );

                    m.setCategory(
                            rs.getString("category")
                    );

                    m.setSupplier(
                            rs.getString("supplier")
                    );

                    m.setQuantity(
                            rs.getInt("quantity")
                    );

                    m.setPrice(
                            rs.getDouble("price")
                    );

                    Date expiryDate =
                            rs.getDate("expiry_date");

                    if (expiryDate != null) {

                        m.setExpiryDate(
                                expiryDate.toString()
                        );

                    } else {

                        m.setExpiryDate("");
                    }

                    list.add(m);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return list;
    }
}