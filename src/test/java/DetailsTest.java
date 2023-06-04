import common.Constants;
import common.RequestTypes;
import static io.restassured.RestAssured.*;

import common.ResponseCodes;
import data.DetailsData;
import io.restassured.RestAssured;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import util.CommonUtil;

import java.io.IOException;

public class DetailsTest {
    CommonUtil commonUtil;
    SoftAssert softAssert;
    JSONObject jsonObject;

    @Test
    public void testGetDetails() throws IOException, ParseException {
        DetailsData detailsData =
                new DetailsData("src/test/resources/DetailsCatalogueFalseData.json");

        commonUtil = new CommonUtil();
        softAssert = new SoftAssert();

//        baseURI = "https://api.tmsandbox.co.nz";
//        basePath = "/v1/Categories/6327/Details.json";

        String response = given().contentType("application/json").queryParam("catalogue", detailsData.getCatalogue()).log().all()
                .when().get("https://api.tmsandbox.co.nz"+String.format("/%s/Categories/6327/Details.json", detailsData.getApiVersion()))
                .then().log().all().statusCode(ResponseCodes.RESPONSE_CODE_200).extract().asString();

        jsonObject = new JSONObject(response);

        softAssert.assertEquals(jsonObject.get("Name"), "Carbon credits", "Name mismatch");
        softAssert.assertEquals(jsonObject.get("CanRelist"), true, "The value for CanRelist is not true");

        jsonObject = new JSONObject(jsonObject.getJSONArray("Promotions").get(1).toString());

        softAssert.assertEquals(jsonObject.get("Description"), "Good position in category",
                "Description for promotions Gallery is not correct");

        softAssert.assertAll();

    }

}
