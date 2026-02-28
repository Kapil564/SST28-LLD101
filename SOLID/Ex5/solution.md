Here is the Markdown content you can use for your `solution.md` file. It breaks down the problem, the approach, and the exact code fixes for each file.

```markdown
# Ex5 Solution: Liskov Substitution Principle (LSP)

## The Problem
The current design violates the Liskov Substitution Principle (LSP). The base class `Exporter` establishes an implicit contract that it can export an `ExportRequest` to an `ExportResult`. However, the subclasses break this substitutability in several ways:

1. **Tightened Preconditions (`PdfExporter.java`):** Throws an exception if the text is over 20 characters. A caller using the base `Exporter` type would not expect this sudden failure.
2. **Changed Semantics/Lossy Data (`CsvExporter.java`):** Silently corrupts the data by removing newlines and commas. Subclasses must preserve the core meaning of the data.
3. **Inconsistent Contracts (`JsonExporter.java`):** Handles `null` requests with its own unique logic (returning an empty array), whereas the base class and other subclasses don't have a unified strategy for bad inputs.

## The Approach
To fix this, we need to enforce a strict contract in the base class and ensure no subclass violates it.
1. **Template Method Pattern:** Update `Exporter.java` to handle the core contract (e.g., null checking) and delegate the actual formatting to a protected abstract method (`doExport`).
2. **Remove Arbitrary Constraints:** Fix `PdfExporter` so it accepts any length of text.
3. **Fix Data Corruption:** Update `CsvExporter` to properly escape CSV fields instead of deleting characters.
4. **Standardize Null Handling:** Remove the custom null checks from `JsonExporter` since the base class now handles it.

---

## Solution Code

### 1. `Exporter.java`
**Changes:** Introduced the Template Method pattern to enforce standard preconditions (null checking) across all exporters.

```java
public abstract class Exporter {
    
    // The Base Contract: Enforces preconditions before delegating to subclasses.
    public final ExportResult export(ExportRequest req) {
        if (req == null) {
            throw new IllegalArgumentException("ExportRequest cannot be null");
        }
        return doExport(req);
    }

    // Subclasses only implement the format-specific encoding.
    protected abstract ExportResult doExport(ExportRequest req);
}

```

### 2. `PdfExporter.java`

**Changes:** Removed the arbitrary length restriction that tightened the base contract.

```java
import java.nio.charset.StandardCharsets;

public class PdfExporter extends Exporter {
    @Override
    protected ExportResult doExport(ExportRequest req) {
        // LSP fix: Removed the exception for content > 20 chars.
        String fakePdf = "PDF(" + req.title + "):" + req.body;
        return new ExportResult("application/pdf", fakePdf.getBytes(StandardCharsets.UTF_8));
    }
}

```

### 3. `CsvExporter.java`

**Changes:** Fixed the lossy conversion. Instead of replacing commas and newlines with spaces, we wrap the body in double quotes to create valid CSV.

```java
import java.nio.charset.StandardCharsets;

public class CsvExporter extends Exporter {
    @Override
    protected ExportResult doExport(ExportRequest req) {
        // LSP fix: Preserve meaning by properly escaping instead of deleting characters.
        String body = req.body == null ? "" : req.body;
        
        // Wrap in quotes and escape internal quotes to support newlines and commas
        String escapedBody = "\"" + body.replace("\"", "\"\"") + "\"";
        
        String csv = "title,body\n" + req.title + "," + escapedBody + "\n";
        return new ExportResult("text/csv", csv.getBytes(StandardCharsets.UTF_8));
    }
}

```

### 4. `JsonExporter.java`

**Changes:** Removed the inconsistent null-handling logic, as it is now safely managed by the base class. Improved JSON escaping.

```java
import java.nio.charset.StandardCharsets;

public class JsonExporter extends Exporter {
    @Override
    protected ExportResult doExport(ExportRequest req) {
        // Null request check is now safely handled by Exporter.java
        String json = "{\"title\":\"" + escape(req.title) + "\",\"body\":\"" + escape(req.body) + "\"}";
        return new ExportResult("application/json", json.getBytes(StandardCharsets.UTF_8));
    }

    private String escape(String s) {
        if (s == null) return "";
        // Safely escape quotes and newlines for valid JSON
        return s.replace("\"", "\\\"").replace("\n", "\\n");
    }
}

```

```

```