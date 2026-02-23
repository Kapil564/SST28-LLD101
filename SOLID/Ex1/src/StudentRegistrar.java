import java.util.List;

public class StudentRegistrar {
    private final StudentRepository db;
    private final Validator validator;

    public StudentRegistrar(StudentRepository db, Validator validator) {
        this.db        = db;
        this.validator = validator;
    }

    public RegistrationResult register(String name, String email,
                                       String phone, String program) {
        List<String> errors = validator.validate(name, email, phone, program);

        if (!errors.isEmpty()) {
            return RegistrationResult.failure(errors);
        }

        String id        = IdUtil.nextStudentId(db.count());
        StudentRecord rec = new StudentRecord(id, name, email, phone, program);
        db.save(rec);

        return RegistrationResult.success(rec, db.count());
    }
}