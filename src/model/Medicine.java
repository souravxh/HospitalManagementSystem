package model;

public class Medicine {

    private int id;
    private String medicineName;
    private String category;
    private String supplier;
    private int quantity;
    private double price;
    private String expiryDate;
    private String createdAt;

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
    // Medicine Name
    // =========================

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    // =========================
    // Category
    // =========================

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // =========================
    // Supplier
    // =========================

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    // =========================
    // Quantity
    // =========================

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // =========================
    // Price
    // =========================

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // =========================
    // Expiry Date
    // =========================

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    // =========================
    // Created At
    // =========================

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}