package ui;

import dao.LoginController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton;
    private JCheckBox showPassword;
    private JLabel dateLabel;

    public LoginFrame() {

        // ==========================
        // FRAME SETTINGS
        // ==========================

        setTitle("Hospital Management System");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);


        // ==========================
        // MAIN PANEL
        // ==========================

        JPanel mainPanel =
                new JPanel(new GridLayout(1, 2));


        // ==========================
        // LEFT PANEL
        // ==========================

        JPanel leftPanel =
                new JPanel();

        leftPanel.setBackground(
                new Color(25, 118, 210)
        );

        leftPanel.setLayout(
                new BorderLayout()
        );


        JLabel hospitalIcon =
                new JLabel(
                        "🏥",
                        SwingConstants.CENTER
                );

        hospitalIcon.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        120
                )
        );


        JLabel title =
                new JLabel(
                        "Hospital Management System",
                        SwingConstants.CENTER
                );

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );


        JLabel subtitle =
                new JLabel(
                        "Smart Healthcare Management",
                        SwingConstants.CENTER
                );

        subtitle.setForeground(Color.WHITE);

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );


        JPanel bottomPanel =
                new JPanel();

        bottomPanel.setOpaque(false);

        bottomPanel.setLayout(
                new BoxLayout(
                        bottomPanel,
                        BoxLayout.Y_AXIS
                )
        );


        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        bottomPanel.add(title);

        bottomPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 10)
                )
        );

        bottomPanel.add(subtitle);

        bottomPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 30)
                )
        );


        leftPanel.add(
                hospitalIcon,
                BorderLayout.CENTER
        );

        leftPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // ==========================
        // RIGHT PANEL
        // ==========================

        JPanel rightPanel =
                new JPanel();

        rightPanel.setBackground(
                Color.WHITE
        );

        rightPanel.setBorder(
                new EmptyBorder(
                        50,
                        60,
                        50,
                        60
                )
        );

        rightPanel.setLayout(
                new BoxLayout(
                        rightPanel,
                        BoxLayout.Y_AXIS
                )
        );


        // ==========================
        // LOGIN TITLE
        // ==========================

        JLabel loginTitle =
                new JLabel("LOGIN");

        loginTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        32
                )
        );


        // ==========================
        // WELCOME TEXT
        // ==========================

        JLabel welcome =
                new JLabel("Welcome Back");

        welcome.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        welcome.setForeground(
                Color.GRAY
        );

        welcome.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );


        // ==========================
        // DATE & TIME
        // ==========================

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd MMM yyyy   HH:mm"
                );

        dateLabel =
                new JLabel(
                        LocalDateTime
                                .now()
                                .format(formatter)
                );

        dateLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        dateLabel.setForeground(
                Color.GRAY
        );


        // ==========================
        // USERNAME FIELD
        // ==========================

        usernameField =
                new JTextField();

        usernameField.setMaximumSize(
                new Dimension(320, 40)
        );

        usernameField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );


        // ==========================
        // PASSWORD FIELD
        // ==========================

        passwordField =
                new JPasswordField();

        passwordField.setMaximumSize(
                new Dimension(320, 40)
        );

        passwordField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );


        // ==========================
        // SHOW PASSWORD
        // ==========================

        showPassword =
                new JCheckBox(
                        "Show Password"
                );

        showPassword.setBackground(
                Color.WHITE
        );


        showPassword.addActionListener(e -> {

            if (showPassword.isSelected()) {

                passwordField.setEchoChar(
                        (char) 0
                );

            } else {

                passwordField.setEchoChar(
                        '•'
                );
            }
        });


        // ==========================
        // LOGIN BUTTON
        // ==========================

        loginButton =
                new JButton(
                        "Secure Login"
                );

        loginButton.setMaximumSize(
                new Dimension(320, 45)
        );

        loginButton.setBackground(
                new Color(25, 118, 210)
        );

        loginButton.setForeground(
                Color.WHITE
        );

        loginButton.setFocusPainted(
                false
        );

        loginButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );


        // ==========================
        // EXIT BUTTON
        // ==========================

        exitButton =
                new JButton("Exit");

        exitButton.setMaximumSize(
                new Dimension(320, 45)
        );

        exitButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );


        exitButton.addActionListener(
                e -> System.exit(0)
        );


        // ==========================
        // LOGIN CONTROLLER
        // ==========================

        LoginController controller =
                new LoginController();


        // ==========================
        // LOGIN ACTION
        // ==========================

        loginButton.addActionListener(e -> {

            String username =
                    usernameField
                            .getText()
                            .trim();

            String password =
                    String.valueOf(
                            passwordField
                                    .getPassword()
                    );


            // ==========================
            // VALIDATION
            // ==========================

            if (
                    username.isEmpty()
                            ||
                            password.isEmpty()
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter Username and Password!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            // ==========================
            // LOGIN CHECK
            // ==========================

            if (
                    controller.login(
                            username,
                            password
                    )
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login Successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );


                dispose();


                new DashboardFrame();


            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Username or Password!",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        });


        // ==========================
        // ADD COMPONENTS
        // ==========================

        rightPanel.add(
                loginTitle
        );

        rightPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 10)
                )
        );


        rightPanel.add(
                welcome
        );

        rightPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 5)
                )
        );


        rightPanel.add(
                dateLabel
        );

        rightPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 30)
                )
        );


        rightPanel.add(
                new JLabel("Username")
        );

        rightPanel.add(
                usernameField
        );

        rightPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 20)
                )
        );


        rightPanel.add(
                new JLabel("Password")
        );

        rightPanel.add(
                passwordField
        );


        rightPanel.add(
                showPassword
        );

        rightPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 25)
                )
        );


        rightPanel.add(
                loginButton
        );

        rightPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 15)
                )
        );


        rightPanel.add(
                exitButton
        );


        // ==========================
        // ADD PANELS
        // ==========================

        mainPanel.add(
                leftPanel
        );

        mainPanel.add(
                rightPanel
        );


        add(mainPanel);


        // ==========================
        // SHOW WINDOW
        // ==========================

        setVisible(true);
    }
}