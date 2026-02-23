import java.util.List;

public interface Validator{
    List<String> validate(String name, String email, String phone, String program);
}
