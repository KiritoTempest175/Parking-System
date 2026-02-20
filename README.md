# 🅿️ Parking Management System

A desktop **Parking Management System** built with **Java Swing**, designed for university OOP coursework. The application provides a complete GUI-based solution for managing vehicle parking operations including entry, exit, payments, penalties, and slot management — all backed by file-based data persistence.

---

## 📋 Table of Contents

- [Features](#-features)
- [Screenshots & Workflow](#-screenshots--workflow)
- [Project Structure](#-project-structure)
- [Architecture & OOP Concepts](#-architecture--oop-concepts)
- [Parking Rates](#-parking-rates)
- [Shift Management](#-shift-management)
- [Getting Started](#-getting-started)
- [Usage Guide](#-usage-guide)
- [Data Files](#-data-files)
- [Technologies Used](#-technologies-used)
- [Contributors](#-contributors)

---

## ✨ Features

- **🔐 Shift-Based Admin Authentication** — Time-based login system with 3 shifts (A, B, C) and a master admin override.
- **🚗 Vehicle Entry Management** — Register vehicles with driver details, license plate, slot assignment, and automatic time stamping.
- **🚪 Vehicle Exit Processing** — Look up parked vehicles by receipt, calculate payment based on actual hours, and process checkout.
- **🔍 Vehicle Search** — Search for any vehicle (parked or exited) using their receipt number.
- **💰 Dynamic Payment Calculation** — Hourly rates that vary by vehicle type, with automatic payment computation.
- **⚠️ Penalty System** — Configurable penalty rates per vehicle type for overstay or violations.
- **♿ Handicap Slot Support** — 10 dedicated handicap parking slots (out of 100 total) with special reduced rates.
- **📊 Slot Management** — Real-time tracking of 100 parking slots with availability status.
- **📁 File-Based Persistence** — All data (entries, exits, payments, slot status) stored in `.txt` files for simplicity.

---

## 🔄 Screenshots & Workflow

The application follows this user flow:

```
┌──────────────┐     ┌──────────────┐     ┌──────────────────────────────────┐
│  Login Page  │────▶│  Admin Panel │────▶│  Enter Vehicle  │  Exit Vehicle  │
│  (Shift Auth)│     │  (Dashboard) │     │  Search Vehicle                  │
└──────────────┘     └──────────────┘     └──────────────────────────────────┘
```

### Login Page
- Enter email and password credentials.
- System automatically validates against the current shift schedule.
- Master admin (`admin@gmail.com`) can log in during any shift.

### Admin Panel
Three main operations available:
1. **Enter Vehicle** — Register a new vehicle into the parking system.
2. **Exit Vehicle** — Process a vehicle's departure with payment.
3. **Search** — Look up vehicle details by receipt number.

### Entry Panel
- Input: Driver Name, License Plate, Receipt No., Slot No., Color, Hours Parked, Vehicle Type, Handicap status.
- Auto-captures current date/time as entry timestamp.
- Validates slot availability before assignment.
- Calculates and displays total payment.

### Exit Panel
- Input: Receipt Number to look up vehicle.
- Automatically calculates hours parked and payment due.
- Validates payment amount (handles exact, excess, and insufficient payments).
- Frees the parking slot upon successful exit.

### Search Panel
- Input: Receipt Number.
- Searches both active entries (`CarInfo.txt`) and exit records (`ExitInfo.txt`).
- Displays comprehensive vehicle information and current status.

---

## 📁 Project Structure

```
OOP Project/
├── README.md                    # This file
├── Code.txt                     # Full source code reference
├── OOP Report.pdf               # Project report document
└── OOP/                         # Main project directory
    ├── src/                     # Java source files
    │   ├── Admin.java           # Admin credentials & initialization
    │   ├── LoginPage.java       # GUI login with shift validation
    │   ├── AdminPanel.java      # Main dashboard with navigation
    │   ├── Entry.java           # Vehicle entry form & processing
    │   ├── Exit.java            # Vehicle exit & payment processing
    │   ├── Search.java          # Vehicle search functionality
    │   ├── VehicleEntry.java    # Vehicle class hierarchy (abstract + concrete)
    │   ├── Payment.java         # Payment & penalty calculation engine
    │   └── SlotManagement.java  # Parking slot management system
    ├── CarInfo.txt              # Active vehicle entry records
    ├── ExitInfo.txt             # Processed vehicle exit records
    ├── LoginInfo.txt            # Admin credentials store
    ├── Payment.txt              # Vehicle-type payment rates
    ├── Penalty.txt              # Vehicle-type penalty rates
    └── parking_slots.txt        # Real-time slot status (100 slots)
```

---

## 🏗️ Architecture & OOP Concepts

This project demonstrates the following **Object-Oriented Programming** principles:

### 1. Abstraction
- **`Vehicle`** is an abstract class with an abstract method `Type()`, providing a template for all vehicle types.

### 2. Inheritance
- **`Car`**, **`Bike`**, **`Jeep`**, **`Wagon`**, and **`Truck`** all extend the abstract `Vehicle` class, inheriting common attributes (driver name, license plate, slot number, etc.) while overriding `Type()`.

### 3. Polymorphism
- Each vehicle subclass provides its own implementation of `Type()` and the `Time` interface methods (`Entry()`, `Exit()`).
- Method overloading in `PaymentCalculator.calculatePayment()` — supports calls with and without the penalty flag.

### 4. Encapsulation
- All fields in `Vehicle`, `Slot`, `Admin`, and `PaymentMethod` are `private`, accessed exclusively through getter methods.

### 5. Interfaces
- **`Time`** interface — defines `Entry()` and `Exit()` methods for timestamp handling, implemented by all vehicle classes.
- **`Penalty`** interface — defines penalty calculation methods per vehicle type, implemented by `PaymentMethod`.

### Class Diagram

```
                    ┌─────────────────┐
                    │  «interface»    │
                    │     Time        │
                    │─────────────────│
                    │ + Entry()       │
                    │ + Exit()        │
                    └────────┬────────┘
                             │ implements
        ┌────────────────────┼────────────────────┐
        │                    │                    │
┌───────┴──────┐   ┌────────┴───────┐   ┌────────┴───────┐
│  «abstract»  │   │                │   │                │
│   Vehicle    │   │   «interface»  │   │     Slot       │
│──────────────│   │    Penalty     │   │────────────────│
│ - DriverName │   │────────────────│   │ - slotId       │
│ - LicencePlate│  │ + getCarPenalty│   │ - isOccupied   │
│ - ReceiptNo  │   │ + getBikePenalty│  │ - category     │
│ - SlotNo     │   │ + getJeepPenalty│  │────────────────│
│ - Color      │   │ + getWagonPenalty│ │ + parkVehicle()│
│ - HourParked │   │ + getTruckPenalty│ │ + removeVehicle│
│──────────────│   │ + getHandicap  │   │ + getSlotStatus│
│ + Type()*    │   └───────┬────────┘   └────────────────┘
└──────┬───────┘           │ implements
       │ extends           │
       ├────────┐    ┌─────┴──────────┐
       │        │    │ PaymentMethod  │
  ┌────┴───┐ ┌──┴──┐│────────────────│
  │  Car   │ │Bike ││ - baseAmount   │
  ├────────┤ ├─────┤│────────────────│
  │  Jeep  │ │Wagon││ + getCarPenalty│
  ├────────┤ ├─────┤│ + getBikePenalty│
  │  Truck │ │     ││ ...            │
  └────────┘ └─────┘└────────────────┘
```

---

## 💰 Parking Rates

### Base Hourly Rates

| Vehicle Type | Rate (Rs./hour) |
|:-------------|:---------------:|
| 🚗 Car       | 70              |
| 🏍️ Bike      | 50              |
| 🚙 Jeep      | 100             |
| 🚐 Wagon     | 150             |
| 🚛 Truck     | 250             |
| ♿ Handicap   | 20              |

### Penalty Rates (for overstay/violations)

| Vehicle Type | Penalty (Rs.)   |
|:-------------|:---------------:|
| 🚗 Car       | 35 (50%)        |
| 🏍️ Bike      | 15 (30%)        |
| 🚙 Jeep      | 60 (60%)        |
| 🚐 Wagon     | 300 (200%)      |
| 🚛 Truck     | 375 (150%)      |
| ♿ Handicap   | 100 (flat)      |

---

## ⏰ Shift Management

The system operates on a **3-shift schedule** for admin authentication:

| Shift | Hours          | Admin      |
|:-----:|:--------------:|:----------:|
| **A** | 00:00 – 07:59  | Huzaifa    |
| **B** | 08:00 – 15:59  | Maaz       |
| **C** | 16:00 – 23:59  | Ijlal      |

> **Note:** The master admin account (`admin@gmail.com` / `1234`) can log in during any shift.

---

## 🚀 Getting Started

### Prerequisites

- **Java JDK 8** or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, NetBeans) or command-line compiler

### Installation & Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/KiritoTempest175/Parking-System.git
   cd Parking-System
   ```

2. **Initialize data files** — Run the setup classes first to create required data files:
   ```bash
   cd OOP/src

   # Generate admin login credentials
   javac Admin.java
   java Admin

   # Generate parking slot data (100 slots)
   javac SlotManagement.java
   java SlotManagement

   # Generate payment and penalty rate files
   javac Payment.java
   java Payment
   ```

3. **Compile all source files:**
   ```bash
   javac *.java
   ```

4. **Run the application:**
   ```bash
   java LoginPage
   ```

### Quick Start Credentials

| Email              | Password | Shift       |
|:-------------------|:--------:|:-----------:|
| admin@gmail.com    | 1234     | Any (Master)|
| huzaifa@gmail.com  | 7096     | A (Night)   |
| maaz@gmail.com     | 7091     | B (Morning) |
| ijlal@gmail.com    | 7072     | C (Evening) |

---

## 📖 Usage Guide

### Registering a Vehicle (Entry)

1. Log in with valid credentials during your assigned shift.
2. Click **"Enter Vehicle"** on the Admin Panel.
3. Fill in all fields: Driver Name, License Plate, Receipt No., Slot No., Color, Hours Parked.
4. Select the vehicle type from the dropdown (Car, Bike, Jeep, Wagon, Truck).
5. Check **Handicap** if applicable — the system will validate slot type compatibility.
6. Click **Enter** to register. The system confirms the entry and displays the payment summary.

### Processing a Vehicle Exit

1. Click **"Exit Vehicle"** on the Admin Panel.
2. Enter the vehicle's Receipt Number and click **Check**.
3. The system retrieves vehicle details and calculates the payment due based on actual time parked.
4. Enter the payment amount, check the **Paid** checkbox, and click **Update**.
5. The system processes the exit, frees the slot, and records everything to `ExitInfo.txt`.

### Searching for a Vehicle

1. Click **"Search"** on the Admin Panel.
2. Enter the Receipt Number and click **Search**.
3. The system displays whether the vehicle is **currently parked** or has **already exited**, along with all associated details.

---

## 📂 Data Files

| File               | Purpose                                              |
|:-------------------|:-----------------------------------------------------|
| `LoginInfo.txt`    | Admin credentials (email, password, shift, name)     |
| `CarInfo.txt`      | Active vehicle entry records with full details        |
| `ExitInfo.txt`     | Completed exit records with payment information       |
| `parking_slots.txt`| Real-time status of all 100 slots (General/Handicap) |
| `Payment.txt`      | Base hourly rates per vehicle type                    |
| `Penalty.txt`      | Penalty rates per vehicle type                        |

### Slot Configuration (parking_slots.txt)

- **Slots 1–10**: Handicap designated
- **Slots 11–100**: General parking
- Status values: `Available` | `Taken`

---

## 🛠️ Technologies Used

| Technology      | Purpose                          |
|:----------------|:---------------------------------|
| **Java**        | Core programming language        |
| **Java Swing**  | GUI framework (JFrame, JPanel, GroupLayout) |
| **Java I/O**    | File-based data persistence      |
| **Java Time API** | Date/time handling for entry, exit, and shift management |
| **IntelliJ IDEA** | Development IDE                |

---

## 👥 Contributors

| Name       | Role                        |
|:-----------|:----------------------------|
| **Huzaifa** | Developer (Shift A Admin)  |
| **Maaz**    | Developer (Shift B Admin)  |
| **Ijlal**   | Developer (Shift C Admin)  |

---

## 📄 License

This project is developed as part of a **university OOP course project**. Feel free to use it for educational purposes.

---

<p align="center">
  <b>Made with ❤️ using Java Swing</b>
</p>
