public class SmsSender extends NotificationSender {
    public SmsSender(AuditLog audit) { super(audit); }

    @Override
    public void send(Object request) {
        SmsRequest req = (SmsRequest) request;
        // SmsRequest has no subject field - contract is now clear
        System.out.println("SMS -> to=" + req.phone + " body=" + req.body);
        audit.add("sms sent");
    }
}