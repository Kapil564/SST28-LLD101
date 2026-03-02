public class Main {
    public static void main(String[] args) {
        System.out.println("=== Evaluation Pipeline ===");
        IPlagiarismChecker pc = new PlagiarismChecker();
        ICodeGrader grader = new CodeGrader();
        IReportWriter writer = new ReportWriter();
        Rubric rubric = new Rubric();

        EvaluationPipeline pipeline = new EvaluationPipeline(pc, grader, writer, rubric);
        Submission sub = new Submission("23BCS1007", "public class A{}", "A.java");
        pipeline.evaluate(sub);
    }
}