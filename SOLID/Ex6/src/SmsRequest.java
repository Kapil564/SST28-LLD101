public class SmsRequest {
    public final String phone;
    public final String body;

    public SmsRequest(String phone, String body) {
        this.phone = phone;
        this.body = body;
    }
}

