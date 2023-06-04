package util;

import org.json.JSONObject;

public class CommonUtil {
    JSONObject jsonObject;

    public JSONObject convertStringToJson(String value) {
        jsonObject = new JSONObject(value);
        return jsonObject;
    }
}
