import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Slot {
    private int slotId;
    private boolean isOccupied;
    private String category;

    public Slot(int slotId, String category) {
        this.slotId = slotId;
        this.category = category;
        this.isOccupied = false;
    }

    public boolean parkVehicle() {
        if (isOccupied) {
            return false;
        }

        this.isOccupied = true;
        return true;
    }

    public boolean removeVehicle() {
        if (!isOccupied) {
            return false;
        }

        this.isOccupied = false;
        return true;
    }

    public String getSlotStatus() {
        if (isOccupied) {
            return "OCCUPIED";
        } else if (category.equals("HANDICAPPED")) {
            return "HANDICAPPED_AVAILABLE";
        } else {
            return "AVAILABLE";
        }
    }

    public String getStatusForFile() {
        String slotType = category.equals("HANDICAPPED") ? "Handicap" : "General";
        String status = isOccupied ? "Taken" : "Available";
        return "Slot " + slotId + " " + slotType + " " + status;
    }

    public int getSlotId() { return slotId; }
    public boolean isOccupied() { return isOccupied; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        String categoryStr = category.equals("HANDICAPPED") ? " [HANDICAPPED]" : "";
        String status = isOccupied ? "OCCUPIED" : "AVAILABLE";
        return "Slot " + slotId + categoryStr + " [" + status + "]";
    }
}

class SlotManager {
    private ArrayList<Slot> slots;
    private static final int TOTAL_SLOTS = 100;
    private static final int HANDICAPPED_SLOTS = 10;
    private static final String DATA_FILE = "parking_slots.txt";

    public SlotManager() {
        slots = new ArrayList<>(TOTAL_SLOTS);
        initializeSlots();
        loadExistingData();
    }

    private void initializeSlots() {
        for (int i = 1; i <= HANDICAPPED_SLOTS; i++) {
            slots.add(new Slot(i, "HANDICAPPED"));
        }
        for (int i = HANDICAPPED_SLOTS + 1; i <= TOTAL_SLOTS; i++) {
            slots.add(new Slot(i, "REGULAR"));
        }
    }

    private void loadExistingData() {
        File dataFile = new File(DATA_FILE);
        if (!dataFile.exists()) {
            saveToFile();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                processDataLine(line.trim());
            }

        } catch (IOException e) {
        }
    }

    private boolean processDataLine(String line) {
        try {
            String[] parts = line.split(" ");
            if (parts.length >= 4 && parts[0].equals("Slot")) {
                int slotId = Integer.parseInt(parts[1]);
                String status = parts[3];

                if (slotId >= 1 && slotId <= TOTAL_SLOTS && status.equals("Taken")) {
                    Slot slot = slots.get(slotId - 1);
                    return slot.parkVehicle();
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Slot slot : slots) {
                writer.println(slot.getStatusForFile());
            }
        } catch (IOException e) {
        }
    }

    public List<Integer> findAvailableSlots() {
        List<Integer> availableSlots = new ArrayList<>();
        for (Slot slot : slots) {
            if (slot.getSlotStatus().equals("AVAILABLE")) {
                availableSlots.add(slot.getSlotId());
            }
        }
        return availableSlots;
    }

    public List<Integer> findAvailableHandicappedSlots() {
        List<Integer> availableHandicappedSlots = new ArrayList<>();
        for (Slot slot : slots) {
            if (slot.getSlotStatus().equals("HANDICAPPED_AVAILABLE")) {
                availableHandicappedSlots.add(slot.getSlotId());
            }
        }
        return availableHandicappedSlots;
    }

    public List<Integer> getOccupiedHandicappedSlots() {
        List<Integer> occupiedHandicappedSlots = new ArrayList<>();
        for (Slot slot : slots) {
            if (slot.getCategory().equals("HANDICAPPED") && slot.getSlotStatus().equals("OCCUPIED")) {
                occupiedHandicappedSlots.add(slot.getSlotId());
            }
        }
        return occupiedHandicappedSlots;
    }

    public boolean parkVehicle(int slotNumber) {
        if (slotNumber < 1 || slotNumber > TOTAL_SLOTS) {
            return false;
        }

        Slot slot = slots.get(slotNumber - 1);
        if (slot.getSlotStatus().equals("OCCUPIED")) {
            return false;
        }

        boolean success = slot.parkVehicle();
        if (success) {
            saveToFile();
        }
        return success;
    }

    public int parkVehicleAuto(boolean needHandicapped) {
        List<Integer> availableSlots = needHandicapped ? findAvailableHandicappedSlots() : findAvailableSlots();
        if (availableSlots.isEmpty()) {
            return -1;
        }

        int slotNumber = availableSlots.get(0);
        boolean success = parkVehicle(slotNumber);
        return success ? slotNumber : -1;
    }

    public boolean removeVehicle(int slotNumber) {
        if (slotNumber < 1 || slotNumber > TOTAL_SLOTS) {
            return false;
        }

        Slot slot = slots.get(slotNumber - 1);
        if (!slot.getSlotStatus().equals("OCCUPIED")) {
            return false;
        }

        boolean success = slot.removeVehicle();
        if (success) {
            saveToFile();
        }
        return success;
    }

    public void displayParkingStatus() {
        int totalOccupied = 0, regularOccupied = 0, handicappedOccupied = 0;
        int regularAvailable = 0, handicappedAvailable = 0;

        for (Slot slot : slots) {
            String status = slot.getSlotStatus();
            if (status.equals("OCCUPIED")) {
                totalOccupied++;
                if (slot.getCategory().equals("HANDICAPPED")) {
                    handicappedOccupied++;
                } else {
                    regularOccupied++;
                }
            } else if (status.equals("AVAILABLE")) {
                regularAvailable++;
            } else if (status.equals("HANDICAPPED_AVAILABLE")) {
                handicappedAvailable++;
            }
        }
    }

    public void displayFileContents() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
            }
        } catch (IOException e) {
        }
    }

    public Slot getSlot(int slotNumber) {
        if (slotNumber >= 1 && slotNumber <= TOTAL_SLOTS) {
            return slots.get(slotNumber - 1);
        }
        return null;
    }

    public int getTotalSlots() {
        return TOTAL_SLOTS;
    }

    public static int getHandicappedSlotCount() {
        return HANDICAPPED_SLOTS;
    }
}

public class SlotManagement {
    public static void main(String[] args) {
        SlotManager manager = new SlotManager();

        manager.parkVehicle(1);
        manager.parkVehicle(15);
        manager.parkVehicle(25);
        manager.parkVehicleAuto(true);
        manager.parkVehicleAuto(false);

        manager.displayParkingStatus();

        manager.removeVehicle(15);

        manager.displayFileContents();

        manager.displayParkingStatus();
    }
}