package ui;

import dao.DashboardDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class ReportsPanel extends JPanel {

    private final DashboardDAO dao;

    // Statistic labels
    private JLabel patientsValue;
    private JLabel doctorsValue;
    private JLabel appointmentsValue;
    private JLabel billsValue;
    private JLabel pharmacyValue;
    private JLabel revenueValue;

    // Billing labels
    private JLabel paidBillsValue;
    private JLabel pendingBillsValue;

    private final Color BACKGROUND = new Color(240, 245, 250);
    private final Color BLUE = new Color(25, 118, 210);
    private final Color GREEN = new Color(46, 125, 50);
    private final Color ORANGE = new Color(239, 108, 0);
    private final Color PURPLE = new Color(123, 31, 162);
    private final Color TEAL = new Color(0, 121, 107);
    private final Color RED = new Color(198, 40, 40);

    public ReportsPanel() {

        dao = new DashboardDAO();

        setLayout(new BorderLayout());
        setBackground(BACKGROUND);

        createUI();

        loadReports();
    }


    // =========================================================
    // CREATE UI
    // =========================================================

    private void createUI() {

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBackground(BACKGROUND);

        mainPanel.setBorder(
                new EmptyBorder(
                        10,
                        25,
                        20,
                        25
                )
        );


        // =====================================================
        // HEADER
        // =====================================================

        JPanel headerPanel = new JPanel(new BorderLayout());

        headerPanel.setBackground(BACKGROUND);

        headerPanel.setBorder(
                new EmptyBorder(
                        5,
                        0,
                        15,
                        0
                )
        );


        JPanel titlePanel = new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setBackground(BACKGROUND);


        JLabel title =
                new JLabel("Reports & Analytics");

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(
                new Color(35, 35, 45)
        );


        JLabel subtitle =
                new JLabel(
                        "Hospital overview and management reports"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        subtitle.setForeground(Color.GRAY);


        titlePanel.add(title);
        titlePanel.add(
                Box.createRigidArea(
                        new Dimension(0, 5)
                )
        );
        titlePanel.add(subtitle);


        // =====================================================
        // REFRESH BUTTON
        // =====================================================

        JButton refreshButton =
                new JButton("Refresh Reports");

        refreshButton.setPreferredSize(
                new Dimension(
                        150,
                        45
                )
        );

        refreshButton.setBackground(BLUE);

        refreshButton.setForeground(Color.WHITE);

        refreshButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        refreshButton.setFocusPainted(false);

        refreshButton.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        12,
                        5,
                        12
                )
        );


        refreshButton.addActionListener(
                e -> loadReports()
        );


        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        headerPanel.add(
                refreshButton,
                BorderLayout.EAST
        );


        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // CONTENT
        // =====================================================

        JPanel contentPanel =
                new JPanel();

        contentPanel.setBackground(BACKGROUND);

        contentPanel.setLayout(
                new BoxLayout(
                        contentPanel,
                        BoxLayout.Y_AXIS
                )
        );


        // =====================================================
        // STATISTICS GRID
        // =====================================================

        JPanel statisticsPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                3,
                                18,
                                18
                        )
                );

        statisticsPanel.setBackground(
                BACKGROUND
        );


        // Patients
        JPanel patientCard =
                createStatisticCard(
                        "Total Patients",
                        "0",
                        BLUE
                );

        patientsValue =
                getValueLabel(patientCard);


        // Doctors
        JPanel doctorCard =
                createStatisticCard(
                        "Total Doctors",
                        "0",
                        GREEN
                );

        doctorsValue =
                getValueLabel(doctorCard);


        // Appointments
        JPanel appointmentCard =
                createStatisticCard(
                        "Appointments",
                        "0",
                        ORANGE
                );

        appointmentsValue =
                getValueLabel(appointmentCard);


        // Bills
        JPanel billCard =
                createStatisticCard(
                        "Total Bills",
                        "0",
                        PURPLE
                );

        billsValue =
                getValueLabel(billCard);


        // Pharmacy
        JPanel pharmacyCard =
                createStatisticCard(
                        "Pharmacy Items",
                        "0",
                        TEAL
                );

        pharmacyValue =
                getValueLabel(pharmacyCard);


        // Revenue
        JPanel revenueCard =
                createStatisticCard(
                        "Total Revenue",
                        "₹ 0.00",
                        RED
                );

        revenueValue =
                getValueLabel(revenueCard);


        statisticsPanel.add(patientCard);
        statisticsPanel.add(doctorCard);
        statisticsPanel.add(appointmentCard);

        statisticsPanel.add(billCard);
        statisticsPanel.add(pharmacyCard);
        statisticsPanel.add(revenueCard);


        contentPanel.add(statisticsPanel);


        contentPanel.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                20
                        )
                )
        );


        // =====================================================
        // LOWER SECTION
        // =====================================================

        JPanel lowerPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                18,
                                0
                        )
                );

        lowerPanel.setBackground(
                BACKGROUND
        );


        // =====================================================
        // BILLING SUMMARY
        // =====================================================

        JPanel billingSummary =
                createWhitePanel();


        JLabel billingTitle =
                new JLabel(
                        "Billing Summary"
                );

        billingTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );


        JPanel billingRows =
                new JPanel();

        billingRows.setLayout(
                new BoxLayout(
                        billingRows,
                        BoxLayout.Y_AXIS
                )
        );

        billingRows.setBackground(
                Color.WHITE
        );


        paidBillsValue =
                new JLabel("0");

        pendingBillsValue =
                new JLabel("0");


        JPanel paidRow =
                createReportRow(
                        "Paid Bills",
                        paidBillsValue,
                        GREEN
                );


        JPanel pendingRow =
                createReportRow(
                        "Pending Bills",
                        pendingBillsValue,
                        ORANGE
                );


        billingRows.add(paidRow);

        billingRows.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                12
                        )
                )
        );

        billingRows.add(pendingRow);


        billingSummary.add(
                billingTitle
        );

        billingSummary.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                15
                        )
                )
        );

        billingSummary.add(
                billingRows
        );


        // =====================================================
        // HOSPITAL REPORTS
        // =====================================================

        JPanel hospitalReports =
                createWhitePanel();


        JLabel reportsTitle =
                new JLabel(
                        "Hospital Reports"
                );

        reportsTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );


        JLabel reportsSubtitle =
                new JLabel(
                        "Monitor the overall hospital activity"
                );

        reportsSubtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        reportsSubtitle.setForeground(
                Color.GRAY
        );


        JPanel reportButtons =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                10,
                                10
                        )
                );

        reportButtons.setBackground(
                Color.WHITE
        );


        JButton patientReport =
                createReportButton(
                        "Patient Report"
                );


        JButton doctorReport =
                createReportButton(
                        "Doctor Report"
                );


        JButton appointmentReport =
                createReportButton(
                        "Appointment Report"
                );


        JButton billingReport =
                createReportButton(
                        "Billing Report"
                );


        patientReport.addActionListener(
                e -> showMessage(
                        "Patient report is ready."
                )
        );


        doctorReport.addActionListener(
                e -> showMessage(
                        "Doctor report is ready."
                )
        );


        appointmentReport.addActionListener(
                e -> showMessage(
                        "Appointment report is ready."
                )
        );


        billingReport.addActionListener(
                e -> showMessage(
                        "Billing report is ready."
                )
        );


        reportButtons.add(
                patientReport
        );

        reportButtons.add(
                doctorReport
        );

        reportButtons.add(
                appointmentReport
        );

        reportButtons.add(
                billingReport
        );


        hospitalReports.add(
                reportsTitle
        );

        hospitalReports.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                5
                        )
                )
        );

        hospitalReports.add(
                reportsSubtitle
        );

        hospitalReports.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                15
                        )
                )
        );


        hospitalReports.add(
                reportButtons
        );


        lowerPanel.add(
                billingSummary
        );

        lowerPanel.add(
                hospitalReports
        );


        contentPanel.add(
                lowerPanel
        );


        // =====================================================
        // SCROLL
        // =====================================================

        JScrollPane scrollPane =
                new JScrollPane(
                        contentPanel
                );

        scrollPane.setBorder(null);

        scrollPane.setBackground(
                BACKGROUND
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);


        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        add(
                mainPanel,
                BorderLayout.CENTER
        );
    }


    // =========================================================
    // STATISTIC CARD
    // =========================================================

    private JPanel createStatisticCard(
            String title,
            String value,
            Color valueColor
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(
                Color.WHITE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        225,
                                        230,
                                        235
                                )
                        ),
                        new EmptyBorder(
                                18,
                                20,
                                18,
                                20
                        )
                )
        );


        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );


        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        38
                )
        );

        valueLabel.setForeground(
                valueColor
        );


        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );


        return card;
    }


    // =========================================================
    // GET VALUE LABEL
    // =========================================================

    private JLabel getValueLabel(
            JPanel card
    ) {

        Component component =
                ((BorderLayout) card.getLayout())
                        .getLayoutComponent(
                                BorderLayout.CENTER
                        );

        return (JLabel) component;
    }


    // =========================================================
    // WHITE PANEL
    // =========================================================

    private JPanel createWhitePanel() {

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBackground(
                Color.WHITE
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        225,
                                        230,
                                        235
                                )
                        ),
                        new EmptyBorder(
                                18,
                                20,
                                18,
                                20
                        )
                )
        );

        return panel;
    }


    // =========================================================
    // REPORT ROW
    // =========================================================

    private JPanel createReportRow(
            String title,
            JLabel value,
            Color color
    ) {

        JPanel row =
                new JPanel(
                        new BorderLayout()
                );

        row.setBackground(
                Color.WHITE
        );

        row.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );


        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );


        value.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        value.setForeground(
                color
        );

        value.setHorizontalAlignment(
                SwingConstants.RIGHT
        );


        row.add(
                titleLabel,
                BorderLayout.WEST
        );

        row.add(
                value,
                BorderLayout.EAST
        );


        return row;
    }


    // =========================================================
    // REPORT BUTTON
    // =========================================================

    private JButton createReportButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setFocusPainted(false);

        button.setBackground(
                new Color(
                        245,
                        248,
                        252
                )
        );

        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                220,
                                225,
                                230
                        )
                )
        );


        return button;
    }


    // =========================================================
    // LOAD REPORTS
    // =========================================================

    public void loadReports() {

        try {

            int patients =
                    dao.getPatientCount();

            int doctors =
                    dao.getDoctorCount();

            int appointments =
                    dao.getAppointmentCount();

            int bills =
                    dao.getBillCount();

            int pharmacy =
                    dao.getPharmacyCount();

            double revenue =
                    dao.getTotalRevenue();

            int paid =
                    dao.getPaidBills();
            int pending =
                    dao.getPendingBills();




            patientsValue.setText(
                    String.valueOf(patients)
            );

            doctorsValue.setText(
                    String.valueOf(doctors)
            );

            appointmentsValue.setText(
                    String.valueOf(appointments)
            );

            billsValue.setText(
                    String.valueOf(bills)
            );

            pharmacyValue.setText(
                    String.valueOf(pharmacy)
            );


            NumberFormat currency =
                    NumberFormat.getCurrencyInstance(
                            new Locale("en", "IN")
                    );

            revenueValue.setText(
                    currency.format(revenue)
            );


            paidBillsValue.setText(
                    String.valueOf(paid)
            );

            pendingBillsValue.setText(
                    String.valueOf(pending)
            );


        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load reports.",
                    "Reports Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // MESSAGE
    // =========================================================

    private void showMessage(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Hospital Reports",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}