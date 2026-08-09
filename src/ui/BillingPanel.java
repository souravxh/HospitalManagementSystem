package ui;

import dao.BillingDAO;
import dao.PatientDAO;
import model.Billing;
import model.Patient;
import utils.PDFGenerator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

public class BillingPanel extends JPanel {

    private JComboBox<String> patientBox;
    private JTextField doctorChargeField;
    private JTextField medicineChargeField;
    private JTextField roomChargeField;
    private JLabel totalLabel;
    private JComboBox<String> statusBox;
    private JTextField searchField;

    private JTable table;
    private DefaultTableModel model;
    private JTextArea invoiceArea = new JTextArea();

    private BillingDAO billingDAO;
    private PatientDAO patientDAO;

    private final DecimalFormat money = new DecimalFormat("0.00");

    private final Color BG = new Color(245, 248, 252);
    private final Color CARD = Color.WHITE;
    private final Color PRIMARY = new Color(37, 99, 235);
    private final Color PRIMARY_DARK = new Color(29, 78, 216);
    private final Color TEXT = new Color(31, 41, 55);
    private final Color MUTED = new Color(107, 114, 128);
    private final Color BORDER = new Color(226, 232, 240);
    private final Color SUCCESS = new Color(16, 140, 80);
    private final Color DANGER = new Color(220, 60, 60);

    public BillingPanel() {
        billingDAO = new BillingDAO();
        patientDAO = new PatientDAO();

        setLayout(new BorderLayout());
        setBackground(BG);

        createUI();
        loadPatients();
        loadBills();
        updateTotal();
    }

    private void createUI() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CARD);
        header.setBorder(new EmptyBorder(20, 28, 18, 28));

        JLabel title = new JLabel("Billing Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel("Create, manage and print hospital billing invoices");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(MUTED);

        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setOpaque(false);
        titleBox.add(title);
        titleBox.add(Box.createVerticalStrut(4));
        titleBox.add(subtitle);

        header.add(titleBox, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(14, 14));
        content.setBackground(BG);
        content.setBorder(new EmptyBorder(15, 20, 20, 20));

        JPanel formCard = createFormCard();
        content.add(formCard, BorderLayout.NORTH);

        JPanel historyCard = createHistoryCard();
        content.add(historyCard, BorderLayout.CENTER);

        JPanel actionBar = createActionBar();
        content.add(actionBar, BorderLayout.SOUTH);

        add(content, BorderLayout.CENTER);
    }

    private JPanel createFormCard() {

        JPanel card = new JPanel(new BorderLayout(18, 0));
        card.setBackground(CARD);
        card.setBorder(new EmptyBorder(16, 18, 16, 18));

        JPanel left = new JPanel(new GridLayout(3, 2, 12, 10));
        left.setOpaque(false);

        patientBox = new JComboBox<>();
        doctorChargeField = new JTextField("0");
        medicineChargeField = new JTextField("0");
        roomChargeField = new JTextField("0");
        statusBox = new JComboBox<>(new String[]{"Paid", "Pending"});

        styleField(patientBox);
        styleField(doctorChargeField);
        styleField(medicineChargeField);
        styleField(roomChargeField);
        styleField(statusBox);

        addField(left, "Patient", patientBox);
        addField(left, "Doctor Charge", doctorChargeField);
        addField(left, "Medicine Charge", medicineChargeField);
        addField(left, "Room Charge", roomChargeField);
        addField(left, "Payment Status", statusBox);

        JPanel totalCard = new JPanel(new BorderLayout());
        totalCard.setBackground(new Color(239, 246, 255));
        totalCard.setBorder(new LineBorder(new Color(191, 219, 254), 1, true));
        totalCard.setPreferredSize(new Dimension(220, 110));

        JLabel totalCaption = new JLabel("TOTAL AMOUNT");
        totalCaption.setFont(new Font("Segoe UI", Font.BOLD, 12));
        totalCaption.setForeground(PRIMARY);

        totalLabel = new JLabel("₹ 0.00");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        totalLabel.setForeground(PRIMARY_DARK);

        JPanel totalText = new JPanel();
        totalText.setOpaque(false);
        totalText.setLayout(new BoxLayout(totalText, BoxLayout.Y_AXIS));
        totalText.setBorder(new EmptyBorder(15, 18, 15, 18));
        totalText.add(totalCaption);
        totalText.add(Box.createVerticalStrut(8));
        totalText.add(totalLabel);

        totalCard.add(totalText, BorderLayout.CENTER);

        card.add(left, BorderLayout.CENTER);
        card.add(totalCard, BorderLayout.EAST);

        DocumentListener listener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateTotal(); }
            public void removeUpdate(DocumentEvent e) { updateTotal(); }
            public void changedUpdate(DocumentEvent e) { updateTotal(); }
        };

        doctorChargeField.getDocument().addDocumentListener(listener);
        medicineChargeField.getDocument().addDocumentListener(listener);
        roomChargeField.getDocument().addDocumentListener(listener);

        return card;
    }

    private JPanel createHistoryCard() {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD);
        card.setBorder(new LineBorder(BORDER, 1, true));

        model = new DefaultTableModel(
                new String[]{
                        "Bill ID", "Patient ID", "Doctor", "Medicine",
                        "Room", "Total", "Status", "Date"
                }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(31);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(TEXT);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(TEXT);
        table.setGridColor(new Color(241, 245, 249));
        table.setAutoCreateRowSorter(true);
        table.setShowVerticalLines(false);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setForeground(TEXT);
        table.getTableHeader().setBackground(new Color(248, 250, 252));
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));

        DefaultTableCellRenderer moneyRenderer = new DefaultTableCellRenderer();
        moneyRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(2).setCellRenderer(moneyRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(moneyRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(moneyRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(moneyRenderer);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createEmptyBorder());

        JPanel tableHeader = new JPanel(new BorderLayout());
        tableHeader.setOpaque(false);
        tableHeader.setBorder(new EmptyBorder(10, 14, 8, 14));

        JLabel historyTitle = new JLabel("Bill History");
        historyTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        historyTitle.setForeground(TEXT);

        JLabel hint = new JLabel("Select a bill to preview");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        hint.setForeground(MUTED);

        tableHeader.add(historyTitle, BorderLayout.WEST);
        tableHeader.add(hint, BorderLayout.EAST);

        card.add(tableHeader, BorderLayout.NORTH);
        card.add(tableScroll, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                previewInvoice();
            }
        });

        return card;
    }

    private JPanel createActionBar() {

        JPanel bar = new JPanel(new BorderLayout(10, 0));
        bar.setOpaque(false);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        searchPanel.setOpaque(false);

        searchField = new JTextField(12);
        searchField.setPreferredSize(new Dimension(150, 38));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setToolTipText("Enter Bill ID");

        JButton search = createButton("Search", PRIMARY);
        search.addActionListener(e -> searchBill());

        JButton refresh = createButton("Refresh", new Color(75, 85, 99));
        refresh.addActionListener(e -> {
            loadPatients();
            loadBills();
        });

        searchPanel.add(searchField);
        searchPanel.add(search);
        searchPanel.add(refresh);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.setOpaque(false);

        JButton calculate = createButton("Calculate", new Color(14, 116, 144));
        calculate.addActionListener(e -> updateTotal());

        JButton generate = createButton("Generate Bill", SUCCESS);
        generate.addActionListener(e -> saveBill());

        JButton print = createButton("Print Invoice", new Color(124, 58, 237));
        print.addActionListener(e -> printInvoice());

        JButton pdf = createButton("Generate PDF", new Color(234, 88, 12));
        pdf.addActionListener(e -> generatePDF());

        JButton delete = createButton("Delete", DANGER);
        delete.addActionListener(e -> deleteSelectedBill());

        buttons.add(calculate);
        buttons.add(generate);
        buttons.add(print);
        buttons.add(pdf);
        buttons.add(delete);

        bar.add(searchPanel, BorderLayout.WEST);
        bar.add(buttons, BorderLayout.EAST);

        return bar;
    }

    private void addField(JPanel panel, String labelText, JComponent component) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(TEXT);
        panel.add(label);
        panel.add(component);
    }

    private void styleField(JComponent component) {
        component.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        component.setPreferredSize(new Dimension(180, 36));
        component.setBorder(new LineBorder(BORDER, 1, true));
    }

    private JButton createButton(String text, Color background) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(
                text.equals("Generate Bill") ? 130 : 118, 38));
        return button;
    }

    private void updateTotal() {
        double doctor = parseAmount(doctorChargeField.getText());
        double medicine = parseAmount(medicineChargeField.getText());
        double room = parseAmount(roomChargeField.getText());

        double total = doctor + medicine + room;

        if (totalLabel != null) {
            totalLabel.setText("₹ " + money.format(total));
        }
    }

    private double parseAmount(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        }

        try {
            double number = Double.parseDouble(value.trim());
            return number < 0 ? 0 : number;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void saveBill() {

        if (patientBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a patient.",
                    "Missing Patient",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        double doctor = getValidatedAmount(doctorChargeField, "Doctor charge");
        double medicine = getValidatedAmount(medicineChargeField, "Medicine charge");
        double room = getValidatedAmount(roomChargeField, "Room charge");

        if (doctor < 0 || medicine < 0 || room < 0) {
            return;
        }

        double total = doctor + medicine + room;

        if (total <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter at least one charge greater than 0.",
                    "Invalid Total",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            Billing b = new Billing();

            b.setPatientId(getPatientId());
            b.setDoctorCharge(doctor);
            b.setMedicineCharge(medicine);
            b.setRoomCharge(room);
            b.setTotalAmount(total);
            b.setPaymentStatus(statusBox.getSelectedItem().toString());
            b.setBillDate(LocalDate.now().toString());

            boolean result = billingDAO.addBill(b);

            if (result) {
                loadBills();

                updateTotal();

                JOptionPane.showMessageDialog(
                        this,
                        "Bill generated successfully.\nSelect the new bill from the table to generate its PDF.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                resetForm();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Bill could not be saved.\nCheck your database connection and bills table.",
                        "Save Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Error while generating bill:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private double getValidatedAmount(JTextField field, String name) {
        String value = field.getText().trim();

        if (value.isEmpty()) {
            return 0;
        }

        try {
            double amount = Double.parseDouble(value);

            if (amount < 0) {
                JOptionPane.showMessageDialog(
                        this,
                        name + " cannot be negative.",
                        "Invalid Amount",
                        JOptionPane.WARNING_MESSAGE
                );
                return -1;
            }

            return amount;

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    name + " must be a valid number.",
                    "Invalid Amount",
                    JOptionPane.WARNING_MESSAGE
            );
            return -1;
        }
    }

    private void searchBill() {

        String text = searchField.getText().trim();

        if (text.isEmpty()) {
            loadBills();
            return;
        }

        try {
            int id = Integer.parseInt(text);

            Billing bill = billingDAO.getBillById(id);

            if (bill == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "No bill found with ID: " + id,
                        "Not Found",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            model.setRowCount(0);
            addBillingRow(bill);

            if (table.getRowCount() > 0) {
                table.setRowSelectionInterval(0, 0);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid numeric Bill ID.",
                    "Invalid Search",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void printInvoice() {

        if (invoiceArea == null || invoiceArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Select a bill from the table first.",
                    "No Invoice",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        PrinterJob job = PrinterJob.getPrinterJob();

        job.setPrintable(
                invoiceArea.getPrintable(
                        new java.text.MessageFormat("City Hospital Invoice"),
                        new java.text.MessageFormat("Page {0}")
                )
        );

        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Unable to print invoice:\n" + ex.getMessage(),
                        "Print Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void generatePDF() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Select a bill from the table first.",
                    "Select Bill",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);

        try {
            Billing b = new Billing();

            b.setId(Integer.parseInt(model.getValueAt(modelRow, 0).toString()));
            b.setPatientId(Integer.parseInt(model.getValueAt(modelRow, 1).toString()));
            b.setDoctorCharge(Double.parseDouble(model.getValueAt(modelRow, 2).toString()));
            b.setMedicineCharge(Double.parseDouble(model.getValueAt(modelRow, 3).toString()));
            b.setRoomCharge(Double.parseDouble(model.getValueAt(modelRow, 4).toString()));
            b.setTotalAmount(Double.parseDouble(model.getValueAt(modelRow, 5).toString()));
            b.setPaymentStatus(model.getValueAt(modelRow, 6).toString());
            b.setBillDate(model.getValueAt(modelRow, 7).toString());

            PDFGenerator.generateBillPDF(b);

        } catch (Exception ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to generate PDF:\n" + ex.getMessage(),
                    "PDF Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void previewInvoice() {

        int row = table.getSelectedRow();

        if (row == -1) {
            invoiceArea.setText("");
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);

        StringBuilder invoice = new StringBuilder();

        invoice.append("\n");
        invoice.append("              CITY HOSPITAL\n");
        invoice.append("          HOSPITAL BILLING INVOICE\n");
        invoice.append("============================================\n\n");

        invoice.append("Bill ID          : ")
                .append(model.getValueAt(modelRow, 0)).append("\n\n");

        invoice.append("Patient ID       : ")
                .append(model.getValueAt(modelRow, 1)).append("\n\n");

        invoice.append("--------------------------------------------\n");

        invoice.append(String.format(
                "Doctor Charge    : ₹ %s%n%n",
                model.getValueAt(modelRow, 2)
        ));

        invoice.append(String.format(
                "Medicine Charge  : ₹ %s%n%n",
                model.getValueAt(modelRow, 3)
        ));

        invoice.append(String.format(
                "Room Charge      : ₹ %s%n%n",
                model.getValueAt(modelRow, 4)
        ));

        invoice.append("--------------------------------------------\n");

        invoice.append(String.format(
                "TOTAL AMOUNT     : ₹ %s%n%n",
                model.getValueAt(modelRow, 5)
        ));

        invoice.append("Payment Status    : ")
                .append(model.getValueAt(modelRow, 6)).append("\n\n");

        invoice.append("Bill Date         : ")
                .append(model.getValueAt(modelRow, 7)).append("\n\n");

        invoice.append("============================================\n");
        invoice.append("       Thank you for choosing City Hospital\n");

        if (invoiceArea == null) {
            invoiceArea = new JTextArea();
            invoiceArea.setEditable(false);
            invoiceArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
            invoiceArea.setMargin(new Insets(15, 15, 15, 15));
        }

        invoiceArea.setText(invoice.toString());
        invoiceArea.setCaretPosition(0);
    }

    private void deleteSelectedBill() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Select a bill from the table first.",
                    "Select Bill",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);
        int id = Integer.parseInt(model.getValueAt(modelRow, 0).toString());

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete Bill ID " + id + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        if (billingDAO.deleteBill(id)) {
            loadBills();
            invoiceArea.setText("");

            JOptionPane.showMessageDialog(
                    this,
                    "Bill deleted successfully.",
                    "Deleted",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Bill could not be deleted.",
                    "Delete Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void resetForm() {
        doctorChargeField.setText("0");
        medicineChargeField.setText("0");
        roomChargeField.setText("0");
        statusBox.setSelectedIndex(0);
        updateTotal();
    }

    private int getPatientId() {

        String value = patientBox.getSelectedItem().toString();

        return Integer.parseInt(
                value.split("-")[0].trim()
        );
    }

    private void loadPatients() {

        patientBox.removeAllItems();

        try {
            List<Patient> list = patientDAO.getPatientList();

            for (Patient p : list) {
                patientBox.addItem(
                        p.getId() + " - " + p.getName()
                );
            }

            if (patientBox.getItemCount() == 0) {
                patientBox.addItem("No patients available");
                patientBox.setSelectedIndex(0);
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load patients:\n" + ex.getMessage(),
                    "Patient Loading Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadBills() {

        model.setRowCount(0);
        if (invoiceArea != null) {
            invoiceArea.setText("");
        }

        try {
            List<Billing> list = billingDAO.getBills();

            for (Billing b : list) {
                addBillingRow(b);
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load bills:\n" + ex.getMessage(),
                    "Billing Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void addBillingRow(Billing b) {

        model.addRow(new Object[]{
                b.getId(),
                b.getPatientId(),
                money.format(b.getDoctorCharge()),
                money.format(b.getMedicineCharge()),
                money.format(b.getRoomCharge()),
                money.format(b.getTotalAmount()),
                b.getPaymentStatus(),
                b.getBillDate()
        });
    }
}