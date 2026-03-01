public class Main {
    public static void main(String[] args) {
        System.out.println("=== Notification Demo ===");
        AuditLog audit = new AuditLog();

        // Create channel-specific requests
        EmailRequest emailReq = new EmailRequest("riya@sst.edu", "Welcome", "Hello and welcome to SST!");
        SmsRequest smsReq = new SmsRequest("9876543210", "Hello and welcome to SST!");
        WhatsAppRequest waReq = new WhatsAppRequest("9876543210", "Hello and welcome to SST!");

        NotificationSender email = new EmailSender(audit);
        NotificationSender sms = new SmsSender(audit);
        NotificationSender wa = new WhatsAppSender(audit);

        email.send(emailReq);
        sms.send(smsReq);

        // Validate before sending (not inside sender)
        String error = WhatsAppValidator.validate(waReq);
        if (error != null) {
            System.out.println("WA ERROR: " + error);
            audit.add("WA failed");
        } else {
            wa.send(waReq);
        }

        System.out.println("AUDIT entries=" + audit.size());
    }
}