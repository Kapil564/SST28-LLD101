public class WhatsAppValidator {
    public static String validate(WhatsAppRequest request) {
        if (request.phone == null || !request.phone.startsWith("+")) {
            return "phone must start with + and country code";
        }
        return null;
    }
}

