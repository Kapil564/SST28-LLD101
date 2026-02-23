import java.util.*;

public class RegistrationResult {
    private final boolean success;
    private final List<String> errors;
    private final StudentRecord record;
    private final int totalCount;

    private RegistrationResult(boolean success, List<String> errors,
                               StudentRecord record, int totalCount) {
        this.success    = success;
        this.errors     = errors;
        this.record     = record;
        this.totalCount = totalCount;
    }

    public static RegistrationResult failure(List<String> errors) {
        return new RegistrationResult(false, errors, null, 0);
    }

    public static RegistrationResult success(StudentRecord record, int total) {
        return new RegistrationResult(true, List.of(), record, total);
    }

    public boolean isSuccess()        { return success; }
    public List<String> getErrors()   { return errors; }
    public StudentRecord getRecord()  { return record; }
    public int getTotalCount()        { return totalCount; }
}