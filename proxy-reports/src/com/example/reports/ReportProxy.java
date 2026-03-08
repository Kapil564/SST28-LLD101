package com.example.reports;

/**
 * TODO (student):
 * Implement Proxy responsibilities here:
 * - access check
 * - lazy loading
 * - caching of RealReport within the same proxy
 */
public class ReportProxy implements Report {

    private final String reportId;
    private final String title;
    private final String classification;
    private final AccessControl accessControl = new AccessControl();
    private RealReport realReport;
    public ReportProxy(String reportId, String title, String classification) {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
    }

    @Override
    public void display(User user) {
        if(!accessControl.canAccess(user, classification)) {
            System.out.println("ACCESS DENIED: User '" + user.getName() +
                    "' does not have permission to view report " + classification);
            return;
        }
        if (realReport == null) {
            realReport = new RealReport(reportId, title, classification);
        }
        realReport.display(user);

//        // Starter placeholder: intentionally incorrect.
//        // Students should remove direct real loading on every call.
//        RealReport report = new RealReport(reportId, title, classification);
//        report.display(user);
    }
}