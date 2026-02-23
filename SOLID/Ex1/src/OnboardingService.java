import java.util.*;

public class OnboardingService {
    private final InputParser parser;
    private final StudentRegistrar registrar;
    private final Presenter presenter;

    public OnboardingService(InputParser parser,
                             StudentRegistrar registrar,
                             Presenter presenter) {
        this.parser    = parser;
        this.registrar = registrar;
        this.presenter = presenter;
    }

    public void registerFromRawInput(String raw) {
        presenter.printInput(raw);

        Map<String, String> kv = parser.parse(raw);

        String name    = kv.getOrDefault("name", "");
        String email   = kv.getOrDefault("email", "");
        String phone   = kv.getOrDefault("phone", "");
        String program = kv.getOrDefault("program", "");

        RegistrationResult result = registrar.register(name, email, phone, program);

        if (!result.isSuccess()) {
            presenter.printErrors(result.getErrors());
            return;
        }

        presenter.printSuccess(result.getRecord(), result.getTotalCount());
    }
}