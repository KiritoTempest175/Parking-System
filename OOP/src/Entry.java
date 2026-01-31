import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Entry extends JFrame {
    private JTextField driverNameField, licensePlateField, receiptNoField, slotNoField;

    private JTextField colorField, hoursParkedField, entryTimeField;
    private JLabel titleLabel;
    private JComboBox<String> vehicleTypeCombo;
    private JRadioButton handicapRadio;
    private JScrollPane displayScrollPane;
    private JTextPane displayPane;
    private JButton enterButton, backButton;

    public Entry() {
        initComponents();
        setCurrentTime();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Entry Vehicle");
        setName("Entry");
        setResizable(false);
        setSize(new Dimension(700, 500));

        initializeFields();
        initializeLabelsAndButtons();
        setupLayout();
    }

    private void initializeFields() {

        driverNameField = new JTextField("Driver Name");
        licensePlateField = new JTextField("License Plate");
        receiptNoField = new JTextField("Receipt No.");
        slotNoField = new JTextField("Slot No.");
        colorField = new JTextField("Color");
        hoursParkedField = new JTextField("Hours Parked");
        entryTimeField = new JTextField();
        entryTimeField.setEditable(false);

        addPlaceholderBehavior(driverNameField, "Driver Name");
        addPlaceholderBehavior(licensePlateField, "License Plate");
        addPlaceholderBehavior(receiptNoField, "Receipt No.");
        addPlaceholderBehavior(slotNoField, "Slot No.");
        addPlaceholderBehavior(colorField, "Color");
        addPlaceholderBehavior(hoursParkedField, "Hours Parked");

        vehicleTypeCombo = new JComboBox<>(new String[]{"Car", "Bike", "Jeep", "Wagon", "Truck"});
        handicapRadio = new JRadioButton("Handicap");

        displayPane = new JTextPane();
        displayPane.setEditable(false);
        displayScrollPane = new JScrollPane(displayPane);
    }

    private void initializeLabelsAndButtons() {
        titleLabel = new JLabel();
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Entry Panel");
        titleLabel.setAlignmentY(0.0F);

        enterButton = new JButton("Enter");
        enterButton.addActionListener(this::enterButtonClicked);

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
                                .addGap(54, 54, 54)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(driverNameField, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(licensePlateField, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(receiptNoField, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(slotNoField, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(colorField, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                        .addComponent(hoursParkedField, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                        .addComponent(vehicleTypeCombo, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(entryTimeField))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(displayScrollPane, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(180, 180, 180)
                                .addComponent(handicapRadio, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(enterButton)
                                .addGap(105, 105, 105))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
                                .addContainerGap())
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(backButton)
                                .addGap(18, 18, 18)
                                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                                .addGap(78, 78, 78)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(driverNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(colorField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGap(54, 54, 54)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(hoursParkedField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(licensePlateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGap(57, 57, 57)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(receiptNoField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(entryTimeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGap(56, 56, 56)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(slotNoField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(vehicleTypeCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(displayScrollPane, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                                .addComponent(handicapRadio)
                                                .addGap(24, 24, 24))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(enterButton)
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }

    private void setCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        entryTimeField.setText(now.format(formatter));
    }

    private void addPlaceholderBehavior(JTextField field, String placeholder) {
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }

    private boolean isPlaceholderText(JTextField field, String placeholder) {
        return field.getText().equals(placeholder) || field.getText().trim().isEmpty();
    }

    private void resetFieldToPlaceholder(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
    }

    private void backButtonClicked(ActionEvent evt) {
        this.dispose();
        new AdminPanel().setVisible(true);
    }

    private boolean isSlotAlreadyUsed(int slotNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader("CarInfo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("Slot No: " + slotNumber)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Note: CarInfo.txt file not found or cannot be read. Assuming slot is available.");
            return false;
        }
        return false;
    }

    private String checkSlotAvailability(int slotNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader("parking_slots.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(" ");
                if (parts.length >= 4 && parts[0].equals("Slot")) {
                    int fileSlotId = Integer.parseInt(parts[1]);
                    if (fileSlotId == slotNumber) {
                        return parts[3];
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading parking_slots.txt file: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Error: Cannot read parking slots file. Please ensure parking_slots.txt exists.",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing slot number from file");
        }
        return "INVALID";
    }

    private String getSlotType(int slotNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader("parking_slots.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(" ");
                if (parts.length >= 4 && parts[0].equals("Slot")) {
                    int fileSlotId = Integer.parseInt(parts[1]);
                    if (fileSlotId == slotNumber) {
                        return parts[2];
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading slot file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing slot number from file");
        }
        return "General";
    }

    private boolean updateSlotStatus(int slotNumber, boolean occupy) {
        java.util.List<String> lines = new java.util.ArrayList<>();
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
            JOptionPane.showMessageDialog(null,
                    "Error reading parking slots file: " + e.getMessage(),
                    "File Read Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (updated) {
            try (PrintWriter writer = new PrintWriter(new FileWriter("parking_slots.txt"))) {
                for (String line : lines) {
                    writer.println(line);
                }
                System.out.println("Successfully updated slot " + slotNumber + " status to " + (occupy ? "Taken" : "Available"));
                return true;
            } catch (IOException e) {
                System.err.println("Error writing to parking_slots.txt file: " + e.getMessage());
                JOptionPane.showMessageDialog(null,
                        "Error updating parking slots file: " + e.getMessage(),
                        "File Write Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.err.println("Slot " + slotNumber + " not found in parking_slots.txt for update");
        }

        return false;
    }

    private void enterButtonClicked(ActionEvent evt) {
        try {
            if (isPlaceholderText(driverNameField, "Driver Name") ||
                    isPlaceholderText(licensePlateField, "License Plate") ||
                    isPlaceholderText(receiptNoField, "Receipt No.") ||
                    isPlaceholderText(slotNoField, "Slot No.") ||
                    isPlaceholderText(colorField, "Color") ||
                    isPlaceholderText(hoursParkedField, "Hours Parked")) {

                JOptionPane.showMessageDialog(this, "Please fill in all fields!");
                return;
            }

            String driverName = driverNameField.getText().trim();
            String licensePlate = licensePlateField.getText().trim();
            String receiptNo = receiptNoField.getText().trim();
            int slotNo = Integer.parseInt(slotNoField.getText().trim());
            String color = colorField.getText().trim();
            float hoursParked = Float.parseFloat(hoursParkedField.getText().trim());
            String vehicleType = (String) vehicleTypeCombo.getSelectedItem();
            String entryTime = entryTimeField.getText();
            String handicap = handicapRadio.isSelected() ? "Yes" : "No";

            String slotStatus = checkSlotAvailability(slotNo);

            if (slotStatus.equals("INVALID")) {
                JOptionPane.showMessageDialog(this,
                        "Invalid slot number! Slot " + slotNo + " does not exist.",
                        "Slot Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (slotStatus.equals("Taken")) {
                JOptionPane.showMessageDialog(this,
                        "Slot " + slotNo + " is already occupied! Please choose another slot.",
                        "Slot Occupied",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (isSlotAlreadyUsed(slotNo)) {
                JOptionPane.showMessageDialog(this,
                        "Slot " + slotNo + " has already been used for vehicle entry!\n" +
                                "Each slot can only be used once. Please choose a different slot.",
                        "Duplicate Slot Entry",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String slotType = getSlotType(slotNo);
            if (handicap.equals("Yes") && !slotType.equals("Handicap")) {
                int response = JOptionPane.showConfirmDialog(this,
                        "You selected handicap parking but slot " + slotNo + " is a regular slot.\n" +
                                "Do you want to continue with this regular slot?",
                        "Slot Type Mismatch",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (response != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            if (handicap.equals("No") && slotType.equals("Handicap")) {
                int response = JOptionPane.showConfirmDialog(this,
                        "Slot " + slotNo + " is reserved for handicapped parking.\n" +
                                "Do you want to continue parking in this handicapped slot?",
                        "Handicapped Slot Warning",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (response != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            Vehicle vehicle = null;

            switch (vehicleType.toLowerCase()) {
                case "car":
                    vehicle = new Car(driverName, licensePlate, receiptNo, slotNo, color, hoursParked);
                    break;
                case "bike":
                    vehicle = new Bike(driverName, licensePlate, receiptNo, slotNo, color, hoursParked);
                    break;
                case "jeep":
                    vehicle = new Jeep(driverName, licensePlate, receiptNo, slotNo, color, hoursParked);
                    break;
                case "wagon":
                    vehicle = new Wagon(driverName, licensePlate, receiptNo, slotNo, color, hoursParked);
                    break;
                case "truck":
                    vehicle = new Truck(driverName, licensePlate, receiptNo, slotNo, color, hoursParked);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Unknown vehicle type selected!");
                    return;
            }

            LocalDateTime now = LocalDateTime.now();
            int hour = now.getHour();
            String currentShift;
            if (hour >= 0 && hour < 8) {
                currentShift = "A";
            } else if (hour >= 8 && hour < 16) {
                currentShift = "B";
            } else {
                currentShift = "C";
            }

            String currentAdminName = "UNKNOWN";
            try {
                BufferedReader reader = new BufferedReader(new FileReader("LoginInfo.txt"));
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");
                    if (parts.length == 4) {
                        switch (currentShift) {
                            case "A": {currentAdminName = "Huzaifa"; break;}
                            case "B": {currentAdminName = "Maaz"; break;}
                            case "C": {currentAdminName = "Ijlal"; break;}
                            default: {currentAdminName = "Admin";}
                        }
                    }
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }

            double baseRate = getBaseRateForVehicle(vehicleType);
            double totalPayment = baseRate * hoursParked;

            if (handicap.equals("Yes")) {
                totalPayment = 20 * hoursParked;
            }

            StringBuilder info = new StringBuilder();
            info.append("=== VEHICLE ENTRY INFORMATION ===\n\n");
            info.append("Driver Name: ").append(driverName).append("\n");
            info.append("License Plate: ").append(licensePlate).append("\n");
            info.append("Receipt No: ").append(receiptNo).append("\n");
            info.append("Slot No: ").append(slotNo).append(" (").append(slotType).append(")\n");
            info.append("Color: ").append(color).append("\n");
            info.append("Hours Parked: ").append(hoursParked).append("\n");
            info.append("Vehicle Type: ").append(vehicle.Type()).append("\n");
            info.append("Entry Time: ").append(entryTime).append("\n");
            info.append("Handicap: ").append(handicap).append("\n");
            info.append("Current Admin: ").append(currentAdminName).append("\n");
            info.append("=====================================\n");
            info.append("PAYMENT CALCULATION:\n");
            info.append("Base Rate: Rs. ").append(String.format("%.2f", baseRate)).append(" per hour\n");
            info.append("Hours: ").append(hoursParked).append("\n");
            if (handicap.equals("Yes")) {
                info.append("Handicap Rate Applied: Rs. 20 per hour\n");
            }
            info.append("Total Payment: Rs. ").append(String.format("%.2f", totalPayment)).append("\n");
            info.append("=====================================\n");

            displayPane.setText(info.toString());

            try {
                boolean slotUpdated = updateSlotStatus(slotNo, true);

                if (!slotUpdated) {
                    JOptionPane.showMessageDialog(this,
                            "Error: Could not update slot status. Entry cancelled.",
                            "Slot Update Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                FileWriter writer = new FileWriter("CarInfo.txt", true);
                writer.write(info.toString());
                writer.write("\n");
                writer.close();

                JOptionPane.showMessageDialog(this,
                        "Vehicle entry recorded successfully!\nSlot " + slotNo + " is now occupied.\nTotal Payment: Rs. " + String.format("%.2f", totalPayment));

                clearFields();

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving to file: " + e.getMessage());
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Slot No and Hours Parked!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage());
        }
    }

    private double getBaseRateForVehicle(String vehicleType) {
        switch (vehicleType.toLowerCase()) {
            case "car":
                return 70.0;
            case "bike":
                return 50.0;
            case "jeep":
                return 100.0;
            case "wagon":
                return 150.0;
            case "truck":
                return 250.0;
            default:
                return 70.0;
        }
    }

    private void clearFields() {
        resetFieldToPlaceholder(driverNameField, "Driver Name");
        resetFieldToPlaceholder(licensePlateField, "License Plate");
        resetFieldToPlaceholder(receiptNoField, "Receipt No.");
        resetFieldToPlaceholder(slotNoField, "Slot No.");
        resetFieldToPlaceholder(colorField, "Color");
        resetFieldToPlaceholder(hoursParkedField, "Hours Parked");

        handicapRadio.setSelected(false);
        vehicleTypeCombo.setSelectedIndex(0);
        setCurrentTime();
    }

    public static void main(String[] args) {
        new LoginPage().setVisible(true);
    }

}