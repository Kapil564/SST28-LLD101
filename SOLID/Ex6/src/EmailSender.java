public class EmailSender extends NotificationSender {
    public EmailSender(AuditLog audit) { super(audit); }

    @Override
    public void send(Object request) {
        EmailRequest req = (EmailRequest) request;
        // No truncation: preserve full message to honor LSP contract
        System.out.println("EMAIL -> to=" + req.email + " subject=" + req.subject + " body=" + req.body);
        audit.add("email sent");
    }
}