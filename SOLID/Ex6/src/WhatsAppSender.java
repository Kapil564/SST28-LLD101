public class WhatsAppSender extends NotificationSender {
    public WhatsAppSender(AuditLog audit) { super(audit); }

    @Override
    public void send(Object request) {
        WhatsAppRequest req = (WhatsAppRequest) request;
        // Validation moved to WhatsAppValidator - no precondition tightening here
        System.out.println("WA -> to=" + req.phone + " body=" + req.body);
        audit.add("wa sent");
    }
}