package iuh.adapter;

import iuh.core.JsonResponseService;
import iuh.util.JsonXmlConverter;
import iuh.xmlsystem.XmlProvider;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:51 PM
 */
public class XmlToJsonAdapter implements JsonResponseService {

    private final XmlProvider xmlProvider;

    public XmlToJsonAdapter(XmlProvider xmlProvider) {
        this.xmlProvider = xmlProvider;
    }

    @Override
    public String fetchJson() {
        String xml = xmlProvider.fetchXml();
        return JsonXmlConverter.xmlToJson(xml);
    }
}
