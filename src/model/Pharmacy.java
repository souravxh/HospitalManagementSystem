package model;

public class Pharmacy {

    private int id;
    private String medicineName;
    private String category;
    private int quantity;
    private double price;
    private String supplier;
    private String expiryDate;

    // =========================
    // ID
    // =========================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // =========================
    // MEDICINE NAME
    // =========================

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    // =========================
    // CATEGORY
    // =========================

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // =========================
    // QUANTITY
    // =========================

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // =========================
    // PRICE
    // =========================

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // =========================
    // SUPPLIER
    // =========================

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    // =========================
    // EXPIRY DATE
    // =========================

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}