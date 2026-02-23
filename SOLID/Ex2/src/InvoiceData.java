
import java.util.List;

public class InvoiceData {
    public final String invoiceId;
    public final List<LineItem> lines;
    public final double subtotal;
    public final double taxPercent;
    public final double taxAmount;
    public final double discountAmount;
    public final double total;

    public InvoiceData(String invoiceId, List<LineItem> lines, double subtotal, double taxPercent, double taxAmount, double discountAmount, double total) {
        this.invoiceId = invoiceId;
        this.lines = lines;
        this.subtotal = subtotal;
        this.taxPercent = taxPercent;
        this.taxAmount = taxAmount;
        this.discountAmount = discountAmount;
        this.total = total;
    }

    public static class LineItem {
        public final String name;
        public final int qty;
        public final double lineTotal;

        public LineItem(String name, int qty, double lineTotal) {
            this.name = name;
            this.qty = qty;
            this.lineTotal = lineTotal;
        }
    }
}