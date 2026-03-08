# Proxy Design Pattern - CampusVault Refactoring

## Overview
This document outlines the refactoring steps taken to implement the **Proxy Design Pattern** in the CampusVault reporting tool. The original implementation suffered from security vulnerabilities, performance bottlenecks due to eager file loading, and tight coupling.

## Design Flaws Addressed
1. **Security (No Access Control):** Any user could view restricted reports (e.g., a student viewing a faculty report).
2. **Performance (Eager Loading):** Simulated expensive disk reads occurred immediately, even before access was verified.
3. **Inefficiency (No Caching):** Repeated views of the same report triggered the expensive disk read multiple times.
4. **Tight Coupling:** The client code (`ReportViewer`) depended directly on the concrete file class rather than an abstraction.

## Refactoring Steps

### 1. Extracted the Real Subject (`RealReport.java`)
* Moved the heavy file-reading logic (`loadFromDisk()`) from the old `ReportFile` class into a new `RealReport` class.
* The expensive operation now executes exactly once inside the constructor, ensuring the content is loaded only when this object is actually instantiated.

### 2. Implemented the Proxy (`ReportProxy.java`)
* Created a proxy class that implements the same `Report` interface as `RealReport`.
* **Access Control:** Added a security layer utilizing `AccessControl` to verify user roles before doing any heavy lifting.
* **Lazy Loading & Caching:** Added a `RealReport` field to cache the instance. The proxy now delays instantiating `RealReport` until access is granted and it is actually needed. Subsequent requests reuse the cached instance.

### 3. Decoupled the Client (`ReportViewer.java`)
* Changed the `open()` method signature to accept the `Report` interface instead of the concrete class. This allows the viewer to seamlessly accept the proxy without needing to know about the access control or lazy-loading mechanics happening behind the scenes.

### 4. Updated the Application Entry Point (`App.java`)
* Replaced all direct instantiations of the eager-loading concrete file with `ReportProxy` objects.
* Deleted the now-obsolete `ReportFile.java`.

## Final Output Behavior
* **Authorized & First View:** Triggers the disk load and displays the content.
* **Unauthorized View:** Intercepted by the proxy; prints `ACCESS DENIED` and completely prevents the expensive disk load.
* **Authorized & Repeat View:** Bypasses the disk load and immediately displays the content using the cached `RealReport` instance inside the proxy.