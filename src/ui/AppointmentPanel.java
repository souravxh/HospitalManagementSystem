package ui;

import dao.AppointmentDAO;
import dao.DoctorDAO;
import dao.PatientDAO;

import model.Appointment;
import model.Doctor;
import model.Patient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AppointmentPanel extends JPanel {

    private JComboBox<String> patientBox;
    private JComboBox<String> doctorBox;

    private JTextField dateField;
    private JTextField timeField;

    private JComboBox<String> statusBox;

    private JTable table;
    private DefaultTableModel model;

    private AppointmentDAO appointmentDAO;
    private PatientDAO patientDAO;
    private DoctorDAO doctorDAO;

    public AppointmentPanel() {

        appointmentDAO = new AppointmentDAO();
        patientDAO = new PatientDAO();
        doctorDAO = new DoctorDAO();

        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 250));

        createUI();
        loadAppointments();
    }

    private void createUI() {

        JLabel title = new JLabel("Appointment Management");

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        title.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        add(title, BorderLayout.NORTH);

        // =========================
        // FORM
        // =========================

        JPanel form = new JPanel(
                new GridLayout(
                        5,
                        2,
                        15,
                        15
                )
        );

        form.setBackground(Color.WHITE);

        form.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        patientBox = new JComboBox<>();

        doctorBox = new JComboBox<>();

        dateField = new JTextField();

        timeField = new JTextField();

        statusBox = new JComboBox<>(
                new String[]{
                        "Scheduled",
                        "Completed",
                        "Cancelled"
                }
        );

        form.add(new JLabel("Patient"));
        form.add(patientBox);

        form.add(new JLabel("Doctor"));
        form.add(doctorBox);

        form.add(new JLabel("Date (YYYY-MM-DD)"));
        form.add(dateField);

        form.add(new JLabel("Time"));
        form.add(timeField);

        form.add(new JLabel("Status"));
        form.add(statusBox);

        // =========================
        // LOAD DROPDOWNS
        // =========================

        loadPatients();
        loadDoctors();

        // =========================
        // BUTTONS
        // =========================

        JButton addBtn =
                new JButton("Book Appointment");

        JButton updateBtn =
                new JButton("Update");

        JButton deleteBtn =
                new JButton("Delete");

        JButton refreshBtn =
                new JButton("Refresh");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        // =========================
        // TABLE
        // =========================

        model = new DefaultTableModel();

        model.setColumnIdentifiers(
                new String[]{
                        "ID",
                        "Patient ID",
                        "Doctor ID",
                        "Date",
                        "Time",
                        "Status"
                }
        );

        table = new JTable(model);

        table.setRowHeight(28);

        JScrollPane scroll =
                new JScrollPane(table);

        // =========================
        // CENTER
        // =========================

        JPanel center =
                new JPanel(
                        new BorderLayout()
                );

        center.setBorder(
                new EmptyBorder(
                        10,
                        20,
                        20,
                        20
                )
        );

        center.add(
                form,
                BorderLayout.NORTH
        );

        JPanel lower =
                new JPanel(
                        new BorderLayout()
                );

        lower.add(
                buttonPanel,
                BorderLayout.NORTH
        );

        lower.add(
                scroll,
                BorderLayout.CENTER
        );

        center.add(
                lower,
                BorderLayout.CENTER
        );

        add(
                center,
                BorderLayout.CENTER
        );

        // =========================
        // ADD APPOINTMENT
        // =========================

        addBtn.addActionListener(e -> {

            if (patientBox.getItemCount() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "No patients available.\nPlease add a patient first.",
                        "No Patient",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (doctorBox.getItemCount() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "No doctors available.\nPlease add a doctor first.",
                        "No Doctor",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (dateField.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter appointment date.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );

                dateField.requestFocus();

                return;
            }

            if (timeField.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter appointment time.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );

                timeField.requestFocus();

                return;
            }

            try {

                Appointment a =
                        getAppointment();

                if (appointmentDAO.addAppointment(a)) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Appointment Booked Successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    dateField.setText("");
                    timeField.setText("");

                    loadAppointments();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Failed to book appointment.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error:\n" + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                ex.printStackTrace();
            }

        });

        // =========================
        // DELETE
        // =========================

        deleteBtn.addActionListener(e -> {

            int row =
                    table.getSelectedRow();

            if (row == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select an appointment first.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            int id =
                    Integer.parseInt(
                            model.getValueAt(
                                    row,
                                    0
                            ).toString()
                    );

            int confirm =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Delete this appointment?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirm ==
                    JOptionPane.YES_OPTION) {

                if (appointmentDAO.deleteAppointment(id)) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Appointment Deleted."
                    );

                    loadAppointments();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Failed to delete appointment.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }

        });

        // =========================
        // UPDATE
        // =========================

        updateBtn.addActionListener(e -> {

            int row =
                    table.getSelectedRow();

            if (row == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select an appointment first.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            try {

                Appointment a =
                        getAppointment();

                a.setId(
                        Integer.parseInt(
                                model.getValueAt(
                                        row,
                                        0
                                ).toString()
                        )
                );

                if (appointmentDAO.updateAppointment(a)) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Appointment Updated Successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    loadAppointments();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Failed to update appointment.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error:\n" + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                ex.printStackTrace();
            }

        });

        // =========================
        // REFRESH
        // =========================

        refreshBtn.addActionListener(e -> {

            loadPatients();
            loadDoctors();
            loadAppointments();

            JOptionPane.showMessageDialog(
                    this,
                    "Patient, Doctor and Appointment lists refreshed."
            );

        });

        // =========================
        // TABLE CLICK
        // =========================

        table.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (e.getValueIsAdjusting()) {
                        return;
                    }

                    int row =
                            table.getSelectedRow();

                    if (row == -1) {
                        return;
                    }

                    String patientId =
                            model.getValueAt(
                                    row,
                                    1
                            ).toString();

                    String doctorId =
                            model.getValueAt(
                                    row,
                                    2
                            ).toString();

                    String date =
                            model.getValueAt(
                                    row,
                                    3
                            ).toString();

                    String time =
                            model.getValueAt(
                                    row,
                                    4
                            ).toString();

                    String status =
                            model.getValueAt(
                                    row,
                                    5
                            ).toString();

                    selectComboById(
                            patientBox,
                            patientId
                    );

                    selectComboById(
                            doctorBox,
                            doctorId
                    );

                    dateField.setText(date);

                    timeField.setText(time);

                    statusBox.setSelectedItem(status);
                });
    }

    // =========================
    // GET APPOINTMENT
    // =========================

    private Appointment getAppointment() {

        Appointment a =
                new Appointment();

        a.setPatientId(
                getIdFromCombo(patientBox)
        );

        a.setDoctorId(
                getIdFromCombo(doctorBox)
        );

        a.setAppointmentDate(
                dateField.getText().trim()
        );

        a.setAppointmentTime(
                timeField.getText().trim()
        );

        a.setStatus(
                statusBox
                        .getSelectedItem()
                        .toString()
        );

        return a;
    }

    // =========================
    // GET ID FROM COMBO
    // =========================

    private int getIdFromCombo(
            JComboBox<String> box
    ) {

        if (box.getSelectedItem() == null) {

            throw new IllegalStateException(
                    "Please select an item."
            );
        }

        String value =
                box.getSelectedItem()
                        .toString();

        int separator =
                value.indexOf(" - ");

        if (separator == -1) {

            throw new IllegalStateException(
                    "Invalid selection: " + value
            );
        }

        String id =
                value.substring(
                        0,
                        separator
                ).trim();

        return Integer.parseInt(id);
    }

    // =========================
    // SELECT COMBO BY ID
    // =========================

    private void selectComboById(
            JComboBox<String> box,
            String id
    ) {

        for (int i = 0;
             i < box.getItemCount();
             i++) {

            String item =
                    box.getItemAt(i);

            if (item.startsWith(id + " - ")) {

                box.setSelectedIndex(i);

                return;
            }
        }
    }

    // =========================
    // LOAD PATIENTS
    // =========================

    private void loadPatients() {

        if (patientBox == null) {
            return;
        }

        patientBox.removeAllItems();

        try {

            List<Patient> patients =
                    patientDAO.getPatientList();

            if (patients == null ||
                    patients.isEmpty()) {

                patientBox.addItem(
                        "No patients available"
                );

                return;
            }

            for (Patient p : patients) {

                patientBox.addItem(
                        p.getId()
                                + " - "
                                + p.getName()
                );
            }

        } catch (Exception e) {

            patientBox.addItem(
                    "Unable to load patients"
            );

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load patients:\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // LOAD DOCTORS
    // =========================

    private void loadDoctors() {

        if (doctorBox == null) {
            return;
        }

        doctorBox.removeAllItems();

        try {

            List<Doctor> doctors =
                    doctorDAO.getDoctorList();

            if (doctors == null ||
                    doctors.isEmpty()) {

                doctorBox.addItem(
                        "No doctors available"
                );

                return;
            }

            for (Doctor d : doctors) {

                doctorBox.addItem(
                        d.getId()
                                + " - "
                                + d.getName()
                );
            }

        } catch (Exception e) {

            doctorBox.addItem(
                    "Unable to load doctors"
            );

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load doctors:\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // LOAD APPOINTMENTS
    // =========================

    private void loadAppointments() {

        if (model == null) {
            return;
        }

        model.setRowCount(0);

        try {

            List<Appointment> list =
                    appointmentDAO
                            .getAllAppointments();

            if (list == null) {
                return;
            }

            for (Appointment a : list) {

                model.addRow(
                        new Object[]{
                                a.getId(),
                                a.getPatientId(),
                                a.getDoctorId(),
                                a.getAppointmentDate(),
                                a.getAppointmentTime(),
                                a.getStatus()
                        }
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load appointments:\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}