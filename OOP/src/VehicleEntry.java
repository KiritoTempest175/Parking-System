import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

abstract class Vehicle {
    private String DriverName;
    private String LicencePlate;
    private String ReceiptNo;
    private int SlotNo;
    private String Color;
    private float HourParked;
    private String CarType;

    Vehicle(String DriverName, String LicencePlate, String ReceiptNo, int SlotNo, String Color, float HourParked)
    {
        this.DriverName = DriverName;
        this.LicencePlate = LicencePlate;
        this.ReceiptNo = ReceiptNo;
        this.SlotNo = SlotNo;
        this.Color = Color;
        this.HourParked = HourParked;
    }

    public abstract String Type();
    public String getDriverName()
    {
        return DriverName;
    }
    public String getLicencePlate()
    {
        return LicencePlate;
    }
    public String getReceiptNo()
    {
        return ReceiptNo;
    }
    public int getSlotNo()
    {
        return SlotNo;
    }
    public String getColor()
    {
        return Color;
    }
    public float getHourParked()
    {
        return HourParked;
    }
}

interface Time{
    public String Entry();
    public String Exit();
}

class Car extends Vehicle implements Time{
    Car(String DriverName, String LicencePlate, String ReceiptNo, int SlotNo, String Color, float HourParked) {
        super(DriverName, LicencePlate, ReceiptNo, SlotNo, Color, HourParked);
    }

    @Override
    public String Type() {
        return "Car";
    }

    @Override
    public String Entry() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String Exit() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
class Bike extends Vehicle implements Time{
    Bike(String DriverName, String LicencePlate, String ReceiptNo, int SlotNo, String Color, float HourParked) {
        super(DriverName, LicencePlate, ReceiptNo, SlotNo, Color, HourParked);
    }

    @Override
    public String Type() {
        return "Bike";
    }

    @Override
    public String Entry() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String Exit() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
class Jeep extends Vehicle implements Time{
    Jeep(String DriverName, String LicencePlate, String ReceiptNo, int SlotNo, String Color, float HourParked) {
        super(DriverName, LicencePlate, ReceiptNo, SlotNo, Color, HourParked);
    }

    @Override
    public String Type() {
        return "Jeep";
    }

    @Override
    public String Entry() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String Exit() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
class Wagon extends Vehicle implements Time{
    Wagon(String DriverName, String LicencePlate, String ReceiptNo, int SlotNo, String Color, float HourParked) {
        super(DriverName, LicencePlate, ReceiptNo, SlotNo, Color, HourParked);
    }

    @Override
    public String Type() {
        return "Wagon";
    }

    @Override
    public String Entry() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String Exit() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
class Truck extends Vehicle implements Time{
    Truck(String DriverName, String LicencePlate, String ReceiptNo, int SlotNo, String Color, float HourParked) {
        super(DriverName, LicencePlate, ReceiptNo, SlotNo, Color, HourParked);
    }

    @Override
    public String Type() {
        return "Truck";
    }

    @Override
    public String Entry() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String Exit() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
public class VehicleEntry{
    public static void main(String[] args){
        String DriverName = null, LicencePlate = null, ReceiptNo = null, Color = null;
        int SlotNo = 0;
        float HourParked = 0;

        try{
            File file = new File("CarInfo.txt");
            FileReader reader = new FileReader(file);
            FileWriter writer = new FileWriter(file);

            writer.write("Driver Name: " + DriverName + "\nLicence Plate: " + LicencePlate + "Receipt No: " + ReceiptNo + "\nSlot No :" + SlotNo + "Color: " + Color + "\nHour Parked: " + HourParked);
            writer.close();
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }

    }
}