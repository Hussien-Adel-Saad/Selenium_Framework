package org.hussien.core.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;


public class JSONParser {

    public JsonNode parseJSON(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            return mapper.readTree(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON file: " + filePath, e);
        }
    }

    // Helper method to get nested values
    public static String getValue(JsonNode node, String key) {
        return node.has(key) ? node.get(key).asText() : null;
    }
}