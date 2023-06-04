package data;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

@Getter
@Setter
public class DetailsData {

    private String catalogue;
    private String apiVersion;

    public DetailsData(String fileName) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(fileName);
        JSONObject detailsJsonData = (JSONObject) jsonParser.parse(reader);

        this.catalogue = detailsJsonData.get("catalogue").toString();
        this.apiVersion = detailsJsonData.get("apiVersion").toString();

    }

}
