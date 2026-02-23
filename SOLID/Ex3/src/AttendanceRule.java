
public class AttendanceRule implements EligibilityRule{
    private int minAttendance;
    public AttendanceRule(int minAttendance){
        this.minAttendance = minAttendance;
    }
    @Override
    public String check(StudentProfile s){
        if (s.attendancePct < minAttendance) {
            return "attendance below "+minAttendance;
        }
        return null;
    }
}
