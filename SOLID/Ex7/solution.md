# Solution: Exercise 7 - Interface Segregation Principle (ISP)

## Overview of the Problem
The original design featured a "fat" interface (`SmartClassroomDevice`) that forced every classroom device to implement methods it didn't need (e.g., forcing an AC to implement `scanAttendance()`). This resulted in dummy implementations, polluting the codebase and making device capabilities unclear. To fix this, we applied the Interface Segregation Principle by breaking the large interface into smaller, highly cohesive, capability-specific interfaces.

---

## Step 1: Segregating the Fat Interface
**Files Modified/Created:** * `SmartClassroomDevice.java`
* `PowerControl.java`, `InputControl.java`, `BrightnessControl.java`, `TemperatureControl.java`, `ScannerControl.java` (New Files)

**Changes Made:**
Instead of maintaining one monolithic interface, we converted `SmartClassroomDevice` into an empty "marker" interface. We then extracted specific behaviors into separate interfaces. For example, `powerOn()` and `powerOff()` moved to `PowerControl`, while `setBrightness()` moved to `BrightnessControl`.

**Why we did this:**
By defining smaller contracts, we ensure that client code (like the devices) only depends on the exact capabilities they actually possess. This keeps the abstractions clean and adheres strictly to OOP best practices.

---

## Step 2: Cleaning Up Device Implementations
**Files Modified:** * `Projector.java`
* `LightsPanel.java`
* `AirConditioner.java`
* `AttendanceScanner.java`

**Changes Made:**
We removed all the "dummy" methods from these classes. Instead of implementing the single fat interface, each device class now implements only the specific capability interfaces it supports.
* The `Projector` now implements `PowerControl` and `InputControl`.
* The `LightsPanel` implements `PowerControl` and `BrightnessControl`.
* The `AirConditioner` implements `PowerControl` and `TemperatureControl`.
* The `AttendanceScanner` implements `PowerControl` and `ScannerControl`.

**Why we did this:**
This eliminates the misleading empty methods (like the Projector returning `0` for attendance). Now, looking at a class declaration instantly tells you exactly what that hardware is capable of doing without any hidden surprises.

---

## Step 3: Refactoring the Registry for Dynamic Lookups
**Files Modified:** * `DeviceRegistry.java`

**Changes Made:**
We updated the registry so that it no longer retrieves devices by matching hard-coded String names (e.g., `"Projector"`). Instead, we introduced generic methods (`getFirstByCapability` and `getAllByCapability`) that look up devices based on the interface they implement using `Class<T>`.

**Why we did this:**
This decouples the registry from concrete implementations. If a new device is added in the future (like a SmartBoard that also implements `InputControl`), the registry can find it automatically without needing its exact class name. This makes the system much more extensible.

---

## Step 4: Updating the Controller Logic
**Files Modified:** * `ClassroomController.java`

**Changes Made:**
We changed how the controller interacts with the classroom. Rather than asking the registry for a specific hardware piece by string name, the controller now asks the registry for a specific *capability*. For example, to set the temperature, it simply requests a device that implements `TemperatureControl`. For the shutdown sequence, it fetches a list of *all* devices implementing `PowerControl` and iterates through them to turn them off.

**Why we did this:**
Programming to interfaces (capabilities) rather than concrete implementations drastically reduces coupling. The controller no longer cares *what* the device is, only *what it can do*. This ensures the core logic remains untouched even as new hardware types are added to the classroom ecosystem.