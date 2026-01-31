import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Search extends JFrame {

    private JLabel titleLabel;
    private JTextField receiptField;
    private JTextPane resultPane;
    private JScrollPane scrollPane;
    private JButton searchButton, backButton;

    public Search() {
        initComponents();
        setupPlaceholder();
        setupFrame();
    }

    private void initComponents() {
        titleLabel = new JLabel("Search Vehicle");
        receiptField = new JTextField();
        resultPane = new JTextPane();
        searchButton = new JButton("Search");
        backButton = new JButton("← Back");

        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBackground(Color.BLACK);

        backButton.setFont(new Font("Arial", Font.PLAIN, 12));
        backButton.addActionListener(this::backButtonClicked);

        searchButton.addActionListener(this::searchButtonClicked);

        resultPane.setEditable(false);
        scrollPane = new JScrollPane(resultPane);

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(backButton)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(189, 189, 189)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(receiptField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                        .addComponent(scrollPane))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(300, 300, 300)
                                .addComponent(searchButton)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(backButton)
                                .addGap(18, 18, 18)
                                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(receiptField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 257, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(searchButton)
                                .addContainerGap(25, Short.MAX_VALUE))
        );

        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupPlaceholder() {
        receiptField.setText("Enter Receipt Number");
        receiptField.setForeground(Color.GRAY);

        receiptField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (receiptField.getText().equals("Enter Receipt Number")) {
                    receiptField.setText("");
                    receiptField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (receiptField.getText().isEmpty()) {
                    receiptField.setForeground(Color.GRAY);
                    receiptField.setText("Enter Receipt Number");
                }
            }
        });
    }

    private void backButtonClicked(ActionEvent evt) {
        this.dispose();
        new AdminPanel().setVisible(true);
    }

    private void searchButtonClicked(ActionEvent evt) {
        String receiptNo = receiptField.getText().trim();

        if (receiptNo.isEmpty() || receiptNo.equals("Enter Receipt Number")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid receipt number!");
            return;
        }

        searchVehicleDetails(receiptNo);
    }

    private void searchVehicleDetails(String receiptNo) {
        StringBuilder searchResults = new StringBuilder();
        boolean foundInCarInfo = false;
        boolean foundInExitInfo = false;

        foundInCarInfo = searchInCarInfo(receiptNo, searchResults);

        foundInExitInfo = searchInExitInfo(receiptNo, searchResults);

        if (!foundInCarInfo && !foundInExitInfo) {
            searchResults.append("=== SEARCH RESULTS ===\n\n");
            searchResults.append("Receipt Number: ").append(receiptNo).append("\n");
            searchResults.append("Status: NOT FOUND\n\n");
            searchResults.append("This receipt number was not found in our records.\n");
            searchResults.append("Please check the receipt number and try again.\n");
            searchResults.append("========================\n");

            JOptionPane.showMessageDialog(this, "Receipt number '" + receiptNo + "' not found in records!");
        }

        resultPane.setText(searchResults.toString());
    }

    private boolean searchInCarInfo(String receiptNo, StringBuilder results) {
        try {
            File carInfoFile = new File("CarInfo.txt");
            if (!carInfoFile.exists()) {
                return false;
            }

            BufferedReader reader = new BufferedReader(new FileReader(carInfoFile));
            String line;
            StringBuilder vehicleInfo = new StringBuilder();
            boolean found = false;
            boolean inVehicleBlock = false;

            String driverName = "";
            String licensePlate = "";
            String slotNo = "";
            String vehicleType = "";
            String entryTime = "";
            String hoursParked = "";
            String totalPayment = "";

            while ((line = reader.readLine()) != null) {
                if (line.contains("=== VEHICLE ENTRY INFORMATION ===")) {
                    vehicleInfo = new StringBuilder();
                    inVehicleBlock = true;
                    driverName = licensePlate = slotNo = vehicleType = entryTime = hoursParked = totalPayment = "";
                    continue;
                }

                if (inVehicleBlock) {
                    if (line.contains("Receipt No: " + receiptNo)) {
                        found = true;
                    }

                    if (line.startsWith("Driver Name: ")) {
                        driverName = line.substring("Driver Name: ".length()).trim();
                    }
                    if (line.startsWith("License Plate: ")) {
                        licensePlate = line.substring("License Plate: ".length()).trim();
                    }
                    if (line.startsWith("Slot No: ")) {
                        slotNo = line.substring("Slot No: ".length()).trim();
                    }
                    if (line.startsWith("Vehicle Type: ")) {
                        vehicleType = line.substring("Vehicle Type: ".length()).trim();
                    }
                    if (line.startsWith("Entry Time: ")) {
                        entryTime = line.substring("Entry Time: ".length()).trim();
                    }
                    if (line.startsWith("Hours Parked: ")) {
                        hoursParked = line.substring("Hours Parked: ".length()).trim();
                    }
                    if (line.startsWith("Total Payment: Rs. ")) {
                        totalPayment = line.substring("Total Payment: Rs. ".length()).trim();
                    }

                    if (line.contains("=====================================")) {
                        if (found) {
                            results.append("=== VEHICLE FOUND (CURRENTLY PARKED) ===\n\n");
                            results.append("Receipt No: ").append(receiptNo).append("\n");
                            results.append("Driver Name: ").append(driverName).append("\n");
                            results.append("License Plate: ").append(licensePlate).append("\n");
                            results.append("Slot No: ").append(slotNo).append("\n");
                            results.append("Vehicle Type: ").append(vehicleType).append("\n");
                            results.append("Entry Time: ").append(entryTime).append("\n");
                            results.append("Hours Parked: ").append(hoursParked).append("\n");
                            results.append("Total Payment Due: Rs. ").append(totalPayment).append("\n");
                            results.append("Status: CURRENTLY PARKED\n");
                            results.append("==========================================\n\n");

                            reader.close();
                            return true;
                        }
                        inVehicleBlock = false;
                        found = false;
                    }
                }
            }
            reader.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading vehicle information: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error processing vehicle data: " + e.getMessage());
        }

        return false;
    }

    private boolean searchInExitInfo(String receiptNo, StringBuilder results) {
        try {
            File exitInfoFile = new File("ExitInfo.txt");
            if (!exitInfoFile.exists()) {
                return false;
            }

            BufferedReader reader = new BufferedReader(new FileReader(exitInfoFile));
            String line;
            boolean found = false;
            boolean inExitBlock = false;

            String driverName = "";
            String licensePlate = "";
            String slotNo = "";
            String vehicleType = "";
            String entryTime = "";
            String exitTime = "";
            String hoursParked = "";
            String requiredPayment = "";
            String amountReceived = "";
            String changeReturned = "";
            String paymentStatus = "";

            while ((line = reader.readLine()) != null) {
                if (line.contains("=== VEHICLE EXIT COMPLETED ===")) {
                    inExitBlock = true;
                    driverName = licensePlate = slotNo = vehicleType = entryTime = exitTime =
                            hoursParked = requiredPayment = amountReceived = changeReturned = paymentStatus = "";
                    continue;
                }

                if (inExitBlock) {
                    if (line.contains("Receipt No: " + receiptNo)) {
                        found = true;
                    }

                    if (line.startsWith("Driver Name: ")) {
                        driverName = line.substring("Driver Name: ".length()).trim();
                    }
                    if (line.startsWith("License Plate: ")) {
                        licensePlate = line.substring("License Plate: ".length()).trim();
                    }
                    if (line.startsWith("Slot No: ")) {
                        slotNo = line.substring("Slot No: ".length()).trim();
                        // Remove "(Now Available)" if present
                        if (slotNo.contains("(")) {
                            slotNo = slotNo.substring(0, slotNo.indexOf("(")).trim();
                        }
                    }
                    if (line.startsWith("Vehicle Type: ")) {
                        vehicleType = line.substring("Vehicle Type: ".length()).trim();
                    }
                    if (line.startsWith("Entry Time: ")) {
                        entryTime = line.substring("Entry Time: ".length()).trim();
                    }
                    if (line.startsWith("Exit Time: ")) {
                        exitTime = line.substring("Exit Time: ".length()).trim();
                    }
                    if (line.startsWith("Hours Parked: ")) {
                        hoursParked = line.substring("Hours Parked: ".length()).trim();
                    }
                    if (line.startsWith("Required Payment: Rs. ")) {
                        requiredPayment = line.substring("Required Payment: Rs. ".length()).trim();
                    }
                    if (line.startsWith("Amount Received: Rs. ")) {
                        amountReceived = line.substring("Amount Received: Rs. ".length()).trim();
                    }
                    if (line.startsWith("Change Returned: Rs. ")) {
                        changeReturned = line.substring("Change Returned: Rs. ".length()).trim();
                    }
                    if (line.startsWith("Payment Status: ")) {
                        paymentStatus = line.substring("Payment Status: ".length()).trim();
                    }

                    if (line.contains("===============================")) {
                        if (found) {
                            results.append("=== VEHICLE FOUND (ALREADY EXITED) ===\n\n");
                            results.append("Receipt No: ").append(receiptNo).append("\n");
                            results.append("Driver Name: ").append(driverName).append("\n");
                            results.append("License Plate: ").append(licensePlate).append("\n");
                            results.append("Slot No: ").append(slotNo).append("\n");
                            results.append("Vehicle Type: ").append(vehicleType).append("\n");
                            results.append("Entry Time: ").append(entryTime).append("\n");
                            results.append("Exit Time: ").append(exitTime).append("\n");
                            results.append("Hours Parked: ").append(hoursParked).append("\n");
                            results.append("Required Payment: Rs. ").append(requiredPayment).append("\n");
                            results.append("Amount Received: Rs. ").append(amountReceived).append("\n");
                            if (!changeReturned.isEmpty()) {
                                results.append("Change Returned: Rs. ").append(changeReturned).append("\n");
                            }
                            results.append("Payment Status: ").append(paymentStatus).append("\n");
                            results.append("Status: VEHICLE EXITED\n");
                            results.append("========================================\n\n");

                            reader.close();
                            return true;
                        }
                        inExitBlock = false;
                        found = false;
                    }
                }
            }
            reader.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading exit information: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error processing exit data: " + e.getMessage());
        }

        return false;
    }

    private void setupFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Search Vehicle");
        setName("Search");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new LoginPage().setVisible(true);
    }
}