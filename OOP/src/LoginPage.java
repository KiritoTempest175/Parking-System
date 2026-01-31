import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LoginPage extends JFrame {

    private JPanel mainPanel;
    private JLabel titleLabel, emailLabel, passwordLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginPage() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login Page");
        setBackground(Color.WHITE);
        setLocationByPlatform(true);
        setName("Login");
        setResizable(false);

        titleLabel = new JLabel();
        emailLabel = new JLabel();
        passwordLabel = new JLabel();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton();

        titleLabel.setBackground(Color.BLACK);
        titleLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD | Font.ITALIC, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Parking Management System");
        titleLabel.setAlignmentY(0.0F);

        emailLabel.setFont(new Font("JetBrains Mono NL ExtraBold", Font.PLAIN, 12));
        emailLabel.setText("Email");

        passwordLabel.setFont(new Font("JetBrains Mono NL ExtraBold", Font.PLAIN, 12));
        passwordLabel.setText("Password");

        emailField.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.BLACK));
        emailField.setCursor(new Cursor(Cursor.TEXT_CURSOR));

        passwordField.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.BLACK));

        loginButton.setFont(new Font("JetBrains Mono NL ExtraBold", Font.BOLD, 12));
        loginButton.setText("Login");
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                loginButtonClicked(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(168, 168, 168)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(emailLabel)
                                                        .addComponent(emailField)
                                                        .addComponent(passwordLabel)
                                                        .addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(273, 273, 273)
                                                .addComponent(loginButton)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .addGap(76, 76, 76)
                                .addComponent(emailLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(emailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(passwordLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(loginButton)
                                .addContainerGap(118, Short.MAX_VALUE))
        );

        pack();
    }

    private void loginButtonClicked(MouseEvent evt) {

        Admin admin = new Admin();
        String username = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();

        String currentShift;
        String defaultShift = "X";
        if (hour >= 0 && hour < 8) {
            currentShift = "A";
        } else if (hour >= 8 && hour < 16) {
            currentShift = "B";
        } else {
            currentShift = "C";
        }

        boolean success = false;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("LoginInfo.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 4) {
                    String fileUsername = parts[0];
                    String filePassword = parts[1];
                    String shift = parts[2];
                    String currentAdmin = parts[3];

                    if (username.equals(fileUsername) &&
                            password.equals(filePassword) &&
                            currentShift.equals(shift) || defaultShift.equals(shift)) {

                        success = true;
                        break;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (success) {
            new AdminPanel().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials or not your shift.");
        }
    }

    public static void main(String[] args) {
                new LoginPage().setVisible(true);
    }
}