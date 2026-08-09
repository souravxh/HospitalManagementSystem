package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SettingsPanel extends JPanel {

    private JTextField hospitalNameField;
    private JTextField hospitalPhoneField;
    private JTextField hospitalEmailField;
    private JTextField hospitalAddressField;

    private JTextField adminNameField;
    private JTextField adminUsernameField;

    private JCheckBox appointmentNotification;
    private JCheckBox billingNotification;
    private JCheckBox pharmacyNotification;

    private JComboBox<String> themeBox;


    public SettingsPanel() {

        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 250));

        createUI();
    }


    // =========================================================
    // MAIN UI
    // =========================================================

    private void createUI() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 245, 250));

        mainPanel.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        30,
                        30
                )
        );


        // =====================================================
        // HEADER
        // =====================================================

        JLabel title = new JLabel("Settings");

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        32
                )
        );

        title.setAlignmentX(Component.LEFT_ALIGNMENT);


        JLabel subtitle = new JLabel(
                "Manage hospital system preferences and administrator settings"
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );

        subtitle.setForeground(Color.GRAY);

        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);


        mainPanel.add(title);

        mainPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 5)
                )
        );

        mainPanel.add(subtitle);

        mainPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 25)
                )
        );


        // =====================================================
        // HOSPITAL INFORMATION
        // =====================================================

        JPanel hospitalCard =
                createCard("Hospital Information");

        hospitalNameField =
                createTextField("City Hospital");

        hospitalPhoneField =
                createTextField("+91 9876543210");

        hospitalEmailField =
                createTextField("info@cityhospital.com");

        hospitalAddressField =
                createTextField("Howrah, West Bengal");


        hospitalCard.add(
                createField(
                        "Hospital Name",
                        hospitalNameField
                )
        );

        hospitalCard.add(
                createField(
                        "Phone Number",
                        hospitalPhoneField
                )
        );

        hospitalCard.add(
                createField(
                        "Email Address",
                        hospitalEmailField
                )
        );

        hospitalCard.add(
                createField(
                        "Address",
                        hospitalAddressField
                )
        );


        mainPanel.add(hospitalCard);


        mainPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 20)
                )
        );


        // =====================================================
        // ADMINISTRATOR INFORMATION
        // =====================================================

        JPanel adminCard =
                createCard("Administrator");

        adminNameField =
                createTextField("Administrator");

        adminUsernameField =
                createTextField("admin");


        adminCard.add(
                createField(
                        "Administrator Name",
                        adminNameField
                )
        );

        adminCard.add(
                createField(
                        "Username",
                        adminUsernameField
                )
        );


        JButton changePasswordButton =
                new JButton("Change Password");

        styleButton(
                changePasswordButton,
                new Color(25, 118, 210)
        );


        changePasswordButton.addActionListener(
                e -> showChangePasswordDialog()
        );


        JPanel passwordPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                10
                        )
                );

        passwordPanel.setOpaque(false);

        passwordPanel.add(
                changePasswordButton
        );


        adminCard.add(passwordPanel);


        mainPanel.add(adminCard);


        mainPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 20)
                )
        );


        // =====================================================
        // NOTIFICATIONS
        // =====================================================

        JPanel notificationCard =
                createCard("Notifications");


        appointmentNotification =
                new JCheckBox(
                        "Appointment notifications"
                );

        billingNotification =
                new JCheckBox(
                        "Billing notifications"
                );

        pharmacyNotification =
                new JCheckBox(
                        "Pharmacy notifications"
                );


        appointmentNotification.setSelected(true);
        billingNotification.setSelected(true);
        pharmacyNotification.setSelected(true);


        styleCheckBox(
                appointmentNotification
        );

        styleCheckBox(
                billingNotification
        );

        styleCheckBox(
                pharmacyNotification
        );


        notificationCard.add(
                appointmentNotification
        );

        notificationCard.add(
                billingNotification
        );

        notificationCard.add(
                pharmacyNotification
        );


        mainPanel.add(notificationCard);


        mainPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 20)
                )
        );


        // =====================================================
        // APPEARANCE
        // =====================================================

        JPanel appearanceCard =
                createCard("Appearance");


        themeBox =
                new JComboBox<>(
                        new String[]{
                                "Light",
                                "Dark",
                                "System Default"
                        }
                );


        themeBox.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        themeBox.setPreferredSize(
                new Dimension(
                        250,
                        40
                )
        );


        appearanceCard.add(
                createField(
                        "Theme",
                        themeBox
                )
        );


        mainPanel.add(
                appearanceCard
        );


        mainPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 25)
                )
        );


        // =====================================================
        // BUTTONS
        // =====================================================

        JPanel actionPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                5
                        )
                );

        actionPanel.setOpaque(false);

        actionPanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        JButton saveButton =
                new JButton("Save Settings");

        JButton resetButton =
                new JButton("Reset");


        styleButton(
                saveButton,
                new Color(25, 118, 210)
        );

        styleButton(
                resetButton,
                new Color(100, 100, 100)
        );


        saveButton.addActionListener(
                e -> saveSettings()
        );


        resetButton.addActionListener(
                e -> resetSettings()
        );


        actionPanel.add(saveButton);

        actionPanel.add(resetButton);


        mainPanel.add(actionPanel);


        // =====================================================
        // SCROLL
        // =====================================================

        JScrollPane scrollPane =
                new JScrollPane(
                        mainPanel
                );

        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);


        add(
                scrollPane,
                BorderLayout.CENTER
        );
    }


    // =========================================================
    // CARD CREATION
    // =========================================================

    private JPanel createCard(String title) {

        JPanel card =
                new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBackground(Color.WHITE);

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

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        JLabel heading =
                new JLabel(title);

        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        21
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
                                15
                        )
                )
        );


        return card;
    }


    // =========================================================
    // FIELD CREATION
    // =========================================================

    private JPanel createField(
            String label,
            JComponent component
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
                                5
                        )
                );

        panel.setOpaque(false);

        panel.setBorder(
                new EmptyBorder(
                        5,
                        0,
                        5,
                        0
                )
        );


        JLabel labelComponent =
                new JLabel(label);

        labelComponent.setPreferredSize(
                new Dimension(
                        180,
                        35
                )
        );

        labelComponent.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );


        if (component instanceof JTextField) {

            component.setPreferredSize(
                    new Dimension(
                            350,
                            38
                    )
            );
        }


        panel.add(
                labelComponent,
                BorderLayout.WEST
        );

        panel.add(
                component,
                BorderLayout.CENTER
        );


        return panel;
    }


    // =========================================================
    // TEXT FIELD
    // =========================================================

    private JTextField createTextField(
            String value
    ) {

        JTextField field =
                new JTextField(value);

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );


        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        210,
                                        215,
                                        220
                                )
                        ),
                        new EmptyBorder(
                                5,
                                10,
                                5,
                                10
                        )
                )
        );


        return field;
    }


    // =========================================================
    // CHECKBOX STYLE
    // =========================================================

    private void styleCheckBox(
            JCheckBox checkBox
    ) {

        checkBox.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        checkBox.setBackground(
                Color.WHITE
        );

        checkBox.setFocusPainted(false);

        checkBox.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );
    }


    // =========================================================
    // BUTTON STYLE
    // =========================================================

    private void styleButton(
            JButton button,
            Color color
    ) {

        button.setBackground(color);

        button.setForeground(Color.WHITE);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setFocusPainted(false);

        button.setBorder(
                new EmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }


    // =========================================================
    // CHANGE PASSWORD
    // =========================================================

    private void showChangePasswordDialog() {

        JPasswordField oldPassword =
                new JPasswordField();

        JPasswordField newPassword =
                new JPasswordField();

        JPasswordField confirmPassword =
                new JPasswordField();


        JPanel panel =
                new JPanel(
                        new GridLayout(
                                3,
                                2,
                                10,
                                10
                        )
                );

        panel.setBorder(
                new EmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );


        panel.add(
                new JLabel("Current Password:")
        );

        panel.add(oldPassword);


        panel.add(
                new JLabel("New Password:")
        );

        panel.add(newPassword);


        panel.add(
                new JLabel("Confirm Password:")
        );

        panel.add(confirmPassword);


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Change Password",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );


        if (result == JOptionPane.OK_OPTION) {

            String newPass =
                    new String(
                            newPassword.getPassword()
                    );

            String confirmPass =
                    new String(
                            confirmPassword.getPassword()
                    );


            if (newPass.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "New password cannot be empty.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }


            if (!newPass.equals(confirmPass)) {

                JOptionPane.showMessageDialog(
                        this,
                        "New passwords do not match.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }


            JOptionPane.showMessageDialog(
                    this,
                    "Password changed successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }


    // =========================================================
    // SAVE SETTINGS
    // =========================================================

    private void saveSettings() {

        JOptionPane.showMessageDialog(
                this,
                "Settings saved successfully.",
                "Settings",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // =========================================================
    // RESET SETTINGS
    // =========================================================

    private void resetSettings() {

        hospitalNameField.setText(
                "City Hospital"
        );

        hospitalPhoneField.setText(
                "+91 9876543210"
        );

        hospitalEmailField.setText(
                "info@cityhospital.com"
        );

        hospitalAddressField.setText(
                "Howrah, West Bengal"
        );

        adminNameField.setText(
                "Administrator"
        );

        adminUsernameField.setText(
                "admin"
        );


        appointmentNotification.setSelected(
                true
        );

        billingNotification.setSelected(
                true
        );

        pharmacyNotification.setSelected(
                true
        );


        themeBox.setSelectedIndex(0);


        JOptionPane.showMessageDialog(
                this,
                "Settings reset successfully.",
                "Reset",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}