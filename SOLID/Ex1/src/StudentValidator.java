import java.util.*;

public class StudentValidator implements Validator {
    private final Set<String> allowedPrograms;

    public StudentValidator(Set<String> allowedPrograms) {
        this.allowedPrograms = allowedPrograms;
    }

    @Override
    public List<String> validate(String name, String email,
                                 String phone, String program) {
        List<String> errors = new ArrayList<>();

        if (name == null || name.isBlank())
            errors.add("Name is required");

        if (email == null || !email.contains("@"))
            errors.add("Valid email is required");

        if (phone == null || phone.isBlank())
            errors.add("Phone is required");

        if (!allowedPrograms.contains(program))
            errors.add("Program must be one of: " + allowedPrograms);

        return errors;
    }
}