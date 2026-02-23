import java.util.List;

public interface Presenter{
    void printInput(String raw);
    void printErrors(List<String> errors);
    void printSuccess(StudentRecord rec, int total);
}
