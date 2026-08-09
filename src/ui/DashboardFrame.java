package ui;

import dao.DashboardDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardFrame extends JFrame {

    private JPanel mainContent;
    private CardLayout cardLayout;

    private final Color SIDEBAR_COLOR = new Color(25, 118, 210);
    private final Color BACKGROUND_COLOR = new Color(240, 245, 250);


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public DashboardFrame() {

        setTitle("Hospital Management System");

        setSize(1400, 800);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(true);


        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(BACKGROUND_COLOR);


        // =====================================================
        // SIDEBAR
        // =====================================================

        JPanel sidebar = createSidebar();

        mainPanel.add(
                sidebar,
                BorderLayout.WEST
        );


        // =====================================================
        // CONTENT
        // =====================================================

        JPanel content =
                new JPanel(new BorderLayout());

        content.setBackground(
                BACKGROUND_COLOR
        );


        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                createHeader();

        content.add(
                header,
                BorderLayout.NORTH
        );


        // =====================================================
        // CARD LAYOUT
        // =====================================================

        cardLayout = new CardLayout();

        mainContent =
                new JPanel(cardLayout);

        mainContent.setBackground(
                BACKGROUND_COLOR
        );


        // =====================================================
        // ADD ALL MODULES
        // =====================================================

        mainContent.add(
                createDashboardHome(),
                "dashboard"
        );


        mainContent.add(
                new PatientPanel(),
                "patients"
        );


        mainContent.add(
                new DoctorPanel(),
                "doctors"
        );


        mainContent.add(
                new AppointmentPanel(),
                "appointments"
        );


        mainContent.add(
                new BillingPanel(),
                "billing"
        );


        // Pharmacy
        try {

            mainContent.add(
                    new PharmacyPanel(),
                    "pharmacy"
            );

        } catch (Exception e) {

            mainContent.add(
                    createUnavailablePanel(
                            "Pharmacy"
                    ),
                    "pharmacy"
            );
        }


        // Reports
        try {

            mainContent.add(
                    new ReportsPanel(),
                    "reports"
            );

        } catch (Exception e) {

            mainContent.add(
                    createUnavailablePanel(
                            "Reports"
                    ),
                    "reports"
            );
        }


        // Settings
        mainContent.add(
                new SettingsPanel(),
                "settings"
        );


        content.add(
                mainContent,
                BorderLayout.CENTER
        );


        // =====================================================
        // PROFILE FOOTER
        // =====================================================

        JPanel profile =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        profile.setBackground(
                Color.WHITE
        );

        profile.setBorder(
                new EmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );


        JLabel adminLabel =
                new JLabel(
                        "Administrator"
                );

        adminLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );


        profile.add(adminLabel);


        content.add(
                profile,
                BorderLayout.SOUTH
        );


        // =====================================================
        // ADD CONTENT
        // =====================================================

        mainPanel.add(
                content,
                BorderLayout.CENTER
        );


        add(mainPanel);


        // =====================================================
        // DEFAULT PAGE
        // =====================================================

        cardLayout.show(
                mainContent,
                "dashboard"
        );


        setVisible(true);
    }


    // =========================================================
    // SIDEBAR
    // =========================================================

    private JPanel createSidebar() {

        JPanel sidebar =
                new JPanel();


        sidebar.setPreferredSize(
                new Dimension(
                        250,
                        800
                )
        );


        sidebar.setBackground(
                SIDEBAR_COLOR
        );


        sidebar.setLayout(
                new BoxLayout(
                        sidebar,
                        BoxLayout.Y_AXIS
                )
        );


        // =====================================================
        // LOGO
        // =====================================================

        JLabel logo =
                new JLabel("HMS");


        logo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        34
                )
        );


        logo.setForeground(
                Color.WHITE
        );


        logo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        sidebar.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                30
                        )
                )
        );


        sidebar.add(logo);


        sidebar.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                35
                        )
                )
        );


        // =====================================================
        // MENU
        // =====================================================

        String[] menu = {

                "Dashboard",
                "Patients",
                "Doctors",
                "Appointments",
                "Billing",
                "Pharmacy",
                "Reports",
                "Settings",
                "Logout"

        };


        for (String item : menu) {

            JButton button =
                    createMenuButton(item);


            button.addActionListener(
                    e -> handleMenu(item)
            );


            sidebar.add(button);


            sidebar.add(
                    Box.createRigidArea(
                            new Dimension(
                                    0,
                                    8
                            )
                    )
            );
        }


        return sidebar;
    }


    // =========================================================
    // MENU BUTTON
    // =========================================================

    private JButton createMenuButton(
            String text
    ) {

        JButton button =
                new JButton(text);


        button.setMaximumSize(
                new Dimension(
                        220,
                        45
                )
        );


        button.setPreferredSize(
                new Dimension(
                        220,
                        45
                )
        );


        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        button.setBackground(
                SIDEBAR_COLOR
        );


        button.setForeground(
                Color.WHITE
        );


        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );


        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setOpaque(true);


        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        return button;
    }


    // =========================================================
    // MENU HANDLER
    // =========================================================

    private void handleMenu(
            String item
    ) {

        switch (item) {

            case "Dashboard":

                cardLayout.show(
                        mainContent,
                        "dashboard"
                );

                break;


            case "Patients":

                cardLayout.show(
                        mainContent,
                        "patients"
                );

                break;


            case "Doctors":

                cardLayout.show(
                        mainContent,
                        "doctors"
                );

                break;


            case "Appointments":

                cardLayout.show(
                        mainContent,
                        "appointments"
                );

                break;


            case "Billing":

                cardLayout.show(
                        mainContent,
                        "billing"
                );

                break;


            case "Pharmacy":

                cardLayout.show(
                        mainContent,
                        "pharmacy"
                );

                break;


            case "Reports":

                cardLayout.show(
                        mainContent,
                        "reports"
                );

                break;


            case "Settings":

                cardLayout.show(
                        mainContent,
                        "settings"
                );

                break;


            case "Logout":

                int result =
                        JOptionPane.showConfirmDialog(
                                this,
                                "Are you sure you want to logout?",
                                "Logout",
                                JOptionPane.YES_NO_OPTION
                        );


                if (result ==
                        JOptionPane.YES_OPTION) {

                    dispose();

                    new LoginFrame();
                }

                break;
        }
    }


    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {

        JPanel header =
                new JPanel();


        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );


        header.setBackground(
                BACKGROUND_COLOR
        );


        header.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        15,
                        25
                )
        );


        JLabel title =
                new JLabel(
                        "Welcome, Administrator"
                );


        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );


        JLabel subtitle =
                new JLabel(
                        "Hospital Management Dashboard"
                );


        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );


        subtitle.setForeground(
                Color.GRAY
        );


        JLabel clock =
                new JLabel();


        clock.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );


        clock.setForeground(
                new Color(
                        90,
                        90,
                        90
                )
        );


        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd MMM yyyy  |  HH:mm:ss"
                );


        Timer timer =
                new Timer(
                        1000,
                        e -> clock.setText(
                                LocalDateTime.now()
                                        .format(formatter)
                        )
                );


        clock.setText(
                LocalDateTime.now()
                        .format(formatter)
        );


        timer.start();


        header.add(title);


        header.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                4
                        )
                )
        );


        header.add(subtitle);


        header.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                8
                        )
                )
        );


        header.add(clock);


        return header;
    }


    // =========================================================
    // DASHBOARD HOME
    // =========================================================

    private JPanel createDashboardHome() {

        JPanel main =
                new JPanel(
                        new BorderLayout()
                );


        main.setBackground(
                BACKGROUND_COLOR
        );


        main.setBorder(
                new EmptyBorder(
                        10,
                        20,
                        20,
                        20
                )
        );


        DashboardDAO dao =
                new DashboardDAO();


        // =====================================================
        // STAT CARDS
        // =====================================================

        JPanel cards =
                new JPanel(
                        new GridLayout(
                                2,
                                4,
                                18,
                                18
                        )
                );


        cards.setBackground(
                BACKGROUND_COLOR
        );


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


        int beds =
                dao.getAvailableBedCount();


        int paid =
                dao.getPaidBills();


        int pending =
                dao.getPendingBills();


        cards.add(
                createStatCard(
                        "Total Patients",
                        String.valueOf(patients),
                        new Color(25, 118, 210)
                )
        );


        cards.add(
                createStatCard(
                        "Doctors",
                        String.valueOf(doctors),
                        new Color(46, 125, 50)
                )
        );


        cards.add(
                createStatCard(
                        "Appointments",
                        String.valueOf(appointments),
                        new Color(239, 108, 0)
                )
        );


        cards.add(
                createStatCard(
                        "Total Bills",
                        String.valueOf(bills),
                        new Color(123, 31, 162)
                )
        );


        cards.add(
                createStatCard(
                        "Pharmacy Items",
                        String.valueOf(pharmacy),
                        new Color(0, 121, 107)
                )
        );


        cards.add(
                createStatCard(
                        "Beds Available",
                        String.valueOf(beds),
                        new Color(198, 40, 40)
                )
        );


        cards.add(
                createStatCard(
                        "Paid Bills",
                        String.valueOf(paid),
                        new Color(46, 125, 50)
                )
        );


        cards.add(
                createStatCard(
                        "Pending Bills",
                        String.valueOf(pending),
                        new Color(245, 124, 0)
                )
        );


        main.add(
                cards,
                BorderLayout.NORTH
        );


        // =====================================================
        // LOWER INFORMATION AREA
        // =====================================================

        JPanel lower =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                18,
                                18
                        )
                );


        lower.setBackground(
                BACKGROUND_COLOR
        );


        lower.setBorder(
                new EmptyBorder(
                        20,
                        0,
                        0,
                        0
                )
        );


        // Revenue card

        double revenue =
                dao.getTotalRevenue();


        JPanel revenueCard =
                createInformationCard(
                        "Revenue Overview"
                );


        JLabel revenueLabel =
                new JLabel(
                        "₹ "
                                + String.format(
                                "%.2f",
                                revenue
                        )
                );


        revenueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        38
                )
        );


        revenueLabel.setForeground(
                new Color(
                        25,
                        118,
                        210
                )
        );


        revenueCard.add(
                revenueLabel
        );


        revenueCard.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                10
                        )
                )
        );


        JLabel revenueText =
                new JLabel(
                        "Total revenue recorded in billing"
                );


        revenueText.setForeground(
                Color.GRAY
        );


        revenueCard.add(
                revenueText
        );


        // Quick actions

        JPanel actionsCard =
                createInformationCard(
                        "Quick Actions"
                );


        JButton patientsButton =
                new JButton(
                        "Manage Patients"
                );


        JButton appointmentsButton =
                new JButton(
                        "Appointments"
                );


        JButton billingButton =
                new JButton(
                        "Billing"
                );


        styleActionButton(
                patientsButton
        );


        styleActionButton(
                appointmentsButton
        );


        styleActionButton(
                billingButton
        );


        patientsButton.addActionListener(
                e -> cardLayout.show(
                        mainContent,
                        "patients"
                )
        );


        appointmentsButton.addActionListener(
                e -> cardLayout.show(
                        mainContent,
                        "appointments"
                )
        );


        billingButton.addActionListener(
                e -> cardLayout.show(
                        mainContent,
                        "billing"
                )
        );


        actionsCard.add(
                patientsButton
        );


        actionsCard.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                8
                        )
                )
        );


        actionsCard.add(
                appointmentsButton
        );


        actionsCard.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                8
                        )
                )
        );


        actionsCard.add(
                billingButton
        );


        lower.add(revenueCard);

        lower.add(actionsCard);


        main.add(
                lower,
                BorderLayout.CENTER
        );


        return main;
    }


    // =========================================================
    // STAT CARD
    // =========================================================

    private JPanel createStatCard(
            String title,
            String value,
            Color color
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
                                18,
                                18,
                                18
                        )
                )
        );


        JLabel titleLabel =
                new JLabel(title);


        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );


        titleLabel.setForeground(
                new Color(
                        80,
                        80,
                        80
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
                        34
                )
        );


        valueLabel.setForeground(color);


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
    // INFORMATION CARD
    // =========================================================

    private JPanel createInformationCard(
            String title
    ) {

        JPanel card =
                new JPanel();


        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
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
                                20,
                                25,
                                20,
                                25
                        )
                )
        );


        JLabel heading =
                new JLabel(title);


        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );


        heading.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        card.add(heading);


        card.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                20
                        )
                )
        );


        return card;
    }


    // =========================================================
    // ACTION BUTTON
    // =========================================================

    private void styleActionButton(
            JButton button
    ) {

        button.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        button.setMaximumSize(
                new Dimension(
                        250,
                        40
                )
        );


        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );


        button.setBackground(
                new Color(
                        25,
                        118,
                        210
                )
        );


        button.setForeground(
                Color.WHITE
        );


        button.setFocusPainted(false);

        button.setBorderPainted(false);


        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }


    // =========================================================
    // UNAVAILABLE MODULE
    // =========================================================

    private JPanel createUnavailablePanel(
            String moduleName
    ) {

        JPanel panel =
                new JPanel(
                        new GridBagLayout()
                );


        panel.setBackground(
                BACKGROUND_COLOR
        );


        JLabel label =
                new JLabel(
                        moduleName
                                + " module is not available yet."
                );


        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );


        label.setForeground(
                Color.GRAY
        );


        panel.add(label);


        return panel;
    }
}