package iuh.util;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:51 PM
 */
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonXmlConverter {

    // JSON cực đơn giản dạng: {"name":"Nguyen","age":21}
    public static String jsonToXml(String json, String rootName) {
        Map<String, String> map = parseSimpleJson(json);
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(rootName).append(">");

        for (var e : map.entrySet()) {
            sb.append("<").append(e.getKey()).append(">")
                    .append(escapeXml(e.getValue()))
                    .append("</").append(e.getKey()).append(">");
        }

        sb.append("</").append(rootName).append(">");
        return sb.toString();
    }

    // XML cực đơn giản dạng: <root><name>Nguyen</name><age>21</age></root>
    public static String xmlToJson(String xml) {
        Map<String, String> map = parseSimpleXml(xml);
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        int i = 0;
        for (var e : map.entrySet()) {
            sb.append("\"").append(e.getKey()).append("\":");
            String v = e.getValue();

            // nếu là số -> không cần quote (demo)
            if (v.matches("-?\\d+(\\.\\d+)?")) sb.append(v);
            else sb.append("\"").append(escapeJson(v)).append("\"");

            if (i < map.size() - 1) sb.append(",");
            i++;
        }

        sb.append("}");
        return sb.toString();
    }

    // ===== Helpers (demo) =====

    private static Map<String, String> parseSimpleJson(String json) {
        Map<String, String> map = new LinkedHashMap<>();
        String s = json.trim();
        if (s.startsWith("{")) s = s.substring(1);
        if (s.endsWith("}")) s = s.substring(0, s.length() - 1);

        if (s.isBlank()) return map;

        String[] pairs = s.split(",");
        for (String p : pairs) {
            String[] kv = p.split(":", 2);
            String key = kv[0].trim().replaceAll("^\"|\"$", "");
            String val = kv[1].trim().replaceAll("^\"|\"$", "");
            map.put(key, val);
        }
        return map;
    }

    private static Map<String, String> parseSimpleXml(String xml) {
        Map<String, String> map = new LinkedHashMap<>();
        String s = xml.trim();

        // bỏ root outer tag
        int firstClose = s.indexOf(">");
        int lastOpen = s.lastIndexOf("<");
        if (firstClose == -1 || lastOpen == -1) return map;

        String inner = s.substring(firstClose + 1, lastOpen);

        // parse dạng <k>v</k> lặp lại
        while (true) {
            int open1 = inner.indexOf("<");
            if (open1 == -1) break;
            int close1 = inner.indexOf(">", open1);
            if (close1 == -1) break;

            String tag = inner.substring(open1 + 1, close1).trim();
            int endTag = inner.indexOf("</" + tag + ">", close1);
            if (endTag == -1) break;

            String value = inner.substring(close1 + 1, endTag);
            map.put(tag, value);

            inner = inner.substring(endTag + tag.length() + 3); // </tag> length
        }
        return map;
    }

    private static String escapeXml(String s) {
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private static String escapeJson(String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}