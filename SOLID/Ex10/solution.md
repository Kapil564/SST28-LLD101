# Solution: Exercise 10 - Dependency Inversion Principle (DIP)

## Overview of the Problem
The original design featured a high-level service (`TransportBookingService`) that directly instantiated concrete low-level dependencies (`DistanceCalculator`, `DriverAllocator`, `PaymentGateway`) using the `new` keyword. This created tight coupling, making the system difficult to test in isolation and forcing modifications to the core booking logic whenever a new implementation (like a new payment method) was needed. To fix this, we applied the Dependency Inversion Principle by introducing abstractions (interfaces) and injecting them into the service.

---

## Step 1: Creating Abstractions (Interfaces)
**Files Modified/Created:** * `IDistanceCalculator.java`, `IDriverAllocator.java`, `IPaymentGateway.java` (New Files)

**Changes Made:**
We created three new interfaces to explicitly define the contracts for calculating distance, allocating drivers, and processing payments.

**Why we did this:**
By defining these abstractions, we ensure that the high-level booking logic depends on stable contracts rather than volatile concrete implementations. This separates the "what needs to be done" from the "how it is actually done."

---

## Step 2: Cleaning Up Concrete Implementations
**Files Modified:** * `DistanceCalculator.java`
* `DriverAllocator.java`
* `PaymentGateway.java`

**Changes Made:**
We modified the existing concrete classes so that they explicitly implement our newly created interfaces. The internal business logic of these classes remained completely unchanged.

**Why we did this:**
This ensures that our existing low-level modules correctly fulfill the contracts required by the new abstractions, allowing them to be safely and seamlessly substituted into any component that expects those interfaces.

---

## Step 3: Refactoring the Booking Service (Constructor Injection)
**Files Modified:** * `TransportBookingService.java`

**Changes Made:**
We removed the hard-coded `new` instantiations from inside the `book()` method. Instead, we declared the interfaces as private final fields at the class level and injected them into the service via its constructor.

**Why we did this:**
This shifts the responsibility of creating dependencies away from the service itself. The service now only knows *that* these operations are handled, not *how* they are handled. This drastically improves testability, as we can now easily pass mock objects (like a `FakePaymentGateway`) into the constructor during unit testing.

---

## Step 4: Wiring Dependencies in the Composition Root
**Files Modified:** * `Main.java`

**Changes Made:**
We updated the `Main` class to act as the composition root. It is now responsible for instantiating the specific concrete classes (`DistanceCalculator`, `DriverAllocator`, `PaymentGateway`) and passing them into the constructor of `TransportBookingService` before executing the booking request.

**Why we did this:**
Programming to interfaces requires a central place to wire the actual implementations together. By extracting this setup logic to `Main`, we keep the entire core system fully decoupled. If we ever need to swap out an implementation (for example, switching to a `StripePaymentGateway`), we only have to change one line in the application's entry point, leaving the core booking logic completely untouched.