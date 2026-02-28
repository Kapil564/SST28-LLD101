**1. Problem:**
The `HostelFeeCalculator` breaks the **Open/Closed Principle (OCP)**. It uses hardcoded `switch` and `if-else` statements to calculate prices. If a new room type or add-on is introduced, you are forced to edit the core calculation logic, making the code open to modification rather than extension.

**2. Approach:**
Use polymorphism to replace the conditionals. Create a common interface for anything that has a price, and build separate, single-purpose classes for each specific room and add-on. Because we cannot change the original request format (which uses legacy `int` and `enum` values), we will build a "Registry" (a Map) to translate the old inputs into our new pricing objects. The calculator will simply look up the item in the registry and ask for its price.

**3. Implementation**

* **Interface:** `PricingComponent`
* **Room Classes:** `SingleRoomPricing`, `DoubleRoomPricing`, `TripleRoomPricing`, `DeluxeRoomPricing`
* **Add-On Classes:** `MessPricing`, `LaundryPricing`, `GymPricing`
* **Translator Class:** `PricingRegistry`
* **Modified Class:** `HostelFeeCalculator`