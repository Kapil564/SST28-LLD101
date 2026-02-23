public class CreditsRule implements EligibilityRule{
    private int minCredits;
    public CreditsRule(int credits) {
        this.minCredits = credits;
    }
    @Override
    public String check(StudentProfile s) {
        if (s.earnedCredits < minCredits) {
            return "credits below "+minCredits;
        }
        return null;
    }
}
