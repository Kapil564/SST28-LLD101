import java.util.*;

 class InputParser {


    public static Map<String, String> parse(String raw) {
        Map<String, String> result = new HashMap<>();

        if (raw == null || raw.isBlank()) return result;

        String[] pairs = raw.split(";");

        for (String pair : pairs) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2) {
                result.put(kv[0].trim(), kv[1].trim());
            }
        }

        return result;
    }
}