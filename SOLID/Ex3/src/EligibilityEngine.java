import java.util.*;

public class EligibilityEngine {
//    private final FakeEligibilityStore store;
    private final List<EligibilityRule> rules;

    public EligibilityEngine(List<EligibilityRule> rules) {
//        this.store = store;
        this.rules = rules;
    }

    public EligibilityEngineResult evaluate(StudentProfile s) {
        List<String> reasons = new ArrayList<>();
        String status = "ELIGIBLE";

        for(EligibilityRule rule : rules){
            String failureReason = rule.check(s);
            if (failureReason != null) {
                status = "NOT_ELIGIBLE";
                reasons.add(failureReason);
                break;
            }
        }

        return new EligibilityEngineResult(status, reasons);
    }
}

