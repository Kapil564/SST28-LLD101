import java.util.Set;

public class Main {
    public static void main(String[] args) {
        FakeDb db = new FakeDb();
        Validator validator        = new StudentValidator(Set.of("CSE", "AI", "SWE"));
        Presenter presenter        = new OnboardingPresenter();
        InputParser parser         = new InputParser();
        StudentRegistrar registrar = new StudentRegistrar(db, validator);
        OnboardingService service  = new OnboardingService(parser, registrar, presenter);


        String raw = "name=Riya;email=riya@sst.edu;phone=9876543210;program=CSE";
        service.registerFromRawInput(raw);

        System.out.println();


        String raw2 = "name=John;email=john@sst.edu;phone=1234567890;program=DS";
        service.registerFromRawInput(raw2);

        System.out.println();
        System.out.println("-- DB DUMP --");
        System.out.print(TextTable.render3(db));
    }
}