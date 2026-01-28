package iuh.adapter;

import iuh.core.JsonRequestService;
import iuh.util.JsonXmlConverter;
import iuh.xmlsystem.XmlSystem;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:51 PM
 */
public class JsonToXmlAdapter implements JsonRequestService {
    private final XmlSystem xmlSystem;

    public JsonToXmlAdapter(XmlSystem xmlSystem) {
        this.xmlSystem = xmlSystem;
    }

    @Override
    public void sendJson(String json) {
        String xml = JsonXmlConverter.jsonToXml(json, "root");
        xmlSystem.sendXml(xml);
    }
}