import Interfaces.DiscountPolicy;
import Interfaces.InvoiceStore;
import Interfaces.TaxPolicy;

import java.util.*;

public class CafeteriaSystem {
    private final Map<String, MenuItem> menu = new LinkedHashMap<>();
    private int invoiceSeq = 1000;
    private final TaxPolicy taxPolicy;
    private final DiscountPolicy discountPolicy;
    private final InvoiceFormatter formatter;
    private final InvoiceStore store;
    public CafeteriaSystem(TaxPolicy taxPolicy, DiscountPolicy discountPolicy,
                           InvoiceFormatter formatter, InvoiceStore store) {
        this.taxPolicy = taxPolicy;
        this.discountPolicy = discountPolicy;
        this.formatter = formatter;
        this.store = store;
    }

    public void addToMenu(MenuItem i) { menu.put(i.id, i); }

    // Intentionally SRP-violating: menu mgmt + tax + discount + format + persistence.
    public void checkout(String customerType, List<OrderLine> lines) {
        String invId = "INV-" + (++invoiceSeq);
        StringBuilder out = new StringBuilder();
        out.append("Invoice# ").append(invId).append("\n");

        //pricing
        double subtotal = 0.0;
        List<InvoiceData.LineItem> lineItems = new ArrayList<>();

        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            double lineTotal = item.price * l.qty;
            subtotal += lineTotal;
            lineItems.add(new InvoiceData.LineItem(item.name, l.qty, lineTotal));
        }

        double taxPct = taxPolicy.getTaxPercent(customerType);
        double tax = subtotal * (taxPct / 100.0);
        double discount = discountPolicy.getDiscountAmount(customerType, subtotal, lines.size());
        double total = subtotal + tax - discount;
        InvoiceData data = new InvoiceData(invId, lineItems, subtotal, taxPct, tax, discount, total);

        String printable = formatter.format(data);
        System.out.print(printable);
        store.save(invId, printable);
        System.out.println("Saved invoice: " + invId + " (lines=" + store.countLines(invId) + ")");
    }
}
