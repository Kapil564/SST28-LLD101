import java.nio.charset.StandardCharsets;

public class CsvExporter extends Exporter {
    @Override
    protected ExportResult doExport(ExportRequest req) {
        // LSP issue: changes meaning by lossy conversion
        String body = req.body == null ? "" : req.body;
        String escapedBody = "\"" + body.replace("\"", "\"\"") + "\"";
        String csv = "title,body\n" + req.title + "," + escapedBody + "\n";
        return new ExportResult("text/csv", csv.getBytes(StandardCharsets.UTF_8));
    }
}
