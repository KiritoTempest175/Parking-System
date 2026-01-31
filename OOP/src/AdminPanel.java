import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminPanel extends JFrame {

    private JLabel titleLabel;
    private JButton enterVehicleButton, exitVehicleButton, searchButton;

    public AdminPanel() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin Panel");
        setName("Admin");
        setResizable(false);

        titleLabel = new JLabel();
        enterVehicleButton = new JButton();
        exitVehicleButton = new JButton();
        searchButton = new JButton();

        titleLabel.setBackground(Color.BLACK);
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Admin Panel");
        titleLabel.setAlignmentY(0.0F);

        enterVehicleButton.setFont(new Font("JetBrains Mono NL ExtraBold", Font.BOLD, 20));
        enterVehicleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        enterVehicleButton.setText("Enter Vehicle");
        enterVehicleButton.addActionListener(this::enterVehicleButtonClicked);

        exitVehicleButton.setFont(new Font("JetBrains Mono NL ExtraBold", Font.BOLD, 20));
        exitVehicleButton.setText("Exit Vehicle");
        exitVehicleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitVehicleButton.addActionListener(this::exitVehicleButtonClicked);

        searchButton.setFont(new Font("JetBrains Mono NL ExtraBold", Font.BOLD, 20));
        searchButton.setText("Search");
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.addActionListener(this::searchButtonClicked);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(titleLabel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 234, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(exitVehicleButton, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(enterVehicleButton, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(searchButton, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
                                .addGap(216, 216, 216))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                                .addGap(91, 91, 91)
                                .addComponent(enterVehicleButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(exitVehicleButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57)
                                .addComponent(searchButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(80, Short.MAX_VALUE))
        );

        pack();
    }

    private void enterVehicleButtonClicked(ActionEvent evt) {
        new Entry().setVisible(true);
        this.dispose();
    }

    private void exitVehicleButtonClicked(ActionEvent evt) {
        new Exit().setVisible(true);
        this.dispose();
    }

    private void searchButtonClicked(ActionEvent evt) {
        new Search().setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
                new LoginPage().setVisible(true);
    }
}