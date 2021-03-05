package InterfaceServer;

import java.util.HashMap;
import java.util.Map;

public class HTMLPageHandler {
    private static String delimiter = "%";

    public String fillTemplate(String htmlFileString, HashMap<String, String> context) throws Exception {
        for(Map.Entry<String, String> entry : context.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            htmlFileString = htmlFileString.replaceAll(this.delimiter + key + this.delimiter, value);
        }
        return htmlFileString;
    }

    private void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
}
