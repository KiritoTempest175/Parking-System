import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Exit extends JFrame {

    private JLabel titleLabel;
    private JTextField receiptNoField, paymentField;
    private JScrollPane displayScrollPane;
    private JTextPane displayPane;
    private JButton checkButton, updateButton, backButton;
    private JCheckBox paidCheckBox;

    public Exit() {
        initComponents();
        setupPlaceholders();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Exit Vehicle");
        setName("Exit");
        setResizable(false);

        initializeFields();
        initializeLabelsAndButtons();
        setupLayout();
    }

    private void initializeFields() {
        receiptNoField = new JTextField();
        paymentField = new JTextField();

        displayPane = new JTextPane();
        displayPane.setEditable(false);
        displayScrollPane = new JScrollPane(displayPane);

        paidCheckBox = new JCheckBox("Paid");
    }

    private void initializeLabelsAndButtons() {
        titleLabel = new JLabel();
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Exit Panel");
        titleLabel.setAlignmentY(0.0F);

        checkButton = new JButton("Check");
        checkButton.addActionListener(this::checkButtonClicked);

        updateButton = new JButton("Update");
        updateButton.addActionListener(this::updateButtonClicked);

        backButton = new JButton("← Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 12));
        backButton.addActionListener(this::backButtonClicked);
    }

    private void setupLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(backButton)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(79, 79, 79)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(receiptNoField, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                                        .addComponent(paymentField))
                                                .addGap(18, 18, 18)
                                                .addComponent(paidCheckBox))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(107, 107, 107)
                                                .addComponent(checkButton)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(displayScrollPane, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(updateButton)
                                .addGap(110, 110, 110))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(backButton)
                                .addGap(18, 18, 18)
                                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(65, 65, 65)
                                                .addComponent(displayScrollPane, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(106, 106, 106)
                                                .addComponent(receiptNoField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(50, 50, 50)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(paymentField)
                                                        .addComponent(paidCheckBox))
                                                .addGap(18, 18, 18)
                                                .addComponent(checkButton)))
                                .addGap(18, 18, 18)
                                .addComponent(updateButton)
                                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();
    }

    private void setupPlaceholders() {
        setupPlaceholder(receiptNoField, "Enter Receipt Number");

        setupPlaceholder(paymentField, "Enter Payment Amount");
    }

    private void setupPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }

    private void backButtonClicked(ActionEvent evt) {
        this.dispose();
        new AdminPanel().setVisible(true);
    }

    private String currentDriverName = "";
    private String currentLicensePlate = "";
    private int currentSlotNo = 0;
    private String currentVehicleType = "";
    private String currentEntryTime = "";
    private double currentPayment = 0.0;
    private float currentHoursParked = 0.0f;

    private void checkButtonClicked(ActionEvent evt) {
        String receiptNo = receiptNoField.getText().trim();
        if (receiptNo.isEmpty() || receiptNo.equals("Enter Receipt Number")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid receipt number!");
            return;
        }

        if (isVehicleAlreadyExited(receiptNo)) {
            displayPane.setText("VEHICLE ALREADY EXITED\n\n" +
                    "Receipt No: " + receiptNo + "\n" +
                    "Status: This vehicle has already been processed and exited.\n" +
                    "=====================================");
            JOptionPane.showMessageDialog(this, "This vehicle has already exited the parking system!");
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader("CarInfo.txt"));
            String line;
            StringBuilder vehicleInfo = new StringBuilder();
            boolean found = false;
            boolean inVehicleBlock = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains("=== VEHICLE ENTRY INFORMATION ===")) {
                    vehicleInfo = new StringBuilder();
                    inVehicleBlock = true;
                    vehicleInfo.append(line).append("\n");
                    continue;
                }

                if (inVehicleBlock) {
                    vehicleInfo.append(line).append("\n");

                    if (line.contains("Receipt No: " + receiptNo)) {
                        found = true;
                    }

                    if (line.startsWith("Driver Name: ")) {
                        currentDriverName = line.substring("Driver Name: ".length()).trim();
                    }
                    if (line.startsWith("License Plate: ")) {
                        currentLicensePlate = line.substring("License Plate: ".length()).trim();
                    }
                    if (line.startsWith("Slot No: ")) {
                        String slotInfo = line.substring("Slot No: ".length()).trim();
                        String[] parts = slotInfo.split(" ");
                        currentSlotNo = Integer.parseInt(parts[0]);
                    }
                    if (line.startsWith("Vehicle Type: ")) {
                        currentVehicleType = line.substring("Vehicle Type: ".length()).trim();
                    }
                    if (line.startsWith("Entry Time: ")) {
                        currentEntryTime = line.substring("Entry Time: ".length()).trim();
                    }

                    if (line.contains("=====================================") && vehicleInfo.length() > 50) {
                        if (found) {
                            calculatePaymentForVehicle();

                            StringBuilder displayInfo = new StringBuilder();
                            displayInfo.append("=== VEHICLE EXIT INFORMATION ===\n\n");
                            displayInfo.append("Receipt No: ").append(receiptNo).append("\n");
                            displayInfo.append("Driver Name: ").append(currentDriverName).append("\n");
                            displayInfo.append("License Plate: ").append(currentLicensePlate).append("\n");
                            displayInfo.append("Slot No: ").append(currentSlotNo).append("\n");
                            displayInfo.append("Vehicle Type: ").append(currentVehicleType).append("\n");
                            displayInfo.append("Entry Time: ").append(currentEntryTime).append("\n");
                            displayInfo.append("Hours Parked: ").append(String.format("%.2f", currentHoursParked)).append("\n");
                            displayInfo.append("Payment Due: Rs. ").append(String.format("%.2f", currentPayment)).append("\n");
                            displayInfo.append("Status: Ready for Exit\n");
                            displayInfo.append("=====================================\n");

                            displayPane.setText(displayInfo.toString());

                            paymentField.setText(String.format("%.2f", currentPayment));
                            paymentField.setForeground(Color.BLACK);

                            reader.close();
                            return;
                        }
                        inVehicleBlock = false;
                        found = false;
                    }
                }
            }
            reader.close();

            displayPane.setText("Receipt number not found!\nPlease check the receipt number and try again.");
            JOptionPane.showMessageDialog(this, "Receipt number '" + receiptNo + "' not found in records!");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading vehicle information: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error parsing vehicle data: " + e.getMessage());
        }
    }

    private void calculatePaymentForVehicle() {
        try {

            currentHoursParked = (float) Payment.getHoursParked(currentEntryTime);

            currentPayment = Payment.calculateVehiclePayment(currentVehicleType, currentEntryTime);

            System.out.println("Payment calculation:");
            System.out.println("Vehicle Type: " + currentVehicleType);
            System.out.println("Entry Time: " + currentEntryTime);
            System.out.println("Hours Parked: " + currentHoursParked);
            System.out.println("Calculated Payment: " + currentPayment);

        } catch (Exception e) {
            System.err.println("Error calculating payment: " + e.getMessage());
            e.printStackTrace();

            PaymentCalculator calculator = new PaymentCalculator();
            currentHoursParked = (float) calculator.calculateHoursParked(currentEntryTime);
            currentPayment = calculator.calculatePayment(currentVehicleType, currentHoursParked);
        }
    }

    private void updateButtonClicked(ActionEvent evt) {
        String receiptNo = receiptNoField.getText().trim();
        if (receiptNo.isEmpty() || receiptNo.equals("Enter Receipt Number")) {
            JOptionPane.showMessageDialog(this, "Please check vehicle details first!");
            return;
        }

        if (currentSlotNo == 0) {
            JOptionPane.showMessageDialog(this, "Please check vehicle details first!");
            return;
        }

        String paymentText = paymentField.getText().trim();
        if (paymentText.isEmpty() || paymentText.equals("Enter Payment Amount")) {
            JOptionPane.showMessageDialog(this, "Please enter payment amount!");
            return;
        }

        double enteredPayment;
        try {
            enteredPayment = Double.parseDouble(paymentText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid payment amount!");
            return;
        }

        if (enteredPayment < 0) {
            JOptionPane.showMessageDialog(this, "Payment amount cannot be negative!");
            return;
        }

        if (enteredPayment < currentPayment) {
            JOptionPane.showMessageDialog(this,
                    "Insufficient payment!\n" +
                            "Required: Rs. " + String.format("%.2f", currentPayment) + "\n" +
                            "Entered: Rs. " + String.format("%.2f", enteredPayment) + "\n" +
                            "Please pay the full amount.");
            return;
        } else if (enteredPayment > currentPayment) {
            double change = enteredPayment - currentPayment;
            int result = JOptionPane.showConfirmDialog(this,
                    "Excess payment received!\n" +
                            "Required: Rs. " + String.format("%.2f", currentPayment) + "\n" +
                            "Received: Rs. " + String.format("%.2f", enteredPayment) + "\n" +
                            "Change to return: Rs. " + String.format("%.2f", change) + "\n\n" +
                            "Proceed with exit?",
                    "Excess Payment", JOptionPane.YES_NO_OPTION);

            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        }

        if (!paidCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please confirm payment before updating!");
            return;
        }

        try {
            boolean slotUpdated = updateSlotStatus(currentSlotNo, false); // false means make available

            if (!slotUpdated) {
                JOptionPane.showMessageDialog(this, "Error updating slot status!");
                return;
            }

            LocalDateTime exitTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            StringBuilder exitInfo = new StringBuilder();
            exitInfo.append("=== VEHICLE EXIT COMPLETED ===\n");
            exitInfo.append("Receipt No: ").append(receiptNo).append("\n");
            exitInfo.append("Driver Name: ").append(currentDriverName).append("\n");
            exitInfo.append("License Plate: ").append(currentLicensePlate).append("\n");
            exitInfo.append("Slot No: ").append(currentSlotNo).append(" (Now Available)\n");
            exitInfo.append("Vehicle Type: ").append(currentVehicleType).append("\n");
            exitInfo.append("Entry Time: ").append(currentEntryTime).append("\n");
            exitInfo.append("Exit Time: ").append(exitTime.format(formatter)).append("\n");
            exitInfo.append("Hours Parked: ").append(String.format("%.2f", currentHoursParked)).append("\n");
            exitInfo.append("Required Payment: Rs. ").append(String.format("%.2f", currentPayment)).append("\n");
            exitInfo.append("Amount Received: Rs. ").append(paymentText).append("\n");

            if (enteredPayment > currentPayment) {
                exitInfo.append("Change Returned: Rs. ").append(String.format("%.2f", enteredPayment - currentPayment)).append("\n");
            }

            exitInfo.append("Payment Status: PAID\n");
            exitInfo.append("Status: Vehicle exited successfully\n");
            exitInfo.append("===============================\n\n");

            FileWriter writer = new FileWriter("ExitInfo.txt", true);
            writer.write(exitInfo.toString());
            writer.close();

            displayPane.setText(exitInfo.toString());

            String successMessage = "Vehicle exit processed successfully!\n" +
                    "Slot " + currentSlotNo + " is now available.\n" +
                    "Required Payment: Rs. " + String.format("%.2f", currentPayment) + "\n" +
                    "Amount Received: Rs. " + paymentText + " - PAID";

            if (enteredPayment > currentPayment) {
                successMessage += "\nChange to return: Rs. " + String.format("%.2f", enteredPayment - currentPayment);
            }

            JOptionPane.showMessageDialog(this, successMessage);

            receiptNoField.setText("Enter Receipt Number");
            receiptNoField.setForeground(Color.GRAY);
            paymentField.setText("Enter Payment Amount");
            paymentField.setForeground(Color.GRAY);
            paidCheckBox.setSelected(false);
            currentSlotNo = 0;
            currentDriverName = "";
            currentLicensePlate = "";
            currentVehicleType = "";
            currentEntryTime = "";
            currentPayment = 0.0;
            currentHoursParked = 0.0f;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving exit information: " + e.getMessage());
        }
    }

    private boolean isVehicleAlreadyExited(String receiptNo) {
        try {
            File exitFile = new File("ExitInfo.txt");
            if (!exitFile.exists()) {
                return false;
            }

            BufferedReader reader = new BufferedReader(new FileReader(exitFile));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains("Receipt No: " + receiptNo)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
            return false;

        } catch (IOException e) {
            System.err.println("Error checking exit records: " + e.getMessage());
            return false;
        }
    }

    private boolean updateSlotStatus(int slotNumber, boolean occupy) {
        ArrayList<Object> lines = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("parking_slots.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(" ");
                if (parts.length >= 4 && parts[0].equals("Slot")) {
                    int fileSlotId = Integer.parseInt(parts[1]);
                    if (fileSlotId == slotNumber) {
                        String category = parts[2];
                        String newStatus = occupy ? "Taken" : "Available";
                        lines.add("Slot " + slotNumber + " " + category + " " + newStatus);
                        updated = true;
                    } else {
                        lines.add(line);
                    }
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading parking_slots.txt file: " + e.getMessage());
            return false;
        }

        if (updated) {
            try (PrintWriter writer = new PrintWriter(new FileWriter("parking_slots.txt"))) {
                for (Object line : lines) {
                    writer.println(line);
                }
                System.out.println("Successfully updated slot " + slotNumber + " status to " + (occupy ? "Taken" : "Available"));
                return true;
            } catch (IOException e) {
                System.err.println("Error writing to parking_slots.txt file: " + e.getMessage());
            }
        }

        return false;
    }

    private class PaymentCalculator {
        private Map<String, Double> baseRates;

        public PaymentCalculator() {
            this.baseRates = new HashMap<>();
            loadRates();
        }

        private void loadRates() {

            try (BufferedReader reader = new BufferedReader(new FileReader("Payment.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");
                    if (parts.length >= 2) {
                        String vehicleType = parts[0].toLowerCase();
                        double rate = Double.parseDouble(parts[1]);
                        baseRates.put(vehicleType, rate);
                    }
                }
            } catch (IOException | NumberFormatException e) {
                setDefaultRates();
            }
        }

        private void setDefaultRates() {
            baseRates.put("car", 70.0);
            baseRates.put("bike", 50.0);
            baseRates.put("jeep", 100.0);
            baseRates.put("wagon", 150.0);
            baseRates.put("truck", 250.0);
            baseRates.put("handicap", 20.0);
        }

        public double calculatePayment(String vehicleType, double hoursParked) {
            String type = vehicleType.toLowerCase();
            double baseRate = baseRates.getOrDefault(type, 50.0);
            double actualHours = Math.max(hoursParked, 1.0);
            return baseRate * Math.ceil(actualHours);
        }

        public double calculateHoursParked(String entryTime) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime entry = LocalDateTime.parse(entryTime, formatter);
                LocalDateTime now = LocalDateTime.now();

                long minutes = java.time.temporal.ChronoUnit.MINUTES.between(entry, now);
                return Math.max(minutes / 60.0, 0.0);
            } catch (Exception e) {
                return 1.0;
            }
        }
    }

    public static void main(String[] args) {
        new LoginPage().setVisible(true);
    }
}