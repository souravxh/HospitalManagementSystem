package model;

public class Billing {

    private int id;

    private int patientId;

    private double doctorCharge;

    private double medicineCharge;

    private double roomCharge;

    private double totalAmount;

    private String paymentStatus;

    private String billDate;


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
    // Patient ID
    // =========================

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }


    // =========================
    // Doctor Charge
    // =========================

    public double getDoctorCharge() {
        return doctorCharge;
    }

    public void setDoctorCharge(double doctorCharge) {
        this.doctorCharge = doctorCharge;
    }


    // =========================
    // Medicine Charge
    // =========================

    public double getMedicineCharge() {
        return medicineCharge;
    }

    public void setMedicineCharge(double medicineCharge) {
        this.medicineCharge = medicineCharge;
    }


    // =========================
    // Room Charge
    // =========================

    public double getRoomCharge() {
        return roomCharge;
    }

    public void setRoomCharge(double roomCharge) {
        this.roomCharge = roomCharge;
    }


    // =========================
    // Total Amount
    // =========================

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }


    // =========================
    // Payment Status
    // =========================

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    // =========================
    // Bill Date
    // =========================

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

}