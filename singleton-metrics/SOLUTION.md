# Solution: Exercise A — Singleton Refactoring (PulseMeter)

This document outlines the fixes applied to the broken `MetricsRegistry` to transform it into a robust, thread-safe Singleton, preventing multiple instantiations via concurrency, reflection, and serialization.

## Summary of Fixes

1. **Private Constructor with Reflection Defense:** The constructor was made `private` to prevent external classes from using the `new` keyword. An internal check (`if (INSTANCE != null)`) was added to throw an `IllegalStateException` if reflection is used to force a second instantiation.
2. **Thread-Safe Lazy Initialization (Double-Checked Locking):** The `getInstance()` method was updated to use the Double-Checked Locking pattern. It checks if the instance is null, synchronizes on the class lock, and checks again before initializing.
3. **Serialization Defense:** The `readResolve()` method was implemented. This ensures that during deserialization, the JVM discards the newly allocated object and returns the existing Singleton instance instead.
4. **Usage Correction in Loader:** `MetricsLoader.java` was updated to use `MetricsRegistry.getInstance()` instead of calling `new MetricsRegistry()`, ensuring it populates the global registry.

---

## Why is the `volatile` keyword required?

In the Double-Checked Locking pattern, the `INSTANCE` variable **must** be declared with the `volatile` keyword (`private static volatile MetricsRegistry INSTANCE;`).

If you **do not** add the `volatile` keyword, your application is vulnerable to a subtle and dangerous concurrency bug caused by **instruction reordering**.

### What happens without `volatile`?

When the JVM executes `INSTANCE = new MetricsRegistry();`, it performs three distinct steps under the hood:
1. **Allocate memory** for the `MetricsRegistry` object.
2. **Call the constructor** to initialize the object (e.g., set up the `HashMap`).
3. **Assign the memory address** to the `INSTANCE` variable.

To optimize performance, the CPU or the Java compiler is allowed to reorder these steps as long as the logic remains consistent for the *current thread*. It might reorder the execution to: **1 -> 3 -> 2**.

**The Fatal Scenario:**
* **Thread A** enters the synchronized block and executes `INSTANCE = new MetricsRegistry()`.
* Due to reordering, the JVM allocates memory (Step 1) and immediately assigns the address to `INSTANCE` (Step 3), but the constructor *has not finished running yet* (Step 2 is pending).
* **Thread B** calls `getInstance()`. It checks `if (INSTANCE == null)`.
* Because `INSTANCE` now holds a memory address, the check returns `false`.
* **Thread B** happily returns the `INSTANCE` and tries to use it (e.g., calling `.increment()`).
* However, because the constructor hasn't finished initializing the `HashMap` (Step 2), **Thread B crashes** with a `NullPointerException` or corrupts the application state.

### How `volatile` fixes this:
The `volatile` keyword establishes a **"happens-before" guarantee**. It strictly prohibits the JVM and CPU from reordering the write to the `INSTANCE` variable. It guarantees that the object is 100% fully constructed and initialized in main memory *before* the `INSTANCE` variable is updated, ensuring that no thread ever sees a partially initialized object.

---

## Final Refactored Code

    // 1. MUST BE VOLATILE
    private static volatile MetricsRegistry INSTANCE; 

    // 2. PRIVATE CONSTRUCTOR + REFLECTION DEFENSE
    private MetricsRegistry() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Instance already exists! Use getInstance().");
        }
    }

    // 3. THREAD-SAFE LAZY INIT (Double-Checked Locking)
    public static MetricsRegistry getInstance() {
        if (INSTANCE == null) {
            synchronized (MetricsRegistry.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MetricsRegistry();
                }
            }
        }
        return INSTANCE;
    }

  

    // 4. SERIALIZATION DEFENSE
    @Serial
    protected Object readResolve() {
        return getInstance();
    }
}
// Inside loadFromFile method:

// FIXED: Use the singleton instead of creating a new instance
MetricsRegistry registry = MetricsRegistry.getInstance();

for (String key : props.stringPropertyNames()) {
    // ... logic remains identical