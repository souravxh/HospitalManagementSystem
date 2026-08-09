package ui;

import dao.PharmacyDAO;
import model.Pharmacy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PharmacyPanel extends JPanel {

    private JTextField medicineField;
    private JTextField categoryField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField supplierField;
    private JTextField expiryField;
    private JTextField searchField;

    private JTable medicineTable;
    private DefaultTableModel tableModel;

    private PharmacyDAO pharmacyDAO;

    private int selectedId = -1;

    // =========================
    // COLORS
    // =========================

    private final Color BLUE = new Color(25, 118, 210);
    private final Color DARK = new Color(30, 41, 59);
    private final Color BACKGROUND = new Color(244, 247, 251);
    private final Color WHITE = Color.WHITE;
    private final Color GREEN = new Color(22, 163, 74);
    private final Color RED = new Color(220, 38, 38);
    private final Color ORANGE = new Color(234, 88, 12);


    // =========================
    // CONSTRUCTOR
    // =========================

    public PharmacyPanel() {

        pharmacyDAO = new PharmacyDAO();

        setLayout(new BorderLayout());
        setBackground(BACKGROUND);

        createUI();

        loadMedicines();
    }


    // =========================
    // CREATE UI
    // =========================

    private void createUI() {

        JPanel mainPanel = new JPanel(
                new BorderLayout(20, 20)
        );

        mainPanel.setBackground(BACKGROUND);

        mainPanel.setBorder(
                new EmptyBorder(
                        25,
                        25,
                        25,
                        25
                )
        );


        // =========================
        // HEADER
        // =========================

        JPanel header = new JPanel(
                new BorderLayout()
        );

        header.setOpaque(false);


        JPanel titlePanel = new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setOpaque(false);


        JLabel title = new JLabel(
                "Pharmacy Management"
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(DARK);


        JLabel subtitle = new JLabel(
                "Manage medicines, stock, pricing and expiry details"
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        subtitle.setForeground(
                new Color(100, 116, 139)
        );


        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(subtitle);


        header.add(
                titlePanel,
                BorderLayout.WEST
        );


        // =========================
        // SEARCH
        // =========================

        JPanel searchPanel = new JPanel(
                new BorderLayout(8, 0)
        );

        searchPanel.setOpaque(false);


        searchField = new JTextField();

        searchField.setPreferredSize(
                new Dimension(
                        220,
                        40
                )
        );

        searchField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        searchField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(203, 213, 225)
                        ),
                        new EmptyBorder(
                                5,
                                10,
                                5,
                                10
                        )
                )
        );


        JButton searchButton =
                createButton(
                        "Search",
                        BLUE
                );


        searchButton.addActionListener(
                e -> searchMedicines()
        );


        searchField.addActionListener(
                e -> searchMedicines()
        );


        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        searchPanel.add(
                searchButton,
                BorderLayout.EAST
        );


        header.add(
                searchPanel,
                BorderLayout.EAST
        );


        mainPanel.add(
                header,
                BorderLayout.NORTH
        );


        // =========================
        // FORM
        // =========================

        JPanel formPanel = new JPanel(
                new GridBagLayout()
        );

        formPanel.setBackground(WHITE);

        formPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(226, 232, 240)
                        ),
                        new EmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        7,
                        7,
                        7,
                        7
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        medicineField = new JTextField();

        categoryField = new JTextField();

        quantityField = new JTextField();

        priceField = new JTextField();

        supplierField = new JTextField();

        expiryField = new JTextField();


        // Row 1

        addFormField(
                formPanel,
                gbc,
                "Medicine Name",
                medicineField,
                0,
                0
        );

        addFormField(
                formPanel,
                gbc,
                "Category",
                categoryField,
                2,
                0
        );

        addFormField(
                formPanel,
                gbc,
                "Quantity",
                quantityField,
                4,
                0
        );


        // Row 2

        addFormField(
                formPanel,
                gbc,
                "Price",
                priceField,
                0,
                1
        );

        addFormField(
                formPanel,
                gbc,
                "Supplier",
                supplierField,
                2,
                1
        );

        addFormField(
                formPanel,
                gbc,
                "Expiry Date",
                expiryField,
                4,
                1
        );


        // =========================
        // BUTTONS
        // =========================

        JPanel buttonPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        10,
                        5
                )
        );

        buttonPanel.setOpaque(false);


        JButton addButton =
                createButton(
                        "Add Medicine",
                        GREEN
                );

        JButton updateButton =
                createButton(
                        "Update",
                        BLUE
                );

        JButton deleteButton =
                createButton(
                        "Delete",
                        RED
                );

        JButton clearButton =
                createButton(
                        "Clear",
                        new Color(
                                100,
                                116,
                                139
                        )
                );


        addButton.addActionListener(
                e -> addMedicine()
        );

        updateButton.addActionListener(
                e -> updateMedicine()
        );

        deleteButton.addActionListener(
                e -> deleteMedicine()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );


        buttonPanel.add(addButton);

        buttonPanel.add(updateButton);

        buttonPanel.add(deleteButton);

        buttonPanel.add(clearButton);


        gbc.gridx = 0;
        gbc.gridy = 2;

        gbc.gridwidth = 6;

        gbc.weightx = 1;

        formPanel.add(
                buttonPanel,
                gbc
        );


        // =========================
        // TABLE
        // =========================

        String[] columns = {

                "ID",
                "Medicine Name",
                "Category",
                "Quantity",
                "Price",
                "Supplier",
                "Expiry Date"

        };


        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };


        medicineTable =
                new JTable(
                        tableModel
                );


        medicineTable.setRowHeight(38);

        medicineTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        medicineTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        medicineTable.setShowGrid(false);

        medicineTable.setIntercellSpacing(
                new Dimension(
                        0,
                        0
                )
        );


        medicineTable.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                14
                        )
                );


        medicineTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                42
                        )
                );


        medicineTable.getTableHeader()
                .setBackground(
                        new Color(
                                241,
                                245,
                                249
                        )
                );


        medicineTable.getTableHeader()
                .setForeground(DARK);


        medicineTable.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        if (
                                e.getClickCount() == 1
                        ) {

                            loadSelectedMedicine();

                        }
                    }
                }
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        medicineTable
                );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                226,
                                232,
                                240
                        )
                )
        );


        // =========================
        // CENTER PANEL
        // =========================

        JPanel centerPanel = new JPanel(
                new BorderLayout(
                        0,
                        15
                )
        );

        centerPanel.setOpaque(false);


        centerPanel.add(
                formPanel,
                BorderLayout.NORTH
        );


        centerPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );


        add(mainPanel);
    }


    // =========================
    // FORM FIELD
    // =========================

    private void addFormField(
            JPanel panel,
            GridBagConstraints gbc,
            String label,
            JTextField field,
            int x,
            int y
    ) {

        gbc.gridx = x;
        gbc.gridy = y;

        gbc.gridwidth = 1;

        gbc.weightx = 0;

        JLabel jLabel =
                new JLabel(
                        label
                );

        jLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        jLabel.setForeground(DARK);


        panel.add(
                jLabel,
                gbc
        );


        gbc.gridx = x + 1;

        gbc.weightx = 1;

        field.setPreferredSize(
                new Dimension(
                        150,
                        38
                )
        );

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        203,
                                        213,
                                        225
                                )
                        ),
                        new EmptyBorder(
                                5,
                                8,
                                5,
                                8
                        )
                )
        );


        panel.add(
                field,
                gbc
        );
    }


    // =========================
    // BUTTON CREATOR
    // =========================

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(
                        text
                );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                color
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                new EmptyBorder(
                        10,
                        18,
                        10,
                        18
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }


    // =========================
    // LOAD MEDICINES
    // =========================

    private void loadMedicines() {

        tableModel.setRowCount(0);

        ArrayList<Pharmacy> medicines =
                pharmacyDAO.getMedicines();


        for (Pharmacy p : medicines) {

            tableModel.addRow(
                    new Object[]{

                            p.getId(),

                            p.getMedicineName(),

                            p.getCategory(),

                            p.getQuantity(),

                            String.format(
                                    "₹ %.2f",
                                    p.getPrice()
                            ),

                            p.getSupplier(),

                            p.getExpiryDate()

                    }
            );
        }
    }


    // =========================
    // SEARCH
    // =========================

    private void searchMedicines() {

        String keyword =
                searchField
                        .getText()
                        .trim();


        if (keyword.isEmpty()) {

            loadMedicines();

            return;
        }


        tableModel.setRowCount(0);


        ArrayList<Pharmacy> medicines =
                pharmacyDAO.searchMedicine(
                        keyword
                );


        for (Pharmacy p : medicines) {

            tableModel.addRow(
                    new Object[]{

                            p.getId(),

                            p.getMedicineName(),

                            p.getCategory(),

                            p.getQuantity(),

                            String.format(
                                    "₹ %.2f",
                                    p.getPrice()
                            ),

                            p.getSupplier(),

                            p.getExpiryDate()

                    }
            );
        }
    }


    // =========================
    // ADD MEDICINE
    // =========================

    private void addMedicine() {

        if (!validateFields()) {
            return;
        }


        Pharmacy p =
                getPharmacyFromFields();


        if (
                pharmacyDAO.addMedicine(p)
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Medicine added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            loadMedicines();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add medicine.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================
    // UPDATE MEDICINE
    // =========================

    private void updateMedicine() {

        if (selectedId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a medicine first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (!validateFields()) {
            return;
        }


        Pharmacy p =
                getPharmacyFromFields();


        p.setId(
                selectedId
        );


        if (
                pharmacyDAO.updateMedicine(p)
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Medicine updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            loadMedicines();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update medicine.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================
    // DELETE MEDICINE
    // =========================

    private void deleteMedicine() {

        if (selectedId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a medicine first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this medicine?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );


        if (
                choice != JOptionPane.YES_OPTION
        ) {

            return;
        }


        if (
                pharmacyDAO.deleteMedicine(
                        selectedId
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Medicine deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            loadMedicines();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete medicine.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================
    // LOAD SELECTED MEDICINE
    // =========================

    private void loadSelectedMedicine() {

        int row =
                medicineTable
                        .getSelectedRow();


        if (row == -1) {
            return;
        }


        selectedId =
                Integer.parseInt(
                        medicineTable
                                .getValueAt(
                                        row,
                                        0
                                )
                                .toString()
                );


        medicineField.setText(
                medicineTable
                        .getValueAt(
                                row,
                                1
                        )
                        .toString()
        );


        categoryField.setText(
                medicineTable
                        .getValueAt(
                                row,
                                2
                        )
                        .toString()
        );


        quantityField.setText(
                medicineTable
                        .getValueAt(
                                row,
                                3
                        )
                        .toString()
        );


        String price =
                medicineTable
                        .getValueAt(
                                row,
                                4
                        )
                        .toString()
                        .replace(
                                "₹",
                                ""
                        )
                        .trim();


        priceField.setText(
                price
        );


        supplierField.setText(
                medicineTable
                        .getValueAt(
                                row,
                                5
                        )
                        .toString()
        );


        expiryField.setText(
                medicineTable
                        .getValueAt(
                                row,
                                6
                        )
                        .toString()
        );
    }


    // =========================
    // VALIDATION
    // =========================

    private boolean validateFields() {

        if (
                medicineField
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            showWarning(
                    "Please enter medicine name."
            );

            medicineField.requestFocus();

            return false;
        }


        if (
                categoryField
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            showWarning(
                    "Please enter category."
            );

            categoryField.requestFocus();

            return false;
        }


        if (
                quantityField
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            showWarning(
                    "Please enter quantity."
            );

            quantityField.requestFocus();

            return false;
        }


        if (
                priceField
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            showWarning(
                    "Please enter price."
            );

            priceField.requestFocus();

            return false;
        }


        try {

            int quantity =
                    Integer.parseInt(
                            quantityField
                                    .getText()
                                    .trim()
                    );


            if (quantity < 0) {

                showWarning(
                        "Quantity cannot be negative."
                );

                return false;
            }

        } catch (NumberFormatException e) {

            showWarning(
                    "Quantity must be a valid number."
            );

            quantityField.requestFocus();

            return false;
        }


        try {

            double price =
                    Double.parseDouble(
                            priceField
                                    .getText()
                                    .trim()
                    );


            if (price < 0) {

                showWarning(
                        "Price cannot be negative."
                );

                return false;
            }

        } catch (NumberFormatException e) {

            showWarning(
                    "Price must be a valid number."
            );

            priceField.requestFocus();

            return false;
        }


        if (
                !expiryField
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            try {

                java.sql.Date.valueOf(
                        expiryField
                                .getText()
                                .trim()
                );

            } catch (IllegalArgumentException e) {

                showWarning(
                        "Expiry date must be in YYYY-MM-DD format."
                );

                expiryField.requestFocus();

                return false;
            }
        }


        return true;
    }


    // =========================
    // CREATE OBJECT
    // =========================

    private Pharmacy getPharmacyFromFields() {

        Pharmacy p =
                new Pharmacy();


        p.setMedicineName(
                medicineField
                        .getText()
                        .trim()
        );


        p.setCategory(
                categoryField
                        .getText()
                        .trim()
        );


        p.setQuantity(
                Integer.parseInt(
                        quantityField
                                .getText()
                                .trim()
                )
        );


        p.setPrice(
                Double.parseDouble(
                        priceField
                                .getText()
                                .trim()
                )
        );


        p.setSupplier(
                supplierField
                        .getText()
                        .trim()
        );


        p.setExpiryDate(
                expiryField
                        .getText()
                        .trim()
        );


        return p;
    }


    // =========================
    // CLEAR
    // =========================

    private void clearFields() {

        medicineField.setText("");

        categoryField.setText("");

        quantityField.setText("");

        priceField.setText("");

        supplierField.setText("");

        expiryField.setText("");

        searchField.setText("");

        selectedId = -1;

        medicineTable.clearSelection();
    }


    // =========================
    // WARNING
    // =========================

    private void showWarning(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation",
                JOptionPane.WARNING_MESSAGE
        );
    }
}