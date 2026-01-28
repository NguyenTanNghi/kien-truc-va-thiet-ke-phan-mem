package iuh;

import iuh.adapter.JsonToXmlAdapter;
import iuh.adapter.XmlToJsonAdapter;
import iuh.core.JsonRequestService;
import iuh.core.JsonResponseService;
import iuh.xmlsystem.XmlProvider;
import iuh.xmlsystem.XmlSystem;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:48 PM
 */
public class Main {
    public static void main(String[] args) {

        // ===== 1) JSON -> XML =====
        System.out.println("=== JSON -> XML ===");
        XmlSystem xmlSystem = new XmlSystem();
        JsonRequestService jsonService = new JsonToXmlAdapter(xmlSystem);

        String json = "{\"name\":\"Nghi\",\"age\":21}";
        jsonService.sendJson(json);

        // ===== 2) XML -> JSON =====
        System.out.println("\n=== XML -> JSON ===");
        XmlProvider xmlProvider = new XmlProvider();
        JsonResponseService jsonResponseService = new XmlToJsonAdapter(xmlProvider);

        String resultJson = jsonResponseService.fetchJson();
        System.out.println("✅ Client received JSON:");
        System.out.println(resultJson);
    }
}