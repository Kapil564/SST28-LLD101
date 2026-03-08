# Solution: Adapter Pattern Refactoring (Payments)

## 1. Overview
The original implementation of `OrderService` was tightly coupled to two incompatible third-party payment SDKs (`FastPayClient` and `SafeCashClient`). It relied on a hard-coded `switch` statement based on a provider string to execute mismatched payment flows.

To solve this, we introduced the **Adapter Design Pattern**. By creating a unified `PaymentGateway` interface and wrapping the external SDKs in adapter classes, we decoupled the `OrderService` from the specific implementations of the payment providers.

## 2. Structural Changes

### A. The Target Interface
The system already had a defined target interface, which we utilized as the standard contract for all payment operations.
- **File:** `PaymentGateway.java`
- **Details:** Exposes a single unified method: `String charge(String customerId, int amountCents);`

### B. Created the Adapters
We created two new adapter classes that implement `PaymentGateway` and translate the standardized `charge` call into the provider-specific SDK calls.
- **File:** `FastPayAdapter.java`
    - Wraps `FastPayClient`.
    - Translates the `charge` method directly to `FastPayClient.payNow()`.
- **File:** `SafeCashAdapter.java`
    - Wraps `SafeCashClient`.
    - Translates the `charge` method into SafeCash's two-step process: creating a `SafeCashPayment` object and then calling `confirm()`.

### C. Refactored the Client (`OrderService`)
We removed the provider-specific branching and tight coupling from the business logic.
- **File:** `OrderService.java`
- **Details:** - Modified the `charge` method to eliminate the `switch` statement.
    - The service now fetches the `PaymentGateway` from the provided `Map` and directly calls `gw.charge(customerId, amountCents)`.
    - The service now knows *nothing* about `FastPayClient` or `SafeCashClient`.

### D. Updated the Dependency Injection (`App`)
We updated the application entry point to register the adapters instead of the raw SDKs.
- **File:** `App.java`
- **Details:** - Initialized the raw SDKs (`FastPayClient`, `SafeCashClient`).
    - Wrapped them in their respective adapters (`FastPayAdapter`, `SafeCashAdapter`).
    - Added the adapters to the `gateways` map and passed it to `OrderService`.

## 3. How it Satisfies the Requirements
1. **DIP & OCP Compliance:** `OrderService` now depends purely on the `PaymentGateway` abstraction. Adding a new payment provider (e.g., Stripe) only requires creating a `StripeAdapter` and adding it to the map in `App.java`. `OrderService` remains completely untouched.
2. **Simplified Business Logic:** The glue code previously duplicating payment translation logic inside `OrderService` is now neatly encapsulated within the stateless adapter classes.
3. **Behavior Preserved:** Running the `App` successfully processes both payments and prints the expected transaction IDs to the console.