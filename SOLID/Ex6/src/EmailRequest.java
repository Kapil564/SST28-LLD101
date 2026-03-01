public class EmailRequest {
    public final String email;
    public final String subject;
    public final String body;

    public EmailRequest(String email, String subject, String body) {
        this.email = email;
        this.subject = subject;
        this.body = body;
    }
}

