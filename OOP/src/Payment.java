import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

interface Penalty {
    double getCarPenalty(double amount);
    double getBikePenalty(double amount);
    double getJeepPenalty(double amount);
    double getWagonPenalty(double amount);
    double getTruckPenalty(double amount);
    double getHandicapPenalty(double amount);
}

class PaymentMethod implements Penalty {
    private double baseAmount;

    public PaymentMethod() {
        this.baseAmount = 0;
    }

    public PaymentMethod(double baseAmount) {
        this.baseAmount = baseAmount;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    @Override
    public double getCarPenalty(double amount) {
        return amount * 0.5;
    }

    @Override
    public double getBikePenalty(double amount) {
        return amount * 0.3;
    }

    @Override
    public double getJeepPenalty(double amount) {
        return amount * 0.6;
    }

    @Override
    public double getWagonPenalty(double amount) {
        return amount * 2.0;
    }

    @Override
    public double getTruckPenalty(double amount) {
        return amount * 1.5;
    }

    @Override
    public double getHandicapPenalty(double amount) {
        return 100;
    }
}

class PaymentCalculator {
    private Map<String, Double> baseRates;
    private Map<String, Double> penaltyRates;
    private PaymentMethod paymentMethod;

    public PaymentCalculator() {
        this.baseRates = new HashMap<>();
        this.penaltyRates = new HashMap<>();
        this.paymentMethod = new PaymentMethod();
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
        } catch (IOException e) {
            System.err.println("Error loading payment rates: " + e.getMessage());
            setDefaultRates();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing payment rates: " + e.getMessage());
            setDefaultRates();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("Penalty.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    String vehicleType = parts[0].toLowerCase();
                    double penalty = Double.parseDouble(parts[1]);
                    penaltyRates.put(vehicleType, penalty);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading penalty rates: " + e.getMessage());
            setDefaultPenalties();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing penalty rates: " + e.getMessage());
            setDefaultPenalties();
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

    private void setDefaultPenalties() {
        penaltyRates.put("car", 35.0);
        penaltyRates.put("bike", 15.0);
        penaltyRates.put("jeep", 60.0);
        penaltyRates.put("wagon", 300.0);
        penaltyRates.put("truck", 375.0);
        penaltyRates.put("handicap", 100.0);
    }

    public double calculatePayment(String vehicleType, double hoursParked) {
        return calculatePayment(vehicleType, hoursParked, false);
    }

    public double calculatePayment(String vehicleType, double hoursParked, boolean applyPenalty) {
        String type = vehicleType.toLowerCase();
        double baseRate = baseRates.getOrDefault(type, 50.0); // Default rate if type not found

        double actualHours = Math.max(hoursParked, 1.0);
        double basePayment = baseRate * Math.ceil(actualHours); // Round up to next hour

        if (applyPenalty) {
            double penalty = penaltyRates.getOrDefault(type, basePayment * 0.5);
            return basePayment + penalty;
        }

        return basePayment;
    }

    public double calculateHoursParked(String entryTime) {
        return calculateHoursParked(entryTime, LocalDateTime.now());
    }

    public double calculateHoursParked(String entryTime, LocalDateTime exitTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime entry = LocalDateTime.parse(entryTime, formatter);

            long minutes = ChronoUnit.MINUTES.between(entry, exitTime);
            return Math.max(minutes / 60.0, 0.0);

        } catch (Exception e) {
            System.err.println("Error calculating hours parked: " + e.getMessage());
            return 1.0;
        }
    }

    public double getBaseRate(String vehicleType) {
        return baseRates.getOrDefault(vehicleType.toLowerCase(), 50.0);
    }

    public double getPenaltyRate(String vehicleType) {
        return penaltyRates.getOrDefault(vehicleType.toLowerCase(), 25.0);
    }
}

public class Payment {
    public static void main(String[] args) {
        PaymentMethod car = new PaymentMethod(70);
        PaymentMethod bike = new PaymentMethod(50);
        PaymentMethod jeep = new PaymentMethod(100);
        PaymentMethod wagon = new PaymentMethod(150);
        PaymentMethod truck = new PaymentMethod(250);
        PaymentMethod handicap = new PaymentMethod(20);
        PaymentMethod penaltyCalculator = new PaymentMethod();

        createPaymentRatesFile(car, bike, jeep, wagon, truck, handicap);

        createPenaltyRatesFile(penaltyCalculator, car, bike, jeep, wagon, truck, handicap);
    }

    private static void createPaymentRatesFile(PaymentMethod car, PaymentMethod bike,
                                               PaymentMethod jeep, PaymentMethod wagon,
                                               PaymentMethod truck, PaymentMethod handicap) {
        try (FileWriter writer = new FileWriter("Payment.txt")) {
            writer.write("Car " + car.getBaseAmount() + "\n");
            writer.write("Bike " + bike.getBaseAmount() + "\n");
            writer.write("Jeep " + jeep.getBaseAmount() + "\n");
            writer.write("Wagon " + wagon.getBaseAmount() + "\n");
            writer.write("Truck " + truck.getBaseAmount() + "\n");
            writer.write("Handicap " + handicap.getBaseAmount() + "\n");
        } catch (IOException e) {
            System.err.println("Error creating payment rates file: " + e.getMessage());
        }
    }

    private static void createPenaltyRatesFile(PaymentMethod penaltyCalculator,
                                               PaymentMethod car, PaymentMethod bike,
                                               PaymentMethod jeep, PaymentMethod wagon,
                                               PaymentMethod truck, PaymentMethod handicap) {
        try (FileWriter writer = new FileWriter("Penalty.txt")) {
            writer.write("Car " + penaltyCalculator.getCarPenalty(car.getBaseAmount()) + "\n");
            writer.write("Bike " + penaltyCalculator.getBikePenalty(bike.getBaseAmount()) + "\n");
            writer.write("Jeep " + penaltyCalculator.getJeepPenalty(jeep.getBaseAmount()) + "\n");
            writer.write("Wagon " + penaltyCalculator.getWagonPenalty(wagon.getBaseAmount()) + "\n");
            writer.write("Truck " + penaltyCalculator.getTruckPenalty(truck.getBaseAmount()) + "\n");
            writer.write("Handicap " + penaltyCalculator.getHandicapPenalty(handicap.getBaseAmount()) + "\n");
        } catch (IOException e) {
            System.err.println("Error creating penalty rates file: " + e.getMessage());
        }
    }

    private static void testPaymentCalculator() {
        PaymentCalculator calculator = new PaymentCalculator();

        // Test different scenarios
        String[] vehicleTypes = {"Car", "Bike", "Jeep", "Wagon", "Truck", "Handicap"};
        double[] hours = {1.5, 2.0, 3.5, 5.0, 0.5};

        for (String vehicle : vehicleTypes) {
            for (double hour : hours) {
                double payment = calculator.calculatePayment(vehicle, hour);
                double penaltyPayment = calculator.calculatePayment(vehicle, hour, true);
            }
        }
    }

    public static double calculateVehiclePayment(String vehicleType, String entryTime) {
        PaymentCalculator calculator = new PaymentCalculator();
        double hoursParked = calculator.calculateHoursParked(entryTime);
        return calculator.calculatePayment(vehicleType, hoursParked);
    }

    public static double calculateVehiclePayment(String vehicleType, String entryTime,
                                                 LocalDateTime exitTime) {
        PaymentCalculator calculator = new PaymentCalculator();
        double hoursParked = calculator.calculateHoursParked(entryTime, exitTime);
        return calculator.calculatePayment(vehicleType, hoursParked);
    }

    public static double getHoursParked(String entryTime) {
        PaymentCalculator calculator = new PaymentCalculator();
        return calculator.calculateHoursParked(entryTime);
    }
}